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

import fr.inria.plasmalab.rmlbis.ast.expr.operators.BuiltIn;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.DefaultVisitor;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ManualVisitor;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ReversedVisitor;

public class Function extends Expr {

	public BuiltIn ident;
	public Expr[] parameters;

	public Function(BuiltIn ident, Expr[] parameters) {
		this.ident = ident;
		this.parameters = parameters;
		for (Expr parameter: parameters)
			parameter.parents.add(this);
		this.eval();
	}

	@Override
	public void eval() {

		boolean mustBeNan = false;
		for (Expr p: parameters)
			mustBeNan |= Double.isNaN(p.value);
		if (mustBeNan)
			this.value = Double.NaN;
		else
			this.value = ident.eval(parameters);
	}	

	@Override
	public void accept(DefaultVisitor v) {
		for(Expr parameter: parameters)
			parameter.accept(v);
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