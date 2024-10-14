/**
 * This file is part of fr.inria.plasmalab.sequential.
 *
 * fr.inria.plasmalab.sequential is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.sequential is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.sequential.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.sequential.scheduler;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.sequential.data.SequentialOrder;
import fr.inria.plasmalab.sequential.utils.OutOrderTaskScheduler;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;

public class OutOrderSequentialScheduler extends SequentialScheduler{
	final static Logger logger = LoggerFactory.getLogger(OutOrderSequentialScheduler.class);
	
	protected int taskSum;

	public OutOrderSequentialScheduler(AbstractModel model,
			List<AbstractRequirement> requirements, double alpha, double beta, double delta, double proba, String nodeURI) {
		super(model, requirements, alpha, beta, delta, proba, nodeURI);
		//Task scheduler
		taskScheduler = new OutOrderTaskScheduler(alpha, beta, delta, proba);
		taskSum = 0;
	}
	
	@Override
	public Order schedule(String uid) throws IOException{
		Order order;
		if(nodeTaskManager.nodeIsRegistered(uid)){
			try{
				batchCreationLock.lock();
				String taskUID = nodeTaskManager.getUnassignedTask();
				//Is there an unassigned task
				if(taskUID != null){
					order = nodeTaskManager.gerOrder(taskUID);
					//Register affectation
					nodeTaskManager.registerAffectation(uid, taskUID);
					//Doesn't need to reschedule the task
				}
				//Else generate a new order
				else{
					//If the worker should stop
					if(stopOrderReceived || !continueSimulation){
						order = Order.createTerminateOrder(null);
						logger.debug("Sending terminate order to "+uid);
						//Node will be disconnected
						nodeTaskManager.removeNode(uid);
					}
					//Else the worker will receive a new work
					else {
						order = SequentialOrder.createSequentialBatchOrder(batch,again, null);
						//Register order
						nodeTaskManager.registerOrder(order);
						taskUID = order.getUid();
						//Register affectation
						nodeTaskManager.registerAffectation(uid, taskUID);
						//Schedule task
						taskSum += batch;
						((OutOrderTaskScheduler)taskScheduler).scheduleTask(taskUID, batch, taskSum);
						//batch += LINEARINCR;
					}
				}
			}finally{
				batchCreationLock.unlock();
				logger.debug("unlock batch creation lock");
			}
		}else{
			//If the node never registered with this scheduler
			order = Order.createTerminateOrder("0");
			logger.debug("Sending terminate order to unknown "+uid);
			listener.notifyServiceDisconnected(nodeURI);
			//We don't register the affectation or the order if its sent to an unknow node
		}
		return order;
	}
}
