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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.Value;
import fr.inria.plasmalab.rmlbis.ast.factory.ModuleFactory;
import fr.inria.plasmalab.rmlbis.ast.factory.TypeChecking;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Parameter;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.processing.Renaming;
import fr.inria.plasmalab.rmlbis.ast.processing.Update;
import fr.inria.plasmalab.rmlbis.exceptions.DuplicateIdentifier;
import fr.inria.plasmalab.rmlbis.exceptions.WrongType;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class Module {
	
	private String name;
	private List<Parameter> parameters;
	/** True if the module is instantiated */
	private boolean instance;
	private Map<String,Variable> localVariables;
	private List<Command> commands;	
	/** The set of channels on which this module synchronizes */
	private Set<String> synchronisations;
	
	// LM: I remove invariant because they are only used in PTA, which cannot be simulated with SMC
	// (unless some distribution is given on the time elapsing)
	//private Expr invariant;
	
	public Module(String name) {
		this.name = name;
		this.parameters = new ArrayList<Parameter>(0);
		this.instance = true;
		this.localVariables = new HashMap<String, Variable>(0);
		this.commands = new ArrayList<Command>(0);
		this.synchronisations= new HashSet<String>(0); 
		//this.invariant = null;
	}
	
	public String getName() {
		return name;
	}
	
	// --------- Parameters ---------
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public void addParameter(Parameter param) throws DuplicateIdentifier {
		parameters.add(param);
		instance = false;
		if (!param.constant) {
			addLocalVariable(param);
		}
	}

	// --------- Local variables ---------
	public Map<String,Variable> getLocalVariables() {
		return localVariables;
	}
	
	public Variable getLocalVariable(String name) {
		return localVariables.get(name);
	}
	
	public void addLocalVariable(Variable var) throws DuplicateIdentifier {		
		Variable ret = localVariables.put(var.ref.name, var); 
		if (ret != null)
			throw new DuplicateIdentifier(ret,var);
	}
	
	// --------- Transitions ---------
	public List<Command> getCommands() {
		return commands;
	}

	public void addCommand(Command t) {
		commands.add(t);
		if (t.isSynchronised())
			synchronisations.add(t.getChannel());
	}
	
	public Set<String> getSynchronisations() {
		return synchronisations;
	}
	
	/** Return true if the module synchronizes on the @param channel. */
	public boolean isSynchronised(String channel) {
		return synchronisations.contains(channel);
	}
	
	/** Return the list of commands that can synchronize on a @param channel. */
	public List<Command> getSynchronized(String channel) {
		List<Command> ret = new ArrayList<Command>();
		for (Command c : commands) {
			if (c.isSynchronised() && c.getChannel().equals(channel))
				ret.add(c);
		}
		return ret;
	}
	
	/** Return the list of commands that are not synchronized. */
	public List<Command> getNotSynchronized() {
		List<Command> ret = new ArrayList<Command>();
		for (Command c : commands) {
			if (!c.isSynchronised())
				ret.add(c);
		}
		return ret;
	}
	
//	// --------- Invariant ---------
//	public Expr getInvariant() {
//		return invariant;
//	}
//	
//	public void setInvariant(Expr e) {
//		invariant = e;
//	}
	
	// --------------------------------
	
	/** Build a new module, with a @param new_module_name, by applying the @param variablesRenamings given in parameter.
	 *  New local variables and new parameters are created. They must all be renamed.
	 *  Expressions in commands are renamed according to the renamings of local variables, parameters and global variables. 
	 * @throws PlasmaSyntaxException 
	 */
	public Module rename(ModuleFactory factory, String new_module_name, Map<String,String> variablesRenamings) throws PlasmaSyntaxException {
		//if (!parameters.isEmpty())
		//	throw new PlasmaException("Parameterized module cannot be renamed.");
		
		Module renamed = new Module(new_module_name);		
		Renaming ren = new Renaming(factory, variablesRenamings);
		Map<String,Variable> new_variables = new HashMap<String, Variable>(); // list of variables renamed (variables that can be modified by the module: local variables, global variables)
		
		// 1 rename local variables
		for (Variable var : localVariables.values()) {
			if (!(var instanceof Parameter)) {
				String newvarname = variablesRenamings.get(var.getName());
				if (newvarname != null) {
					// create a new variable and add it to the list of new variables
					Variable newvar;
					if (var.lowerExpr != null){
					  newvar = factory.makeVariable(newvarname,ren.apply(var.lowerExpr),ren.apply(var.upperExpr),ren.apply(var.initValue));
					} else {
					  newvar = factory.makeVariable(newvarname,var.type,var.initValue);
					}
					renamed.addLocalVariable(newvar);
					new_variables.put(newvar.ref.name,newvar);
				}
				else 
					throw new PlasmaSyntaxException("Local variable " + var.ref + " must be renamed.");
			}
		}

		// 2 rename global variables
		// We must find which variables are renamed to create the assignations of the actions.
		for (Variable var : factory.model.getGlobalVariables()) {
			String newvarname = variablesRenamings.get(var.getName());
			if (newvarname != null) {
				// get the new variable and add it to the list of new variables
				Variable newvar = factory.model.getGlobalVariable(newvarname);
				if (newvar != null) {
					new_variables.put(newvar.ref.name,newvar);
				}
				else
					throw new PlasmaSyntaxException("Global variable " + newvarname + " does not exist.");
			}
		}
				
		// 3 rename parameters
		for (Parameter param : parameters) {
			String newvarname = variablesRenamings.get(param.ref.name);
			if (newvarname != null) {
				Parameter newvar = factory.makeParameter(newvarname,ren.apply(param.lowerExpr),ren.apply(param.upperExpr),ren.apply(param.initValue),param.constant);
				renamed.addParameter(newvar);
				if (!newvar.constant) {
					new_variables.put(newvar.ref.name,newvar);
				}
			}
			else 
				throw new PlasmaSyntaxException("Parameter " + param.ref + " must be renamed.");
		}
		
		// 4 rename commands 
		for (Command trans : commands)
			renamed.addCommand(trans.rename(new_variables,ren));
		
		// renamed.setInvariant(ren.apply(invariant)); 
		// TODO rename clocks
		return renamed;
	}
	
	
	
	/** Build a new module that is an instance of this module.
	 *  Local variables and parameters are renamed by prefixing their name with the new module name.
	 *  New local variables and parameters are created.
	 *  Renamings can be applied to the new module (except for local variables and parameters) 
  	 *  Expressions in commands are renamed according to the renamings of local variables and parameters. 
	 * @throws PlasmaSyntaxException 
	 */ 
	public Module instantiate(ModuleFactory factory, String new_module_name, Map<String,String> variablesRenamings) throws PlasmaSyntaxException {
		Module renamed = new Module(new_module_name);
		Map<String,Variable> new_variables = new HashMap<String, Variable>(); // list of variables renamed
		
		// 1 prefix all local variables with the new module name
		for (Variable var : localVariables.values()) {
			String previous = variablesRenamings.put(var.ref.name, new_module_name+"."+var.ref.name);
			if (previous != null)
				throw new PlasmaSyntaxException("Local variable " + var.ref.name + " cannot be renamed while instantiating.");
		}
		// 2 prefix all constant parameters with the new module name
		// (non constant parameters are also local variables and therefore have been renamed previously) 
		for (Parameter param : parameters) {
			if (param.constant) {
				String previous = variablesRenamings.put(param.ref.name, new_module_name+"."+param.ref.name);
				if (previous != null)
					throw new PlasmaSyntaxException("Constant parameter " + param.ref.name + " cannot be renamed while instantiating.");
			}
		}
	
		// 3 rename global variables
		for (Variable var : factory.model.getGlobalVariables()) {
			String newvarname = variablesRenamings.get(var.getName());
			if (newvarname != null) {
				// get the new variable and add it to the list of new variables
				Variable newvar = factory.model.getGlobalVariable(newvarname);
				if (newvar != null) {
					new_variables.put(newvar.ref.name,newvar);
				}
				else
					throw new PlasmaSyntaxException("Global variable " + newvarname + " does not exist.");
			}
		}
		
		Renaming ren = new Renaming(factory, variablesRenamings);
		
		// 3 create the new variables
		for (Variable var : localVariables.values()) {
			if (!(var instanceof Parameter)) {
				String newvarname = variablesRenamings.get(var.ref.name);			
				Variable newvar = factory.makeVariable(newvarname,ren.apply(var.lowerExpr),ren.apply(var.upperExpr),ren.apply(var.initValue));
				newvar.rename(ren);
				renamed.addLocalVariable(newvar);
				new_variables.put(newvar.ref.name,newvar);
			}
		}
		
		// 4 create new parameters
		for (Parameter param : parameters) {
			String newvarname = variablesRenamings.get(param.ref.name);
			Parameter newvar = factory.makeParameter(newvarname,ren.apply(param.lowerExpr),ren.apply(param.upperExpr),ren.apply(param.initValue),param.constant);
			renamed.parameters.add(newvar);
			if (!newvar.constant) {
				newvar.rename(ren);
				renamed.addLocalVariable(newvar);
				new_variables.put(newvar.ref.name,newvar);
			}	
		}
		
		// 5 rename the commands
		for (Command trans : commands)
			renamed.addCommand(trans.rename(new_variables,ren));
		
		// renamed.setInvariant(ren.apply(invariant)); 
		// TODO rename clocks ?
		return renamed;
	}	
	
	/** Initialize the parameters of the modules with the @param param_values.
	 *  @param param_values List of expression whose value is used to initialize the parameters. The list must be at least as long as the parameters list.
	 *  
	 *  New initial value are computed and the ref value are updated.
	 *  If the value is different than the previous one the parents expressions are updated.  
	 */
	public void initialize(List<Expr> param_values) {
		assert instance;
		assert param_values.size() >= parameters.size();
		Update up = new Update();		
		List<Ref> updated = new ArrayList<Ref>(parameters.size());

		// 1 set the value of the parameters
		for (int i = 0; i < parameters.size(); i++) {
			Parameter param = parameters.get(i);
			param.initValue = new Value(param_values.get(i).value);
			if (param.ref.value != param.initValue.value) {
				param.ref.value = param.initValue.value;
				updated.add(param.ref);
			}
		}
		up.propagate(updated);
		
		// 2 reset the values of local variables
		updated = new ArrayList<Ref>(localVariables.size());
		for (Variable var : localVariables.values()) {
			if (var.ref.value != var.initValue.value) {
				var.ref.value = var.initValue.value;
				updated.add(var.ref);
			}
		}
		up.propagate(updated);
	}
		

	// --------------------------------

	/** A module is a procedure if all its commands are not synchronized 
	 */
	public boolean isProcedure() {
		for (Command c : commands)
			if (c.isSynchronised())
				return false;
		return true;
	}
	
	public boolean isInstance() {
		return instance;
	}
	
	public boolean typeCheck(TypeChecking tck) throws WrongType {
		for (Command  c : commands) {
			c.typeCheck(tck);
		}
		return true;
	}
	
	public String toString() {
		String ret = "module " + name + "\n";
		for (Identifier param : parameters) {
			ret += "\t parameter " + param.toString() + "\n";
		}
		for (Variable v : localVariables.values()) {
			ret += "\t variable " + v.toString() + "\n";
		}
		for (Command co : commands) {
			ret += "\t command " + co.toString() + "\n";
		}
		ret += "end module";
		return ret;
	}

	/** Print module's commands */
	public String toPrism(String sysPrefix, String guardPrefix) {
		String ret = "";
		for (Command co : commands) {
			ret += "\t" + co.toPrism(sysPrefix, guardPrefix) + ";\n";
		}
		return ret;
	}
	
}
