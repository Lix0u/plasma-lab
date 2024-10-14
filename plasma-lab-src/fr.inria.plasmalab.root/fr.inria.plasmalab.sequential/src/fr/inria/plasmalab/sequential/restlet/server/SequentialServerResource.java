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
package fr.inria.plasmalab.sequential.restlet.server;

import java.io.IOException;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.montecarlo.data.MontecarloResult;
import fr.inria.plasmalab.sequential.scheduler.SequentialScheduler;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;

public class SequentialServerResource extends ServerResource implements	InterfaceSequentialServerResource {

	@Override
	@Post("?register")
	public String register() {
		return getScheduler().registerNewNode();
	}

	@Override
	@Post("?ready")
	public Order ready(String uid) {
		try {
			return getScheduler().schedule(uid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Post("?results")
	public void postResults(MontecarloResult result) {
		getScheduler().putResult(result);
	}

	private SequentialScheduler getScheduler() {
		return (SequentialScheduler) getApplication().getContext()
				.getAttributes().get("sequential");
	}

	@Override
	@Get("?initialStates")
	public OptimVariables getOptimVariables() {
		return getScheduler().getOptimVariables();
	}

	@Override
	@Post("?error")
	public void postErrorMessage(String message) {
		getScheduler().postErrorMessage(message);
	}

}
