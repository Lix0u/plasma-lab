/**
 * This file is part of fr.inria.plasmalab.algorithm.
 *
 * fr.inria.plasmalab.algorithm is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.algorithm is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.algorithm.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.algorithm;

import org.restlet.Client;

/**
 * This interface defines the method published by a Worker. 
 * 
 * A Worker is a part of PLASMA Lab algorithm architecture. This architecture is
 * composed of two different type of objects, Scheduler(local) and Worker(distributed)
 * communicating together. For more informations see PLASMA Lab web sites.
 * 
 * @author kevin.corre@inria.fr
 */
public interface InterfaceAlgorithmWorker {

	/**
	 * This methods is used by the worker to connect itself to the Scheduler.
	 * @param c Http client
	 * @param host address of the scheduler.
	 * @param port at which the scheduler is listening to. 
	 * @param uri id of the scheduler. 
	 * @see InterfaceAlgorithmScheduler.getNodeURI
	 */
	public String connect(Client c, String host, String port, String uri);
	
	/** 
	 * This methods start the worker 
	 */
	public void start();

}
