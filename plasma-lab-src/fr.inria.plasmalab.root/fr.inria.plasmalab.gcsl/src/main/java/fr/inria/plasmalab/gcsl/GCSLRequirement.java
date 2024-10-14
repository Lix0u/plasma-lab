/**
 * This file is part of fr.inria.plasmalab.gcsl.
 *
 * fr.inria.plasmalab.gcsl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.gcsl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.gcsl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.gcsl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.bltl.checker.BLTLChecker;
import fr.inria.plasmalab.bltl.parser.FormulaLexer;
import fr.inria.plasmalab.bltl.parser.FormulaParser;
import fr.inria.plasmalab.gcsl.tools.GCSL2Plasma;
import fr.inria.plasmalab.workflow.concrete.Variable;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;

public class GCSLRequirement extends AbstractRequirement{

	//Model
	private AbstractModel model;
	//Requirement
	private Expr requirementRoot;
	private BLTLChecker checker;
	
	//Optimization
	private List<Variable> optimizationVariables;
	
	public GCSLRequirement(String name, File file, String id){
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
		}
		content.substring(0, content.length()-2);
		this.origin = file;
	}
	
	public GCSLRequirement(String name, String content, String id) {
		this.name = name;
		this.id = id;
		this.content = content;
		this.origin = null;
	}

	@Override
	public void setModel(AbstractModel model) {
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
		
		if(model != null){
			if(!model.getId().equals("rml")){
				PlasmaDataException error = new PlasmaDataException("Model is not of the requested RML type.");
				errors.add(error);
				return true;
			}
		}
		try {
			//Translation
			GCSL2Plasma gcslTranslator = new GCSL2Plasma();
			if(model == null)
				throw new PlasmaDataException("No model set for GCSL requirement.");
			gcslTranslator.setModel(model);
			String translation;
			translation = gcslTranslator.translate(content);
			System.err.println(translation);
			
			//Parsing BLTL
			CharStream cs = new ANTLRStringStream(translation);
			FormulaLexer lexer = new FormulaLexer(cs);
			CommonTokenStream tokens = new CommonTokenStream();
			tokens.setTokenSource(lexer);
			FormulaParser pa = new FormulaParser(tokens);

			if(model != null)
				pa.gFormulaRules.modelIdMap = model.getIdentifiers();
			else
				pa.gFormulaRules.modelIdMap = new HashMap<String, InterfaceIdentifier>();
			
			try {
				requirementRoot = pa.formula();
				if(!pa.getErrors().isEmpty()){
					for(PlasmaDataException exc:pa.getErrors()){
						errors.add(new PlasmaDataException("PlasmaException occured after GCSL translation", exc));
					}
					return true;
				}
			} catch (RecognitionException e) {
				PlasmaDataException error = new PlasmaDataException(e);
				errors.add(error);
				//e.printStackTrace();
				return true;
			}
			checker = new BLTLChecker(model);
			optimizationVariables = pa.gFormulaRules.optimizationVariables;
			return false;
		} catch (PlasmaDataException e) {
			errors.add(e);
			return true;
		} catch (PlasmaRuntimeException e) {
			errors.add(new PlasmaDataException(e));
			return true;
		}
	}

	@Override
	public List<Variable> getOptimizationVariables() {
		if(optimizationVariables==null)
			optimizationVariables = new ArrayList<Variable>();
		return optimizationVariables;
	}
}