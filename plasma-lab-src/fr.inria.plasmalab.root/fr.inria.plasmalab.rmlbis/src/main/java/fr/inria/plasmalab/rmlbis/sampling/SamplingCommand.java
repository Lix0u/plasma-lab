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
package fr.inria.plasmalab.rmlbis.sampling;

import java.util.Map;

import fr.inria.plasmalab.rmlbis.ast.Action;
import fr.inria.plasmalab.rmlbis.ast.Command;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.operators.BinOp;
import fr.inria.plasmalab.rmlbis.ast.factory.ExprFactory;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.processing.Renaming;

public class SamplingCommand extends Command {

	/** The total rate without the sampling parameters */
	private Expr totalRateNormal;
	
	public SamplingCommand(String channel, Expr guard) {
		super(channel, guard);
		this.totalRateNormal = null;
	}
	
	public void addAction(Action a, ExprFactory factory) {
		this.actions.add(a);
		SamplingAction as = (SamplingAction)a;
		Expr newActionRate = factory.makeBinExpr(as.getSamplingRateExpr(), BinOp.Mul, as.getRateExpr());
		
		if (this.totalRate == null) {
			this.totalRate = newActionRate;
			this.totalRateNormal =  as.getRateExpr();
		}
		else {
			this.totalRate = factory.makeBinExpr(this.totalRate, BinOp.Add, newActionRate);
			this.totalRateNormal = factory.makeBinExpr(this.totalRateNormal, BinOp.Add, as.getRateExpr());
		}
	}
	
	public double getTotalRate(boolean hastime) {
		if (totalRate == null) // no actions (it should not really happen)
			return 1.0;
		if (hastime)
			return totalRate.value;
		else
			return 1.0;
	}
	
	public double getTotalRateNormal(boolean hastime) {
		if (totalRateNormal == null) // no actions
			return 1.0;
		if (hastime)
			return totalRateNormal.value;
		else
			return 1.0;
	}
	
	// ----------------------
	
	public SamplingCommand rename(Map<String,Variable> new_vars, Renaming ren) {
		String newchannel = ren.mapping.get(channel); 
		if (newchannel == null)
			newchannel = channel;
		Expr newguard = ren.apply(guard);
		SamplingCommand newtrans = new SamplingCommand(newchannel, newguard);
		for (Action a : actions) {
			newtrans.addAction(a.rename(new_vars,ren));
		}
		newtrans.totalRate = ren.apply(totalRate);
		newtrans.totalRateNormal = ren.apply(totalRateNormal);
		return newtrans;
	}
}
