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
package fr.inria.plasmalab.smartsampling.data;

import java.util.HashMap;
import java.util.Map;

import fr.inria.plasmalab.algorithm.data.SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.shared.ResultIdentifier;

public class IteratedMDP_SMCResult implements SMCResult {
	private double pr;
	private int totalCount;
	
	private AbstractRequirement origin;
	
	private InterfaceIdentifier[] headers;
	private HashMap<InterfaceIdentifier, Object> values;
	
	public IteratedMDP_SMCResult(int totalCount, boolean indicator, double value, int iterations, boolean minORmax, String reward,
			Map<InterfaceIdentifier, Double> initState, InterfaceIdentifier[] optimVars,
			AbstractRequirement origin, AbstractModel model){
		
		this.totalCount = totalCount;
		if (reward.isEmpty())
			this.pr = value;
		else
			this.pr=indicator ? 1.0 : 0.0;
		
		ResultIdentifier simID,minmaxID,iterationsID;
		simID = new ResultIdentifier("# Simulations", false);
		if (minORmax)
			if (reward.isEmpty())
				minmaxID = new ResultIdentifier("Max probability", false);
			else
				minmaxID = new ResultIdentifier("Max of " + reward, false);
		else
			if (reward.isEmpty())
				minmaxID = new ResultIdentifier("Min probability", false);
			else
				minmaxID = new ResultIdentifier("Min of " + reward, false);
		iterationsID = new ResultIdentifier("Iterations", false);
		
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
		
		this.values.put(minmaxID, value);
		headers[i] = minmaxID;
		this.values.put(iterationsID, iterations);
		headers[i+1] = iterationsID;
		this.values.put(simID, totalCount);
		headers[i+2] = simID;
		
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
		for(InterfaceIdentifier id:headers){
			if(id.getName().equals(header))
				return values.get(id);
		}
		throw new PlasmaExperimentException("header " + header + " not found in IteratedMDP_SMCResult.");
	}

	@Override
	public String getCategory() {
		return origin.getName();
	}

	@Override
	public double getPr() {
		return pr;
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
			throw new PlasmaExperimentException("header ID " + id.getName() + " not found in IteratedMDP_SMCResult.");
	}

}
