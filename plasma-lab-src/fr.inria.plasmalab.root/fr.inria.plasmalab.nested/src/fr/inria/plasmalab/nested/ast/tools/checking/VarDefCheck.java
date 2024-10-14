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
import java.util.Set;

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
import fr.inria.plasmalab.nested.ast.tools.checking.exceptions.UndefinedVariable;
import fr.inria.plasmalab.nested.ast.visitors.Visitor;


public class VarDefCheck implements Visitor {

	private Set<String> declarations;
	
	public VarDefCheck(Set<String> declarations) {
		this.declarations = declarations;
	}
	
	public VarDefCheck(Map<String, ?> declarations) {
		this.declarations = declarations.keySet();
	}
	
	@Override
	public void visit(Ident idExpr){
		if (!declarations.contains(idExpr.value))
			throw new UndefinedVariable(idExpr);
	}

	public void doVisit(Expr e) {
		e.accept(this);
	}

	@Override
	public void visit(Temp1 expr) { }

	@Override
	public void visit(Temp2 expr) { }

	@Override
	public void visit(Expr1 expr) { }

	@Override
	public void visit(Expr2 expr) { }

	@Override
	public void visit(Floating expr) { }

	@Override
	public void visit(Int expr) { }

	@Override
	public void visit(Bool expr) { }

	@Override
	public void visit(Proba proba) { }
}