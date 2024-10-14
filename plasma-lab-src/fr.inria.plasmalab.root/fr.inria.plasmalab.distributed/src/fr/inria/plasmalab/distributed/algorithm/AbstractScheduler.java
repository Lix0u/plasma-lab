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
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.distributed.DistributedBay;
import fr.inria.plasmalab.distributed.Service;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.algorithm.common.Result;
import fr.inria.plasmalab.distributed.algorithm.server.NodeTaskManager;
import fr.inria.plasmalab.distributed.algorithm.server.TaskScheduler;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.listener.ExperimentationListener;

/** @author ltraonou@inria.fr
*     
*   This class implements generic methods for distributed scheduler.

*   Notably, It implements a scheduler that rearrange results
*   dynamically in order to ensure unbiased results.
*   This problem and it's solution is described in Younes thesis:
*   Statistical Probabilistic Model Checking.
*   
*   This class factorizes the initialization, the management of orders...
*/
public abstract class AbstractScheduler implements InterfaceAlgorithmScheduler {
	/** The logger used by the schedulers */
	protected final static Logger logger = LoggerFactory.getLogger(InterfaceAlgorithmScheduler.class);

	/** The id of the factory used to instantiate this algorithm */
	protected String nodeURI;

	/** The listener of the scheduler */
	protected ExperimentationListener listener;
	
	// model, requirements, optimization
	protected AbstractModel model;
	protected List<AbstractRequirement> requirements;
	protected OptimVariables optimVariables;
	protected List<Map<InterfaceIdentifier, Double>> optimInitialStates;
	protected int optimNState;
	protected boolean optimActivated;
	
	// distributed parameters:
	protected String port;
	protected int nbThread;
	protected int batch;
	protected int frequency;
	protected List<AbstractDataFactory> adfList;
	protected List<InterfaceAlgorithmFactory> aafList;
	
	//protected final static int LINEARINCR = 0;
	protected NodeTaskManager nodeTaskManager;
	protected TaskScheduler taskScheduler;
	protected Lock batchCreationLock;
	protected boolean stopOrderReceived;
	protected boolean errorOccured;
	
	
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
		this.port = port;
		this.nbThread = nbThread;
		this.batch = batch;
		this.frequency = frequency;
		this.adfList = adfList;
		this.aafList = aafList;	
	}
	
	@Override
	public void abortScheduling() {
		stopOrderReceived = true;
	}
	
	/**
	 * Post error message to the experimentation manager and abort experimentation.
	 */
	public void postErrorMessage(String errorMessage){
		errorOccured = true;
		abortScheduling();
		listener.notifyAlgorithmError(nodeURI, errorMessage);
	}
	
	/**
	 * Add a result to the TaskScheduler
	 */
	public synchronized void putResult(Result result){
		taskScheduler.insertResult(result);
	}

	/**
	 * Register a new node on the NodeTaskManager
	 * 
	 * @return the uid of the node or null if the node cannot be registered (because the scheduler has terminated).
	 */
	public String registerNewNode() {
		//if (stopOrderReceived)
		//	return null;
		String uid = UUID.randomUUID().toString();
		nodeTaskManager.registerNode(uid);
		listener.notifyNewServiceConnected(nodeURI);
		return uid;
	}

	/**
	 * Send an order to node requesting one.
	 * This can either be a BATCH order, a WAIT order or a TERMINATE order.
	 */
	public Order schedule(String nodeUID) {
		Order order;
		if(nodeTaskManager.nodeIsRegistered(nodeUID)){
			try{
				batchCreationLock.lock();
				order = reassignTaskTo(nodeUID); //If unassigned task
				if(order == null){ 				 //Else generate a new order
					order = generateOrder();
					switch(order.getType()) {
					case BATCH:
						logger.info("Sending batch order to "+nodeUID);
						//Register order
						nodeTaskManager.registerOrder(order);
						String taskUID = order.getUid();
						//Register affectation
						nodeTaskManager.registerAffectation(nodeUID, taskUID);
						//Schedule task
						taskScheduler.scheduleTask(taskUID);
						break;
					case TERMINATE:
						logger.info("Sending terminate order to "+nodeUID);
						//Node will be disconnected
						nodeTaskManager.removeNode(nodeUID);
						break;
					default:	// WAIT or RESEND
						//logger.debug("Sending " + order.getType() + " order to "+nodeUID);
						break;
					}
				}
			}finally{
				batchCreationLock.unlock();
				// logger.debug("unlock batch creation lock");
			}
		}else{
			//If the node never registered with this scheduler
			order = Order.createTerminateOrder("0");
			logger.info("Sending terminate order to unknown "+nodeUID);
			listener.notifyServiceDisconnected(nodeURI);
			//We don't register the affectation or the order if its sent to an unknown node
		}
		return order;
	}
	
	/** Generate a new order for a node 
     * 	This can either be a BATCH order, a WAIT order or a TERMINATE order.
     */
	protected abstract Order generateOrder();
	
	/** Initializes a run of the algorithm
	 * 
	 *  This method should be called at the beginning of the run() method.
	 */
	protected void initializeServer() {
		// set listening bay
		DistributedBay dbBay = DistributedBay.getDistributedBay(aafList);
		dbBay.setExperiment(model, requirements, this);
		dbBay.startServer(Integer.parseInt(port));
					
		// set optim initial states
		optimInitialStates = optimVariables.getOptimVariablesRange(model);
		optimActivated = !(optimVariables.getVars() == null || optimVariables.getVars().size()==0);
		optimNState = optimActivated? optimInitialStates.size(): 1;
		
		// start services if any 
		for(int i=0; i<nbThread; i++)
			Service.startService("localhost", port, "/", adfList, aafList);
		
		//NodeTask manager
		nodeTaskManager = new NodeTaskManager();
		//Task scheduler
		taskScheduler = new TaskScheduler();
		//Lock
		batchCreationLock = new ReentrantLock();
		
		stopOrderReceived = false;
		errorOccured=false;
	}
	
	@Deprecated
	protected void terminateServer() {
//		stopOrderReceived = true;
		// wait for the workers to disconnect
//		try {
//			Thread.sleep(SLEEP_FOR);
//		} catch (InterruptedException e) {
//			System.err.println(e.getMessage());
//		}
//		// wait for the worker to terminate, then stop the server
//		DistributedBay dbBay = DistributedBay.getDistributedBay(aafList);
//		dbBay.stopServer();
	}
	
	/** This method wait for a result and remove it from the nodeTaskManager
	 *  
	 *  If a stop order is received the method returns null.
	 *  
	 *  @return a result produced by a worker or null if a stop order was received.
	 * @throws PlasmaExperimentException 
	 * 
	 */
	protected Result getNextResult() throws PlasmaExperimentException {
		Result result = null;
		while (result == null) {
			if (stopOrderReceived)
				throw new PlasmaStopOrder();
			result = taskScheduler.getNextResult();
			if(result != null) 
				nodeTaskManager.removeTask(result.getUid());
			else {
				try {
					Thread.sleep(frequency);
				} catch (InterruptedException e) {
					throw new PlasmaExperimentException(e);
				}
			}	
		}
		return result;
	}
	
	/**
	 * This method reassign an unassigned task to a node.
	 * 
	 * This method reassign an unassigned task to a node if such order
	 * is available. If there is an unassigned task which points to 
	 * a null order or no assigned task, return null. 

	 * @param nodeUID the node UID.
	 * @return the order to assign to node.
	 */
	protected Order reassignTaskTo(String nodeUID){
		Order order = null;
		String taskUID = nodeTaskManager.getUnassignedTask();
		//If there an unassigned task
		if(taskUID != null){
			order = nodeTaskManager.gerOrder(taskUID);
			if(order != null){
				//Register affectation
				//Doesn't need to reschedule the task
				nodeTaskManager.registerAffectation(nodeUID, taskUID);
				logger.info("Reassign task " + taskUID + " to node " + nodeUID);
			}
		}
		return order;
	}
	

}
