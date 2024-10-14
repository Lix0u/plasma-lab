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


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Context is a flag:
 * INIT can replace REQ but should not happen
 * REQ can't replace INIT
 * INIT (resp. REQ) can't replace INIT (resp. REQ) and will throw an Exception.
 */
public class VarContext{
	private Variable var;
	private Boolean context;
	
	@JsonCreator
	public VarContext(@JsonProperty("variable")Variable var, @JsonProperty("context")Boolean context) {
		this.var=var;
		this.context=context;
	}
	
	public Variable getVar() {
		return var;
	}
	public void setVar(Variable var) {
		this.var = var;
	}
	public Boolean getContext() {
		return context;
	}
	public void setContext(Boolean context) {
		this.context = context;
	}		
}