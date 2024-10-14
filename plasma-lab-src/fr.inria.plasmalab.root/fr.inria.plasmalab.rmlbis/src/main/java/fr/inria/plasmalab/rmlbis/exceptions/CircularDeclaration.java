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
package fr.inria.plasmalab.rmlbis.exceptions;

import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;

public class CircularDeclaration extends PlasmaRuntimeException {

	private static final long serialVersionUID = 2710569233929261305L;
	
	public CircularDeclaration(InterfaceIdentifier declaration) {
		super("Circular dependency for " + declaration.getName());		
	}

}
