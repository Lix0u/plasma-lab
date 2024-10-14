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
package fr.inria.plasmalab.rmlbis.ast.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.inria.plasmalab.rmlbis.ast.Module;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Formula;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Label;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.processing.Update;
import fr.inria.plasmalab.rmlbis.ast.rewards.Reward;
import fr.inria.plasmalab.rmlbis.exceptions.DuplicateIdentifier;
import fr.inria.plasmalab.rmlbis.exceptions.WrongType;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class Model {

	protected ModelType type;
	protected PlasmaSystem defaultSystem;
	protected Map<String,Module> modules;
	protected Expr init;
	// Identifiers:
	protected Map<String,Variable> globalVars;
	protected Map<String,Constant> constants;
	protected Map<String,Formula> formulas;
	protected Map<String,Label> labels;
	protected Map<String,Reward> rewards;

	public Model() {
		type = 	null;
		defaultSystem = null;	
		modules = new HashMap<String,Module>(0);
		init = null;
		globalVars = new HashMap<String,Variable>(0);
		constants = new HashMap<String,Constant>(0);
		formulas =  new HashMap<String,Formula>(0);
		labels = new HashMap<String,Label>(0);
		rewards = new HashMap<String,Reward>(0);
	}
	
	public Model(ModelType t) {
		type = t;
		defaultSystem = null;	
		modules = new HashMap<String,Module>(0);
		init = null;
		globalVars = new HashMap<String,Variable>(0);
		constants = new HashMap<String,Constant>(0);
		formulas =  new HashMap<String,Formula>(0);
		labels = new HashMap<String,Label>(0);
		rewards = new HashMap<String,Reward>(0);
	}
	
    public Model(Model m) {
    	type = m.type;
    	defaultSystem  = m.defaultSystem;
    	modules = m.modules;
    	init = m.init;
    	globalVars = m.globalVars;
    	constants = m.constants;
    	formulas  = m.formulas;
    	labels = m.labels;
    	rewards = m.rewards;
    }
	
	public ModelType getType() {
		return type;
	}
		
	// --------- Modules ---------
	public Map<String,Module> getModules() {
		return modules;
	}
	
	public Module getModule(String name) {
		return modules.get(name);
	}
	
	public void addModule(Module m) {
		Module ret = modules.put(m.getName(), m);
		if (ret != null)
			throw new PlasmaRuntimeException("Module: " + m.getName() + " was already declared");
	}
	
	
	// --------- Default system ---------
	public void setDefaultSystem(PlasmaSystem sys) {
		defaultSystem = sys;
	}	
	
	public PlasmaSystem getDefaultSystem() {
		return defaultSystem;
	}
	
	// --------- Global Variables ---------
	public Collection<Variable> getGlobalVariables() {
		return globalVars.values();
	}
	
	public Variable getGlobalVariable(String name) {
		return globalVars.get(name);
	}
	
	public void addGlobalVariable(Variable var) throws DuplicateIdentifier {
		Variable ret = globalVars.put(var.ref.name, var); 
		if (ret != null)
			throw new DuplicateIdentifier(ret,var);
	}
	
	// --------- Constants ---------
	public Collection<Constant> getConstants() {
		return constants.values();
	}
	
	public Constant getConstant(String name) {
		return constants.get(name);
	}
	
	public void addConstant(Constant cons) throws DuplicateIdentifier {
		Constant c = constants.put(cons.getName(),cons);
		if (c != null)
			throw new DuplicateIdentifier(c,cons);
	}
	
	/** Update the value of the expressions in the model with the initial value of the constants. */ 
	public void updateConstantValues() {
		updateConstantValues(new HashSet<Constant>(constants.size()));		
	}

	/** Update the value of the expressions in the model with the current value of the constants.
	 *  The set evaluated provides a list of constant already updated that must not be re-evaluated 
	 *  (because the value of their expression might be different from the value of the ref).
	 */
	public void updateConstantValues(Set<Constant> evaluated) {
		Update up = new Update();
		while (evaluated.size() < constants.size()) {
			for (Constant c : constants.values()) {
				if (!evaluated.contains(c) & !Double.isNaN(c.value.value)) {
					if (c.ref.value != c.value.value) {				
						List<Ref> updated = new ArrayList<Ref>(1);
						c.ref.value = c.value.value;
						updated.add(c.ref);
						up.propagate(updated);
					}
					evaluated.add(c);
				}
			}
		}
	}
	
	
	// --------- Formulas ---------
	public Collection<Formula> getFormulas() {
		return formulas.values();
	}
	
	public Formula getFormula(String name) {
		return formulas.get(name);
	}
	
	public void addFormula(Formula form) throws DuplicateIdentifier {
		Formula f = formulas.put(form.getName(),form);
		if (f != null)
			throw new DuplicateIdentifier(f,form);
	}

	
	// --------- Labels ---------
	public Map<String,Label> getLabels() {
		return labels;
	}
	
	public Label getLabel(String name) {
		return labels.get(name);
	}
	
	public void addLabel(Label lab) throws DuplicateIdentifier {
		Label l = labels.put(lab.getName(),lab);
		if (l != null)
			throw new DuplicateIdentifier(l,lab);
	}
	
	
	// --------- Rewards ---------
	public Collection<Reward> getRewards() {
		return rewards.values();
	}
	
	public Reward getReward(String name) {
		return rewards.get(name);
	}
	
	public void addReward(Reward rew) throws DuplicateIdentifier {
		Reward r = rewards.put(rew.getName(),rew);
		if (r != null)
			throw new DuplicateIdentifier(r,rew);
	}
	
	
	// --------- Initializations ---------
	public Expr getInit() {
		return init;
	}
	
	public void setInit(Expr i) {
		// TODO: maybe check that there are no other init in the code..., but it's not very important
		this.init = i;
	}
	
	
	// ------------------
	
	public boolean typeCheck() throws WrongType {
		return defaultSystem.typeCheck();
	}
	
	public String toString() {
		String ret = "model " + type.toString() + "\n";
		for (Variable v : globalVars.values()) {
			ret += "\t global variable " + v.toString() + "\n";
		}
		ret += defaultSystem.toString() + "\n";
		return ret;
	}
	
	public String toPrism() throws PlasmaSyntaxException, PlasmaSimulatorException {
		String ret = type.toString() + "\n\n";
		// constants:
		for (Constant c : constants.values()) {
			ret += "const " + c.type.toString() + " " + c.getName() + " = " + c.value.toPrism("") + ";\n";
		}
		// global variables:
		for (Variable v : globalVars.values()) {
			ret += "global " + v.toPrism("") + ";\n";
		}		
		// labels
		for (Label l : labels.values()) {
			ret += l.toPrism("") + ";\n";
		}
		// rewards
		for (Reward r : rewards.values()) {
			ret += r.toPrism("") + ";\n";
		}

		// modules:
		ret += defaultSystem.toPrism() + "\n";
		return ret;
	}	

}
