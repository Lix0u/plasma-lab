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
package fr.inria.plasmalab.rmlbis.simulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.antlr.runtime.RecognitionException;

import fr.inria.plasmalab.rmlbis.ast.Module;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Label;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Parameter;
import fr.inria.plasmalab.rmlbis.ast.identifiers.TimeIdentifier;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.models.Model;
import fr.inria.plasmalab.rmlbis.ast.models.ModelType;
import fr.inria.plasmalab.rmlbis.ast.processing.SimpleEval;
import fr.inria.plasmalab.rmlbis.ast.processing.Update;
import fr.inria.plasmalab.rmlbis.ast.rewards.Reward;
import fr.inria.plasmalab.rmlbis.parsing.PRISMParserProcessing;
import fr.inria.plasmalab.rmlbis.simulator.responsibility.RMLMDPSamplingSimulator;
import fr.inria.plasmalab.rmlbis.simulator.responsibility.RMLMDPSimulator;
import fr.inria.plasmalab.rmlbis.simulator.responsibility.RMLSamplingSimulator;
import fr.inria.plasmalab.rmlbis.simulator.responsibility.RMLSimulator;
import fr.inria.plasmalab.rmlbis.simulator.responsibility.RMLUniformSimulator;
import fr.inria.plasmalab.rmlbis.simulator.responsibility.SimulatorResponsibility;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.InterfaceSamplingSimulator;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;


/** Interface used by the checkers between the structural model (class Model) and the simulators (SimulatorResponsability) */ 
public class RMLModel extends AbstractModel implements InterfaceSamplingSimulator {
	
	protected Model model;
	protected final TimeIdentifier timeId = new TimeIdentifier();
	
	protected SimulatorResponsibility simulator;
	protected SimulatorResponsibility uniform_simulator;
	protected SimulatorResponsibility active_simulator;

	public RMLModel(String name, File file, String id) throws PlasmaDataException {
		this.name = name;
		this.id = id;
		content = "";
		try {
			// read the file
			FileReader fr = new FileReader(file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				content = content+br.readLine()+"\n";
			br.close();
		} catch (IOException e) {
			throw new PlasmaDataException(e);
		}        
		this.origin = file;
		this.model = null;
	}

	public RMLModel(String name, String content, String id){
		this.name = name;
		this.id = id;
		this.content = content;
		this.origin = null;
	}

	@Override
	public InterfaceState newPath() throws PlasmaSimulatorException {
		active_simulator = simulator;
		try {
			return simulator.newPath();
		}
		catch(PlasmaRuntimeException e) {
			throw new PlasmaSimulatorException(e);
		}
	}

	@Override
	public InterfaceState newPath(long seed) throws PlasmaSimulatorException {
		active_simulator = simulator;
		try {
			return simulator.newPath(seed);
		}
		catch(PlasmaRuntimeException e) {
			throw new PlasmaSimulatorException(e);
		}
	}	
	
	public InterfaceState newPath(List<InterfaceState> initTrace) throws PlasmaSimulatorException {
		active_simulator = simulator;
		try {
			return simulator.newPath(initTrace);
		}
		catch(PlasmaRuntimeException e) {
			throw new PlasmaSimulatorException(e);
		}
	}
	
	@Override
	public InterfaceState newUniformPath() throws PlasmaSimulatorException {
		active_simulator = uniform_simulator;
		try {
			return uniform_simulator.newPath();
		}
		catch(PlasmaRuntimeException e) {
			throw new PlasmaSimulatorException(e);
		}
	}

	/** Simulate a random step in the model. 
	 *  This method can only be called for purely stochastic Systems. 
	 *  When called on nondeterministic systems, the resolution of choices is uniform
	 * @throws PlasmaSimulatorException 
	 */
	@Override
	public InterfaceState simulate() throws PlasmaSimulatorException {
		try {
			return active_simulator.simulate();
		}
		catch(PlasmaRuntimeException e) {
			throw new PlasmaSimulatorException(e);
		}
	}

	/** Revert to previous state in the trace. */
	@Override
	public void backtrack() throws PlasmaSimulatorException {
		try {
			active_simulator.backtrack();
		}
		catch(PlasmaRuntimeException e) {
			throw new PlasmaSimulatorException(e);
		}
	}

	@Override
	public void cut(int stateNb) throws PlasmaSimulatorException {
		active_simulator.cut(stateNb);
	}

	@Override
	public RMLState getCurrentState() {		
		return active_simulator.getCurrentState();
	}

	@Override
	public List<InterfaceState> getTrace() {		
		return active_simulator.getTrace();
	}
	
	@Override
	public InterfaceState getStateAtPos(int pos) {		
		return active_simulator.getStateAtPos(pos);
	}

	@Override
	public int getDeadlockPos() {
		return active_simulator.getDeadlockPos();
	}
	
	@Override
	public List<InterfaceIdentifier> getStateProperties() {		
		return new ArrayList<InterfaceIdentifier>(model.getLabels().values());
	}

	/** Parse the model and create the simulator. */
	@Override
	public boolean checkForErrors() {
		if(errors == null)
			errors = new ArrayList<PlasmaDataException>();
		else
			errors.clear();
		try {
			PRISMParserProcessing process = new PRISMParserProcessing(content);
			this.model = process.model;
		}
		catch (PlasmaSyntaxException e) {
			errors.add(e);
			return true;
		}
		catch (PlasmaRuntimeException e) {
			errors.add(new PlasmaSyntaxException(e));
			return true;
		} catch (RecognitionException e) {
			errors.add(new PlasmaSyntaxException(e));
			return true;
		} catch (Exception e) {
			errors.add(new PlasmaSyntaxException(e));
			return true;
		} 
		        
        switch(model.getType()){
        case dtmcsampling: case ctmcsampling:
        	simulator = new RMLSamplingSimulator(this);
        	break;
        case mdp: case mdpshd:
        	simulator = new RMLMDPSimulator(this);
        	break;
        case mdpsampling: case mdpshdsampling:
        	simulator = new RMLMDPSamplingSimulator(this);
        	break;
        case pta:
        	errors.add(new PlasmaDataException("The RML simualtor does support PTA"));
			return true;
        default:
        	simulator = new RMLSimulator(this);
        }
        uniform_simulator = new RMLUniformSimulator(this);
        active_simulator = simulator;
        
		return false;
	}

	/** Return all the identifiers that can be checked by BLTL requirements (constants, global variables and local variables, module parameters, labels, rewards). */
	@Override
	public Map<String, InterfaceIdentifier> getIdentifiers() {
		Map<String, InterfaceIdentifier> ret = new HashMap<String, InterfaceIdentifier>();
		for (Constant c : model.getConstants())
			ret.put(c.getName(),c);
		for (Variable v : model.getDefaultSystem().getVariables())
			ret.put(v.getName(),v);
		for (Module m : model.getDefaultSystem().getModules().values())
			for (Parameter p : m.getParameters())
				if (p.constant)
					ret.put(p.getName(), p);
		for (Label l : model.getLabels().values())
			ret.put(l.getName(),l);
		for (Reward r : model.getRewards())
			ret.put(r.getName(),r);
		if (hasTime())
			ret.put(timeId.getName(), timeId);
		return ret;
	}

	/** Return all the identifiers that must be followed in the GUI tables (variables and labels).
	 *  Sort the identifiers by alphabetic order.
	 **/
	@Override
	public InterfaceIdentifier[] getHeaders() {
		int size = model.getDefaultSystem().getVariables().size()+model.getLabels().size()+model.getRewards().size();
		if(hasTime())
			size ++;
		InterfaceIdentifier[] ret = new InterfaceIdentifier[size];
		int index=0;
		for (InterfaceIdentifier v : model.getDefaultSystem().getVariables()) {
			ret[index] = v;
			index++;
		}
		for (InterfaceIdentifier l : model.getLabels().values()) {
			ret[index] = l;
			index++;
		}
		for (InterfaceIdentifier r : model.getRewards()) {
			ret[index] = r;
			index++;
		}
		if (hasTime())
			ret[index] = timeId;
		Arrays.sort(ret);
		return ret;
	}
	
	public RMLState createInitialState(PlasmaSystem sys, double time, Random rng) {
        HashMap<InterfaceIdentifier, Double> mapValues = new HashMap<InterfaceIdentifier, Double>(sys.getVariables().size());
        if (sys.getInit() != null) {
        	boolean ok = false;
        	while (!ok) {
				for (Variable var : sys.getVariables()) {
					double val = var.pickRandomInitValue(rng);
		        	mapValues.put(var, val);
		        	var.ref.value = val;
				}
				sys.getInit().accept(new SimpleEval());
				ok = (sys.getInit().value == 1.0);
	        }
        	// Revert the value of the ref, so that the expressions of the model are properly evaluated by updateExpressions
			for (Variable var : sys.getVariables()) {
	        	var.ref.value = Double.NaN;
			}
			sys.getInit().accept(new SimpleEval());
        }
        else {
        	for (Variable var : sys.getVariables()) {
    			mapValues.put(var, var.initValue.value);
        	}
        }
		if (hasTime())
			mapValues.put(timeId, time);
		RMLState newState = new RMLState(mapValues,null,sys);
        newState.updateExpressions();
        
        // add labels
        for (Label l : sys.getLabels().values()) {
        	newState.values.put(l, l.body.value);
        }
        // add rewards
        for (Reward r : model.getRewards()) {
        	newState.values.put(r, 0.0);
        }
        return newState;
	}
	
	@Override
	public void setValueOf(Map<InterfaceIdentifier,Double> update) throws PlasmaSimulatorException {
		try {
			List<Ref> updated = new ArrayList<Ref>(update.size());
			Set<Constant> updatedConstants = new HashSet<Constant>(model.getConstants().size());
			for (Entry<InterfaceIdentifier, Double> e : update.entrySet()) {
				InterfaceIdentifier ident = e.getKey();
				if (ident instanceof Constant) {		
					Constant c = (Constant) ident;
					double value = e.getValue().doubleValue();
					c.ref.value = value;
					updated.add(c.ref);
					updatedConstants.add(c);
				}
				else if (ident instanceof Variable) {
					double value = e.getValue().doubleValue();
					((Variable) ident).initValue = model.getDefaultSystem().factory.makeValue(value);
				}
			}
			Update up = new Update();
			up.propagate(updated);
			model.updateConstantValues(updatedConstants);
		}
		catch(PlasmaRuntimeException e) {
			throw new PlasmaSimulatorException(e);
		}
	}

	@Override
	public boolean hasTime() {
		return model.getType().equals(ModelType.ctmc) || model.getType().equals(ModelType.ctmcsampling);
	}

	@Override
	public InterfaceIdentifier getTimeId() {
		return timeId;
	}
	
	public Model getModel() {
		return model;
	}

}