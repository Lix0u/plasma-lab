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
package fr.inria.plasmalab.rmlbis.ast.identifiers;

import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.visitors.IdentVisitor;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

public class Formula extends Identifier {
	
	public Expr body;
	/** The type of a formula can not be set at its construction because the type is not declared and not
	 * known a priori. It is not compatible with the attribute Identifier.type which is final. Here I masked it with 
	 * the attribute Formula.type that can not be changed. 
	 */
	public Type type;
	
	public Formula(Ref ref, Expr body) {
		super(ref, null);
		this.body = body;
	}
	
	public String toString() {
		return ref.name + " = " + body.toString();
	}

	@Override
	public boolean isBoolean() {
		return body.type.subTypeOf(Type.Boolean);
	}

	@Override
	public void accept(IdentVisitor v) {
		v.visit(this);
	}
	
	@Override
	public int compareTo(InterfaceIdentifier o) {
		//Time is always the first Id
		if(o instanceof TimeIdentifier)
			return +1;
		return this.getName().compareTo(o.getName());
	}
	
}
