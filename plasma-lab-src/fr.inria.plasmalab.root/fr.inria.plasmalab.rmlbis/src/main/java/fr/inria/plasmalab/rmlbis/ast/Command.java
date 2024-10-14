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
package fr.inria.plasmalab.rmlbis.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.operators.BinOp;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.factory.ExprFactory;
import fr.inria.plasmalab.rmlbis.ast.factory.TypeChecking;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.processing.Renaming;
import fr.inria.plasmalab.rmlbis.exceptions.WrongType;

public class Command {

	/** Channel on which the command is synchronized.
	 *  Empty string if the command is not synchronized. 
	 */
	protected String channel;
	/** Guard that enabled the command */
	protected Expr guard;
	/** Expr that is the sum of the action rate 
	 *  null until an action is added to the command.
	 */
	protected Expr totalRate;
	/** List of actions */
	protected List<Action> actions;
	
	public Command(String channel, Expr guard) {
		this.channel = channel;
		this.guard = guard;
		this.totalRate = null;
		this.actions = new ArrayList<Action>(0);
	}
	
	// channel
	public String getChannel() {
		return this.channel;
	}
	
	public boolean isSynchronised() {
		return !this.channel.isEmpty();
	}
	
	// guard
	public Expr getGuard() {
		return this.guard;
	}
	
	// actions
	public List<Action> getActions() {
		return this.actions;
	}
	
	public void addAction(Action a, ExprFactory factory) {
		this.actions.add(a);
		if (this.totalRate == null)
			this.totalRate = a.getRateExpr();
		else
			this.totalRate = factory.makeBinExpr(this.totalRate, BinOp.Add, a.getRateExpr());
	}
	
	public void addAction(Action a) {
		this.actions.add(a);
	}
	
	// total rate	
	public double getTotalRate(boolean hastime) {
		if (totalRate == null) // no actions (it should not really happen)
			return 1.0;
		if (hastime)
			return this.totalRate.value;
		else
			return 1.0;
	}
	
	public Expr getTotalRateExpr() {
		return totalRate;
	}
	
	// ----------------------
	
	public Command rename(Map<String,Variable> new_vars, Renaming ren) {
		String newchannel = ren.mapping.get(channel); 
		if (newchannel == null)
			newchannel = channel;
		Expr newguard = ren.apply(guard);
		Command newtrans = new Command(newchannel, newguard);
		for (Action a : actions) {
			newtrans.addAction(a.rename(new_vars,ren));
		}
		newtrans.totalRate = ren.apply(totalRate);
		return newtrans;
	}
	
	public boolean typeCheck(TypeChecking tck) throws WrongType {
		tck.subTypeOf(Type.Boolean, guard);
		for (Action a : actions) {
			a.typeCheck(tck);
		}
		return true;
    }	
	
	public String toString() {
		String ret = "[" + channel + "] ";
		ret += guard.toString();
		ret += " -> ";
		boolean notfirst = false;
		for (Action a : actions) {
			if (notfirst) {
				ret += " + ";
			}
			ret += a.toString();
			notfirst = true;
		}
		return ret;
	}

	public String toPrism(String sysPrefix, String guardPrefix) {
		String ret = "[] ";
		if (!channel.isEmpty())
			ret = "[" + sysPrefix + channel + "] ";
		ret += guardPrefix + guard.toPrism(sysPrefix);
		ret += " -> ";
		boolean notfirst = false;
		for (Action a : actions) {
			if (notfirst) {
				ret += " + ";
			}
			ret += a.toPrism(sysPrefix);
			notfirst = true;
		}
		return ret;
	}
}
