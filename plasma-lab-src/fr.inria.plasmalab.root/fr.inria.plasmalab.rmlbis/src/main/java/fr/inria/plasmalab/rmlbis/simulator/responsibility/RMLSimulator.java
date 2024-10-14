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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.rmlbis.ast.Action;
import fr.inria.plasmalab.rmlbis.ast.Command;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Label;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.processing.Update;
import fr.inria.plasmalab.rmlbis.ast.rewards.Reward;
import fr.inria.plasmalab.rmlbis.simulator.RMLModel;
import fr.inria.plasmalab.rmlbis.simulator.RMLState;
import fr.inria.plasmalab.rmlbis.simulator.RMLTransition;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDeadlockException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public class RMLSimulator extends SimulatorResponsibility {
	
	final static Logger logger = LoggerFactory.getLogger(RMLSimulator.class);

	/** Structure to store the rates of a channel.
     *  - the rates of the module are the sum of the rates (1 if DTMC) of the enabled commands on this channel
     *  - the rate of the channel is the product of the module rates  
	 */
	class ChanRates {
		String name;
		double rate;
		List<Double> moduleRates;
		
		ChanRates(String channel) {
			name = channel;
			rate = 1;
			moduleRates = new ArrayList<Double>();
		}
	}

	protected Random actionRng, mainRng;
	protected double currentElapsedTime;
	protected boolean hastime;
	protected double totalRate;
	protected double totalRateNonSynchronized;
	protected List<ChanRates> channels; // list of enabled channels and their rates
	protected List<Command> nonSynchronizedCommands; // list of the non synchronized commands enabled 
	protected List<Command> selectedSynchro; // list of selected commands
	protected List<InterfaceIdentifier> clocks;

	public RMLSimulator(RMLModel rmlModel) {
		super(rmlModel);
		
		logger.debug("new RMLSimulator");
		
		this.mainRng = new Random();		
		this.currentElapsedTime = 0;
		this.hastime = rmlModel.hasTime(); // the model is not a CTMC
		
		this.clocks = new ArrayList<InterfaceIdentifier>();
        for (Variable v : model.getDefaultSystem().getVariables()) {
        	if (v.type == Type.Clock)
        		clocks.add(v);
        }
	}
	
	@Override
	public InterfaceState newPath() {
		this.trace = new ArrayList<InterfaceState>();
		actionRng = new Random(mainRng.nextLong());
		initialState = rmlModel.createInitialState(model.getDefaultSystem(),0.0,actionRng);
		trace.add(initialState);
		currentState = initialState;
		deadlockPos = -1;
		return initialState;
	}

	@Override
	public InterfaceState newPath(long seed) {
		this.trace = new ArrayList<InterfaceState>();
		actionRng = new Random(seed);
		initialState = rmlModel.createInitialState(model.getDefaultSystem(),0.0,actionRng);
		trace.add(initialState);
		currentState = initialState;
		deadlockPos = -1;
		return initialState;
	}
	
	@Override
	public InterfaceState newPath(List<InterfaceState> initTrace) {
		trace = new ArrayList<InterfaceState>(initTrace);
		actionRng = new Random(mainRng.nextLong());
		initialState = (RMLState)(trace.get(0));
		currentState = (RMLState) trace.get(trace.size()-1);
		currentState.updateExpressions();
		deadlockPos = -1;
		return currentState;
	}

	/** Simulate a random step in the model. 
	 *  This method can only be called for purely stochastic Systems. 
	 *  When called on nondeterministic systems, the resolution of choices is uniform
	 * @throws PlasmaSimulatorException 
	 */
	@Override
	public InterfaceState simulate() throws PlasmaSimulatorException {
		// Choose the next transition
		InterfaceTransition chosenTransition = getNextTransition();
		if (chosenTransition == null){
			deadlockPos = trace.size()-1;
			throw new PlasmaDeadlockException(currentState, trace.size());
		}
		
		// Build data for the new state
		HashMap<InterfaceIdentifier, Double> newValues = new HashMap<InterfaceIdentifier, Double>(currentState.getValues()); // copy the state values
		
		// Compute rewards on the previous state
		for (Reward r : model.getRewards()) {
			double val = r.getStateReward();
			if (rmlModel.hasTime())
				val *= currentElapsedTime;
			val += r.getTransitionReward(chosenTransition.getName());
			newValues.put(r, newValues.get(r) + val);
		}
		
		// Update the ref value
		Map<InterfaceIdentifier, Double> update = chosenTransition.getUpdate();
		List<Ref> updated = new ArrayList<Ref>(update.size());
		for (InterfaceIdentifier c : clocks) {
			Identifier cident = (Identifier) c; 
			double newClockTime = Double.NaN;
			try {
				newClockTime = currentState.getValueOf(cident) + currentElapsedTime;
			} catch (PlasmaSimulatorException e) {
				logger.warn(e.getMessage());
			} 
			// The elapsed time will be erased by a clock reset. 
			newValues.put(cident, newClockTime);										   // The elapsed time is 1 in discrete time models.
			cident.updateRef(newClockTime); // update the value of the ref
			updated.add(cident.ref);
		}
		for (Entry<InterfaceIdentifier,Double> currentVar : update.entrySet()) {
			Identifier ident = (Identifier) currentVar.getKey();
			Double value = currentVar.getValue();
			newValues.put(ident,value); // update the value of ident
			ident.updateRef(value); // update the value of the ref
			updated.add(ident.ref);
		}
		// Update time
		if (rmlModel.hasTime()) {
			try {
				double newTime = currentState.getValueOf(rmlModel.getTimeId()) + currentElapsedTime;
				newValues.put(rmlModel.getTimeId(), newTime);
			} catch (PlasmaSimulatorException e) {
				logger.warn(e.getMessage());
				newValues.put(rmlModel.getTimeId(), Double.NaN);
			}
		}
		
		// Update the value of the expressions in the model
		Update up = new Update();
		up.propagate(updated);
		
		// Update labels (the value of their expression has been updated with Update)
		for (Label l : model.getLabels().values()) {
			newValues.put(l,l.body.value);
		}
		
		// Construct the new State		
		RMLState newState = new RMLState(newValues,chosenTransition,currentState.getSystem());
		currentState = newState;
		
		// Update the trace
		trace.add(currentState);
		return currentState;
	}

	/** Revert to previous state in the trace. */
	@Override
	public void backtrack() throws PlasmaSimulatorException {
		if (trace.size()>1) {
			RMLState previousState = (RMLState) trace.remove(trace.size()-1);
			currentState = (RMLState) trace.get(trace.size()-1);

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
		} else
			throw new PlasmaSimulatorException("Trace is already at initial state. Cannot backtrack.");
	}
	
	/** Compute the next transition.
	 *  @return the transition to perform to get the new state
	 */
	protected InterfaceTransition getNextTransition() {
		// initialize
		totalRate = 0.0;
		channels = new ArrayList<ChanRates>();
		totalRateNonSynchronized = 0.0;
		nonSynchronizedCommands = new ArrayList<Command>();
		
		// 1 computing enabled commands that synchronize
		boolean deadlock = computeSynchronizedCommands();
		
		// 2 computing enabled commands that don't synchronize
		deadlock = deadlock & computeNonSynchronizedCommands();
		
		if (deadlock)
			return null;
		
		// 3 select channel
		ChanRates selectedChannel = selectChannel();
		String channelName = "";
		
		// 4 select commands
		if (selectedChannel != null) {
			selectSynchronizedCommands(selectedChannel);
			channelName = selectedChannel.name;
		}
		else
			selectNonSynchronizedCommand();
	
		// 5 select actions
		List<Action> selectedActions = selectActions();

		// 6 update the elapsed time
		updateTime();
		
		// 7 Instantiate the chosen transition
		return nextTransition(channelName, selectedActions);
	}
	
	/** 1 computing enabled commands that synchronize 
	 *  @return true if there there is no synchronized commands enabled; false otherwise.
	 *  Update the totalRate, the total rate of the enabled commands.
	 *  Update chanRates, an initially empty list of enabled channels. 
	 */
	protected boolean computeSynchronizedCommands(){
		double moduleRate;
		ChanRates chanRate;
		// for each channel, list the commands that may synchronize in each module: 
		for (Entry<String,List<List<Command>>> cll : currentState.getSystem().getSynchronised().entrySet()) { 
			chanRate = new ChanRates(cll.getKey());
			// for each module list the commands
			for (List<Command> cl : cll.getValue()) {
				// this module rate is 0
				moduleRate = 0.0;
				for (Command c : cl) {
					// this command is active
					if (c.getGuard().value != 0) {
						moduleRate += c.getTotalRate(hastime); // + 1 if DTMC
					}
				}
				// Product of all the module rates on this channel
				chanRate.rate *= moduleRate;	// becomes zero if a module cannot synchronize on the channel
				if (chanRate.rate ==0)
					break;
				chanRate.moduleRates.add(moduleRate);
			}
			if (chanRate.rate > 0) { // the channel is enabled
				// Add the rate of the channel to total rate
				totalRate += chanRate.rate;
				channels.add(chanRate);
			}
		}
		return channels.isEmpty();
	}
	
	/** 2 computing enabled commands that don't synchronize
	 *  @return true if there there is no non synchronized commands enabled; false otherwise.
	 *  Update the totalRate, the total rate of the enabled commands.
	 *  Update the list of nonSynchronizedCommands. 
	 **/
	protected boolean computeNonSynchronizedCommands(){
		for (Command c : currentState.getSystem().getNotSynchronised()) {
			if (c.getGuard().value != 0) {
				nonSynchronizedCommands.add(c);
				totalRateNonSynchronized += c.getTotalRate(hastime); // + 1 if DTMC
			}
		}
		totalRate += totalRateNonSynchronized;
		return nonSynchronizedCommands.isEmpty();
	}
	
	/** Select randomly a channel according to the rates of each enabled channels
	 *  @return the channel selected or null if no channel has been selected.
	 */
	protected ChanRates selectChannel() {
		double ran = selectRandomTransition(totalRate);
		for (ChanRates chanRate : channels) {			
			if (ran <= chanRate.rate)
				return chanRate;
			else
				ran -= chanRate.rate;
		}
		return null;
	}
	
	/** Select synchronized commands, one for each module that synchronizes on the selected channel. 
	 *  For each module, the selection is random according to the total rate of each command, that is 1 in case of a DTMC.
	 *  @param chanRate : rate of the channel and the associated rates of the modules (in the same order as chanCommands)
	 *  Update selectedSynchro: the list of commands chosen for the transition.
	 */
	protected void selectSynchronizedCommands(ChanRates chanRate) {
		selectedSynchro = new ArrayList<Command>(chanRate.moduleRates.size());
		//  List of commands that can be used for the synchronization
		List<List<Command>> chanCommands = currentState.getSystem().getSynchronised().get(chanRate.name);
		int index = 0; // index of the current module in chanRate.moduleRates
		for (List<Command> cl : chanCommands) { // for each module
			double ran = selectRandomTransition(chanRate.moduleRates.get(index));
			for (Command c : cl) {
				if (c.getGuard().value != 0) {
					double r = c.getTotalRate(hastime);
					if (ran <= r) {
						selectedSynchro.add(c);
						break;
					}
					else
						ran -= r;
				}
			}
			index++;
		}
	}
	
	/** Select a non synchronized command amongst all the enabled non synchronized commands.
	 *  The selection is random according to the total rate of each command, that is 1 in case of a DTMC.
	 * 	Update selectedSynchro: a list that contains only the selected command.
	 */
	protected void selectNonSynchronizedCommand() {
		selectedSynchro = new ArrayList<Command>(1);
		double ran = selectRandomTransition(totalRateNonSynchronized);
		for (Command c : nonSynchronizedCommands) {
			double r = c.getTotalRate(hastime);
			if (ran <= r) {
				selectedSynchro.add(c);
				break;
			}
			else
				ran -= r;
		}
	}
	
	/** Select the actions of the selected commands, one for each synchronized commands.
	 *  The selection is random according to the rate of each action. 
	 *  @return the list of actions.
	 */
	protected List<Action> selectActions() {
		List<Action> selectedActions = new ArrayList<Action>();
		for (Command c : selectedSynchro) {
			double ran = selectRandomAction(c.getTotalRate(true)); // when selecting actions the rate are not normalized (even for DTMC)
			for (Action a : c.getActions()) {
				double r = a.getRate();
				if (ran <= r) {
					selectedActions.add(a);
					break;
				}
				else
					ran -= r;
			}
		}
		return selectedActions;
	}
	
	/** Update the elapsed according to an exponential distribution (for CTMC)
	 *  The elapsed time is 1 for DTMC.
	 */
	protected void updateTime() {
		double ran;
		if (hastime) { // model has time
			/* Choose the elapsed time */
			do {
				ran = actionRng.nextDouble();		
			} while (ran == 0);	// Avoids choosing the value 0 which is problematic
			currentElapsedTime = - Math.log(ran)/totalRate;
		}
		else {
			currentElapsedTime = 1; // discrete time
		}
	}

	/** Compute the next transition according that implements a given list of actions
	 * 
	 *  @param channel : channel of the transition
	 *  @param actions : list of actions performed during the transition
	 *  @return the new RMLTransition
	 */
	protected RMLTransition nextTransition(String channel, List<Action> actions) {
		Map<InterfaceIdentifier,Double> updates = new HashMap<InterfaceIdentifier, Double>();
		for (Action a : actions) {
			// computes the values of each assignment and add the assignment to the updates list
			for (Entry<Variable, Expr> u : a.getAssignations().entrySet()) {
				if (u.getKey().ref.value != u.getValue().value)
					updates.put(u.getKey(), u.getValue().value);
			}
		}
		RMLTransition t = new RMLTransition(channel,selectedSynchro,updates);
		return t;
	}
	
	/** Return a random value between 0 and maxValue using the actionRng generator */
	protected double selectRandomTransition(double maxValue) {
		return selectRandomAction(maxValue);
	}
	
	/** Return a random value between 0 and maxValue using the actionRng generator */
	protected double selectRandomAction(double maxValue) {
		double ran = actionRng.nextDouble();		
		return ran*maxValue;
	}
}
