/**
 * This file is part of fr.inria.plasmalab.llvm :: LLVM plugin.
 *
 * fr.inria.plasmalab.llvm :: LLVM plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.llvm :: LLVM plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.llvm :: LLVM plugin.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.llvm;

import java.util.HashMap;
import java.util.Map;

import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

/** Only store the statetag */
public class LLVMState implements InterfaceState {
	
	protected LLVMSimulator simulator;
	protected long statevalue;
	
	public LLVMState(long tag, LLVMSimulator sim) {
		this.statevalue = tag;
		this.simulator = sim;
	}
	
	public LLVMState(LLVMState original) {
		this.statevalue = original.statevalue;
		this.simulator = original.simulator;
	}
	
	@Override
	public InterfaceState cloneState() {
		return new LLVMState(this);
	}

	@Override
	public InterfaceIdentifier[] getHeaders() {
		return simulator.getHeaders();
	}

	@Override
	public String getCategory() {
		return "LLVM state";
	}

	@Override
	public String[] toStringArray() {
		String[] ret = { Long.toString(statevalue) };
		return ret;
	}
	
	public String toString() {
		return Long.toString(statevalue);
	}

	@Override
	public InterfaceTransition getIncomingTransition() {
		return null;
	}

	@Override
	public Double getValueOf(InterfaceIdentifier id) throws PlasmaSimulatorException {
		if (id == simulator.statetag)
			return new Double(statevalue);
		else
			return getValueOf(id.getName());
	}

	/** Get a value from this state.
	 *  - If the current state of the simulator is not different than this one, load this state.
	 *  - Get the value that corresponds to the identifier
	 *  - Reload the current state if it was changed.
	 */
	@Override
	public Double getValueOf(String id) throws PlasmaSimulatorException {
		Double ret = Double.NaN;
		if (id.equals(simulator.statetag.getName()))
			return new Double(statevalue);
		
		// check currentState
		LLVMState currentState = (LLVMState) simulator.getCurrentState();
		if (currentState != this) {
			// switch simulator to this state
			simulator.loadState(statevalue);
			simulator.readState(); // read the state to empty the bufferedreader
		}
		
		// send commands
		simulator.sendCommand(LLVMSimulator.getvalue_command);
		simulator.sendCommand(id);
		
		// read outputs
		String output_line = simulator.readLineFromSimulator();
		if (!output_line.startsWith("Process"))
			throw new PlasmaSimulatorException("Error while reading value of variable " + id + ": no \"Process\"");
		output_line = simulator.readLineFromSimulator();
		String[] v = output_line.split(" ");
		if (v.length != 4)
			throw new PlasmaSimulatorException("Error while reading value of variable " + id + ": wrong format");
		if (!id.equals(v[2]))
			throw new PlasmaSimulatorException("Error while reading value: received variable " + v[2] + " does not match query " + id);
		try {
			ret = Double.parseDouble(v[3]);
		} catch (NumberFormatException e) {
			throw new PlasmaSimulatorException("Error while reading value of variable " + id, e);
		}
		output_line = simulator.readLineFromSimulator();
		if (!output_line.startsWith("/Process"))
			throw new PlasmaSimulatorException("Error while reading value of variable " + id + ": no \"/Process\"");
		
		// check currentState again
		if (currentState != this) {
			// switch back to currentState
			simulator.loadState(currentState.statevalue);
			simulator.readState(); // read the state to empty the bufferedreader
		}
		return ret;
	}

	/** Only output the statetag */
	@Override
	public Map<InterfaceIdentifier, Double> getValues() {
		HashMap<InterfaceIdentifier, Double> ret = new HashMap<InterfaceIdentifier, Double>(1);
		ret.put(simulator.statetag, new Double(statevalue));
		return ret;
	}

	@Override
	public void setValueOf(InterfaceIdentifier id, double value) throws PlasmaSimulatorException {
		throw new PlasmaSimulatorException("setValueOf method is not implemented for LLVMState");
	}


}
