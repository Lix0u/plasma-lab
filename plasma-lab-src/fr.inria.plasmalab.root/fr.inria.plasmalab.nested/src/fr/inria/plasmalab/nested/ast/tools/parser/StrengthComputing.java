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
package fr.inria.plasmalab.nested.ast.tools.parser;

import java.util.Map;
import java.util.Stack;

import fr.inria.plasmalab.nested.ast.nodes.Bool;
import fr.inria.plasmalab.nested.ast.nodes.Expr1;
import fr.inria.plasmalab.nested.ast.nodes.Expr2;
import fr.inria.plasmalab.nested.ast.nodes.Floating;
import fr.inria.plasmalab.nested.ast.nodes.Ident;
import fr.inria.plasmalab.nested.ast.nodes.Int;
import fr.inria.plasmalab.nested.ast.nodes.Proba;
import fr.inria.plasmalab.nested.ast.nodes.Temp1;
import fr.inria.plasmalab.nested.ast.nodes.Temp2;
import fr.inria.plasmalab.nested.ast.visitors.ManualVisitor;

public class StrengthComputing implements ManualVisitor {

	public static class Strength {
		public double alpha = 0;
		public double beta = 0;

		public Strength(double alpha, double beta) {
			this.alpha = alpha;
			this.beta = beta;
		}
	}

	public Map<Proba, Strength> strength;
	public Stack<Strength> context;  

	@SuppressWarnings("incomplete-switch")
	@Override
	public void visit(Temp1 expr) {
		// assumption: if I am visiting this node then I know there is a proba nested in it child.
		// For now, we only consider the time step for the bounds.
		Strength local = context.peek();
		switch (expr.op) {
		case Future:
			context.push(new Strength(local.alpha, local.beta / expr.bound));
			expr.child.accept(this);
			context.pop();
			return ;
		case Globally:
			context.push(new Strength(local.alpha / expr.bound, local.beta));
			expr.child.accept(this);
			context.pop();
			return ;
		case Next:
			expr.child.accept(this);
			return ;
		}
// TODO Auto-generated method stub

	}

	@Override
	public void visit(Temp2 expr) {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void visit(Expr1 expr) {
		// assumption: if I am visiting this node then I know there is a proba nested in it child.
		// For now, we only consider the time step for the bounds.
		switch (expr.op) {
		case Neg: 	case Not:
			expr.child.accept(this);
		}

	}

	@Override
	public void visit(Expr2 expr) {
		switch (expr.op) {
		case And:
			if (expr.left.nesting > 0) {			//left has nesting => right has it also See Expr2 constructor...
				Strength local = context.peek();
				context.push(new Strength(local.alpha / 2, local.beta));
				expr.left.accept(this);
				expr.right.accept(this);
				context.pop();
			} else if (expr.right.nesting > 0)  //only right has nested proba // we do not need to tag left child: we skip it.
				expr.right.accept(this);
			// else { } //no nested proba => nothing to do...
			return ;
		case Or:
			if (expr.left.nesting > 0) {
				Strength local = context.peek();
				context.push(new Strength(local.alpha, local.beta / 2));
				expr.left.accept(this);
				expr.right.accept(this); 
				context.pop();
			} else if (expr.right.nesting > 0) 
				expr.right.accept(this); // we reuse the same strength <\alpha, \beta> for the right child
			// else { } no nested proba: nothing to do
			return ;
		case Imp:
			if (expr.left.nesting > 0) {
				Strength local = context.peek();
				double alpha = local.alpha,
						beta = local.beta / 2;

				context.push(new Strength(beta, alpha));
				expr.left.accept(this);
				context.pop();

				context.push(new Strength(alpha, beta));
				expr.right.accept(this);
				context.pop();
			} else if (expr.right.nesting > 0) //only right has nested proba 
				expr.right.accept(this); 
			// else no nested proba 
			return ;

		case Pmi: // right => left
			if (expr.left.nesting > 0) {
				Strength local = context.peek();
				double alpha = local.alpha,
						beta = local.beta / 2;

				context.push(new Strength(alpha, beta));
				expr.left.accept(this);
				context.pop();

				context.push(new Strength(beta, alpha));
				expr.right.accept(this);
				context.pop();
			} else if (expr.right.nesting > 0) //only right has nested proba 
				expr.right.accept(this); 
			// else no nested proba 
			return ;

		case Eq:	case Neq:
		case Lt: case Le: case Gt:	case Ge: // purely arithmetic => no nested proba
		case Add: case Min: case Mul: 	case Div:
			return ;
		default:
			//TODO Until WeakUntil Until
		}
	}

	@Override
	public void visit(Floating expr) { }

	@Override
	public void visit(Int expr) {	}

	@Override
	public void visit(Ident expr) { }

	@Override
	public void visit(Bool expr) { }

	@Override
	public void visit(Proba proba) { }

}
