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
package fr.inria.plasmalab.workflow.concrete.ranged;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.inria.plasmalab.workflow.concrete.Variable;

public class RangedNumbers implements RangedValue<List<RangedNumber<Number>>>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -151520164979594494L;
	// Each digit is ranged over its own domain <lower, upper, step>
	protected ArrayList<RangedNumber<Number>> digits;
	//protected int intValue = 0;
	
	public RangedNumbers() {digits = new ArrayList<RangedNumber<Number>>();}
	
	public RangedNumbers(List<Variable> varList){
		digits = new ArrayList<RangedNumber<Number>>(varList.size());
		for(int i=0; i<varList.size(); i++)
			digits.add(varList.get(i));
	}
	
	public RangedNumbers(RangedNumber<Number>[] array) {
		digits = new ArrayList<RangedNumber<Number>>(array.length);
		for(int i=0; i<array.length; i++)
			digits.add(array[i]);
		//evalAsInt();
	}	

	// An Integer representation of the rangedNumber
	protected void evalAsInt() {
		/*int coeff = 1;
		intValue = 0;
		
		for(RangedNumber<Number>  digit: digits) {
			intValue += digit.toInt() * coeff;
			coeff *= digit.rangeSize();
		}*/
	}



	/** Initalize value to minimum 
	 * 
	 */
	public void init() {
		for(RangedNumber<Number> rn:digits)
			rn.init();
	}
	
	public boolean incr() {
		boolean overflow = true;

		// we increment the number :
		for (RangedNumber<Number>  digit: digits)
			if (digit.incr()) {
				overflow = false; break;	//No carry: increment is finished
			} else 							// we get a carry: digit = lower and we continue to propagate the carry
				digit.setToLower();
		// if the loop is completed with a remaining carry <=> overflow
		// Notice that when overflow is true, all digit is setToLower
		// We also increment the Integer valuation
		//intValue = overflow ? 0 : intValue + 1;
		return !overflow;
	}

	public void setToUpper() {
		for (RangedNumber<Number>  digit: digits)
			digit.setToUpper();
		//evalAsInt();
	}

	public void setToLower() { // "the number "Zero"
		for (RangedNumber<Number>  digit: digits)
			digit.setToLower();
		//intValue = 0;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<RangedNumber<Number>> getValue() {
		return (ArrayList<RangedNumber<Number>>) digits.clone();
	}

	public int toInt () {
		return 0;//intValue;
	}
	
	public RangedNumbers clone() {
		RangedNumbers copy = new RangedNumbers();
		//copy.intValue = this.intValue;
		copy.digits = new ArrayList<RangedNumber<Number>>(this.digits);
		return copy;
	}
	
	public String toString () {
		String str = "";
		for (RangedNumber<Number>  digit: digits)
			str += "." + digit +"= "+digit.value ;
		return str;
	}
}
