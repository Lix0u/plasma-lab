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

/**
 * SMCParameter class defines SMC algorithms parameter object.
 * 
 * These parameters are instantiated by each SMC AlgorithmFactory. Their purpose is to describe
 * the information needed for launching a SMC algorithm. They are used by the GUI to display
 * corresponding graphical components.
 * 
 * The SMCParameter class describes a parameter either of type text or boolean.
 * 
 * @see InterfaceAlgorithmFactory
 * @see ParametersPanel
 * 
 * @author kevin.corre@inria.fr
 */
public class SMCParameter {
	
	protected String name;
	protected String tip;
	protected boolean isBool;
	
	/**
	 * SMC Parameter constructor.
	 * 
	 * @param name the name of this parameter.
	 * @param tip the tip associated with this parameter.
	 * @param isBool true if this parameter is a boolean value, false if it is a text value.
	 */
	public SMCParameter(String name, String tip, boolean isBool){
		this.name = name;
		this.tip = tip;
		this.isBool = isBool;
	}
	/**
	 * This method returns the name of this parameter.
	 * @return the name of this parameter.
	 */
	public String getName(){
		return name;
	}
	/**
	 * This method returns the tip associated with this parameter.
	 * @return the tip of this parameter.
	 */
	public String getTip(){
		return tip;
	}
	/**
	 * This method indicate if this parameter is a boolean value or a text value.
	 * @return the isBool value.
	 */
	public boolean isBoolean(){
		return isBool;
	}

}
