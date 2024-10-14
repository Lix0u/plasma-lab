/**
 * This file is part of fr.inria.plasmalab.cusum.
 *
 * fr.inria.plasmalab.cusum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.cusum is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.cusum.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.cusum.data;

import java.util.HashMap;
import java.util.Map;

import fr.inria.plasmalab.algorithm.data.SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.shared.ResultIdentifier;

public class CuSumResult implements SMCResult {

	private boolean detection;	
	private AbstractRequirement origin;
	private Map<InterfaceIdentifier, Object> values;
	
	private static final ResultIdentifier changeId = new ResultIdentifier("Time of change", false);
	private static final ResultIdentifier sampleChangeId = new ResultIdentifier("Sample of change", false);
	private static final ResultIdentifier detectionId = new ResultIdentifier("Change detected", true);
	private static final ResultIdentifier minProbaId = new ResultIdentifier("Minimum probability", true);
	private static final ResultIdentifier maxProbaId = new ResultIdentifier("Maximum probability", true);

	public CuSumResult(double changeTime, int changeSample, boolean detection, AbstractRequirement origin) {
		this.origin = origin;
		this.detection = detection;

		this.values = new HashMap<InterfaceIdentifier, Object>();
		this.values.put(changeId, changeTime);
		this.values.put(sampleChangeId, changeSample);
		this.values.put(detectionId, detection);
	}
	
	public CuSumResult(double minProba, double maxProba, AbstractRequirement origin) {		
		this.origin = origin;

		this.values = new HashMap<InterfaceIdentifier, Object>();
		this.values.put(minProbaId, minProba);
		this.values.put(maxProbaId, maxProba);
	}

	@Override
	public AbstractRequirement getOriginRequirement() {
		return origin;
	}

	@Override
	public InterfaceIdentifier[] getHeaders() {
		return values.keySet().toArray(new InterfaceIdentifier[values.size()]);
	}

	@Override
	public Object getValueOf(String header) throws PlasmaExperimentException {
		for (InterfaceIdentifier id: values.keySet())
			if(id.getName().equals(header))
				return values.get(id);
		throw new PlasmaExperimentException("header " + header + " not found in CuSumResult.");
	}

	@Override
	public String getCategory() {
		return origin.getName();
	}

	@Override
	public double getPr() {
		return detection?1:0;
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
			throw new PlasmaExperimentException("header ID " + id.getName() + " not found in CuSumResult.");
	}

}