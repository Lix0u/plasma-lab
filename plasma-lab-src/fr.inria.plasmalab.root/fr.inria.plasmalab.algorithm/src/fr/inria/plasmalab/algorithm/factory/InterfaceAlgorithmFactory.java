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
package fr.inria.plasmalab.algorithm.factory;

import java.util.List;
import java.util.Map;

import net.xeoh.plugins.base.Plugin;
import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.InterfaceAlgorithmWorker;
import fr.inria.plasmalab.algorithm.data.SMCParameter;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
/**
 * This interface defines the method published by an Algorithm factory. 
 * 
 * A Factory is loaded as a plugin and can instantiate Scheduler and Worker.
 * 
 * @see InterfaceAlgorithmScheduler
 * @see InterfaceAlgorithmWorker
 *  
 * @author kevin.corre@inria.fr
 */
public interface InterfaceAlgorithmFactory extends Plugin {
	/**
	 * toString is used in the GUI of PLASMA Lab to display the usage name 
	 * of a plugin.
	 * @return the usage name of this plugin.
	 */
	public String toString();
	/**
	 * 
	 * @return the usage name of this plugin.
	 */
	public String getName();
	/**
	 * 
	 * @return a description of this plugin.
	 */
	public String getDescription();
	/**
	 * This method returns the identifier of this algorithm plugin.
	 * @see InterfaceAlgorithmWorker
	 * @see InterfaceAlgorithmFactory
	 * @return this plugin identifier
	 */
	public String getId();
	
	/**
	 * This method returns true if this algorithm offer a distributed procedure.
	 * @return if the algorithm can be distributed
	 */
	public boolean isDistributed();
	
	public List<SMCParameter> getParametersList();
	
	/**
	 * This method fills a map with parameters name as keys and parameters values as object.
	 * 
	 * We assume that if an SMCOption is false, parameters from this option are absent from the parameters array. 
	 * 
	 * @param parametersMap the map to fill 
	 * @param parameters an array of parameters. Those parameters are given in the same order as the order 
	 * given by the getParametersList method. 
	 */
	public void fillParametersMap(Map<String, Object> parametersMap, String[] parameters) throws PlasmaParameterException;
	
	/**
	 * This method instantiate a new Worker.
	 * @param model
	 * @param requirements
	 * @return a new Worker.
	 */
	public InterfaceAlgorithmWorker createWorker(AbstractModel model, List<AbstractRequirement> requirements) throws PlasmaParameterException;
	
	/**
	 * This method instantiate a new Scheduler.
	 * @param model
	 * @param requirements
	 * @param parameters
	 * @return a new Scheduler
	 * @throws PlasmaParameterException
	 */
	public InterfaceAlgorithmScheduler createScheduler(AbstractModel model,
			List<AbstractRequirement> requirements, Map<String, Object> parameters) throws PlasmaParameterException;
	
	/**
	 * This method returns the class of the ServerResource working with the algorithm.
	 * Return null if the algorithm doesn't support distributed mode. 
	 * @return "Class<? extends ServerResource>"
	 */
	public Class<?> getResourceHandler();
}
