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
package fr.inria.plasmalab.rmlbis.ast.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import fr.inria.plasmalab.rmlbis.ast.expr.BinExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Function;
import fr.inria.plasmalab.rmlbis.ast.expr.ITEExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.UnExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Value;
import fr.inria.plasmalab.rmlbis.ast.expr.operators.BinOp;
import fr.inria.plasmalab.rmlbis.ast.expr.operators.BuiltIn;
import fr.inria.plasmalab.rmlbis.ast.expr.operators.UnOp;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;

public class ExprFactory {


	protected Map<String,Ref> idents = new HashMap<String,Ref>();
	protected Map<String,Expr> expressions = new HashMap<String,Expr>();

	public Expr makeRef(String name) {
		Ref found = idents.get(name);

		if (found != null) {
			if (found.target != null && found.target instanceof Identifier)
				return Content.of((Identifier)found.target);
			else
				return found;
		}
		else {
			found = new Ref(name);
			idents.put(name, found);
			return found;
		}
	}


	public Expr makeValue(int value) {
		String key = Integer.toString(value);
		
		Expr found = expressions.get(key);

		if (found == null) {
			found = new Value(value);
			found.str = key;
			expressions.put(key,found);
		}
		return found;
	}

	public Expr makeValue(double value) {
		String key = Double.toString(value);
		
		Expr found = expressions.get(key);

		if (found == null) {
			found = new Value(value);
			found.str = key;
			expressions.put(key,found);
		}
		return found;
	}

	public Expr makeValue(boolean value) {
		String key = Boolean.toString(value);
		
		Expr found = expressions.get(key);

		if (found == null) {
			found = new Value(value);
			found.str = key;
			expressions.put(key,found);
		}
		return found;
	}

	public Expr makeUnExpr(UnOp unop, Expr child) {
		String key = unop.toString() + child;
		
		Expr found = expressions.get(key);

		if (found == null) {
			found = new UnExpr(unop, child);
			found.str = key;
			expressions.put(key,found);
		}
		return found;
	}

	public Expr makeBinExpr(Expr left, BinOp binop, Expr right)  {
		String key = left + binop.toString() + right;
		
		Expr found = expressions.get(key);

		if (found == null) {
			found = new BinExpr(left, binop, right);
			found.str = key;
			expressions.put(key,found);
		}
		return found;
	}

	public Expr makeRightAssocBinExpr(BinOp op, Stack<Expr> stack) {
		Expr e = stack.pop();
		while (!stack.empty())
			e = makeBinExpr(stack.pop(), op, e);
		return e;
	}

	public Expr makeRightAssocBinExpr(Stack<BinOp> opStack, Stack<Expr> stack) {
		Expr e = stack.pop();
		while (!stack.empty())
			e = makeBinExpr(stack.pop(), opStack.pop(), e);
		return e;
	}

	public Expr makeITEExpr(Expr _if, Expr _then, Expr _else) {
		String key = _if + "?" + _then + ":" + _else;
		
		Expr found = expressions.get(key);

		if (found == null) {
			found = new ITEExpr(_if, _then, _else);
			found.str = key;
			expressions.put(key,found);
		}
		return found;
	}

	public Expr makeFunction(BuiltIn ident, Expr[] parameters) {
		String key = ident + "(";
		
		for (int i = 0; i < parameters.length - 1; i++)
			key += parameters[i].toString() + ",";
		
		if (parameters.length > 0)
			key += parameters[parameters.length - 1];  
		key += ")";
		
		Expr found = expressions.get(key);
		if (found == null) {
			found = new Function(ident, parameters);
			found.str = key;
			expressions.put(key,found);
		}
		return found;
	}

	public void flush() {
		expressions.clear();

		Map<String,Ref> toKeep = new HashMap<String,Ref>();

		for (Map.Entry<String,Ref> entry: idents.entrySet())
			if (!entry.getValue().parents.isEmpty())
				toKeep.put(entry.getKey(), entry.getValue());

	}

	public Map<String,Ref> getIdents() {
		return idents;
	}

	public void cleanIdents() {
		for (Map.Entry<String,Ref> entry: idents.entrySet())
			if (entry.getValue().parents.isEmpty())
				idents.remove(entry.getKey());
	}


}
