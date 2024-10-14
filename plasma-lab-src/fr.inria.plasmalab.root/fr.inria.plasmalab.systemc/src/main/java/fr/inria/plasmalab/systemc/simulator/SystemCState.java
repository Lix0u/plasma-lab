/**
 * This file is part of fr.inria.plasmalab.systemc :: systemC plugin.
 *
 * fr.inria.plasmalab.systemc :: systemC plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.systemc :: systemC plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.systemc :: systemC plugin.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.systemc.simulator;

import java.util.Map;

import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public class SystemCState implements InterfaceState {
	
	private Map<InterfaceIdentifier,Double> values;
	
	public SystemCState(Map<InterfaceIdentifier,Double> values) {
		this.values=values;
	}

	@Override
	public InterfaceIdentifier[] getHeaders() {
		InterfaceIdentifier[] names =   new SystemCIdentifier[values.size()];
		int i = 0;
		for(InterfaceIdentifier id : values.keySet()){
			names[i]=id;
		}
		return names;
	}

	@Override
	public String getCategory() {
		//Category is needed because InterfaceState inherit from InterfaceResult.
		return "SystemC simulation";
	}

	@Override
	public String[] toStringArray() {
		return new String[]{};
	}

	@Override
	public InterfaceTransition getIncomingTransition() {
		return null;
	}

	@Override
	public Double getValueOf(InterfaceIdentifier id) throws PlasmaSimulatorException {
		if (values.containsKey(id))
			return values.get(id);
		else
			throw new PlasmaSimulatorException("Unknown identifier: "+id.getName());
	}

	@Override
	public Double getValueOf(String id) throws PlasmaSimulatorException {
        for(InterfaceIdentifier scid : values.keySet()){
        	if (scid.equals(id)){
        		return values.get(scid);
        	}
        }
		throw new PlasmaSimulatorException("Unknown identifier: "+id);
	}

	@Override
	public void setValueOf(InterfaceIdentifier id, double value) throws PlasmaSimulatorException {
		if (values.containsKey(id)){
		   values.put(id, value);
		}
		else
			throw new PlasmaSimulatorException("Unknown identifier: "+id.getName());
	}

	@Override
	public InterfaceState cloneState() {
		return new SystemCState(values);
	}

	@Override
	public Map<InterfaceIdentifier, Double> getValues() {
		return values;
	}

}
