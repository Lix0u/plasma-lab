/**
 * This file is part of fr.inria.plasmalab.distributed.
 *
 * fr.inria.plasmalab.distributed is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.distributed is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.distributed.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.distributed.restlet.common;

import java.io.IOException;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import fr.inria.plasmalab.distributed.algorithm.AbstractScheduler;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;


/** Implementation of common methods from InterfaceServerResource 
 */
public abstract class CommonServerResource extends ServerResource implements InterfaceServerResource {
	
	/**
	 * Register operation where a new node connect itself to the scheduler and get its UID.
	 * This method communicate the client host and port to enable callback/hearbeat.
	 * @param host
	 * @param port
	 * @return an UID for the node to identify itself.
	 */
	@Post("?register")
	public String register() {
		return getScheduler().registerNewNode();
	}
	
	/**
	 * Returns a representation of the list of initialStates.
	 * @return the list of initial states
	 * @throws IOException 
	 */
	@Get("?optimVariables")
	public OptimVariables getOptimVariables(){
		return getScheduler().getOptimVariables();
	}
	
	/**
	 * Ready operation where a node declare itself as ready to run.
	 * @param uid
	 * @return the number of run to check, 0 for wait, -1 for stop.
	 */
	@Post("?ready")
	public Order ready(String uid)  {
		return getScheduler().schedule(uid);
	}	

	/**
	 * This method post an error message aborting the experimentation.
	 * @param message
	 */
	@Post("?error")
	public void postErrorMessage(String message)  {
		getScheduler().postErrorMessage(message);
		
	}
	
	/** Get the concrete scheduler used by the server resource in this experimentation.
	 *  The scheduler can be retrieve with getApplication().getContext().getAttributes().get(nodeURI)
	 *  where nodURI is the id of the scheduler.
	 * 
	 *  @return the scheduler used by the experimentation
	 */
	protected abstract AbstractScheduler getScheduler();
	
}
