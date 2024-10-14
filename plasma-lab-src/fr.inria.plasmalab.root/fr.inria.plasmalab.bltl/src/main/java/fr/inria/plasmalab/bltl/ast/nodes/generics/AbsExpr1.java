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
package fr.inria.plasmalab.bltl.ast.nodes.generics;

import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.bltl.ast.operators.OpLevel;
import fr.inria.plasmalab.bltl.ast.operators.UnOp;
import fr.inria.plasmalab.bltl.ast.visitors.Visitor;


public abstract class AbsExpr1 extends Expr {

	protected Expr child;
	protected UnOp op;


	public AbsExpr1(UnOp op, Expr child) {
		this.op = op;
		this.child = child;
	}


	public boolean isPriorTo(AbsExpr1 expr) {
		return (OpLevel.of(this.op) - OpLevel.of(expr.op)) > 0;
	}
	
	public boolean isPriorTo(AbsExpr2 expr) {
		return (OpLevel.of(this.op) - OpLevel.of(expr.op())) > 0;
	}
	@Override
	public void accept(Visitor v) {
		child.accept(v);
	}

	public UnOp op() {
		return op;
	}
	
	public Expr child() {
		return child;
	}
}
