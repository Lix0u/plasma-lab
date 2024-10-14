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

import fr.inria.plasmalab.algorithm.data.SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.shared.ResultIdentifier;

/** Print only the probability, not the conditional probabilities of each level, and the average number of levels */
public class ISDistributed_SMCResult implements SMCResult {
	
	private static final ResultIdentifier 
				simID = new ResultIdentifier("~ Levels", false),
				resID = new ResultIdentifier("Result", false);
	
	private AbstractModel model;
	private AbstractRequirement requirement;
	
	private double levels;
	private double pr;
	/** Results identifiers, in the order they should be display */
	private InterfaceIdentifier[] headers;
	private HashMap<InterfaceIdentifier, Object> values;

	public ISDistributed_SMCResult(AbstractModel model, AbstractRequirement req, double levels, double proba){
		this.model = model;
		this.requirement = req;
		this.levels = levels;
		this.pr = proba;
				
		this.headers = new InterfaceIdentifier[2];		
		this.values = new HashMap<InterfaceIdentifier, Object>(2);
		headers[0] = resID;
		this.values.put(resID, pr);
		headers[1] = simID;
		this.values.put(simID, levels);
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
		throw new PlasmaExperimentException("header " + header + " not found in ISDistributed_SMCResult.");
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
			throw new PlasmaExperimentException("header ID " + id.getName() + " not found in ISDistributed_SMCResult.");
	}
	
	public String toString() {
		return Double.toString(pr) + ", " + Double.toString(levels);
	}

}
