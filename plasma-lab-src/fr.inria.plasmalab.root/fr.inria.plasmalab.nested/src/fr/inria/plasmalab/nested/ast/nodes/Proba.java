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
import fr.inria.plasmalab.nested.ast.visitors.ManualVisitor;
import fr.inria.plasmalab.nested.ast.visitors.Visitor;

public class Proba extends Expr {

	public double alpha1 = 1;
	public double beta1 = 1;
	public double proba;
	public double delta = 1;

	
	public final Expr child;

	public Proba (Expr child, double proba) {
		this.child = child;
		this.nesting = child.nesting + 1;
		this.proba = proba;
		//this.alpha = alpha;
		//this.beta = beta;
	}
		
	@Override
	public void accept(Visitor v) {
		child.accept(v);
		v.visit(this);
	}

	@Override
	public void accept(ManualVisitor v) {
		v.visit(this);
	}

}
