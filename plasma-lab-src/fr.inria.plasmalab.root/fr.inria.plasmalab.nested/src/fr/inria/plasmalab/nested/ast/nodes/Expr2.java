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
package fr.inria.plasmalab.nested.ast.nodes;

import fr.inria.plasmalab.nested.ast.Expr;
import fr.inria.plasmalab.nested.ast.operators.BinOp;
import fr.inria.plasmalab.nested.ast.visitors.ManualVisitor;
import fr.inria.plasmalab.nested.ast.visitors.Visitor;

public class Expr2 extends Expr {
	
	public final Expr left;
	public final Expr right;
	public final BinOp op;
	
	
	
	public Expr2(Expr left, BinOp binop, Expr right) {
	    // optimization purpose: we mirror the children by considering :
		// 1- Expressions are always evaluated from left to right in a lazy way
		// 2- Expressions with nested probabilities are more expensive to evaluate.
		// Thus The subexpression supposed to be easier to check is the left child.
		// In the next, we assume that for any expr2: Expr2, expr2.left.nesting > 0 => expr2.right.nesting > 0   
		if (left.nesting > right.nesting) { 
			this.nesting = left.nesting;
			
			switch (binop) {
			case Imp:
				this.op = BinOp.Pmi; break;
			case Pmi:
				this.op = BinOp.Imp; break;
			default: 
				this.op = binop;
			}
			this.left = right;
			this.right = left;
			
		} else {
			this.nesting = right.nesting;	
			this.op = binop;
			this.left = left;
			this.right = right;	
		}
		
	/*	if (op.isTemporal())
			throw new IllegalArgumentException(
					"Class Expr2 can not be instancied for " + op);
		
		if (nesting > 0 && (op == BinOp.Eq || op == BinOp.Neq))
			if (op.isTemporal())
				throw new RuntimeException(
						"No nested probabilistic is allowed in expression \"f == g\"");
					*/

	}
	
	@Override
	public void accept(Visitor v) {
		left.accept(v);
		right.accept(v);
		v.visit(this);
	}

	@Override
	public void accept(ManualVisitor v) {
		v.visit(this);
	}
}
