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
package fr.inria.plasmalab.rmlbis.ast.expr.operators;


/**
 * 
 * @author bboyer
 * The binary operators used in language "Reactive Module Language"
 * The class also implements the semantics over values of type double 
 */

public enum BinOp {
	And, Or, Imp, Iff,
	Eq, Neq, Lt, Le, Gt, Ge,
	Add, Sub, Mul, Div;


	public String toString() {
		switch (this) {			

		case And:	return "&"; 	case Or: return "|";
		case Imp:	return "=>";	case Iff: return "<=>";

		case Eq: return "=";		case Neq:return "!=";
		case Lt: return "<";		case Le: return "<=";
		case Gt: return ">";		case Ge: return ">=";

		case Add: return "+";		case Sub: return "-";
		case Mul: return "*";		case Div: return "/";

		default:
			return null;
		}
	}

	public static final boolean preCondition = chkLevel() && chkToString();

	/** Check for completeness of methods OpLevel.of() and OpString.of() */
	private static boolean chkLevel () {
		for (BinOp op: BinOp.values())
			assert OpLevel.of(op) >= 0:
				"OpLevel.of(" + op + ") has no correct value";
			return true;
	}
	private static boolean chkToString () {
		for (BinOp op: BinOp.values())
			assert op.toString()!= null:
				"BinOp." + op + ".toString() has no value";
		return true;
	}


	public double eval(double left, double right) {

		switch (this) {
		case Add: return left + right;
		case Sub: return left - right;
		case Mul: return left * right;
		case Div:
			if(right == 0)
				return Double.NaN;
			else
				return left / right;
		case Eq:  return (left == right) ? 1 : 0;
		case Iff:
			if (left != 0)
				return (right != 0) ? 1 : 0;
			else
				return (right == 0) ? 1 : 0;
		case Neq: return (left != right) ? 1 : 0;
		case Le:  return (left <= right) ? 1 : 0;
		case Lt:  return (left <  right) ? 1 : 0;
		case Ge:  return (left >= right) ? 1 : 0;
		case Gt:  return (left >  right) ? 1 : 0;
		case Imp: return (left == 0) ? 1 : right;
		case And: return (left * right != 0) ? 1 : 0;
		case Or:  return (left == 0) ? right : 1;
		default:
			throw new Error("Internal Error: eval(" + this + ") is undefined");
		}
	}

}
