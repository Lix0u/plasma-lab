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
package fr.inria.plasmalab.bltl.ast.nodes;

import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.bltl.ast.visitors.ManualVisitor;
import fr.inria.plasmalab.bltl.ast.visitors.Visitor;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDeadlockException;


public class RewardExpr extends Expr {

	protected String reward;
	protected Expr child;
	
	public RewardExpr(String r, Expr c) {
		this.reward = r;
		this.child = c;
	}
	
	@Override
	public void accept(Visitor v) {
		child.accept(v);
		v.visit(this);
	}

	@Override
	public void accept(ManualVisitor v) {
		v.visit(this);
	}
	
	public String getReward() {
		return reward;
	}
	
	public Expr child() {
		return child;
	}

}
