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
package fr.inria.plasmalab.bltl.ast.operators;


public enum UnOp {
	Neg, Not,
	Future(true), Globally(true), Next(true);
	
	private boolean temporal;
	
	private UnOp(boolean temporal) {
		this.temporal = temporal;		
	}
	
	private UnOp() {
		temporal = false;
	}
	
	
	public boolean isTemporal() {
		return temporal;
	}
	
	public String toString() {
		switch (this) {
		case Future: 	return "F";
		case Globally: 	return "G";
		case Next: 		return "X";
		
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


}
