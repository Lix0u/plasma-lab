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
package fr.inria.plasmalab.bltl.checker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.bltl.ast.Expr;
import fr.inria.plasmalab.bltl.ast.leaves.BoolExpr;
import fr.inria.plasmalab.bltl.ast.leaves.DeadlockExpr;
import fr.inria.plasmalab.bltl.ast.leaves.FloatExpr;
import fr.inria.plasmalab.bltl.ast.leaves.IdentExpr;
import fr.inria.plasmalab.bltl.ast.leaves.IntExpr;
import fr.inria.plasmalab.bltl.ast.nodes.Expr1;
import fr.inria.plasmalab.bltl.ast.nodes.Expr2;
import fr.inria.plasmalab.bltl.ast.nodes.RewardExpr;
import fr.inria.plasmalab.bltl.ast.nodes.StepBoundExpr1;
import fr.inria.plasmalab.bltl.ast.nodes.StepBoundExpr2;
import fr.inria.plasmalab.bltl.ast.nodes.TimeBoundExpr1;
import fr.inria.plasmalab.bltl.ast.nodes.TimeBoundExpr2;
import fr.inria.plasmalab.bltl.ast.operators.BinOp;
import fr.inria.plasmalab.bltl.ast.operators.UnOp;
import fr.inria.plasmalab.bltl.ast.visitors.ManualVisitor;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDeadlockException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public class BLTLChecker implements ManualVisitor {

	protected Double result = 0.;
	protected AbstractModel model;

	protected InterfaceState currentState;
	//CurrentStepNb is the state number that we are exploring counting from 0
	// Nb it is also equals to the number of transition (step) taken
	protected int currentStepNb;
	//CurrentTime is the time of the state we are exploring
	protected double currentTime;
	//Offset to reduce the interval to explore (lower bound)
	protected Integer offsetStepNb;
	//untilstep is used in the simulation mode only
	//In the simulation mode, the checker can't ask the simulator for new state and is limited to a given trace
	//The maximum number of state that are used is untilStep. If a requirement need more state than untilStep 
	// to decide, it's return value is set to undecided. 
	protected int untilStep;
	//Offset map which map an Expr to its last exploring state. Semantics of an Offset might differ due to the type of Expr.
	protected Map<Expr, Integer> offSetMap;
	
	final static Logger logger = LoggerFactory.getLogger(BLTLChecker.class);

	protected Double getResult() {
		return result;
	}

	protected void setResult(double value) {
		result = value;
	}


	public BLTLChecker(AbstractModel model){
		offSetMap = new HashMap<Expr, Integer>();
		this.model = model;
	}	

	@Override
	public void visit(TimeBoundExpr1 expr) {

		int entryStepNb = currentStepNb;								// Remember time of arrival
		double entryTime = currentTime;
		if (model.hasTime())
			try {
				currentTime = currentState.getValueOf(model.getTimeId());
			} catch (PlasmaSimulatorException e) {
				throw new PlasmaRuntimeException(e);
			}
		else
			currentTime = currentStepNb;

		//Compute upperBound
		//Store result value
		Double cResult = result;
		//Visit bound Expr
		//result will be equal to the explored timebound
		expr.getTimebound().accept(this);
		double localBound = result+currentTime;
		//Restore result value
		result = cResult;

		//Is the expr we are about to check already partially checked until offset?
		offsetStepNb = offSetMap.get(expr);
		if (offsetStepNb!=null && currentStepNb < offsetStepNb)
			currentStepNb = offsetStepNb;
		
		try{
			// LTL loop
			// The loop may be interrupted when the child is evaluated to 1 or 0 depending if we are 
			// evaluating a globally or a fatally expression. The corresponding value that is used to interrupt
			// this loop is shortcut
			double shortcut = (expr.op() == UnOp.Future) ? 0 : 1;
			ltl: {
				while (currentTime <= localBound) {			
					// Should we generate a new state?
					while (model.getTrace().size() <= currentStepNb)
						callSimulate();

					// Get the last state
					currentState = model.getStateAtPos(currentStepNb);
					// Is time > timebound
					// Progress time
					if (model.hasTime())
						currentTime = currentState.getValueOf(model.getTimeId());
					else
						currentTime = currentStepNb;			

					//Next
					if (expr.op() == UnOp.Next) {
						//Advance
						while(currentTime < localBound){
							currentStepNb ++;
							// If new stepNb outbound the untilStep break from while
							if (currentStepNb > untilStep && untilStep >= 0) {
								// Push undecided
								setResult(Double.NaN);
								// stack.push(Double.NaN);
								break ltl;
							}
							// Should we generate a new state?
							while (model.getTrace().size() <= currentStepNb)
								callSimulate();
							currentState = model.getStateAtPos(currentStepNb);
							if (model.hasTime())
								currentTime = currentState.getValueOf(model.getTimeId());
							else
								currentTime = currentStepNb;
						}
					} else if (currentTime > localBound)
						// Break from fatally / globally
						break;

					// Is the property satisfied?
					expr.child().accept(this);
					
					// Next
					if (expr.op() == UnOp.Next) {
						break ltl;
					}

					// Fatally or Globally
					// child has the shortcut value, time to leave: [OP<= bound child] is shortcut value
					if (getResult() != shortcut)
						break ltl;
					
					// Else continue
					// Progress stepNb
					// currentStepNb = map.sousExpr.time + 1
					currentStepNb++;
					// If new stepNb outbound the untilStep break from while
					if (currentStepNb > untilStep && untilStep >= 0)
						break;
				}
				//Case to exit
				// Undecided if untilStep positive and
				// If var currentStepNb didn't exceeded localbound
				if (untilStep >= 0 && currentTime <= localBound) {
					// Push undecided
					setResult(Double.NaN);
				}
				// Other cases, there is nothing to do
				// F true, true is already set, F end of bound, false is already set
				// G false, false is already set, G end of bound, true is already set
				// X, result is already set
			}
		} catch(PlasmaDeadlockException e){
			// Get the right state (last one available, because deadlock)
			currentState = model.getStateAtPos(model.getTrace().size() - 1);

			logger.debug("Deadlock catched in "+expr.op().toString()+" "+expr.toString()+".\n"
					+ "Visit child "+expr.child().toString()+" with offset "+offsetStepNb);
			// Is the property satisfied?
			// Push the value of the child
			expr.child().accept(this);
			
			
		} catch (PlasmaSimulatorException e) {
			throw new PlasmaRuntimeException(e);
		}
		//Remember farthest exploration
		offSetMap.put(expr, currentStepNb);
		//Resume currentStepNb
		currentStepNb = entryStepNb;
		currentState = model.getStateAtPos(currentStepNb);
		//Resume time
		currentTime = entryTime;
	}


	@Override
	public void visit(TimeBoundExpr2 expr) {
		// Remember time of arrival
		int entryStepNb = currentStepNb;
		double entryTime = currentTime;
		// Progress time
		if (model.hasTime())
			try {
				currentTime = currentState.getValueOf(model.getTimeId());
			} catch (PlasmaSimulatorException e) {
				throw new PlasmaRuntimeException(e);
			}
		else
			currentTime = currentStepNb;
		
		//Compute upperBound
		//Store result value
		Double cResult = result;
		//Visit bound Expr
		//result will be equal to the explored timebound
		expr.getTimebound().accept(this);
		double localBound = result+currentTime;
		//Restore result value
		result = cResult;

		//Is the expr we are about to check already partially checked until offset?
		offsetStepNb = offSetMap.get(expr);
		if (offsetStepNb!=null && currentStepNb < offsetStepNb)
			currentStepNb = offsetStepNb;
		
		try {
			until: {
				while (currentTime <= localBound) {
					// If new stepNb outbound the untilStep break from while
					if (currentStepNb > untilStep && untilStep >= 0) {
						// Push undecided
						setResult(Double.NaN);
						// stack.push(Double.NaN);
						break until;
					}
					// Should we generate a new state?
					while (model.getTrace().size() <= currentStepNb)
						callSimulate();
					// Get the right state
					currentState = model.getStateAtPos(currentStepNb);
					// Progress time
					if (model.hasTime())
						currentTime = currentState.getValueOf(model.getTimeId());
					else
						currentTime = currentStepNb;
					// Is time > timebound
					if (currentTime > localBound)
						break;

					// IF RIGHT push true

					// Has the child been partially computed?
					if (offSetMap.containsKey(expr.right()))
						// If yes set offset
						offsetStepNb = offSetMap.get(expr.right());
					else
						// Else set offset to currentState
						offsetStepNb = currentStepNb;
					expr.right().accept(this);

					if (getResult() != 0)
						break until;

					// ELSE IF LEFT continue

					expr.left().accept(this);
					if (getResult() != 1)
						break until;

					// Progress stepNb
					currentStepNb++;
					// If new stepNb outbound the untilStep break from while
					if (currentStepNb > untilStep && untilStep >= 0)
						break;
				}

				// Case to exit
				// Undecided if untilStep positive and
				// If var currentStepNb didn't exceeded localbound
				if (untilStep >= 0 && currentTime <= localBound)
					setResult(Double.NaN);
				else if (expr.op() == BinOp.Until)
					setResult(0.);
				else if (expr.op() == BinOp.WeakUntil)
					setResult(1.);
			}
		} catch (PlasmaDeadlockException e) {
			logger.debug("Deadlock catched in " + expr.toString() + " set result to false.");
			setResult(0.);
		} catch (PlasmaSimulatorException e) {
			throw new PlasmaRuntimeException(e);
		}
		// Remember farthest exploration
		offSetMap.put(expr, currentStepNb);
		// Resume currentStepNb
		currentStepNb = entryStepNb;
		currentState = model.getStateAtPos(currentStepNb);
		//Resume time
		currentTime = entryTime;
	}


	@Override
	public void visit(StepBoundExpr1 expr) {

		//Remember time of arrival
		int entryStepNb = currentStepNb;
		//Compute upperBound
		//Store result value
		Double cResult = result;
		//Visit bound Expr
		expr.getStepbound().accept(this);
		int localBound = (int) (result+currentStepNb);
		//Restore result value
		result = cResult;

		//Is the expr we are about to check already partially checked until offset?
		offsetStepNb = offSetMap.get(expr);
		if (offsetStepNb!=null && currentStepNb < offsetStepNb)
			currentStepNb = offsetStepNb;
		
		try{
			// LTL loop
			// The loop my be interrupted for when the child is evaluated to 1 or 0 depending if we are 
			// evaluating a globally or a fatally expression. The corresponding value that is used to interrupt
			// this loop is shortcut
			double shortcut = (expr.op() == UnOp.Future) ? 0 : 1;
			ltl: {
				while (currentStepNb <= localBound) {
					
					//Next
					if (expr.op() == UnOp.Next) {
						currentStepNb = localBound;
						// If new stepNb outbound the untilStep break from while
						if (currentStepNb > untilStep && untilStep >= 0) {
							// Push undecided
							setResult(Double.NaN);
							// stack.push(Double.NaN);
							break ltl;
						}
					}
					// Should we generate a new state?
					while (model.getTrace().size() <= currentStepNb)
						callSimulate();
					// Get the right state
					currentState = model.getStateAtPos(currentStepNb);

					// Is the property satisfied?
					expr.child().accept(this);
					//Next
					if(expr.op() == UnOp.Next){
						break ltl;
					}
					// Fatally or Globally
					if (getResult() != shortcut)
						break ltl;
					
					// Progress stepNb
					currentStepNb++;
					// If new stepNb outbound the untilStep break from while
					if (currentStepNb > untilStep && untilStep >= 0)
						break;
				}
				//Case to exit
				// Undecided if untilStep positive and
				// If var currentStepNb didn't exceeded localbound
				if (untilStep >= 0 && currentStepNb <= localBound) {
					// Push undecided
					setResult(Double.NaN);
				}
				// Other cases, there is nothing to do
				// F true, true is already set, F end of bound, false is already set
				// G false, false is already set, G end of bound, true is already set
				// X, result is already set
			}
		}catch(PlasmaDeadlockException e){
			// Get the right state (last one available, because deadlock)
			currentState = model.getStateAtPos(model.getTrace().size() - 1);

			logger.debug("Deadlock catched in "+expr.op().toString()+" "+expr.toString()+".\n"
					+ "Visit child "+expr.child().toString()+" with offset "+offsetStepNb);
			// Is the property satisfied?
			// Push the value of the child
			expr.child().accept(this);
		} catch (PlasmaSimulatorException e) {
			throw new PlasmaRuntimeException(e);
		}
		//Remember farthest exploration
		offSetMap.put(expr, currentStepNb);
		//Resume currentStepNb
		currentStepNb = entryStepNb;
		currentState = model.getStateAtPos(currentStepNb);
	}


	@Override
	public void visit(StepBoundExpr2 expr) {
		//Remember time of arrival
		int entryStepNb = currentStepNb;
		//Compute upperBound
		//Store result value
		Double cResult = result;
		//Visit bound Expr
		expr.getStepbound().accept(this);
		int localBound = (int) (result+currentStepNb);
		//Restore result value
		result = cResult;
		//Is the expr we are about to check already partially checked until offset?
		offsetStepNb = offSetMap.get(expr);
		if (offsetStepNb!=null && currentStepNb < offsetStepNb)
			currentStepNb = offsetStepNb;
		
		try{
			until: {
				while (currentStepNb <= localBound) {
					//If new stepNb outbound the untilStep break from while
					if(currentStepNb>untilStep && untilStep >=0){
						// Push undecided
						setResult(Double.NaN);
						//stack.push(Double.NaN);
						break until;
					}
					// Should we generate a new state?
					while (model.getTrace().size() <= currentStepNb)
						callSimulate();
					// Get the right state
					currentState = model.getStateAtPos(currentStepNb);
	
					//IF RIGHT push true
	
					// Has the child been partially computed?
					if(offSetMap.containsKey(expr.right()))
						//If yes set offset
						offsetStepNb = offSetMap.get(expr.right());
					else 
						//Else set offset to currentState
						offsetStepNb = currentStepNb;
					expr.right().accept(this);
	
					if (getResult() != 0)
						break until;
	
					//ELSE IF LEFT continue		
	
					expr.left().accept(this);
					if(getResult() != 1)
						break until;
	
					// Progress stepNb
					currentStepNb++;
					//If new stepNb outbound the untilStep break from while
					if (currentStepNb>untilStep && untilStep >=0)
						break;
				}

				//Case to exit
				// Undecided if untilStep positive and
				// If var currentStepNb didn't exceeded localbound
				if (untilStep >= 0 && currentStepNb <= localBound) 
						setResult(Double.NaN);
				else if (expr.op()==BinOp.Until)
					setResult(0.);
				else if (expr.op()==BinOp.WeakUntil)
					setResult(1.);
			}
		}catch (PlasmaDeadlockException e){
			logger.debug("Deadlock catched in Until "+expr.toString()+" set result to false.");
			setResult(0.);
		} catch (PlasmaSimulatorException e) {
			throw new PlasmaRuntimeException(e);
		}
		//Remember farthest exploration
		offSetMap.put(expr, currentStepNb);
		//Resume currentStepNb
		currentStepNb = entryStepNb;
		currentState = model.getStateAtPos(currentStepNb);
	}


	@SuppressWarnings("incomplete-switch")
	@Override
	public void visit(Expr1 expr) {
		expr.child().accept(this);
		switch(expr.op()) {
		case Neg:
			setResult(-getResult());
			return;
		case Not:
			if(!getResult().isNaN())
				setResult(getResult() == 0 ? 1. : 0.);
			return;
			//!undecided = undecided
		}
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
			else if (getResult() == Double.NaN) { //Undecided && Right => false if Right = false else undecided
				expr.right().accept(this);
				if (getResult() != 0)
					setResult(Double.NaN);
			} // else false && ? = false
			return ;
		case Or:
			expr.left().accept(this);
			if (getResult() == 0) // false || Right = Right{
				expr.right().accept(this);
			else if (getResult() == Double.NaN) { // undecided || Right => true if Right = true else undecided
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
			else{
				expr.right().accept(this);
				//Undecided => True = true
				//else Undecided
				if(getResult()!= 1)
					setResult(Double.NaN);
			}
			return ;
		case Eq:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setResult(left == right ? 1. : 0.);
			return ;
		case Neq:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setResult(left != right ? 1. : 0.);
			return ;
		case Lt:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setResult(left < right ? 1. : 0.);
			return ;
		case Le:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setResult(left <= right ? 1. : 0.);
			return ;
		case Gt:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setResult(left > right ? 1. : 0.);
			return ;
		case Ge:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setResult(left >= right ? 1. : 0.);
			return ;
		case Add:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setResult(left + right);
			return ;
		case Min:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setResult(left - right);
			return ;
		case Mul:
			expr.right().accept(this); right = getResult();
			expr.left().accept(this);	left = getResult();
			setResult(left * right);
			return ;
		case Div:
			expr.right().accept(this); right = getResult();
			if (right == 0){
				throw new PlasmaRuntimeException("line "+expr.line+":"+expr.start+" - Divide by 0");
			}
			expr.left().accept(this);	left = getResult();
			setResult(left / right);
			return ;
		}
	}

	@Override
	public void visit(FloatExpr expr) {
		setResult(expr.value);
	}

	@Override
	public void visit(IntExpr expr) {
		setResult(expr.value);
	}

	@Override
	public void visit(IdentExpr expr) {
		try {
			setResult(currentState.getValueOf(expr.id));
		}
		catch(PlasmaSimulatorException e) {
			throw new PlasmaRuntimeException(e);
		}
	}

	@Override
	public void visit(BoolExpr expr) {
		setResult(expr.value ? 1.0 : 0.0);
	}

	@Override
	public void visit(DeadlockExpr expr) {
		//If a deadlock occured before or on the current step there is a deadlock
		int deadlockPos = model.getDeadlockPos();
		setResult((deadlockPos > 0 && deadlockPos <= currentStepNb) ? 1.0 : 0.0);
	}
	
	@Override
	public void visit(RewardExpr expr) {
		expr.child().accept(this);
		
		List<InterfaceState> trace = model.getTrace();
		try {
			setResult(trace.get(trace.size()-1).getValueOf(expr.getReward()));
		} catch (PlasmaSimulatorException e) {
			throw new PlasmaRuntimeException(e);
		}
	}

	@Deprecated
	//Not used anymore since, check returns Double
	public boolean getAnswer() {
		return getResult() == 1;
	}

	public Double getNumericalAnswer() {
		return getResult();
	}

	public void reinit(InterfaceState path) {
		reinit(-1, path);
	}

	public void reinit(int untilStep, InterfaceState path) {
		result = 0.;
		currentStepNb = 0;
		offsetStepNb = 0;
		offSetMap.clear();
		this.untilStep = untilStep;
		this.currentState = path;
	}

	protected void callSimulate() throws PlasmaSimulatorException {
		model.simulate();
	}


}
