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

import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.bltl.ast.visitors.ManualVisitor;
import fr.inria.plasmalab.bltl.ast.visitors.Visitor;
import fr.inria.plasmalab.bltl.parser.BoundExpr;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;


public class Adaptive extends Expr {

	public static enum Modality {
		May, Must;
	}
	
	
	public Expr assumption;
	public Expr pre_trigger;
	public Expr post_trigger;
	public Expr guarantee;
	public Expr bound;
	public Modality modality; 
	private boolean isTimeBounded;
	
	public Adaptive(Expr assumption, Expr pre, Expr post,  BoundExpr bound, Expr guarantee, Modality modality) {
		this.assumption = assumption;
		this.pre_trigger = pre;
		this.post_trigger = post;
		if(bound!=null){
			this.bound = bound.expr;
			this.isTimeBounded = bound.isTime;
		}
		else{
			this.bound = null;
			this.isTimeBounded = false;
		}
		this.guarantee = guarantee;
		this.modality = modality;
				
		if (bound != null && isTimeBounded)
			throw new PlasmaRuntimeException("Time bound is not supported for now...");
	}
	
	
	public void accept(AVisitor av) {
		av.visit(this);
	}

	@Override
	public void accept(Visitor v) {
		((AVisitor) v).visit(this);
	}

	@Override
	public void accept(ManualVisitor v) {
		((AVisitor) v).visit(this);
	}
	
	public Expr getBound() {
		return bound;
	}

}
