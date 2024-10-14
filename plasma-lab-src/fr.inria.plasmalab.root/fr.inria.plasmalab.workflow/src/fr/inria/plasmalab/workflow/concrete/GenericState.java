/**
 * This file is part of fr.inria.plasmalab.workflow.
 *
 * fr.inria.plasmalab.workflow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.workflow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.workflow.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.workflow.concrete;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

/** Generic state object that stores a map of values
 *  keys are InterfaceIdentififer
 *  values are Double 
 */
public class GenericState implements InterfaceState {

	private Map<InterfaceIdentifier,Double> values;
	
	public GenericState(Map<InterfaceIdentifier,Double> v) {
		this.values = v;
	}

	public GenericState(GenericState original) {
		this.values = new HashMap<InterfaceIdentifier,Double>(original.getValues());
	}
	
	@Override
	public InterfaceState cloneState() {
		return new GenericState(this);
	}
	
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
	public String getCategory() {
		return "Generic state";
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
			int intVal = value.intValue();
			if (value == intVal)
				ret += name + ": " + intVal + ";\n";
			else
				ret += name + ": " + value + ";\n";
		}
		return ret;
	}

	@Override
	public InterfaceTransition getIncomingTransition() {
		return null;
	}

	@Override
	public Double getValueOf(InterfaceIdentifier id) throws PlasmaSimulatorException {
		Double value = values.get(id);
		if (value != null)
			return value;
		throw new PlasmaSimulatorException("Unknown identifier: " + id.getName());
	}

	@Override
	public Double getValueOf(String id) throws PlasmaSimulatorException {
		for (Map.Entry<InterfaceIdentifier, Double> e : values.entrySet())
			if (e.getKey().getName().equals(id))
				return e.getValue();
		throw new PlasmaSimulatorException("Unknown identifier: " + id);
	}

	@Override
	public Map<InterfaceIdentifier, Double> getValues() {
		return values;
	}

	@Override
	public void setValueOf(InterfaceIdentifier id, double value) throws PlasmaSimulatorException {
		if (values.containsKey(id))
			values.put(id, value);
		else
			throw new PlasmaSimulatorException("Unknown identifier: " + id.getName());
	}



}
