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
package fr.inria.plasmalab.rmlbis.ast.expr.operators;

import fr.inria.plasmalab.rmlbis.ast.expr.Expr;


public enum BuiltIn {
	Min(2, null), Max(2, null), Floor(1, null), Ceil(1, null),
	Pow(2, 2), Mod(2, 2), Log(2, 2);

	private final Integer minArity;
	private final Integer maxArity; //null means +oo

	private BuiltIn(Integer minArity, Integer maxArity) {
		this.minArity = minArity;
		this.maxArity = maxArity;
	}

	
	private double min(double[] args) {
		double min = args[0];

		for (double cur : args)  
			min = min <= cur ? min : cur;
		return min;
	}

	private double max(double[] args) {
		double max = args[0];

		for (double cur : args)  
			max = max >= cur ? max : cur;
			return max;
	}

	public double eval(double args[]) {

		switch (this) {
		case Ceil: 
			return Math.ceil(args[0]);
		case Floor:
			return Math.floor(args[0]);
		case Log:
			return Math.log(args[0]);
		case Max:
			return max(args);
		case Min:
			return min(args);
		case Mod:
			return args[0] % args[1];
		case Pow:
			return Math.pow(args[0], args[1]);
		default:
			return Double.NaN;
		}
	}


	
	private double min(Expr[] args) {
		double min = args[0].value;

		for (Expr cur : args)  
			min = min <= cur.value ? min : cur.value;
		return min;
	}

	private double max(Expr[] args) {
		double max = args[0].value;

		for (Expr cur : args)  
			max = max >= cur.value ? max : cur.value;
			return max;
	}

	public double eval(Expr[] args) {
		double x = args[0].value;
		
		switch (this) {
		case Ceil: 
			return Math.ceil(x);
		case Floor:
			return Math.floor(x);
		case Log:
			return Math.log(x);
		case Max:
			return max(args);
		case Min:
			return min(args);
		case Mod: {
			double y = args[1].value;
			return x % y;
		}
		case Pow: {
			double y = args[1].value;
			return Math.pow(x, y);
		}
		default:
			return Double.NaN;
		}
	}

	public boolean checkArity(int args) {
		// args \in [minArity..maxArity] or args \in [minArity..+oo)
		return (minArity <= args && (maxArity == null || args <= maxArity));
	}
}