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
package fr.inria.plasmalab.nested.ast.tools.checking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import fr.inria.plasmalab.nested.ast.tools.printer.Comparator;
import fr.inria.plasmalab.nested.ast.visitors.Visitor;
import fr.inria.plasmalab.workflow.concrete.Variable;
import fr.inria.plasmalab.workflow.concrete.ranged.RangedNumbers;

public class PropertyInstantiator implements Visitor {

	@SuppressWarnings("unused")
	private Map<String, Variable> varMap;
	private RangedNumbers allVar;
	private Stack<String> stack;
	private Comparator cmp;
	
	private final boolean	left = true,
							right = false,
							single = false;
	
	public String getChild(Expr expr, boolean left) {
		int val = cmp.eval(expr);
		//right assoc !
		if (val == 0 && left || val > 0)
			return "(" + stack.pop() + ")";
		else
			return stack.pop();
	}
	
	public PropertyInstantiator(Map<String, Variable> varMap){
		this.varMap = varMap;
		allVar = new RangedNumbers(new ArrayList<Variable>(varMap.values()));
		stack = new Stack<String>();
	}

	public List<String> instantiate(Expr root) {
		List<String> properties = new ArrayList<String>();
		
		allVar.init();
		do {
			root.accept(this);
			properties.add(stack.pop());
			System.out.println(properties.get(0));
		} while (allVar.incr());

		allVar.setToLower();
		return properties;
	}

	@Override
	public void visit(Temp1 expr) {
		cmp = new Comparator(expr);
		
		String child = getChild(expr.child, single); //stack.pop();
		String bound = (expr.unitIsStep) ? "<= #" + ((int)(expr.bound))
				 						 :  "<= " + (expr.bound);
		
		stack.push(expr.op + bound + " " + child);
	}

	@Override
	public void visit(Temp2 expr) {
		cmp = new Comparator(expr);
		
		String	rightStr = getChild(expr.right, right),
				leftStr = getChild(expr.left, left);

		String bound = (expr.unitIsStep) ? "<= #" + ((int)(expr.bound))
										 :  "<= " + (expr.bound);

		leftStr += " " + expr.op +  bound + " " + rightStr;
		stack.push(leftStr);
	}

	@Override
	public void visit(Expr1 expr) {
		cmp = new Comparator(expr);
		
		String child = getChild(expr.child, single); //stack.pop();
		
		stack.push(expr.op + child);
	}

	@Override
	public void visit(Expr2 expr) {
		cmp = new Comparator(expr);
		
		String 	rightStr = getChild(expr.right, right),
				leftStr = getChild(expr.left, left); 
		
		stack.push(leftStr + expr.op + rightStr);
	}

	@Override
	public void visit(Floating fExpr) {
		stack.push(String.valueOf(fExpr.value));
	}

	@Override
	public void visit(Int iExpr) {
		stack.push(String.valueOf(iExpr.value));

	}

	@Override
	public void visit(Ident idExpr) {
		stack.push(idExpr.value);
	}

	@Override
	public void visit(Bool bExpr) {
		stack.push(String.valueOf(bExpr.value));
	}

	@Override
	public void visit(Proba proba) {
		String str = getChild(proba.child, single); //stack.pop();
	
		str = "Pr >= " + proba.proba + " [" + str + " ]";
		stack.push(str);
	}
}
