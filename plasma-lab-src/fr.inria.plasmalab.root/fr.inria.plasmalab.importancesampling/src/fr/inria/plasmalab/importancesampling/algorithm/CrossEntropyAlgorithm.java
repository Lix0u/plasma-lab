/**
 * This file is part of fr.inria.plasmalab.importancesampling.
 *
 * fr.inria.plasmalab.importancesampling is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.importancesampling is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.importancesampling.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.importancesampling.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.importancesampling.data.CE_SMCResult;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.InterfaceSamplingSimulator;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceSamplingTransition;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.listener.ExperimentationListener;
import fr.inria.plasmalab.workflow.shared.ResultInterface;


public class CrossEntropyAlgorithm  implements InterfaceAlgorithmScheduler {
	final static Logger logger = LoggerFactory.getLogger(CrossEntropyAlgorithm.class);
	
	/** The id of the factory used to instantiate this algorithm */
	protected String nodeURI;

	protected int totalcount;
	protected double smoothing;
	protected int max_iteration;
	protected int nb_parameters;
	/** True if the algorithm must compute init values for the parameters. */
	protected boolean init;
		
	protected List<AbstractRequirement> requirements;
	protected AbstractModel model;
	protected List<InterfaceIdentifier> sampling_parameters;
	protected List<InterfaceIdentifier> count_parameters;
	protected List<InterfaceIdentifier> rate_parameters;
	
	protected List<Map<InterfaceIdentifier, Double>> optimInitialStates;
	protected OptimVariables optimVariables;
	protected InterfaceIdentifier[] optimId;

	protected ExperimentationListener listener;
	protected boolean stopOrderReceived; 
	
	// data
	protected int currentSt, currentReq;
	
	//results 
	protected List<ResultInterface> results;

	public CrossEntropyAlgorithm(
			AbstractModel model,
			List<AbstractRequirement> requirements,
			int totalcount,
			double smoothing,
			int max_iteration,
			int nbParameters,
			boolean init,
			String nodeURI) {
		this.totalcount = totalcount;
		this.smoothing = smoothing;
		this.max_iteration = max_iteration;
		this.nb_parameters = nbParameters;
		this.init = init;
		
		this.requirements = requirements;
		this.model = model;
		
		//Answer on
		this.nodeURI = nodeURI;
	}

	@Override
	public void run() {
		listener.notifyAlgorithmStarted(nodeURI);
		
		this.optimId = optimVariables.generateOptimIdentifiers(model).toArray(new InterfaceIdentifier[0]);
		optimInitialStates = optimVariables.getOptimVariablesRange(model);
		boolean optimActivated = !(optimVariables.getVars() == null || optimVariables.getVars().size()==0);
		int optimNState = optimActivated? optimInitialStates.size(): 1;
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
					initializeSamplingIdentifiers();
					runCurrent();
				}
			}
			listener.notifyAlgorithmCompleted(nodeURI);
		} catch (PlasmaStopOrder e) {
			listener.notifyAlgorithmStopped(nodeURI);
		} catch (PlasmaExperimentException e) {
			listener.notifyAlgorithmError(nodeURI, e.getMessage());
		}
	}
	
	private void runCurrent() throws PlasmaStopOrder, PlasmaSimulatorException, PlasmaCheckerException {
		AbstractRequirement requirement = requirements.get(currentReq);
		double[] current_parameters = new double[nb_parameters];
		double normal_sum = 0.0; // sum of the parameters, kept constant by normalization
		int iteration = 1;
		
		while (iteration <= max_iteration) {
			double[] numerator = new double[nb_parameters];
			Arrays.fill(numerator, 0.0);
			double[] denominator = new double[nb_parameters];
			Arrays.fill(denominator, 0.0);
			double sum_res = 0.0;
			
			for (int nbSimu = 1; nbSimu<=totalcount; nbSimu++) {			
				InterfaceState path = (init && iteration == 1) ?
						((InterfaceSamplingSimulator) model).newUniformPath() :  
						model.newPath();
				if (nbSimu == 1 && iteration == 1) {
					// initialize the parameters value: must be done there after a first initial state has been computed 
					for (int i = 0; i < nb_parameters; i++) {
						current_parameters[i] = path.getValueOf(sampling_parameters.get(i));
						normal_sum += current_parameters[i];
					}
				}
				double res = requirement.check(path);
				if (res > 0) {
					List<InterfaceState> trace = requirement.getLastTrace();
					InterfaceState lastState = trace.get(trace.size()-1);
					InterfaceSamplingTransition lastTransition = (InterfaceSamplingTransition) lastState.getIncomingTransition();
					if (lastTransition != null) {
						double likelihoodratio = lastTransition.getLikelihoodRatio();
						res *= likelihoodratio;
					}
					sum_res += res;
					
					for (int i = 0; i < nb_parameters; i++) {
						double sum_rate = 0.0;
						for (int j = 1; j < trace.size(); j++) {
							InterfaceState previousState = trace.get(j-1);
							InterfaceSamplingTransition t = (InterfaceSamplingTransition) trace.get(j).getIncomingTransition();
							sum_rate += previousState.getValueOf(rate_parameters.get(i)) / t.getTotalRate();
						}
						numerator[i] += res * lastState.getValueOf(count_parameters.get(i));
						denominator[i] += res * sum_rate;
					}
				}
				if (stopOrderReceived)
					throw new PlasmaStopOrder();
			}
			if (sum_res == 0)
				throw new PlasmaSimulatorException("No simulation has verified the property.");
			
			// update parameters and model
			current_parameters = updateParameters(current_parameters, numerator, denominator, normal_sum);
			
			// publish results
			results.add(new CE_SMCResult(model,requirement,sum_res/totalcount,current_parameters,sampling_parameters,iteration));
			listener.publishResults(nodeURI, results);

			iteration++;
		}	
	}
	
	private void initializeSamplingIdentifiers() throws PlasmaExperimentException {
		sampling_parameters = new ArrayList<InterfaceIdentifier>(nb_parameters);
		count_parameters = new ArrayList<InterfaceIdentifier>(nb_parameters);
		rate_parameters = new ArrayList<InterfaceIdentifier>(nb_parameters);
		for (int i = 1; i <= nb_parameters; i++) {
			InterfaceIdentifier id = model.getIdentifiers().get("lambda" + i);
			if (id == null)
				throw new PlasmaExperimentException("Unknown parameter lambda" + i);
			sampling_parameters.add(id);
			id = model.getIdentifiers().get("nb_lambda" + i);
			if (id == null)
				throw new PlasmaExperimentException("Unknown parameter nb_lambda" + i);
			count_parameters.add(id);
			id = model.getIdentifiers().get("\"rate_lambda" + i + "\"");
			if (id == null)
				throw new PlasmaExperimentException("Unknown parameter rate_lambda" + i);
			rate_parameters.add(id);
		}
	}
	
	private double[] updateParameters(double[] current_parameters, double[] numerator, double[] denominator, double normal_sum) throws PlasmaSimulatorException {
		// update parameters
		double[] new_parameters = new double[nb_parameters];
		double sum_parameters = 0.0;
		for (int i = 0; i < nb_parameters; i++) {
			new_parameters[i] = numerator[i] / denominator[i];
			logger.debug("update lambda" + i+1 + ". Numerator: " +  numerator[i] + "; Denominator: " + denominator[i]);

			// smoothing:
			if (new_parameters[i] == 0) {
				new_parameters[i] = smoothing * current_parameters[i];
			}
			sum_parameters += new_parameters[i];
		}
		
		// normalize:
		sum_parameters = normal_sum / sum_parameters;
		for (int i = 0; i < nb_parameters; i++) {
			new_parameters[i] = new_parameters[i] * sum_parameters;
		}
		
		// update model
		Map<InterfaceIdentifier,Double> update = new HashMap<InterfaceIdentifier, Double>(nb_parameters);
		for (int i = 0; i < nb_parameters; i++) {
			update.put(sampling_parameters.get(i), new_parameters[i]);
			logger.debug("update lambda" + i+1 + ". Previous: " +  current_parameters[i] + "; New: " + new_parameters[i]);
		}
		model.setValueOf(update);
		return new_parameters;
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
	public void setOptimizationVariables(OptimVariables optimVariables) {
		this.optimVariables = optimVariables;		
	}

	@Override
	public void abortScheduling() {
		stopOrderReceived = true;
	}

	@Override
	public void setServices(String port, int nbThread, int batch, int frequency,
			List<AbstractDataFactory> adfList,
			List<InterfaceAlgorithmFactory> aafList) {
		// nothing: algorithm is not distributed		
	}



}
