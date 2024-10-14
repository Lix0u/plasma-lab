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

import fr.inria.plasmalab.rmlbis.ast.expr.BinExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Function;
import fr.inria.plasmalab.rmlbis.ast.expr.ITEExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.UnExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Value;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ManualVisitor;


public class Printer implements ManualVisitor {

	protected String value;
	
	public Printer() {
		this.value = "";
	}
	
	public Printer(String init) {
		this.value = init;
	}
	
	public String getValue() {
		return this.value;
	}

	@Override
	public void visit(UnExpr unary) {
		value += "(";
		switch (unary.op) {
		case Neg:
			value += "-";
			break;
		case Not:
			value += "!";
			break;
		}
		unary.child.accept(this);
		value += ")";
	}

	@Override
	public void visit(BinExpr binary) {
		value += "(";
		binary.left.accept(this);
		switch (binary.op) {
		case And:
			value += " & ";
			break;
		case Or:
			value += " | ";
			break;
		case Iff: 
			value += "<=>";
			break;
		case Imp:
			value += "=>";
			break;
		case Add:
			value += "+";
			break;
		case Sub:
			value += "-";
			break;
		case Mul:
			value += "*";
			break;
		case Div:
			value += "/";
			break;
		case Eq:
			value += "=";
			break;
		case Neq:
			value += "!=";
			break;
		case Le:
			value += "<=";
			break;
		case Lt:
			value += "<";
			break;
		case Ge:
			value += ">=";
			break;
		case Gt:
			value += ">";
			break;
		}
		binary.right.accept(this);
		value += ")";
	}

	@Override
	public void visit(ITEExpr ite) {
		value += "(";
		ite.ifCond.accept(this);
		value += " ? ";
		ite.thenCase.accept(this);
		value += " : ";
		ite.elseCase.accept(this);
		value += ")";
	}

	@Override
	public void visit(Value val) {
		if (val.type == Type.Boolean) {
			if (val.value == 0)
				value += "false";
			else
				value += "true";
		} else if (val.type == Type.Integer) {
			value += (int) val.value;
		} else {
			value += val.value;
		}
	}

	@Override
	public void visit(Function fun) {
		value += fun.ident;
		value += "(";
		boolean notfirst = false;
		for (Expr param : fun.parameters) {
			if (notfirst) {
				value += ",";
			}
			param.accept(this);
			notfirst = true;
		}
		value += ")";
	}

	@Override
	public void visit(Ref id) {
		value += id.name;
	}
}
