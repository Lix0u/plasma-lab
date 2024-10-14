/**
 * This file is part of fr.inria.plasmalab.altl.
 *
 * fr.inria.plasmalab.altl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.altl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.altl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.altl.printer;

import fr.inria.plasmalab.altl.ast.AVisitor;
import fr.inria.plasmalab.altl.ast.Adaptive;
import fr.inria.plasmalab.altl.ast.IdentExpr;
import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.bltl.ast.nodes.Expr1;
import fr.inria.plasmalab.bltl.ast.nodes.Expr2;
import fr.inria.plasmalab.bltl.ast.nodes.StepBoundExpr1;
import fr.inria.plasmalab.bltl.ast.nodes.StepBoundExpr2;
import fr.inria.plasmalab.bltl.ast.nodes.TimeBoundExpr1;
import fr.inria.plasmalab.bltl.ast.nodes.TimeBoundExpr2;
import fr.inria.plasmalab.bltl.ast.nodes.generics.AbsExpr1;
import fr.inria.plasmalab.bltl.ast.nodes.generics.AbsExpr2;
import fr.inria.plasmalab.bltl.ast.operators.OpLevel;
import fr.inria.plasmalab.bltl.ast.printer.Comparator;


public class AComparator extends Comparator implements AVisitor {

	private int level;
	final int adaptiveLevel = -1; 

	@Override
	public void setBaseExpr(AbsExpr1 expr) {
		this.level = OpLevel.of(expr.op());
	}

	@Override
	public void setBaseExpr(AbsExpr2 expr) {
		this.level = OpLevel.of(expr.op());
	}
	
	public void setBaseExpr(Adaptive expr) {
		this.level = adaptiveLevel;
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
	public void visit(IdentExpr expr) {
		doOnExpr(expr);
	}

	@Override
	public void visit(Adaptive expr) {
		this.level -= adaptiveLevel;		
	}

}