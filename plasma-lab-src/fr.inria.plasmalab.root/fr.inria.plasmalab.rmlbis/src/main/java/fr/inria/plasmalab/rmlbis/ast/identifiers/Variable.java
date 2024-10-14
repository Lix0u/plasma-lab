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

import java.util.Random;

import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.factory.ExprFactory;
import fr.inria.plasmalab.rmlbis.ast.processing.Renaming;
import fr.inria.plasmalab.rmlbis.ast.visitors.IdentVisitor;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class Variable extends  Identifier {

	/** Expression of the initial value. Should never be null. */
	public Expr initValue;
	/** Expression of a minimum value. Only used for ranged integer variables. Otherwise it is null. */
	public Expr lowerExpr;
	/** Expression of a maximum value. Only used for ranged integer variables. Otherwise it is null. */
	public Expr upperExpr;

	public Variable(Ref ref, Expr lower, Expr upper) {
		super(ref, Type.Integer);
		this.initValue = lower;
		this.lowerExpr = lower;
		this.upperExpr = upper;
	}

	public Variable(Ref ref, Expr lower, Expr upper, Expr initValue) {
		super(ref, Type.Integer);

		this.initValue = initValue;
		this.lowerExpr = lower;
		this.upperExpr = upper;
	}

	public Variable(Ref ref, Type type, ExprFactory factory) {
		super(ref, type);
		switch (type) {
		case Boolean:
			this.initValue = factory.makeValue(false);
			break;
		case Double:
			this.initValue = factory.makeValue(0.0);
			break;
		case Integer:
			this.initValue = factory.makeValue(0);
			break;
		case Clock:
			this.initValue = factory.makeValue(0.0);
			break;
		default:
			throw new Error("There is no case for Type " + type);
		}
		this.lowerExpr = null;
		this.upperExpr = null;
	}

	public Variable(Ref ref, Type type, Expr initValue) {
		super(ref, type); 
		this.initValue = initValue;
		this.lowerExpr = null;
		this.upperExpr = null;
	}

	public Variable(Ref ref, Variable toClone) {
		super(ref, toClone.type);
		
		this.initValue = toClone.initValue;
		this.lowerExpr = toClone.lowerExpr;
		this.upperExpr = toClone.upperExpr;
	}
	
	@Override
	public void updateRef(double v) {
		if (lowerExpr != null && upperExpr != null 
				&& (v < lowerExpr.value | v > upperExpr.value)) {
			throw new PlasmaRuntimeException("Value " + v + " is outside range of variable " + ref.name);
		}
		ref.value = v;	
	}
	
//	public Variable(Parameter param) {
//		super(param.ref, param.type);
//		
//		this.scope = param.scope;
//		this.lowerExpr = param.lowerExpr;
//		this.upperExpr = param.upperExpr;
//	}

	public String toString() {
//		if (type == Type.RangedInt) {
//			return ref.name + " between [" + lowerExpr.toString() + "," + upperExpr.toString() + "] starts at " + initValue.toString();			
//		}
//		else {
//			return type + " " + ref.name + " starts at " + initValue.toString();
//		}
		return ref.name;
	}
	
	public String toPrism(String sysPrefix) throws PlasmaSyntaxException {
		String name = sysPrefix + getName().replace(".","_");
		
		if (lowerExpr != null && upperExpr !=null) {
			return name + " : [" + lowerExpr.toPrism(sysPrefix) + ".." + upperExpr.toPrism(sysPrefix) + "] init " + initValue.toPrism(sysPrefix);			
		}
		else if (type == Type.Boolean) {
			return name + " : bool init " + initValue.toPrism(sysPrefix);
		}
		else {
			throw new PlasmaSyntaxException("Uncompatible with PRISM: " + type + " is not a valid type for variables.");
		}
	}

	@Override
	public void accept(IdentVisitor v) {
		v.visit(this);		
	}

	public void rename(Renaming ren) {
		initValue = ren.apply(initValue);
		if (lowerExpr != null && upperExpr !=null) {
			lowerExpr = ren.apply(lowerExpr); 
			upperExpr = ren.apply(upperExpr);
		}
	}
	
	public double pickRandomInitValue(Random rng) {
		double val = 0.0;
		switch (type) {
		case Boolean:
			val = rng.nextBoolean() ? 1.0 : 0.0;
			break;
		case Integer:
			if (this.upperExpr != null && this.lowerExpr != null)
				val = rng.nextInt((int)(this.upperExpr.value - this.lowerExpr.value + 1)) + this.lowerExpr.value;
			break;
		default:	// double and clock
			val = initValue.value;
			break;
		}
		return val;
	}
}