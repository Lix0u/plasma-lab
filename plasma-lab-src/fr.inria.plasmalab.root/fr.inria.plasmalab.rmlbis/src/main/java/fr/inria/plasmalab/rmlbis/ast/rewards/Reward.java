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
package fr.inria.plasmalab.rmlbis.ast.rewards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.inria.plasmalab.rmlbis.ast.identifiers.TimeIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

public class Reward implements InterfaceIdentifier {

	/** Name starts and ends with quotes */
	private String name;
	private ArrayList<StateReward> stateRewards;
	private HashMap<String,List<TransitionReward>> transitionRewards;
	
	public Reward(String name) {
		this.name = name;
		this.stateRewards = new ArrayList<StateReward>();
		this.transitionRewards = new HashMap<String,List<TransitionReward>>();
	}

	/** Compute the value of state rewards */
	public double getStateReward() {
		double val = 0;
		for (StateReward r : stateRewards) {
			val += r.getValue();
		}
		return val;
	}
		
	/** Compute the value of transition rewards that satisfy the channel */
	public double getTransitionReward(String channel) {
		double val = 0;
		List<TransitionReward> l = transitionRewards.get(channel);
		if (l != null) {
			for (TransitionReward r : l) {
				val += r.getValue();
			}
		}
		return val;
	}
	
	public void addRewardStatement(StateReward r) {
		if (r instanceof TransitionReward) {
			TransitionReward rt = (TransitionReward) r;
			List<TransitionReward> l = transitionRewards.get(rt.getChannel());
			if (l == null) {
				l = new ArrayList<TransitionReward>();
				transitionRewards.put(rt.getChannel(),l);
			}
			l.add(rt);
		}
		else
			stateRewards.add(r);
	}
	
	public String toString() {
		return name;
	}

	public String toPrism(String sysPrefix) {
		String ret = "reward "+ this.getName() + "+\n" ;
		for (StateReward r : stateRewards) {
			ret += r.toPrism(sysPrefix) + "\n";
		}
		for (List<TransitionReward> l : transitionRewards.values()) {
			for (TransitionReward r : l) {
				ret += r.toPrism(sysPrefix) + "\n";
			}
		}
		ret += "endrewards";
		return ret;
	}
	
	@Override
	public boolean isBoolean() {
		return false;
	}

	@Override
	public int compareTo(InterfaceIdentifier o) {
		//Time is always the first Id
		if(o instanceof TimeIdentifier)
			return +1;
		return this.getName().compareTo(o.getName());
	}

	@Override
	public String getName() {
		return name;
	}
	
}
