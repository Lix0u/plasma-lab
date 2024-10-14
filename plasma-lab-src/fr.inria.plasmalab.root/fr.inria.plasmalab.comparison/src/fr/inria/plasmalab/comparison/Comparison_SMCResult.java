/**
 * This file is part of fr.inria.plasmalab.comparison.
 *
 * fr.inria.plasmalab.comparison is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.comparison is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.comparison.  If not, see <http://www.gnu.org/licenses/>.
 */
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
/**
 * This file is part of fr.inria.plasmalab.sequential.
 *
 * fr.inria.plasmalab.sequential is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.sequential is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.sequential.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.comparison;

import java.util.HashMap;
import java.util.Map;

import fr.inria.plasmalab.algorithm.data.SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.shared.ResultIdentifier;

public class Comparison_SMCResult implements SMCResult {
	private int result; // -1: reject, 0: indifferent, 1: accept
	private int totalCount;
	
	private AbstractRequirement lowerReq;
	private AbstractRequirement greaterReq;
	
	private HashMap<InterfaceIdentifier, Object> values;
	private InterfaceIdentifier[] headers;

	private static final ResultIdentifier simId = new ResultIdentifier("# Simulations", false);
	
	public Comparison_SMCResult(int totalCount, int result, Map<InterfaceIdentifier, Double> initState, InterfaceIdentifier[] optimVars,
			AbstractRequirement lowerReq, AbstractRequirement greaterReq, AbstractModel model){
		
		this.totalCount = totalCount;
		this.result = result;
		
		this.headers = new InterfaceIdentifier[1+optimVars.length];		
		this.values = new HashMap<InterfaceIdentifier, Object>(1+optimVars.length);
		
		int i = 0;
		Map<String, InterfaceIdentifier> modelIdentifiers = model.getIdentifiers();
		for(InterfaceIdentifier var:optimVars){
			InterfaceIdentifier id = modelIdentifiers.get(var.getName());
			if(initState.containsKey(id)){
				this.values.put(var, initState.get(id));
			}
			else{
				// If there is no modelIdentifier equivalent to the var
				// We add the variable as an identifier in the map
				this.values.put(var, initState.get(var));
			}
			headers[i] = var;
			i++;
		}
		
		this.values.put(simId, totalCount);
		headers[i] = simId;
	
		this.lowerReq = lowerReq;
		this.greaterReq = greaterReq;
	}

	@Override
	@Deprecated
	public AbstractRequirement getOriginRequirement() {
		return greaterReq;
	}

	@Override
	public InterfaceIdentifier[] getHeaders() {
		return headers;
	}

	@Override
	public Object getValueOf(String header) throws PlasmaExperimentException {
		for(InterfaceIdentifier id:values.keySet()){
			if(id.getName().equals(header))
				return values.get(id);
		}
		throw new PlasmaExperimentException("header " + header + " not found in Comparison_SMCResult.");
	}

	@Override
	public String getCategory() {
		return lowerReq.getName() + " <= " + greaterReq.getName();
	}

	@Override
	public double getPr() {
		switch (result) {
		case -1:
			return 0.0;
		case 0:
			return 0.5;
		case 1:
			return 1.0;
		default:
			return 0.5;
		} 
	}

	@Override
	public int getTotalCount() {
		return totalCount;
	}

	@Override
	public Object getValueOf(InterfaceIdentifier id) throws PlasmaExperimentException {
		Object o = values.get(id);
		if (o != null)
			return o;
		else
			throw new PlasmaExperimentException("header ID " + id.getName() + " not found in Sequential_SMCResult.");
	}

}