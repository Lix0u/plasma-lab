/**
 * This file is part of fr.inria.plasmalab.rmlbis.
 *
 * fr.inria.plasmalab.rmlbis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.rmlbis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.rmlbis.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.rmlbis.ast.processing;

import java.util.Collection;

import fr.inria.plasmalab.rmlbis.ast.expr.BinExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Function;
import fr.inria.plasmalab.rmlbis.ast.expr.ITEExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.UnExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Value;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ManualVisitor;


/** Update the values of the expressions when a set of ref have been updated. 
 *  Use the eval() functions of Expr to compute the new values.
 *  If the value changes the update is propagated to the parents of the expression.
 *  The update is compatible with NaN values.
 *
 */
public class Update implements ManualVisitor {
	
	public void propagate(Collection<Ref> updated) {
		for (Ref var: updated) {
			var.accept(this);
		}
	}
	
	private void eval(Expr e) {
		double oldValue = e.value;
		e.eval();

		if (oldValue != e.value) {
			for (Expr parent: e.parents)
				parent.accept(this);
		}
	}
	
	@Override
	public void visit(UnExpr unary) {
		eval(unary);
	}
	
	@Override
	public void visit(BinExpr binary) {
		eval(binary);
	}

	@Override
	public void visit(ITEExpr iteExpr) {
		eval(iteExpr);
	}
	
	@Override
	public void visit(Function fun) {
		eval(fun);
	}

	@Override
	public void visit(Value val) {
		for (Expr parent: val.parents)
			parent.accept(this);
	}

	@Override
	public void visit(Ref id) {
		for (Expr parent: id.parents)
			parent.accept(this);
	}

}
