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

import org.restlet.Client;
import org.restlet.resource.ClientResource;

import fr.inria.plasmalab.distributed.algorithm.AbstractWorker;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.restlet.common.InterfaceServerResource;
import fr.inria.plasmalab.importancesampling.data.CE_Result;
import fr.inria.plasmalab.importancesampling.server.InterfaceCEServerRessource;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.InterfaceSamplingSimulator;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceSamplingTransition;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;

public class CrossEntropyWorker extends AbstractWorker {

	protected InterfaceCEServerRessource mcr; 
	
	protected List<InterfaceIdentifier> sampling_parameters;
	protected List<InterfaceIdentifier> count_parameters;
	protected List<InterfaceIdentifier> rate_parameters;
	protected boolean initialized_id;
	
	public CrossEntropyWorker(AbstractModel model, List<AbstractRequirement> req) {
		this.model = model;
		this.requirements = req;
	}

	@Override
	public String connect(Client c, String host, String port, String uri) {
		ClientResource client = new ClientResource("http://"+host+":"+port+"/"+uri+"/");
		client.setNext(c); 
		mcr = client.wrap(InterfaceCEServerRessource.class);
		
		uid = mcr.register();
		return uid;
	}
	
	@Override
	protected InterfaceServerResource getServerResource() {
		return mcr;
	}
	
	/** Perform additional initialization and then start */
	@Override
	public void start() {
		initialized_id = false;	
		super.start();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void processBatchOrder(Order order) throws PlasmaExperimentException {
		int req = (Integer) order.getParam("req");
		int st = (Integer) order.getParam("st");
		boolean init = (Boolean) order.getParam("init");
		int todo = (Integer) order.getParam("todo");
		List<Double> params = (ArrayList<Double>) order.getParam("params");
		logger.debug(uid+" received batch order of " + todo);
		
		AbstractRequirement requirement = requirements.get(req);
		int nb_parameters = params.size();
		if (!initialized_id) {
			initializeSamplingIdentifiers(nb_parameters);
			initialized_id = true;
		}

		// update model
		Map<InterfaceIdentifier,Double> update = new HashMap<InterfaceIdentifier, Double>(nb_parameters);
		for (int i = 0; i < nb_parameters; i++) {
			update.put(sampling_parameters.get(i), params.get(i));
		}
		if(optimActivated) {
			update.putAll(optimInitialStates.get(st));	
		}
		model.setValueOf(update);
	
		// simulate
		double[] numerator = new double[nb_parameters];
		Arrays.fill(numerator, 0.0);
		double[] denominator = new double[nb_parameters];
		Arrays.fill(denominator, 0.0);
		double sum_res = 0.0;
		for (int nbSimu = 1; nbSimu<=todo; nbSimu++) {
			InterfaceState path = init ? ((InterfaceSamplingSimulator) model).newUniformPath() :  model.newPath();
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
		}
		
		//Post
		CE_Result result = new CE_Result(order.getUid(), todo, sum_res, numerator, denominator);
		logger.debug(uid+" Batch completed, post results");
		mcr.postCEResults(result);
	}
	
	private void initializeSamplingIdentifiers(int nb_parameters) throws PlasmaExperimentException {
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


}
