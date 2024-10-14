/**
 * This file is part of fr.inria.plasmalab.algorithm.
 *
 * fr.inria.plasmalab.algorithm is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.algorithm is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.algorithm.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.algorithm.data;

import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

/**
 * This interface represents results returned by SMCAlgorithms to the GUI.
 * 
 * @author kevin.corre@inria.frheaders
 */
public interface SMCResult extends ResultInterface{
	/**
	 * Returns the AbstractRequirementheaders used to instantiate and check the requirement
	 * that produced this SMCResult.
	 * @return an AbstractRequirement.
	 */
	@Deprecated
	public AbstractRequirement getOriginRequirement();
	/**
	 * Returns the probability computed (Positive/Total).
	 * @return the probability
	 */
	public double getPr();
	/**
	 * Returns the total number of simulation.
	 * @return the total number of simulation.
	 */
	@Deprecated
	public int getTotalCount();

}
