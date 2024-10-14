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
package fr.inria.plasmalab.rmlbis.ast.expr.types;

/**
 * First draft for the basic types of PRISM.
 * BEWARE: I assumed that RangedInt is a subType of Integer as usually.
 * 		BUT the usage of subType remains subtle in some context like in 
 * 		VariableProcessing.typeCheck(), when the variable is a RangedInt:
 * 		the attached expression initValue, lowerExpr, upperExpr have the type
 * 		Integer, i.e. the most precise type which can be inferred over an expression...
 * 		It is well-typed but we have var.initValue.subTypeOf(var.type) == false 
 * 			 
 * @author bboyer
 *
 */
public enum Type {
	Boolean,
	Double,
	Clock,
	Integer;

	public String toString() {
		switch (this) {			
		case Boolean:	return "bool";
		case Double: return "double";
		case Integer:	return "int";
		case Clock: return "clock";
		
		default:
			return "";
		}
	}

	public boolean subTypeOf(Type t) {
		switch (this) {
		case Boolean:
			return (t == Boolean);
		case Clock:
			return (t == Clock || t == Double);
		case Double:
			return (t == Double || t == Clock);
		case Integer:
			return (t == Double || t == Integer || t == Clock); 
		default:
			return false;
		}		
	}
}

