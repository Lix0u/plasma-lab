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



public class OpLevel {

	/** Operators from the lowest to the highest priority level 
	 * 	W, U, F, G, X			  					[ level   0 ]
	 *	=>											[ level 101 ]
	 *	|											[ level 102 ]
	 * 	&											[ level 103 ]
	 * 	!											[ level 104 ]
	 * 	!= ==										[ level 110 ]
	 * 	<=, >=, <, >								[ level 201 ]
	 * 	+ -											[ level 210 ]
	 * 	*, /										[ level 211 ]
	 * 	-   (unary minus)							[ level 215 ]
	 * 
	 * 	Same line <=> Same Priority.
	 * 
	 * 	It seems that the LL(1) parser CoCo/R considers all the binary operators as
	 * 	right-associative operators
	 *
	 *	Operators are split in different enumerations (for now 4): there is exactly
	 *	one method OpLevel.of(...) for each one of these enumeration types.
	 *	
	 *	Moreover, to ensure the completeness of the declared enum in case where someone wants
	 * 	to extend one of them, the completeness of the corresponding method is ensured by an assertion
	 * 	in the corresponding enumeration type, i.e. the assertion checks that for all Constructor C
	 * 	defined by the enumeration,	OpLevel.of(C) is set ( >= 0). 
	 * 
	 */


	private static final int tempLevel = 0;
	private static final int logicLevel = 100;
	private static final int numericLevel = 200;
	
	public static final int upperLevel = 1500;

	public static int of(BinOp op) {
		switch (op) {
		case Until:		return tempLevel;
		case WeakUntil: return tempLevel;
		
		case Imp:	return logicLevel + 1;
		case Or:	return logicLevel + 2; 
		case And:	return logicLevel + 3;
		// Not return logicLevel + 4;

		case Eq: case Neq:
			return logicLevel + 10;

		case Lt: case Le:	case Gt: case Ge:
			return numericLevel + 1;

		case Add: case Min:
			return numericLevel + 10;
		case Mul: case Div:
			return numericLevel + 11;
		default:
			return -1;
		}
	}

	public static int of(UnOp op) {
		switch (op) {
		case Future: case Globally: case Next:
			return tempLevel;
		
		case Not:
			return logicLevel + 4; 
		case Neg: 
			return numericLevel + 15;
		default:
			return -1;
		}
	}
}

