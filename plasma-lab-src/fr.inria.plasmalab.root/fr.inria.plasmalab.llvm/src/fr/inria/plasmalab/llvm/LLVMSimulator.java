/**
 * This file is part of fr.inria.plasmalab.llvm :: LLVM plugin.
 *
 * fr.inria.plasmalab.llvm :: LLVM plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.llvm :: LLVM plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.llvm :: LLVM plugin.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.llvm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.workflow.concrete.GenericIdentifier;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDeadlockException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class LLVMSimulator extends AbstractModel {
	
	final static Logger logger = LoggerFactory.getLogger(LLVMSimulator.class);
	
	protected final static String identifiers_command = "identifiers"; 
	protected final static String quit_command = "quit"; 
	protected final static String newsim_command = "newsim"; 
	protected final static String simulate_command = "rnd"; 
	protected final static String loadstate_command = "loadState"; 
	protected final static String getvalue_command = "getValue"; 

	/** Path to the llvm simulator + options. */
	private String executable;
	/** Path to the llvm model. */
	private File model;
	/** Process that executes the llvm simulator. */
    private Process execution;
    /** Messages received from the llvm simulator. */
    private BufferedReader read_from_simulator;
    /** Commands send to the llvm simulator. */
    private BufferedWriter write_to_simulator;
    
    /** Identifier of the state */
    protected InterfaceIdentifier statetag;
    /** List of all the variables of the llvm code (+ the statetag) */
    private Map<String, InterfaceIdentifier> identifiers;
    /** Current state of the LLVM simulator */
	private LLVMState currentState;
	/** True if the LLVM simulator was started at the initial state using the newsim command.
	 *  False if it has been started at a random state using loadState  
	 */
	private boolean simulatorStartedFromInitialState;
	/** List of states of the current simulation */
	private List<InterfaceState> trace;
	/** -1 if no deadlock.
	 *  Size of the trace otherwise.
	 */
	private int deadlockPos;
	
    public LLVMSimulator(String name, String content, String id) {
		this.name = name;
		this.id = id;
		this.content = content;
		this.origin = null;
		this.execution = null;
		this.statetag = new GenericIdentifier("statetag");
		this.errors = new ArrayList<PlasmaDataException>();
	}
	
	public LLVMSimulator(String name, File file, String id) throws PlasmaDataException {
		this.name = name;
		this.id = id;
		this.content = "";
		this.origin = file;
		this.execution = null;
		this.statetag = new GenericIdentifier("statetag");
		
		try {
			FileReader fr = new FileReader(file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				content = content+br.readLine()+"\n";
			br.close();
		} catch (FileNotFoundException e) {
			throw new PlasmaDataException(e);
		} catch (IOException e) {
			throw new PlasmaDataException(e);
		}  
		this.errors = new ArrayList<PlasmaDataException>();
	}
	
	/** Check for errors and initialize the simulator:
	 *  - get the simulator path
	 *  - get the model path and check it
	 *  - launch the simulator
	 */
	@Override
	public boolean checkForErrors() {
		errors.clear();

		//Verify model content
		InputStream is = new ByteArrayInputStream(content.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		check: {
			try {
				// read first line: path to go executable
		    	executable = br.readLine();
		    	if (executable == null) {
		    		errors.add(new PlasmaSyntaxException("Empty model: no path to llvm simulator"));
		    		break check;
		    	}
		    	logger.debug("Executable command: " + executable);
		    	String line = br.readLine();
		    	if (line == null) {
			    	errors.add(new PlasmaSyntaxException("Incomplete model: no path to llvm model"));
			    	break check; 
		    	}	
		    	model = new File(line);
		    	if (!model.exists()) {
			    	errors.add(new PlasmaSyntaxException("llvm model not found at " + model.getPath()));
		    		break check;
		    	}
		    	logger.debug("Model path: " + line);
		    	launchSimulator();
			} catch (IOException e) {
				errors.add(new PlasmaDataException(e));
			} catch (PlasmaSimulatorException e) {
				errors.add(new PlasmaDataException(e));
			}
		}
		if (!errors.isEmpty() && execution != null) {
			execution.destroy();
			execution =  null;
		}
		this.simulatorStartedFromInitialState = true;
		return !errors.isEmpty();
	}
	
	/** Start a new simulation, either from the initial state y sending the newsim commmand,
	 *  or from a state previously loaded.
	 */
	@Override
	public InterfaceState newPath() throws PlasmaSimulatorException {
		if (simulatorStartedFromInitialState) {
			logger.debug("Sending newsim command to the llvm simulator");
			sendCommand(newsim_command);
			// read initial state
			long statevalue = readState();
			currentState = new LLVMState(statevalue, this);
		}
		trace = new ArrayList<InterfaceState>();
		trace.add(currentState);
		return currentState;
	}

	@Override
	public InterfaceState newPath(List<InterfaceState> initTrace) throws PlasmaSimulatorException {
		return newPath();
	}

	@Override
	public InterfaceState newPath(long seed) throws PlasmaSimulatorException {
		return newPath();
	}

	/** Simulate a new state form the current state. */
	@Override
	public InterfaceState simulate() throws PlasmaSimulatorException {
		if (deadlockPos<0) {
			logger.debug("Sending simulate command to the llvm simulator");
			sendCommand(simulate_command);
					
			// receive state
			long statevalue = readState();
			currentState = new LLVMState(statevalue, this);
			trace.add(currentState);
		}
		if (deadlockPos>=0)
			throw new PlasmaDeadlockException(currentState, trace.size());
		return currentState;
	}

	@Override
	public void backtrack() throws PlasmaSimulatorException {
		throw new PlasmaSimulatorException("Backtrack is not supported by the LLVM simulator.");
	}

	@Override
	public void cut(int stateNb) throws PlasmaSimulatorException {
		throw new PlasmaSimulatorException("Cut is not supported by the LLVM simulator.");
	}

	/** Load a previous state. It will be the current state of the simulator for the next simulations */
	@Override
	public void setValueOf(Map<InterfaceIdentifier, Double> update) throws PlasmaSimulatorException {
		if (!update.containsKey(statetag))
			throw new PlasmaSimulatorException("LLVMSimulator cannot update without a statetag identifier");
		long statevalue = update.get(statetag).longValue();
		loadState(statevalue);
		readState();
		simulatorStartedFromInitialState = false;
		currentState = new LLVMState(statevalue, this);
	}
	
	@Override
	public Map<String, InterfaceIdentifier> getIdentifiers() {
		return identifiers;
	}
	
	@Override
	public InterfaceIdentifier[] getHeaders() {
		InterfaceIdentifier[] ret = new InterfaceIdentifier[identifiers.size()];
		int index=0;
		for (InterfaceIdentifier val : identifiers.values()) {
			ret[index] = val;
			index++;
		}
		Arrays.sort(ret);
		return ret;
	}


	@Override
	public InterfaceState getCurrentState() {
		return currentState;
	}

	@Override
	public InterfaceState getStateAtPos(int pos) {
		return trace.get(pos);
	}

	@Override
	public List<InterfaceState> getTrace() {
		return trace;
	}

	@Override
	public int getDeadlockPos() {
		return deadlockPos;
	}


	@Override
	public InterfaceIdentifier getTimeId() {
		return null;
	}

	@Override
	public boolean hasTime() {
		return false;
	}

	@Override
	public List<InterfaceIdentifier> getStateProperties() {
		return new ArrayList<InterfaceIdentifier>(0);
	}


	// Private methods *********************************************************************************** 

	/** Launch the LLVM simulator 
	 *  - execute the command
	 *  - initialize the buffers
	 *  - read the first lines output by the simulator
	 *  - ask the simulator for the identifiers
	 * 
	 * @throws PlasmaSimulatorException
	 */
	private void launchSimulator() throws PlasmaSimulatorException {
    	String commandString = executable +  " " + model.getPath();
		String[] command = commandString.split(" ");
		logger.debug("Launching llvm simulator");
		try {
			if(execution != null)
				stopSimulator();
			ProcessBuilder builder = new ProcessBuilder(command);
			builder.redirectErrorStream(true);
			execution = builder.start();
			read_from_simulator = new BufferedReader (new InputStreamReader (execution.getInputStream()));
			write_to_simulator = new BufferedWriter (new OutputStreamWriter(execution.getOutputStream()));
			
			String output_line = readLineFromSimulator();
			while(!output_line.isEmpty())
				output_line = readLineFromSimulator();
		} catch (IOException e) {
			throw new PlasmaSimulatorException("Error when starting the LLVM simulator", e);
		}
		
		queryIdentifiers();
	}
	
	/** Ask the simulator for the identifiers of the model.
	 *  These are all the variables of the model.
	 *  
	 * @throws PlasmaSimulatorException
	 */
	private void queryIdentifiers() throws PlasmaSimulatorException {
		logger.debug("Query the model's identifiers");
		identifiers = new HashMap<String, InterfaceIdentifier>();
		identifiers.put(statetag.getName(), statetag);		
		sendCommand(identifiers_command);
		
		// receive identifiers
		String output_line = readLineFromSimulator();
		if (!output_line.equals("VariableNames:"))
			throw new PlasmaSimulatorException("Error while sending \"" + identifiers_command + "\" command to the simulator.");
		output_line = readLineFromSimulator();
		while(!output_line.equals("/VariableNames")) {
			String varName = output_line.split(" ")[1];
			GenericIdentifier id = new GenericIdentifier(varName);
			identifiers.put(varName, id);
			logger.debug("New identifier: " + varName);
			output_line = readLineFromSimulator();
		}
	}

	/** Terminate the simuator */
	private void stopSimulator() throws PlasmaSimulatorException {
		logger.debug("Stopping llvm simulator");
		try {	
			if(execution != null){
				write_to_simulator.write(quit_command + "\n");
				write_to_simulator.close();
				read_from_simulator.close();
			}
		}
		catch (IOException e) {
			throw new PlasmaSimulatorException("Error when stopping the LLVM simulator", e);
		}
	}
	
	/** Read a state output by the simulator.
	 *  Only extract the statetag and the number of transitions.
	 */
	protected long readState() throws PlasmaSimulatorException {
		logger.debug("Read state");
		long statevalue = 0;
		String output_line = readLineFromSimulator();
		if (!output_line.equals("ConcreteState:"))
			throw new PlasmaSimulatorException("Error while reading state from the simulator: no \"ConcreteState:\"");
		
		// read state tag
		output_line = readLineFromSimulator();
		if (!output_line.startsWith("Statetag:"))
			throw new PlasmaSimulatorException("Error while reading state from the simulator: no \"Statetag:\"");
		try {
			statevalue = Long.parseLong(output_line.split(" ")[1]);
		}
		catch(NumberFormatException e) {
			throw new PlasmaSimulatorException("Cannot parse statetag", e);
		}
		
		// read the rest of the state to empty the bufferedreader
		output_line = readLineFromSimulator();
		while (!output_line.startsWith("Transitions")) {
			output_line = readLineFromSimulator();
		}
		
		// read the number of transitions
		int nbTransition = 0;
		try {
			nbTransition = Integer.parseInt(output_line.split(" ")[1]);
		}
		catch(NumberFormatException e) {
			throw new PlasmaSimulatorException("NumberFormatException", e);
		}
		
		// read last line
		output_line = readLineFromSimulator();
		if (!output_line.equals("/ConcreteState"))
			throw new PlasmaSimulatorException("Error while reading state from the simulator: no \"/ConcreteState\"");
		
		if (nbTransition == 0)	// deadlock
			deadlockPos = trace.size()-1;
		else
			deadlockPos = -1;
		return statevalue;
	}
	
	/** Read a state output by the simulator.
	 *  Extract all the values and store them in the map.
	 */
	@Deprecated
	protected  void readState(HashMap<InterfaceIdentifier, Double> values) throws PlasmaSimulatorException {
		logger.debug("Read state");
		String output_line = readLineFromSimulator();
		if (!output_line.equals("ConcreteState:"))
			throw new PlasmaSimulatorException("Error while reading state from the simulator: no \"ConcreteState:\"");
		
		// read state tag
		output_line = readLineFromSimulator();
		if (!output_line.startsWith("Statetag:"))
			throw new PlasmaSimulatorException("Error while reading state from the simulator: no \"Statetag:\"");
		values.put(statetag, Double.parseDouble(output_line.split(" ")[1]));
		
		// read globals
		output_line = readLineFromSimulator();
		if (!output_line.equals("Globals:"))
			throw new PlasmaSimulatorException("Error while reading state from the simulator: no \"Globals:\"");
		output_line = readLineFromSimulator();
		while(!output_line.equals("/Globals")) {
			String[] v = output_line.split(" ");
			InterfaceIdentifier id = identifiers.get(v[2]);
			if (id == null)
				throw new PlasmaSimulatorException("Error while reading state: unknown identifier " + v[2]);
			try {
				Double value = Double.parseDouble(v[3]);
				values.put(id, value);
			} catch (NumberFormatException e) {
				throw new PlasmaSimulatorException("Error while reading state", e);
			}
			output_line = readLineFromSimulator();
		}
		
		// read processes
		output_line = readLineFromSimulator();
		while (!output_line.startsWith("Transitions")) {
			if (!output_line.startsWith("Process"))
				throw new PlasmaSimulatorException("Error while reading state from the simulator: no \"Process\"");
			output_line = readLineFromSimulator();
			while (!output_line.equals("/Process")) {
				String[] v = output_line.split(" ");
				InterfaceIdentifier id = identifiers.get(v[2]);
				if (id == null)
					throw new PlasmaSimulatorException("Error while reading state: unknown identifier " + v[2]);
				try {
					Double value = Double.parseDouble(v[3]);
					values.put(id, value);
				} catch (NumberFormatException e) {
					throw new PlasmaSimulatorException("Error while reading state", e);
				}
				output_line = readLineFromSimulator();
			}
			output_line = readLineFromSimulator();
		}
		
		// read the number of transitions
		int nbTransition = 0;
		try {
			nbTransition = Integer.parseInt(output_line.split(" ")[1]);
		}
		catch(NumberFormatException e) {
			throw new PlasmaSimulatorException(e);
		}
				
		// read last line
		output_line = readLineFromSimulator();
		if (!output_line.equals("/ConcreteState"))
			throw new PlasmaSimulatorException("Error while reading state from the simulator: no \"/ConcreteState\"");
		
		if (nbTransition == 0)	// deadlock
			deadlockPos = trace.size()-1;
		else
			deadlockPos = -1;
	}
	
	/** Read a single line from the simulator's output
	 * 
	 * @return the String corresponding to the line read.
	 * @throws PlasmaSimulatorException
	 */
	protected String readLineFromSimulator() throws PlasmaSimulatorException {
		String output_line;
		try {
			output_line = read_from_simulator.readLine();
		} catch (IOException e) {
			throw new PlasmaSimulatorException("Error while reading output from the simulator", e);
		}
		if (output_line == null)
			throw new PlasmaSimulatorException("Error while reading output from the simulator: no output line");
		logger.debug("Read output line: " + output_line);
		return output_line;
	}
	
	/** Send a command to the simulator */
	protected void sendCommand(String command) throws PlasmaSimulatorException {
		logger.debug("Send command: " + command);
		try {
			write_to_simulator.write(command + "\n");
			write_to_simulator.flush();
		} catch (IOException e) {
			throw new PlasmaSimulatorException("Unable to send commands to the simulator", e);
		}
	}
	
	/** Load a previous state */
	protected void loadState(long statevalue) throws PlasmaSimulatorException {
		sendCommand(loadstate_command);
		sendCommand(Long.toString(statevalue));
	}
}
