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
package fr.inria.plasmalab.distributed.algorithm.server;

import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.heartbeat.common.HeartbeatListener;
import fr.inria.plasmalab.distributed.heartbeat.server.HeartbeatConsumer;

/**
 * This class handles node, task and order managment. 
 * 
 * This class handles node registration and removal on timeout. Task registration
 * and reaffectation in case of timeout and link order to tasks.
 * @author kevin.corre@inria.fr
 *
 */
public class NodeTaskManager implements HeartbeatListener{
	final static Logger logger = LoggerFactory.getLogger(NodeTaskManager.class);
	
	//NodeTaskMap : nodeUID -> taskUID
	private Map<String, String> nodeTaskMap;
	//TaskMap : taskUID -> order
	private Map<String, Order> taskOrderMap;
	//Unassigned tasks
	private Queue<String> unassignedTasks;
	
	//TIMEOUT DETECTION
	private HeartbeatConsumer heartBeatConsumer;

	public NodeTaskManager(){
		//Concurrent map
		nodeTaskMap = new ConcurrentHashMap<String, String>();
		taskOrderMap = new ConcurrentHashMap<String, Order>();
		unassignedTasks = new ConcurrentLinkedQueue<String>();
		
		//TIMEOUT DETECTION
		//Start heartbeat consumer
		heartBeatConsumer = new HeartbeatConsumer(this);
		new Thread(heartBeatConsumer).start();
	}
	
	/**
	 * Register a new order and returns its affected UID.
	 * @param 		the order to register
	 * @return 		the task UID
	 */
	public String registerOrder(Order order){
		String uid = UUID.randomUUID().toString();
		order.setUid(uid);
		taskOrderMap.put(uid, order);
		logger.debug(uid+" task registered.");
		return uid;
	}
	/**
	 * Register an affectation from a task to a node.
	 * @param 		the nodeUID
	 * @param 		the taskUID
	 */
	public void registerAffectation(String nodeUID, String taskUID){
		nodeTaskMap.put(nodeUID, taskUID);
		logger.debug(nodeUID+" affected to task -> "+taskUID);
	}
	/**
	 * Remove references to a completed task.
	 * @param 		the taskUID
	 */
	public void removeTask(String taskUID){
		//nodeTaskMap.put(nodeUID, null);
		taskOrderMap.remove(taskUID);
		logger.debug(taskUID+" task completed.");
	}
	/**
	 * Returns the oldest task in the unassigned task queue or null if any.
	 * This methods call the poll method (retrieve and remove) on the unassigned
	 * tasks queue.
	 * @return		the oldest unassigned task
	 */
	public String getUnassignedTask(){
		return unassignedTasks.poll();
	}
	
	/**
	 * Returns the Order corresponding to the task UID.
	 * @param 		the taskUID
	 * @return		the Order
	 */
	public Order gerOrder(String taskUID){
		return taskOrderMap.get(taskUID);
	}
	
	/**
	 * A node has triggered a timeout.
	 * Its task will be put in the unassigned task queue and this nodeUID
	 * will be removed from the nodeTaskMap.
	 * @param 		the nodeUID
	 */
	@Override
	public void timeoutTriggered(String nodeUID){
		String taskUID = nodeTaskMap.get(nodeUID);
		if(taskUID != "" && taskUID != null)
			unassignedTasks.add(taskUID);
		// remove node
		removeNode(nodeUID);				
	}
	
	/**
	 * Register a node.
	 * @param nodeUID
	 */
	public void registerNode(String nodeUID){
		if(nodeTaskMap.containsKey(nodeUID)){
			logger.warn(nodeUID+" is already registered.");
		}
		else{
			nodeTaskMap.put(nodeUID, "");
			//Start listening for timeout
			heartBeatConsumer.registerNode(nodeUID);
		}
	}
	
	/**
	 * A node has disconnected or has been ordered to disconnect.
	 * @param nodeUID	the nodeUID
	 */
	public void removeNode(String nodeUID){
		//Stop listening for timeout
		heartBeatConsumer.removeNode(nodeUID);
		nodeTaskMap.remove(nodeUID);
		logger.debug(nodeUID+" node removed");
	}
	
	/**
	 * Return true if the specified node has been registered
	 * @param 		nodeUID
	 * @return		true if the node has been registered, else false
	 */
	public boolean nodeIsRegistered(String nodeUID){
		return nodeTaskMap.containsKey(nodeUID);
	}
	
	public void stopMonitoring(){
		heartBeatConsumer.stopListening();
		//Clear
		nodeTaskMap.clear();
		taskOrderMap.clear();
	}
}
