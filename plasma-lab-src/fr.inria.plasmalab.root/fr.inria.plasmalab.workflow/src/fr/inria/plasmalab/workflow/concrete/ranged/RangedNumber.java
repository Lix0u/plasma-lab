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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class RangedNumber<E> implements RangedValue<E> {
	final static Logger logger = LoggerFactory.getLogger(RangedNumber.class);
	protected Double min;
	protected Double max;
	protected Double inc;
	@JsonIgnore
	protected Double value;
	
	//No Arg constructor for deserialization of Serializable class extending RangedNumber
	public RangedNumber(){
		logger.debug("rn "+min+" "+max+" "+inc);
		min = 0.;
		max = 0.;
		inc = 1.;
		value = 0.;
	}
	
	public RangedNumber (double lower, double upper, double step) {		
		this.max = upper;		
		this.min	= lower;
		this.inc	= step;
		this.value	= lower; //default value is set to the smallest of the range
	}

	/** Initalize value to minimum 
	 * 
	 */
	public void init(){
		value = min;
	}
	
	/** Increments the current [value] by [step]. It returns true iff
	 * 	the increment was enable and done. 
	 */
	public boolean incr() {
		if (max > 0 && inc>0 && value+inc<=max) {
			value += inc;
			//intval++
			return true;
		} else
			return false;
	}
	/** Similar to {@code incr()} method but decrements the value */
	public boolean decr() {
		if (value > min && max > 0) {
			value -= inc;// intval--; 
			return true;
		} else
			return false;
	}
	
	public void setToLower () {
		value	= min;
		//intval	= lower;
	}

	public void setToUpper () {
		value	= max;
		//intval	= (upper - lower) / step;
	}

	
	/** returns the position of the current value in the range */
	public Integer toInt() {
		return value.intValue();
	}
	
	/**
	 * returns a clone of the current Double value.
	 * @return
	 */
	@JsonIgnore
	public Double getActualValue(){
		return new Double(value);
	}
	
	/** returns the number of the all values for the RangedInteger */
	public int rangeSize() {
		return (int) ((max - min) / inc + 1);
	}
	
	public String toString () {
		return String.valueOf(value);
	}
	
	@Override
	public abstract RangedNumber<E> clone();
}
