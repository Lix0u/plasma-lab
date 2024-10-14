/**
 * This file is part of fr.inria.plasmalab.root.
 *
 * fr.inria.plasmalab.root is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.root is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.root.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.test;

import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

public class TestIdentifier implements InterfaceIdentifier {

	private String name;
	
	public TestIdentifier(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(InterfaceIdentifier id) {
		return id.getName().compareTo(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isBoolean() {
		return false;
	}

}
