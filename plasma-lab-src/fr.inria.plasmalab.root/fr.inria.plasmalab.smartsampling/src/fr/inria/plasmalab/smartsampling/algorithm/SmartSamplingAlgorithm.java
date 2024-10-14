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
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.AbstractAlgorithm;
import fr.inria.plasmalab.smartsampling.data.IteratedMDP_SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceSamplingTransition;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class SmartSamplingAlgorithm extends AbstractAlgorithm {
	/** The logger used by the algorithm */
	protected final static Logger logger = LoggerFactory.getLogger(SmartSamplingAlgorithm.class);
	
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
	/** Initial probability */
	protected double initProba;
	/** Checking a reward instead of a probability */
	protected String reward;
	/** Additional hypothesis test */ 
	protected boolean sprt;
	/** SPRT confidence */ 
	protected double alpha;
	
	/** Maximum (resp. minimum) probability over all the schedulers
	 *  When checking a reward the result is also store in this matrix
	 */
	protected double probability[][];
	/** SPRT results for reachability rewards */
	protected boolean sprtIndicator[][];
	/** Number of iterations of the algorithm */
	protected int iterations[][];
	/** Total number of simulations performed*/
	protected int nbTotalSimulations[][];
	
	// Global variables
	protected int nbSchedulers;

	/** Constructor for minimum and maximum probability */
	public SmartSamplingAlgorithm(AbstractModel model, List<AbstractRequirement> requirements, boolean maximum, int schedBudget, int simBudget, int factor,
			double epsilon, double delta, double initProba, String nodeURI) {
		this.requirements = requirements;
		this.model = model;
		this.nodeURI = nodeURI;

		this.maximum = maximum;
		this.schedBudget = schedBudget;
		this.simBudget = simBudget;
		this.factor = factor;
		this.epsilon = epsilon;
		this.delta = delta;
		this.initProba = initProba;
		this.reward = "";
	}
	
	/** Constructor for minimum and maximum reward without hypothesis test */
	public SmartSamplingAlgorithm(AbstractModel model, List<AbstractRequirement> requirements, boolean maximum, int schedBudget, int simBudget, int factor,
			double epsilon, double delta, String reward, String nodeURI) {
		this.requirements = requirements;
		this.model = model;
		this.nodeURI = nodeURI;
		
		this.maximum = maximum;
		this.schedBudget = schedBudget;
		this.simBudget = simBudget;
		this.factor = factor;
		this.epsilon = epsilon;
		this.delta = delta;
		this.reward = reward;
		this.sprt = false;
	}
	
	/** Constructor for minimum and maximum reward with hypothesis test */
	public SmartSamplingAlgorithm(AbstractModel model, List<AbstractRequirement> requirements, boolean maximum, int schedBudget, int simBudget, int factor,
			double epsilon, double delta, String reward, double threshold, double alpha, String nodeURI) {
		this.requirements = requirements;
		this.model = model;
		this.nodeURI = nodeURI;

		this.maximum = maximum;
		this.schedBudget = schedBudget;
		this.simBudget = simBudget;
		this.factor = factor;
		this.epsilon = epsilon;
		this.delta = delta;
		this.reward = reward;
		this.sprt = true;
		this.initProba = threshold;
		this.alpha = alpha;
	}
	
	@Override
	public void run() {
		initializeAlgorithm();

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
			for(int st=0; st<optimNState; st++) {
				if(optimActivated)
					model.setValueOf(optimInitialStates.get(st));
				for(int req=0; req<requirements.size(); req++) {
					if (reward.isEmpty())
						runProba(st,req);
					else
						runReward(st,req);
				}
			}
		}
		catch(PlasmaStopOrder e){ }
		catch (PlasmaExperimentException e) {
			listener.notifyAlgorithmError(nodeURI, e.getMessage());
			errorOccured = true;
		}
		
		if(!errorOccured){
			// Notify new results
			listener.publishResults(nodeURI, getResults());
			// Notify completed
			if(stopOrderReceived)
				listener.notifyAlgorithmStopped(nodeURI);
			else
				listener.notifyAlgorithmCompleted(nodeURI);
		}
	}
		
		
	private void runProba(int st, int req) throws PlasmaSimulatorException, PlasmaCheckerException, PlasmaStopOrder {
		long start1 = System.currentTimeMillis();
		long start2;
		long time;
		iterations[st][req] = 0;
	
		int scheduler, nbTokeep, batchUpdate;
		int nbIterMax = (int) (Math.log(simBudget)/Math.log(factor));
		
		listener.publishResults(nodeURI, getResults());	
		Random schedulerRng = new Random();

		// First iteration -------------------------------------------------------------------------------------------------------
		// Roughly estimate the maximum (resp. minimum) probability ps
		int totalToDo = (int)(Math.sqrt(simBudget));
		while (probability[st][req]==0 && totalToDo <= simBudget) {
			start2 = System.currentTimeMillis();
			iterations[st][req]++;
			nbSchedulers = simBudget/totalToDo;
			probability[st][req] = maximum ? 0 : 1;
			logger.info("Iteration " + iterations[st][req] + ": " + totalToDo + " simulations; " + nbSchedulers + " schedulers;");
			nbIterMax++;
			batchUpdate = Math.max((int) (nbSchedulers*0.01),1);

			//Iterate over scheduler
			for(scheduler=0; scheduler<nbSchedulers; scheduler++){
				// Initialize a new scheduler/seed
				long seed = schedulerRng.nextLong();
				
				// Check the scheduler
				double proba = checkSchedulerProba(totalToDo, requirements.get(req), seed);
				
				if (!maximum && proba == 0) { // The minimum probability is 0. 
					probability[st][req] = 0;
					break;
				}
			
				// Update the maximum probability
				if((maximum && proba > probability[st][req]) || (!maximum && proba < probability[st][req])) {
					probability[st][req] = proba;
				}
									
				// Update progress
				if(scheduler%batchUpdate == 0){
					double percent = ((double)nbTotalSimulations[st][req])/(nbIterMax*simBudget+schedBudget);
					double remaining = (System.currentTimeMillis()-start1)/percent-(System.currentTimeMillis()-start1);
					listener.notifyProgress((int)(100*percent));
					listener.notifyTimeRemaining((long)remaining);
				}
			}
			time = System.currentTimeMillis() - start2;
			logger.info("Iteration " + iterations[st][req] + ": " + simBudget + " simulations in " + time + " ms ");
			listener.publishResults(nodeURI, getResults());								
			totalToDo = totalToDo*2;
		}
		if (probability[st][req]==0)
			return;
		
		// Iteration 2: initialize scheduler seeds -------------------------------------------------------------------------------------------------------
		start2 = System.currentTimeMillis();
		iterations[st][req]++;
		totalToDo = (int)(1/probability[st][req]);
		nbSchedulers = schedBudget/totalToDo;
		nbTokeep = Math.min(Math.max(nbSchedulers/factor,1), simBudget);

		logger.info("Iteration " + iterations[st][req] + " : " + totalToDo + " simulations; " + nbSchedulers + " schedulers;");
		batchUpdate = Math.max((int) (nbSchedulers*0.01),1);
		
		TreeMap<Double, List<Long>> bestSchedulers = new TreeMap<Double, List<Long>>();
		int bestSchedulersSize = 0;
		for(scheduler=0; scheduler<nbSchedulers; scheduler++){
			long seed = schedulerRng.nextLong();
			nbTotalSimulations[st][req] += totalToDo;

			// Check the scheduler
			double val = checkSchedulerProba(totalToDo, requirements.get(req), seed);
			
			// Update the map
			bestSchedulersSize = updateBestSchedulers(val, seed, bestSchedulers, bestSchedulersSize, nbTokeep);
			
			// Update progress
			if(scheduler%batchUpdate == 0){
				double percent = ((double)nbTotalSimulations[st][req])/(nbIterMax*simBudget+schedBudget);
				double remaining = (System.currentTimeMillis()-start1)/percent-(System.currentTimeMillis()-start1);
				listener.notifyProgress((int)(100*percent));
				listener.notifyTimeRemaining((long)remaining);
			}
		}
		// Keep the best schedulers according to the factor
		Map.Entry<Double,List<Long>> best = maximum ? bestSchedulers.lastEntry() : bestSchedulers.firstEntry();
		probability[st][req] = best.getKey();
		
		nbSchedulers = nbTokeep;
		long[] schedulerSeed = new long[nbSchedulers];
		sortBestSchedulers(bestSchedulers,schedulerSeed);
				
		// Notify new results
		time = System.currentTimeMillis() - start2;
		logger.info("Iteration " + iterations[st][req] + " : " + schedBudget + " simulations in " + time + " ms ");
		listener.publishResults(nodeURI, getResults());	
		
		// Iteration 3 to n -------------------------------------------------------------------------------------------------------
		// Compute the probability of each of the schedulers. Keep the greater half (resp. the lower half)		
		int chernoffBound = (int) Math.ceil(-Math.log(1-Math.exp(Math.log(1-delta)/nbSchedulers))/(2* Math.pow(epsilon, 2)));
		totalToDo = Math.min(simBudget/nbSchedulers,chernoffBound);
		while(totalToDo < chernoffBound) {
			start2 = System.currentTimeMillis();
			iterations[st][req]++;
			
			logger.info("Iteration " + iterations[st][req] + " : " + totalToDo + " simulations; " + nbSchedulers + " schedulers;");
			batchUpdate = Math.max((int) (nbSchedulers*0.01),1);

			bestSchedulers = new TreeMap<Double, List<Long>>();
			bestSchedulersSize = 0;
			nbTokeep = Math.max(nbSchedulers/factor, 1);
			for(scheduler=0; scheduler<nbSchedulers; scheduler++){
				nbTotalSimulations[st][req] += totalToDo;

				// Check the scheduler
				double val = checkSchedulerProba(totalToDo, requirements.get(req), schedulerSeed[scheduler]);
				
				// Update the map
				bestSchedulersSize = updateBestSchedulers(val, schedulerSeed[scheduler], bestSchedulers, bestSchedulersSize, nbTokeep);
							
				// Update progress
				if(scheduler%batchUpdate == 0){
					double percent = ((double)nbTotalSimulations[st][req])/(nbIterMax*simBudget+schedBudget);
					double remaining = (System.currentTimeMillis()-start1)/percent-(System.currentTimeMillis()-start1);
					listener.notifyProgress((int)(100*percent));
					listener.notifyTimeRemaining((long)remaining);
				}
			}
			// Keep the best schedulers according to the factor
			best = maximum ? bestSchedulers.lastEntry() : bestSchedulers.firstEntry();
			probability[st][req] = best.getKey();
			logger.info("minimum probability: " + bestSchedulers.firstEntry().getKey());
			logger.info("maximum probability: " + bestSchedulers.lastEntry().getKey());
			
			nbSchedulers = nbTokeep;
			schedulerSeed = new long[nbSchedulers];
			sortBestSchedulers(bestSchedulers,schedulerSeed);
			
			chernoffBound = (int) 	Math.ceil(-Math.log(1-Math.exp(Math.log(1-delta)/nbSchedulers))/(2* Math.pow(epsilon, 2)));
			totalToDo = Math.min(simBudget/nbSchedulers,chernoffBound);
					
			// Notify new results
			time = System.currentTimeMillis() - start2;
			logger.info("Iteration " + iterations[st][req] + " : " + simBudget + " simulations in " + time + " ms ");

			listener.publishResults(nodeURI, getResults());	
		}
	}
	
	/** Check one requirement with one scheduler. 
	 * @throws PlasmaSimulatorException 
	 * @throws PlasmaCheckerException 
	 * @throws PlasmaStopOrder */
	private double checkSchedulerProba(int totalDoDo, AbstractRequirement currentRequirement, 
			long schedulerSeed) throws PlasmaSimulatorException, PlasmaCheckerException, PlasmaStopOrder {
		double ret = 0;
		for (int i = 0; i < totalDoDo; i++) {
			InterfaceState path = model.newPath(schedulerSeed);
			double res = currentRequirement.check(path);
			if (res > 0) {
				List<InterfaceState> trace = currentRequirement.getLastTrace();
				InterfaceTransition lastTransition = trace.get(trace.size()-1).getIncomingTransition();
				if (lastTransition != null && lastTransition instanceof InterfaceSamplingTransition) {
					res *= ((InterfaceSamplingTransition)lastTransition).getLikelihoodRatio();
				}
				ret += res;
			}
			if (stopOrderReceived)
				throw new PlasmaStopOrder();
		}
		return ret / (double) totalDoDo;
	}
	
	private void runReward(int st, int req) throws PlasmaSimulatorException, PlasmaCheckerException, PlasmaStopOrder {
		long start1 = System.currentTimeMillis();
		long start2;
		long time;
		iterations[st][req] = 0;
	
		int scheduler, nbTokeep, batchUpdate, chernoffBound;
		int nbIterMax = (int) (Math.log(simBudget)/Math.log(factor));
		Map.Entry<Double,List<Long>> best;
		
		listener.publishResults(nodeURI, getResults());	
		Random schedulerRng = new Random();

		// Iteration 2: initialize scheduler seeds -------------------------------------------------------------------------------------------------------
		start2 = System.currentTimeMillis();
		iterations[st][req]++;
		int totalToDo = 1;
		nbSchedulers = schedBudget;
		nbTokeep = Math.min(Math.max(nbSchedulers/factor,1), simBudget);

		logger.info("Iteration " + iterations[st][req] + " : " + totalToDo + " simulations; " + nbSchedulers + " schedulers;");
		batchUpdate = Math.max((int) (nbSchedulers*0.01),1);
		
		TreeMap<Double, List<Long>> bestSchedulers = new TreeMap<Double, List<Long>>();
		int bestSchedulersSize = 0;
		double average = 0;
		for(scheduler=0; scheduler<nbSchedulers; scheduler++){
			long seed = schedulerRng.nextLong();
			nbTotalSimulations[st][req] += totalToDo;

			// Check the scheduler
			double val = checkSchedulerReward(totalToDo, requirements.get(req),0,seed,new int[1]);
			average += val;
			
			// Update the map
			bestSchedulersSize = updateBestSchedulers(val, seed, bestSchedulers, bestSchedulersSize, nbTokeep);
			
			// Update progress
			if(scheduler%batchUpdate == 0){
				double percent = ((double)nbTotalSimulations[st][req])/(nbIterMax*simBudget+schedBudget);
				double remaining = (System.currentTimeMillis()-start1)/percent-(System.currentTimeMillis()-start1);
				listener.notifyProgress((int)(100*percent));
				listener.notifyTimeRemaining((long)remaining);
			}
		}
		average = average / nbSchedulers;

		// Notify new results
		time = System.currentTimeMillis() - start2;
		logger.info("Average reward: " + average);
		logger.info("Iteration " + iterations[st][req] + " : " + schedBudget + " simulations in " + time + " ms ");
		listener.publishResults(nodeURI, getResults());	

		// Iteration 3 to n -------------------------------------------------------------------------------------------------------
		// Compute the probability of each of the schedulers. Keep the greater half (resp. the lower half)
		nbSchedulers = nbTokeep;
		totalToDo = Math.max(simBudget/nbSchedulers,1);
		long[] schedulerSeed;
		int[] truesPerSched;
		int totalDone = 0;
		do {
			schedulerSeed = new long[nbSchedulers];
			truesPerSched = new int[nbSchedulers];
			sortBestSchedulers(bestSchedulers,schedulerSeed);	
			
			start2 = System.currentTimeMillis();
			iterations[st][req]++;
			logger.info("Iteration " + iterations[st][req] + " : " + totalToDo + " simulations; " + nbSchedulers + " schedulers;");
			batchUpdate = Math.max((int) (nbSchedulers*0.01),1);

			bestSchedulers = new TreeMap<Double, List<Long>>();
			bestSchedulersSize = 0;
			nbTokeep = Math.max(nbSchedulers/factor, 1);
			for(scheduler=0; scheduler<nbSchedulers; scheduler++){
				nbTotalSimulations[st][req] += totalToDo;
				
				// Check the scheduler
				double val = checkSchedulerReward(totalToDo, requirements.get(req), scheduler, schedulerSeed[scheduler], truesPerSched);
				
				// Update the map
				bestSchedulersSize = updateBestSchedulers(val, schedulerSeed[scheduler], bestSchedulers, bestSchedulersSize, nbTokeep);
							
				// Update progress
				if(scheduler%batchUpdate == 0){
					double percent = ((double)nbTotalSimulations[st][req])/(nbIterMax*simBudget+schedBudget);
					double remaining = (System.currentTimeMillis()-start1)/percent-(System.currentTimeMillis()-start1);
					listener.notifyProgress((int)(100*percent));
					listener.notifyTimeRemaining((long)remaining);
				}
			}
			
			totalDone = totalToDo;
			nbSchedulers = nbTokeep;
			chernoffBound = (int) 	Math.ceil(-Math.log(1-Math.exp(Math.log(1-delta)/nbSchedulers))/(2* Math.pow(epsilon, 2)));
			totalToDo = Math.min(simBudget/nbSchedulers,chernoffBound);

			best = maximum ? bestSchedulers.lastEntry() : bestSchedulers.firstEntry();
			probability[st][req] = best.getKey();

			// Notify new results
			time = System.currentTimeMillis() - start2;
			logger.info("Iteration " + iterations[st][req] + " : " + simBudget + " simulations in " + time + " ms ");

			listener.publishResults(nodeURI, getResults());	
		} while(totalToDo < chernoffBound);

		if (sprt) {
			sprtIndicator[st][req]=false;
			double a = 8*(Math.PI-3)/(3*Math.PI*(4-Math.PI));
			for (long seed : best.getValue()) {
				int trues = 0;
				for (int sched = 0; sched < schedulerSeed.length; sched++) {
					if (schedulerSeed[sched] == seed) {
						trues = truesPerSched[sched];
						break;
					}
				}
				double ratioZ = (trues - totalDone*initProba)/Math.sqrt(totalDone*initProba*(1-initProba));
				double ratioAlpha = Math.sqrt(2*(Math.sqrt(Math.pow((2/(Math.PI*a)+Math.log(1-alpha*alpha)/2),2)-Math.log(1-alpha*alpha)/a)-(2/(Math.PI*a)+Math.log(1-alpha*alpha)/2)));
				if (ratioZ > ratioAlpha) {// Ho is accepted
					sprtIndicator[st][req] = true;
					break;
				}
			}
			if (sprtIndicator[st][req])
				logger.info("SPRT hypothesis is accepted");
			else
				logger.info("SPRT hypothesis is rejected");
		}	
	}
	
	
	/** Check one requirement with one scheduler.
	 *  Returns the expected value of the reward at the end of the traces and updates the number of true simulations per scheduler
	 * @throws PlasmaSimulatorException 
	 * @throws PlasmaCheckerException 
	 * @throws PlasmaStopOrder 
	 */
	private double checkSchedulerReward(int totalToDo, AbstractRequirement currentRequirement,
			int scheduler, long schedulerSeed, int[] truesPerSched) throws PlasmaSimulatorException, PlasmaCheckerException, PlasmaStopOrder {
		double ret = 0;
		truesPerSched[scheduler]=0;
		for (int i = 0; i < totalToDo; i++) {
			InterfaceState path = model.newPath(schedulerSeed);
			truesPerSched[scheduler] += currentRequirement.check(path);
			List<InterfaceState> trace = currentRequirement.getLastTrace();
			double res = trace.get(trace.size()-1).getValueOf(reward); 
			InterfaceTransition lastTransition = trace.get(trace.size()-1).getIncomingTransition();
			if (lastTransition != null && lastTransition instanceof InterfaceSamplingTransition) {
				res *= ((InterfaceSamplingTransition)lastTransition).getLikelihoodRatio();
			}
			ret += res; 
			if (stopOrderReceived)
				throw new PlasmaStopOrder();
		}
		return ret / (double) totalToDo;
	}
	
	/** Update the TreeMap of the best schedulers with a new scheduler seed.
	 *  The seed is added if the value val (either the probability or the reward) is better than the worst one, or the map is not full yet. */ 
	private int updateBestSchedulers(double val, long seed, TreeMap<Double, List<Long>> bestSchedulers, int bestSchedulersSize, int nbTokeep) {
		if (bestSchedulersSize < nbTokeep
				|| (maximum && val > bestSchedulers.firstKey()) 
				|| (!maximum && val < bestSchedulers.lastKey())) { 
			List<Long> schedulers = bestSchedulers.get(val);
			if (schedulers == null) {
				schedulers = new ArrayList<Long>(1);
				bestSchedulers.put(val, schedulers);
			}
			schedulers.add(seed);
			bestSchedulersSize++;
			if (bestSchedulersSize > nbTokeep) {
				// remove one seed
				Map.Entry<Double,List<Long>> worst = maximum ? bestSchedulers.firstEntry() : bestSchedulers.lastEntry();
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
	
	/** Keep the best scheduler seeds */
	private void sortBestSchedulers(TreeMap<Double, List<Long>> bestSchedulers, long[] schedulerSeeds) {
		int scheduler = 0;
		NavigableMap<Double, List<Long>> iteratedMap = maximum ? bestSchedulers.descendingMap() : bestSchedulers;
		for (Map.Entry<Double,List<Long>> entry : iteratedMap.entrySet()) {
			List<Long> schedulers = entry.getValue();
			for (Long seed : schedulers) {
				schedulerSeeds[scheduler] = seed;
				//	schedulerResult[scheduler] = entry.getKey();
				scheduler++;
				if (scheduler >= nbSchedulers) {
//					logger.info("probability last kept: " + entry.getKey());
					return;
				}
			}
		}
	}
	
	public List<ResultInterface> getResults() {
		List<ResultInterface> results = new ArrayList<ResultInterface>();
		for(int st=0; st<optimInitialStates.size();st++) {
			for(int req=0; req<requirements.size();req++) {
				boolean indicator = false;
				if (sprtIndicator != null)
					indicator = sprtIndicator[st][req];
				IteratedMDP_SMCResult result = new IteratedMDP_SMCResult(nbTotalSimulations[st][req], indicator, probability[st][req],  iterations[st][req],
						maximum, reward, optimInitialStates.get(st), getOptimId(), requirements.get(req), model);
				results.add(result);
			}
		}
		return results;
	}

}
