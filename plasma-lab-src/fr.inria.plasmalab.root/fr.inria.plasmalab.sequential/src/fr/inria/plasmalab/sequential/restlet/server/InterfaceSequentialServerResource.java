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

import org.restlet.resource.Post;

import fr.inria.plasmalab.distributed.restlet.common.InterfaceServerResource;
import fr.inria.plasmalab.montecarlo.data.MontecarloResult;

/** An interface only implemented by SequentialServerResource */
public interface InterfaceSequentialServerResource extends InterfaceServerResource {
	
	@Post("?results")
	public void postResults(MontecarloResult result);
}
