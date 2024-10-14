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
package fr.inria.plasmalab.rmlbis.ast.factory;

import java.util.ArrayList;
import java.util.List;

import fr.inria.plasmalab.rmlbis.ast.Action;
import fr.inria.plasmalab.rmlbis.ast.Command;
import fr.inria.plasmalab.rmlbis.ast.Module;
import fr.inria.plasmalab.rmlbis.ast.ModuleInstance;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Formula;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.models.Model;
import fr.inria.plasmalab.rmlbis.sampling.SamplingAction;
import fr.inria.plasmalab.rmlbis.sampling.SamplingCommand;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class ModuleFactory extends IdentFactory {

	public Model model;	
	public List<ModuleInstance> modules_to_instantiate;
	
	protected Module module = null;
	protected Command command = null;
	protected Action action = null;


	public ModuleFactory(Model m) {
		this.model = m;
		this.modules_to_instantiate = new ArrayList<ModuleInstance>(0);
		// add the existing constant in the model
		for (Constant c : model.getConstants()) {
			this.idents.put(c.getName(),c.ref);
		}
		// add the existing global variables in the model
		for (Variable v : model.getGlobalVariables()) {
			this.idents.put(v.getName(),v.ref);
		}
		// add the existing formulas in the model
		for (Formula f : model.getFormulas()) {
			this.idents.put(f.getName(),f.ref);
		}
		// add the existing labels in the model. 
		// LM: I comment this. Labels should not be necessary in the factory since they are not use in the model expressions.
		// plus it would forbid to overwrite global labels in systems, which is needed. 
		//		for (Label l : model.getLabels().values()) {
		//			this.idents.put(l.getName(),l.ref);
		//		}
	}
			
	public Module openModule(String name) {
		assert module == null:
			"Internal Error: Component " + module.getName() + " have to be closed before opening a new one";
		module = new Module(name);
		return module;
	}
	
	public void makeCommand (String channel, Expr guard) {
		assert command == null:
			"Before making a new command the previous one must be added";
		command = new Command(channel, guard);
	}
	
	public void makeSamplingCommand (String channel, Expr guard) {
		assert command == null:
			"Before making a new command the previous one must be added";
		command = new SamplingCommand(channel, guard);
	}
	
	public void makeAction (Expr rate) {
		// assignations.isEmpty() <=> No update action  = TRUE  
		// empty is the update is TRUE
		action = new Action(rate);
	}
	
	public void makeSamplingAction (Expr rate, Expr sampling) {
		// assignations.isEmpty() <=> No update action  = TRUE  
		// empty is the update is TRUE
		if (sampling != null)
			action = new SamplingAction(rate,sampling);
		else
			action = new SamplingAction(rate,rate);
	}
	
	public void addAssignation(String vname, Expr vupdate) {
		assert module != null && command != null && action != null:
			"An assignation needs a module and a command and an action";
		
		Variable var = module.getLocalVariable(vname);
        if (var==null) {	// only non-synchronized command can update global variables
    		var = model.getGlobalVariable(vname);
    		if (var != null && command.isSynchronised())
            	throw new PlasmaRuntimeException("Synchronized command cannot modify global variable " + vname);
        }
        if (var==null)
        	throw new PlasmaRuntimeException("Unknown variable " + vname + " in assignement.");
        action.addAssignation(var, vupdate);
	}
	
	public void addAction() {
		assert action != null:
			"An action must be created before being added";
		command.addAction(action,this);
		action = null;
	}

	public void addCommand() {
		assert command != null:
			"A Command must be created before being added";
		module.addCommand(command);
		command = null;
	}

	public void closeModule() {
		assert module != null:
			"A Module must be created before being closed";
		module = null;
	}
	
	
	// --------- Modules to instantiate ---------
	public void addModuleInstance(ModuleInstance m) {
		modules_to_instantiate.add(m);
	}
	
    /** Instantiate the modules of the factory.
     *  Add them to the model.
     * @throws PlasmaSyntaxException 
     */
     public void instantiateModules() throws PlasmaSyntaxException {
    	 for (ModuleInstance mi : modules_to_instantiate) {
    		 Module m = mi.instantiate(this,model.getModules(),null);
             model.addModule(m);
    	 }
     }
     
     /** Initialize the module instances of the factory. */
      public void initializeModules() {
     	 for (ModuleInstance mi : modules_to_instantiate) {
     		 mi.initialize();
     	 }
      }

	
}
