/**
 * This file is part of fr.inria.plasmalab.systemc :: systemC plugin.
 *
 * fr.inria.plasmalab.systemc :: systemC plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.systemc :: systemC plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.systemc :: systemC plugin.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.systemc.simulator;
/*
 * The systemc simulation is done by running a modified systemc kernel with an instrument version of the model.
 * The preparation of such an executable is not done within plasma-lab but by the user.
 * The simulator class here simply parse the output of this executable.
 * The content of the model is simply the path to the executable, on the first line of the text.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDeadlockException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class SystemCSimulator extends AbstractModel {
	
	// beginning of lines that correspond to states output from systemC simulator
	private static final String stateLinePrefix = "# plasma-state";
	
	//need to ba automatically computed at some point
	private static final String default_exec_number = "100";
	
	private List<InterfaceState> trace;
	private Map<String, InterfaceIdentifier> identifiers;
	
	private BufferedReader br;
	private File executable;
	//private String trace_file_path;
    private Process execution;
    private BufferedReader current_state;
    private BufferedReader read_from_simulator;
    private BufferedWriter write_to_simulator;
    private boolean deadlock;

	protected final static SystemCIdentifier TIMEID = new SystemCIdentifier("time",false);
	//protected static final SystemCIdentifier VALUEID = new SystemCIdentifier("X");
	
	private SystemCState initialState;
	
	public SystemCSimulator(String name, String content, String id){
		this.name = name;
		this.id = id;
		this.content = content;
		errors = new ArrayList<PlasmaDataException>();
		identifiers = new HashMap<String, InterfaceIdentifier>();
		trace = new ArrayList<InterfaceState>();
	}

	@Override
	public InterfaceState newPath() throws PlasmaSimulatorException {
		trace = new ArrayList<InterfaceState>();
		deadlock = false;
		//if(trace_file_path != null){
		//	File previous_trace_file = new File(trace_file_path);
		//	if(!previous_trace_file.delete()){
		//		System.err.println("Unable to delete file " + trace_file_path + ", please do it manually.");
		//	}
		//}
		//trace_file_path = executable.getParent() + "/trace" + UUID.randomUUID();
		if(launchSim(default_exec_number,"","true")) {
		  	initialState=readState();
		  	trace.add(initialState);
		} else {
			throw new PlasmaSimulatorException("Unable to start a new trace.");
		}
		return initialState;
	}

	@Override
	public InterfaceState newPath(long seed) throws PlasmaSimulatorException {
		//This method would configure the new execution run to use a given seed.
		return newPath();
	}

	@Override
	@Deprecated
	public InterfaceState newPath(List<InterfaceState> trace) throws PlasmaSimulatorException {
		// Deprecated
		return newPath();
	}

	@Override
	public void setValueOf(Map<InterfaceIdentifier, Double> update) throws PlasmaSimulatorException {
		// This method change the initial state with a set of initial values.
		for(InterfaceIdentifier id:update.keySet())
			initialState.setValueOf(id, update.get(id));
	}

	@Override
	public InterfaceState simulate() throws PlasmaSimulatorException {
		SystemCState new_state = newStateFromSimulator();
		if (new_state==null) {
			deadlock = true;
		    throw new PlasmaDeadlockException(getCurrentState(), getTraceLength());
		}
		else{
			trace.add(new_state);
		}
		return new_state;
	}

	@Override
	public void backtrack() throws PlasmaSimulatorException {
		throw new PlasmaSimulatorException("Backtrack not supported by the simulator.");
	}

	@Override
	public void cut(int stateNb) {
		// In cut we forget the state before stateNb
		for(int i=0; i<stateNb; i++)
			trace.remove(0);
	}

	@Override
	public InterfaceState getCurrentState() {
		return trace.get(trace.size()-1);
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
		return deadlock ? trace.size()-1 : -1;
	}

	@Override
	public List<InterfaceIdentifier> getStateProperties() {
		//Not supported by our simulator
		//Path property are boolean values defined in the simulator
		//These values are displayed in simulation mode
		return new ArrayList<InterfaceIdentifier>(0);
	}

	

	@Override
	public Map<String, InterfaceIdentifier> getIdentifiers() {
		return identifiers;
	}

	@Override
	public InterfaceIdentifier getTimeId() {
		return TIMEID;
	}

	@Override
	public boolean hasTime() {
		//Return true if model as a dedicated time value. 
		//Otherwise only trace length is used.
		return false;
	}

	@Override
	public InterfaceIdentifier[] getHeaders() {
		InterfaceIdentifier[] names =  new SystemCIdentifier[identifiers.size()];
		int i = 0;
		for(InterfaceIdentifier id : identifiers.values()){
			names[i]=id;
			i++;
		}
		return names;
	}

	@Override
	public boolean checkForErrors() {
		//Parse the model and return true if errors were found
		//Executed on edition panel and before a new experimentation
		//Can be used to run an initial configuration step
		
		//Empty from previous erros
		errors.clear();

		//Verify model content
		boolean trace_and_exec_defined = false;
		InputStream is = new ByteArrayInputStream(content.getBytes());
		br = new BufferedReader(new InputStreamReader(is));
	    try {
	    	trace_and_exec_defined = true;
	    	// read first line: path to systemc executable
	    	String line = br.readLine();
	    	if (line == null){ 
	    		return false; // no content -> no error
	    	}
	    	executable = new File(line); 
	    	if(!(executable.exists() || executable.canExecute())){
	    		errors.add(new PlasmaSyntaxException("The file " + line + " does not exist or is not executable."));
	    		trace_and_exec_defined = false;
	    	}
	    	//trace_file_path = executable.getParent() + "/trace_init" ;	    		    	
	    	if (errors.isEmpty() ){
	      	    trace_and_exec_defined = launchSim("1","","true");
	    	}
	    	
		} catch (IOException e) {
			errors.add(new PlasmaDataException(e));
		} catch (PlasmaSimulatorException e) {
			errors.add(new PlasmaDataException(e));
		}
	    //Configuration step
	    //We init the initial state which could have been changed by setValueOf function
	    if(trace_and_exec_defined) {
	    	//gotoNextTraceStart();
	        try {
				initialState = readState();
			} catch (PlasmaSimulatorException e) {
				errors.add(new PlasmaDataException(e));
			}
	        trace.add(initialState);
	    }
		return !errors.isEmpty();
	}
	
	private boolean launchSim(String trace_length,String trace_file,String newFile) throws PlasmaSimulatorException{
		//String[] command = {executable.getPath(), "-tl" , trace_length , "-tf" , trace_file , "-tfr" , newFile};
		String[] command = {executable.getPath()};
		//execution = new ProcessBuilder(command,params);
		//execution.directory(executable.getParentFile());
		//System.out.println(execution.command());
		try {
			String output_line;
			if(execution != null){
				// change the quit command from "quit\n" to "#plasma-quit#\n"
				write_to_simulator.write("#plasma-quit#\n");
				write_to_simulator.close();
				read_from_simulator.close();
			}
			execution = Runtime.getRuntime().exec(command);
			read_from_simulator = new BufferedReader (new InputStreamReader (execution.getInputStream()));
			write_to_simulator = new BufferedWriter (new OutputStreamWriter (execution.getOutputStream()));
			
			requestNewState();
			while((output_line = read_from_simulator.readLine()) != null){
				if(output_line.startsWith(stateLinePrefix)){
					break;
				}
			}
			if(output_line.startsWith(stateLinePrefix)){
				current_state = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(output_line.getBytes())));
				return true;
			} else {
				errors.add(new PlasmaDataException("Unable to read state from simulator."));
			}
	   /*     if(current_state !=null) current_state.close();
			File trace_f = new File(trace_file);
			if(trace_f.exists()){
				current_state = new BufferedReader(new FileReader(trace_file));
				return true;
			} else {  
				errors.add(new PlasmaException("Unable to open trace file"));
			}*/
		} catch (IOException e) {
			errors.add(new PlasmaDataException("Error when launching the SystemC executable, or trying to read trace."));
		}/* catch (InterruptedException e) {
			errors.add(new PlasmaException("SystemC execution was interrupted."));
		}*/ 
		return false;
	}

	
	/**
	 * Reads the next state from the current position in current trace.read.
	 * Returns null if there is no next state in the current trace.
	 * @return
	 * @throws PlasmaSimulatorException 
	 */
	private SystemCState readState() throws PlasmaSimulatorException{
		HashMap<InterfaceIdentifier,Double>values = new  HashMap<InterfaceIdentifier,Double>();
		int c=' ';
		try{
			// reach first parenthesis
			while(c!='(') {
				c=current_state.read();
				if(c=='\n') return null;
			}
			// and start reading further
			c=current_state.read();
			// until ) it is the val state
			String var_state = "";
			while(c!=')'){
				var_state+=(char) c;
				c=current_state.read();
			}
			// reach the ":" indicating begining of time value
			while(c!=':') {c=current_state.read();}
			// and go further
			c=current_state.read();
			String time_state = "";
			while(c!=']'){
				time_state+=(char) c;
				c=current_state.read();
			}
			fillVarValues(var_state,values);
			readTime(time_state,values);
		} catch (IOException e){
			throw new PlasmaSimulatorException("Error while reading state");
		}
		return new SystemCState(values);
	}

	
	
	/**
	 * Copy the variables assignment declared in var_state to the Map values.
	 * @param var_state
	 * @param values
	 */
	private void fillVarValues(String var_state,HashMap<InterfaceIdentifier, Double> values) {
		String[] pairs = var_state.split(",");
		for(String pair_ident_val: pairs){
			String[] ident_val = pair_ident_val.split(":");
			String ident = ident_val[0].trim();
			String val_s = ident_val[1].trim();
			InterfaceIdentifier id;
			if(identifiers.containsKey(ident)){
				id = identifiers.get(ident);
			} else {
				boolean is_boolean = val_s.equals("true") || val_s.equals("false");
				id = new SystemCIdentifier(ident,is_boolean);
				identifiers.put(ident, id);
			}
			Double val;
			if(val_s.equals("true")){
				val = new Double(1);
			} else if (val_s.equals("false")){
				val = new Double(0);
			} else {
			    val = new Double(val_s);
			}
			values.put(id, val);
		}
	}
	
	/**
	 * Read the timestamp associated to the state.
	 * Convert to ns if the time stamp has a unit.
	 * @param time_state
	 * @param values
	 * @throws PlasmaSimulatorException 
	 */
	private void readTime(String time_state,HashMap<InterfaceIdentifier, Double> values) throws PlasmaSimulatorException {
		int exponent = 0;
		if(time_state.contains("s")){
			time_state=time_state.trim();
			String[] val_unit = time_state.split(" ");
			time_state= val_unit[0];
			String unit = val_unit[1].trim();
			if(unit.equals("s")) { exponent=9;}
			else if (unit.equals("ms")) { exponent=6; }
			else if (unit.equals("us")) { exponent=3; }
			else if (unit.equals("ns")) { exponent=0; }
			else if (unit.equals("ps")) { exponent=-3; }
			else throw new PlasmaSimulatorException("Unknown time unit");
		}
		String time_val = time_state.trim();
		Double time = new Double(time_val);
		time = time * Math.pow(10,exponent);
		values.put(TIMEID, time);
		identifiers.put("time", TIMEID);
	}
	
	/**
	 * Request a new state from the systemc simulator
	 * @throws PlasmaSimulatorException 
	 */
	private void requestNewState() throws PlasmaSimulatorException{
		try {
			// change the command from "\n" to "#plasma-newstate#\n"
			write_to_simulator.write("#plasma-newstate#\n");
			write_to_simulator.flush();
		} catch (IOException e) {
			throw new PlasmaSimulatorException("Unable to send commands to the simulator.");
		}
	}
	
	/**
	 * Waits for the simulator to output a new state, parse it and return it.
	 * @throws PlasmaSimulatorException 
	 */
	private SystemCState newStateFromSimulator() throws PlasmaSimulatorException{
		requestNewState();
		try{
			String output_line;
			while((output_line = read_from_simulator.readLine()) != null){
				if(output_line.startsWith(stateLinePrefix)){
					current_state = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(output_line.getBytes())));
					return readState();
				}
			}
		} catch (IOException e) {
			throw new PlasmaSimulatorException("Error while reading state from simulator");
		}
		return null;
	}


}
