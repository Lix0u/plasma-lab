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


public enum TerOp {
	ITE;


	public String toString() {
		switch (this) {			

		case ITE:	return "?";
		
		default:
			return null;
		}
	}

	public static final boolean preCondition = chkLevel() && chkToString();

	/** Check for completeness of methods OpLevel.of() and OpString.of() */
	private static boolean chkLevel () {
		for (TerOp op: TerOp.values())
			assert OpLevel.of(op) >= 0:
				"OpLevel.of(" + op + ") has no correct value";
			return true;
	}
	private static boolean chkToString () {
		for (TerOp op: TerOp.values())
			assert op.toString()!= null:
				"BinOp." + op + ".toString() has no value";
		return true;
	}

}
