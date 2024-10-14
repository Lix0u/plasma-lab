/**
 * This file is part of fr.inria.plasmalab.altl.
 *
 * fr.inria.plasmalab.altl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.altl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.altl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.altl.ast;

import fr.inria.plasmalab.bltl.ast.visitors.ManualVisitor;
import fr.inria.plasmalab.bltl.ast.visitors.Visitor;

public class IdentExpr extends fr.inria.plasmalab.bltl.ast.leaves.IdentExpr {
	public boolean prime = false;
	
	
	public IdentExpr(String id) {
		super(null, id);
	}

	public void accept(AVisitor av) {
		av.visit(this);
	}

	public void accept(Visitor av) {
		((AVisitor) av).visit(this);
	}

	public void accept(ManualVisitor av) {
		((AVisitor) av).visit(this);
	}

}
