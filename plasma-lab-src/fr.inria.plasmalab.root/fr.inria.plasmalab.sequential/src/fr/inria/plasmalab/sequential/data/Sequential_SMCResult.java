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
package fr.inria.plasmalab.sequential.data;

import java.util.HashMap;
import java.util.Map;

import fr.inria.plasmalab.algorithm.data.SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.shared.ResultIdentifier;

public class Sequential_SMCResult implements SMCResult {
	private double pr;
	private int totalCount;
	private boolean satisfied;
	
	private AbstractRequirement origin;
	
	private HashMap<InterfaceIdentifier, Object> values;
	private InterfaceIdentifier[] headers;

	private static final ResultIdentifier satisfiedId = new ResultIdentifier("Satisfied", true);
	private static final ResultIdentifier simId = new ResultIdentifier("# Simulations", false);
	private static final ResultIdentifier posSimId = new ResultIdentifier("# Positive Simulation", false);
	private static final ResultIdentifier prId = new ResultIdentifier("Pr", false);
	
	public Sequential_SMCResult(int totalCount, int trueCount, boolean satisfied, Map<InterfaceIdentifier, Double> initState, InterfaceIdentifier[] optimVars,
			AbstractRequirement origin, AbstractModel model){
		
		this.totalCount = totalCount;
		this.satisfied = satisfied;
		if(totalCount>0)
			pr = trueCount/(double)totalCount;
		else
			pr=0;
		
		this.headers = new InterfaceIdentifier[4+optimVars.length];		
		this.values = new HashMap<InterfaceIdentifier, Object>(4+optimVars.length);
		
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
		
		this.values.put(satisfiedId, satisfied);
		headers[i] = satisfiedId;
		this.values.put(simId, totalCount);
		headers[i+1] = simId;
		this.values.put(posSimId, trueCount);
		headers[i+2] = posSimId;
		this.values.put(prId, pr);
		headers[i+3] = prId;

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
		throw new PlasmaExperimentException("header " + header + " not found in Sequential_SMCResult.");
	}

	@Override
	public String getCategory() {
		return origin.getName();
	}

	@Override
	public double getPr() {
		return satisfied? 1. : 0.;
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