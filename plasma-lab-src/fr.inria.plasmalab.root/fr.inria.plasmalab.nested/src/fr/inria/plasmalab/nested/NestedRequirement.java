/**
 * This file is part of fr.inria.plasmalab.nested.
 *
 * fr.inria.plasmalab.nested is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.nested is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.nested.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.nested;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.inria.plasmalab.nested.ast.Expr;
import fr.inria.plasmalab.nested.ast.checker.InitStrength;
import fr.inria.plasmalab.nested.ast.checker.SatChecker;
import fr.inria.plasmalab.nested.ast.checker.SeqHypTestArgs;
import fr.inria.plasmalab.nested.ast.nodes.Proba;
import fr.inria.plasmalab.nested.ast.tools.parser.Parser;
import fr.inria.plasmalab.nested.ast.tools.parser.Scanner;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class NestedRequirement extends AbstractRequirement {
	public static final String badAlgoMsg = "A nested probability can only be" +
			" checked by the nested sequential hypothesis testing algorithm.\n"+
			"Experimentation interrupted.";
	
	//Model
	private AbstractModel model;
	//Requirement
	private Expr requirementRoot;
	private SatChecker checker;
	
	//Errors
	private boolean setToSequential;
	
	public NestedRequirement(String name, File file, String id){
		this.name = name;
		this.id = id;
		content = "";
		try {
			FileReader fr = new FileReader(file.getAbsoluteFile());
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
	
	public NestedRequirement(String name, String content, String id){
		this.name = name;
		this.id = id;
		this.content = content;
		this.origin = null;
	}

	@Override
	public void setModel(AbstractModel model) {
		checker = new SatChecker(model);
		this.model=model;
	}

	@Override
	public Double check(InterfaceState path) throws PlasmaCheckerException {
		if(setToSequential){
			try { 
				checker.reinit(-1, path);
				checker.apply(requirementRoot);
				//requirementRoot.accept(checker);
				return checker.getNumericalAnswer();
			} catch (PlasmaRuntimeException e) {
				throw new PlasmaCheckerException(e.getCause());
			}
		}else{
			throw new PlasmaCheckerException(badAlgoMsg);
		}
	}
	
	@Override
	public Double check (int untilStep, InterfaceState path) throws PlasmaCheckerException{
		throw new PlasmaCheckerException("Uncompatible requirement");
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
		setToSequential = false;
		if(errors == null)
			errors = new ArrayList<PlasmaDataException>();
		else
			errors.clear();
		try { 
			Scanner sc = new Scanner(content.getBytes());
			Parser pa = new Parser(sc);
			pa.Parse();
			requirementRoot = pa.getRoot();
			if(pa.errors.firstError != null){
				PlasmaDataException error = new PlasmaSyntaxException(pa.errors.firstError);
				errors.add(error);
			}
		}
		catch (Exception e) {
			errors.add(new PlasmaSyntaxException(e));
			return true;
		} 
		if (!(requirementRoot instanceof Proba)) {
			PlasmaDataException error = new PlasmaSyntaxException("The root of a nested requirement must be a probability operator.");
			errors.add(error);
		}	
		return false;		
	}

	public void setSpecificParameters(double alpha, double beta, double delta) {
		SeqHypTestArgs[] shtArgs = { new SeqHypTestArgs(alpha, beta, delta) };
		InitStrength init = new InitStrength(shtArgs);
		init.apply(requirementRoot);	
		setToSequential = true;
	}
}
