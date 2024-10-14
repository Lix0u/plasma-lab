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

public class TransitionReward extends StateReward {
	
	private String channel;
	
	public TransitionReward(Expr c, Expr r) {
		super(c,r);
		this.channel = "";
	}
	
	public TransitionReward(String s, Expr c, Expr r) {
		super(c,r);
		this.channel = s;
	}
	
	public String getChannel() {
		return channel;
	}
	
	@Override
	public String toPrism(String sysPrefix) {
		return "[" + channel + "]" + super.toPrism(sysPrefix);
	}
	
}
