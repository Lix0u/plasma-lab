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
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class ISFixedLevelsAlgorithm extends AbstractAlgorithm {
	/** The logger used by the algorithm */
	protected final static Logger logger = LoggerFactory.getLogger(ISFixedLevelsAlgorithm.class);
	
	// parameters
	protected int budget;
	protected double[] levels;

	// data
	protected int currentSt, currentReq;
	
	//results 
	protected List<ResultInterface> results;
	
	/** Fixed levels algorithm */
	public ISFixedLevelsAlgorithm(AbstractModel model, List<AbstractRequirement> reqs, int budget, double[] levels, String nodeURI) {
		this.budget = budget;
		this.levels = levels;

		this.nodeURI = nodeURI;
		this.model = model;
		this.requirements = reqs;		
	}

	@Override
	public void run() {
		initializeAlgorithm();
		
		listener.notifyAlgorithmStarted(nodeURI);	
		logger.info("Starting fixed levels importance spliting algorithm with budget " + budget);

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
		List<Double> conditionalProbabilities = new ArrayList<Double>(levels.length);
		for (int i = 0; i < levels.length; i++) {
			conditionalProbabilities.add(Double.NaN);
		}
		List<GlobalState> paths = new LinkedList<GlobalState>(); 
		double 	scorePath = 0;
		AbstractRequirement requirement = requirements.get(currentReq);
		Random rebranchingRng = new Random();
		int accepted = 0;
		

		// 1 generate first set of traces
		for (int i = 0; i < budget; i++) {
			InterfaceState initState = model.newPath();
			scorePath = requirement.check("score",levels[0],initState);		
			if (scorePath >= levels[0]) {
				paths.add(new GlobalState(model.getCurrentState(), requirement.getLastTrace().get(requirement.getLastTrace().size()-1)));
				accepted++;
			}
			if(stopOrderReceived)
				throw new PlasmaStopOrder();
		}
		if (accepted == 0)
			throw new PlasmaExperimentException("No path has reached level 0 whose value is " +  levels[0]);
		conditionalProbabilities.set(0,((double)accepted/budget));
		logger.info("Probability at level 0: " + (double)accepted/budget);
		
		// publish results
		results.add(new IS_SMCResult(model,requirements.get(currentReq),conditionalProbabilities));
		listener.publishResults(nodeURI, results);

		// 2 for each level restarts the simulations until reaching the next level
		for (int level = 1; level < levels.length; level++) {
			List<GlobalState> newPaths = new LinkedList<GlobalState>();
			List<GlobalState> orderedPaths = new LinkedList<GlobalState>();
			accepted = 0;
			ListIterator<GlobalState> it = paths.listIterator(); 
			for (int i = 0; i < budget; i++) {
				GlobalState initState;
				if (i < paths.size()) { // first restart all the accepted paths
					initState = it.next();
					if (!it.hasNext())
						it = orderedPaths.listIterator(); /* empty here */
				}
				else if (paths.size() > 0) { // then restart some random accepted paths, while there are paths
					int index = rebranchingRng.nextInt(paths.size());
					initState = paths.remove(index); /* ... choose randomly from paths */
					orderedPaths.add(initState); /* ... and add to an empty list */
					it = orderedPaths.listIterator();
				} else { // finally if all paths are used, re-cycle
					initState = it.next();
					if (!it.hasNext())
						it = orderedPaths.listIterator();
				}
				
//					Map<InterfaceIdentifier,Double> update = new HashMap<InterfaceIdentifier, Double>(headers.length);
//					for (InterfaceIdentifier id : headers) {
//						update.put(id, initState.modelState.getValueOf(id.getName()));
//					}
				model.setValueOf(initState.modelState.getValues());
				model.newPath();					
				
				scorePath = requirement.check("score",levels[level],initState.observerState);
				if (scorePath >= levels[level]) {
					newPaths.add(new GlobalState(model.getCurrentState(), requirement.getLastTrace().get(requirement.getLastTrace().size()-1)));
					accepted++;
				}
				if(stopOrderReceived)
					throw new PlasmaStopOrder();
			}
			if (accepted == 0)
				throw new PlasmaExperimentException("No path has reached level " + level + " whose value is " + levels[level]);
			
			conditionalProbabilities.set(level,((double)accepted/budget));
			logger.info("Probability at level " + level + ": " + (double)accepted/budget);
			
			// publish results
			results.remove(results.size()-1);
			results.add(new IS_SMCResult(model,requirements.get(currentReq),conditionalProbabilities));
			listener.publishResults(nodeURI, results);

			// prepare next level
			paths = newPaths;
		}
	}
}
