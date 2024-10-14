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
package fr.inria.plasmalab.montecarlo.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.AbstractAlgorithm;
import fr.inria.plasmalab.montecarlo.data.ChernoffMDP_SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class ChernoffMDPAlgortihm extends AbstractAlgorithm {
	/** The logger used by the algorithm */
	protected final static Logger logger = LoggerFactory.getLogger(ChernoffMDPAlgortihm.class);
	
	protected int totalToDo;
	
	//MDP
	protected int schedCount;
	protected int scheduler;
	protected double[][] min, max;
	
	//Total done is the number of simulation runs for a given scheduler (inherited from MCAlgorithm)
	//Mdp total done is the total number of sim runs for the experimentation
	protected int mdp_totalDone;

	//Positive matrix is the number of positive for a given scheduler (inherited from MCAlgorithm)
	//Mdp Positive matrix is the total number of positive for the experimentation
	protected int[][] mdp_positiveMatrix;
	protected int[][] zeroSched;
	

	public ChernoffMDPAlgortihm(AbstractModel model,
			List<AbstractRequirement> requirements, int totalcount, int schedCount,
			String nodeURI) {

		this.requirements = requirements;
		this.model = model;
		this.nodeURI = nodeURI;
		
		this.totalToDo = totalcount;
		this.schedCount = schedCount;
	}
	
	@Override
	public void run() {
		initializeAlgorithm();

		long start = System.currentTimeMillis();
		
		int batchUpdate = (int) (totalToDo*0.01*schedCount);
		batchUpdate = batchUpdate==0? 1:batchUpdate;
		
		int[][] positiveMatrix = new int[requirements.size()][optimNState];
		mdp_positiveMatrix = new int[requirements.size()][optimNState];
		min = new double[requirements.size()][optimNState];
		max = new double[requirements.size()][optimNState];
		zeroSched = new int[requirements.size()][optimNState];
		int[] totalDone = new int[optimNState];
		for(int i=0; i<positiveMatrix.length; i++){
			Arrays.fill(positiveMatrix[i], 0);
			Arrays.fill(mdp_positiveMatrix[i], 0);
			Arrays.fill(zeroSched[i], 0);
			Arrays.fill(min[i], 1);
			Arrays.fill(max[i], 0);
		}
		mdp_totalDone = 0;
		scheduler=0;

		listener.notifyAlgorithmStarted(nodeURI);
		listener.publishResults(nodeURI, getResults());	
		logger.info("Starting Monte Carlo algorithm for MDP for " + schedCount + " schedulers and " + totalToDo + " simulations.");

		try {
			Random schedulerRng = new Random();
			//Iterate over scheduler
			for( ; scheduler<schedCount; scheduler++){
				// Initialize a new scheduler/seed
				long schedulerSeed = schedulerRng.nextLong();
				//Reinit totalDone for this scheduler
				Arrays.fill(totalDone, 0);
				
				for (int st = 0; st < optimNState; st++) {				
					if(optimActivated)
						model.setValueOf(optimInitialStates.get(st));
					for (int i = 0; i < totalToDo; i++) {
						InterfaceState path = model.newPath(schedulerSeed);
						// For each requirements
						for (int j = 0; j < requirements.size(); j++) {
							double check = requirements.get(j).check(path);
							positiveMatrix[j][st] += check;
							mdp_positiveMatrix[j][st] += check;
						}
						
						//One optim state with all its requirements checked
						mdp_totalDone++;
						totalDone[st] = totalDone[st]+1;
						if (mdp_totalDone % batchUpdate == 0) {
							// Notify new results
							double percent = ((double) mdp_totalDone / (totalToDo*schedCount*optimNState));
							double remaining = (System.currentTimeMillis() - start)
									/ percent
									- (System.currentTimeMillis() - start);
							listener.notifyProgress((int) (percent * 100));
							listener.notifyTimeRemaining((long) remaining);
							listener.publishResults(nodeURI, getResults());
						}
						if (stopOrderReceived)
							throw new PlasmaStopOrder();
					}
				}
				for(int i=0; i<positiveMatrix.length; i++){
					for(int j=0; j<positiveMatrix[i].length; j++){
						double pr = positiveMatrix[i][j]/(double)totalDone[j];
						if(pr>max[i][j])
							max[i][j]=pr;
						if(pr>0 && pr<min[i][j])
							min[i][j]=pr;
						else if(pr==0)
							zeroSched[i][j]++;
					}
					Arrays.fill(positiveMatrix[i], 0);
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
			listener.publishResults(nodeURI, getResults());
			// Notify completed
			if(stopOrderReceived)
				listener.notifyAlgorithmStopped(nodeURI);
			else
				listener.notifyAlgorithmCompleted(nodeURI);
		}
	}
	
	public List<ResultInterface> getResults() {
		List<ResultInterface> results = new ArrayList<ResultInterface>();
		for(int j=0; j<optimInitialStates.size();j++){
			for(int i=0; i<requirements.size();i++){
				AbstractRequirement req = requirements.get(i);
				double minval = min[i][j];
				if (zeroSched[i][j] == scheduler)
					minval = 0.0;
				ChernoffMDP_SMCResult result = new ChernoffMDP_SMCResult(mdp_totalDone, minval, max[i][j], zeroSched[i][j],
						optimInitialStates.get(j), getOptimId(), req, model);
				results.add(result);
			}
		}
		return results;
	}


}
