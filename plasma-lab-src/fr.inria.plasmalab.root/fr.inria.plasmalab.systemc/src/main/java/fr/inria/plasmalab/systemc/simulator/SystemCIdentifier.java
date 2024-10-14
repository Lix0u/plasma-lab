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

import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

public class SystemCIdentifier implements InterfaceIdentifier {
	
	private String name;
	private boolean is_boolean;

	public SystemCIdentifier(String name,boolean is_boolean) {
		this.name = name;
		this.is_boolean = is_boolean; // by defaut: not boolean
	}

	@Override
	public int compareTo(InterfaceIdentifier id) {
		return name.compareTo(id.getName());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isBoolean() {
		return is_boolean;
	}
	
	/**
	 * Value displayed in tree view of the GUI, in the Model panel.
	 */
	public String toString(){
		return name;
	}
	

}
