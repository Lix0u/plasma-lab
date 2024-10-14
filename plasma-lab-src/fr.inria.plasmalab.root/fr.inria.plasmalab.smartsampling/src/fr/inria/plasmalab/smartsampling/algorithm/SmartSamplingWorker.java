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
package fr.inria.plasmalab.smartsampling.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Client;
import org.restlet.resource.ClientResource;

import fr.inria.plasmalab.distributed.algorithm.AbstractWorker;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.restlet.common.InterfaceServerResource;
import fr.inria.plasmalab.smartsampling.data.IteratedMDPResult;
import fr.inria.plasmalab.smartsampling.server.InterfaceSmartSamplingServerResource;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceSamplingTransition;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public class SmartSamplingWorker extends AbstractWorker {
	
	protected InterfaceSmartSamplingServerResource mcr; 
	
	public SmartSamplingWorker(AbstractModel model,  List<AbstractRequirement> requirements){
		this.model = model;
		this.requirements = requirements;
	}
	
	@Override
	public String connect(Client c, String host, String port, String uri) {
		ClientResource client = new ClientResource("http://"+host+":"+port+"/"+uri+"/");
		client.setNext(c); 
		mcr = client.wrap(InterfaceSmartSamplingServerResource.class);
		
		uid = mcr.register();
		return uid;
	}
	
	@Override
	protected InterfaceServerResource getServerResource() {
		return mcr;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void processBatchOrder(Order order) throws PlasmaSimulatorException, PlasmaCheckerException {
		int todo = (Integer) order.getParam("batch");
		List<Long> seeds = (ArrayList<Long>) order.getParam("seeds");
		int st = (Integer) order.getParam("st");		/* State, requirement and reward could be only transfer once */
		int req = (Integer) order.getParam("req");
		String reward = (String) order.getParam("reward");
		int[] positivePerScheduler = new int[seeds.size()];
		double[] resultPerScheduler = new double[seeds.size()];
		logger.debug(uid + " received batch order of " + todo);

		if (seeds.size()>0) {
			if(optimActivated)
				model.setValueOf(optimInitialStates.get(st)); /* this could be only done once (until next state) */
			for (int sched=0; sched<seeds.size(); sched++) {
				for(int sim=0; sim<todo; sim++){
					InterfaceState path = model.newPath(seeds.get(sched));
					double res = requirements.get(req).check(path);
					if (res > 0)
						positivePerScheduler[sched]++;
					if (reward.isEmpty()) {
						if (res > 0) {
							List<InterfaceState> trace = requirements.get(req).getLastTrace();
							InterfaceTransition lastTransition = trace.get(trace.size()-1).getIncomingTransition();
							if (lastTransition != null && lastTransition instanceof InterfaceSamplingTransition) {
								res *= ((InterfaceSamplingTransition)lastTransition).getLikelihoodRatio();
							}
						}
					}
					else {									
						List<InterfaceState> trace = requirements.get(req).getLastTrace();
						InterfaceState lastState = trace.get(trace.size()-1);
						res = lastState.getValueOf(reward); 
						InterfaceTransition lastTransition = lastState.getIncomingTransition();
						if (lastTransition != null && lastTransition instanceof InterfaceSamplingTransition) {
							res *= ((InterfaceSamplingTransition)lastTransition).getLikelihoodRatio();
						}
					}
					resultPerScheduler[sched] += res;
				}
			}
			//Post
			IteratedMDPResult result = new IteratedMDPResult(order.getUid(), todo, positivePerScheduler, resultPerScheduler);
			mcr.postIResults(result);
		}
	}

}
