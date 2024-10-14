/**
 * This file is part of fr.inria.plasmalab.bltl.
 *
 * fr.inria.plasmalab.bltl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bltl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bltl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.bltl.ast.printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.bltl.ast.leaves.IdentExpr;
import fr.inria.plasmalab.workflow.concrete.Variable;
/**
 * This is a pretty printer generating a set of BLTLRequirement
 * from a BLTLRequirement and a set of Variable.
 * @author kevin.corre@inria.fr
 *
 */
public class BLTLRequirementInstantiator extends PrettyPrinter {
	protected List<Map<String, Double>> reqVariablesRange;
	
	protected int rangeIndex;
	
	public BLTLRequirementInstantiator(List<Variable> requirementVariables){
		super();
		reqVariablesRange = getReqVariablesRange(requirementVariables);
	}
	
	public List<String> instantiate(Expr root) {
		if(reqVariablesRange == null || reqVariablesRange.size()<=0)
			return null;
		List<String> properties = new ArrayList<String>();
		for(rangeIndex=0;rangeIndex<reqVariablesRange.size();rangeIndex++ ){
			root.accept(this);
			properties.add(result);
		}
		return properties;
	}
	

	@Override
	public void visit(IdentExpr idExpr) {
		// Is it a variable?
		if(reqVariablesRange.get(rangeIndex).containsKey(idExpr.id.getName()))
			result = reqVariablesRange.get(rangeIndex).get(idExpr.id.getName()).toString();
		else
			result = idExpr.id.getName();
	}
	
	/**
	 * This method generate the list of initial values from a list of optimization variables and a model.
	 * 
	 * @param model
	 * @param optimVariables
	 * @return
	 */
	public List<Map<String, Double>> getReqVariablesRange(List<Variable> requirementVariables){
		List<Map<String, Double>> range = new ArrayList<Map<String,Double>>();
		
		boolean overflow;
		if(requirementVariables != null && requirementVariables.size()>0){
			for (Variable digit: requirementVariables){
				digit.init();
			}
			do{
				Map<String, Double> valueMap = new HashMap<String, Double>();
				for(Variable var:requirementVariables){
					valueMap.put(var.getName(), var.getActualValue());
				}
				range.add(valueMap);
				overflow = true;
				// we increment the number :
				for (Variable digit: requirementVariables){
					if (digit.incr()) {
						overflow = false; break;	//No carry: increment is finished
					} else 							// we get a carry: digit = lower and we continue to propagate the carry
						digit.setToLower();
				}
			}while(!overflow);
		}else{
			return null;
		}
		return range;
	}
}
