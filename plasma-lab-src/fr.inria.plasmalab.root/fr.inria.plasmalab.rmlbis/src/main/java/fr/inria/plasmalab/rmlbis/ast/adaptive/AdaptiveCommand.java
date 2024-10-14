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
package fr.inria.plasmalab.rmlbis.ast.adaptive;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.rmlbis.ast.Module;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

/** A transition in an adaptive system:
 *  If the guard is satisfied the command may change the current system according to one of its actions. 
 * 
 * @author ltraonou
 *
 */
public class AdaptiveCommand {
	
	/** Current system in which the guard and the expressions are evaluated. */
	private PlasmaSystem activeSystem;
	private Expr guard;
	private List<AdaptiveAction> actions;
	
	public AdaptiveCommand(PlasmaSystem activeSystem, Expr guard) {
		this.activeSystem = activeSystem;
		this.guard = guard;
		this.actions = new ArrayList<AdaptiveAction>();
	}

	public PlasmaSystem getActiveSystem() {
		return activeSystem;
	}
	
	public Expr getGuard() {
		return guard;
	}
	
	public List<AdaptiveAction> getActions() {
		return this.actions;
	}
	
	public void addAction(AdaptiveAction a) {
		this.actions.add(a);
	}

	/** Instantiate all the procedures in all the actions using the module factory of the current system. 
	 * @throws PlasmaSyntaxException */
	public void instantiate(Map<String,Module> originals) throws PlasmaSyntaxException {
		for (AdaptiveAction a : actions)
			a.instantiate(activeSystem.factory, originals);
	}

	public String toPrism() throws PlasmaSyntaxException {
		String ret = "";
		String sysPrefix = activeSystem.getName() + "_";	
		
		for (AdaptiveAction a : actions) {
			if (!ret.isEmpty())
				ret += " + ";
			ret += a.toPrism(sysPrefix);			
		}
		return "\t[] active=" + activeSystem.getId() + " & !reinit & " + guard.toPrism(sysPrefix) + " -> " + ret;
	}
	
	public String getPrismGuard() throws PlasmaSyntaxException {
		String sysPrefix = activeSystem.getName() + "_";	
		return guard.toPrism(sysPrefix); 
	}
}
