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
package fr.inria.plasmalab.nested.ast.tools.printer;

import java.util.Stack;

import fr.inria.plasmalab.nested.ast.Expr;
import fr.inria.plasmalab.nested.ast.nodes.Bool;
import fr.inria.plasmalab.nested.ast.nodes.Expr1;
import fr.inria.plasmalab.nested.ast.nodes.Expr2;
import fr.inria.plasmalab.nested.ast.nodes.Floating;
import fr.inria.plasmalab.nested.ast.nodes.Ident;
import fr.inria.plasmalab.nested.ast.nodes.Int;
import fr.inria.plasmalab.nested.ast.nodes.Proba;
import fr.inria.plasmalab.nested.ast.nodes.Temp1;
import fr.inria.plasmalab.nested.ast.nodes.Temp2;
import fr.inria.plasmalab.nested.ast.visitors.Visitor;

public class PrettyPrinter implements Visitor {
	
	protected Stack<String> stack = new Stack<String>();
	private Comparator	cmp;
	private final boolean	left = true,
							right = false,
							single = false;

	//TODO Does not handle the Argument of the Temporal Operators...
	public String getChild(Expr expr, boolean left) {
		int val = cmp.eval(expr);
		//right assoc !
		if (val == 0 && left || val > 0)
			return "(" + stack.pop() + ")";
		else
			return stack.pop();
	}
	
	
	@Override
	public void visit(Expr1 expr) {
		cmp = new Comparator(expr);
		
		String str = getChild(expr.child, single); //stack.pop();
		str = expr.op.toString() + str;
		stack.push(str);
	}

	@Override
	public void visit(Expr2 expr) {
		cmp = new Comparator(expr);
		
		String	strR = getChild(expr.right, right), ///stack.pop(), //Here, order is important
				strL = getChild(expr.left, left); //stack.pop();
		stack.push(strL + " " + expr.op.toString() + " " + strR);
	}

	
	@Override
	public void visit(Temp1 expr) {
		cmp = new Comparator(expr);
		
		String str = getChild(expr.child, single); //stack.pop();

		str = expr.op.toString() + str;
		stack.push(str);
	}
	
	@Override
	public void visit(Temp2 expr) {
		cmp = new Comparator(expr);
		
		String	strR = getChild(expr.right, right), ///stack.pop(), //Here, order is important
				strL = getChild(expr.left, left); //stack.pop();
		stack.push(strL + " " + expr.op.toString() + " " + strR);
	}
	
	@Override
	public void visit(Floating expr) {
		stack.add(Double.toString(expr.value));
	}

	@Override
	public void visit(Int expr) {
		stack.add(Integer.toString(expr.value));
	}

	@Override
	public void visit(Ident expr) {
		stack.add(expr.value);
	}

	@Override
	public void visit(Bool expr) {
		stack.add(Boolean.toString(expr.value != 0));
	}
	
	
	public String of(Expr root) {
		stack.clear();
		root.accept(this);
		return stack.pop();
	}



	
	@Override
	public void visit(Proba proba) {
		String str = getChild(proba.child, single); //stack.pop();
		str = "Pr >= " + proba.proba + " [" + str + " ]";
		stack.push(str);
	}
}
