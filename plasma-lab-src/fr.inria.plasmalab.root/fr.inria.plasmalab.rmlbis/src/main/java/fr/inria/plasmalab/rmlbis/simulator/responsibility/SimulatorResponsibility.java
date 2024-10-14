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
package fr.inria.plasmalab.rmlbis.simulator.responsibility;

import java.util.ArrayList;
import java.util.List;

import fr.inria.plasmalab.rmlbis.ast.models.Model;
import fr.inria.plasmalab.rmlbis.simulator.RMLModel;
import fr.inria.plasmalab.rmlbis.simulator.RMLState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public abstract class SimulatorResponsibility {

	protected RMLModel rmlModel;
	protected Model model;
	protected List<InterfaceState> trace;
	protected RMLState initialState, currentState;
	protected int deadlockPos;
	
	public SimulatorResponsibility(RMLModel rmlModel) {
		this.rmlModel = rmlModel;
		this.model = rmlModel.getModel();
		this.trace = new ArrayList<InterfaceState>();
		this.initialState = null;
		this.currentState = null;
		this.deadlockPos = -1;
	}
	
	public abstract InterfaceState newPath();

	public abstract InterfaceState newPath(long seed);
	
	public abstract InterfaceState newPath(List<InterfaceState> initTrace);

	public abstract InterfaceState simulate() throws PlasmaSimulatorException;
		
	public abstract void backtrack() throws PlasmaSimulatorException;
		
	public int getDeadlockPos(){
		return deadlockPos;
	}

	public RMLState getCurrentState() {
		return currentState;
	}
	
	public List<InterfaceState> getTrace() {		
		return trace;
	}
	
	public InterfaceState getStateAtPos(int pos) {		
		return trace.get(pos);
	}
	
	public void cut(int stateNb) throws PlasmaSimulatorException {
		if(stateNb >= trace.size())
			throw new PlasmaSimulatorException("Cannot cut at a position superior than trace length.");
		for(int i=0; i<stateNb; i++ )
			trace.remove(0);
	}
}