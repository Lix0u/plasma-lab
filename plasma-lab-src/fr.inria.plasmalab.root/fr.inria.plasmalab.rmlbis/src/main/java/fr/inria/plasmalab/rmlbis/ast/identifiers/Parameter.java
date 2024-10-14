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

public class Parameter extends Variable {
	
	public boolean constant;

	
	public Parameter(Ref ref, Expr lower, Expr upper, boolean constant) {
		super(ref, lower, upper);
		this.constant = constant; 
	}

	public Parameter(Ref ref, Expr lower, Expr upper, Expr initValue, boolean constant) {
		super(ref, lower, upper, initValue);
		this.constant = constant;
	}

	public Parameter(Ref ref, Type type, boolean constant, ExprFactory factory) {
		super(ref, type, factory);
		this.constant = constant;
	}

	public Parameter(Ref ref, Type type, Expr initValue, boolean constant) {
		super(ref, type, initValue);
		this.constant = constant;
	}

	public Parameter(Ref ref, Parameter toClone) {
		super(ref, toClone);
		this.constant = toClone.constant;
	}

	@Override
	public void accept(IdentVisitor v) {
		v.visit(this);		
	}
	
	public String toPrism(String sysPrefix, Expr value) {
		String name = sysPrefix + getName().replace(".","_");
		
		if (constant) {
			return type.toString() + " " + name + " = " + value.toPrism(sysPrefix);
		}
		else {
			if (lowerExpr != null && upperExpr !=null) {
				return name + " : [" + lowerExpr.toPrism(sysPrefix) + ".." + upperExpr.toPrism(sysPrefix) + "] init " + value.toPrism(sysPrefix);			
			}
			else if (type == Type.Boolean) {
				return name + " : bool init " + value.toPrism(sysPrefix);
			}
			else {
				throw new RuntimeException("Uncompatible with PRISM: " + type + " is not a valid type for parameters.");
			}
		}
	}
	
	


}
	