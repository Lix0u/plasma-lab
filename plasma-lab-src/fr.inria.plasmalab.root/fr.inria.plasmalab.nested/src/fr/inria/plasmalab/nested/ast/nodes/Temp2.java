/**
 * This file is part of fr.inria.plasmalab.nested.
 *
 * fr.inria.plasmalab.nested is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.nested is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.nested.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.nested.ast.nodes;

import fr.inria.plasmalab.nested.ast.Expr;
import fr.inria.plasmalab.nested.ast.operators.BinOp;
import fr.inria.plasmalab.nested.ast.visitors.ManualVisitor;
import fr.inria.plasmalab.nested.ast.visitors.Visitor;

public class Temp2 extends Expr2 {

	final public double bound;
	final public boolean unitIsStep;

	public Temp2(Expr left, BinOp binop, double timeBound, Expr right) {
		super(left, binop, right);
		this.bound = timeBound;
		this.unitIsStep = false;
	}
	
	public Temp2(Expr left, BinOp binop, int stepBound, Expr right) {
		super(left, binop, right);
		this.bound = stepBound;
		this.unitIsStep = true;
	}
	
	
	@Override
	public void accept(Visitor v) {
		left.accept(v);
		right.accept(v);
		v.visit(this);
	}

	@Override
	public void accept(ManualVisitor v) {
		v.visit(this);
	}

}
