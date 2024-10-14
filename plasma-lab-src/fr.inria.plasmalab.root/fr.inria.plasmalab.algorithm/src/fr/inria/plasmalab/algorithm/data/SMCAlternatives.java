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
 * The SMCAlternatives represents lists of exclusive SMCParameter.
 * 
 * The SMCAlternatives class extends the SMCParameter class. It represents 
 * several list of SMCParameter, each list excluding the others.
 * Using this structure allow to create a list of parameter such as:
 * A SMCParameter
 * B SMCAlternatives (exclusive with D)
 * + C SMCParameter (depends of B)
 * D SMCAlternatives (exclusive with B)
 * + E SMCParameter (depends of D)
 * + F SMCParameter (depends of D)
 * 
 * The GUI displays it as several radio button enabling a list of parameters when checked.
 * 
 * @author kevin.corre@inria.fr
 *
 */
public class SMCAlternatives extends SMCParameter {
	protected List<SMCParameter> alternatives;
	protected SMCAlternatives next;
	protected SMCAlternatives head;
	
	/**
	 * Basic constructor for a SMCAlternatives.
	 * @param name the name of this alternative.
	 * @param tip the tip.
	 * @param alternatives the list of SMCParameter enabled by this alternative.
	 * @param head the head of this list of SMCAlternatives. If null, head = this.
	 */
	public SMCAlternatives(String name, String tip, List<SMCParameter> alternatives,
			SMCAlternatives head) {
		super(name, tip, true);
		this.alternatives = alternatives;
		this.next = null;
		if(head == null)
			this.head = this;
		else
			this.head = head;
	}
	/**
	 * This method returns the list of SMCParameter enabled when this alternative
	 * is active.
	 * @return a list of SMCParameter
	 */
	public List<SMCParameter> getAlternatives(){
		return alternatives;
	}
	/**
	 * This method returns the next SMCAlternatives related to this.
	 * @return a SMCAlternaives
	 */
	public SMCAlternatives getNext(){
		return next;
	}
	/**
	 * This method set the next SMCAlternatives related to this.
	 * @param next the next SMCAlternatives
	 */
	public void setNext(SMCAlternatives next){
		this.next = next;
	}
	/**
	 * This method returns the head of this SMCAlternatives list.
	 * @return the head of this SMCAlternatives list.
	 */
	public SMCAlternatives getHead(){
		return head;
	}

}
