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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.ast.adaptive.AdaptiveSystem;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Label;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.exceptions.DuplicateIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class AdaptiveModel extends Model {

	protected AdaptiveSystem adaptive_system;
	protected Map<String,PlasmaSystem> systems;		
	
	public AdaptiveModel(ModelType t) {
		super(t);
		adaptive_system = null;
		systems = new HashMap<String,PlasmaSystem>(0);
	}

	// --------- Systems ---------
	public Map<String,PlasmaSystem> getSystems() {
		return systems;
	}
	
	public PlasmaSystem getSystem(String name) {
		return systems.get(name);
	}
	
	public void addSystem(PlasmaSystem s) {
		systems.put(s.getName(), s);
	}
	
	// This function is overwritten such that the new label is added to each system of the adaptive model  
	public void addLabel(Label lab) throws DuplicateIdentifier {
		super.addLabel(lab);
		for (PlasmaSystem sys : systems.values()) {
			sys.addLabel(lab);
		}
	}
	
	// --------- Adaptive Systems ---------
	public AdaptiveSystem getAdaptiveSystem() {
		return adaptive_system;
	}
	
	public void setAdaptiveSystem(AdaptiveSystem sys) {
		adaptive_system = sys;
	}
		
	// ------------------
	
	public boolean typeCheck() {
		for (PlasmaSystem sys : systems.values()) {
			sys.typeCheck();
		}
		return true;
	}
	
	public String toString() {
		String ret = "adaptive model " + type.toString() + "\n";
		for (Variable v : globalVars.values()) {
			ret += "\t global variable " + v.toString() + "\n";
		}
		for (PlasmaSystem sys : systems.values()) {
			ret += sys.toString() + "\n";
		}
		ret += adaptive_system.toString() + "\n";
		return ret;
	}
	
	public String toPrism(List<String> idents) throws PlasmaSyntaxException {
		String ret = type.toString() + "\n\n";
		// constants:
		for (Constant c : constants.values()) {
			ret += "const " + c.type.toString() + " " + c.getName() + " = " + c.value.toPrism("") + ";\n";
		}
		// global variables:
		for (Variable v : globalVars.values()) {
			ret += "global " + v.toPrism("") + ";\n";
			idents.add(v.getName());
		}
		// labels
		for (Label l : labels.values()) {
			ret += l.toPrism("") + ";\n";
		}
		for (PlasmaSystem sys : systems.values()) {
			for (Label l : sys.getLabels().values()) {
				ret += l.toPrism(sys.getName() + "_") + ";\n";
			}
		}

		// modules and adaptive transitions
		ret += adaptive_system.toPrism(idents) + "\n";
		return ret;
	}
}
