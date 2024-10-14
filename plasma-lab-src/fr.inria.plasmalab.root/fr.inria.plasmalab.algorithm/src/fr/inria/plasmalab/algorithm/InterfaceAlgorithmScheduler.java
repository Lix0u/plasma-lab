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

import java.util.List;

import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.listener.ExperimentationListener;
/**
 * This interface defines the methods published by an Algorithm or a Scheduler.
 * An Algorithm implements a non-distributed SMC algorithm.
 * A Scheduler distributes SMC experiments to distributed Workers
 * 
 * These methods are used by the {@link ExperimentationManager} launching the experimentation.
 * 
 * @author kevin.corre@inria.fr
 */
public interface InterfaceAlgorithmScheduler extends Runnable {
	/**
	 * This method returns the identifier of this algorithm plugin.
	 * @see InterfaceAlgorithmWorker
	 * @see InterfaceAlgorithmFactory
	 * @return this plugin identifier
	 */
	public String getNodeURI();
	
	/**
	 * This method set the listener of this algoritm.
	 * @see ExperimentationListener
	 * @param listener
	 */
	public void setExpListener(ExperimentationListener listener);
	
	/**
	 * This method set the list of optimization variables for this experiment. 
	 * 
	 * Each combinations of variables from this list must be checked.
	 * If the list is empty, the algorithm check only check the standard initialState.
	 * @param initialStates
	 */
	public void setOptimizationVariables(OptimVariables optimVariables);
	
	/**
	 * This method send the abort experiment message.
	 * When this message is received, the scheduler should stop working ASAP.
	 */
	public void abortScheduling();
	
	/**
	 * This method set the parameters to instantiate services.
	 * @param port which must be used by services to connect themselves to PLASMA Lab.
	 * @param nbThread is the number of thread running a service to launch.
	 * @param batch is the number of simulations to distribute to each threads.
	 * @param adfList The list of loaded {@link AbstractDataFactory}
	 * @param aafList The list of loaded {@link InterfaceAlgorithmFactory}
	 */
	public void setServices(String port, int nbThread, int batch, int frequency,
			List<AbstractDataFactory> adfList,
			List<InterfaceAlgorithmFactory> aafList);
}
