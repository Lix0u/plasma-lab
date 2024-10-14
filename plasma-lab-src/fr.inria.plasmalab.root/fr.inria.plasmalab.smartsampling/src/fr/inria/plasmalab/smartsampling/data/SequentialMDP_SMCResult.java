/**
 * This file is part of fr.inria.plasmalab.smartsampling.
 *
 * fr.inria.plasmalab.smartsampling is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.smartsampling is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.smartsampling.  If not, see <http://www.gnu.org/licenses/>.
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
package fr.inria.plasmalab.smartsampling.data;

import java.util.HashMap;
import java.util.Map;

import fr.inria.plasmalab.algorithm.data.SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.shared.ResultIdentifier;

public class SequentialMDP_SMCResult implements SMCResult {
	private int satisfied;
	private int totalSimulations;
	
	private AbstractRequirement origin;
	
	private HashMap<InterfaceIdentifier, Object> values;
	private InterfaceIdentifier[] headers;

	private static final ResultIdentifier satisfiedId = new ResultIdentifier("Satisfied", false);
	private static final ResultIdentifier iterationsId = new ResultIdentifier("# Iterations", false);
	private static final ResultIdentifier totalSimId = new ResultIdentifier("# Total Simulations", false);
	
	public SequentialMDP_SMCResult(int iterations, int totalSimulations, int satisfied, Map<InterfaceIdentifier, Double> initState, InterfaceIdentifier[] optimVars,
			AbstractRequirement origin, AbstractModel model){
		this.totalSimulations = totalSimulations;
		this.satisfied = satisfied;
		
		this.headers = new InterfaceIdentifier[3+optimVars.length];		
		this.values = new HashMap<InterfaceIdentifier, Object>(3+optimVars.length);
		
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
		
		switch(satisfied) {
		case 1:
			this.values.put(satisfiedId,"true"); break;
		case 0:
			this.values.put(satisfiedId,"false"); break;
		default:
			this.values.put(satisfiedId,"undecided"); break;
		}
		headers[i] = satisfiedId;
		this.values.put(iterationsId, iterations);
		headers[i+1] = iterationsId;
		this.values.put(totalSimId, totalSimulations);
		headers[i+2] = totalSimId;

		this.origin = origin;
	}

	@Override
	public AbstractRequirement getOriginRequirement() {
		return origin;
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
		throw new PlasmaExperimentException("header " + header + " not found in SequentialMDP_SMCResult.");
	}

	@Override
	public String getCategory() {
		return origin.getName();
	}

	@Override
	public double getPr() {
		return satisfied==1? 1. : 0.;
	}

	@Override
	public int getTotalCount() {
		return totalSimulations;
	}

	@Override
	public Object getValueOf(InterfaceIdentifier id) throws PlasmaExperimentException {
		Object o = values.get(id);
		if (o != null)
			return o;
		else
			throw new PlasmaExperimentException("header ID " + id.getName() + " not found in SequentialMDP_SMCResult.");
	}

}