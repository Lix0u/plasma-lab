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
package fr.inria.plasmalab.sequential.worker;

import java.util.Arrays;
import java.util.List;

import org.restlet.Client;
import org.restlet.resource.ClientResource;

import fr.inria.plasmalab.distributed.algorithm.AbstractWorker;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.restlet.common.InterfaceServerResource;
import fr.inria.plasmalab.montecarlo.data.MontecarloResult;
import fr.inria.plasmalab.sequential.restlet.server.InterfaceSequentialServerResource;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public class SequentialWorker extends AbstractWorker {
	protected InterfaceSequentialServerResource mcr; 
	
	public SequentialWorker(AbstractModel model, List<AbstractRequirement> requirements) {
		this.model = model;
		this.requirements = requirements;
	}
	
	@Override
	public String connect(Client c, String host, String port, String uri) {
		ClientResource client = new ClientResource("http://"+host+":"+port+"/"+uri+"/");
		client.setNext(c); 
		mcr = client.wrap(InterfaceSequentialServerResource.class);
		
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
		int toDo = (Integer) order.getParam("batch");
		List<List<Boolean>> again = (List<List<Boolean>>) order.getParam("again");
		logger.debug("Received batch order"+toDo);
		
		int[][] positiveMatrix = new int[requirements.size()][optimNState];
		for(int i=0; i<positiveMatrix.length; i++)
			Arrays.fill(positiveMatrix[i], 0);
		MontecarloResult result = null;
	
		//For each initial states
		for(int st=0; st<optimNState; st++) {
			if(optimActivated)
				model.setValueOf(optimInitialStates.get(st));
			for(int i=0; i<toDo; i++) {
				//Initialize a new trace
				InterfaceState path = model.newPath();
				//For each requirements
				for(int j=0; j<requirements.size(); j++)
					if(again.get(j).get(st) && requirements.get(j).check(path) > 0)
						positiveMatrix[j][st] ++;
			}
			//Post
			result = new MontecarloResult(order.getUid(), toDo, positiveMatrix, null);
			mcr.postResults(result);
		}
	}
}
