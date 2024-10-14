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
package fr.inria.plasmalab.importancesampling.server;

import org.restlet.resource.Post;

import fr.inria.plasmalab.distributed.restlet.common.InterfaceServerResource;
import fr.inria.plasmalab.importancesampling.data.CE_Result;

/** An interface only implemented by MDPIteratedServerResource  that extends InterfaceServerResource.
 *  It precises the postCEResults operation to send results of the cross entropy algorithm.
 */
public interface InterfaceCEServerRessource extends InterfaceServerResource {
		
	/**
	 * Results operation where a node sends its newly computed results.
	 * 
	 * @param result a CE_Result
	 */
	@Post("?resultsSampling")
	public void postCEResults(CE_Result result);
	
}
