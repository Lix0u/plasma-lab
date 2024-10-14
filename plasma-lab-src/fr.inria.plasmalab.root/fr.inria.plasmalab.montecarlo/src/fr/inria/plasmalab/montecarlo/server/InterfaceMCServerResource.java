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

import fr.inria.plasmalab.distributed.restlet.common.InterfaceServerResource;
import fr.inria.plasmalab.montecarlo.data.MontecarloResult;

/** An interface only implemented by MontecarloServerResource that extends InterfaceServerResource.
 *  It precises the postResults operation.
 */
public interface InterfaceMCServerResource extends InterfaceServerResource {

	/**
	 * Results operation where a node sends its newly computed results.
	 * 
	 * @param result the Monte Carlo result that contains a total checked value and a total positive value .
	 */
	@Post("?results")
	public void postResults(MontecarloResult result);
}
