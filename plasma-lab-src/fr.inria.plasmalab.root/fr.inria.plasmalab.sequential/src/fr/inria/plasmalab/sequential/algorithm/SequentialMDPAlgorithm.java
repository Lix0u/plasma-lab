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

public class SequentialMDPAlgorithm implements InterfaceAlgorithmScheduler {
	
	/** The id of the factory used to instantiate this algorithm */
	protected String nodeURI;
	
	protected int schedCount;
	private final double p0, p1, ratioT, ratioF, strengthA, strengthB;

	protected int[][] totalDone, positiveMatrix;
	protected boolean[][] again, satisfied;
	
	protected List<AbstractRequirement> requirements;
	protected List<Map<InterfaceIdentifier, Double>> optimInitialStates;
	protected OptimVariables optimVariables;
	protected InterfaceIdentifier[] optimId;
	protected AbstractModel model;
	
	protected ExperimentationListener listener;
	protected boolean stopOrderReceived;
	protected boolean errorOccured;

	public SequentialMDPAlgorithm(AbstractModel model, List<AbstractRequirement> requirements,
			double alpha, double beta, double delta, double proba, int schedCount, String id) {	
		this.p0 = (proba + delta);
		this.p1 = (proba - delta); 
		this.ratioT = p1 / p0;
		this.ratioF = (1 - p1) / (1 - p0);
		this.strengthA = (1 - beta) / alpha;
		this.strengthB = beta / (1 - alpha);
		
		this.schedCount = schedCount;
		this.requirements = requirements;
		this.model = model;
		
		this.nodeURI = id;
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
		
		totalDone = new int[optimNState][requirements.size()];
		positiveMatrix = new int[optimNState][requirements.size()];
		satisfied = new boolean[optimNState][requirements.size()];
		again = new boolean[optimNState][requirements.size()];
		for(int i=0; i<totalDone.length; i++){
			Arrays.fill(totalDone[i], 0);
			Arrays.fill(positiveMatrix[i], 0);
			Arrays.fill(satisfied[i], false);
			Arrays.fill(again[i], true);
		}
		
		listener.notifyAlgorithmStarted(nodeURI);
		listener.publishResults(nodeURI, getResults());
		stopOrderReceived = false;
		errorOccured=false;
		
		//Throw schedulers
		Random schedulerRng = new Random();
		
		//For each scheduler run hypothesis testing
		try {
			for (int schedI = 0; schedI < schedCount; schedI++) {
				//Forget false results from previous schedulers
				boolean allSatisfied = true;
				for (int st = 0; st < optimNState; st++) {
					for (int req = 0; req < requirements.size(); req++) {
						if(!satisfied[st][req]){
							allSatisfied = false;
							again[st][req] = true;
							totalDone[st][req] = 0;
							positiveMatrix[st][req] = 0;
						}
					}
				}
				if(allSatisfied)
					break;
				long scheduler = schedulerRng.nextLong();
				for (int st = 0; st < optimNState; st++) {
					if(optimActivated)
						model.setValueOf(optimInitialStates.get(st));
					run(scheduler,st);
				}
			}
		} catch (PlasmaSimulatorException e) {
			errorOccured = true;
			listener.notifyAlgorithmError(nodeURI, e.getMessage());
		} catch (PlasmaCheckerException e) {
			errorOccured = true;
			listener.notifyAlgorithmError(nodeURI, e.getMessage());
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
		
	private void run(long scheduler, int st) throws PlasmaCheckerException, PlasmaSimulatorException {
		boolean oneAgain = true;
		double seqratio[] = new double[requirements.size()];
		Arrays.fill(seqratio, 1.);
		
		run: do{
			if(stopOrderReceived)
				break run;
			oneAgain = false;
			
			InterfaceState path = model.newPath(scheduler);
			//For each requirements
			for(int req=0; req<requirements.size(); req++) {
				if(again[st][req]) {
					totalDone[st][req] ++;
					if(requirements.get(req).check(path) > 0) {
						positiveMatrix[st][req] ++;
						seqratio[req] *= ratioT;
					}
					else {
						seqratio[req] *= ratioF;
					}
					if(seqratio[req] < strengthA && seqratio[req] > strengthB){
						//We continue at least for one
						oneAgain = true;
					} else{
						again[st][req] = false;
						satisfied[st][req] = seqratio[req] <= strengthB;
					}
				}
			}
		} while(oneAgain);
	}
			
//	{	
//			again2=true;
//			errorOccured=false;
//			long start = System.currentTimeMillis();
//			listener.notifyAlgorithmStarted(nodeURI);
//			listener.publishResults(nodeURI, getResults());
//			stopOrderReceived = false;
//	
//			
//			//Fill seqratio
//			double seqratio[][] = new double[positiveMatrix.length][positiveMatrix[0].length];
//			
//			final double p0 = (proba + delta),
//						 p1 = (proba - delta); 
//	
//	
//			final double	ratioT = p1 / p0,
//							ratioF = (1 - p1) / (1 - p0);
//			
//			final double	strengthA = (1 - beta) / alpha,
//							strengthB = beta / (1 - alpha);
//			
//			//Throw schedulers
//			Random schedulerRng = new Random();
//			
//			//For each scheduler run hypothesis testing
//			// Break if one scheduler satisfies the property
//			run: for (int schedI = 0; schedI < schedCount; schedI++) {
//				long scheduler = schedulerRng.nextLong();
//				for(double[] array:seqratio)
//					Arrays.fill(array, 1.);
//				//Forget false results from previous schedulers
//				boolean allSatisfied = true;
//				for (int st = 0; st < optimNState; st++) {
//					for (int j = 0; j < requirements.size(); j++) {
//						if(!satisfied[j][st]){
//							again[j][st] = true;
//							allSatisfied = false;
//							totalDone[j][st] = 0;
//							positiveMatrix[j][st] = 0;
//						}
//					}
//				}
//				if(allSatisfied)
//					break run;
//				
//				do {
//					if (stopOrderReceived)
//						break run;
//
//					// Clear update Matrix
//					for (int[] mat : updateMatrix) {
//						Arrays.fill(mat, 0);
//					}
//					for (int st = 0; st < optimNState; st++) {
//						try {
//							if(optimActivated)
//								model.setValueOf(optimInitialStates.get(st));
//							InterfaceState path = model.newPath(scheduler);
//							//For each requirements
//							for(int j=0; j<requirements.size(); j++)
//								if(again[j][st]){
//									totalDone[j][st] ++;
//									if(requirements.get(j).check(path) > 0){
//										positiveMatrix[j][st] ++;
//										updateMatrix[j][st] ++;
//									}
//								}
//						} catch (PlasmaDeadlockException e) {
//							System.err.println(e.getMessage());
//							e.printStackTrace();
//							listener.notifyAlgorithmError(nodeURI,
//									e.getMessage());
//							errorOccured = true;
//							break run;
//						} catch (PlasmaParameterException e) {
//							e.printStackTrace();
//							listener.notifyAlgorithmError(nodeURI,
//									e.getMessage());
//							errorOccured = true;
//							break run;
//						} catch (PlasmaException e) {
//							e.printStackTrace();
//							listener.notifyAlgorithmError(nodeURI,
//									e.getMessage());
//							errorOccured = true;
//							break run;
//						}
//						for (int i = 0; i < positiveMatrix.length; i++) {
//							for (int j = 0; j < positiveMatrix[i].length; j++) {
//								// We doesn't take unneeded results
//								if (again[i][j]) {
//									// update seqratio matrix
//									int pos = updateMatrix[i][j];
//									int neg = 1 - updateMatrix[i][j];
//									seqratio[i][j] *= Math.pow(ratioT, pos)
//											* Math.pow(ratioF, neg);
//								}
//							}
//						}
//						again2 = false;
//						// Test "again" stop condition
//						for (int i = 0; i < seqratio.length; i++) {
//							double[] array = seqratio[i];
//							for (int j = 0; j < array.length; j++) {
//								if(again[i][j]){
//									double seq = array[j];
//									if (seq < strengthA && seq > strengthB) {
//										// We continue at least for one
//										again2 = true;
//									} else {
//										double pr = (double)positiveMatrix[i][j]/totalDone[i][j];
//										satisfied[i][j] = seq <= strengthB;
//										again[i][j] = false;
//
//										System.err.println("Sc_"+schedI+": ["+i+','+j+"] = "+satisfied[i][j]+"; Pr=" + pr + " --- "+scheduler );
//									}
//								}
//							}
//						}
//						// Notify new results
//						// listener.publishResults(nodeURI, getResults());
//					}
//				} while (again2);
//				// Notify new results
//				listener.publishResults(nodeURI, getResults());
//			}
//
//			if(!errorOccured){
//				// Notify new results
//				listener.publishResults(nodeURI, getResults());
//				// Notify completed
//				if(stopOrderReceived)
//					listener.notifyAlgorithmStopped(nodeURI);
//				else
//					listener.notifyAlgorithmCompleted(nodeURI);
//				//System.err.println("Done in "+(System.currentTimeMillis()-start)+"ms");			
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			listener.notifyAlgorithmError(nodeURI, e.getMessage());
//		}
//	}

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
		for(int i=0; i<optimInitialStates.size(); i++){
			for(int j=0; j<requirements.size(); j++){
				AbstractRequirement req = requirements.get(j);
				Sequential_SMCResult result = new Sequential_SMCResult(totalDone[i][j], positiveMatrix[i][j], satisfied[i][j],
						optimInitialStates.get(i), optimId, req, model);
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
