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
package fr.inria.plasmalab.bltl.ast.nodes;

import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.bltl.ast.nodes.generics.AbsTemporalExpr2;
import fr.inria.plasmalab.bltl.ast.nodes.generics.ITimeBound;
import fr.inria.plasmalab.bltl.ast.operators.BinOp;
import fr.inria.plasmalab.bltl.ast.visitors.ManualVisitor;
import fr.inria.plasmalab.bltl.ast.visitors.Visitor;

public class TimeBoundExpr2 extends AbsTemporalExpr2 implements ITimeBound {

	public Expr timebound;

	public TimeBoundExpr2(Expr left, BinOp binop, Expr timebound, Expr right) {
		super(left, binop, right);
		this.timebound = timebound;
	}
	@Override
	public void accept(Visitor v) {
		super.accept(v);
		v.visit(this);
	}

	@Override
	public void accept(ManualVisitor v) {
		v.visit(this);
	}
	@Override
	public Expr getTimebound() {
		return timebound;
	}
}
