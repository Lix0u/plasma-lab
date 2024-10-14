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

public class PlasmaSyntaxException extends PlasmaDataException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7318244845395281751L;
	private static final String MSG = "Syntax error in a data.";

	public PlasmaSyntaxException(){
		super(MSG);
	}
	public PlasmaSyntaxException(String message){
		super(message);
	}
	public PlasmaSyntaxException(String message, Throwable cause){
		super(message+"\n"+cause.getMessage(), cause);
	}
	public PlasmaSyntaxException(Throwable cause){
		super(MSG+"\n"+cause.getMessage(), cause);
	}

}
