/**
 * This file is part of fr.inria.plasmalab.smartsampling.
 *
 * fr.inria.plasmalab.smartsampling is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.smartsampling is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.smartsampling.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.smartsampling.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import fr.inria.plasmalab.distributed.algorithm.AbstractScheduler;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.algorithm.common.Order.Type;
import fr.inria.plasmalab.smartsampling.data.IteratedMDPResult;
import fr.inria.plasmalab.smartsampling.data.IteratedMDP_SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

/** This scheduler implements smart sampling algorithms for mdp 
 *  
 *  @author ltraonou@inria.fr
 */
public class SmartSamplingScheduler extends AbstractScheduler {  
	
	/** Time in ms for which workers have to sleep if there is no work for them 
	 *  Can be configured in the command line.
	 */ 
	public static int SLEEP_ORDER = 100;
	
	/** A class to represent a scheduler */
	class Scheduler {
		public long seed;
		public int positive;
		
		public Scheduler(long s, int p) {
			this.seed = s;
			this.positive = p;
		}
	}
	
	// parameters
	/** True if checking maximum probability. False if checking minimum probability */
	protected boolean maximum; 
	/** Budget for the second iteration that generates the set of schedulers */
	protected int schedBudget;
	/** Total number of simulations of all the schedulers for each iterations */
	protected int simBudget;
	/** Reduction factor */
	protected int factor;
	/** Precision */
	protected double epsilon;
	/** Confidence */
	protected double delta;
	/** Initial probability and probability threshold for sprt*/
	protected double initProba;
	/** Checking a reward */
	protected String reward;
	/** Additional hypothesis test */ 
	protected boolean sprt;
	/** SPRT confidence */ 
	protected double alpha;
	
	// results
	/** Maximum (resp. minimum) probability over all the schedulers*/
	protected double probability[][];
	/** SPRT results for reachability rewards */
	protected boolean sprtIndicator[][];
	/** Number of iterations of the algorithm */
	protected int iterations[][];
	/** Total number of simulations performed*/
	protected int nbTotalSimulations[][];
			
	// shared variables
	protected Random schedulerRng;
	protected long[] schedulerSeeds;
	protected boolean seedsInitialized;
	protected Long remainingSeed;
	protected int currentWorkingScheduler, nbSchedulers, currentSt, currentReq;
	protected int totalToDo, remainingToDo, totalDone;
	
	/** Constructor for minimum and maximum probability */
	public SmartSamplingScheduler(AbstractModel model, List<AbstractRequirement> requirements, boolean maximum, int schedBudget, int simBudget, int factor,
			double epsilon, double delta, double initProba, String nodeURI){
		
		this.maximum = maximum;
		this.schedBudget = schedBudget;
		this.simBudget = simBudget;
		this.factor = factor;
		this.epsilon = epsilon;
		this.delta = delta;
		this.initProba = initProba;
		this.schedulerRng = new Random();
		this.remainingSeed = null;
		this.reward = "";
		
		this.requirements = requirements;
		this.model = model;
		this.nodeURI = nodeURI;
	}
	
	/** Constructor for minimum and maximum reward without hypothesis test */
	public SmartSamplingScheduler(AbstractModel model, List<AbstractRequirement> requirements, boolean maximum, int schedBudget, int simBudget, int factor,
			double epsilon, double delta, String reward, String nodeURI){

		this.maximum = maximum;
		this.schedBudget = schedBudget;
		this.simBudget = simBudget;
		this.factor = factor;
		this.epsilon = epsilon;
		this.delta = delta;
		this.schedulerRng = new Random();
		this.remainingSeed = null;
		this.reward = reward;
		this.sprt = false;	

		this.requirements = requirements;
		this.model = model;
		this.nodeURI = nodeURI;
	}
	
	/** Constructor for minimum and maximum reward with hypothesis test */
	public SmartSamplingScheduler(AbstractModel model, List<AbstractRequirement> requirements, boolean maximum, int schedBudget, int simBudget, int factor,
			double epsilon, double delta, String reward, double threshold, double alpha, String nodeURI){
		
		this.maximum = maximum;
		this.schedBudget = schedBudget;
		this.simBudget = simBudget;
		this.factor = factor;
		this.epsilon = epsilon;
		this.delta = delta;
		this.schedulerRng = new Random();
		this.remainingSeed = null;
		this.reward = reward;
		this.sprt = true;
		this.initProba = threshold;
		this.alpha = alpha;
		
		this.requirements = requirements;
		this.model = model;
		this.nodeURI = nodeURI;
	}
	
	@Override
	public void run() {		
		initializeServer();

		listener.notifyAlgorithmStarted(nodeURI);
		
		probability = new double[optimNState][requirements.size()];
		iterations = new int[optimNState][requirements.size()];
		nbTotalSimulations = new int[optimNState][requirements.size()];
		if (!reward.isEmpty())
			sprtIndicator = new boolean[optimNState][requirements.size()];
		for(int i=0; i<probability.length; i++){
			Arrays.fill(probability[i], initProba);
			Arrays.fill(iterations[i], 0);
			Arrays.fill(nbTotalSimulations[i], 0);
		}
		
		try {
			for(currentSt=0; currentSt<optimNState; currentSt++) {
				if(optimActivated)
					model.setValueOf(optimInitialStates.get(currentSt));				// certainly useless: the scheduler doesn't need a runnable model
				for(currentReq=0; currentReq<requirements.size(); currentReq++) {
					if (reward.isEmpty())
						runProba();
					else
						runReward();
				}
			}
		}
		catch(PlasmaStopOrder e) { }
		catch(PlasmaExperimentException e) {
			errorOccured = true;
			listener.notifyAlgorithmError(nodeURI, e.getMessage());
		}
		totalToDo = 0; // Terminate workers
		
		if(!errorOccured){
			// Notify new results
			listener.publishResults(nodeURI, getResults());
			// Notify completed
			if(stopOrderReceived)
				listener.notifyAlgorithmStopped(nodeURI);
			else
				listener.notifyAlgorithmCompleted(nodeURI);
		}
		
		terminateServer();
	}
	
	private void runProba() throws PlasmaExperimentException {
		long start1 = System.currentTimeMillis();
		long start2, time;
		int currentReadingScheduler, nbTokeep, batchUpdate;
		double currentResult; 
		int nbIterMax = (int) (Math.log(simBudget)/Math.log(factor));
		iterations[currentSt][currentReq] = 0;
		seedsInitialized = false;
        schedulerSeeds = null;
        
		listener.publishResults(nodeURI, getResults());	
		
		// First iterations -------------------------------------------------------------------------------------------------------
		// Roughly estimate the maximum (resp. minimum) probability ps
		totalToDo = (int)(Math.sqrt(simBudget));
		while (probability[currentSt][currentReq]==0 && totalToDo <= simBudget) {
			start2 = System.currentTimeMillis();
			iterations[currentSt][currentReq]++;
			
			nbSchedulers = simBudget/totalToDo;
			probability[currentSt][currentReq] = maximum ? 0 : 1;
			remainingToDo = totalToDo;
			currentWorkingScheduler = 0;
			
			logger.info("Iteration " + iterations[currentSt][currentReq] + ": " + totalToDo + " simulations; " + nbSchedulers + " schedulers;");
			nbIterMax++;
			batchUpdate = Math.max((int) (nbSchedulers*0.01),1);

			// Get results
			currentReadingScheduler = 0;
			totalDone = 0;
			currentResult = 0.0;
			while (currentReadingScheduler<nbSchedulers) {
				IteratedMDPResult result = (IteratedMDPResult) getNextResult();
				int done = result.getTotalDone();
				double[] results = result.getResultPerScheduler();
				// assume the results are received in order
				for (int i = 0; i < results.length; i++) {
					totalDone += done;
					nbTotalSimulations[currentSt][currentReq] += done;
					currentResult += results[i];
					if (totalDone >= totalToDo) {
						double proba = currentResult/(double)totalToDo;
						// If checking minimum and the probability becomes zero, checking the other schedulers is useless.
						// But I don't know how to stop the workers properly.
						if((maximum && proba > probability[currentSt][currentReq]) 
								|| (!maximum && proba < probability[currentSt][currentReq])) {
							probability[currentSt][currentReq]=proba;
						}
						// pass to next scheduler
						currentReadingScheduler++;
						totalDone = 0;
						currentResult = 0.0;			
					}
				}
				if(currentReadingScheduler%batchUpdate == 0){
					double percent = ((double)nbTotalSimulations[currentSt][currentReq])/(nbIterMax*simBudget+schedBudget);
					double remaining = (System.currentTimeMillis()-start1)/percent-(System.currentTimeMillis()-start1);
					listener.notifyProgress((int)(100*percent));
					listener.notifyTimeRemaining((long)remaining);
				}
			}

			time = System.currentTimeMillis() - start2;
			logger.info("Iteration " + iterations[currentSt][currentReq] + ": " + simBudget + " simulations in " + time + " ms ");
			// Notify new results
			listener.publishResults(nodeURI, getResults());
			totalToDo = totalToDo*2;
		}
		currentWorkingScheduler = Integer.MAX_VALUE; // Stop scheduling orders: must be done before resetting nbSchedulers
		if (probability[currentSt][currentReq]==0)
			return;
		
		// Iteration 2: initialize scheduler seeds -------------------------------------------------------------------------------------------------------
		start2 = System.currentTimeMillis();
		iterations[currentSt][currentReq]++;
		totalToDo = (int)(1/probability[currentSt][currentReq]);
		nbSchedulers = schedBudget/totalToDo;
		schedulerSeeds = new long[nbSchedulers];
		nbTokeep = Math.min(Math.max(nbSchedulers/factor,1), simBudget);
		
		logger.info("Iteration " + iterations[currentSt][currentReq] + " : " + totalToDo + " simulations; " + nbSchedulers + " schedulers;");
		batchUpdate = Math.max((int) (nbSchedulers*0.01),1);
		
		TreeMap<Double, List<Scheduler>> bestSchedulers = new TreeMap<Double, List<Scheduler>>();
		int bestSchedulersSize = 0;
		remainingToDo = totalToDo;
		currentWorkingScheduler = 0; // Restart scheduling orders

		// Get results
		currentReadingScheduler = 0;
		currentResult = 0.0;
		totalDone = 0;
		while (currentReadingScheduler<nbSchedulers) {
			IteratedMDPResult result = (IteratedMDPResult) getNextResult();
			int done = result.getTotalDone();
			double[] results = result.getResultPerScheduler();
			// assume the results are received in order
			for (int i = 0; i < results.length; i++) {
				totalDone += done;
				nbTotalSimulations[currentSt][currentReq] += done;
				currentResult += results[i];
				if (totalDone >= totalToDo) {
					double proba = currentResult/(double)totalToDo;
					bestSchedulersSize = updateBestSchedulers(proba, 0, schedulerSeeds[currentReadingScheduler], bestSchedulers, bestSchedulersSize, nbTokeep);

					// pass to next scheduler
					currentReadingScheduler++;
					totalDone = 0;
					currentResult = 0.0;			
				}
			}
			if(currentReadingScheduler%batchUpdate == 0){
				double percent = ((double)nbTotalSimulations[currentSt][currentReq])/(nbIterMax*simBudget+schedBudget);
				double remaining = (System.currentTimeMillis()-start1)/percent-(System.currentTimeMillis()-start1);
				listener.notifyProgress((int)(100*percent));
				listener.notifyTimeRemaining((long)remaining);
			}
		}
		currentWorkingScheduler = Integer.MAX_VALUE; // Stop scheduling orders: must be done before resetting nbSchedulers
		// Keep the greater half (resp. the lower half)
		Map.Entry<Double,List<Scheduler>> best = maximum ? bestSchedulers.lastEntry() : bestSchedulers.firstEntry();
		probability[currentSt][currentReq] = best.getKey();

		nbSchedulers = nbTokeep;
		sortBestSchedulers(bestSchedulers);
		seedsInitialized = true;
		
		// Notify new results
		time = System.currentTimeMillis() - start2;
		logger.info("Iteration " + iterations[currentSt][currentReq] + " : " + schedBudget + " simulations in " + time + " ms ");
		listener.publishResults(nodeURI, getResults());
		
		// Iteration 3 to n  -------------------------------------------------------------------------------------------------------
		// Compute the probability of each of the schedulers. Keep the greater half (resp. the lower half)
		int chernoffBound = (int) Math.ceil(-Math.log(1-Math.exp(Math.log(1-delta)/nbSchedulers))/(2* Math.pow(epsilon, 2)));
		totalToDo = Math.min(simBudget/nbSchedulers,chernoffBound);
		while(totalToDo < chernoffBound) {
			start2 = System.currentTimeMillis();
			iterations[currentSt][currentReq]++;
			bestSchedulers = new TreeMap<Double, List<Scheduler>>();
			bestSchedulersSize = 0;
			remainingToDo = totalToDo;
			currentWorkingScheduler = 0; // Restart scheduling orders
			nbTokeep = Math.max(nbSchedulers/factor, 1);

			logger.info("Iteration " + iterations[currentSt][currentReq] + " : " + totalToDo + " simulations; " + nbSchedulers + " schedulers;");
			batchUpdate = Math.max((int) (nbSchedulers*0.01),1);

			// Get results
			currentReadingScheduler = 0;
			currentResult = 0.0;
			totalDone = 0;
			while (currentReadingScheduler<nbSchedulers) {
				IteratedMDPResult result = (IteratedMDPResult) getNextResult();
				int done = result.getTotalDone();
				double[] results = result.getResultPerScheduler();
				// assume the results are received in order
				for (int i = 0; i < results.length; i++) {
					totalDone += done;
					nbTotalSimulations[currentSt][currentReq] += done;
					currentResult += results[i];
					if (totalDone >= totalToDo) {
						double proba = currentResult/(double)totalToDo;
						bestSchedulersSize = updateBestSchedulers(proba, 0, schedulerSeeds[currentReadingScheduler], bestSchedulers, bestSchedulersSize, nbTokeep);

						// pass to next scheduler
						currentReadingScheduler++;
						totalDone = 0;
						currentResult = 0.0;			
					}
				}
				if(currentReadingScheduler%batchUpdate == 0){
					double percent = ((double)nbTotalSimulations[currentSt][currentReq])/(nbIterMax*simBudget+schedBudget);
					double remaining = (System.currentTimeMillis()-start1)/percent-(System.currentTimeMillis()-start1);
					listener.notifyProgress((int)(100*percent));
					listener.notifyTimeRemaining((long)remaining);
				}
			}
			currentWorkingScheduler = Integer.MAX_VALUE; // Stop scheduling orders
			// Keep the greater half (resp. the lower half)
			best = maximum ? bestSchedulers.lastEntry() : bestSchedulers.firstEntry();
			probability[currentSt][currentReq] = best.getKey();

			nbSchedulers = nbTokeep;
			sortBestSchedulers(bestSchedulers);

			chernoffBound = (int) 	Math.ceil(-Math.log(1-Math.exp(Math.log(1-delta)/nbSchedulers))/(2* Math.pow(epsilon, 2)));
			totalToDo = Math.min(simBudget/nbSchedulers,chernoffBound);
					
			// Notify new results
			time = System.currentTimeMillis() - start2;
			logger.info("Iteration " + iterations[currentSt][currentReq] + " : " + simBudget + " simulations in " + time + " ms ");
			listener.publishResults(nodeURI, getResults());
		}
	}
	
	private void runReward() throws PlasmaExperimentException {
		long start1 = System.currentTimeMillis();
		long start2, time;
		int currentReadingScheduler, nbTokeep, batchUpdate;
		double currentResult; 
		int nbIterMax = (int) (Math.log(simBudget)/Math.log(factor));
		iterations[currentSt][currentReq] = 0;
		seedsInitialized = false;

		listener.publishResults(nodeURI, getResults());	
		
		// Iteration 2: initialize scheduler seeds -------------------------------------------------------------------------------------------------------
		start2 = System.currentTimeMillis();
		iterations[currentSt][currentReq]++;
		totalToDo = 1;
		nbSchedulers = schedBudget/totalToDo;
		nbTokeep = Math.min(Math.max(nbSchedulers/factor,1), simBudget);
		schedulerSeeds = new long[nbSchedulers];
		
		logger.info("Iteration " + iterations[currentSt][currentReq] + " : " + totalToDo + " simulations; " + nbSchedulers + " schedulers;");
		batchUpdate = Math.max((int) (nbSchedulers*0.01),1);
		
		TreeMap<Double, List<Scheduler>> bestSchedulers = new TreeMap<Double, List<Scheduler>>();
		int bestSchedulersSize = 0;
		remainingToDo = totalToDo;
		currentWorkingScheduler = 0; // Restart scheduling orders

		// Get results
		currentReadingScheduler = 0;
		currentResult = 0;
		totalDone = 0;
		double average=0;
		while (currentReadingScheduler<nbSchedulers) {
			IteratedMDPResult result = (IteratedMDPResult) getNextResult();
			int done = result.getTotalDone();
			double[] results = result.getResultPerScheduler();			
			// assume the results are received in order
			for (int i = 0; i < results.length; i++) {
				totalDone += done;
				nbTotalSimulations[currentSt][currentReq] += done;
				currentResult += results[i];
				if (totalDone >= totalToDo) {
					double value = currentResult/(double)totalToDo;
					average += value;
					bestSchedulersSize = updateBestSchedulers(value, 0, schedulerSeeds[currentReadingScheduler], bestSchedulers, bestSchedulersSize, nbTokeep);

					// pass to next scheduler
					currentReadingScheduler++;
					totalDone = 0;
					currentResult = 0;			
				}
			}	
			if(currentReadingScheduler%batchUpdate == 0){
				double percent = ((double)nbTotalSimulations[currentSt][currentReq])/(nbIterMax*simBudget+schedBudget);
				double remaining = (System.currentTimeMillis()-start1)/percent-(System.currentTimeMillis()-start1);
				listener.notifyProgress((int)(100*percent));
				listener.notifyTimeRemaining((long)remaining);
			}
		}
		average = average / nbSchedulers;
		currentWorkingScheduler = Integer.MAX_VALUE; // Stop scheduling orders: must be done before resetting nbSchedulers
		// Keep the greater half (resp. the lower half)
		Map.Entry<Double,List<Scheduler>> best = maximum ? bestSchedulers.lastEntry() : bestSchedulers.firstEntry();
		probability[currentSt][currentReq] = best.getKey();
		logger.info("Average reward: " + average);
//		logger.info("minimum probability: " + bestSchedulers.firstEntry().getKey());
//		logger.info("maximum probability: " + bestSchedulers.lastEntry().getKey());
//		logger.info("best schedulers: " + best.getValue().size() + " ; seed: " + best.getValue().get(0) + " ; proba: " + probability[st][currentReq]);
		
		nbSchedulers = nbTokeep;
		sortBestSchedulers(bestSchedulers);
		seedsInitialized = true;

		// Notify new results
		time = System.currentTimeMillis() - start2;
		logger.info("Iteration " + iterations[currentSt][currentReq] + " : " + schedBudget + " simulations in " + time + " ms ");
		listener.publishResults(nodeURI, getResults());
		
		// Iteration 3 to n  -------------------------------------------------------------------------------------------------------
		// Compute the probability of each of the schedulers. Keep the greater half (resp. the lower half)
		int chernoffBound = (int) Math.ceil(-Math.log(1-Math.exp(Math.log(1-delta)/nbSchedulers))/(2* Math.pow(epsilon, 2)));
		totalToDo = Math.min(simBudget/nbSchedulers,chernoffBound);
		while(totalToDo < chernoffBound) {
			start2 = System.currentTimeMillis();
			iterations[currentSt][currentReq]++;
			bestSchedulers = new TreeMap<Double, List<Scheduler>>();
			bestSchedulersSize = 0;
			remainingToDo = totalToDo;
			currentWorkingScheduler = 0; // Restart scheduling orders
			nbTokeep = Math.max(nbSchedulers/factor, 1);

			logger.info("Iteration " + iterations[currentSt][currentReq] + " : " + totalToDo + " simulations; " + nbSchedulers + " schedulers;");
			batchUpdate = Math.max((int) (nbSchedulers*0.01),1);

			// Get results
			currentReadingScheduler = 0;
			currentResult = 0;
			int currentPositive = 0;
			totalDone = 0;
			while (currentReadingScheduler<nbSchedulers) {
				IteratedMDPResult result = (IteratedMDPResult) getNextResult();				
				int done = result.getTotalDone();
				double[] results = result.getResultPerScheduler();
				int[] positives = result.getPositivePerScheduler();
				// assume the results are received in order
				for (int i = 0; i < results.length; i++) {
					totalDone += done;
					nbTotalSimulations[currentSt][currentReq] += done;
					currentResult += results[i];
					currentPositive += positives[i];
					if (totalDone >= totalToDo) {
						double value = currentResult/(double)totalToDo;
						bestSchedulersSize = updateBestSchedulers(value, currentPositive, schedulerSeeds[currentReadingScheduler], bestSchedulers, bestSchedulersSize, nbTokeep);

						// pass to next scheduler
						currentReadingScheduler++;
						totalDone = 0;
						currentResult = 0;
						currentPositive = 0;		
					}
				}				
				if(currentReadingScheduler%batchUpdate == 0){
					double percent = ((double)nbTotalSimulations[currentSt][currentReq])/(nbIterMax*simBudget+schedBudget);
					double remaining = (System.currentTimeMillis()-start1)/percent-(System.currentTimeMillis()-start1);
					listener.notifyProgress((int)(100*percent));
					listener.notifyTimeRemaining((long)remaining);
				}
			}
			currentWorkingScheduler = Integer.MAX_VALUE; // Stop scheduling orders
			// Keep the greater half (resp. the lower half)
			best = maximum ? bestSchedulers.lastEntry() : bestSchedulers.firstEntry();
			probability[currentSt][currentReq] = best.getKey();
//			logger.info("minimum probability: " + bestSchedulers.firstEntry().getKey());
//			logger.info("maximum probability: " + bestSchedulers.lastEntry().getKey());
//			logger.info("best schedulers: " + best.getValue().size() + " ; seed: " + best.getValue().get(0) + " ; proba: " + probability[st][currentReq]);
			
			nbSchedulers = nbTokeep;
			sortBestSchedulers(bestSchedulers);

			chernoffBound = (int) 	Math.ceil(-Math.log(1-Math.exp(Math.log(1-delta)/nbSchedulers))/(2* Math.pow(epsilon, 2)));
			totalToDo = Math.min(simBudget/nbSchedulers,chernoffBound);
					
			// Notify new results
			time = System.currentTimeMillis() - start2;
			logger.info("Iteration " + iterations[currentSt][currentReq] + " : " + simBudget + " simulations in " + time + " ms ");
			listener.publishResults(nodeURI, getResults());
		}
		
		if (sprt) {
			sprtIndicator[currentSt][currentReq]=false;
			double a = 8*(Math.PI-3)/(3*Math.PI*(4-Math.PI));
			for (Scheduler sched : best.getValue()) {
				double ratioZ = (sched.positive - totalDone*initProba)/Math.sqrt(totalDone*initProba*(1-initProba));
				double ratioAlpha = Math.sqrt(2*(Math.sqrt(Math.pow((2/(Math.PI*a)+Math.log(1-alpha*alpha)/2),2)-Math.log(1-alpha*alpha)/a)-(2/(Math.PI*a)+Math.log(1-alpha*alpha)/2)));
				if (ratioZ > ratioAlpha) {// Ho is accepted
					sprtIndicator[currentSt][currentReq] = true;
					break;
				}
			}
		}
	}
	
	@Override
	protected Order generateOrder() {
		if (stopOrderReceived || totalToDo == 0)
			return Order.createTerminateOrder(null);
		else if (currentWorkingScheduler >= nbSchedulers)
			return Order.createWaitOrder(SLEEP_ORDER, null);
		else {
			// Prepare the work
			int nbSchedPerBatch = Math.min(Math.max(batch/totalToDo,1), nbSchedulers-currentWorkingScheduler);
			long schedulers[] = new long[nbSchedPerBatch];
			int currentBatch = Math.min(batch, remainingToDo);
			for (int i = 0; i < nbSchedPerBatch; i++) {
				if (seedsInitialized) {
					schedulers[i] = schedulerSeeds[currentWorkingScheduler];
				} else { 
					schedulers[i] = schedulerRng.nextLong();
					if (schedulerSeeds != null)
						schedulerSeeds[currentWorkingScheduler] = schedulers[i];
				}
				remainingToDo -= currentBatch;
				if (remainingToDo == 0) {
					// pass to next scheduler (if any)
					currentWorkingScheduler++;
					remainingToDo = totalToDo;
				}
			}
			
			//Create batch order
			return createMDPIteratedBatchOrder(currentBatch, schedulers, currentSt, currentReq, reward, null);
		}
	}
		
	private int updateBestSchedulers(double resultVal, int positiveVal, long seed, TreeMap<Double, List<Scheduler>> bestSchedulers, int bestSchedulersSize, int nbTokeep) {
		if (bestSchedulersSize < nbTokeep
				|| (maximum && resultVal > bestSchedulers.firstKey()) 
				|| (!maximum && resultVal < bestSchedulers.lastKey())) { 
			List<Scheduler> schedulers = bestSchedulers.get(resultVal);
			if (schedulers == null) {
				schedulers = new ArrayList<Scheduler>(1);
				bestSchedulers.put(resultVal, schedulers);
			}
			schedulers.add(new Scheduler(seed,positiveVal));
			bestSchedulersSize++;
			if (bestSchedulersSize > nbTokeep) {
				// remove one seed
				Map.Entry<Double,List<Scheduler>> worst = maximum ? bestSchedulers.firstEntry() : bestSchedulers.lastEntry();
				schedulers = worst.getValue();
				if (schedulers.size() > 1) {
					schedulers.remove(schedulers.size()-1);
				}
				else {
					bestSchedulers.remove(worst.getKey());
				}
				bestSchedulersSize--;
			}
		}
		return bestSchedulersSize;
	}
	
	/** Refill scheduler seed with the best schedulers */
	private void sortBestSchedulers(TreeMap<Double, List<Scheduler>> bestSchedulers) {
		schedulerSeeds = new long[nbSchedulers];
		int scheduler = 0;
		NavigableMap<Double, List<Scheduler>> iteratedMap = maximum ? bestSchedulers.descendingMap() : bestSchedulers;
		for (Map.Entry<Double,List<Scheduler>> entry : iteratedMap.entrySet()) {
			List<Scheduler> schedulers = entry.getValue();
			for (Scheduler sched : schedulers) {
				schedulerSeeds[scheduler] = sched.seed;
				scheduler++;
				if (scheduler >= nbSchedulers) {
					// logger.info("probability last kept: " + entry.getKey());
					return;
				}
			}
		}
	}
	
	private List<ResultInterface> getResults() {
		List<ResultInterface> results = new ArrayList<ResultInterface>();
		for(int st=0; st<optimInitialStates.size();st++) {
			for(int req=0; req<requirements.size();req++) {
				boolean indicator = false;
				if (sprtIndicator != null)
					indicator = sprtIndicator[st][req];
				IteratedMDP_SMCResult result = new IteratedMDP_SMCResult(nbTotalSimulations[st][req], indicator, probability[st][req], iterations[st][req],
						maximum, reward, optimInitialStates.get(st), getOptimId(), requirements.get(req), model);
				results.add(result);
			}
		}
		return results;
	}
	
	public static Order createMDPIteratedBatchOrder(int toDo, long[] seeds, int st, int req, String reward, String uid){
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("batch", toDo);
		content.put("seeds", seeds);
		content.put("st", st);
		content.put("req", req);
		content.put("reward", reward);
		return new Order(Type.BATCH, content, uid);
	}
}
