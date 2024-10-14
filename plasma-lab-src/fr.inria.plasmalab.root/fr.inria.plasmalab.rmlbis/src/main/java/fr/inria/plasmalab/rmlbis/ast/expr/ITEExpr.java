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
package fr.inria.plasmalab.rmlbis.ast.expr;

import fr.inria.plasmalab.rmlbis.ast.visitors.expr.DefaultVisitor;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ManualVisitor;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ReversedVisitor;


public class ITEExpr extends Expr {

	public Expr ifCond;
	public Expr thenCase;
	public Expr elseCase;

	public ITEExpr(Expr ifCond, Expr thenCase, Expr elseCase) {
		this.ifCond = ifCond;
		this.thenCase = thenCase;
		this.elseCase = elseCase;
		
		ifCond.parents.add(this);
		thenCase.parents.add(this);
		elseCase.parents.add(this);

		this.eval();
	}
	
	@Override
	public void eval() {
		if (Double.isNaN(ifCond.value))
			this.value = Double.NaN;
		else
			this.value = ifCond.value != 0 ? thenCase.value : elseCase.value;
	}
	
	@Override
	public void accept(DefaultVisitor v) {
		ifCond.accept(v);
		thenCase.accept(v);
		elseCase.accept(v);
		v.visit(this);
	}

	@Override
	public void accept(ManualVisitor v) {
		v.visit(this);
	}

	@Override
	public void accept(ReversedVisitor v) {
		v.visit(this);

		for (Expr parent : this.parents)
			parent.accept(v);
	}

}
