/**
 * This file is part of fr.inria.plasmalab.rmlbis.
 *
 * fr.inria.plasmalab.rmlbis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.rmlbis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.rmlbis.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.rmlbis.simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
import fr.inria.plasmalab.rmlbis.ast.processing.Update;
import fr.inria.plasmalab.rmlbis.ast.rewards.Reward;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public class RMLState implements InterfaceState {

	/** Map an identifier name to a double value */
	protected Map<InterfaceIdentifier, Double> values;
	/** The transition used to reach the state. */
	protected InterfaceTransition incomingTransition;
	/** System in which the state is defined.*/
	protected PlasmaSystem system;

	/** Copy a state */
	public RMLState (RMLState original) {
		this.system = original.system;
		this.values = new HashMap<InterfaceIdentifier,Double>(original.values);
		this.incomingTransition = original.getIncomingTransition(); 
	}

	/** Construct a new state from a map of values. */
	public RMLState (Map<InterfaceIdentifier,Double> values, InterfaceTransition t, PlasmaSystem sys) {
		this.values = values;
		this.incomingTransition = t;
		this.system = sys;
	}

	public PlasmaSystem getSystem() {
		return system;
	}
	
	@Override

	public InterfaceTransition getIncomingTransition() {
		return incomingTransition;
	}

	@Override
	public InterfaceState cloneState() {
		return new RMLState(this);
	}
	
	/** Update all the expressions in the model to the values of this state. */
	public void updateExpressions() {	
		List<Ref> updated = new ArrayList<Ref>(values.size());
		for(Entry<InterfaceIdentifier,Double> val : values.entrySet()) {
			if (val.getKey() instanceof Identifier) {
				Identifier id = (Identifier) val.getKey();
	    		if (id.ref.value != val.getValue().doubleValue()) {
	    			id.ref.value = val.getValue().doubleValue();
	    	    	updated.add(id.ref);
	    		}
	    	}
		}
		Update up = new Update();
		up.propagate(updated);
	}

	/** Return an array with the state variables identifiers and the state labels identifiers.
	 *  The array is sorted by alphabetic order. 
	 */
	@Override
	public InterfaceIdentifier[] getHeaders() {
		InterfaceIdentifier[] ret = new InterfaceIdentifier[values.size()];
		int index=0;
		for (InterfaceIdentifier val : values.keySet()) {
			ret[index] = val;
			index++;
		}
		Arrays.sort(ret);
		return ret;
	}
	
	@Override
	public Map<InterfaceIdentifier, Double> getValues() {
		return this.values;
	}

	/** Return the value of an identifier specified by its name.
	 *  1. If the name includes the system's name ("system.module.variable"), then it checks if the current system
	 *  corresponds to the identifier's system. If YES it return the value of the identifier "module.variable.
	 *  If NO it returns NaN.
	 *  2. The name is looked up in the system's idents map. If no Ref correspond to this name it returns NaN.
	 *  3. If there is a Ref associated to the name and this Ref has a target identifier, it returns the value of this identifier.
	 *  Else if the target is null it returns NaN.  
	 *  
	 * @throws PlasmaSimulatorException if name is not a value of the state
	 */
	@Override
	public Double getValueOf(String name) throws PlasmaSimulatorException {
		// variable
		String[] parts = name.split("\\.");
		if (parts.length > 2) {
			if (!parts[0].equals(system.getName())) {
				return Double.NaN;
			}
			name = parts[1] + "." + parts[2];
		}
		Ref ref = system.getIdent(name);
		if (ref != null && ref.target != null) {
			return getValueOf(ref.target);
		}
		
		// label
		InterfaceIdentifier id = system.getLabel(name);
		if (id != null) {
			return getValueOf(id);
		}
		
		// instantaneous reward
		if (name.endsWith("[I]\"")) {
			String s = name.replace("[I]","");
			Reward r = system.model.getReward(s);	
			if (r != null)
				return r.getStateReward(); // BEWARE: only works if the model expressions are evaluated to the value of this state.
			else
				throw new PlasmaSimulatorException("Unknown reward identifier: " + s);
		}
		
		// continuous reward
		id = system.model.getReward(name);
		if (id != null) {
			return getValueOf(id);
		}
		
		// else search through the values
		for (Map.Entry<InterfaceIdentifier, Double> e : values.entrySet()) {
			if (e.getKey().getName().equals(name)) {
				return e.getValue();
			}
		}
		throw new PlasmaSimulatorException("Unknown identifier: " + name);
	}

	/** Returns the value of an identifier:
	 *  - If the identifier belongs to the map values it returns the double value associated to the identifier.
	 *  - If the identifier is a constant it returns its fixed value.
	 *  - Else it returns NaN.
	 */
	@Override
	public Double getValueOf(InterfaceIdentifier id) throws PlasmaSimulatorException {
		Double value = values.get(id);
		if (value != null)
			return value;
		if (id instanceof Constant) {
			Constant c = (Constant) id;
			return c.ref.value;
		}
		throw new PlasmaSimulatorException("Unknown identifier: " + id.getName());
	}

	@Override
	public void setValueOf(InterfaceIdentifier id, double value) throws PlasmaSimulatorException {
		if (values.containsKey(id))
			values.put(id, value);
		else
			throw new PlasmaSimulatorException("Unknown identifier: " + id.getName());
	}

	@Override
	public String getCategory() {
		return "RML Simulation";
	}

	@Override
	public String[] toStringArray() {
		String [] ret = new String[values.size()];
		int i = 0;
		for(Double value : values.values()) {
			ret[i] = value.toString();
			i++;
		}
		return ret;
	}

	public String toString() {
		String ret = "";
		for(Entry<InterfaceIdentifier,Double> et : values.entrySet()) {
			String name = et.getKey().toString();
			Double value = et.getValue();
			if (name.isEmpty())
				name = "time";
			int intVal = value.intValue();
			if (value == intVal)
				ret += name + ": " + intVal + ";\n";
			else
				ret += name + ": " + value + ";\n";
		}
		return ret;
	}
}
