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
package fr.inria.plasmalab.bltl.ast.visitors;

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

public abstract class BasicVisitor implements Visitor {
	
	public abstract void doVisit(Expr root);

	@Override
	public void visit(TimeBoundExpr1 expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TimeBoundExpr2 expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StepBoundExpr1 expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StepBoundExpr2 expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Expr1 expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Expr2 expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FloatExpr expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IntExpr expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IdentExpr expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BoolExpr expr) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void visit(DeadlockExpr expr) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void visit(RewardExpr expr) {
		// TODO Auto-generated method stub

	}
}
