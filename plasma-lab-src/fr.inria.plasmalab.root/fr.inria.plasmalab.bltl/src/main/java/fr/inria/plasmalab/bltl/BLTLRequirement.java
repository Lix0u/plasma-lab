/**
 * This file is part of fr.inria.plasmalab.bltl.
 *
 * fr.inria.plasmalab.bltl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bltl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bltl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.bltl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.bltl.ast.printer.BLTLRequirementInstantiator;
import fr.inria.plasmalab.bltl.checker.BLTLChecker;
import fr.inria.plasmalab.bltl.checker.VarDefCheck;
import fr.inria.plasmalab.bltl.parser.FormulaLexer;
import fr.inria.plasmalab.bltl.parser.FormulaParser;
import fr.inria.plasmalab.workflow.concrete.Variable;
import fr.inria.plasmalab.workflow.data.AbstractFunction;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class BLTLRequirement extends AbstractRequirement{
	private static BLTLToObserver bltltoobserver = new BLTLToObserver(); 

	//Model
	protected AbstractModel model;
	//Requirement
	protected Expr requirementRoot;
	protected BLTLChecker checker;
	
	//Optimization
	protected List<Variable> optimizationVariables;
	//Requirement variables
	/* Requirement variables are declared by a requirement
	 * and used to check multiple instances of the same requirement
	 * modulo values of these variables.
	 */
	protected List<Variable> requirementVariables;
	//Override identifiers
	/* Override identifiers are used to bypass the varDefCheck.
	 * This allow to use identifiers as values in certain circumstances
	 * like PLASMA2Simulink where a String is used as a value for the
	 * variable action. This override is done without check and under 
	 * the user responsibility.
	 */
	protected List<InterfaceIdentifier> overrideIdentifiers;
	
	public BLTLRequirement(String name, File file, String id) throws PlasmaDataException{
		this.name = name;
		this.id = id;
		content = "";
		try {
			FileReader fr = new FileReader(file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				content = content+br.readLine()+"\n";
			br.close();
		} catch (IOException e) {
			throw new PlasmaDataException(e);
		}  
		this.origin = file;
		this.errors = new ArrayList<PlasmaDataException>(0);
	}
	
	public BLTLRequirement(String name, String content, String id){
		this.name = name;
		this.id = id;
		this.content = content;
		this.origin = null;
		this.errors = new ArrayList<PlasmaDataException>(0);
	}

	@Override
	public void setModel(AbstractModel model) {
		checker = new BLTLChecker(model);
		this.model=model;
	}

	@Override
	public Double check(InterfaceState path) throws PlasmaCheckerException {
		try {
			checker.reinit(-1, path);
			requirementRoot.accept(checker);
			return checker.getNumericalAnswer();
		} catch(PlasmaRuntimeException e) {
			throw new PlasmaCheckerException(e.getCause());
		}
	}
	
	@Override
	public Double check(int untilStep, InterfaceState path) throws PlasmaCheckerException {
		try {
			checker.reinit(untilStep, path);
			requirementRoot.accept(checker);
			return checker.getNumericalAnswer();
		} catch(PlasmaRuntimeException e) {
			throw new PlasmaCheckerException(e.getCause());
		}
	}
	
	@Override
	public Double check(String id, double untilValue, InterfaceState path) throws PlasmaCheckerException {
		throw new PlasmaCheckerException("Uncompatible requirement");
	}

	@Override
	public List<InterfaceState> getLastTrace() {
		return model.getTrace();
	}

	@Override
	public boolean checkForErrors() {
		errors.clear();
		if(content.isEmpty()) {
			errors.add(new PlasmaDataException("Empty BLTL Requirement."));
			return true;
		}
		
		CharStream cs = new ANTLRStringStream(content);
		FormulaLexer lexer = new FormulaLexer(cs);
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		FormulaParser parser = new FormulaParser(tokens);
		
		//Scanner sc = new Scanner(content.getBytes());
		//ParserSean pa = new ParserSean(sc);
		if(model != null)
			parser.gFormulaRules.modelIdMap = model.getIdentifiers();
		else
			parser.gFormulaRules.modelIdMap = new HashMap<String, InterfaceIdentifier>();
		try {
			requirementRoot = parser.formula();
			errors.addAll(parser.getErrors());
			if (!errors.isEmpty())
				return true;
			
			// get requirement variable
			requirementVariables = new ArrayList<Variable>(parser.gFormulaRules.requirementVarMap.values());
			overrideIdentifiers = parser.gFormulaRules.overrideIdentifiers;
			
			//If model was set
			//Test variables declarations
			if(model != null){
				Set<String> declarations = new HashSet<String>(model.getIdentifiers().keySet());
				for(Variable var:requirementVariables)
					declarations.add(var.getName());
				VarDefCheck vdf = new VarDefCheck(declarations, overrideIdentifiers);
				vdf.doVisit(requirementRoot);
				errors.addAll(vdf.getUndefinedVariables());
			}
		} catch (RecognitionException e) {
			PlasmaSyntaxException error = new PlasmaSyntaxException(e);
			errors.add(error);
			//e.printStackTrace();
			return true;
		} catch (PlasmaRuntimeException e) {
			PlasmaSyntaxException error = new PlasmaSyntaxException(e);
			errors.add(error);
			//e.printStackTrace();
			return true;
		}
		optimizationVariables = parser.gFormulaRules.optimizationVariables;
		return false;		
	}

	@Override
	public List<Variable> getOptimizationVariables() {
		if(optimizationVariables==null)
			optimizationVariables = new ArrayList<Variable>(0);
		return optimizationVariables;
	}

	@Override
	public List<AbstractRequirement> generateInstantiatedRequirement() throws PlasmaDataException {
		List<String> reqsInst = new BLTLRequirementInstantiator(requirementVariables).instantiate(requirementRoot);
		if(reqsInst == null || reqsInst.size()<=0)
			return new ArrayList<AbstractRequirement>();
		List<AbstractRequirement> reqList = new ArrayList<AbstractRequirement>();
		for(String req:reqsInst){
			BLTLRequirement newReq = new BLTLRequirement(this.name+" "+req, req, this.id);
			reqList.add(newReq);
			newReq.setModel(model);
			if(newReq.checkForErrors()){
				String errorMsg = "Error in "+newReq.getName();
				for(PlasmaDataException err:newReq.getErrors()){
					errorMsg += "\n"+err.getMessage();
				}
				throw new PlasmaDataException(errorMsg);
			}
		}
		
		return reqList;
	}

	@Override
	public List<AbstractFunction> getFunctions() {
		List<AbstractFunction> result = new ArrayList<AbstractFunction>(1);
		result.add(bltltoobserver);
		return result;
	}
}
