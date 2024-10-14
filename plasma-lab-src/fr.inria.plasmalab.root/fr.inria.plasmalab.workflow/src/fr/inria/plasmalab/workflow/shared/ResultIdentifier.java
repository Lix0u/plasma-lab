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
package fr.inria.plasmalab.workflow.shared;

import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

public class ResultIdentifier implements InterfaceIdentifier {
	private String name;
	private boolean isBoolean;
	
	
	public ResultIdentifier(String name, boolean isBoolean){
		this.name = name;
		this.isBoolean = isBoolean;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean isBoolean() {
		return isBoolean;
	}

	@Override
	public int compareTo(InterfaceIdentifier o) {
		return this.getName().compareTo(o.getName());
	}
}
