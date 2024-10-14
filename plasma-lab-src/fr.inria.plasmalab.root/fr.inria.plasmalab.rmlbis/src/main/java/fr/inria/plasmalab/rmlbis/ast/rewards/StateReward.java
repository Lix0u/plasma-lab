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
package fr.inria.plasmalab.rmlbis.ast.rewards;

import fr.inria.plasmalab.rmlbis.ast.expr.Expr;

public class StateReward {
	
	protected Expr condition;
	protected Expr reward;
	
	public StateReward(Expr c, Expr r) {
		this.condition = c;
		this.reward = r;
	}
	
	public Expr getCondition() {
		return condition;
	}
	
	public Expr getReward() {
		return reward;
	}
	
	public double getValue() {
		if (condition.value != 0)
			return reward.value;
		else
			return 0;
	}
	
	public String toPrism(String sysPrefix) {
		return condition.toPrism(sysPrefix) + " : " + reward.toPrism(sysPrefix) + ";";
	}
	
}
