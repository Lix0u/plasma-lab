/**
 * This file is part of fr.inria.plasmalab.importancesplitting.
 *
 * fr.inria.plasmalab.importancesplitting is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.importancesplitting is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.importancesplitting.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.importancesplitting.data;

import java.util.HashMap;
import java.util.List;

import fr.inria.plasmalab.algorithm.data.SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.shared.ResultIdentifier;

public class IS_SMCResult implements SMCResult {
	
	private static final ResultIdentifier 
				simID = new ResultIdentifier("# Level", false),
				resID = new ResultIdentifier("Result", false);
	
	private AbstractModel model;
	private AbstractRequirement requirement;
	
	private List<Double> probabilities;
	private double pr;
	/** Results identifiers, in the order they should be display */
	private InterfaceIdentifier[] headers;
	private HashMap<InterfaceIdentifier, Object> values;

	public IS_SMCResult(AbstractModel model, AbstractRequirement req, List<Double> conditionalProbabilities){
		this.model = model;
		this.requirement = req;
		this.probabilities = conditionalProbabilities;
		// compute the probability
		this.pr = 1;
		for (Double levelProba : conditionalProbabilities) {
			pr *= levelProba;
		}
		this.headers = new InterfaceIdentifier[conditionalProbabilities.size()+2];		
		this.values = new HashMap<InterfaceIdentifier, Object>(conditionalProbabilities.size()+2);
		headers[0] = resID;
		this.values.put(resID, pr);
		headers[1] = simID;
		this.values.put(simID, conditionalProbabilities.size());
		for (int i=0; i<conditionalProbabilities.size(); i++) {
			InterfaceIdentifier id = new ResultIdentifier("level " + i, false);
			this.values.put(id, conditionalProbabilities.get(i));
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
		for(InterfaceIdentifier id: headers){
			if(id.getName().equals(header))
				return values.get(id);
		}
		throw new PlasmaExperimentException("header " + header + " not found in IS_SMCResult.");
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
			throw new PlasmaExperimentException("header ID " + id.getName() + " not found in IS_SMCResult.");
	}
	
	public String toString() {
		String ret = Double.toString(pr);
		for (Double levelProba : probabilities) {
			ret += levelProba.toString() + ", ";
		}
		return ret;
	}

}
