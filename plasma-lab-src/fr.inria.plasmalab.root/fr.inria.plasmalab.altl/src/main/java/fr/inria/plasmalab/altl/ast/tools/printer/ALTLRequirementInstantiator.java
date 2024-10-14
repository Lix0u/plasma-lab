/**
 * This file is part of fr.inria.plasmalab.altl.
 *
 * fr.inria.plasmalab.altl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.altl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.altl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.altl.ast.tools.printer;

import java.util.List;

import fr.inria.plasmalab.altl.ast.AVisitor;
import fr.inria.plasmalab.altl.ast.Adaptive;
import fr.inria.plasmalab.bltl.ast.printer.BLTLRequirementInstantiator;
import fr.inria.plasmalab.workflow.concrete.Variable;

public class ALTLRequirementInstantiator extends BLTLRequirementInstantiator implements AVisitor{
	
	public ALTLRequirementInstantiator(List<Variable> requirementVariables) {
		super(requirementVariables);
	}

	@Override
	public void visit(Adaptive expr) {
		visitChild(expr.assumption, false);
		String strA = result;
		visitChild(expr.pre_trigger, false);
		String strPreT = result;
		visitChild(expr.post_trigger, false); 
		String strPostT = result;
		visitChild(expr.guarantee, false);
		String strG = result;

		result = strA + "; [" + strPreT + "," + strPostT + "]" + "==>" + strG;
	}

	@Override
	public void visit(fr.inria.plasmalab.altl.ast.IdentExpr idExpr) {
		// Is it a variable?
		if(reqVariablesRange.get(rangeIndex).containsKey(idExpr.id))
			result = reqVariablesRange.get(rangeIndex).get(idExpr.id).toString();
		else
			result = idExpr.name;
	}
}
