/**
 * This file is part of fr.inria.plasmalab.nested.
 *
 * fr.inria.plasmalab.nested is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.nested is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.nested.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.nested.ast.operators;


public enum BinOp {
	Until(true), WeakUntil(true),
	And, Or, Imp, Pmi,			// Imp = '=>', and Pmi = '<=' Pmi is internally used to always have a lazy evaluation from left to right
								// Especially consider the case (A => B) an we know that checking A is more expensive than checking B
								// In this case, we will build B <= A, and to check it, we will first check B and then A when required. 
	Eq, Neq, Lt, Le, Gt, Ge,
	Add, Min, Mul, Div;

	private boolean temporal;

	private BinOp() {
		temporal = false;
	}

	private BinOp(boolean temporal) {
		this.temporal = temporal;
	}

	public boolean isTemporal() {
		return temporal;
	}


	public String toString() {
		switch (this) {

		case Until:	return "U";		case WeakUntil: return "W";			

		case And:	return "&"; 	case Or: return "|";
		case Imp:	return "=>";

		case Eq: return "=";		case Neq:return "!=";
		case Lt: return "<";		case Le: return "<=";
		case Gt: return ">";		case Ge: return ">=";

		case Add: return "+";		case Min: return "-";
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

}
