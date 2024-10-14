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
package fr.inria.plasmalab.workflow.concrete;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.inria.plasmalab.workflow.concrete.ranged.RangedNumber;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

public class Variable extends RangedNumber<Number> implements InterfaceIdentifier{
	final static Logger logger = LoggerFactory.getLogger(Variable.class);
	private static int count = 1;
	private String name;
	private VariableType type;

	//Annotating this constructor for Jackson because when deserializing a Variable,
	// all values are known.
	@JsonCreator
	public Variable(@JsonProperty("name")String name, @JsonProperty("min")double min, 
			@JsonProperty("max")double max, @JsonProperty("inc")double inc, 
			@JsonProperty("type")VariableType type){
		super(min, max, inc);
		this.name=name;
		this.type = type;
		this.value = min;
	}
	public Variable(String name, VariableType type){
		super(0,0,1);
		this.name=name;
		this.type = type;
	}
	public Variable(String name){
		super(0,0,1);
		this.name = name;
		this.type = VariableType.UNDEFINED;
	}
	/**
	 * 
	 */
	public Variable(){
		super(0,0,1);
		type = VariableType.UNDEFINED;
		name = "Var"+count;
		count ++;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the min
	 */
	public Double getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(Double min) {
		this.min = min;
	}
	/**
	 * @return the max
	 */
	public Double getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(Double max) {
		this.max = max;
	}
	/**
	 * @return the inc
	 */
	public Double getInc() {
		return inc;
	}
	/**
	 * @param inc the inc to set
	 */
	public void setInc(Double inc) {
		this.inc = inc;
	}
	
	/**
	 * Only for serialization
	 * @param value
	 */
	public void setValue(Double value){
		this.value = value;
	}
	@Override
	public Number getValue() {
		return value;
	}
	
	
	//TYPE
	@JsonIgnore
	public boolean typeIsTime(){
		return type == VariableType.ISTIME;
	}
	@JsonIgnore
	public boolean typeIsStep(){
		return type == VariableType.ISSTEP;
	}
	@JsonIgnore
	public boolean typeIsBool(){
		return type == VariableType.BOOL;
	}
	public VariableType getType(){
		return type;
	}
	public void setType(VariableType type){
		this.type = type;
	}
	
	//OBJECT OVERRIDE
	@Override
	public String toString(){
		return name;
	}
	@Override
	public boolean equals(Object obj){
		if(obj.getClass().equals(Variable.class))
			return (((Variable)obj).getName().equals(getName()));
		else
			return false;
	}
	@Override
	public RangedNumber<Number> clone() {
		return new Variable(name, min, max, inc, type);
	}
	@Override
	public int compareTo(InterfaceIdentifier arg0) {
		// TODO Stub de la méthode généré automatiquement
		return 0;
	}
	@Override
	@JsonIgnore
	public boolean isBoolean() {
		return type.equals(VariableType.BOOL);
	}
}
