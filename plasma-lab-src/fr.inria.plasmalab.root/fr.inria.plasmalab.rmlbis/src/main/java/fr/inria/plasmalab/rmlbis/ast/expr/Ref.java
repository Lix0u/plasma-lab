/**
 * This file is part of fr.inria.plasmalab.rmlbis.
 *
 * fr.inria.plasmalab.rmlbis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.rmlbis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.rmlbis.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.rmlbis.ast.expr;

import fr.inria.plasmalab.rmlbis.ast.visitors.expr.DefaultVisitor;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ManualVisitor;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ReversedVisitor;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

public class Ref extends Expr {

	public String name;
	public InterfaceIdentifier target = null; 
	
	public Ref(String name) {
		this.name = name;
	}
	
	/** When translated to prism, if the reference name contains a dot, it means that it belongs to a module instantiated in the system.
	 *  In that case the dot is replaced by an underscore and the ref name is then prefixed by the system's name.
	 *  Otherwise the ref is global and the name is not changed.  
	 */
	public String getPrismName(String sysPrefix) {
		if (name.contains("."))
			return sysPrefix + name.replace(".", "_");
		else
			return name;
	}
	
	@Override
	public void eval() { }
	
	@Override
	public void accept(DefaultVisitor v) {
		v.visit(this);
	}

	@Override
	public void accept(ManualVisitor v) {
		v.visit(this);
	}

	@Override
	public void accept(ReversedVisitor v) {
		v.visit(this);
		
		for(Expr parent: parents) 
			parent.accept(v);
	}
	
	public String toString() {
		return name;
	}
}
