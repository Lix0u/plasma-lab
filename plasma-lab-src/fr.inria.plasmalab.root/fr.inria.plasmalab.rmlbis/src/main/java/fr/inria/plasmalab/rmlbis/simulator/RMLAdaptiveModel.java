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


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.antlr.runtime.RecognitionException;

import fr.inria.plasmalab.rmlbis.ast.Module;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Label;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Parameter;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.models.AdaptiveModel;
import fr.inria.plasmalab.rmlbis.ast.rewards.Reward;
import fr.inria.plasmalab.rmlbis.parsing.AdaptiveParserProcessing;
import fr.inria.plasmalab.rmlbis.simulator.responsibility.RMLAdaptiveSimulator;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class RMLAdaptiveModel extends RMLModel {
	
	public RMLAdaptiveModel(String name, File file, String id) throws PlasmaDataException {
		super(name,file,id);
	}

	public RMLAdaptiveModel(String name, String content, String id){
		super(name,content,id);
	}
	
	public AdaptiveModel getModel() {
		return (AdaptiveModel) model;
	}

	@Override
	public boolean checkForErrors() {
		if(errors == null)
			errors = new ArrayList<PlasmaDataException>();
		else
			errors.clear();
		try {
			AdaptiveParserProcessing process = new AdaptiveParserProcessing(content);
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
		}
		
        simulator = new RMLAdaptiveSimulator(this);
		return false;
	}
	
	/** Return all the identifiers of all the systems (constants, global variables, local variables, parameters, system labels).
	 *  The key is the name prefixed by the system name.
	 *  However, this function is not used in ALTL. 
	 */
	@Override
	public Map<String, InterfaceIdentifier> getIdentifiers() {
		Map<String, InterfaceIdentifier> ret = new HashMap<String, InterfaceIdentifier>();
		for (Constant c : model.getConstants())
			ret.put(c.getName(),c);
		for (Variable v : model.getGlobalVariables())
			ret.put(v.getName(),v);
		for (Reward r : model.getRewards())
			ret.put(r.getName(),r);
		//  Global labels are copied in each systems 
		//		for (Label l : model.getLabels().values())
		//			ret.put(l.getName(),l);
		for (PlasmaSystem sys : getModel().getSystems().values()) {
			for (Parameter p : sys.getParameters())
				ret.put(sys.getName() + "_" + p.getName(),p);
			for (Label l : sys.getLabels().values())
				ret.put(sys.getName() + "_" + l.getName(),l);
			for (Module m : sys.getModules().values()) {
				for (Variable v : m.getLocalVariables().values())
					ret.put(sys.getName() + "_" + v.getName(),v);
				for (Parameter p : m.getParameters())
					if (p.constant)
						ret.put(sys.getName() + "_" + p.getName(),p);
			}
		}
		if (hasTime())
			ret.put(timeId.getName(), timeId);
		return ret;
	}

	/** Return all the identifiers that must be followed in the GUI tables (variables and labels).
	 *  Sort the identifiers by alphabetic order.
	 **/
	@Override
	public InterfaceIdentifier[] getHeaders() {
		LinkedList<InterfaceIdentifier> list = new LinkedList<InterfaceIdentifier>();
		if (hasTime())
			list.add(timeId);
		for (Variable v : model.getGlobalVariables())
			list.add(v);
		for (Label l : model.getLabels().values())
			list.add(l);
		for (Reward r : model.getRewards())
			list.add(r);
		Collections.sort(list);
		for (PlasmaSystem sys : getModel().getSystems().values()) {
			ArrayList<InterfaceIdentifier> templist = new ArrayList<InterfaceIdentifier>();
			for (Label l : sys.getLabels().values()) {
				if (model.getLabel(l.getName()) == null) // only local labels
					templist.add(l);
			}
			for (Module m : sys.getModules().values()) {
				for (Variable v : m.getLocalVariables().values())
					templist.add(v);
			}
			Collections.sort(templist);
			list.addAll(templist);
		}
		return list.toArray(new InterfaceIdentifier[list.size()]);
	}

}