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

import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.location.Location;
import fr.inria.plasmalab.rmlbis.ast.visitors.IdentVisitor;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

public class Identifier extends Location implements InterfaceIdentifier {
	
	public final Ref ref;
	public final Type type;
	//public int pos;
	
	public Identifier(Ref ref, Type type) {
		this.ref = ref;
		this.type = type;
	}
	
	public Identifier(Identifier id) {
		this.ref = id.ref;
		this.type = id.type;
	}
	
	@Override
	public String getName() {
		return ref.name;
	}
	
	public void updateRef(double v) {
		ref.value = v;
	}
	
	public void accept(IdentVisitor v) {
		v.visit(this);
	}
	
	public String toString() {
//		Printer printer = new Printer();
//		this.accept(printer);
//		return printer.getValue();
		return ref.name;
	}
	
	@Override
	public int compareTo(InterfaceIdentifier o) {
		//Time is always the first Id
		if(o instanceof TimeIdentifier)
			return +1;
		return this.getName().compareTo(o.getName());
	}
	
	@Override
	public boolean isBoolean() {
		return this.type == Type.Boolean;
	}
}
