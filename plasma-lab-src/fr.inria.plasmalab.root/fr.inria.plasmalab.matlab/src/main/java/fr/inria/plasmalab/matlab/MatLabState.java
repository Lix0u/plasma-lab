/**
 * This file is part of fr.inria.plasmalab.matlab.
 *
 * fr.inria.plasmalab.matlab is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.matlab is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.matlab.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.matlab;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;

public class MatLabState implements InterfaceState {
	private int stateNb;
	private MatLabSessionModel model;
	
	public MatLabState(MatLabSessionModel model, int stateNb){
		this.stateNb = stateNb;
		this.model = model;
	}

	@Override
	public InterfaceIdentifier[] getHeaders() {
		Collection<InterfaceIdentifier> ids = model.getIdentifiers().values();
		InterfaceIdentifier[] headers = ids.toArray(new InterfaceIdentifier[ids.size()]);
		return headers;
	}

	@Override
	public Double getValueOf(String id) {
		InterfaceIdentifier iid;
		if(id.equals("Time"))
			iid= model.getIdentifiers().get("");
		else
			iid= model.getIdentifiers().get(id);
		return model.getValues().get(iid)[stateNb];
	}

	@Override
	public String getCategory() {
		return "MATLAB Session";
	}

	@Override
	@Deprecated
	public String[] toStringArray() {
		return null;
	}

	@Override
	public InterfaceTransition getIncomingTransition() {
		return null;
	}

	@Override
	public Double getValueOf(InterfaceIdentifier id) {
		//Assume that if there is not enough values continue with last value
		//This is the case with constant who only have one value
		double[] values = model.getValues().get(id);
		int last = stateNb >= values.length ? values.length-1 : stateNb;
		return values[last];
	}

	@Override
	public void setValueOf(InterfaceIdentifier id, double value) {	}

	@Override
	public InterfaceState cloneState() {
		return null;
	}
	
	public void setStateNb(int stateNb){
		this.stateNb = stateNb;
	}

	@Override
	public Map<InterfaceIdentifier, Double> getValues() {
		Map<InterfaceIdentifier, Double> ret = new HashMap<InterfaceIdentifier, Double>(model.getIdentifiers().size());
		for (InterfaceIdentifier id : model.getIdentifiers().values()) {
			ret.put(id, getValueOf(id));
		}
		return ret;
	}

}
