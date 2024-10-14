/**
 * This file is part of fr.inria.plasmalab.rmlbis.
 *
 * fr.inria.plasmalab.rmlbis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.rmlbis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.rmlbis.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.rmlbis.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.rmlbis.ast.Action;
import fr.inria.plasmalab.rmlbis.ast.Command;
import fr.inria.plasmalab.rmlbis.ast.Module;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.models.Model;
import fr.inria.plasmalab.rmlbis.ast.processing.Update;
import fr.inria.plasmalab.rmlbis.simulator.RMLState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public class ObserverSimulator {
	final static Logger logger = LoggerFactory.getLogger(ObserverSimulator.class);

	private Model observer;
	private List<Module> obsModules; // List that contains the name of the observers, in the order the must be executed 
	private List<Identifier> externalIds;
	private RMLState currentState, initialState;
	private List<InterfaceState> trace;	// trace of the observer state
	protected List<InterfaceIdentifier> clocks;
	private InterfaceIdentifier scoreID, decidedID;
	
	public ObserverSimulator(Model obs, List<Module> obsModules, List<Identifier> ids) {
		this.observer = obs;
		this.obsModules = obsModules;
		this.externalIds = ids;
		this.trace = new ArrayList<InterfaceState>();
		this.clocks = new ArrayList<InterfaceIdentifier>();
	    for (Variable v : obs.getDefaultSystem().getVariables()) {
	    	if (v.type == Type.Clock)
	    		clocks.add(v);
	    }
	    this.scoreID = observer.getDefaultSystem().getIdent("score").target;
	    this.decidedID = observer.getDefaultSystem().getIdent("decided").target;
	}
	
	// these 2 methods must be called only after newPath has been called (otherwise currentState is null)
	public double getScore() throws PlasmaSimulatorException {
		if (currentState != null)
			return currentState.getValueOf(scoreID);
		else
			throw new PlasmaSimulatorException("Current state of the observer has not been initialized (null)");
	}
	public boolean isDecided() throws PlasmaSimulatorException {
		if (currentState != null)
			return currentState.getValueOf(decidedID) != 0.0;
		else
			throw new PlasmaSimulatorException("Current state of the observer has not been initialized (null)");
	}
	
	/** Initialize a new path from a state, that either comes from the model or from the observer.
	 * @throws PlasmaSimulatorException */
	public InterfaceState newPath(InterfaceState state) throws PlasmaSimulatorException {
		trace = new ArrayList<InterfaceState>();
		initialState = createInitialState(observer.getDefaultSystem(), state);
		trace.add(initialState);
		currentState = initialState;
		return initialState;
	}
	
	/** Initialize a new path from an existing path of the observer */
	public InterfaceState newPath(List<InterfaceState> initTrace) {
		trace = new ArrayList<InterfaceState>(initTrace);
		initialState = (RMLState) trace.get(0);
		currentState = (RMLState) trace.get(trace.size()-1);
		currentState.updateExpressions();
		return currentState;
	}
	
	/** Create an initial from a state that either comes from the model or from the observer.
	 *  If the state comes from the model, the values of the observer variables are their initial value, as declared in the observer.
	 *  If the state comes from the observer, the values of the observer variables are their value in the state.
	 *  In either case the values of the external variables are taken from the state.
	 * @throws PlasmaSimulatorException 
	 */
	private RMLState createInitialState(PlasmaSystem sys, InterfaceState state) throws PlasmaSimulatorException {
        HashMap<InterfaceIdentifier, Double> mapValues = new HashMap<InterfaceIdentifier, Double>(sys.getVariables().size() + externalIds.size());
       	for (Variable var : sys.getVariables()) {
			try {
       			mapValues.put(var, state.getValueOf(var));
			} catch (PlasmaSimulatorException e) {
				mapValues.put(var, var.initValue.value);
			}	
       	}
       	for (Identifier id : externalIds) {
			try {
				mapValues.put(id, state.getValueOf(id.getName()));
			} catch (PlasmaSimulatorException e) {
				throw new PlasmaSimulatorException(e.getMessage() + " in state: " + state.toString() + " when creating the initial state of the observer.");
			}
       	}
		RMLState newState = new RMLState(mapValues,null,sys);
        newState.updateExpressions();
        
        return newState;
	}
	
	public void simulate(InterfaceState modelState, double elapsedTime) {
		HashMap<InterfaceIdentifier, Double> newValues = new HashMap<InterfaceIdentifier, Double>(currentState.getValues()); // copy the state values
		Update up = new Update();
		List<Ref> updated = new ArrayList<Ref>();

		// update external ids ref value and the value of clocks
		for (Identifier id : externalIds) {
			try {
				id.ref.value = modelState.getValueOf(id.getName());
				if (id.ref.value != currentState.getValueOf(id)) {
					updated.add(id.ref);
					newValues.put(id,id.ref.value); // update the value of ident
				}
			} catch (PlasmaSimulatorException e) {
				updated.add(id.ref);
				newValues.put(id,Double.NaN); // update the value of ident
				logger.warn(e.getMessage());
			}
		}
		for (InterfaceIdentifier c : clocks) {
			double newClockTime = currentState.getValues().get(c) + elapsedTime; // The elapsed time will be erased by a clock reset. 
			newValues.put(c, newClockTime);										 // The elapsed time is 1 in discrete time models.
			Ref ref = ((Identifier)c).ref;
			ref.value = newClockTime; // update the value of the ref
			updated.add(ref);
		}
		up.propagate(updated);
				
		// execute observers sequentially
		List<InterfaceIdentifier> updateIds = new ArrayList<InterfaceIdentifier>();
		List<Double> updateValues = new ArrayList<Double>();
		for (Module m : obsModules) {
			// execute commands sequentially
			for (Command c : m.getCommands()) {
				if (c.getGuard().value != 0) {
					Action a = c.getActions().get(0); // only one action
					for (Entry<Variable, Expr> u : a.getAssignations().entrySet()) {
						if (u.getKey().ref.value != u.getValue().value) {
							updateIds.add(u.getKey());
							updateValues.add(u.getValue().value);
						}
					}
				}
				updated = new ArrayList<Ref>(updateIds.size());
				for (int i = 0; i < updateIds.size(); i++) {
					InterfaceIdentifier ident = updateIds.get(i);
					Double value = updateValues.get(i);
					newValues.put(ident,value); // update the value of ident
					Ref ref = ((Identifier)ident).ref;
					ref.value = value; // update the value of the ref
					updated.add(ref);
				}

				// apply the update to the expressions 
				up.propagate(updated);
				updateIds.clear();
				updateValues.clear();
			}
		}

		// Construct the new State		
		RMLState newState = new RMLState(newValues,null,observer.getDefaultSystem());
		currentState = newState;
		
		// Update the trace
		trace.add(currentState);
	}

	public RMLState getCurrentState() {
		return currentState;
	}
	
	public List<InterfaceState> getTrace() {
		return trace;
	}


}
