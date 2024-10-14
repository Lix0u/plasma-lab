/**
 * This file is part of fr.inria.plasmalab.montecarlo.
 *
 * fr.inria.plasmalab.montecarlo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.montecarlo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.montecarlo.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.montecarlo.scheduler;

import java.util.ArrayList;
import java.util.List;

import fr.inria.plasmalab.distributed.algorithm.AbstractScheduler;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.montecarlo.data.MontecarloResult;
import fr.inria.plasmalab.montecarlo.data.Montecarlo_SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

/**
 * @author ltraonou@inria.fr
 * 
 */
public class MontecarloScheduler  extends  AbstractScheduler {
	protected int totalToDo;
	protected int remainingToDo;
	protected int totalDone;
		
	public MontecarloScheduler(AbstractModel model, List<AbstractRequirement> requirements, int totalcount, String nodeURI){		
		remainingToDo = totalcount;
		totalToDo = totalcount;
		totalDone = 0;
		
		this.requirements = requirements;
		this.model = model;
		this.nodeURI = nodeURI;
	}
	
	@Override
	public void run() {
		initializeServer();

		int[][] positiveMatrix = new int[requirements.size()][optimNState];
		double[][]results = new double[requirements.size()][optimNState];
		
		long start = System.currentTimeMillis();
		listener.notifyAlgorithmStarted(nodeURI);
		listener.publishResults(nodeURI, getResults(positiveMatrix,results));
		logger.info("Starting Monte Carlo distributed algorithm with " + totalToDo + " simulations.");

		try {
			while(totalDone < totalToDo) {
				MontecarloResult result = (MontecarloResult) getNextResult();
				totalDone += result.getTotal();
				int[][] updatePositive = result.getPositiveMatrix();
				double[][] updateResults = result.getResults();
				for (int i = 0; i < positiveMatrix.length; i++) {
					for (int j = 0; j < positiveMatrix[i].length; j++) {
						positiveMatrix[i][j] = positiveMatrix[i][j] + updatePositive[i][j];
						results[i][j] = results[i][j] + updateResults[i][j];
					}
				}
				//Notify new results
				logger.debug("received results of " + result.getTotal() + " simulations.");
				double percent = ((double)totalDone/totalToDo);
				double remaining = (System.currentTimeMillis()-start)/percent-(System.currentTimeMillis()-start);
				listener.notifyProgress((int)(percent*100));
				listener.notifyTimeRemaining((long)remaining);
				listener.publishResults(nodeURI, getResults(positiveMatrix, results));
			}
		}
		catch (PlasmaStopOrder e) { }
		catch (PlasmaExperimentException e) {
			//logger.error(e.getMessage(),e);
			listener.notifyAlgorithmError(nodeURI, e.toString());
			errorOccured = true;
		}
	
		if(!errorOccured){
			// Notify new results
			listener.publishResults(nodeURI, getResults(positiveMatrix, results));
			// Notify completed
			if(stopOrderReceived)
				listener.notifyAlgorithmStopped(nodeURI);
			else
				listener.notifyAlgorithmCompleted(nodeURI);		
		}
		
		terminateServer();
	}
	
	/**
	 * This method creates an order according to what is left to do
	 * @return an order.
	 */
	protected Order generateOrder(){
		if (stopOrderReceived || remainingToDo <= 0)
			return Order.createTerminateOrder(null);
		else {
			//Create batch order
			int currentBatch = Math.min(remainingToDo, batch);
			remainingToDo -= currentBatch;
			logger.debug("create batch order of " + currentBatch + "; remaining " + remainingToDo + " to do.");
			return Order.createBatchOrder(currentBatch,null);
			//currentBatch += LINEARINCR;
		}
	}
		
	private List<ResultInterface> getResults(int[][] positiveMatrix, double[][] results) {
		List<ResultInterface> smcresults = new ArrayList<ResultInterface>();
		for(int j=0; j<optimInitialStates.size();j++){
			for(int i=0; i<requirements.size();i++){
				AbstractRequirement req = requirements.get(i);
				Montecarlo_SMCResult result = new Montecarlo_SMCResult(totalDone, positiveMatrix[i][j], results[i][j],
						optimInitialStates.get(j), getOptimId(), req, model);
				smcresults.add(result);
			}
		}
		return smcresults;
	}



}
