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

import java.util.Collection;
import java.util.HashSet;

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
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.DefaultVisitor;

public class Referencies implements DefaultVisitor, IdentVisitor {
	private Collection<Ref> dependencies = new HashSet<Ref>();
	
	@Override
	public void visit(UnExpr unary) { }

	@Override
	public void visit(BinExpr binary) { }

	@Override
	public void visit(ITEExpr ite) { }

	@Override
	public void visit(Value val) { }

	@Override
	public void visit(Function fun) { }

	@Override
	public void visit(Ref id) {
		dependencies.add(id);
	}

	@Override
	public void visit(Constant constant) {
		constant.value.accept(this);
	}

	@Override
	public void visit(Formula formula) {
		formula.body.accept(this);
	}

	@Override
	public void visit(Variable variable) {
		if (variable.lowerExpr != null) {
			variable.lowerExpr.accept(this);
			variable.upperExpr.accept(this);
		}
		variable.initValue.accept(this);
	}

	@Override
	public void visit(Parameter parameter) {
		if (parameter.lowerExpr != null) {
			parameter.lowerExpr.accept(this);
			parameter.upperExpr.accept(this);
		}	
	}
	
	@Override
	public void visit(Identifier id) {	}
	
	public static Collection<Ref> of(Expr expr){
		Referencies dep = new Referencies();
		expr.accept(dep);
		return dep.dependencies;
	}
		
	public static Collection<Ref> of(Identifier ident) {
		Referencies dep = new Referencies();
		ident.accept(dep);
		return dep.dependencies;
	}



}
