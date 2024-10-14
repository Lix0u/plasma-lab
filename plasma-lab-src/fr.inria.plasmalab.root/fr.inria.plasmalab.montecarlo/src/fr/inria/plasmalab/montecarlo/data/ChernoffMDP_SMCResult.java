/**
 * This file is part of fr.inria.plasmalab.montecarlo.
 *
 * fr.inria.plasmalab.montecarlo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.montecarlo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.montecarlo.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.montecarlo.data;

import java.util.HashMap;
import java.util.Map;

import fr.inria.plasmalab.algorithm.data.SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.shared.ResultIdentifier;

public class ChernoffMDP_SMCResult implements SMCResult {
	private double pr;
	private int totalCount;
	
	private AbstractRequirement origin;
	
	private HashMap<InterfaceIdentifier, Object> values;
	private InterfaceIdentifier[] headers;
	
	private static final ResultIdentifier 
				simID = new ResultIdentifier("# Simulations", false),
				minID = new ResultIdentifier("Min positive", false),
				maxID = new ResultIdentifier("Max positive", false),
				zeroSchedID = new ResultIdentifier("False schedulers", false);
			
	
	public ChernoffMDP_SMCResult(int totalCount, double min, double max, int zeroSched,
			Map<InterfaceIdentifier, Double> initState, InterfaceIdentifier[] optimVars,
			AbstractRequirement origin, AbstractModel model){
		
		this.totalCount = totalCount;
		pr=min;
		
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
		
		this.values.put(simID, totalCount);
		headers[i]=simID;
		//this.values.put(posSimID, trueCount);
		this.values.put(minID, min);
		headers[i+1]=minID;
		this.values.put(maxID, max);
		headers[i+2]=maxID;
		this.values.put(zeroSchedID, zeroSched);
		headers[i+3]=zeroSchedID;
	
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
		throw new PlasmaExperimentException("header " + header + " not found in ChernoffMDP_SMCResult.");
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
			throw new PlasmaExperimentException("header ID " + id.getName() + " not found in ChernoffMDP_SMCResult.");
	}

}
