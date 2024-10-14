/**
 * This file is part of fr.inria.plasmalab.nested.
 *
 * fr.inria.plasmalab.nested is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.nested is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.nested.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.nested.ast;

import fr.inria.plasmalab.nested.ast.visitors.ManualVisitor;
import fr.inria.plasmalab.nested.ast.visitors.Visitor;

public abstract class Expr {

	public enum Type { Int, Bool, Float }
	
	public int line;		/** negative values means irrelevant content */
	public int start;
	public int end;
	
	
	/** nesting indicates the level of nested probabilistic quantifications in Expr 
	 * Expr.nesting == 0 means that no nested proba.
	 * */
	public int nesting = 0; 
	public double init_alpha = 0;
	public double init_beta = 0;
	
	public abstract void accept(Visitor v);
	
	/**
	 * Accept method for the {@link ManualVisitor}. A ManualVisitor will call accept methods for 
	 * sons of this node by itself. 
	 * @param v the ManualVisitor.
	 */
	public abstract void accept(ManualVisitor v);
}
