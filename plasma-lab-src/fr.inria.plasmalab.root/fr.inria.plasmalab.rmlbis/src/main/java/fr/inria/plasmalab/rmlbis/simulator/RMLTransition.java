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
package fr.inria.plasmalab.rmlbis.simulator;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.inria.plasmalab.rmlbis.ast.Command;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;

public class RMLTransition implements InterfaceTransition {

	private String channel;
	private List<Command> selectedSynchro;
	private Map<InterfaceIdentifier, Double> updates;
	
	public RMLTransition(String c, List<Command> selectedSynchro, Map<InterfaceIdentifier, Double> u) {
		this.channel = c;
		this.selectedSynchro = selectedSynchro;
		this.updates = u;
	}
	
	@Override
	public String getName() {
		return this.channel;
	}

	@Override
	public Map<InterfaceIdentifier, Double> getUpdate() {
		return this.updates;
	}
	
	public List<Command> getCommands() {
		return this.selectedSynchro;
	}
	
	public String toString() {
		String ret = "[" + channel + "] : ";
		boolean first = true;
		for (Entry<InterfaceIdentifier, Double> u : updates.entrySet()) {
			if (!first)
				ret += " & ";
			ret += u.getKey() + " = " + u.getValue();
		}
		return ret;
	}

}
