/**
 * This file is part of fr.inria.plasmalab.workflow.
 *
 * fr.inria.plasmalab.workflow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.workflow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.workflow.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.workflow.concrete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

/**
 * This class is a Map of optimization Variable.
 * Optimization variables are used to generate different initial states from a model,
 * replacing original values of the variables by those of the variable.
 * Variable maps ranges to an InterfaceIdentifier.
 * 
 * The class maps variable to a flag:
 * INIT can replace REQ but should not happen
 * REQ can't replace INIT
 * INIT (resp. REQ) can't replace INIT (resp. REQ) and will throw an Exception.
 * @author kevin.corre@inria.fr
 *
 */
public class OptimVariables{
	/** The logger. */
	final static Logger logger = LoggerFactory.getLogger(OptimVariables.class);
	public static final boolean INIT=true, REQ=false, MODEL=true;
	/**
	 * Context is a flag:
	 * INIT can replace REQ but should not happen
	 * REQ can't replace INIT
	 * INIT (resp. REQ) can't replace INIT (resp. REQ) and will throw an Exception.
	 */
	private Map<String,VarContext> varsMap;	
	private List<VariableConstraint> constraints;

	public OptimVariables(){
		this.varsMap = new HashMap<String, VarContext>();
		this.constraints = new ArrayList<VariableConstraint>();
	}
	@JsonCreator
	public OptimVariables(@JsonProperty("vars")List<Variable> vars){
		this.varsMap = new HashMap<String, VarContext>();
		for(Variable var:vars){
			addVar(var, INIT);
		}
		this.constraints = new ArrayList<VariableConstraint>();
	}
	
	public Map<String,VarContext> getVarsMap(){
		return varsMap;
	}
	
	public List<Variable> getVars(){
		List<Variable> vars = new ArrayList<Variable>();
		for(VarContext vc: varsMap.values()){
			vars.add(vc.getVar());
		}
		return vars;
	}
	
	public void setVars(List<Variable> vars){
		this.varsMap.clear();
		for(Variable var:vars){
			addVar(var, INIT);
		}
	}
	
	public void addVars(List<Variable> addedVars){
		for(Variable var:addedVars){
			addVar(var, REQ);
		}
	}
	
	private void addVar(Variable var, boolean context){
		if(varsMap.containsKey(var.getName())){
			boolean oldContext = varsMap.get(var.getName()).getContext();
			if(oldContext == context) {
				logger.warn("Optimization variable ("+var+") declared twice, skipped.");
				return;
			}
			else if(oldContext == REQ){
				varsMap.put(var.getName(), new VarContext(var, context));
			}
			else if(oldContext == INIT){
				//Don't replace
			}
		}
		else{
			varsMap.put(var.getName(), new VarContext(var, INIT));
		}
	}
	
	public void addConstraints(List<VariableConstraint> addedCons){
		constraints.addAll(addedCons);
	}
	
	/**
	 * This method generate the list of initial values from a list of optimization variables and a model.
	 * 
	 * @param model
	 * @param optimVariables
	 * @return
	 */
	public List<Map<InterfaceIdentifier, Double>> getOptimVariablesRange(AbstractModel model){
		List<Map<InterfaceIdentifier, Double>> range = new ArrayList<Map<InterfaceIdentifier,Double>>();
		Map<String, InterfaceIdentifier> modelIdentifiers = model.getIdentifiers();
		List<Variable> vars = getVars();
		boolean overflow;
		if(vars != null && vars.size()>0){
			for (Variable digit: vars){
				digit.init();
			}
			do{
				Map<InterfaceIdentifier, Double> initState = new HashMap<InterfaceIdentifier, Double>();
				for(Variable var:vars){
					if(modelIdentifiers.containsKey(var.getName())){
						InterfaceIdentifier id = modelIdentifiers.get(var.getName());
						initState.put(id, var.getActualValue());
					}
					else{
						// If there is no modelIdentifier equivalent to the var
						// We add the variable as an identifier in the map and throw warning
						logger.warn("The optimization variable "+var.getName()+
								" could not be identifier as a model identifier. Adding it anyway.");
						initState.put(var, var.getActualValue());
					}
				}
				// check if the initial satisfies all the constraints
				boolean add = true;
				for (VariableConstraint cons : constraints) {
					if (!cons.check(initState,varsMap)) {
						add = false;
						break;
					}
				}
				if (add)
					range.add(initState);
				overflow = true;
				// we increment the number :
				for (Variable digit: vars){
					if (digit.incr()) {
						overflow = false; break;	//No carry: increment is finished
					} else 							// we get a carry: digit = lower and we continue to propagate the carry
						digit.setToLower();
				}
			}while(!overflow);
		}else{
			//Returns list with one empty map = no modification
			//optimInitialStates of size 1 with an empty map means no optimization
			//But it is still one initial state to test
			range.add(new HashMap<InterfaceIdentifier, Double>(0));
		}
		return range;
	}
	
	/**
	 * This method generate a list of InterfaceIdentifier from a model and a list of Variable
	 * @param model
	 * @param optimVariables
	 * @return a list of InterfaceIdentifier
	 */
	public List<InterfaceIdentifier> generateOptimIdentifiers(AbstractModel model){
		List<InterfaceIdentifier> optimId = new ArrayList<InterfaceIdentifier>();
		List<Variable> vars = getVars();
		if(vars != null){
			for(Variable var:vars){
				if(model.getIdentifiers().get(var)!=null)
					optimId.add(model.getIdentifiers().get(var.getName()));
				else
					optimId.add(var);
			}
		}
		return optimId;
	}
}
