/**
 * This file is part of fr.inria.plasmalab.nested.
 *
 * fr.inria.plasmalab.nested is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.nested is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.nested.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.nested.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.AbstractAlgorithm;
import fr.inria.plasmalab.nested.NestedRequirement;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class NestedAlgorithm extends AbstractAlgorithm {
	protected final static Logger logger = LoggerFactory.getLogger(AbstractAlgorithm.class);

	private double alpha, beta, delta;
	
	public NestedAlgorithm(AbstractModel model, List<AbstractRequirement> requirements,
			double alpha, double beta, double delta, String nodeUri) {
		this.model = model;
		this.requirements = requirements;
		this.alpha = alpha;
		this.beta = beta;
		this.delta = delta;
		this.nodeURI = nodeUri;
	}
	
	@Override
	public void run() {
		initializeAlgorithm();
		List<ResultInterface> results = new ArrayList<ResultInterface>();

		listener.notifyAlgorithmStarted(nodeURI);
		listener.publishResults(nodeURI, results);
		logger.info("Starting Nested algorithm.");
		
		try {
			for(int st=0; st<optimNState; st++){
				if(optimActivated)
					model.setValueOf(optimInitialStates.get(st));
				InterfaceState path = model.newPath();
				for(int j=0; j<requirements.size(); j++) {
					NestedRequirement req;
					if (requirements.get(j) instanceof NestedRequirement)
						req = (NestedRequirement) requirements.get(j);
					else
						throw new PlasmaExperimentException("Wrong requirement type: nested algorithm only works on nested requirements.");
					req.setSpecificParameters(alpha, beta, delta);
					double value = requirements.get(j).check(path);
					Nested_SMCResult result = new Nested_SMCResult( value==1.0 ? true : false,
							optimInitialStates.get(st), getOptimId(), req, model);
					results.add(result);
					listener.publishResults(nodeURI, results);
					if(stopOrderReceived)
						throw new PlasmaStopOrder();
				}
			}
		}
		catch (PlasmaStopOrder e) { }
		catch (PlasmaExperimentException e) {
			//logger.error(e.getMessage(),e);
			listener.notifyAlgorithmError(nodeURI, e.toString());
			errorOccured = true;
		}
		if(!errorOccured){
			// Notify completed
			if(stopOrderReceived)
				listener.notifyAlgorithmStopped(nodeURI);
			else
				listener.notifyAlgorithmCompleted(nodeURI);
		}
	}

}
