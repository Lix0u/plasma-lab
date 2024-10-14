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
package fr.inria.plasmalab.montecarlo.worker;

import java.util.Arrays;
import java.util.List;

import org.restlet.Client;
import org.restlet.resource.ClientResource;

import fr.inria.plasmalab.distributed.algorithm.AbstractWorker;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.restlet.common.InterfaceServerResource;
import fr.inria.plasmalab.montecarlo.data.MontecarloResult;
import fr.inria.plasmalab.montecarlo.server.InterfaceMCServerResource;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceSamplingTransition;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

/** Worker for the Monte Carlo algorithm and the Chernoff MDP algorithm.
 *  
 * @author ltraonou
 *
 */
public class ChernoffMDPWorker extends AbstractWorker {
	
	protected InterfaceMCServerResource mcr; 
	
	public ChernoffMDPWorker(AbstractModel model, List<AbstractRequirement> requirements){
		this.model = model;
		this.requirements = requirements;
	}

	@Override
	public String connect(Client c, String host, String port, String uri) {
		ClientResource client = new ClientResource("http://"+host+":"+port+"/"+uri+"/");
		client.setNext(c); 
		mcr = client.wrap(InterfaceMCServerResource.class);
		
		uid = mcr.register();
		return uid;
	}

	@Override
	protected InterfaceServerResource getServerResource() {
		return mcr;
	}

	@Override
	protected void processBatchOrder(Order order) throws PlasmaSimulatorException, PlasmaCheckerException {
		int toDo = (Integer) order.getParam("batch");
		long schedulerSeed = 0;
		boolean mdp =  order.getContent().containsKey("seed");
		if (mdp)
			schedulerSeed = (Long) order.getParam("seed");
		logger.debug(uid + " received batch order of "+toDo);
		
		int[][] positiveMatrix = new int[requirements.size()][optimNState];
		double[][] results = new double[requirements.size()][optimNState];
		for(int i=0; i<positiveMatrix.length; i++) {
			Arrays.fill(positiveMatrix[i], 0);
			Arrays.fill(results[i], 0.0);
		}

		//For each initial states
		for(int st=0; st<optimNState; st++){
			if(optimActivated)
				model.setValueOf(optimInitialStates.get(st));
			for(int i=0; i<toDo; i++) {
				//Initialize a new trace
				InterfaceState path;
				if (mdp)
					path = model.newPath(schedulerSeed);
				else
					path = model.newPath();
				//For each requirements
				for(int j=0; j<requirements.size(); j++) {
					double res = requirements.get(j).check(path);
					if (res > 0) {
						List<InterfaceState> trace = requirements.get(j).getLastTrace();
						InterfaceTransition lastTransition = trace.get(trace.size()-1).getIncomingTransition();
						if (lastTransition != null && lastTransition instanceof InterfaceSamplingTransition) {
							res *= ((InterfaceSamplingTransition)lastTransition).getLikelihoodRatio();
						}
						positiveMatrix[j][st] ++;
						results[j][st] += res;
					}
				}
			}			
		}
		logger.debug(uid + " performed a batch order of "+toDo);

		//Post
		MontecarloResult result = new MontecarloResult(order.getUid(), toDo, positiveMatrix, results);
		mcr.postResults(result);
	}

}
