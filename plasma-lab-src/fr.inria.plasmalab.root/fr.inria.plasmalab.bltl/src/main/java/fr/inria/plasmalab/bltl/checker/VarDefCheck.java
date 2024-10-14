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
package fr.inria.plasmalab.bltl.checker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.bltl.ast.leaves.IdentExpr;
import fr.inria.plasmalab.bltl.ast.visitors.BasicVisitor;
import fr.inria.plasmalab.bltl.exceptions.UndefinedVariable;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;


public class VarDefCheck extends BasicVisitor {

	private Set<String> declarations;
	private List<InterfaceIdentifier> overrides;
	private List<UndefinedVariable> undefinedVariables;
	
	public VarDefCheck(Set<String> declarations, List<InterfaceIdentifier> overrides) {
		this.declarations = declarations;
		this.overrides = overrides;
		undefinedVariables = new ArrayList<UndefinedVariable>();
	}
	@Override
	public void visit(IdentExpr idExpr){
		if(idExpr.id == null){
			//There should be a declaration override, if not, it's an error
			boolean found = false;
			for(InterfaceIdentifier overId:overrides){
				if(overId.getName().equals(idExpr.name)){
					idExpr.id = overId;
					found = true;
					break;
				}
			}
			if(!found)
				undefinedVariables.add(new UndefinedVariable(idExpr.name));
		}
		else if(!declarations.contains(idExpr.id.getName()))
			undefinedVariables.add(new UndefinedVariable(idExpr.id.getName()));
	}

	public void doVisit(Expr e) {
		undefinedVariables.clear();
		e.accept(this);
	}
	
	public List<UndefinedVariable> getUndefinedVariables(){
		return undefinedVariables;
	}
}