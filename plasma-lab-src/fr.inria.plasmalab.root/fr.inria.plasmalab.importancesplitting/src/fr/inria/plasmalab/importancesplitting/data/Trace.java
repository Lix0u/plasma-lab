/**
 * This file is part of fr.inria.plasmalab.importancesplitting.
 *
 * fr.inria.plasmalab.importancesplitting is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.importancesplitting is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.importancesplitting.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.importancesplitting.data;

import java.util.List;
import java.util.ListIterator;

import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public class Trace {
	private List<InterfaceState> modelStates;
	private List<InterfaceState> observerStates;
	private double score; // maximum score along the trace
		
	public Trace(List<InterfaceState> mstates, List<InterfaceState> ostates, double score) {
		this.modelStates = mstates;
		this.observerStates = ostates;
		this.score = score;
	}
		
	public GlobalState getBranchingState(double score) throws PlasmaSimulatorException {
		double lastScore = -1;
		InterfaceState observerState = null;
		InterfaceState modelState = null;
		ListIterator<InterfaceState> it = modelStates.listIterator();
		ListIterator<InterfaceState> jt = observerStates.listIterator();
		while (lastScore <= score && it.hasNext()) {
			modelState = it.next();
			observerState = jt.next();
			lastScore = observerState.getValueOf("score");
		}
		return new GlobalState(modelState, observerState);
	}
	
	public GlobalState getLastState() {
		int index = modelStates.size()-1;
		return new GlobalState(modelStates.get(index), observerStates.get(index));
	}
	
	public double getScore() {
		return score;
	}
	
	public List<InterfaceState> getModelTrace() {
		return modelStates;
	}
	
	public List<InterfaceState> getObserverTrace() {
		return observerStates;
	}
}
