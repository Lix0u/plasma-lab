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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.rmlbis.ast.Action;
import fr.inria.plasmalab.rmlbis.ast.Command;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.sampling.SamplingAction;
import fr.inria.plasmalab.rmlbis.sampling.SamplingCommand;
import fr.inria.plasmalab.rmlbis.sampling.SamplingTransition;
import fr.inria.plasmalab.rmlbis.simulator.RMLModel;
import fr.inria.plasmalab.rmlbis.simulator.RMLTransition;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceSamplingTransition;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;

public class RMLSamplingSimulator extends RMLSimulator {
	final static Logger logger = LoggerFactory.getLogger(RMLSamplingSimulator.class);

	protected double totalRateNormal; // The total rate of the enabled commands without sampling correction
	protected double likelihoodratio; // product of the likelihood ratio of the transition
	
	public RMLSamplingSimulator(RMLModel rmlModel) {
		super(rmlModel);
		logger.debug("new RMLSamplingSimulator");
	}

	protected InterfaceTransition getNextTransition() {
		totalRateNormal = 0.0;
		likelihoodratio = 1.0;
		
		if (currentState.getIncomingTransition() != null) {
			InterfaceSamplingTransition previous = (InterfaceSamplingTransition)(currentState.getIncomingTransition());
			likelihoodratio *= previous.getLikelihoodRatio();
		}
		return super.getNextTransition();
	}

	protected boolean computeSynchronizedCommands(){
		double moduleRate, moduleRateNormal, chanRateNormal;
		ChanRates chanRate;
		// for each channel, list the commands that may synchronize in each module: 
		for (Entry<String,List<List<Command>>> cll : currentState.getSystem().getSynchronised().entrySet()) { 
			chanRate = new ChanRates(cll.getKey());
			chanRateNormal = 1.0;
			// for each module list the commands
			for (List<Command> cl : cll.getValue()) {
				// this module rate is 0
				moduleRate = 0.0;
				moduleRateNormal = 0.0;
				for (Command c : cl) {
					// this command is active
					if (c.getGuard().value != 0) {
						SamplingCommand sc = (SamplingCommand) c;
						moduleRate += c.getTotalRate(hastime);
						moduleRateNormal += sc.getTotalRateNormal(hastime);
					}
				}
				// Product of all the module rates on this channel
				chanRate.rate *= moduleRate;	// becomes zero if a module cannot synchronize on the channel
				chanRateNormal *= moduleRateNormal;
				if (chanRate.rate ==0)
					break;
				chanRate.moduleRates.add(moduleRate);
			}
			if (chanRate.rate > 0) { // the channel is enabled
				// Add the rate of the channel to total rate
				totalRate += chanRate.rate;
				totalRateNormal += chanRateNormal;
				channels.add(chanRate);
			}
		}
		return channels.isEmpty();
	}
	
	protected boolean computeNonSynchronizedCommands(){
		for (Command c : currentState.getSystem().getNotSynchronised()) {
			if (c.getGuard().value != 0) {
				nonSynchronizedCommands.add(c);
				totalRateNonSynchronized += c.getTotalRate(hastime);
				SamplingCommand sc = (SamplingCommand) c;
				totalRateNormal += sc.getTotalRateNormal(hastime);
			}
		}
		totalRate += totalRateNonSynchronized;
		return nonSynchronizedCommands.isEmpty();
	}
	
	protected List<Action> selectActions() {
		List<Action> selectedActions = new ArrayList<Action>();
		for (Command c : selectedSynchro) {
			SamplingCommand sc = (SamplingCommand) c;
			double sumActionRates = c.getTotalRate(true); // sum of the sampled action rates
			double ran = selectRandomAction(sumActionRates);
			for (Action a : c.getActions()) {
				SamplingAction as = (SamplingAction)a;
				double asr = as.getSamplingRate() * as.getRate();
				if (ran <= asr) {
					selectedActions.add(a);
					likelihoodratio *= 1/as.getSamplingRate();
					break;
				}
				else
					ran -= asr;
			}
			if (!hastime) {
				likelihoodratio *= sumActionRates / sc.getTotalRateNormal(true); // sum of the sampled action rates divided by sum of normal action rates
			}
		}
		return selectedActions;
	}

	protected RMLTransition nextTransition(String channel, List<Action> actions) {
		likelihoodratio *= totalRate/totalRateNormal;
		Map<InterfaceIdentifier,Double> updates = new HashMap<InterfaceIdentifier, Double>();
		for (Action a : actions) {
			// computes the values of each assignment and add the assignment to the updates list
			for (Entry<Variable, Expr> u : a.getAssignations().entrySet()) {
				if (u.getKey().ref.value != u.getValue().value)
					updates.put(u.getKey(), u.getValue().value);
			}
		}
		SamplingTransition t = new SamplingTransition(channel,selectedSynchro,updates,likelihoodratio,totalRate);
		return t;
	}
}
