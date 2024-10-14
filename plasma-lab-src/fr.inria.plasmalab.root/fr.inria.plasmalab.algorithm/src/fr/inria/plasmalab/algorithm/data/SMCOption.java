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

import java.util.List;
/**
 * The SMCOption is a list of optional SMCParameter.
 * 
 * The SMCOption class extends the SMCParameter class. It represents a list of 
 * SMCParameter regrouped under the same option. Using this structure allow to 
 * create a list of parameter such as:
 * A SMCParameter
 * B SMCOption
 * + C SMCParameter (depends of B)
 * 
 * The GUI displays it as a check box enabling a list of parameters when checked.
 * 
 * @author kevin.corre@inria.fr
 */
public class SMCOption extends SMCParameter{
	private List<SMCParameter> options;
	
	/**
	 * 
	 * @param name the name of the option.
	 * @param tip the tip of the option.
	 * @param options the list of SMCParameter depending from this option.
	 */
	public SMCOption(String name, String tip, List<SMCParameter> options) {
		super(name,tip,true);
		this.options=options;
	}
	/**
	 * This method returns the list of SMCParameter.
	 * @return the list of SMCParameters
	 */
	public List<SMCParameter> getOptions(){
		return options;
	}
}
