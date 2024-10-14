/**
 * This file is part of fr.inria.plasmalab.root.
 *
 * fr.inria.plasmalab.root is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.root is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.root.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.test;

import java.util.HashMap;
import java.util.Map;

import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceTransition;

public class TestState implements InterfaceState {

	private double value;
//	private int state;
//	private int trace;
	
	public TestState(double value) {
		this.value = value;
//		this.state = s;
//		this.trace = t;
	}
	
	@Override
	public InterfaceIdentifier[] getHeaders() {
		InterfaceIdentifier[] headers = new InterfaceIdentifier[3];
		headers[0] = TestSimulator.valueId;
		return headers;
	}

	@Override
	public String getCategory() {
		return "Test State";
	}

	@Override
	public String[] toStringArray() {
		String[] ret = new String[0];
		ret[0] = Double.toString(value);
		return ret;
	}

	@Override
	public InterfaceTransition getIncomingTransition() {
		return null;
	}

	@Override
	public Double getValueOf(InterfaceIdentifier id) {
		return getValueOf(id.getName());
	}

	@Override
	public Double getValueOf(String id) {
		switch (id) {
		case "value":
			return value;
//		case "state":
//			return new Double(state);
//		case "trace":
//			return new Double(trace);
		default:
			return Double.NaN;
		}
	}

	@Override
	public Map<InterfaceIdentifier, Double> getValues() {
		Map<InterfaceIdentifier, Double> ret = new HashMap<InterfaceIdentifier, Double>(3);
		ret.put(TestSimulator.valueId, value);
		return ret;
	}

	@Override
	public void setValueOf(InterfaceIdentifier id, double value) { 
		switch (id.getName()) {
		case "value":
			this.value = value;
			break;
//		case "state":
//			this.state = (int)value;
//			break;
//		case "trace":
//			this.trace = (int)value;
//			break;
		}
	}

	@Override
	public InterfaceState cloneState() { 
		return new TestState(value);
	}

}
