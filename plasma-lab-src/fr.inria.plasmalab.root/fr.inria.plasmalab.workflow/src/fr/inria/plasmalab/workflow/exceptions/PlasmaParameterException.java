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
package fr.inria.plasmalab.workflow.exceptions;

public class PlasmaParameterException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4164376701817074755L;

	private static final String MSG = "An exception occured while configuring the parameters of an experiment.";

	public PlasmaParameterException(){
		super(MSG);
	}
	public PlasmaParameterException(String message){
		super(message);
	}
	public PlasmaParameterException(String message, Throwable cause){
		super(message, cause);
	}
	public PlasmaParameterException(Throwable cause){
		super(MSG+"\n"+cause.getMessage(), cause);
	}
}
