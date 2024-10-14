/**
 * This file is part of fr.inria.plasmalab.bio :: Biological models.
 *
 * fr.inria.plasmalab.bio :: Biological models is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bio :: Biological models is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bio :: Biological models.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * This file is part of fr.inria.plasmalab.bio.
 *
 * fr.inria.plasmalab.bio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bio.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.bio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;

public class BioState implements InterfaceState{
	
	private List<Double> table;	/**
	 * Maps an String to its Identifier object.
	 */
	private Map<String,InterfaceIdentifier> identifiers;

	public BioState(int size, Map<String, InterfaceIdentifier> identifiers){
		table = new ArrayList<Double>(size);
		for(int i=0; i<size; i++)
			table.add(Double.NaN);
			// Double.isNaN(table[i]);
		this.identifiers = identifiers;
	}
	
	public BioState(double[] array, Map<String,InterfaceIdentifier> identifiers){
		table = new ArrayList<Double>(array.length);
		for(double d:array)
			table.add(d);
		this.identifiers = identifiers;
	}
	
	public BioState(BioState original){
		this.table = new ArrayList<Double>(original.table);
		this.identifiers = original.identifiers;
	}

	
	@Override
	public String[] toStringArray() {
		String[] t2 = new String[table.size()];
		for(int i=0; i<table.size(); i++)
			t2[i] = String.valueOf(table.get(i));
		return t2;
	}
	
	public void setAtPos(int pos, Double value){
		table.set(pos, value);
	}

	@Override
	public Double getValueOf(String id) {
		BioIdentifier bId = (BioIdentifier) identifiers.get(id);
		return table.get(bId.getStatePos());
	}

	@Override
	public InterfaceIdentifier[] getHeaders() {
		InterfaceIdentifier[] headers = new InterfaceIdentifier[identifiers.size()];
		headers = identifiers.values().toArray(headers);		
		return headers;
	}

	@Override
	public String getCategory() {
		return "Bio Simulation";
	}

	@Override
	public InterfaceTransition getIncomingTransition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getValueOf(InterfaceIdentifier id) {
		return table.get(((BioIdentifier) id).getStatePos());
	}

	@Override
	public void setValueOf(InterfaceIdentifier id, double value) {
		table.set(((BioIdentifier)id).getStatePos(),value);
		
	}

	@Override
	public InterfaceState cloneState() {
		return new BioState(this);
	}
	
	public Double getAtPos(int pos){
		return table.get(pos);
	}

	@Override
	public Map<InterfaceIdentifier, Double> getValues() {
		Map<InterfaceIdentifier, Double> ret = new HashMap<InterfaceIdentifier, Double>(identifiers.size());
		for (InterfaceIdentifier id : identifiers.values()) {
			ret.put(id, getValueOf(id));
		}
		return ret;
	}
}
