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

public class SMCObjectParameter extends SMCParameter {
	/**
	 * This class defines a parameter as an AbstractModel.
	 * 
	 * The GUI translates this parameter into a combo box allowing 
	 * to choose an AbstractModel of the current projet. 
	 * 
	 * ie: it is used in the CUSUM algorithm which needs a model to test
	 * and a second model to run a "benchmark".
	 * 
	 * @param name the name of the parameter.
	 * @param tip the tip associated with the parameter.
	 * @param isBool not used in this configuration.
	 */
	public SMCObjectParameter(String name, String tip, boolean isBool){
		super(name,tip,false);
	}
}
