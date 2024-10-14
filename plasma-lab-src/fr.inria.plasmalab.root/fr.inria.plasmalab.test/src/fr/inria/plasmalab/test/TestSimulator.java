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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jdom2.Element;

import fr.inria.plasmalab.workflow.concrete.Variable;
import fr.inria.plasmalab.workflow.concrete.VariableConstraint;
import fr.inria.plasmalab.workflow.data.AbstractFunction;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDeadlockException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRunException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulationException;

/** Simulator that reads value of traces from a file and select randomly a trace */
public class TestSimulator extends AbstractModel {
	private static final String id = "testSimulator";
	private static final String nameAtt = "name", contc="content";

	static TestIdentifier valueId = new TestIdentifier("value");
	
	protected List<PlasmaException> errors;

	private List<List<InterfaceState>> traces;
	private int currentTrace;
	private int currentState;
	private List<InterfaceState> traceValues;
	
	public TestSimulator(String name, String content){
		this.name = name;
		this.content = content;
		errors = new ArrayList<PlasmaException>();
	}

	@Override
	public InterfaceState newPath() {
		this.traceValues = new ArrayList<InterfaceState>();
		Random rng = new Random();
		currentTrace = rng.nextInt(traces.size());
		currentState = 0;
		traceValues.add(getCurrentState());
		return getCurrentState();
	}

	@Override
	public InterfaceState newPath(long seed) {
		this.traceValues = new ArrayList<InterfaceState>();
		Random rng = new Random(seed);
		currentTrace = rng.nextInt(traces.size());
		currentState = 0;
		traceValues.add(getCurrentState());
		return getCurrentState();
	}

	@Override
	@Deprecated
	public InterfaceState newPath(List<InterfaceState> trace) {
		// Deprecated
		return newPath();
	}

	@Override
	public void setValueOf(Map<InterfaceIdentifier, Double> update) {
		// do nothing
	}

	@Override
	public InterfaceState simulate() throws PlasmaDeadlockException {
		if (currentState < getTrace().size()-1)
			currentState++;
		traceValues.add(getCurrentState());
		return getCurrentState();
	}

	@Override
	public boolean backtrack() {
		throw new PlasmaSimulationException("Backtrack not supported by simulator.");
	}

	@Override
	public void cut(int stateNb) throws PlasmaRunException {
		throw new PlasmaSimulationException("Cut is not supported by this simulator.");
	}

	@Override
	public InterfaceState getCurrentState() {
		return traces.get(currentTrace).get(currentState);
	}

	@Override
	public InterfaceState getInitialState() {
		return null;
	}

	@Override
	public InterfaceState getStateAtPos(int pos) {
		return traceValues.get(pos);
	}

	@Override
	public List<InterfaceState> getTrace() {
		return traceValues;
	}

	@Override
	public int getTraceLength() {
		return traceValues.size();
	}

	@Override
	public int getDeadlockPos() {
		return traceValues.size();
	}

	@Override
	public List<InterfaceIdentifier> getPathProperty() {
		//Not supported by our simulator
		//Path property are boolean values defined in the simulator
		//These values are displayed in simulation mode
		return null;
	}

	@Override
	public List<Variable> getOptimizationVariables() {
		//Not supported by our simulator
		//Optimization variable can be declared in model
		return null;
	}

	@Override
	public List<VariableConstraint> getOptimizationConstraints() {
		//Not supported by our simulator
		//Optimization constraints can be declared in model
		return null;
	}

	@Override
	public Map<String, InterfaceIdentifier> getIdentifiers() {
		Map<String, InterfaceIdentifier> ret = new HashMap<String, InterfaceIdentifier>(1);
		ret.put(valueId.getName(), valueId);
		return ret;
	}

	@Override
	public InterfaceIdentifier getTimeId() {
		return null;
	}

	@Override
	public boolean hasTime() {
		return false;
	}

	@Override
	public InterfaceIdentifier[] getHeaders() {
		InterfaceIdentifier[] headers = new InterfaceIdentifier[3];
		headers[0] = valueId;
		return headers;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean checkForErrors() {
		if(errors == null)
			errors = new ArrayList<PlasmaException>();
		else
			errors.clear();
		try {
			String[] lines = content.split("\\r?\\n");
			this.traces = new ArrayList<List<InterfaceState> >(lines.length);
			for (int i = 0; i < lines.length; i++) {
				String[] values = lines[i].split("\\s+");
				List<InterfaceState> trace = new ArrayList<>(values.length);
				for (int j = 0; j < values.length; j++) {
					TestState s = new TestState(Double.parseDouble(values[j]));
					trace.add(s);
				}
				traces.add(trace);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			errors.add(new PlasmaException(e));
			return true;
		}
		return false;
	}

	@Override
	public List<PlasmaException> getErrors() {
		return errors;
	}

	@Override
	public void toJDOM(org.jdom2.Element dataElt) {
		dataElt.setAttribute(nameAtt, name);
		Element cont = new Element(contc);
		cont.setText(content);
		dataElt.addContent(cont);
	}

	@Override
	public List<AbstractFunction> getFunctions() {
		return new ArrayList<AbstractFunction>(0);
	}

}
