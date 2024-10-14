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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import fr.inria.plasmalab.rmlbis.ast.adaptive.AdaptiveSystem;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.factory.ModuleFactory;
import fr.inria.plasmalab.rmlbis.ast.factory.TypeChecking;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Label;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Parameter;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.models.AdaptiveModel;
import fr.inria.plasmalab.rmlbis.ast.models.Model;
import fr.inria.plasmalab.rmlbis.ast.processing.Update;
import fr.inria.plasmalab.rmlbis.exceptions.DuplicateIdentifier;
import fr.inria.plasmalab.rmlbis.exceptions.WrongType;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

/** Define the current context of the simulation.
 *  The system either constructed by default for according to the PRIMS model,
 *  or several systems may be used in an adaptive model.
 *    
 * @author ltraonou
 *
 */
public class PlasmaSystem {

	public Model model;
	private String name;
	private int id;
	private List<Parameter> parameters;
	/** List of module instances (modules + parameters expressions) in the system. */
	private List<ModuleInstance> moduleInstances;
	/** Map of the instantiated modules that composed the system. */
	private Map<String,Module> modules;
	/** All the labels that can be tested on the states of the system:
	 *  - global labels defined outside the system environment
	 *  - local labels defined inside the system environment
	 *  Local labels redefine global labels if they have the same name 
	 */
	private Map<String,Label> labels;
	
	private Expr init;
	/** Factory used to build the expressions of the systems. */ 
	public ModuleFactory factory;

	/** All identifiers (global and local vars, constants, formulas, labels). 
	 *  Build in the factory. 
	 */
	private Map<String,Ref> idents;
	/** Map of all the variables used in this system (global + local) 
	 *  Needs to be sorted to ensure the same initialization order.
	 */
	private TreeSet<Variable> variables;	
	/** Associates to each channel a list of commands that may be synchronized:
	 *  each module provides the list of commands that can synchronize on the current channel. 
	 */
	private TreeMap<String,List<List<Command>>> synchronisedCommands;
	/** List of commands that are not synchronized. */
	private List<Command> notSynchronisedCommands;
	
	// TODO module algebra: not implemented yet and not loaded

	
	/** Construct an empty system. */
	public PlasmaSystem(String name, AdaptiveModel model, ModuleFactory factory) {
		this.model = model;
		this.name = name;
		this.id = model.getSystems().size();
		this.parameters = new ArrayList<Parameter>(0);
		this.moduleInstances = new ArrayList<ModuleInstance>(0);
		this.modules= new HashMap<String, Module>(0);
		this.labels = new HashMap<String,Label>(model.getLabels()); // copy the global labels from the model
		this.init = null;
		this.idents = factory.getIdents();
		this.factory = factory;
		this.variables = new TreeSet<Variable>();
		this.variables.addAll(model.getGlobalVariables());
		this.synchronisedCommands = new TreeMap<String,List<List<Command>>>();
		this.notSynchronisedCommands = new ArrayList<Command>(0);
	}
	
	/** Construct a default system with a non adaptive model:
	 *  - take all the non-parameterized modules of the model
	 *  - no parameter, no formula
	 * 
	 * @param name
	 * @param model
	 * @throws PlasmaSyntaxException 
	 */
	public PlasmaSystem(Model model, ModuleFactory factory) throws PlasmaSyntaxException {
		this.model = model;
		this.name = "default";
		this.id = 0;
		this.parameters = new ArrayList<Parameter>(0);
		this.moduleInstances = null;
		this.modules = new HashMap<String, Module>(model.getModules().size());
		this.labels = model.getLabels(); // labels are the labels of the model
		this.init = model.getInit();
		this.idents = factory.getIdents();
		this.factory = factory;
		this.variables = new TreeSet<Variable>();
		this.variables.addAll(model.getGlobalVariables());
		this.synchronisedCommands = new TreeMap<String,List<List<Command>>>();
		this.notSynchronisedCommands = new ArrayList<Command>();
		for (Module m : model.getModules().values()) {
			if (m.isInstance()) {
				this.registerModule(m);
			}
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}

	// --------- Parameters ---------
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public void addParameter(Parameter param) {
		if (!param.constant)
			throw new PlasmaRuntimeException("In system " + name + " :  parameter " + param.getName() + " must be constant.");
		parameters.add(param);
	}
	
	// --------- Modules ---------
		
	public void addModuleInstance(ModuleInstance m) {
		moduleInstances.add(m);
	}
	
	public Map<String,Module> getModules() {
		return modules;
	}
	
	/** Add the module to the map.
	 *  Collect the list of variables of the module and the lists of commands (synchronized or not). 
	 * @throws PlasmaSyntaxException 
	 **/ 
	private void registerModule(Module m) throws PlasmaSyntaxException {
		assert m.isInstance();
		
		Module ret = modules.put(m.getName(), m);
		if (ret != null)
			throw new PlasmaSyntaxException("In system "+ this.name + " : module " + m.getName() + " was already declared.");
		for (Variable var : m.getLocalVariables().values()) { 
			if (!variables.add(var))
				throw new DuplicateIdentifier(var,var);
		}
		notSynchronisedCommands.addAll(m.getNotSynchronized());
		for (String channel : m.getSynchronisations()) {
			List<List<Command>> syncs = synchronisedCommands.get(channel);			
			if (syncs == null) {
				syncs = new ArrayList<List<Command>>();
				synchronisedCommands.put(channel,syncs);
			}
			syncs.add(m.getSynchronized(channel));
		}
	}
	
	// ---------- Commands --------
	/** Return the list of channels. */
	public Set<String> getChannels() {
		return synchronisedCommands.keySet();
	}
	
	/** Return the map of the synchronized commands. */
	public Map<String,List<List<Command>>> getSynchronised() {
		return synchronisedCommands;
	}
	
	/** Return the list of the non synchronized commands. */
	public List<Command> getNotSynchronised() {
		return notSynchronisedCommands;
	}
	
	// ---------- Variables --------
	public Set<Variable> getVariables() {
		return variables;
	}
	
	// ---------- Identifiers --------
	public Map<String,Ref> getIdents() {
		return idents;
	}
	
	public Ref getIdent(String name) {
		return idents.get(name);
	}
	
	// --------- Labels ---------
	public Map<String,Label> getLabels() {
		return labels;
	}
	
	public Label getLabel(String name) {
		return labels.get(name);
	}
	
	public void addLabel(Label lab) {
		labels.put(lab.getName(),lab); // this erase a previously inserted label with the same name (it allows to overwrite global labels in a system)
	}	
	
	// --------- Initializations ---------
	public Expr getInit() {
		return init;
	}
	
	public void setInit(Expr i) {
		this.init = i;
	}
	
	// ------------------	
	/** Instantiate the module instances in the system and register the new modules created.
	 *  Only the module that are instance are added to the system.
	 *  Modules that are not instance after instantiation (by renaming parameterized modules) cannot be used.
	 * @throws PlasmaSyntaxException 
	 */
	public void instantiate(Map<String,Module> original_modules) throws PlasmaSyntaxException {
		for (ModuleInstance mi : moduleInstances) {
			Module m = mi.instantiate(factory,original_modules,modules);
			if (m.isInstance())
				registerModule(m);
		}
	}
	
	/** Initialize the system and its modules with a set of values for the parameters of the system. */
	public void initialize(List<Expr> values) {
		assert values.size() < parameters.size();
				
		// 1 set the value of the parameters and update the expressions.
		List<Ref> updated = new ArrayList<Ref>(parameters.size());
		for (int i = 0; i < parameters.size(); i++) {
			Parameter param = parameters.get(i);
			param.ref.value = values.get(i).value;
			updated.add(param.ref);
		}

		Update up = new Update();
		up.propagate(updated);
		
		// 2 initialize the modules
		for (ModuleInstance mi : moduleInstances) {
			mi.initialize();
		}
	}
		
	public boolean typeCheck() throws WrongType {
		TypeChecking tck = new TypeChecking();
		for (Module m : modules.values()) {
			m.typeCheck(tck);
		}
		for (Label l : labels.values()) {
			l.body.accept(tck);
		}
		return true;
	}
	
	public String toString() {
		String ret = "system " + name + "\n";
		for (Variable p : parameters) {
			ret += "\t parameter " + p.toString() + "\n";
		}
		for (Module m : modules.values()) {
			ret += m.toString() + "\n";
		}
		if (init != null)
			ret += "init: " + init.toString() + "\n";
		ret += "end system" + "\n";
	
		return ret;
	}

	public String toPrism() throws PlasmaSyntaxException {
		if (!parameters.isEmpty())
			throw new PlasmaSyntaxException("Uncompatible with PRISM: parameterized systems cannot be translated.");
		String ret = model.getType().toString() + "\n\n";
		
		for (ModuleInstance m : moduleInstances) {
			ret += m.toPrism("","", new ArrayList<String>()) + "\n";
		}
		return ret;
	}
	
	public String toPrism(String sysPrefix, List<String> IDordering) throws PlasmaSyntaxException {
		if (!parameters.isEmpty())
			throw new PlasmaSyntaxException("Uncompatible with PRISM: parameterized systems cannot be translated.");
		String ret = model.getType().toString() + "\n\n";

		// constants:
		for (Ref ident : idents.values()) {
			if (ident.target instanceof Constant) {
				Constant c = (Constant) ident.target;
				ret += "const " + c.type.toString() + " " + c.getName() + " = " + c.value.toPrism("") + ";\n";
			}
			// TODO: NO global variables !! add support
			// TODO: NO global labels !! add support
		}
		
		for (Label l : labels.values()) {
			ret += l.toPrism(sysPrefix) + ";\n";
		}
		
		for (ModuleInstance m : moduleInstances) {
			ret += m.toPrism(sysPrefix,"",IDordering) + "\n";
		}
		if (init != null)
			ret += "init " + init.toPrism(sysPrefix) + "endinit\n";
		return ret;
	}

	public String toPrism(AdaptiveSystem ada, List<String> IDordering) throws PlasmaSyntaxException {
		if (!parameters.isEmpty())
			throw new PlasmaSyntaxException("Uncompatible with PRISM: parameterized systems cannot be translated.");
		String ret = model.getType().toString() + "\n\n";
		String sysPrefix = name + "_";	
		//String guardPrefix = "active=" + getId() + " & !reinit & ";
		
		// constants:
		for (Ref ident : idents.values()) {
			if (ident.target instanceof Constant) {
				Constant c = (Constant) ident.target;
				ret += "const " + c.type.toString() + " " + c.getName() + " = " + c.value.toPrism("") + ";\n";
			}
			// TODO: NO global variables !! add support
			// TODO: NO global labels !! add support
		}
		
		for (Label l : labels.values()) {
			ret += l.toPrism(getName() + "_") + ";\n";
		}
		
		for (ModuleInstance m : moduleInstances) {
			ret += m.toPrism(sysPrefix, "", IDordering) + "\n";
		}
		
		if (init != null)
			ret += "init " + init.toPrism(sysPrefix) + "endinit\n";
		return ret;
	}
	
}