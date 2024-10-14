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

import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.DefaultVisitor;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ManualVisitor;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ReversedVisitor;

public class Value extends Expr {

	
	public Value(boolean value) {
		super.type = Type.Boolean;
		this.value = value ? 1. : 0.;
	}
	
	public Value(int value) {
		this.type = Type.Integer;
		this.value = (double) value;
	}
	
	public Value(double value) {
		this.type = Type.Double;
		this.value = value;
	}
	
	@Override
	public void eval() { }

	@Override
	public void accept(DefaultVisitor v) {
		v.visit(this);
	}
	
	@Override
	public void accept(ManualVisitor v) {
		v.visit(this);
	}

	@Override
	public void accept(ReversedVisitor v) {
		v.visit(this);
		
		for (Expr parent: parents)
			parent.accept(v);
	}
	
}
