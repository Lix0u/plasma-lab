/**
 * This file is part of fr.inria.plasmalab.importancesampling.
 *
 * fr.inria.plasmalab.importancesampling is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.importancesampling is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.importancesampling.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.importancesampling.data;

import java.util.HashMap;
import java.util.List;

import fr.inria.plasmalab.algorithm.data.SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.shared.ResultIdentifier;

public class CE_SMCResult implements SMCResult {
	
	private static final ResultIdentifier
		resID = new ResultIdentifier("Result", false),
		iterID = new ResultIdentifier("# Iterations", false);

	private AbstractModel model;
	private AbstractRequirement requirement;

	private double pr;
	private double[] param_values;

	/** Results identifiers, in the order they should be display */
	private InterfaceIdentifier[] headers;
	private HashMap<InterfaceIdentifier, Object> values;
	
	public CE_SMCResult(AbstractModel model, AbstractRequirement req, 
			double pr, double[] param_values, List<InterfaceIdentifier> param_name, int iterations){
		this.model = model;
		this.requirement = req;
		this.pr = pr;
		this.param_values = param_values;
		
		this.headers = new InterfaceIdentifier[param_values.length+2];		
		this.values = new HashMap<InterfaceIdentifier, Object>(param_values.length+2);
		headers[0] = resID;
		this.values.put(resID, pr);
		headers[1] = iterID;
		this.values.put(iterID, iterations);
		for (int i=0; i<param_values.length; i++) {
			InterfaceIdentifier id = new ResultIdentifier(param_name.get(i).getName(), false);
			this.values.put(id, param_values[i]);
			headers[i+2] = id;
		}
	}

	@Override
	public AbstractRequirement getOriginRequirement() {
		return requirement;
	}

	@Override
	public InterfaceIdentifier[] getHeaders() {
		return headers;
	}

	@Override
	public Object getValueOf(String header) throws PlasmaExperimentException {
		for(InterfaceIdentifier id : headers){
			if(id.getName().equals(header))
				return values.get(id);
		}
		throw new PlasmaExperimentException("header " + header + " not found in CE_SMCResult.");
	}

	@Override
	public String getCategory() {
		return model.getName();
	}

	@Override
	public double getPr() {
		return pr;
	}

	@Override
	public int getTotalCount() {
		return 0;
	}

	@Override
	public Object getValueOf(InterfaceIdentifier id) throws PlasmaExperimentException {
		Object o = values.get(id);
		if (o != null)
			return o;
		else
			throw new PlasmaExperimentException("header ID " + id.getName() + " not found in CE_SMCResult.");
	}

	public String toString() {
		String ret = Double.toString(pr);
		for (int i=0; i<param_values.length; i++) {
			ret += param_values[i] + ", ";
		}
		return ret;
	}

}
