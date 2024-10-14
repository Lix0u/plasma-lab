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
package fr.inria.plasmalab.bltl.ast.printer;

import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.bltl.ast.leaves.BoolExpr;
import fr.inria.plasmalab.bltl.ast.leaves.DeadlockExpr;
import fr.inria.plasmalab.bltl.ast.leaves.FloatExpr;
import fr.inria.plasmalab.bltl.ast.leaves.IdentExpr;
import fr.inria.plasmalab.bltl.ast.leaves.IntExpr;
import fr.inria.plasmalab.bltl.ast.nodes.Expr1;
import fr.inria.plasmalab.bltl.ast.nodes.Expr2;
import fr.inria.plasmalab.bltl.ast.nodes.RewardExpr;
import fr.inria.plasmalab.bltl.ast.nodes.StepBoundExpr1;
import fr.inria.plasmalab.bltl.ast.nodes.StepBoundExpr2;
import fr.inria.plasmalab.bltl.ast.nodes.TimeBoundExpr1;
import fr.inria.plasmalab.bltl.ast.nodes.TimeBoundExpr2;
import fr.inria.plasmalab.bltl.ast.nodes.generics.AbsExpr1;
import fr.inria.plasmalab.bltl.ast.nodes.generics.AbsExpr2;
import fr.inria.plasmalab.bltl.ast.operators.OpLevel;
import fr.inria.plasmalab.bltl.ast.visitors.ManualVisitor;


public class Comparator implements ManualVisitor {

	private int level;

	public void setBaseExpr(AbsExpr1 expr) {
		this.level = OpLevel.of(expr.op());
	}

	public void setBaseExpr(AbsExpr2 expr) {
		this.level = OpLevel.of(expr.op());
	}
	
	public void setBaseExpr(int l) {
		this.level = l;
	}
	
	public void doOnAbsExpr1(AbsExpr1 expr) {
		this.level -= OpLevel.of(expr.op());
	}

	public void doOnAbsExpr2(AbsExpr2 expr) {
		this.level -= OpLevel.of(expr.op());
	}

	public void doOnExpr(Expr e) {
		this.level -= OpLevel.upperLevel;
	}

	public int eval(Expr child) {
		child.accept(this);
		return level;
	}

	@Override
	public void visit(TimeBoundExpr1 expr) {
		doOnAbsExpr1(expr);
	}

	@Override
	public void visit(TimeBoundExpr2 expr) {
		doOnAbsExpr2(expr);
	}

	@Override
	public void visit(StepBoundExpr1 expr) {
		doOnAbsExpr1(expr);
	}

	@Override
	public void visit(StepBoundExpr2 expr) {
		doOnAbsExpr2(expr);
	}

	@Override
	public void visit(Expr1 expr) {
		doOnAbsExpr1(expr);
	}

	@Override
	public void visit(Expr2 expr) {
		doOnAbsExpr2(expr);
	}

	@Override
	public void visit(FloatExpr expr) {
		doOnExpr(expr);
	}

	@Override
	public void visit(IntExpr expr) {
		doOnExpr(expr);
	}

	@Override
	public void visit(IdentExpr expr) {
		doOnExpr(expr);		
	}

	@Override
	public void visit(BoolExpr expr) {
		doOnExpr(expr);
	}
	
	@Override
	public void visit(DeadlockExpr expr) {
		doOnExpr(expr);
	}
	
	@Override
	public void visit(RewardExpr expr) {
		// add zero = do nothing
	}

}