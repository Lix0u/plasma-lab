/**
 * This file is part of fr.inria.plasmalab.importancesplitting.
 *
 * fr.inria.plasmalab.importancesplitting is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.importancesplitting is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.importancesplitting.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.importancesplitting.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.AbstractAlgorithm;
import fr.inria.plasmalab.importancesplitting.data.GlobalState;
import fr.inria.plasmalab.importancesplitting.data.IS_SMCResult;
import fr.inria.plasmalab.importancesplitting.data.Trace;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class ISAdaptiveAlgorithm extends AbstractAlgorithm {
	/** The logger used by the algorithm */
	protected final static Logger logger = LoggerFactory.getLogger(ISAdaptiveAlgorithm.class);
	
	// parameters
	protected int budget;
	protected double scorePhi;
	
	// data
	protected int currentSt, currentReq;
	
	//results 
	protected List<ResultInterface> results;
	
	/** Adaptive levels algorithm */
	public ISAdaptiveAlgorithm(AbstractModel model, List<AbstractRequirement> reqs, int budget, double scorePhi, String nodeURI) {
		this.budget = budget;
		this.scorePhi = scorePhi;

		this.requirements = reqs;
		this.model = model;
		this.nodeURI = nodeURI;
	}
	
	@Override
	public void run() {
		initializeAlgorithm();
		logger.info("Starting adaptive importance spliting algorithm with budget " + budget + " until a score of " + scorePhi);

		listener.notifyAlgorithmStarted(nodeURI);
		this.results = new ArrayList<ResultInterface>(optimNState*requirements.size());
		try {
			for(currentSt=0; currentSt<optimNState; currentSt++) {
				if(optimActivated)
					model.setValueOf(optimInitialStates.get(currentSt));
				for(currentReq=0; currentReq<requirements.size(); currentReq++) {
					if (currentReq >=1) {
						// we need to reset the model that has been modified during the algorithm
						model.checkForErrors(); // this is a bit too much, but it does the work...
						if(optimActivated)
							model.setValueOf(optimInitialStates.get(currentSt));
					}
					runCurrent();
					listener.publishResults(nodeURI, results);
				}
			}
		}
		catch (PlasmaStopOrder e) {
			results.add(new IS_SMCResult(model, requirements.get(currentReq), new ArrayList<Double>()));
		}
		catch (PlasmaExperimentException e) {
			results.add(new IS_SMCResult(model, requirements.get(currentReq), new ArrayList<Double>()));
			//logger.error(e.getMessage(),e);
			listener.notifyAlgorithmError(nodeURI, e.toString());
			errorOccured = true;
		}
		
		if(!errorOccured){
			if(stopOrderReceived)
				listener.notifyAlgorithmStopped(nodeURI);
			else
				listener.notifyAlgorithmCompleted(nodeURI);
		}
	}
	
	private void runCurrent() throws PlasmaExperimentException {
		List<Double> conditionalProbabilities = new ArrayList<Double>();
		List<Trace> paths = new LinkedList<Trace>(); 
		double score = 0, scorePath = 0, scoreMin = scorePhi;
		AbstractRequirement requirement = requirements.get(currentReq);
		Random rebranchingRng = new Random();

		// 1 generate first set of traces
		for (int i = 0; i < budget; i++) {
			InterfaceState initState = model.newPath();
			scorePath = requirement.check(initState);
			if (scorePath < scoreMin)
				scoreMin = scorePath;
			paths.add(new Trace(model.getTrace(), requirement.getLastTrace(), scorePath));
			if (stopOrderReceived)
				throw new PlasmaStopOrder(); 
		}

		score = scoreMin;
		List<Trace> newPaths = new LinkedList<Trace>();
		List<Trace> orderedPaths = new LinkedList<Trace>();
		while (score < scorePhi) {
			// 2 determine accepted traces
			scoreMin = scorePhi;
			int accepted = 0;
			ListIterator<Trace> it = paths.listIterator();
			while (it.hasNext()) {
				Trace t = it.next();
				if (t.getScore() > score) {
					accepted++;
					if (t.getScore() < scoreMin)
						scoreMin = t.getScore();
				} else {
					it.remove();
				}
			}
			if (accepted == 0)
				throw new PlasmaExperimentException("No accepted path. Score: " + score);
			
			conditionalProbabilities.add((double) accepted / budget);
			logger.info("Probability at level "	+ conditionalProbabilities.size() + ": " + (double) accepted / budget);

			// 3 rebranch the non accepted traces
			for (int i = 0; i < budget - accepted; i++) {
				Trace oldPath;
				if (paths.size() > 0) { /* while there are paths ... */
					int index = rebranchingRng.nextInt(paths.size());
					oldPath = paths.remove(index); /* ... choose randomly from paths */
					orderedPaths.add(oldPath); /* ... and add to an empty list */
					it = orderedPaths.listIterator(); /* empty here */
				} else { /* used all paths, so re-cycle */
					oldPath = it.next();
					if (!it.hasNext())
						it = orderedPaths.listIterator();
				}
				
				GlobalState initState = oldPath.getBranchingState(score);
//					Map<InterfaceIdentifier, Double> update = new HashMap<InterfaceIdentifier, Double>(headers.length);
//					for (InterfaceIdentifier id : headers) {
//						update.put(id, initState.modelState.getValueOf(id.getName()));
//					}
				model.setValueOf(initState.modelState.getValues());
				model.newPath();

				scorePath = requirement.check(initState.observerState);
				if (scorePath < scoreMin)
					scoreMin = scorePath;
				newPaths.add(new Trace(model.getTrace(), requirement.getLastTrace(), scorePath)); /* store new paths and add later */
				if (stopOrderReceived)
					throw new PlasmaStopOrder();
			}
			if (paths.size() < newPaths.size()) {
				List<Trace> temp = paths;
				paths = newPaths;
				newPaths = temp;
			}
			paths.addAll(newPaths);
			paths.addAll(orderedPaths);
			newPaths.clear(); /* clear newPaths and orderedPaths ready for next level */
			orderedPaths.clear();

			score = scoreMin;
			logger.info("Score after iteration " + conditionalProbabilities.size() + ": " + score);
		}
		results.add(new IS_SMCResult(model, requirements.get(currentReq), conditionalProbabilities));
	}
}
