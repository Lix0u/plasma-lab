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
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;

public class Label implements InterfaceIdentifier {
	
	/** Name starts and ends with quotes */
	private String name;
	public Expr body;
	
	public Label(String name, Expr body) {
		this.name = name;
		this.body = body;
	}
	
	@Override
	public boolean isBoolean() {
		return body.type == Type.Boolean;
	}
	
	public String toString() {
		return name + " = " + body.toString();
	}
	
	public String toPrism(String sysPrefix) {
		String name = this.getName().substring(1, this.getName().length()-1);
		return "label " + sysPrefix + name + " = " + body.toPrism(sysPrefix); 
	}

	@Override
	public int compareTo(InterfaceIdentifier o) {
		//Time is always the first Id
		if(o instanceof TimeIdentifier)
			return +1;
		return this.getName().compareTo(o.getName());
	}

	@Override
	public String getName() {
		return name;
	}
}
