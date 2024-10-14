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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.AbstractAlgorithm;
import fr.inria.plasmalab.montecarlo.data.Montecarlo_SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceSamplingTransition;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.shared.ResultInterface;


/**
 * @author ltraonou@inria.fr
 */
public class MontecarloAlgorithm  extends AbstractAlgorithm {
	/** The logger used by the algorithm */
	protected final static Logger logger = LoggerFactory.getLogger(AbstractAlgorithm.class);
	
	protected int totalToDo;

	public MontecarloAlgorithm(AbstractModel model, List<AbstractRequirement> requirements, int totalcount, String nodeURI) {
		totalToDo = totalcount;

		this.requirements = requirements;
		this.model = model;
		this.nodeURI = nodeURI;
	}

	@Override
	public void run() {
		initializeAlgorithm();
		
		long start = System.currentTimeMillis();

		int batchUpdate = (int) (totalToDo*0.01);
		batchUpdate = batchUpdate==0? 1:batchUpdate;

		int[][] positiveMatrix = new int[requirements.size()][optimNState];
		double[][] results = new double[requirements.size()][optimNState];
		int[] totalDone = new int[optimNState];
		for(int i=0; i<positiveMatrix.length; i++){
			Arrays.fill(positiveMatrix[i], 0);
			Arrays.fill(results[i], 0.0);
		}
		Arrays.fill(totalDone, 0);
		
		listener.notifyAlgorithmStarted(nodeURI);
		listener.publishResults(nodeURI, getResults(totalDone, positiveMatrix, results));
		logger.info("Starting Monte Carlo algorithm with " + totalToDo + " simulations.");
		
		try {
			for(int st=0; st<optimNState; st++){
				if(optimActivated)
					model.setValueOf(optimInitialStates.get(st));
				for(int i=0; i<totalToDo; i++) {
					InterfaceState path = model.newPath();
					for(int j=0; j<requirements.size(); j++) {
						double res = requirements.get(j).check(path);
						if (res != 0) {
							List<InterfaceState> trace = requirements.get(j).getLastTrace();
							InterfaceTransition lastTransition = trace.get(trace.size()-1).getIncomingTransition();
							if (lastTransition != null && lastTransition instanceof InterfaceSamplingTransition) {
								res *= ((InterfaceSamplingTransition)lastTransition).getLikelihoodRatio();
							}
							positiveMatrix[j][st] ++;
							results[j][st] += res;
						}
					}
					totalDone[st] = totalDone[st]+1;
					if(totalDone[st]%batchUpdate == 0){
						//Notify new results
						double percent = ((double)(totalToDo*st+totalDone[st])/(totalToDo*optimNState));
						double remaining = (System.currentTimeMillis()-start)/percent-(System.currentTimeMillis()-start);
						listener.notifyProgress((int)(percent*100));
						listener.notifyTimeRemaining((long)remaining);
						listener.publishResults(nodeURI, getResults(totalDone, positiveMatrix, results));
					}
					if(stopOrderReceived)
						throw new PlasmaStopOrder();
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
			listener.publishResults(nodeURI, getResults(totalDone, positiveMatrix, results));
			// Notify completed
			if(stopOrderReceived)
				listener.notifyAlgorithmStopped(nodeURI);
			else
				listener.notifyAlgorithmCompleted(nodeURI);
		}
	}

	protected List<ResultInterface> getResults(int[] totalDone, int[][] positiveMatrix, double[][] results) {
		List<ResultInterface> smcresults = new ArrayList<ResultInterface>();
		for(int j=0; j<optimInitialStates.size();j++){
			for(int i=0; i<requirements.size();i++){
				AbstractRequirement req = requirements.get(i);
				Montecarlo_SMCResult result = new Montecarlo_SMCResult(totalDone[j], positiveMatrix[i][j], results[i][j],
						optimInitialStates.get(j), getOptimId(), req, model);
				smcresults.add(result);
			}
		}
		return smcresults;
	}

}
