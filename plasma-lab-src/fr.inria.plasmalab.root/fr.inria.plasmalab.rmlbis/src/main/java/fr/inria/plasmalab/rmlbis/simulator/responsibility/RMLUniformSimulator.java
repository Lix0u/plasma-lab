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
import java.util.Map.Entry;

import fr.inria.plasmalab.rmlbis.ast.Action;
import fr.inria.plasmalab.rmlbis.ast.Command;
import fr.inria.plasmalab.rmlbis.simulator.RMLModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;

public class RMLUniformSimulator extends RMLSamplingSimulator {

	/** Uniform simulator:
	 *  - choose uniformly a channel
	 *  - choose uniformly a set of commands for this channel
	 *  - choose uniformly an action for each command
	 *  
	 *  Note that this simulator does not simulate real time. Time elapsing will be always 1.
	 *  This may matter if the property has state rewards. 
	 * 
	 * @param rmlModel
	 */
	
	public RMLUniformSimulator(RMLModel rmlModel) {
		super(rmlModel);
	}
	
	/** Compute the next transition.
	 *  @return the transition to perform to get the new state
	 */
	protected InterfaceTransition getNextTransition() {
		totalRateNormal = 1.0;
		likelihoodratio = 1.0;
		totalRate = 1.0;
		
		channels = new ArrayList<ChanRates>();
		nonSynchronizedCommands = new ArrayList<Command>();
		currentElapsedTime = 1;
		
		// 1 computing enabled commands that synchronize
		boolean deadlock = computeSynchronizedCommands();
		
		// 2 computing enabled commands that don't synchronize
		deadlock = deadlock & computeNonSynchronizedCommands();
		
		if (deadlock)
			return null;
		
		// 3 select channels/commands
		String channelName = "";
		int ran = actionRng.nextInt(channels.size() + nonSynchronizedCommands.size());		
		if (ran < channels.size()) {
			ChanRates selectedChannel = channels.get(ran);
			channelName = selectedChannel.name;
			selectSynchronizedCommands(selectedChannel);
		}
		else {
			ran -= channels.size();
			selectedSynchro = new ArrayList<Command>(1);
			selectedSynchro.add(nonSynchronizedCommands.get(ran));
		}
		
		// 4 select actions
		List<Action> selectedActions = selectActions();
		
		// 5 Instantiate the chosen transition
		return nextTransition(channelName, selectedActions);
	}
	
	protected boolean computeSynchronizedCommands(){
		double moduleRate; // number of enabled command for one channel in a module
		ChanRates chanRate;
		// for each channel, list the commands that may synchronize in each module: 
		for (Entry<String,List<List<Command>>> cll : currentState.getSystem().getSynchronised().entrySet()) {
			chanRate = new ChanRates(cll.getKey());
			// for each module list the commands
			for (List<Command> cl : cll.getValue()) {
				moduleRate = 0.0;
				for (Command c : cl) {
					// this command is active
					if (c.getGuard().value != 0 && c.getTotalRate(hastime) != 0) {
						moduleRate += 1;
					}
				}
				if (moduleRate == 0.0) {
					chanRate = null;
					break;
				}
				chanRate.moduleRates.add(moduleRate);
			}
			if (chanRate != null) { // the channel is enabled
				channels.add(chanRate);
			}
		}
		return channels.isEmpty();
	}
	
	protected boolean computeNonSynchronizedCommands(){
		for (Command c : currentState.getSystem().getNotSynchronised()) {
			if (c.getGuard().value != 0 && c.getTotalRate(hastime) != 0) {
				nonSynchronizedCommands.add(c);
			}
		}
		return nonSynchronizedCommands.isEmpty();
	}
	
	protected void selectSynchronizedCommands(ChanRates chanRate) {
		selectedSynchro = new ArrayList<Command>(chanRate.moduleRates.size());
		//  List of commands that can be used for the synchronization
		List<List<Command>> chanCommands = currentState.getSystem().getSynchronised().get(chanRate.name);
		int index = 0; // index of the current module in chanRate.moduleRates
		for (List<Command> cl : chanCommands) { // for each module
			int ran = actionRng.nextInt(chanRate.moduleRates.get(index).intValue());
			for (Command c : cl) {
				if (c.getGuard().value != 0  && c.getTotalRate(hastime) != 0) {
					if (ran == 0) {
						selectedSynchro.add(c);
						break;
					}
					else
						ran -= 1;
				}
			}
			index++;
		}
	}

	protected List<Action> selectActions() {
		List<Action> selectedActions = new ArrayList<Action>();
		for (Command c : selectedSynchro) {
			// determine which action have a non zero rate
			List<Action> enabled = new ArrayList<Action>(c.getActions().size());
			for (Action a : c.getActions())
				if (a.getRate() > 0)
					enabled.add(a);
			// select randomly from these actions
			int ran = actionRng.nextInt(enabled.size());
			selectedActions.add(enabled.get(ran));
		}
		return selectedActions;
	}
}
