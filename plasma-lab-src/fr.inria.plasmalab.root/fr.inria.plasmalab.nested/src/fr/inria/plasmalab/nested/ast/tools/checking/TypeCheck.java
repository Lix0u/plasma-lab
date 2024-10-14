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
import fr.inria.plasmalab.nested.ast.tools.checking.exceptions.TypeError;
import fr.inria.plasmalab.nested.ast.tools.checking.exceptions.UndefinedVariable;
import fr.inria.plasmalab.nested.ast.visitors.Visitor;


public class TypeCheck implements Visitor {

	private Stack<Expr.Type> types = new Stack<Expr.Type>() ;
	private Map<String, Expr.Type> declarations;


	public TypeCheck(Map<String, Expr.Type> declarations) {
		this.declarations = declarations;
	}

	@Override
	public void visit(Expr1 expr){
		Expr.Type	child = types.pop();

		switch (expr.op) {
		case Not: case Future: case Globally: case Next:
			if (child != Expr.Type.Bool)
				throw new TypeError(expr.child, child, Expr.Type.Bool);
			types.push(Expr.Type.Bool);
			break;

		case Neg:
			if (child != Expr.Type.Float && child != Expr.Type.Int)
				throw new TypeError(expr.child, child, Expr.Type.Float);
			types.push(child);
			break;

		default:
			throw new RuntimeException("TypeChecking.visit(Expr2) does not handle: " + expr.op);
		}
	}

	@Override
	public void visit(Expr2 expr) {

		Expr.Type	right = types.pop(), //types of right(or left)-member
					left = types.pop();

		switch (expr.op) {

		case Until: case WeakUntil:		case And: case Or: case Imp:
			if (left != Expr.Type.Bool)
				throw new TypeError(expr.left, left, Expr.Type.Bool);
			if (right != Expr.Type.Bool)
				throw new TypeError(expr.right, right, Expr.Type.Bool);
			types.push(Expr.Type.Bool);
			break;

		case Eq: case Neq: //defined as polymorphic operators 
			if (left != right)
				throw new TypeError(expr.right, right, left);
			types.push(Expr.Type.Bool);
			break;

		case Lt: case Le: case Gt: case Ge:
			if (left != Expr.Type.Int)
				throw new TypeError(expr.left, left, Expr.Type.Int);
			if (right != Expr.Type.Int)
				throw new TypeError(expr.right, right, Expr.Type.Int);
			types.push(Expr.Type.Bool);
			break;

		case Add: case Min: case Mul: case Div:
			if (left != Expr.Type.Int)
				throw new TypeError(expr.left, left, Expr.Type.Int);
			if (right != Expr.Type.Int)
				throw new TypeError(expr.right, right, Expr.Type.Int);
			types.push(Expr.Type.Int);
			break;

		default:
			throw new IllegalArgumentException(
					"TypeChecking.visit(Expr1) does not handle: " + expr.op);

		}
	}



	public void visit(Floating fExpr){
		types.push(Expr.Type.Float);
	}

	public void visit(Int intExpr){
		types.push(Expr.Type.Int);
	}

	public void visit(Ident idExpr){
		Expr.Type type  = declarations.get(idExpr.value);
		
		if (type == null)
			throw new UndefinedVariable(idExpr);
		else
			types.push(declarations.get(idExpr.value));
	}

	public void visit(Bool bExpr){
		types.push(Expr.Type.Bool);
	}


	@Override
	public void visit(Temp1 expr) {
		visit((Expr1) expr);
	}


	@Override
	public void visit(Temp2 expr) {
		visit((Expr2)expr);
	}
	
	@Override
	public void visit(Proba proba) {
		Expr.Type	child = types.pop();

		if (child != Expr.Type.Bool)
				throw new TypeError(proba.child, child, Expr.Type.Bool);
		types.push(Expr.Type.Bool);
			
	}


	//TODO : A Reprendre le Type checker est supposé gérer les variables de l'environnement!!!!
	public static void start(Expr e) {
		TypeCheck tc = new TypeCheck(null/* TODO PAS BON!!!!!!*/);
		e.accept(tc);
	}

	
	

}