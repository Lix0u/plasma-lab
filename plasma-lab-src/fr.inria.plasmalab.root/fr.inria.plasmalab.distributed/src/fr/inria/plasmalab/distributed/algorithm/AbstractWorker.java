/**
 * This file is part of fr.inria.plasmalab.distributed.
 *
 * fr.inria.plasmalab.distributed is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.distributed is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.distributed.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.distributed.algorithm;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmWorker;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.restlet.common.InterfaceServerResource;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;

/**
 * An abstract implementation of a worker that process the basic orders (WAIT, TERMINATE).
 * 
 * @author ltraonou@inria.fr
 */
public abstract class AbstractWorker implements InterfaceAlgorithmWorker {
	protected final static Logger logger = LoggerFactory.getLogger(InterfaceAlgorithmWorker.class);

	protected AbstractModel model;
	protected List<AbstractRequirement> requirements;
	protected List<Map<InterfaceIdentifier, Double>> optimInitialStates;
	protected int optimNState;
	protected boolean optimActivated;
	protected String uid;
		
	/** This method returns the server resource used by the worker */
	protected abstract InterfaceServerResource getServerResource();

	/** This method process a BATCH order */
	protected abstract void processBatchOrder(Order order) throws PlasmaExperimentException;

	/** This method process a TERMINATE order */
	protected void processTerminateOrder(Order order) { 
		logger.debug(uid+" Terminate.");
	} 

	/** This method process a WAIT order */
	protected void processWaitOrder(Order order) {
		int waitTime = (Integer) order.getParam("wait");
		logger.debug(uid+" will sleep");
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			logger.error(uid + " got an exception while waiting: ", e);
		}
	}

	/** 
	 * This method start the worker and process the orders
	 */
	public void start() {
		OptimVariables optimVariables = getServerResource().getOptimVariables();
		optimInitialStates = optimVariables.getOptimVariablesRange(model);
		optimActivated = !(optimVariables.getVars() == null || optimVariables.getVars().size()==0);
		optimNState = optimActivated ? optimInitialStates.size(): 1;
		
		try {
			boolean stop = false;
			while(!stop) {
				logger.debug(uid+" Send ready signal");
				Order order = getServerResource().ready(uid);			
				logger.debug(uid+" Signal response received");
				switch(order.getType()){
				case BATCH:
					processBatchOrder(order);
					break;
				case TERMINATE:
					stop = true;
					processTerminateOrder(order);
					break;
				case WAIT:
					processWaitOrder(order);
					break;
				default:
					//Terminate with error
					stop = true;
					logger.error(uid+" Unable to read order, terminate.");
					break;
				}
			}
		}
		catch (PlasmaExperimentException e) {
			getServerResource().postErrorMessage(e.getMessage());
			logger.debug(uid+" Terminating worker.",e);
			return;
		}
	}

}
