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
import fr.inria.plasmalab.rmlbis.ast.ModuleInstance;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystemInstance;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.factory.ExprFactory;
import fr.inria.plasmalab.rmlbis.ast.factory.ModuleFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;


/** An action of an adaptive command:
 *  The action is executed according to its probability.
 *  If needed, procedures are applied sequentially to define the new system. 
 * 
 * @author ltraonou
 *
 */
public class AdaptiveAction {
	
	/** The expression value must be a probability (within 0 and 1). */
	private Expr proba;
	/** List of procedures. Must be applied in reverse order. */
	private List<ModuleInstance> procedures;
	/** New system after all the procedures have been applied. */
	private PlasmaSystemInstance newSystem;
	
	/** Construct an action with probability 1. */
	public AdaptiveAction(PlasmaSystemInstance sys, ExprFactory factory) {
		this(sys, factory.makeValue(1));
	}
	
	/** Construct an action with a probability expression. */
	public AdaptiveAction(PlasmaSystemInstance sys, Expr r) {
		this.proba = r;
		this.procedures = new ArrayList<ModuleInstance>(0);
		this.newSystem = sys;		
	}
	
	public Expr getRate() {
		return proba;
	}
	
	public PlasmaSystem getNewSystem() {
		return newSystem.sys;
	}
	
	public PlasmaSystemInstance getNewSystemInstance() {
		return newSystem;
	}
	
	public List<ModuleInstance> getProcedures() {
		return procedures;
	}
	
	public ModuleInstance getProcedure(int index) {
		return procedures.get(index);
	}
	
	public void addProcedure(ModuleInstance proc) {		
		procedures.add(proc);
	}
	
	/** Instantiate all the procedures.
	 *  Note that procedures can only be instantiated from original modules
	 *   
	 * @param factory : factory of the previous system
	 * @param originals : original modules of the model
	 * @throws PlasmaSyntaxException 
	 */
	public void instantiate(ModuleFactory factory, Map<String,Module> originals) throws PlasmaSyntaxException {
		for (ModuleInstance m : procedures) {
			Module proc = m.instantiate(factory, originals, null);
			if (!proc.isProcedure())
				throw new PlasmaSyntaxException("Module: " + proc.getName() + " is not a procedure.");
		}
	}

	public String toPrism(String sysPrefix) throws PlasmaSyntaxException {
		return proba.toPrism(sysPrefix) + " : (active'=" + getNewSystem().getId() + ")&(reinit'=true)";
	}
}
