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

import java.util.Map;
import java.util.Stack;

import fr.inria.plasmalab.rmlbis.ast.expr.BinExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Function;
import fr.inria.plasmalab.rmlbis.ast.expr.ITEExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.UnExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Value;
import fr.inria.plasmalab.rmlbis.ast.factory.ExprFactory;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.DefaultVisitor;

public class Renaming implements DefaultVisitor {

	public final Map<String,String> mapping;
	private ExprFactory factory;
	private Stack<Expr> results = new Stack<Expr>();

	public Renaming(ExprFactory factory, Map<String,String> mapping) {
		this.factory = factory;
		this.mapping = mapping;
	}

	public Expr apply(Expr expr) {
		if (expr == null)
			return null;
		results.clear();
		expr.accept(this);
		Expr result = results.pop();
		return result == null ? expr : result;
	}

	@Override
	public void visit(UnExpr unary) {
		Expr child = results.pop();

		if (child != null)
			results.push(factory.makeUnExpr(unary.op, child));
		else 
			results.push(null);
	}

	@Override
	public void visit(BinExpr binary) {
		Expr right = results.pop();
		Expr left = results.pop();

		if (left == null && right == null)
			results.push(null);
		else {
			if (right == null)
				right = binary.right;
			if (left == null)
				left = binary.left;
			results.push(factory.makeBinExpr(left, binary.op, right));
		}
	}

	@Override
	public void visit(ITEExpr ternary) {
		Expr else_	= results.pop();
		Expr then_	= results.pop();
		Expr if_	= results.pop();

		if (if_ == null && then_ == null && else_ == null)
			results.push(null);
		else {		
			if (if_ == null)
				if_ = ternary.ifCond;
			if (then_ == null)
				then_ = ternary.thenCase;
			if (else_ == null)
				else_ = ternary.elseCase;
			results.push(factory.makeITEExpr(if_, then_, else_));
		}

	}

	@Override
	public void visit(Function fun) {
		int size = fun.parameters.length;
		Expr[] parameters = new Expr[size];

		boolean changed = false;
		for (int i = size - 1; i >= 0; i--) {
			Expr parameter = results.pop();

			if (parameter == null)
				parameters[i] = fun.parameters[i];
			else {
				parameters[i] = parameter;
				changed = true;
			}
		}
		if (changed) 
			results.push(factory.makeFunction(fun.ident, parameters));
		else
			results.push(null);
	}

	@Override
	public void visit(Value val) {
		results.push(null);
	}

	@Override
	public void visit(Ref ident) {
		String nuName = mapping.get(ident.name); 
		if (nuName != null)
			results.push(factory.makeRef(nuName));
		else
			results.push(factory.makeRef(ident.name));
//			results.push(null);
	}

}
