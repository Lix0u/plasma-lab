/**
 * This file is part of fr.inria.plasmalab.terminal.
 *
 * fr.inria.plasmalab.terminal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.terminal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.terminal.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.terminal.commandhandler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.spi.JoranException;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.controler.ExperimentationManager;
import fr.inria.plasmalab.terminal.command.LaunchCommand;
import fr.inria.plasmalab.terminal.outputlistener.OutputListenerFactory;
import fr.inria.plasmalab.terminal.outputlistener.api.OutputListener;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaPluginNotFound;

public class LaunchCommandHandler extends CommonCommandHandler {

	/** The logger. */
	final static Logger log_ = LoggerFactory.getLogger(LaunchCommandHandler.class);
	
	/**
	 * Listener. TODO: abstract it to manage different type of listener.
	 **/
	OutputListener listener_;
	
	/** The local command.*/
	private LaunchCommand launchCommand_;


	/**
     * @param plasmaCommand     The parsed command
     * TODO abstract the result of the parsing.
	 * @throws JoranException 
     */
    public LaunchCommandHandler(LaunchCommand command) throws JoranException {
    	super(command);
    	launchCommand_ = command;
    	listener_ = OutputListenerFactory.createOutputListener(
               launchCommand_.getOutput(), 
               launchCommand_.getFormat(),
               launchCommand_.isProgress(),
               launchCommand_.getHeaders());
    }

	/**
	 * Dispatches the client command.
	 * 
	 * @throws InterruptedException 
	 * 
	 */
	public void dispatchCommand() {
		log_.debug("dispatching launch command");
		InterfaceAlgorithmFactory interfaceAlgorithm = getInterfaceAlgorithm(launchCommand_.getAlgorithm());
		if (interfaceAlgorithm == null) {
			System.err.println("Wrong algorithm ID: " + launchCommand_.getAlgorithm() + ". Check algorithms ID list:");
			System.err.format(format3);
			printAlgorithmsInfo(System.err);
			return;
		}
		
		// Abstract model creation.
		// We create a new model by retrieving the appropriate model factory.
		String modelType = launchCommand_.getModel().getType();
		String modelFile = launchCommand_.getModel().getFile();
		AbstractModel model;
		try {
			model = controler_.createModel(modelType, modelFile);
		} catch (PlasmaPluginNotFound e) {
			System.err.println("Missing plugin for model ID: " + modelType + ". Check models ID list:");
			System.err.format(format3);
			printModelsInfo(System.err);
			return;
		} catch (PlasmaDataException e) {
			log_.error("Error while loading model: " + e.getMessage() + ". Abort.");
			return;
		}

		// Requirements creation
		// We create a new requirement by retrieving the appropriate requirement
		// factory.
		List<AbstractRequirement> abstractRequirements = controler_.createRequirements(launchCommand_.getRequirements());
		if (abstractRequirements.isEmpty()) {
			System.err.println("No requirement built. Check requirements ID list:");
			System.err.format(format3);
			printRequirementsInfo(System.err);
			return;
		}

		// Experimentation manager creation
		// retrieve extra parameters.
		int port = launchCommand_.getPort();
		int batch = launchCommand_.getBatch();
		int frequency = launchCommand_.getFrequency();
		int jobs = launchCommand_.getJobs();
		// copy paste from what os done in the GUI
		
		//Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> params = launchCommand_.getAlgorithmParametersWeak();
		params .put("distributed", jobs != 1);

		try {
			ExperimentationManager exp = ExperimentationManager.createExperimentationManager(controler_);
			exp.addExperimentationListener(listener_);
			log_.info("Starting experiment with " + interfaceAlgorithm.getName());
			exp.setupAnExperiment(interfaceAlgorithm, model, abstractRequirements, port, new OptimVariables(), jobs, batch, frequency, params);
			exp.startExperimentSync();
		} catch(PlasmaParameterException e){
			log_.error(e.getMessage());
			printSingleAlgorithmInfo(interfaceAlgorithm,System.err);
		} catch(PlasmaDataException e){
			log_.error(e.getMessage());
		} catch (InterruptedException e) {
			log_.error(e.getMessage());
		}
			
		log_.info("Closing the experiment");
	}
}
