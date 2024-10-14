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

@Deprecated
public class PlasmaUnknownSMCMethodException extends PlasmaException{
	private static final long serialVersionUID = -4364367212098061949L;
	private static final String MSG = "The method you are trying to use seems to"
			+ " not be handled by plasma.\nPlease report this error.";

	public PlasmaUnknownSMCMethodException(){
		super(MSG);
	}
	public PlasmaUnknownSMCMethodException(String message){
		super(message);
	}
	public PlasmaUnknownSMCMethodException(String message, Throwable cause){
		super(message, cause);
	}
	public PlasmaUnknownSMCMethodException(Throwable cause){
		super(MSG+"\n"+cause.getMessage(), cause);
	}
}
