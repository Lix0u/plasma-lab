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
package fr.inria.plasmalab.altl.prismparser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.rmlbis.simulator.RMLState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

/** A tree structure to store states output by PRISM */
public class PRISMResult {

	/** The children are the possible values for the next variable.
	 *  Integer and boolean values are stored as integers according to the rule 0 means false and not 0 means true; 
	 */
	private Map<Integer,PRISMResult> childs;
	
	public PRISMResult() {
		childs = new HashMap<Integer,PRISMResult>(0);
	}
	
	/** Insert a value to this node and return the next node:
	 *  - if a child node exists for this value, return this child
	 *  - otherwise create a new child node associated to this value. 
	 * @param val : the integer value associated to the child node.
	 * @return the child node.
	 */
	public PRISMResult insertValue(int val) {
		Integer intVal = new Integer(val);
		PRISMResult child = childs.get(intVal);
		if (child == null) {
			child = new PRISMResult();
			childs.put(intVal, child);
		}
		return child;
	}

	/** Check if a state belongs to the results:
	 * @param state : a map of IDs and Double value.
	 * @param IDsOrdering : a list of IDs that gives the ordering of the IDs in the results structure.
	 * @param index : the current position in the list of IDS.
	 * @throws PlasmaSimulatorException 
	 */
	public boolean contains(RMLState state, List<String> IDsOrdering, int index) throws PlasmaSimulatorException {
		if (index >= IDsOrdering.size())
			return true;
		String currentIDName = IDsOrdering.get(index);
		Double val = state.getValueOf(currentIDName);
		if (Double.isNaN(val)) {
			// inefficient: we have to examine all the childs
			for (PRISMResult child : childs.values())
				if (child.contains(state,IDsOrdering,index+1))
					return true;
			return false;	
		}
		
		int intVal = val.intValue();
		PRISMResult child = childs.get(new Integer(intVal));
		if (child == null)
			return false;
		else
			return child.contains(state,IDsOrdering,index+1);
	}
	
}
