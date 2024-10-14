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
import fr.inria.plasmalab.bltl.ast.visitors.ManualVisitor;

public class PrettyPrinter implements ManualVisitor {

	protected String result; // stack = new Stack<String>();
	protected Comparator	cmp;
	protected final boolean	left = true,
			right = false,
			single = false;

	public PrettyPrinter() {
		cmp = new Comparator();
	}

	protected void setComparator(Comparator cmp) {
		this.cmp = cmp;
	}
	
	//TODO Does not handle the Argument of the Temporal Operators...
	protected void visitChild(Expr expr, boolean left) {
		int val = cmp.eval(expr);
		expr.accept(this);
		if (val == 0 && left || val > 0)
			result = "(" + result + ")";
	}	
	protected void visitBound(Expr expr) {
		expr.accept(this);
		if (expr instanceof Expr1 || expr instanceof Expr2)
			result =  "(" + result + ")";
	}
	
	@Override
	public void visit(TimeBoundExpr1 expr) {
		cmp.setBaseExpr(expr);
		String op = expr.op().toString()+"<=";
		visitBound(expr.timebound);
		op += result;
		
		cmp.setBaseExpr(expr);
		visitChild(expr.child(), single);
		result = op + " " + result;
	}

	@Override
	public void visit(TimeBoundExpr2 expr) {
		cmp.setBaseExpr(expr);
		String op = expr.op().toString()+"<=";
		visitBound(expr.timebound);
		op += result;

		cmp.setBaseExpr(expr);
		visitChild(expr.left(), left);
		String left = result;
		cmp.setBaseExpr(expr);
		visitChild(expr.right(), right);
		String right = result; 
		result = left + " " +op + " " + right;
	}

	@Override
	public void visit(StepBoundExpr1 expr) {
		cmp.setBaseExpr(expr);
		String op = expr.op().toString()+"<=#";
		visitBound(expr.stepbound);
		op += result;
		
		cmp.setBaseExpr(expr);
		visitChild(expr.child(), single);
		result = op + " " + result;
	}

	@Override
	public void visit(StepBoundExpr2 expr) {
		cmp.setBaseExpr(expr);
		String op = expr.op().toString()+"<=#";
		visitBound(expr.stepbound);
		op += result;

		cmp.setBaseExpr(expr);
		visitChild(expr.left(), left);
		String left = result;
		cmp.setBaseExpr(expr);
		visitChild(expr.right(), right);
		String right = result; 
		result = left + " " +op + " " + right;
	}


	@Override
	public void visit(Expr1 expr) {
		cmp.setBaseExpr(expr);// = new Comparator(expr);
		visitChild(expr.child(), single);
		result = expr.op().toString() + result;
	}

	@Override
	public void visit(Expr2 expr) {

		cmp.setBaseExpr(expr);
		visitChild(expr.left(), left);
		String left = result;
		cmp.setBaseExpr(expr);
		visitChild(expr.right(), right);
		String right = result; 
		result = left + " " + expr.op().toString() + " " + right;
	}

	@Override
	public void visit(FloatExpr expr) {
		result = Double.toString(expr.value);
	}

	@Override
	public void visit(IntExpr expr) {
		result = Integer.toString(expr.value);
	}

	@Override
	public void visit(IdentExpr expr) {
		result = expr.id == null ? "nullId" : expr.id.getName(); 
	}
	
	@Override
	public void visit(BoolExpr expr) {
		result = Boolean.toString(expr.value);
	}
	
	@Override
	public void visit(DeadlockExpr expr) {
		result = "deadlock";
	}
	
	@Override
	public void visit(RewardExpr expr) {
		cmp.setBaseExpr(0);
		String op = "R{" + expr.getReward() + "}";
		visitChild(expr.child(), single);
		result = op + " " + result;
	}

	protected static PrettyPrinter pp = new PrettyPrinter();

	public static String of(Expr e) {
		e.accept(pp);
		return pp.result;
	}

}
