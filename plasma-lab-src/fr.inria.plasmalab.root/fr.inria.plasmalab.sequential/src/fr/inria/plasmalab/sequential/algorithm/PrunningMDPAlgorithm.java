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
package fr.inria.plasmalab.sequential.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.sequential.data.Sequential_SMCResult;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.listener.ExperimentationListener;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class PrunningMDPAlgorithm implements InterfaceAlgorithmScheduler {
	
	/** The id of the factory used to instantiate this algorithm */
	protected String nodeURI;
	
	private boolean again[][];
	boolean again2;
	
	double	alpha, beta, delta, proba;
	int schedCount;

	protected int totalToDo;
	protected int remainingToDo;
	protected int[][] totalDone;
	
	private int[][] positiveMatrix,updateMatrix;
	private boolean[][] satisfied;
	private List<AbstractRequirement> requirements;
	protected List<Map<InterfaceIdentifier, Double>> optimInitialStates;
	protected OptimVariables optimVariables;
	protected InterfaceIdentifier[] optimId;
	private AbstractModel model;
	
	private ExperimentationListener listener;

	private boolean stopOrderReceived;

	private boolean errorOccured;
	
	/* Prunning scheduler */
	List<Long> prevIterSched, currIterSched;
	int[][] counterExamples, totalSched;
	boolean[][] counterExamplesFound;

	public PrunningMDPAlgorithm(AbstractModel model,
			List<AbstractRequirement> requirements, double alpha, double beta,
			double delta, double proba, int schedCount, String id) {
		this.alpha = alpha;
		this.beta = beta;
		this.delta = delta;
		this.proba = proba;
		this.nodeURI = id;
		this.schedCount = schedCount;
		this.requirements = requirements;
		this.model = model;
	}

	@SuppressWarnings("unused")
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
			
			int reqSize = requirements.size();			
			
			totalDone = new int[reqSize][optimNState];
			again = new boolean[reqSize][optimNState];
			satisfied = new boolean[reqSize][optimNState];
			for(int i=0; i<again.length; i++){
				Arrays.fill(again[i], true);
				Arrays.fill(totalDone[i], 0);
				Arrays.fill(satisfied[i], false);
			}
			again2=true;
			positiveMatrix = new int[reqSize][optimNState];
			updateMatrix = new int[reqSize][optimNState];
			errorOccured=false;

			counterExamples = new int[reqSize][optimNState];
			totalSched = new int[reqSize][optimNState];
			for(int[] array:counterExamples)
				Arrays.fill(array, 0);
			for(int[] array:totalSched)
				Arrays.fill(array, 0);
			counterExamplesFound = new boolean[reqSize][optimNState];

			for(boolean[] array:counterExamplesFound)
				Arrays.fill(array, false);
			
			long start = System.currentTimeMillis();
			listener.notifyAlgorithmStarted(nodeURI);
			listener.publishResults(nodeURI, getResults());
			stopOrderReceived = false;
	//		//modelId = 0;
	
			
			//Fill seqratio
			double seqratio[][] = new double[optimNState][reqSize];
			
			final double p0 = (proba + delta),
						 p1 = (proba - delta); 
	
	
			final double	ratioT = p1 / p0,
							ratioF = (1 - p1) / (1 - p0);
			
			final double	strengthA = (1 - beta) / alpha,
							strengthB = beta / (1 - alpha);
			
			//Set schedulers
			Random schedulerRng = new Random();
			prevIterSched = new ArrayList<Long>();
			currIterSched = new ArrayList<Long>();
			
			//Iter over properties
			prunning: for(int req=0; req<reqSize; req++){
				System.err.println("Prunning "+schedCount+" schedulers for property "+requirements.get(req));
				boolean last = req==reqSize-1;
				//For each scheduler run hypothesis testing
				for (int st = 0; st < optimNState; st++){
					totalSched[req][st] = schedCount;
				}
				// Break if one scheduler satisfies the property
				run: for (int schedI = 0; schedI < schedCount; schedI++) {
					//Get a scheduler
					long scheduler;
					if(prevIterSched.isEmpty())
						scheduler = schedulerRng.nextLong();
					else
						scheduler = prevIterSched.get(schedI);
					
					for(double[] array:seqratio)
						Arrays.fill(array, 1.);
					//Forget false results from previous schedulers
					for (int st = 0; st < optimNState; st++) {
						//Unless it's the last requirement
						if(!last){
							again[req][st] = true;
							totalDone[req][st] = 0;
							positiveMatrix[req][st] = 0;
						}
						//If it's the last requirement
						// we forget until we found a counter example
						else{
							if(!counterExamplesFound[req][st]){
								again[req][st] = true;
								totalDone[req][st] = 0;
								positiveMatrix[req][st] = 0;
							}
						}
					}
					
					do {
						if (stopOrderReceived)
							break prunning;
	
						// Clear update Matrix
						for (int[] mat : updateMatrix) {
							Arrays.fill(mat, 0);
						}
						for (int st = 0; st < optimNState; st++) {
							try {
								if(optimActivated)
									model.setValueOf(optimInitialStates.get(st));
								InterfaceState path = model.newPath();
								// For each requirements
								//for (int j = 0; j < reqSize; j++) {
									if (again[req][st]) {
										model.newPath();
										totalDone[req][st]++;
										if (requirements.get(req).check(
												path) > 0) {
											updateMatrix[req][st]++;
											positiveMatrix[req][st]++;
										}
									}
								//}
							} catch (PlasmaSimulatorException e) {
								listener.notifyAlgorithmError(nodeURI, e.getMessage());
								errorOccured = true;
								break prunning;
							} catch (PlasmaCheckerException e) {
								listener.notifyAlgorithmError(nodeURI, e.getMessage());
								errorOccured = true;
								break prunning;
							}
							for (int i = 0; i < optimNState; i++) {
								//for (int j = 0; j < positiveMatrix[i].length; j++) {
									// We doesn't take unneeded results
									if (again[req][i]) {
										// update seqratio matrix
										int pos = updateMatrix[req][i];
										int neg = 1 - updateMatrix[req][i];
										seqratio[i][req] *= Math.pow(ratioT, pos)
												* Math.pow(ratioF, neg);
									}
								//}
							}
							again2 = false;
							// Test "again" stop condition
							for (int i = 0; i < optimNState; i++) {
								double[] array = seqratio[i];
								//for (int j = 0; j < array.length; j++) {
									if(again[req][i]){
										double seq = array[req];
										if (seq < strengthA && seq > strengthB) {
											// We continue at least for one
											again2 = true;
										} else {
											double pr = (double)positiveMatrix[req][i]/totalDone[req][i];
											satisfied[req][i] = seq <= strengthB;
											again[req][i] = false;
											if(satisfied[req][i]){
												currIterSched.add(scheduler);
												counterExamples[req][i]++;
												counterExamplesFound[req][i] = true;
												if(last)
													totalSched[req][i] = schedI;
											}
											//if(last)
											//	System.err.println("Sc_"+schedI+": ["+req+','+i+"] = "+satisfied[req][i]+" --- "+scheduler );
										}
									}
								//}
							}
							// Notify new results
							// listener.publishResults(nodeURI, getResults());
						}
					} while (again2);
				}
				prevIterSched = currIterSched;
				currIterSched = new ArrayList<Long>();
				schedCount = prevIterSched.size();
				// Notify new results
				listener.publishResults(nodeURI, getResults());
			}

			if(!errorOccured){
				// Notify new results
				listener.publishResults(nodeURI, getResults());
				// Notify completed
				if(stopOrderReceived)
					listener.notifyAlgorithmStopped(nodeURI);
				else
					listener.notifyAlgorithmCompleted(nodeURI);
				//System.err.println("Done in "+(System.currentTimeMillis()-start)+"ms");			
			}
		
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
	
	public List<ResultInterface> getResults() {
		List<ResultInterface> results = new ArrayList<ResultInterface>();
		for(int j=0; j<optimInitialStates.size();j++){
			for(int i=0; i<requirements.size();i++){
				AbstractRequirement req = requirements.get(i);
				Sequential_SMCResult result = new Sequential_SMCResult(totalDone[i][j], positiveMatrix[i][j], satisfied[i][j],
						optimInitialStates.get(j), optimId, req, model);
				results.add(result);
			}
		}
		return results;
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
//			List<AbstractRequirementFactory> arfList,
			List<InterfaceAlgorithmFactory> aafList) {
		//Nothing to do
	}

}
