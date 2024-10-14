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
package fr.inria.plasmalab.rmlbis.simulator.responsibility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import fr.inria.plasmalab.rmlbis.ast.ModuleInstance;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.adaptive.AdaptiveAction;
import fr.inria.plasmalab.rmlbis.ast.adaptive.AdaptiveCommand;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
import fr.inria.plasmalab.rmlbis.ast.models.AdaptiveModel;
import fr.inria.plasmalab.rmlbis.ast.processing.Update;
import fr.inria.plasmalab.rmlbis.simulator.ProcedureSimulator;
import fr.inria.plasmalab.rmlbis.simulator.RMLModel;
import fr.inria.plasmalab.rmlbis.simulator.RMLState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public class RMLAdaptiveSimulator extends RMLSimulator {
	
	public RMLAdaptiveSimulator(RMLModel rmlModel) {
		super(rmlModel);
	}

	@Override
	public InterfaceState newPath() {
		this.trace = new ArrayList<InterfaceState>();
		actionRng = new Random(mainRng.nextLong());
		initialState = rmlModel.createInitialState(((AdaptiveModel)model).getAdaptiveSystem().getStartSystem(),0.0,actionRng);
		trace.add(initialState);
		currentState = initialState;
		deadlockPos = -1;
		return initialState;
	}

	@Override
	public InterfaceState simulate() throws PlasmaSimulatorException {
		RMLState newState;
		
		/* Test if the system changes */
		PlasmaSystem newSystem = getNextSystem();
		if (newSystem != null) {
			// create a new initial state
			double time = 0.0;
			if (rmlModel.hasTime())
				 time = currentState.getValues().get(rmlModel.getTimeId());
			newState = rmlModel.createInitialState(newSystem,time,actionRng);
			newState.updateExpressions(); // should be useless because initialize functions take care of updating the expressions
			
			currentState = newState;
			trace.add(currentState);
			return currentState;
		}
		else {
			return super.simulate();
		}

	}

	@Override
	public void backtrack() throws PlasmaSimulatorException {
		if (trace.size()>1) {
			RMLState previousState = (RMLState) trace.remove(trace.size()-1);
			currentState = (RMLState) trace.get(trace.size()-1);
			
			if (previousState.getIncomingTransition() != null) {
				Map<InterfaceIdentifier, Double> update = previousState.getIncomingTransition().getUpdate();
				List<Ref> updated = new ArrayList<Ref>(update.size());
					
				for (Entry<InterfaceIdentifier,Double> currentVar : update.entrySet()) {
					InterfaceIdentifier ident = currentVar.getKey();
					Double value = currentState.getValueOf(ident);
					Ref id = ((Identifier)ident).ref;
					id.value = value;
					updated.add(id);
				}
				Update up = new Update();
				up.propagate(updated);
			}
			else {
				currentState.updateExpressions();
			}
		}
		else
			throw new PlasmaSimulatorException("Trace is already at initial state. Cannot backtrack.");
	}
	
	private PlasmaSystem getNextSystem() {
		// 1 get the list of enabled adaptive commands
		ArrayList<AdaptiveCommand> enabledCommands = new ArrayList<AdaptiveCommand>();
		for (AdaptiveCommand co : ((AdaptiveModel) model).getAdaptiveSystem().getTransitions(currentState.getSystem())) {
			if (co.getGuard().value != 0) {
				enabledCommands.add(co);
			}
		}
		
		if (enabledCommands.isEmpty()) // the system doesn't change
			return null;
		
		// 2 else choose uniformly among the enabled commands
		AdaptiveCommand selectedCommand = enabledCommands.get(chooseAdaptiveCommand(enabledCommands.size()));
		
		// 3 choose the transitions according to the distributions
		AdaptiveAction selectedAction = null;
		double ran = chooseAdaptiveAction();
		for (AdaptiveAction a : selectedCommand.getActions()) {
			if (ran <= a.getRate().value) {
				selectedAction = a;
				break;
			}
			else {
				ran -= a.getRate().value;
			}
		}
		if (selectedAction == null)
			return null;	// the system doesn't change.
		
		// 4 apply the procedures in reverse order
		for (int i = selectedAction.getProcedures().size()-1; i>=0; i--) {
			ModuleInstance proc = selectedAction.getProcedure(i);
			proc.initialize();
			ProcedureSimulator sim = new ProcedureSimulator(proc.getModule());
			sim.initialize(mainRng.nextLong());
			sim.simulate();
		}
		
		// 4 instantiate the new system
		selectedAction.getNewSystemInstance().initialize();
		return selectedAction.getNewSystem();
	}
	
	private int chooseAdaptiveCommand(int size) {
		return actionRng.nextInt(size);
	}
	
	private double chooseAdaptiveAction() {
		return actionRng.nextDouble();
	}

}
