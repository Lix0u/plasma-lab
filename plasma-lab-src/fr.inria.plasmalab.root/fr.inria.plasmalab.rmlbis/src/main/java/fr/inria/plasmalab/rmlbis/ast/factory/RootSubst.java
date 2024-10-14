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

import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Formula;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Parameter;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.visitors.IdentVisitor;

public class RootSubst implements IdentVisitor {

	private Ref		ref;
	private Expr	body;

	@Override
	public void visit(Constant constant) {
		if (constant.value.equals(ref))
			constant.value = body;
	}

	@Override
	public void visit(Formula formula) {
		if (formula.body.equals(ref))
			formula.body = body;
	}

	@Override
	public void visit(Variable variable) {
		if (variable.lowerExpr != null) {
			if (variable.lowerExpr.equals(ref))	variable.lowerExpr = body;
			if (variable.upperExpr.equals(ref))	variable.upperExpr = body;
		}
		if (variable.initValue.equals(ref))	variable.initValue = body;
	}
	
	@Override
	public void visit(Parameter parameter) {
		if (parameter.lowerExpr != null) {
			if (parameter.lowerExpr.equals(ref))	parameter.lowerExpr = body;
			if (parameter.upperExpr.equals(ref))	parameter.upperExpr = body;
		}
		if (parameter.initValue.equals(ref))	parameter.initValue = body;
	}
	
	@Override
	public void visit(Identifier id) { }
	
	public void apply(Identifier context, Ref ref, Expr body) {
		this.ref = ref;
		this.body = body;
		context.accept(this);
	}

}
