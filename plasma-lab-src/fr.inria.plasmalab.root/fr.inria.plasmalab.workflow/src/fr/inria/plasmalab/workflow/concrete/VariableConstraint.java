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

import java.util.Map;

import fr.inria.plasmalab.workflow.concrete.VarContext;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

public class VariableConstraint {
	
	private String varlow;
	private String varhigh;
	private boolean sign; // true if strict
	
	public VariableConstraint(String varlow, String varhigh, boolean sign) {
		this.varlow = varlow;
		this.varhigh = varhigh;
		this.sign = sign;
	}
	
	/** Check if a state satisfies the constraint */
	public boolean check(Map<InterfaceIdentifier, Double> state, Map<String,VarContext> varsMap) {
		VarContext context = varsMap.get(varlow);
		if (context == null)
			return true;
		Double valueLow = state.get(context.getVar());
		context = varsMap.get(varhigh);
		if (context == null)
			return true;
		Double valueHigh = state.get(context.getVar());
		if (valueLow != null && valueHigh != null) {
			if (sign)
				if (valueLow >= valueHigh)
					return false;
			else
				if (valueLow > valueHigh)
					return false;
		} 
		return true; 
	}

}
