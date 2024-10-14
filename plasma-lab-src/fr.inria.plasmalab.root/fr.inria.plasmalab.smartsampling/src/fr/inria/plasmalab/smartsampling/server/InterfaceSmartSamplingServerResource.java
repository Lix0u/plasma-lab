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
package fr.inria.plasmalab.smartsampling.server;

import org.restlet.resource.Post;

import fr.inria.plasmalab.distributed.restlet.common.InterfaceServerResource;
import fr.inria.plasmalab.smartsampling.data.IteratedMDPResult;

/** An interface only implemented by SmartSamplingServerResource  that extends InterfaceServerResource.
 *  It precises the postIResults operation to send results of the smart sampling mdp algorithm.
 */
public interface InterfaceSmartSamplingServerResource extends InterfaceServerResource {
	
	/**
	 * Results operation where a node sends its newly computed results.
	 * This method sends a IteratedMDPResult type of result.
	 * 
	 * @param result an IteratedMDPResult
	 */
	@Post("?resultsIterated")
	public void postIResults(IteratedMDPResult result);
}
