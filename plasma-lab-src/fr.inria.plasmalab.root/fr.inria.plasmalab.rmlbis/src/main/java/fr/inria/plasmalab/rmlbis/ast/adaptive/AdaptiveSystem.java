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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.rmlbis.ast.Module;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystemInstance;
import fr.inria.plasmalab.rmlbis.ast.models.AdaptiveModel;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class AdaptiveSystem {

	private AdaptiveModel model;
	private PlasmaSystemInstance startSystem;
	private Map<PlasmaSystem,List<AdaptiveCommand>> transitions;
	
	public AdaptiveSystem(AdaptiveModel m) {
		this.model = m;
		this.transitions = new HashMap<PlasmaSystem, List<AdaptiveCommand>>(model.getSystems().size());
		for (PlasmaSystem sys : model.getSystems().values())
			transitions.put(sys, new ArrayList<AdaptiveCommand>());
	}
	
	// --------- Start ---------
	
	public void setStartSystem(PlasmaSystemInstance sysInstance) {
		this.startSystem = sysInstance;
	}

	public PlasmaSystemInstance getStartSystemInstance() {
		return startSystem;
	}
	
	public PlasmaSystem getStartSystem() {
		if (startSystem != null)
			return startSystem.sys;
		else
			return null;
	}
	
	public void initialize() {
		if (startSystem != null)
			startSystem.initialize();
	}

	// --------- Transitions ---------
	public List<AdaptiveCommand> getTransitions(PlasmaSystem sys) {
		return transitions.get(sys);
	}
	
	public void addTransition(AdaptiveCommand t) {
		PlasmaSystem sys = t.getActiveSystem();
		transitions.get(sys).add(t);
	}
	
	public void instantiate(Map<String,Module> originals) throws PlasmaSyntaxException {
		for (List<AdaptiveCommand> l : transitions.values())
			for (AdaptiveCommand t : l)
				t.instantiate(originals);
	}

	/** Print the plasma systems and translate the adaptive system into a new module 
	 * @throws PlasmaSyntaxException */
	public String toPrism(List<String> idents) throws PlasmaSyntaxException {
		assert startSystem != null;
		
		// print the systems
		String ret = "";
		for (PlasmaSystem sys : model.getSystems().values()) {
			ret += sys.toPrism(this,idents) + "\n";
		}
		
		// build an "adaptive"module
		ret += "\n";
		ret += "module adaptive\n";
		ret += "\tactive : [0.." + (model.getSystems().size()-1) + "] init " + getStartSystem().getId() + ";\n";
		ret += "\treinit : bool init false;\n";

		// add the adaptive commands
		for (List<AdaptiveCommand> lco : transitions.values()) {
			for (AdaptiveCommand co : lco) {
				ret += co.toPrism() + ";\n";
			}
		}
		// add reinit commands
		for (PlasmaSystem sys : model.getSystems().values()) {
			ret += "\t[" + sys.getName() + "_reinit] reinit & active=" + sys.getId() + " -> 1000000 : (reinit'=false);\n";
		}
		ret += "endmodule\n";
		
		return ret;
	}
	
	
	public String getPrismAdaptiveGuards() throws PlasmaSyntaxException {
		String ret = "";
		for (List<AdaptiveCommand> lco : transitions.values()) {
			for (AdaptiveCommand co : lco) {
				if (!ret.isEmpty())
					ret += " | ";
				ret += co.getPrismGuard();
			}
		}
		return "(" + ret + ")";
	}
}
