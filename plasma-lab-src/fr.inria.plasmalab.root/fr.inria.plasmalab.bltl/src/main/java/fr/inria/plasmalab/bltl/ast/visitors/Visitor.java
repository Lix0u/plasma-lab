/**
 * This file is part of fr.inria.plasmalab.bltl.
 *
 * fr.inria.plasmalab.bltl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bltl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bltl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.bltl.ast.visitors;

import fr.inria.plasmalab.bltl.ast.leaves.BoolExpr;
import fr.inria.plasmalab.bltl.ast.leaves.DeadlockExpr;
import fr.inria.plasmalab.bltl.ast.leaves.FloatExpr;
import fr.inria.plasmalab.bltl.ast.leaves.IdentExpr;
import fr.inria.plasmalab.bltl.ast.leaves.IntExpr;
import fr.inria.plasmalab.bltl.ast.nodes.Expr1;
import fr.inria.plasmalab.bltl.ast.nodes.Expr2;
import fr.inria.plasmalab.bltl.ast.nodes.RewardExpr;
import fr.inria.plasmalab.bltl.ast.nodes.StepBoundExpr1;
import fr.inria.plasmalab.bltl.ast.nodes.StepBoundExpr2;
import fr.inria.plasmalab.bltl.ast.nodes.TimeBoundExpr1;
import fr.inria.plasmalab.bltl.ast.nodes.TimeBoundExpr2;

public interface Visitor {
	// This solution won't work because we want that all class that extends Expr
	// only implements accept(Visitor v).
	// This is because we can't make concrete reference to a generic method.
	//public void visit(Class<? extends Expr> expr);
	
	// Then if we want to have specific method for our abstract class extending Expr (like AbsExpr1)
	// we could create a hierarchy of abstract class for each level of node composant. 
	// We then fall in to 2 bad solution
	// -If we try to compose (add other visitor interface) to a predefined abstract visitor we then fall 
	// in the problem where common denominator is used lvl 0 visitor = visit(Expr e) we now have only one
	// method used for all our target
	// -Or we define several accept method (one for each Visitor). This is not a nice solution

	public void visit(TimeBoundExpr1 expr);

	public void visit(TimeBoundExpr2 expr);
	
	public void visit(StepBoundExpr1 expr);

	public void visit(StepBoundExpr2 expr);
	
	public void visit(Expr1 expr);

	public void visit(Expr2 expr);
	
	public void visit(FloatExpr expr);

	public void visit(IntExpr expr);

	public void visit(IdentExpr expr);

	public void visit(BoolExpr expr);
	
	public void visit(DeadlockExpr expr);

	public void visit(RewardExpr expr);
}
