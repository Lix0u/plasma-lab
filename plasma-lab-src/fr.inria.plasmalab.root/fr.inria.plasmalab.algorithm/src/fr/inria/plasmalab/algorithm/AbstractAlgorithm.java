/**
 * This file is part of fr.inria.plasmalab.algorithm.
 *
 * fr.inria.plasmalab.algorithm is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.algorithm is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.algorithm.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.algorithm;

import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.listener.ExperimentationListener;

/** @author ltraonou@inria.fr
*        
*   This class implements generic methods for non-distributed SMC algorithms.
*/
public abstract class AbstractAlgorithm implements InterfaceAlgorithmScheduler {

	/** The id of the factory used to instantiate this algorithm */
	protected String nodeURI;

	/** The listener of the algorithm */
	protected ExperimentationListener listener;
	protected boolean stopOrderReceived;
	protected boolean errorOccured;
		
	// model, requirements, optimization
	protected AbstractModel model;
	protected List<AbstractRequirement> requirements;
	protected OptimVariables optimVariables;
	protected List<Map<InterfaceIdentifier, Double>> optimInitialStates;
	protected int optimNState;
	protected boolean optimActivated;
	
	@Override
	public String getNodeURI() {
		return nodeURI;
	}

	@Override
	public void setExpListener(ExperimentationListener listener) {
		this.listener = listener;
	}

	@Override
	public void setOptimizationVariables(OptimVariables optimVariables) {
		this.optimVariables = optimVariables;
	}
	
	public OptimVariables getOptimVariables() {
		return this.optimVariables;
	}

	public InterfaceIdentifier[] getOptimId() {
		return optimVariables.generateOptimIdentifiers(model).toArray(new InterfaceIdentifier[0]);
	}
	
	@Override
	public void setServices(String port, int nbThread, int batch, int frequency,
			List<AbstractDataFactory> adfList,
			List<InterfaceAlgorithmFactory> aafList) {
		// do nothing
	}
	
	@Override
	public void abortScheduling() {
		stopOrderReceived = true;
	}
	
	/** Initializes a run of the algorithm. 
	 * 
 	 *  This method should be called at the beginning of the run() method.
 	 */
	protected void initializeAlgorithm() {
		// set optim initial states
		optimInitialStates = optimVariables.getOptimVariablesRange(model);
		optimActivated = !(optimVariables.getVars() == null || optimVariables.getVars().size()==0);
		optimNState = optimActivated? optimInitialStates.size(): 1;
		
		stopOrderReceived = false;
		errorOccured=false;
	}
	
}
