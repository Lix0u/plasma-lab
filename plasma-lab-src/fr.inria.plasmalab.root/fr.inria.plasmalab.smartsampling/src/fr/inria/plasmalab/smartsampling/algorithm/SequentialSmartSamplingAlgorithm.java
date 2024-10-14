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
package fr.inria.plasmalab.smartsampling.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.smartsampling.data.SequentialMDP_SMCResult;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.listener.ExperimentationListener;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class SequentialSmartSamplingAlgorithm implements InterfaceAlgorithmScheduler {
	
	/** The id of the factory used to instantiate this algorithm */
	protected String nodeURI;
	
	private double	alpha, beta, delta, proba;
	private int simBudget;
	private boolean minORmax; 	// True if checking maximum probability. False if checking minimum probability

	private List<AbstractRequirement> requirements;
	private List<Map<InterfaceIdentifier, Double>> optimInitialStates;
	private OptimVariables optimVariables;
	private InterfaceIdentifier[] optimId;
	private AbstractModel model;

	private ExperimentationListener listener;
	
	private boolean stopOrderReceived;
	private boolean errorOccured;
	
	private int[][] iterations, nbTotalSimulations, satisfied;
	
	public SequentialSmartSamplingAlgorithm(AbstractModel model, List<AbstractRequirement> requirements,
			double alpha, double beta, double delta, double proba, int simBudget, boolean minORmax, String id) {
		this.alpha = alpha;
		this.beta = beta;
		this.delta = delta;
		this.proba = proba;
		this.simBudget = simBudget;
		this.minORmax = minORmax;
		this.nodeURI = id;
		this.requirements = requirements;
		this.model = model;
	}

	@Override
	public void run() {	
		//Optimization procedure
		//Optimization IDs for display
		this.optimId = optimVariables.generateOptimIdentifiers(model).toArray(new InterfaceIdentifier[0]);
		//Optimization initial states
		//optimInitialStates of size 1 with an empty map means no optimization
		optimInitialStates = optimVariables.getOptimVariablesRange(model);
		boolean optimActivated = !(optimVariables.getVars() == null || optimVariables.getVars().size()==0);
		//But it is still one initial state to test
		int optimNState = optimActivated? optimInitialStates.size(): 1;
		
		iterations = new int[optimNState][requirements.size()];
		nbTotalSimulations = new int[optimNState][requirements.size()];
		satisfied = new int[optimNState][requirements.size()];
		for(int st=0; st<optimNState; st++){
			Arrays.fill(iterations[st], 0);
			Arrays.fill(nbTotalSimulations[st], 0);
			Arrays.fill(satisfied[st], -1);
		}
		errorOccured=false;
		stopOrderReceived = false;
		listener.notifyAlgorithmStarted(nodeURI);
		listener.publishResults(nodeURI, getResults());
		
		try {
			for(int st=0; st<optimNState; st++) {
				if(optimActivated)
					model.setValueOf(optimInitialStates.get(st));
				for(int req=0; req<requirements.size(); req++) {
					satisfied[st][req] = run(st,req);
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
	
	private int run(int st, int req) throws PlasmaSimulatorException, PlasmaCheckerException, PlasmaStopOrder {
		Random schedulerRng = new Random();

		final double	p0 = (proba + delta),
					 	p1 = (proba - delta); 
		final double	ratioT = minORmax ? (p1 / p0) : (p0 / p1),
						ratioF = minORmax ? ((1 - p1) / (1 - p0)) : ((1-p0) / (1-p1));
		final double	strengthB = beta / (1 - alpha);
		
		// Initialize scheduler seeds
		int nbSchedulers = (int) (proba*simBudget);
		long[] schedulerSeed = new long[nbSchedulers];
		for(int scheduler = 0; scheduler<nbSchedulers; scheduler++){
			schedulerSeed[scheduler] = schedulerRng.nextLong();
		}
		while (nbSchedulers > 0) {
			iterations[st][req]++;
			int totalToDo = simBudget/nbSchedulers;
			final double 	alphaSched = 1-Math.exp(Math.log(1-alpha)/nbSchedulers),
							betaSched = 1-Math.exp(Math.log(1-beta)/nbSchedulers);
			final double 	strengthASched = (1-betaSched)/alphaSched,
							strengthBSched = betaSched/(1-alphaSched);
			double ratio = 1;
			TreeMap<Double, List<Long>> bestSchedulers = new TreeMap<Double, List<Long>>();
			int bestSchedulersSize = 0;
			for (int scheduler=0; scheduler < nbSchedulers; scheduler++) {
				double ratioSched = 1;
				for (int simu=0; simu < totalToDo; simu++) {
					if(stopOrderReceived)
						throw new PlasmaStopOrder();
					InterfaceState path = model.newPath(schedulerSeed[scheduler]);
					nbTotalSimulations[st][req]++;
					if (requirements.get(req).check(path) > 0) {
						ratio *= ratioT;
						ratioSched *= ratioT;
					}
					else {
						ratio *= ratioF;
						ratioSched *= ratioF;
					}
					if (ratio <= strengthB || ratioSched <= strengthBSched) {
						return 1;
					}
				}
				if (bestSchedulersSize < Math.max(nbSchedulers/2,1)	|| ratioSched < bestSchedulers.lastKey()) { 
					List<Long> schedulers = bestSchedulers.get(ratioSched);
					if (schedulers == null) {
						schedulers = new ArrayList<Long>(1);
						bestSchedulers.put(ratioSched, schedulers);
					}
					schedulers.add(schedulerSeed[scheduler]);
					bestSchedulersSize++;
				}
			}
			double ratioMax = bestSchedulers.firstKey(); // actually, this is the smallest ratio
			if (ratioMax >= strengthASched) {
				return 0;
			}
			if (minORmax && iterations[st][req]==1) {
				double nullRatio = Math.pow(ratioF,totalToDo);
				List<Long> nullSchedulers = bestSchedulers.remove(nullRatio);
				if (nullSchedulers==null)
					nbSchedulers = nbSchedulers/2; // no null schedulers
				else if (bestSchedulers.isEmpty())
					return 0; // only null schedulers
				else
					nbSchedulers = bestSchedulersSize-nullSchedulers.size();
			}
			else {
				nbSchedulers = nbSchedulers/2;
			}
			if (nbSchedulers > 0) {
				schedulerSeed = new long[nbSchedulers];
				int scheduler = 0;
				sort: {
					for (Map.Entry<Double,List<Long>> entry : bestSchedulers.entrySet()) {
						List<Long> schedulers = entry.getValue();
						for (Long seed : schedulers) {
							schedulerSeed[scheduler] = seed;
							scheduler++;
							if (scheduler >= nbSchedulers) {
								break sort;
							}
						}
					}
				}
			}
			listener.publishResults(nodeURI, getResults());
		}
		return -1; // undecided
	}
	
	public List<ResultInterface> getResults() {
		List<ResultInterface> results = new ArrayList<ResultInterface>();
		for(int st=0; st<optimInitialStates.size(); st++){
			for(int req=0; req<requirements.size(); req++){
				SequentialMDP_SMCResult result = new SequentialMDP_SMCResult(iterations[st][req], nbTotalSimulations[st][req], satisfied[st][req],
						optimInitialStates.get(st), optimId, requirements.get(req), model);
				results.add(result);
			}
		}
		return results;
	}

	@Override
	public String getNodeURI() {
		return nodeURI;
	}

	@Override
	public void setExpListener(ExperimentationListener listener) {
		this.listener = listener;
	}

	@Override
	public void setOptimizationVariables(OptimVariables  optimVariables) {
		this.optimVariables = optimVariables;
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

	@Override
	public void setServices(String port, int nbThread, int batch, int frequency,
			List<AbstractDataFactory> adfList,
			List<InterfaceAlgorithmFactory> aafList) {
		//Nothing to do		
	}

}
