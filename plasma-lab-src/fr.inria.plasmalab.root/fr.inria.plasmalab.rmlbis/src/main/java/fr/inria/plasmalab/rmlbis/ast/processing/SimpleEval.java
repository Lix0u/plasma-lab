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
import fr.inria.plasmalab.rmlbis.ast.expr.Function;
import fr.inria.plasmalab.rmlbis.ast.expr.ITEExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.UnExpr;
import fr.inria.plasmalab.rmlbis.ast.expr.Value;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.DefaultVisitor;


/** Evaluate a single expression */ 
public class SimpleEval implements DefaultVisitor {
	
	@Override
	public void visit(UnExpr unary) {
		unary.eval();
	}
	
	@Override
	public void visit(BinExpr binary) {
		binary.eval();
	}

	@Override
	public void visit(ITEExpr iteExpr) {
		iteExpr.eval();
	}
	
	@Override
	public void visit(Function fun) {
		fun.eval();
	}

	@Override
	public void visit(Value val) { }

	@Override
	public void visit(Ref id) { }

}
