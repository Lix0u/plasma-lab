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
package fr.inria.plasmalab.workflow.data.simulation;


import java.util.Map;

import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.shared.ResultInterface;
/**
 * A State is an array of value.
 * <p>
 * A State is an array of value, each value corresponding to an identifier. It also refers
 * to a set of enabled transition and a chosen transition.
 * @author kevin.corre@inria.fr
 *
 */
public interface InterfaceState extends ResultInterface {
	
	/**
	 * This method returns an ArrayList containing each value under a String representation.
	 * 
	 * @return an ArrayList of each value in String format
	 */
	@Deprecated
	public String[] toStringArray();
	
	/**
	 * This method returns the transition from which this state has been reached.
	 * @return the incoming transition of this state.
	 */
	public InterfaceTransition getIncomingTransition();
	
	/**
	 * This method returns the value of an identifier in this state.
	 * @param id the {@link InterfaceIdentifier}.
	 * @return the corresponding value
 	 * @throws PlasmaSimulatorException if the value is not found
	 */
	public Double getValueOf(InterfaceIdentifier id) throws PlasmaSimulatorException;
	
	/**
	 * This method returns the value of an identifier in this state.
	 * @param id the {@link String} id of the {@link InterfaceIdentifier}.
	 * @return the corresponding value
	 * @throws PlasmaSimulatorException if the value is not found
	 * 
	 */
	public Double getValueOf(String id) throws PlasmaSimulatorException;
	
	/**
	 * @return A map of the values store in this state.
	 */
	public Map<InterfaceIdentifier,Double> getValues();

	/**
	 * This method set the value of an identifier in this state.
	 * @param id the {@link InterfaceIdentifier}.
	 * @param value the corresponding value.
	 */
	public void setValueOf(InterfaceIdentifier id, double value) throws PlasmaSimulatorException;
	
	/**
	 *  Clone a state.
	 */
	public InterfaceState cloneState();
}
