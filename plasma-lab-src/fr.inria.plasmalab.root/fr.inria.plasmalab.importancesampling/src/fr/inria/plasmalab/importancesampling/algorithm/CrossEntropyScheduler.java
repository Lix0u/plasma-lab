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

import fr.inria.plasmalab.distributed.algorithm.AbstractScheduler;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.algorithm.common.Order.Type;
import fr.inria.plasmalab.importancesampling.data.CE_Result;
import fr.inria.plasmalab.importancesampling.data.CE_SMCResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class CrossEntropyScheduler extends AbstractScheduler {

	protected static final int SLEEP_ORDER = 100;

	protected int totalcount;
	protected double smoothing;
	protected int max_iteration;
	protected int nb_parameters;
	protected boolean init;
	protected List<InterfaceIdentifier> sampling_parameters;

	// results
	protected List<ResultInterface> results;
	
	// data
	protected int currentSt, currentReq, remainingToDo, iteration;
	protected double[] current_parameters; 
	protected boolean done;
	
	public CrossEntropyScheduler (AbstractModel model, List<AbstractRequirement> requirements,
			int totalcount, double smoothing, int max_iteration, int nbParameters, boolean init, String nodeURI) {
		this.totalcount = totalcount;
		this.smoothing = smoothing;
		this.max_iteration = max_iteration;
		this.nb_parameters = nbParameters;
		this.init = init;
		
		this.requirements = requirements;
		this.model = model;
		this.nodeURI = nodeURI;
	}
	
	@Override
	public void run() {
		initializeServer();

		listener.notifyAlgorithmStarted(nodeURI);

		this.results = new ArrayList<ResultInterface>(optimNState*requirements.size());
		this.done = false;
		try {
			for(currentSt=0; currentSt<optimNState; currentSt++) {
				if(optimActivated)
					model.setValueOf(optimInitialStates.get(currentSt));
				model.checkForErrors(); // we construct the model just to get initial values
				initializeSamplingIdentifiers();
				for(currentReq=0; currentReq<requirements.size(); currentReq++) {
					runCurrent();
				}
			}
		}
		catch (PlasmaStopOrder e) { }
		catch (PlasmaExperimentException e) {
			errorOccured = true;
			listener.notifyAlgorithmError(nodeURI, e.getMessage());
		}
		this.done = true; // terminate all workers
		if(!errorOccured){
			// Notify completed
			if(stopOrderReceived)
				listener.notifyAlgorithmStopped(nodeURI);
			else
				listener.notifyAlgorithmCompleted(nodeURI);		
		}
		
		terminateServer();
	}
	
	private void runCurrent() throws PlasmaExperimentException {
		iteration = 1;
		current_parameters = new double[nb_parameters];
		double normal_sum = 0.0; // sum of the parameters, kept constant by normalization
		
		while (iteration <= max_iteration) {
			int totalDone = 0;
			double[] numerator = new double[nb_parameters];
			Arrays.fill(numerator, 0.0);
			double[] denominator = new double[nb_parameters];
			Arrays.fill(denominator, 0.0);
			double sum_res = 0.0;
			
			if (iteration == 1) {
				InterfaceState path = model.newPath();
				// initialize the parameters value: must be done there after a first initial state has been computed 
				for (int i = 0; i < nb_parameters; i++) {
					current_parameters[i] = path.getValueOf(sampling_parameters.get(i));
					normal_sum += current_parameters[i];
				}
			}
			
			this.remainingToDo = totalcount; // start the workers
			while(totalDone < totalcount) {
				//Get latest result in queue
				CE_Result result = (CE_Result) getNextResult();
				totalDone += result.getTotal();
				sum_res += result.getResult();
				for (int i = 0; i < nb_parameters; i++) {
					numerator[i] += result.getNumerator()[i];
					denominator[i] += result.getDenominator()[i];
				}
			}
			if (sum_res == 0)
				throw new PlasmaExperimentException("No simulation has verified the property.");
						
			// update parameters
			current_parameters = updateParameters(current_parameters, numerator, denominator, normal_sum);
			iteration++;			

			// publish results
			results.add(new CE_SMCResult(model,requirements.get(currentReq),sum_res/totalcount,current_parameters,sampling_parameters,iteration));
			listener.publishResults(nodeURI, results);
		}	
	}

	@Override
	protected Order generateOrder() {
		//If the worker should stop
		if(stopOrderReceived || done)
			return Order.createTerminateOrder(null);
		//If the worker should wait
		else if (remainingToDo <= 0)
			return Order.createWaitOrder(SLEEP_ORDER, null);
		//Else the worker will receive a new work
		else {
			int currentBatch = Math.min(remainingToDo, batch);
			remainingToDo -= currentBatch;
			return createCEBatchOrder(currentReq, currentSt, init && iteration==1, current_parameters, currentBatch, null);
		}
	}

	private void initializeSamplingIdentifiers() throws PlasmaExperimentException {
		sampling_parameters = new ArrayList<InterfaceIdentifier>(nb_parameters);
		for (int i = 1; i <= nb_parameters; i++) {
			InterfaceIdentifier id = model.getIdentifiers().get("lambda" + i);
			if (id == null)
				throw new PlasmaExperimentException("Unknown parameter lambda" + i);
			sampling_parameters.add(id);
		}
	}
	
	private double[] updateParameters(double[] current_parameters, double[] numerator, double[] denominator, double normal_sum) {
		// update parameters
		double[] new_parameters = new double[nb_parameters];
		double sum_parameters = 0.0;
		for (int i = 0; i < nb_parameters; i++) {
			new_parameters[i] = numerator[i] / denominator[i];

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
		
		return new_parameters;
	}	
	
	public static Order createCEBatchOrder(int req, int st, boolean init, double[] current_parameters, int toDo, String uid){
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("req", req);
		content.put("st", st);
		content.put("init", init);
		content.put("params", current_parameters);
		content.put("todo", toDo);
		return new Order(Type.BATCH, content, uid);
	}
}
