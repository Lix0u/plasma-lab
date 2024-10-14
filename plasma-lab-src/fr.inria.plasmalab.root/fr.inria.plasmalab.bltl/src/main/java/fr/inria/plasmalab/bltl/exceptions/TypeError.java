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

import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

@Deprecated
public class TypeError extends PlasmaDataException {
	private static final long serialVersionUID = 5971376995640970950L;

	public Expr expr;
	public Expr.Type expected;
	public Expr.Type found;
	
	public TypeError(Expr expr, Expr.Type found, Expr.Type expected) {
		this.expr = expr;
		this.expected = expected; 
		this.found = found;
	}
	
	@Override
	public  String getMessage(){
		String str = "";
		
		if (expr.line >= 0) 
			str += "line " + expr.line;
		if (expr.start >= 0 && expr.end >= 0) {
			str += (str == "")? "" : ", ";   
			str += "char " + expr.start + " to " + expr.end + "\n";
		} else {
			str += (str == "")? "" : "\n";
			str += "Type Error: it must be " + expected + " instead of " + found + "\n";
		}
		return str;
	}
	
	@Override
	public String toString(){
		return getMessage();
	}
}
