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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.inria.plasmalab.distributed.algorithm.AbstractScheduler;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.algorithm.common.Order.Type;
import fr.inria.plasmalab.montecarlo.data.ChernoffMDP_SMCResult;
import fr.inria.plasmalab.montecarlo.data.MontecarloResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.shared.ResultInterface;


/** This scheduler implements the basic Monte-Carlo techniques for mdp, using the Chernoff bound for multiple schedulers. 
 *  
 *  @author ltraonou@inria.fr
 * 
 */
public class ChernoffMDPScheduler  extends AbstractScheduler {  
	protected static final int SLEEP_ORDER = 100;
	
	// parameters
	/** Number of schedulers */
	protected int schedCount;
	/** Number of simulations to do per scheduler */ 
	protected int totalToDo;
	
	// global variables
	/** Seeds of the scheduler */
	protected long[] schedulerSeed;
	protected int currentReadingScheduler, currentWorkingScheduler;
	protected int remainingToDo;
		
	public ChernoffMDPScheduler(AbstractModel model, List<AbstractRequirement> requirements, int totalcount, int schedCount, String nodeURI){
		this.schedCount = schedCount;
		this.totalToDo = totalcount;
		
		this.currentWorkingScheduler = -1;
		
		this.requirements = requirements;
		this.model = model;
		this.nodeURI = nodeURI;
	}
	
	@Override
	public void run() {
		initializeServer();
		
		double[][] results = new double[requirements.size()][optimNState]; 	// temporary results for one scheduler
		double[][] min = new double[requirements.size()][optimNState];		// Min value for each requirements and each initial states. */
		double[][] max = new double[requirements.size()][optimNState];		// Max value for each requirements and each initial states. */
		int[][] zeroSched = new int[requirements.size()][optimNState]; 		// Number of zero schedulers for each requirements and each initial states
		for(int i=0; i<results.length; i++){
			Arrays.fill(results[i], 0);
			Arrays.fill(zeroSched[i], 0);
			Arrays.fill(min[i], Integer.MAX_VALUE);
			Arrays.fill(max[i], 0);
		}
		int mdp_totalDone = 0; 		// Total number of simulations performed during this experiment
		
		//Initialize all seeds
		schedulerSeed = new long[schedCount];
		Random schedulerRng = new Random();
		for(int i =0; i<schedulerSeed.length; i++){
			schedulerSeed[i] = schedulerRng.nextLong();
		}
		
		long start = System.currentTimeMillis();
		listener.notifyAlgorithmStarted(nodeURI);
		listener.publishResults(nodeURI, getResults(min, max, zeroSched, mdp_totalDone));
		logger.info("Starting Monte Carlo distributed algorithm for MDP for " + schedCount + " schedulers and " + totalToDo + " simulations.");

		remainingToDo = totalToDo;
		mdp_totalDone = 0;
		currentReadingScheduler = 0;
		currentWorkingScheduler = 0;	// start workers
		
		try {
			for(; currentReadingScheduler<schedCount; currentReadingScheduler++){				
				int totalDone = 0;
				while(totalDone < totalToDo){
					MontecarloResult result = (MontecarloResult) getNextResult();
					totalDone += result.getTotal();
					mdp_totalDone += result.getTotal();
					double[][] updateResults = result.getResults();
					for (int i = 0; i < results.length; i++) {
						for (int j = 0; j < results[i].length; j++) {
							results[i][j] = results[i][j] + updateResults[i][j];
						}
					}
					// Notify new results
					logger.debug("received results of " + result.getTotal() + " simulations.");
					double percent = ((double) mdp_totalDone / (totalToDo*schedCount));
					double remaining = (System.currentTimeMillis() - start)
							/ percent
							- (System.currentTimeMillis() - start);
					listener.notifyProgress((int) (percent * 100));
					listener.notifyTimeRemaining((long) remaining);
					listener.publishResults(nodeURI, getResults(min, max, zeroSched, mdp_totalDone));
				}
				// update results and pass to the next scheduler
				for(int i=0; i<results.length; i++){
					for(int j=0; j<results[i].length; j++){
						double pr = results[i][j]/(double)totalDone;
						if(pr>max[i][j])
							max[i][j]=pr;
						if(pr>0 && pr<min[i][j])
							min[i][j]=pr;
						else if(pr==0)
							zeroSched[i][j]++;
						logger.debug("Finished scheduler Sc_"+currentReadingScheduler+": pr= "+pr);
					}
					Arrays.fill(results[i], 0); // reinitialized results
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
			// Notify new results
			listener.publishResults(nodeURI, getResults(min, max, zeroSched, mdp_totalDone));
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
		if (stopOrderReceived || currentWorkingScheduler >= schedCount)
			return Order.createTerminateOrder(null);
		else if (currentWorkingScheduler < 0)
			return Order.createWaitOrder(SLEEP_ORDER, null);
		else {
			int currentBatch = Math.min(remainingToDo, batch);
			remainingToDo -= currentBatch;
			logger.debug("Batch order of " + currentBatch);
			Order order = createMDPBatchOrder(currentBatch, schedulerSeed[currentWorkingScheduler], null);
			
			// Start working with next scheduler
			if (remainingToDo == 0) {
				remainingToDo = totalToDo;
				currentWorkingScheduler++;
				logger.debug("Start working on Sc_" + currentWorkingScheduler);
			}
			return order;
		}
	}
	
	public static Order createMDPBatchOrder(int toDo, long seed, String uid){
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("batch", toDo);
		content.put("seed", seed);
		return new Order(Type.BATCH, content, uid);
	}

	private List<ResultInterface> getResults(double[][] min, double[][] max, int[][] zeroSched, int mdp_totalDone) {
		List<ResultInterface> results = new ArrayList<ResultInterface>();
		for(int j=0; j<optimInitialStates.size();j++){
			for(int i=0; i<requirements.size();i++){
				AbstractRequirement req = requirements.get(i);
				double minval = min[i][j];
				if (zeroSched[i][j] == currentReadingScheduler) // all schedulers are zero except maybe the one currently analyzed 
					minval = 0.0;
				ChernoffMDP_SMCResult result = new ChernoffMDP_SMCResult(mdp_totalDone, minval, max[i][j], zeroSched[i][j],
						optimInitialStates.get(j), getOptimId(), req, model);
				results.add(result);
			}
		}
		return results;
	}
}
