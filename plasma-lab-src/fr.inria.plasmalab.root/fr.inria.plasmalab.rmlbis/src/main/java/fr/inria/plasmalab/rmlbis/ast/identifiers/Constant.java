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
import fr.inria.plasmalab.rmlbis.ast.factory.ExprFactory;
import fr.inria.plasmalab.rmlbis.ast.visitors.IdentVisitor;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;


public class Constant extends Identifier {
	
	public Expr value;
	
	/** Constructor with value expression. */
	public Constant(Ref ref, Type type, Expr value) {
		super(ref, type);
		this.value = value;
//		this.ref.value = this.value.value;
	}

	/** Constructor without initial value. A default expression is created. */
	public Constant(Ref ref, Type type, ExprFactory factory) {
		super(ref, type);
		switch (type) {
		case Boolean:
			this.value = factory.makeValue(false); break;
		case Integer:
			this.value = factory.makeValue(0); break;
		case Double:
			this.value = factory.makeValue(0.0); break;
		case Clock:
			assert false:
				"Constants can neither be a Ranged Integer nor a Clock";
		}
//		this.ref.value = this.value.value;
	}
	
	Constant(Constant c) {
		super(c.ref, c.type);
		this.value = c.value;
//		this.ref.value = this.value.value;
	}
	
	public String toString() {
		return ref.name + " = " + value.toString();
	}
	
	@Override
	public boolean isBoolean() {
		return value.type == Type.Boolean;
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
