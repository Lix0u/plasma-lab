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

import java.util.HashSet;
import java.util.Set;

import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.location.Location;
import fr.inria.plasmalab.rmlbis.ast.processing.PrismPrinter;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.DefaultVisitor;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ManualVisitor;
import fr.inria.plasmalab.rmlbis.ast.visitors.expr.ReversedVisitor;

public abstract class Expr extends Location {
	
	public Set<Expr> parents = new HashSet<Expr>();
	public Type type = null;
	public double value = Double.NaN;
	public String str;
	
	public abstract void eval(); 

 	public abstract void accept(DefaultVisitor v);
	
 	public abstract void accept(ManualVisitor v);
	
 	public abstract void accept(ReversedVisitor v);
 	
	public String toString() {
		return this.str;
	}

	public String toPrism(String sysPrefix){
		PrismPrinter printer = new PrismPrinter(sysPrefix);
		this.accept(printer);
		return printer.getValue();
	}
}
