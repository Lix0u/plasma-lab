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


public enum UnOp {
	Neg, Not;
	
	
	
	public String toString() {
		switch (this) {
		case Neg:	return "-";
		case Not:	return "!";

		default:
			return null;
		}
	}
	

	public static final boolean preCondition = chkLevel() && chkToString();

	/** Checks for completeness of methods OpLevel.of() and OpString.of() */
	private static boolean chkLevel () {
		for (UnOp op: UnOp.values())
			assert OpLevel.of(op) >= 0:
				"OpLevel.of(" + op + ") has no correct value";
			return true;
	}
	private static boolean chkToString () {
		for (UnOp op: UnOp.values())
			assert op.toString() != null:
				"UnOp." + op + ".toString() has no value";
		return true;
	}

	public double eval(double arg) {

		switch (this) {
		case Neg:
			 return -arg;
		case Not:
			return (arg == 0) ? 1 : 0;
		default:
			throw new Error("Internal Error: eval(" + this + ") is undefined");
		}
	}
}
