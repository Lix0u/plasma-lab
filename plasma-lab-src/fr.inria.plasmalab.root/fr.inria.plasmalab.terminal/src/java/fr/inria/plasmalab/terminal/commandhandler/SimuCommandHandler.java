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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.spi.JoranException;
import fr.inria.plasmalab.terminal.command.SimuCommand;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDeadlockException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaPluginNotFound;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public class SimuCommandHandler extends CommonCommandHandler {

	/** The logger. */
	final static Logger log_ = LoggerFactory.getLogger(SimuCommandHandler.class);
	
	/** The local command.*/
	private SimuCommand simuCommand_;
	
	private AbstractModel model;
	private List<AbstractRequirement> abstractRequirements;
	private List<String> headers;
	private int stepNb;
	
	/**
     * @param command     The parsed command
     * TODO abstract the result of the parsing.
	 * @throws JoranException 
     */
    public SimuCommandHandler(SimuCommand command) throws JoranException {
    	super(command);
    	simuCommand_ = command;
    }

	/**
	 * Dispatches the client command.
	 * 
	 * @throws InterruptedException 
	 * 
	 */
	public void dispatchCommand() {
		log_.debug("dispatching simulation command");

		// Abstract model creation.
		// We create a new model by retrieving the appropriate model factory.
		String modelType = simuCommand_.getModel().getType();
		String modelFile = simuCommand_.getModel().getFile();
		try {
			this.model = controler_.createModel(modelType, modelFile);
		} catch (PlasmaPluginNotFound e) {
			System.err.println("Missing plugin for model ID: " + modelType + ". Check models ID list:");
			System.err.format(format3);
			printModelsInfo(System.err);
			return;
		} catch (PlasmaDataException e) {
			log_.error("Error while loading model: " + e.getMessage() + ". Abort.");
			return;
		}
		if(model.checkForErrors()){
			for(PlasmaDataException err:model.getErrors())
				log_.error(err.getMessage());
			log_.error("Error(s) while parsing model. Abort.");
			return;
		}

		// Requirements creation
		// We create a new requirement by retrieving the appropriate requirement
		// factory.
		abstractRequirements = controler_.createRequirements(simuCommand_.getRequirements());
		List<AbstractRequirement> newRequirements = new ArrayList<AbstractRequirement>(abstractRequirements.size());
		for(AbstractRequirement req: abstractRequirements){
			req.setModel(model);
			if(!req.checkForErrors())
				newRequirements.add(req);
			else {	
				for(PlasmaDataException err:model.getErrors())
					log_.error(err.getMessage());
				log_.error("Error(s) while parsing requirement: " + req.getName() + ". Skipped.");
			}
		}
		this.abstractRequirements = newRequirements;		

	
		//Instantiate requirements with RequirementVariables
		newRequirements = new ArrayList<AbstractRequirement>();
		for(AbstractRequirement req: abstractRequirements){
			try {
				List<AbstractRequirement>  instances = req.generateInstantiatedRequirement();
				if(instances==null)
					newRequirements.add(req);
				else if(instances.size()==0)
					newRequirements.add(req);
				else
					newRequirements.addAll(instances);
			} catch (PlasmaDataException e) {
				log_.error("Cannot instantiate requirement: " + e.getMessage() + ". Skipped");
			}
			
		}
		//Replace original requirement with newly instantiated requirements
		this.abstractRequirements = newRequirements;		
		
		System.out.println("Simulation started.");
		System.out.println("Enter step number (default is 1), r to restart and q to quit, press enter.");
		
		// configure headers
		if (!simuCommand_.getHeaders().isEmpty())
			headers = simuCommand_.getHeaders();
		else {
			InterfaceIdentifier[] modelHeaders = model.getHeaders();
			headers = new ArrayList<String>(modelHeaders.length);
			for (InterfaceIdentifier id : modelHeaders)
				headers.add(id.getName());
		}
		
		try {
			// newpath
			newPath();
			
			// readlines
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			int step = 1;
			while (true) {
				String line = reader.readLine();
				if (!line.isEmpty()) {
					if (line.equals("r")) {
						newPath();
						continue;
					}
					if (line.equals("q"))
						break;
					// else parse step number
					step = Integer.parseInt(line);
				}
				performAction(step);
			}
		} catch (PlasmaSimulatorException e) {
			log_.error("Simulation error: " + e.getMessage());
		} catch (IOException e) {
			log_.error("IOException: " + e.getMessage());
		} catch (NumberFormatException e) {
			log_.error("NumberFormatException: " + e.getMessage());
		}
	}
	
	private void newPath() throws PlasmaSimulatorException {
		model.newPath();
		stepNb = 0;		
		printHeaderName();
		drawLine(model.getCurrentState());
	}
	
	private void performAction(int step) throws PlasmaSimulatorException {
		if (step > 0) {
			for (int i = 1; i <= step; i++) {
				if (!simulateAction())
					break;
			}
		}
		else if (step < 0) {
			for (int i = 1; i <= -1*step; i++) {
				if (!backtrackAction())
					break;
			}
		}
	}
	
	private boolean simulateAction() throws PlasmaSimulatorException {
		try {
			model.simulate();
		} catch (PlasmaDeadlockException e) {
			System.out.println(e.getMessage());
			return false;
		}
		stepNb++;
		drawLine(model.getCurrentState());
		return true;
	}
	
	private boolean backtrackAction() throws PlasmaSimulatorException {
		model.backtrack();
		stepNb--;
		drawLine(model.getCurrentState());
		return model.getTraceLength() > 1;
	}
	
	private void printHeaderName(){
		String ret = "#   ";
		for(String h : headers) {
			if (!model.getIdentifiers().containsKey(h))
				log_.warn("No identifier found in the model for header: " + h);
			ret +=  String.format("%-4s ", h);
		}		
		for (AbstractRequirement req : abstractRequirements) {
			ret +=  String.format("%-4s ", req.getName());
		}
		
		System.out.println(ret);
	}
	
	private void drawLine(InterfaceState state){
		String ret = String.format("%-4s", stepNb);
		int length = 0;
		for(String h : headers) {
			length = Math.max(h.length(),4); 
			Double val;
			try {
				val = state.getValueOf(h);
			} catch (PlasmaSimulatorException e) {
				log_.error("Simulator error: " + e.getMessage());
				val = Double.NaN;
			}
			int intVal = val.intValue();
			if (val == intVal)
				ret += String.format("%-" + length + "s ", intVal);
			else
				ret += String.format("%-" + length + "s ", val.toString());
		}
				
		for (AbstractRequirement req : abstractRequirements) {
			Double value;
			try {
				value = req.check(stepNb,model.getStateAtPos(0));
			} catch (PlasmaCheckerException e) {
				log_.error("Checker error: " + e.getMessage());
				value = Double.NaN;
			}
			length = Math.max(req.getName().length(),4); 
			ret += String.format("%-" + length + "s ", value.toString());
		}
		
		System.out.println(ret);
	}
}
