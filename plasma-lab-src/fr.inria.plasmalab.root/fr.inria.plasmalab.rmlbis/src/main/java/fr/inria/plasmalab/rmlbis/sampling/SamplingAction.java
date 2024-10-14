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
import java.util.Map.Entry;

import fr.inria.plasmalab.rmlbis.ast.Action;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.factory.TypeChecking;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.processing.Renaming;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;

public class SamplingAction extends Action {
	
	/** Rate modification of the action 
	 *  (equal to 1.0 if the action is not sampled). 
	 */
	private Expr sampling_rate; 
	
	public SamplingAction(Expr rate, Expr sampling) {
		super(rate);
		this.sampling_rate = sampling;
	}
		
	public Expr getSamplingRateExpr() {
		return this.sampling_rate;
	}
	
	public double getSamplingRate() {
		return this.sampling_rate.value;
	}
	
	public SamplingAction rename(Map<String,Variable> new_vars, Renaming ren) {
		SamplingAction newaction = new SamplingAction(ren.apply(rate), ren.apply(sampling_rate));
		for (Map.Entry<Variable, Expr> assign : assignations.entrySet()) {
			String newvarname = ren.mapping.get(assign.getKey().ref.name);
			if (newvarname == null) {
				newaction.addAssignation(assign.getKey(),assign.getValue()); // no renaming
			}
			else {
				Expr newexpr = ren.apply(assign.getValue());
				Variable newvar = new_vars.get(newvarname);
				if (newvar == null)
					throw new PlasmaRuntimeException("assign variable: " + newvarname + " is unknown");
				newaction.addAssignation(newvar,newexpr);
			}
		}
		return newaction;
	}
	
    public boolean typeCheck(TypeChecking tck) {
		tck.subTypeOf(Type.Double, rate);
		tck.subTypeOf(Type.Double, sampling_rate);
		for (Entry<Variable,Expr> e : assignations.entrySet()) {
			tck.subTypeOf(e.getKey().type, e.getValue());
		}
		return true;
    }
	
	public String toString() {
		String ret = rate.toString();
		ret = "{" + sampling_rate.toString() + "} ";
		ret += " : ";
		boolean notfirst = false;
		for (Map.Entry<Variable, Expr> item : assignations.entrySet()) {
			if (notfirst) {
				ret += " & ";
			}
			ret += "(" + item.getKey().ref.name + "'=";
			ret += item.getValue().toString() + ")";
			notfirst = true;
		}
		return ret;
	}

	public String toPrism(String sysPrefix) {
		String ret = rate.toString();
		ret = "{" + sampling_rate.toString() + "} ";
		ret += " : ";		boolean notfirst = false;
		for (Map.Entry<Variable, Expr> item : assignations.entrySet()) {
			if (notfirst) {
				ret += " & ";
			}
			ret += "(" + item.getKey().ref.getPrismName(sysPrefix) + "'=";
			ret += item.getValue().toPrism(sysPrefix) + ")";
			notfirst = true;
		}
		if (!notfirst)
			ret += "true";
		return ret;
	}
}
