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
package fr.inria.plasmalab.nested.ast.checker;

import java.util.HashMap;
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

public class Strength implements ManualVisitor {

   
    public Map<Proba,AlphaBeta> strength = new HashMap<Proba, AlphaBeta>();;
    public Stack<AlphaBeta> context = new Stack<AlphaBeta>();

    @SuppressWarnings("incomplete-switch") 
	@Override
	public void visit(Temp1 expr) {
        // assumption: if I am visiting this node then I know there is a proba nested in it child.
        // For now, we only consider the steps for the bounds.
        AlphaBeta local = context.peek();
        switch (expr.op) {
        case Future:
            context.push(new AlphaBeta(local.alpha, local.beta / expr.bound));
            expr.child.accept(this);
            context.pop();
            return ;
        case Globally:
            context.push(new AlphaBeta(local.alpha / expr.bound, local.beta));
            expr.child.accept(this);
            context.pop();
            return ;
        case Next:
            expr.child.accept(this);
            return ;
         // there is no default case to detect when an operator that should be covered is not 
         // 
        }
     }

    @Override
	public void visit(Temp2 expr) {
    	 AlphaBeta local = context.peek();
    	 context.push(new AlphaBeta(local.alpha / (expr.bound + 1), local.beta / (expr.bound + 1)));
    	 expr.left.accept(this);
    	 expr.right.accept(this);
    	 context.pop();
        
    }

    @SuppressWarnings("incomplete-switch") // volontaire le switch explose si un opérateur manquant n'est pas traité: Bug d'implem 
    @Override
	public void visit(Expr1 expr) {
        // assumption: if I am visiting this node then I know there is a proba nested in it child.
        // For now, we only consider the time step for the bounds.
        switch (expr.op) {
        case Neg:
            expr.child.accept(this); // no nested proba allowed in it
        case Not:
        	AlphaBeta local = context.peek();
            context.push(new AlphaBeta(local.beta, local.alpha));
            expr.child.accept(this);
        }

    }
    
    @SuppressWarnings("incomplete-switch") // volontaire le switch explose si un opérateur manquant n'est pas traité
	@Override
	public void visit(Expr2 expr) {
		switch (expr.op) {
        case And:
            if (expr.left.nesting > 0) {			//left has nesting => right has it also See Expr2 constructor...
                AlphaBeta local = context.peek();
                context.push(new AlphaBeta(local.alpha / 2, local.beta));
                expr.accept(this);
                expr.left.accept(this);
                expr.right.accept(this);
                context.pop();
            } else if (expr.right.nesting > 0)  //only right has nested proba // we do not need to tag left child: we skip it.
                expr.right.accept(this);
            // else { } //no nested proba => nothing to do...
            return ;
        case Or:
            if (expr.left.nesting > 0) {
                AlphaBeta local = context.peek();
                context.push(new AlphaBeta(local.alpha, local.beta / 2));
                expr.left.accept(this);
                expr.right.accept(this);
                context.pop();
            } else if (expr.right.nesting > 0)
                expr.right.accept(this); // we reuse the same strength <\alpha, \beta> for the right child
            // else { } no nested proba: nothing to do
            return ;
        case Imp:
            if (expr.left.nesting > 0) {
                AlphaBeta local = context.peek();
                double alpha = local.alpha,
                    beta = local.beta / 2;

                context.push(new AlphaBeta(beta, alpha));
                expr.left.accept(this);
                context.pop();

                context.push(new AlphaBeta(alpha, beta));
                expr.right.accept(this);
                context.pop();
            } else if (expr.right.nesting > 0) //only right has nested proba
                expr.right.accept(this);
            
            // else no nested proba
            return ;
        case Pmi: // right => left
            if (expr.left.nesting > 0) {
                AlphaBeta local = context.peek();
                double alpha = local.alpha,
                    beta = local.beta / 2;

                context.push(new AlphaBeta(alpha, beta));
                expr.left.accept(this);
                context.pop();

                context.push(new AlphaBeta(beta, alpha));
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
        // default: Until WeakUntil Until
        }
    }

    @Override
	public void visit(Floating expr) { }

    @Override
	public void visit(Int expr) { }

    @Override
	public void visit(Ident expr) { }

    @Override
	public void visit(Bool expr) { }

    @Override
    public void visit(Proba proba) {

    	strength.put(proba, context.peek());
    	if (proba.nesting > 1) {
    		AlphaBeta ab = new AlphaBeta(proba.alpha1, proba.beta1);
    		context.push(ab);
    		proba.child.accept(this);
    		context.pop();
    	}
    }
    
    public AlphaBeta strengthOf(Proba p) {
    	return strength.get(p);
    }
}

