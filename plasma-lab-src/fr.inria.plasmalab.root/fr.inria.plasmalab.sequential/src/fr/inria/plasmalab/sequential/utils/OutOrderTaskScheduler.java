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
package fr.inria.plasmalab.sequential.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.distributed.algorithm.common.Result;
import fr.inria.plasmalab.distributed.algorithm.server.TaskScheduler;
import fr.inria.plasmalab.montecarlo.data.MontecarloResult;

/**
 * This class handle Task scheduling for the Out of Order Sequential scheduler.
 * 
 * This class handle Task scheduling for the Out of Order distributed Sequential scheduler. Following
 * the optimization proposition found in Younes thesis, this task scheduler compute if one of the
 * hypothesis H0 or H1 can be decided before having computed a complete sequence of results without
 * introducting bias.
 * 
 * @author kevin.corre@inria.fr
 *
 */
public class OutOrderTaskScheduler extends TaskScheduler {
	final static Logger logger = LoggerFactory.getLogger(OutOrderTaskScheduler.class);
	
	private Map<String, OutOrderHypothesis> taskHypothesisMap;
	
	public OutOrderTaskScheduler(double alpha, double beta, double delta, double proba){
		super();
		taskHypothesisMap = new HashMap<String, OutOrderHypothesis>();
		
		//Clear hypothesis structure
		OutOrderHypothesis.clear();

		final double p0 = (proba + delta),
				 p1 = (proba - delta); 

		double H0 = Math.log(beta/(1-alpha))/
					Math.log((p1*(1-p0))/(p0*(1-p1)));
		
		double H1 = Math.log((1-beta)/alpha)/
					Math.log((p1*(1-p0))/(p0*(1-p1)));
		
		double STR = Math.log((1-p0)/(1-p1))/
					Math.log((p1*(1-p0))/(p0*(1-p1)));

		OutOrderHypothesis.setSeqParam(H0, H1, STR);
	}
	
	/**
	 * Insert a taskUID at the tail of the queue
	 * @param 		the taskUID
	 */
	public void scheduleTask(String taskUID, int nsim, int ntotal){
		taskSchedule.add(taskUID);
		OutOrderHypothesis hypothesis = new OutOrderHypothesis(nsim, ntotal);
		taskHypothesisMap.put(taskUID, hypothesis);
		logger.debug(taskUID+" added to schedule.");
	}
	
	/**
	 * Insert a result into the taskResultMap
	 * @param 		the result
	 */
	public void insertResult(Result result){
		String taskUID = result.getUid();
		taskResultMap.put(taskUID, result);
		taskHypothesisMap.get(taskUID).commitResult(((MontecarloResult)result).getPositiveMatrix()[0][0]);
		logger.debug(result.getUid()+" result commited.");
	}
	
	/**
	 * Remove the hypothesis
	 */
	public Result getNextResult(){
		Result result = super.getNextResult();
		//TODO 
		if(result != null){
			taskHypothesisMap.remove(result.getUid());
			OutOrderHypothesis.poll();
		}
		return result;
	}
}
