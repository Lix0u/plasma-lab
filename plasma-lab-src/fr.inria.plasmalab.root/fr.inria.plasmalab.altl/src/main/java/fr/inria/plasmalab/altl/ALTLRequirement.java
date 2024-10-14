/**
 * This file is part of fr.inria.plasmalab.altl.
 *
 * fr.inria.plasmalab.altl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.altl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.altl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.altl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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

import fr.inria.plasmalab.altl.ast.ALTLChecker;
import fr.inria.plasmalab.altl.parser.ALTLLexer;
import fr.inria.plasmalab.altl.parser.ALTLParser;
import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.bltl.checker.VarDefCheck;
import fr.inria.plasmalab.workflow.concrete.Variable;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class ALTLRequirement extends AbstractRequirement{

	//Model
	protected AbstractModel model;
	//Requirement
	protected Expr requirementRoot;
	private ALTLChecker checker;
	
	//Optimization
	protected List<Variable> optimizationVariables;
	//Requirement variables
	/* Requirement variables are declared by a requirement
	 * and used to check multiple instances of the same requirement
	 * modulo values of these variables.
	 */
	protected List<Variable> requirementVariables;
	
	public ALTLRequirement(String name, File file, String id) {
		this.name = name;
		this.id = id;
		content = "";
		try {
			FileReader fr = new FileReader(file.getAbsoluteFile());;
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				content = content+br.readLine()+"\n";
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} content.substring(0, content.length()-2);
		this.origin = file;
	}
	
	public ALTLRequirement(String name, String content, String id){
		this.name = name;
		this.id = id;
		this.content = content;
		this.origin = null;
	}

	@Override
	public void setModel(AbstractModel model) {
		checker = new ALTLChecker(model);
		this.model=model;
	}

	@Override
	public Double check(InterfaceState path) {
		checker.reinit(-1, path);
		requirementRoot.accept(checker);
		return checker.getNumericalAnswer();
	}
	
	@Override
	public Double check(int untilStep, InterfaceState path) {
		checker.reinit(untilStep, path);
		requirementRoot.accept(checker);
		return checker.getNumericalAnswer();
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
		if(errors == null)
			errors = new ArrayList<PlasmaDataException>();
		errors.clear();
		if(content.isEmpty()) {
			errors.add(new PlasmaDataException("Empty BLTL Requirement."));
			return true;
		}
		
		CharStream cs = new ANTLRStringStream(content);
		ALTLLexer lexer = new ALTLLexer(cs);
		CommonTokenStream tokens = new CommonTokenStream();
		tokens.setTokenSource(lexer);
		ALTLParser parser = new ALTLParser(tokens);
		
		if(model != null)
			parser.gFormulaRules.modelIdMap = model.getIdentifiers();
		else
			parser.gFormulaRules.modelIdMap = new HashMap<String, InterfaceIdentifier>();
		
		try {
			requirementRoot = parser.formula();
			// get requirement variables
			requirementVariables = new ArrayList<Variable>(parser.gFormulaRules.requirementVarMap.values());
			
			//If model was set
			//Test variables declarations
			if(model != null){
				Set<String> declarations = new HashSet<String>(model.getIdentifiers().keySet());
				for(Variable var:requirementVariables)
					declarations.add(var.getName());
				VarDefCheck vdf = new VarDefCheck(declarations,new ArrayList<InterfaceIdentifier>(0));
				vdf.doVisit(requirementRoot);
				errors.addAll(vdf.getUndefinedVariables());
			}
			
			if(!errors.isEmpty()){
				errors.addAll(parser.getErrors());
				return true;
			}
		} catch (RecognitionException e) {
			PlasmaSyntaxException error = new PlasmaSyntaxException(e);
			errors.add(error);
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
			optimizationVariables = new ArrayList<Variable>();
		return optimizationVariables;
	}
}
