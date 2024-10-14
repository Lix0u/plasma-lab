/**
 * This file is part of fr.inria.plasmalab.bltl.
 *
 * fr.inria.plasmalab.bltl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bltl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bltl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.bltl.ast;

import fr.inria.plasmalab.bltl.ast.visitors.ManualVisitor;
import fr.inria.plasmalab.bltl.ast.visitors.Visitor;

public abstract class Expr {

	public enum Type { Int, Bool, Float }
	
	public int line;		/** negative values means irrelevant content */
	public int start;
	public int end;
	
	public abstract void accept(Visitor v);
	
	/**
	 * Accept method for the {@link ManualVisitor}. A ManualVisitor will call accept methods for 
	 * sons of this node by itself. 
	 * @param v the ManualVisitor.
	 */
	public abstract void accept(ManualVisitor v);
}
