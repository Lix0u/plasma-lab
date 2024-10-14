/**
 * This file is part of fr.inria.plasmalab.matlab.
 *
 * fr.inria.plasmalab.matlab is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.matlab is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.matlab.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.matlab;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import fr.inria.plasmalab.matlab.components.SimulinkOptimVariable;
import fr.inria.plasmalab.matlab.components.SimulinkVariableConstraint;
import fr.inria.plasmalab.matlab.parser.SimulinkParser;
import fr.inria.plasmalab.workflow.concrete.Variable;
import fr.inria.plasmalab.workflow.concrete.VariableConstraint;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;

public class MatLabSessionModel extends AbstractModel {
	private static Logger logger = Logger.getLogger(MatLabSessionModel.class);

	// MATLAB COMMUNICATION
	private MatlabProxy proxy;
	private static final String plasmaOutput = "plasma_simout";
	private static final String logOutputName = "log_output";
	private static final String simParameters = " 'SignalLogging', 'on',"
		    		+ " 'SignalLoggingName', '"+logOutputName+"'";
	private static final String getLogOutputCmd = plasmaOutput+".get('"+logOutputName+"')";
	private static final String setSaveStateCmd = "set_param(idSML, 'SaveFinalState', 'on',"
			+ " 'FinalStateName', 'last_state')";
	private static final String unsetSaveStateCmd = "set_param(idSML, 'SaveFinalState', 'off')";
	private static final String setLoadStateCmd = "set_param(idSML, 'LoadInitialState', 'on',"
			+ " 'InitialState', 'last_state')";
	private static final String unsetLoadStateCmd = "set_param(idSML, 'LoadInitialState', 'off')";
	private static final String updateLastStateCmd = "last_state = "+plasmaOutput+".get('last_state')";
	private static final String getStopTimeCmd = "get_param(idSML, 'StopTime')";
	private static final String setIDSMLCmd = "idSML = ";
	
	private String pathSML;
	private String fileSML;
	
	// MAPPING STRING -> IIdentifier
	private Map<String, InterfaceIdentifier> identifiers;
	// LIST OPTIM
	private List<SimulinkOptimVariable> optimIdentifiers;
	private List<SimulinkVariableConstraint> optimConstraints;
	// MAPPING IIdentifier -> values
	private Map<InterfaceIdentifier, double[]> values;
	// TIME Identifier
	private final InterfaceIdentifier TIME = new MatLabIdentifier("Time", -1);
	// Simulation stop time of the simulink model
	private int simStopTime;
	// Total current simulation time
	private int simTimeLength;
	// Latest time value;
	private double simLatestTime;
	// Total current number of state
	// Simulation size
	private int simulationSize, currentSimPos;
	// Execution trace
	private List<InterfaceState> trace;
	
	public MatLabSessionModel(String name, File file, MatlabProxy proxy, String id) {
		this.name = name;
		this.id = id;
		this.proxy = proxy;
		errors = new ArrayList<PlasmaDataException>();
		identifiers = new HashMap<String, InterfaceIdentifier>();
		values = new HashMap<InterfaceIdentifier, double[]>();
		content = file.getAbsolutePath();
	}
	public MatLabSessionModel(String name, String content, MatlabProxy proxy, String id) {
		this.name = name;
		this.id = id;
		this.proxy = proxy;
		this.content = content;
		errors = new ArrayList<PlasmaDataException>();
		identifiers = new HashMap<String, InterfaceIdentifier>();
		values = new HashMap<InterfaceIdentifier, double[]>();
	}
	@Override
	public InterfaceState newPath() throws PlasmaSimulatorException {
		//Initialize 
		currentSimPos = -1;
		simTimeLength = 0;
		simLatestTime = 0;
		//Empty trace
		trace = new ArrayList<InterfaceState>();
		//Empty values from previous simulation
		for(InterfaceIdentifier id:values.keySet())
			values.put(id, null);
		try{
			//Get the sim stop time set by the user
			simStopTime = Integer.parseInt(
					(String)proxy.returningEval(getStopTimeCmd,1)[0]);
			//Don't load init state on first state
			proxy.eval(unsetLoadStateCmd);
			//Save last state
			proxy.eval(setSaveStateCmd);
			//Do first simulation run
			progressSimulinkSimulation();
		}catch(MatlabInvocationException e){
			throw new PlasmaSimulatorException(e);
		}
		//Play first simulation step
		simulate();
		
		return trace.get(0);
	}
	@Override
	public InterfaceState newPath(List<InterfaceState> trace) throws PlasmaSimulatorException {
		//Initialize new session;
		throw new PlasmaSimulatorException("MatLab plugin doesn't support newPath(List<InterfaceState> trace)\n");
	}
	@Override
	public InterfaceState newPath(long seed) throws PlasmaSimulatorException {
		return newPath();
	}
	
	@Override
	public InterfaceState simulate() throws PlasmaSimulatorException {
		//Add states if needed
		while(currentSimPos >= simulationSize-1){
			try{
			//Load init state for this simulation
			proxy.eval(setLoadStateCmd);
			progressSimulinkSimulation();
			//Stop loading init state
			proxy.eval(unsetLoadStateCmd);
			}catch(MatlabInvocationException e){
				throw new PlasmaSimulatorException(e);
			}
		}
		//Progress simulation by one step
		currentSimPos ++;
		MatLabState currentState = new MatLabState(this, currentSimPos);
		trace.add(currentState);
		return currentState;
	}	
	
	public void backtrack() throws PlasmaSimulatorException {
		throw new PlasmaSimulatorException("Backtrack not supported by the simulator.");
	}

	@Override
	public void cut(int stateNb) throws PlasmaSimulatorException {
		if(stateNb >= trace.size())
			throw new PlasmaSimulatorException("Cannot cut at a position superior than trace length.");
		for(int i=0; i<stateNb; i++ )
			trace.remove(0);
		//Update state position awarness
		for(int i=0; i<trace.size(); i++ )
			((MatLabState)trace.get(i)).setStateNb(i);
		//Delete state values
		for(InterfaceIdentifier id:values.keySet()){
			double[] array = values.get(id);
			values.put(id, Arrays.copyOfRange(array,stateNb,array.length-1));
		}
		
		//Reduce current simulation position (current state)
		currentSimPos = trace.size() -1;
		//Reduce simulationSize
		simulationSize -= stateNb;
	}
	
	public void progressSimulinkSimulation() throws PlasmaSimulatorException{
		try{
			//SIMULATE
			simTimeLength += simStopTime;
			proxy.eval(plasmaOutput+" = sim(idSML, "
					+ "'StopTime', num2str("+simTimeLength+"), "
					+ "'StartTime', num2str("+simLatestTime+"), "
					/*+ "'SimulationMode', 'accelerator',"*/ + simParameters +");");
			proxy.eval(updateLastStateCmd);
			
			//TIME VALUES
			double[] timeVector = (double[]) proxy.returningEval(getLogOutputCmd+".get(1).Values.Time",1)[0];
			simLatestTime = timeVector[timeVector.length-1];
			concatenateValuesVector(values.get(TIME), timeVector, TIME);
			//Must be after concatenate to take into account we don't keep new.first
			simulationSize = values.get(TIME).length;
			//OUTPUT VALUES
			for(InterfaceIdentifier id:identifiers.values()){
				if(!id.equals(TIME)){
					double[] outVector;
					try{
						outVector = (double[]) proxy.returningEval(getLogOutputCmd+
								".get('"+id.getName()+"').Values.Data",1)[0];
					} catch(ClassCastException e){
						logger.debug(id.getName()+" cannot be cast to double, trying to cast as boolean.");
						try{
							boolean[] vect = (boolean[])proxy.returningEval(getLogOutputCmd+
									".get('"+id.getName()+"').Values.Data",1)[0];
							outVector = new double[vect.length];
							for(int i=0; i<vect.length; i++)
								outVector[i] = vect[i]?1:0;
						}catch(ClassCastException e2){
							logger.debug("failure to cast "+id.getName()+" as boolean.");
							throw new PlasmaSimulatorException(id.getName()+" is of an unsupported type.", e);
						}
					}
					concatenateValuesVector(values.get(id), outVector, id);
				}
			}
		} catch (MatlabInvocationException e){
			throw new PlasmaSimulatorException(e);
		}
	}
	
	/*Concatenate 2 values vector.
	Due to the way we handles the simulation, oldV.last = newV.first, only one of those
	values must be concatenated if oldV != null
	*/
	public void concatenateValuesVector(double[] oldV, double[] newV, InterfaceIdentifier id){
		double[] vector;
		if(oldV != null){
			//Don't take newV.first
			vector = new double[oldV.length+newV.length-1];
			System.arraycopy(oldV, 0, vector, 0, oldV.length);
			System.arraycopy(newV, 1, vector, oldV.length, newV.length-1);
		} else {
			//If there is no previous old values (first sim) we just put the new values
			vector = newV;
		}
		values.put(id, vector);
	}
	
	@Override
	public InterfaceState getCurrentState() {
		return trace.get(currentSimPos);
	}
	@Override
	public InterfaceState getStateAtPos(int pos) {
		return trace.get(pos);
	}
	@Override
	public int getDeadlockPos() {
		//No deadlock in Simulink
		return -1;
	}
	@Override
	public List<InterfaceState> getTrace() {
		return trace;
	}
	@Override
	public List<InterfaceIdentifier> getStateProperties() {
		return new ArrayList<InterfaceIdentifier>(0);
	}
	@Override
	public Map<String, InterfaceIdentifier> getIdentifiers() {
		return identifiers;
	}
	public Map<InterfaceIdentifier, double[]> getValues(){
		return values;
	}

	@Override
	public boolean checkForErrors() {
		//Clean previous structure
		values.clear();
		identifiers.clear();
		errors.clear();

		if(content.equals("")){
			errors.add(new PlasmaDataException("No Simulink model specified."));
			return true;
		}
		//Parse model
		SimulinkParser.parseSimulinkPlasmaModel(content);

		pathSML = SimulinkParser.modelPath();
		pathSML = new File(pathSML).getAbsolutePath(); // change path to absolute path (using user.dir that has been changed previously)
		fileSML = SimulinkParser.modelFile();
		String[] fileSplit = fileSML.split("/");
		String idSML = fileSplit[fileSplit.length-1];
		fileSplit = idSML.split("\\.");
		idSML = "";
		for(int i=0; i<fileSplit.length-1; i++)
			idSML += fileSplit[i];
		optimIdentifiers = SimulinkParser.getOptimAsJavaList();
		optimConstraints = SimulinkParser.getConstraintAsJavaList();
		
		try{
			if(!proxy.isConnected()){
				MatlabProxyFactoryOptions options = new MatlabProxyFactoryOptions.Builder()
						.setUsePreviouslyControlledSession(true)
//						.setHidden(true)
						.build();
				MatlabProxyFactory factory = new MatlabProxyFactory(options);
				proxy = factory.getProxy();		
				proxy.eval("clc");
			}
			
			//Add model path to MATLAB path
			proxy.eval("path(path,'"+pathSML+"')");
			proxy.eval("cd('"+pathSML+"')");
			
			//Open model
			proxy.feval("open_system", fileSML);
			proxy.eval(setIDSMLCmd+"'"+idSML+"'");
			
			//Simulate once to populate the plasma output object
			proxy.eval(plasmaOutput+" = sim(idSML, 'StopTime', num2str(1), "+simParameters+");");
			
			//Analyze output object
			double modelSize = ((double[]) proxy.returningEval(getLogOutputCmd+".numElements", 1)[0])[0];
			if(modelSize==0) {
				errors.add(new PlasmaDataException(content+" Simulink model has no signal output."));
				return true;
			}
			
			for(int i=1; i<=modelSize; i++){
				String signalName = (String) proxy.returningEval(getLogOutputCmd+".get("+i+").Name", 1)[0];
				identifiers.put(signalName, new MatLabIdentifier(signalName, i));
			}
			identifiers.put("", TIME);
			
//			proxy.eval("[timeVector,stateVector,outputVector] = sim('"+content+"');");
//			// Send the array to MATLAB, transpose it, then retrieve it and
//			// convert it to a 2D double array
//			MatlabTypeConverter processor = new MatlabTypeConverter(proxy);
//			double[][] stateVector = processor.getNumericArray("stateVector")
//					.getRealArray2D();
		} catch (MatlabInvocationException e) {
			logger.warn(e);
			errors.add(new PlasmaDataException(e));
			return true;
		} catch (MatlabConnectionException e) {
			logger.warn(e);
			errors.add(new PlasmaDataException(e));
			return true;
		}
		return false;
	}
	
	@Override
	public InterfaceIdentifier getTimeId() {
		return identifiers.get("");
	}
	@Override
	public boolean hasTime() {
		return true;
	}
	@Override
	public InterfaceIdentifier[] getHeaders() {
		return getIdentifiers().values().toArray(new InterfaceIdentifier[getIdentifiers().size()]);
	}
	@Override
	public void setValueOf(Map<InterfaceIdentifier, Double> update) throws PlasmaSimulatorException {
		try {
			for(InterfaceIdentifier id:update.keySet()){
				System.err.println("set_param('"+id.getName()+"', 'Value', '"+update.get(id)+"')");
				proxy.eval("set_param('"+id.getName()+"', 'Value', '"+update.get(id)+"')");
			}
		} catch (MatlabInvocationException e) {
			logger.warn("MatLab invocation exception while setting optimization values.",e);
			throw new PlasmaSimulatorException(e);
		}
		
//		PlasmaRunException e = new PlasmaRunException("MatLabSession does not support Optimization yet.");
//		logger.warn("MatLabSession does not support Optimization yet.",e);
//		throw e;
	}
	@Override
	public List<Variable> getOptimizationVariables() {
		//Create a new list because of a impossible conversion between SimulinkOptimVariable and Variable
		LinkedList<Variable> lst = new LinkedList<Variable>();
		for(SimulinkOptimVariable sov:optimIdentifiers)
			lst.add(sov);
		
		return lst;
	}
	
	@Override
	public List<VariableConstraint> getOptimizationConstraints() {
		//Create a new list because of a impossible conversion between SimulinkVariableConstraint and VariableConstraint
		LinkedList<VariableConstraint> lst = new LinkedList<VariableConstraint>();
		for(SimulinkVariableConstraint sov:optimConstraints)
			lst.add(sov);
		
		return lst;
	}
	
	@Override
	public void clean() throws PlasmaSimulatorException{
		try {
			for(SimulinkOptimVariable sov:optimIdentifiers)
				proxy.eval("set_param('"+sov.getName()+"', 'Value', '"+sov.defaultValue()+"')");
			
			proxy.eval(unsetLoadStateCmd);
			proxy.eval(unsetSaveStateCmd);
		} catch (MatlabInvocationException e) {
			logger.warn("MatLab invocation exception while restoring default optimization values.",e);
			throw new PlasmaSimulatorException(e);
		}
	}	
}
