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


import fr.inria.plasmalab.nested.ast.operators.OpLevel;
import fr.inria.plasmalab.nested.ast.Expr;
import fr.inria.plasmalab.nested.ast.operators.UnOp;
import fr.inria.plasmalab.nested.ast.visitors.ManualVisitor;
import fr.inria.plasmalab.nested.ast.visitors.Visitor;

public class Expr1 extends Expr {

	
	public final Expr child;
	public final UnOp op;


	public boolean isPriorTo(Expr1 expr) {
		return (OpLevel.of(this.op) - OpLevel.of(expr.op)) > 0;
	}
	
	public boolean isPriorTo(Expr2 expr) {
		return (OpLevel.of(this.op) - OpLevel.of(expr.op)) > 0;
	}
	

	public Expr1(UnOp unop, Expr child) {
		this.op = unop;
		this.child = child;
		this.nesting = child.nesting;
		
	//	if (op.isTemporal())
	//		throw new IllegalArgumentException(
	//				"Class Expr1 can not be instancied for " + op);
	}
	
	@Override
	public void accept(Visitor v) {
		child.accept(v);
		v.visit(this);
	}

	@Override
	public void accept(ManualVisitor v) {
		v.visit(this);
	}
	
	public static Expr NegExprOf(Expr child) {
		if (child instanceof Int) {
			((Int)child).value *= -1;
			return child;
		} else if (child instanceof Floating) {
			((Floating)child).value *= -1.0;
			return child;
		} else 
			return new Expr1(UnOp.Neg, child);
	}

	public static Expr NotExprOf(Expr child) {
		if (child instanceof Expr1) {
			Expr1 uExpr = (Expr1) child;
			if (uExpr.op == UnOp.Not)
				return uExpr.child;
			else
				return new Expr1(UnOp.Not, child);
		} else
			return new Expr1(UnOp.Not, child);
	}
}