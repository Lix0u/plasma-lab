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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.distributed.algorithm.common.Result;

/**
 * This class handles Task scheduling.
 * 
 * This class handles Task scheduling. To avoid biais, each 
 * task must be taken into account in the order they were created
 * rather than in the order they are completed. In order to do this,
 * TaskScheduler maintain a task queue (schedule) and deq
 * @author kevin.corre@inria.fr
 *
 */
public class TaskScheduler {
	final static Logger logger = LoggerFactory.getLogger(TaskScheduler.class);
	
	//taskSchedule		Queue of task in order
	protected Queue<String> taskSchedule;
	//taskResultMap		Map of finished task -> result
	protected Map<String, Result> taskResultMap;

	public TaskScheduler(){
		taskSchedule = new ConcurrentLinkedQueue<String>();
		taskResultMap = new ConcurrentHashMap<String, Result>();
	}
	
	/**
	 * Insert a taskUID at the tail of the queue
	 * @param 		the taskUID
	 */
	public void scheduleTask(String taskUID){
		taskSchedule.add(taskUID);
		logger.debug(taskUID+" added to schedule.");
	}
	
	/**
	 * Insert a result into the taskResultMap
	 * @param 		the result
	 */
	public void insertResult(Result result){
		String taskUID = result.getUid();
		taskResultMap.put(taskUID, result);
		logger.debug(result.getUid()+" result commited.");
	}
	
	/**
	 * Check if the result of a task has already been received.
	 * @return 		yes if the result has been received
	 */
	public boolean taskCompleted(String taskUID){
		Result result = taskResultMap.get(taskUID);
		return result != null;
	}
	
	/**
	 * Get the result corresponding to the head of the 
	 * schedule queue.
	 * 
	 * This method returns the results corresponding to
	 * the head of the schedule queue or null if any.
	 * @return		the result
	 */
	public Result getNextResult(){
		String headUID = taskSchedule.peek();
		Result headResult = null;
		if(headUID != null)
			if(taskResultMap.containsKey(headUID)){
				headResult = taskResultMap.get(headUID);
				taskResultMap.remove(headUID);
				taskSchedule.poll();
				logger.debug(headUID+" task and result polled from schedule.");
			}
		return headResult;
	}
}
