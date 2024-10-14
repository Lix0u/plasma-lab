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
package fr.inria.plasmalab.montecarlo.server;

import org.restlet.resource.Post;

import fr.inria.plasmalab.distributed.algorithm.AbstractScheduler;
import fr.inria.plasmalab.distributed.restlet.common.CommonServerResource;
import fr.inria.plasmalab.montecarlo.data.MontecarloResult;

/** Implementation of the server resource for the distributed Monte Carlo algorithm 
 */
public class MontecarloServerResource extends CommonServerResource implements InterfaceMCServerResource {

	@Override
	@Post("?results")
	public void postResults(MontecarloResult result) {
		getScheduler().putResult(result);
	}
	
	@Override
	protected AbstractScheduler getScheduler(){
		return (AbstractScheduler)getApplication().getContext().getAttributes().get("montecarlo");
	}
	
}
