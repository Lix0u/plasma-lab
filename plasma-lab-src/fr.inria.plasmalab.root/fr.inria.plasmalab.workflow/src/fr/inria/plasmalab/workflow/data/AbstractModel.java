/**
 * This file is part of fr.inria.plasmalab.workflow.
 *
 * fr.inria.plasmalab.workflow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.workflow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.workflow.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.workflow.data;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.workflow.concrete.Variable;
import fr.inria.plasmalab.workflow.concrete.VariableConstraint;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

/** Abstract class for a model simulator. 
 *  It allows to generate random traces and provides functions for managing these traces.
 *  A model simulator manages one trace at time, the current trace.
 *  The current state of the model is the last state of the trace. 
 */
public abstract class AbstractModel extends AbstractData {
	/**
	 * 	Instantiate a new path/trace and return the initial state.
	 *  A path is a {@link ArrayList} of {@link InterfaceState}.
	 *  A new path contains the initial state.
	 *  The current state is the initial state.
	 *  
	 *  @return the initial state of the path.
	 **/ 
	public abstract InterfaceState newPath() throws PlasmaSimulatorException;
	
	/**
	 * 	Instantiate a clone path/trace of the one given in parameter and return the last state.
	 *  The states are not necessarily copied. Only the list managing the trace will regenerated.
	 *  The current state is the last state of initTrace.
	 * 
	 *  @param initTrace the trace that we will use from now on.
	 *  @return the last state of the trace
	 **/ 	
	public abstract InterfaceState newPath(List<InterfaceState> initTrace) throws PlasmaSimulatorException;
	
	/**
	 *  Instantiate a new trace with a given seed for random generators.
	 *  A path is a {@link ArrayList} of {@link InterfaceState}.
	 *  A new path contains the initial state.
  	 *  The current state is the initial state.
  	 *  
	 *  @param seed the seed of the random generator used to run the simulation
	 *  @return the initial state of the path.
	 */
	public abstract InterfaceState newPath(long seed) throws PlasmaSimulatorException;	
	
	/**
	 *  Move the trace forward for 1 step.
	 *  The trace length is increased by one.
  	 *  The current state is the new state.
  	 *  
	 *  @return the new current state
	 *  @throws PlasmaSimulatorException
	 */
	public abstract InterfaceState simulate() throws PlasmaSimulatorException;
	
	/**
	 *  Move the trace backward for 1 step.
	 *  The trace length is decreased by one.
	 *  The current state is the previous state.
	 * 
	 * @throws false if we are in the initial state and there is no previous state.
	 */
	public abstract void backtrack() throws PlasmaSimulatorException;
	
	/**
	 *  Cut the current trace, removing the first stateNb states.
	 * 	The current state is not not change. 
	 * 	State #stateNb become state #0.
	 *  The trace length becomes previous_length - stateNb
	 * 
	 * @param stateNb the number of state to delete.
	 * @throws PlasmaSimulatorException if stateNb >= trace's length.
	 */
	public abstract void cut(int stateNb) throws PlasmaSimulatorException;
	
	/**
	 * Change values of the model with a new set of values representing a new initial state. 
	 * InterfaceIdentifier accessible from getIdentifiers can be expected to be impacted by this method.
	 * @param update map from InterfaceIdentifier to new value
	 */
	public abstract void setValueOf(Map<InterfaceIdentifier,Double> update) throws PlasmaSimulatorException;
	
	/**
	 *  Clean procedure after execution.
	 *  In some type of models it may be needed to clean or restore the model after execution.
	 *  eg. optimization may have modified the model in the Simulink model.
	 *  Launch at the completion of an algorithm
	 *  
	 *  By default does nothing.
	 */
	public void clean() throws PlasmaSimulatorException {};
	
	/**
	 * Return the Head {@link InterfaceState} of the current trace.
	 * @return the head of the trace (the last state).
	 */
	public abstract InterfaceState getCurrentState();
	
	/**
	 * Return the state at position pos in the trace.
	 * @param pos
	 * @return the state at the specified position.
	 */
	public abstract InterfaceState getStateAtPos(int pos);
	
	/**
	 * Return the current path (List of interfaceState).
	 * 
	 * @return the current path.
	 */
	public abstract List<InterfaceState> getTrace();
	
	/**
	 * Return the number of states in the current trace.
	 * 
	 * This includes the initial state, thus the trace length minimum size is 1.
	 * @return trace length
	 */
	public int getTraceLength() {
		return getTrace().size();
	}
	
	/** 
	 * Return the index of the last state (getTraceLength()-1) if it is a deadlock state (it has no successor).
	 * Otherwise return -1 the current state may have successors.
	 * @return deadlock position
	 */
	public abstract int getDeadlockPos();
	
	/**
	 * Return a list of {@link InterfaceIdentifier} that represent state properties.
	 * A state property is a boolean formulae that is evaluated on a state. It is represented by an 
	 * InterfaceIdentifier whose value will be obtained from a state.
	 * It is used in Simulation mode in the properties panel. 
	 * 
	 * @return a list of InterfaceIdentifier
	 */
	public abstract List<InterfaceIdentifier> getStateProperties();
	
	/**
	 * This method returns a map of the identifiers of the model.
	 * These are all the identifiers that can be evaluated on a state and used in the requirements. 
	 * 
	 * @return map of identifiers their key being their name
	 */
	public abstract Map<String, InterfaceIdentifier> getIdentifiers();
	
	/**
	 * This method returns the identifier that counts the continuous time.
	 * May be null if the model hasn't continuous time.
	 * 
	 * @return the time identifier
	 */
	public abstract InterfaceIdentifier getTimeId();
	
	/**
	 * This method returns true if the model provides continuous time (like CTMC).
	 * 
	 */
	public abstract boolean hasTime();
	
	/** 
	 * This method returns an array of identifiers that are followed along a trace in simulation mode.
	 * They will appear in the Simulation results panel in the same order as in the array. 
	 * 
	 * @return an array of identifiers
	 */
	public abstract InterfaceIdentifier[] getHeaders();

	
	/* OPTIMIZATION and REQUIREMENT VARIABLES DECLARATION */
	/**
	 * This method returns a list of Variable for the optimization experiments.
	 * Empty by default.
	 * 
	 * @return a List of optimization Variable.
	 */
	public List<Variable> getOptimizationVariables() {
		return new ArrayList<Variable>(0);
	}
	
	/** 
	 * This method returns a list of constraints over the optimization variables
	 * Empty by default.
	 * 
	 * @return a List of optimization Variable.
	 */
	public List<VariableConstraint> getOptimizationConstraints() {
		return new ArrayList<VariableConstraint>(0);
	}

	
}
