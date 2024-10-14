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
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Formula;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Parameter;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.visitors.IdentVisitor;

/** Return the expression contained by the identifier */
public class Content implements IdentVisitor {

	private Expr result;

	@Override
	public void visit(Constant constant) {
		result = constant.ref;
	}

	@Override
	public void visit(Formula formula) {
		result = formula.body;
	}

	@Override
	public void visit(Variable variable) {
		result = variable.ref;
	}

	@Override
	public void visit(Parameter parameter) {
		result = parameter.ref;		
	}
	
	@Override
	public void visit(Identifier id) {
		result = id.ref;		
	}
	
	public static Expr of(Identifier ident) {
		Content ct = new Content();
		ident.accept(ct);
		return ct.result;
	}

	

}
