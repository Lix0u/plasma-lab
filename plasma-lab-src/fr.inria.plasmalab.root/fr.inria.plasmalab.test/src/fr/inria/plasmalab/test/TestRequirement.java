/**
 * This file is part of fr.inria.plasmalab.root.
 *
 * fr.inria.plasmalab.root is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.root is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.root.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.test;


import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import fr.inria.plasmalab.workflow.concrete.Variable;
import fr.inria.plasmalab.workflow.data.AbstractFunction;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaException;


/** Allows to define deterministic requirement. No model is needed by the requirement (any model can be selected as one
 * is required by the GUI).
 * It reads the value it should return from the file and it outputs them in the same order.
 * It cycles the values if more values are needed.
 *  
 * @author ltraonou
 *
 */
public class TestRequirement extends AbstractRequirement {
	
	private double values[];
	private List<PlasmaException> errors;
	private int currentValue;
	
	public TestRequirement(String name, String content) {
		this.name = name;
		this.content = content;
		this.origin = null;
		this.errors = new ArrayList<PlasmaException>();
	}
	
	@Override
	public boolean checkForErrors() {
		errors.clear();
		String[] strValues = content.split("\\s+");
		this.values = new double[strValues.length];
		this.currentValue = 0;
		try {
			for (int i = 0; i < strValues.length; i++) {
				values[i] = Double.parseDouble(strValues[i]);
			}
		} catch (NumberFormatException e) {
			errors.add(new PlasmaException(e));
			return true;
		}		
		return false;
	}
	
	@Override
	public Double check(InterfaceState path) {
		double ret = values[currentValue];
		currentValue++;
		if (currentValue >= values.length)
			currentValue = 0;
		return ret;
	}
	
	@Override
	public Double check(int untilStep, InterfaceState path) {
		return check(path);
	}
	
	@Override
	public Double check(String id, double untilValue, InterfaceState path) {
		return check(path);
	}
	
	@Override
	public List<InterfaceState> getLastTrace() {
		ArrayList<InterfaceState> trace = new ArrayList<InterfaceState>(1);
		trace.add(new TestState(0));
		return trace;
	}
	
	@Override
	public void setModel(AbstractModel model) { };
	
	@Override
	public List<Variable> getOptimizationVariables() { return new ArrayList<Variable>(0); }

	@Override
	public List<AbstractRequirement> generateInstantiatedRequirement() { return new ArrayList<AbstractRequirement>(0); }
	
	@Override
	public void setSpecificParameters(Object[] parameters) { }

	@Override
	public String getId() {	
		return "testRequirement";
	}


	@Override
	public List<PlasmaException> getErrors() {
		return new ArrayList<PlasmaException>();
	}

	@Override
	public void toJDOM(Element dataElt) {
		dataElt.setAttribute("name", name);
		Element cont = new Element("content");
		cont.setText(content);
		dataElt.addContent(cont);
	}

	@Override
	public List<AbstractFunction> getFunctions() {
		return new ArrayList<AbstractFunction>(0);
	};
}
