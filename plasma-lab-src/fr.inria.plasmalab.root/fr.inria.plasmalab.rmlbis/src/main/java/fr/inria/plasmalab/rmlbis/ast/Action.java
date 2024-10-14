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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.factory.TypeChecking;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.processing.Renaming;
import fr.inria.plasmalab.rmlbis.exceptions.WrongType;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;

public class Action {
	
	protected Expr rate;
	protected Map<Variable,Expr> assignations; // empty is the update is TRUE

	public Action(Expr rate) {
		this.rate = rate;
		this.assignations = new HashMap<Variable, Expr>(0);
	}
	
	public Expr getRateExpr() {
		return this.rate;
	}
	
	public double getRate() {
		return this.rate.value;
	}
		
	public Map<Variable,Expr> getAssignations() {
		return this.assignations;
	}
	
	public Expr getAssignationVariable(Variable v) {
		return this.assignations.get(v);
	}
	
	public void addAssignation(Variable var, Expr vupdate) {
		if (var == null)
			throw new PlasmaRuntimeException("assign variable is null");
		this.assignations.put(var, vupdate);
	}
	
	public Action rename(Map<String,Variable> new_vars, Renaming ren) {
		Action newaction = new Action(ren.apply(rate));
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
	
    public boolean typeCheck(TypeChecking tck) throws WrongType {
		tck.subTypeOf(Type.Double, rate);
		for (Entry<Variable,Expr> e : assignations.entrySet()) {
			tck.subTypeOf(e.getKey().type, e.getValue());
		}
		return true;
    }
	
	public String toString() {
		String ret = rate.toString();
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
		String ret = rate.toPrism(sysPrefix) + " : ";
		boolean notfirst = false;
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
