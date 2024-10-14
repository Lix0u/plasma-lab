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

import fr.inria.plasmalab.rmlbis.ast.expr.BinExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Function;
import fr.inria.plasmalab.rmlbis.ast.expr.ITEExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.UnExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Value;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Formula;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Parameter;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.visitors.IdentVisitor;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ManualVisitor;

public class Substitution implements IdentVisitor {

	private class UdapteParent implements ManualVisitor {
		private Ref		target;
		private Expr	body;

		public void setSubst(Ref target, Expr body) {
			this.target = target;
			this.body = body;
		}

		@Override
		public void visit(UnExpr unary) {
			if (unary.child == target) {
				unary.child.parents.remove(unary);
				unary.child = body;
				unary.child.parents.add(unary);
			}
			unary.eval();
		}

		@Override
		public void visit(BinExpr binary) {
			if (binary.left == target) {
				binary.left.parents.remove(binary);
				binary.left = body;
				binary.left.parents.add(binary);
			}
			if (binary.right == target) {
				binary.right.parents.remove(binary);
				binary.right = body;
				binary.right.parents.add(binary);
			}
			binary.eval();
		}

		@Override
		public void visit(ITEExpr ite) {
			if (ite.ifCond == target) {
				ite.ifCond.parents.remove(ite);
				ite.ifCond = body;
				ite.ifCond.parents.add(ite);
			}
			if (ite.thenCase == target) {
				ite.thenCase.parents.remove(ite);
				ite.thenCase = body;
				ite.thenCase.parents.add(ite);
			}
			if (ite.elseCase == target) {
				ite.elseCase.parents.remove(ite);
				ite.elseCase = body;
				ite.elseCase.parents.add(ite);
			}
			ite.eval();
		}

		@Override
		public void visit(Function fun) {
			for (int i = 0; i < fun.parameters.length; i++)
				if (fun.parameters[i] == target) {
					fun.parameters[i].parents.remove(fun);
					fun.parameters[i] = body;
					fun.parameters[i].parents.add(fun);
				}
			fun.eval();
		}

		// -------------------------------------------------------
		// Leaves are not parent: so there are not concerned.
		@Override
		public void visit(Value val) { /* NOP */ }

		@Override
		public void visit(Ref id) { /* NOP */ }
	}	// ------------ class UpdateParent ------------  

	private UdapteParent up = new UdapteParent();
	

	public Substitution() { }

	private void propagate(Expr expr) {
		expr.eval();
		if (!Double.isNaN(expr.value))
			for (Expr parent: expr.parents)
				propagate(parent);
	}

	public void apply(Ref target, Expr body) {
		up.setSubst(target, body);
		
		Expr[] toUpdate = target.parents.toArray(new Expr[target.parents.size()]); // Copy is needed because parents are modified
		for (Expr parent: toUpdate) {
			parent.accept(up);
			propagate(parent);
		}
	}

	public void apply(Identifier ident) {
		ident.accept(this);
	}
	
	@Override
	public void visit(Constant constant) {
		apply(constant.ref, constant.value);
	}

	@Override
	public void visit(Formula formula){
		apply(formula.ref, formula.body);
	}

	@Override
	public void visit(Variable variable) {
		apply(variable.ref, variable.initValue);
	}

	@Override
	public void visit(Parameter parameter) {
		apply(parameter.ref, parameter.initValue);
	}
	
	@Override
	public void visit(Identifier id) { }
	
}