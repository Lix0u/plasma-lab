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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import fr.inria.plasmalab.rmlbis.ast.Action;
import fr.inria.plasmalab.rmlbis.ast.Command;
import fr.inria.plasmalab.rmlbis.ast.Module;
import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.processing.Update;

public class ProcedureSimulator {

	private Random rng;
	private Module proc;
	
	public ProcedureSimulator(Module p) {
		this.proc = p;
	}
	
	public void initialize(long seed) {
		rng = new Random(seed);
	}
	
	/** Simulate a procedure:
	 *  The execution is sequential: one command after another until the last one.
	 *  Enabled commands are executed by randomly choosing one of the action according to their rates.
	 */
	public void simulate() {
		for (Command c : proc.getCommands()) {
			if (c.getGuard().value != 0) {
				// 1 select an action
				Action selectedAction = null;
				double ran = rng.nextDouble()*c.getTotalRate(true);
				for (Action a : c.getActions()) {
					if (ran <= a.getRate()) {
						selectedAction = a;
						break;
					}
					else {
						ran -= a.getRate();
					}
				}

				// 2 apply the update
				Map<Ref,Double> updates = new HashMap<Ref, Double>();
				for (Entry<Variable, Expr> u : selectedAction.getAssignations().entrySet()) {
					updates.put(u.getKey().ref, u.getValue().value);
				}
				List<Ref> updated = new ArrayList<Ref>(updates.size());
				for (Entry<Ref,Double> currentVar : updates.entrySet()) {
					Ref id = currentVar.getKey();
					Double value = currentVar.getValue();
					//Ref id = model.getIdents().get(name);
					id.value = value;
					updated.add(id);
				}
				Update up = new Update();
				up.propagate(updated);	
			}
		}
	}
	
}
