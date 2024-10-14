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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import fr.inria.plasmalab.altl.ast.Adaptive.Modality;
import fr.inria.plasmalab.altl.printer.APrettyPrinter;
import fr.inria.plasmalab.altl.prismparser.PRISMReachabilityChecker;
import fr.inria.plasmalab.bltl.ast.nodes.Expr2;
import fr.inria.plasmalab.bltl.checker.BLTLChecker;
import fr.inria.plasmalab.bltl.exceptions.UndefinedVariable;
import fr.inria.plasmalab.rmlbis.ast.PlasmaSystem;
import fr.inria.plasmalab.rmlbis.simulator.RMLAdaptiveModel;
import fr.inria.plasmalab.rmlbis.simulator.RMLState;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class ALTLChecker extends BLTLChecker implements AVisitor {

	protected boolean isFalse = false;
	protected List<Integer> positions;
	protected PlasmaSystem system = null;


	public ALTLChecker(AbstractModel model) {
		super(model);
		positions = new ArrayList<Integer>(10);
	}

	@Override
	public void visit(Adaptive expr) {
		if (expr.bound != null) {
			//Remember time of arrival
			int entryStepNb = currentStepNb;
			
			//Time of departure => when localBound is reached, expr is decided.
			//Compute upperBound
			//Store result value
			double cResult = result;
			//Visit bound Expr
			expr.getBound().accept(this);
			int localBound = (int) (result+currentStepNb);
			//Restore result value
			result = cResult;
	
			//double shortcut = (expr.op == UnOp.Future) ? 1 : 0;
			// Semantics of expr: expr.assumption /\ expr.trigger ==> expr.goal. 
			// we first check if assumption is satisfied
			expr.assumption.accept(this);
			if (model.getTrace().size() > localBound || getResult() == 0 ) { // bound is exceeded or assumption is false
				setResult(expr.modality == Modality.May ? 1 : 0);	//	true if May; false if Must
				return ;
			}
			else if (getResult() == 2)	// bound is not exceeded but simulation stopped before assumption is undecided
				return;					// result is undecided
			
			//Thus, we look for an eventual change of system after the time the assumption was satisfied
			if (offSetMap.containsKey(expr.assumption))
				currentStepNb = offSetMap.get(expr.assumption);
			else
				currentStepNb = 0;
			
			try{
				while (currentStepNb <= localBound) {
					// Progress stepNb
					currentStepNb++;
					// If new stepNb outbounds the untilStep break from while
					if (untilStep >= 0 && currentStepNb > untilStep)
						break;
					
					// generate one more state if necessary
					while (model.getTrace().size() <= currentStepNb)
						callSimulate();
					
					// check the trigger on previous state
					expr.pre_trigger.accept(this);
					
					// get next state
					currentState = model.getTrace().get(currentStepNb);
					if (((RMLState) currentState).getSystem() == system)
						continue; // No change, we continue again: skip end of the loop
					//otherwise: is the pre and post triggers satisfied by the change ?
					//if pre was not, we continue !
					if (getResult() != 1)
						continue;
					// check post
					expr.post_trigger.accept(this);
					//if not, we continue !
					if (getResult() != 1)
						continue;
					expr.guarantee.accept(this);
					return;
				}
			}
			catch (PlasmaSimulatorException e) {
				throw new PlasmaRuntimeException(e);
			}
			// the loop stopped before the trigger was satisfied
			// the expected value at end of the exploration is stored in expected
			setResult(expr.modality == Modality.May ? 1 : 0);

			//Case to exit
			// Undecided if untilStep positive and
			// If var currentStepNb didn't exceeded localbound
			if (untilStep >= 0 && currentStepNb <= localBound)
				setResult(Double.NaN);					// Push undecided
	
			// Remember farthest exploration
			// offSetMap.put(expr, currentStepNb);
			//Resume currentStepNb
			currentStepNb = entryStepNb;
		}
		else {
			visitUnbounded(expr);
		}
	}
	
	private void visitUnbounded(Adaptive expr) {
		//Remember time of arrival
		int entryStepNb = currentStepNb;
		
		// check the trigger reachability
		PRISMReachabilityChecker checker = new PRISMReachabilityChecker((RMLState)currentState);
		try {
			checker.check(APrettyPrinter.of(expr.pre_trigger).replace(".","_") + " & " + ((RMLAdaptiveModel)model).getModel().getAdaptiveSystem().getPrismAdaptiveGuards());
		} catch (PlasmaSyntaxException e) {
			throw new PlasmaRuntimeException(e);
		} catch (PlasmaCheckerException e) {
			throw new PlasmaRuntimeException(e);
		}
		
		// Semantics of expr: expr.assumption /\ expr.trigger ==> expr.goal. 
		// we first check if assumption is satisfied
		expr.assumption.accept(this);
		if (getResult() == 0 ) { // assumption is false
			setResult(expr.modality == Modality.May ? 1 : 0);	//	true if May; false if Must
			return ;
		}
		else if (getResult() == 2)	// assumption is undecided
			return;					// result is undecided
		
		//Thus, we look for an eventual change of system after the time the assumption was satisfied
		if (offSetMap.containsKey(expr.assumption))
			currentStepNb = offSetMap.get(expr.assumption);
		else
			currentStepNb = 0;

		try {
			while (true) {		// yes
				// Progress stepNb
				currentStepNb++;
				// If new stepNb outbounds the untilStep break from while
				if (untilStep >= 0 && currentStepNb > untilStep) {
					setResult(Double.NaN);
					return;
				}
				
				// generate one more state if necessary
				while (model.getTrace().size() <= currentStepNb)
					callSimulate();
				
				// check the trigger on previous state
				expr.pre_trigger.accept(this);
				
				currentState = model.getTrace().get(currentStepNb);
				if (((RMLState) currentState).getSystem() == system) {
					if (!checker.isWinning((RMLState)currentState)) {
						break;
					}
					else
						continue; // No change, we continue again: skip end of the loop
				}
				
				if (getResult() != 1)
					break;
				//otherwise: is the trigger satisfied by the change ?
				expr.post_trigger.accept(this); // could be replace by a check of the prism results. Which is faster ?
				//if not, we continue !
				if (getResult() != 1)
					break;
				// if yes we check the guarantee
				expr.guarantee.accept(this);
				return;
			}
		} catch (PlasmaSimulatorException e) {
			throw new PlasmaRuntimeException(e);
		}
		
		// the loop stopped before the trigger was satisfied
		// the expected value at end of the exploration is stored in expected
		setResult(expr.modality == Modality.May ? 1 : 0);
				 
		
		// Remember farthest exploration
		// offSetMap.put(expr, currentStepNb);
		//Resume currentStepNb
		currentStepNb = entryStepNb;
		
	}

	
	protected Double lookForValue(String id) {

		ListIterator<Integer> li = positions.listIterator(positions.size());

		while (li.hasPrevious()) {
			int pos = li.previous();
			if (pos <= currentStepNb) {
				try {
					return model.getTrace().get(pos).getValueOf(id);
				} catch (PlasmaSimulatorException e) { }
			}
		}
		return Double.NaN;
	}

	@Override
	public void visit(IdentExpr expr) {
		Double result;
		try {
			result = currentState.getValueOf(expr.id);
		} catch (PlasmaSimulatorException e) {
			result = lookForValue(expr.name);
		}	
		
		if (!Double.isNaN(result))
			setResult(result);
		else if (expr.prime) {
			isFalse = true;
			setResult(0.);
		} else 
			throw new PlasmaRuntimeException(new UndefinedVariable(expr.id));
	}

	protected void setRelResult(double value) { 
		if (value != 2)
			super.setResult(isFalse ? 0 : value);
		else
			super.setResult(Double.NaN);
		isFalse = false;
	}
	protected void setResult(double value) {
		isFalse = false;
		super.setResult(value);
	}


	protected void setArithResult(double value) { 
		super.setResult(value);
	}


	@SuppressWarnings("incomplete-switch")
	@Override
	public void visit(Expr2 expr) {
		double left, right;

		switch (expr.op()) {
		case And:
			expr.left().accept(this);
			if (getResult() == 1) //True && Right = Right
				expr.right().accept(this);
			else if (getResult() == 2) { //Undecided && Right => false if Right = false else undecided
				expr.right().accept(this);
				if (getResult() != 0)
					setResult(Double.NaN);
			} // else false && ? = false
			return ;
		case Or:
			expr.left().accept(this);
			if (getResult() == 0) // false || Right = Right{
				expr.right().accept(this);
			else if (getResult() == 2) { // undecided || Right => true if Right = true else undecided
				expr.right().accept(this);
				//undecided
				if (getResult() != 1)
					setResult(Double.NaN);
			}
			return ;
		case Imp:
			expr.left().accept(this);
			if (getResult() == 1)		//True => Right = Right
				expr.right().accept(this);
			else if (getResult() == 0)	//False => ? = True
				setResult(1.);
			// else	undecided => ? = undecided
			return ;
		case Eq:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setRelResult(left == right ? 1. : 0.);
			return ;
		case Neq:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setRelResult(left != right ? 1. : 0.);
			return ;
		case Lt:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setRelResult(left < right ? 1. : 0.);
			return ;
		case Le:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setRelResult(left <= right ? 1. : 0.);
			return ;
		case Gt:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setRelResult(left > right ? 1. : 0.);
			return ;
		case Ge:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setRelResult(left >= right ? 1. : 0.);
			return ;
		case Add:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setArithResult(left + right);
			return ;
		case Min:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setArithResult(left - right);
			return ;
		case Mul:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setArithResult(left * right);
			return ;
		case Div:
			expr.right().accept(this); right = getResult();
			if (right == 0){
				throw new PlasmaRuntimeException("line "+expr.line+":"+expr.start+" - Divide by 0");
			}
			expr.left().accept(this);	left = getResult();
			setArithResult(left / right);
			return ;
		}
	}

	protected void callSimulate() throws PlasmaSimulatorException {
		model.simulate();
		if (((RMLState) currentState).getSystem() != system)
			positions.add(currentStepNb - 1);

		system = ((RMLState) currentState).getSystem();

	}

	public void reinit(int untilStep, InterfaceState path) {
		currentStepNb = 0;
		offsetStepNb = 0;
		offSetMap.clear();
		this.untilStep = untilStep;
		this.currentState = path;
		this.system = ((RMLState)currentState).getSystem();
	}


}
