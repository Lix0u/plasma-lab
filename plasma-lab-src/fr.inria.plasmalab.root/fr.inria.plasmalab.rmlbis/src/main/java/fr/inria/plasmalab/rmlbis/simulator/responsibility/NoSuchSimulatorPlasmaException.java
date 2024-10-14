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

import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

@Deprecated
public class NoSuchSimulatorPlasmaException extends PlasmaSimulatorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7302999497467657349L;

	private static final String MSG = "No simulator could be found to simulate this model.";

	public NoSuchSimulatorPlasmaException(){
		super(MSG);
	}
	public NoSuchSimulatorPlasmaException(String message){
		super(message);
	}
	public NoSuchSimulatorPlasmaException(String message, Throwable cause){
		super(message, cause);
	}
	public NoSuchSimulatorPlasmaException(Throwable cause){
		super(MSG, cause);
	}

}
