/**
 * This file is part of fr.inria.plasmalab.bltl.
 *
 * fr.inria.plasmalab.bltl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bltl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bltl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.bltl.exceptions;

import fr.inria.plasmalab.bltl.ast.leaves.IdentExpr;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

public class UndefinedVariable extends PlasmaDataException {
	private static final long serialVersionUID = 477974306832391224L;
	private static final String MSG = "Undefined variable ";

	public IdentExpr idExpr;
	public InterfaceIdentifier iident;
	public String sident;
	
	public UndefinedVariable(IdentExpr idExpr) {
		super("UndefinedVariable: " + idExpr.name);
		this.idExpr = idExpr; 
		this.sident = idExpr.name;
	}
	
	public UndefinedVariable(InterfaceIdentifier iident) {
		super("UndefinedVariable: " + iident.getName());
		this.iident = iident;
		sident = iident.getName();
	}
	
	public UndefinedVariable(String sident) {
		super("UndefinedVariable: " +sident);
		this.sident = sident; 
	}
	
	
	@Override
	public  String getMessage(){
		String str = "";
		
		if(idExpr != null){ 
				str += "line " + idExpr.line + " : " +idExpr.start;
		} 
		str += MSG + " " + sident;
		return str;
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
	

}
