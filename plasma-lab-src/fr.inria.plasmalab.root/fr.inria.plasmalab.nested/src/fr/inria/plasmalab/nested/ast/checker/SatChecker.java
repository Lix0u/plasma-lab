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
package fr.inria.plasmalab.nested.ast.checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import fr.inria.plasmalab.nested.ast.Expr;
import fr.inria.plasmalab.nested.ast.nodes.Bool;
import fr.inria.plasmalab.nested.ast.nodes.Expr1;
import fr.inria.plasmalab.nested.ast.nodes.Expr2;
import fr.inria.plasmalab.nested.ast.nodes.Floating;
import fr.inria.plasmalab.nested.ast.nodes.Ident;
import fr.inria.plasmalab.nested.ast.nodes.Int;
import fr.inria.plasmalab.nested.ast.nodes.Proba;
import fr.inria.plasmalab.nested.ast.nodes.Temp1;
import fr.inria.plasmalab.nested.ast.nodes.Temp2;
import fr.inria.plasmalab.nested.ast.operators.UnOp;
import fr.inria.plasmalab.nested.ast.visitors.ManualVisitor;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public class SatChecker implements ManualVisitor {
	private static class OffSet {
		int StepNb = 0;
		Map<Expr,Integer> Map = new HashMap<Expr, Integer>();
	}
	
	//private PrettyPrinter pp = new PrettyPrinter();

	private Stack<Double> stack;
	public AbstractModel model;	
	public InterfaceState currentState;
	public Strength strength;
	
	public Map<Proba,Map<Integer,Double>> ratios;

	//CurrentStepNb is the step number that we are exploring
	protected int currentStepNb;
	private int untilStep;
	
	//Offset to reduce the interval to explore (lower bound)
	private Stack<OffSet> offStack;
	
	private OffSet offSet() {
		return offStack.peek();
	}
	
	//int offsetStepNb; == offset().StepNb;
	//Offset map which map an Expr to its last exploring state. Semantics of an Offset might differ due to the type of Expr.
	//Map<Expr,Integer> offSetMap == offset().map;

	public SatChecker(AbstractModel model) {
		this.model = model;	
		this.untilStep = -1;
		this.ratios = new HashMap<Proba, Map<Integer,Double>>();

		//Doesn't genereate a new trace;
		//currentState = model.newPath();
		currentStepNb = 0;
		
		strength = new Strength();
		stack = new MyStack<Double>();
		offStack = new Stack<SatChecker.OffSet>();
		offStack.push(new OffSet()); //currentStepNb == offset.stepNb
	}	

	public void reinit(int untilStep, InterfaceState path){
		strength = new Strength();
		stack.clear();
		currentStepNb = 0;
		offStack.clear();
		offStack.push(new OffSet());  //currentStepNb == offset.stepNb
		currentState = path;
		this.untilStep = untilStep;
		this.ratios.clear();
	}

	public double getRatioOf(Proba proba, InterfaceState s) {

		try {
			Double res = ratios.get(proba).get(s.hashCode());

			if (res == null)
				return 1;
			else 
				return res;
		} catch (NullPointerException e) {
			return 1;
		}
	}
	public void setRatioOf(Proba proba, InterfaceState s, double seqRatio) {

		try {
			ratios.get(proba).put(s.hashCode(), seqRatio);
		} catch (NullPointerException e) {
			Map<Integer, Double> map = new HashMap<Integer, Double>();
			ratios.put(proba, map);
			map.put(s.hashCode(),seqRatio);
		}
	}

	
	@Override
	public void visit(Temp1 expr) {
		if (expr.unitIsStep)
			visitStepBound(expr);
		else
			visitTimeBound(expr);
	}
	public void visitTimeBound(Temp1 expr) {
	//	System.err.println("looser");

	}
	public void visitStepBound(Temp1 expr) {
		//Remember time of arrival
		int entryStepNb = currentStepNb;
		//Compute upperBound
		int localBound = (int) expr.bound + currentStepNb;

		if (currentStepNb < offSet().StepNb) 
			currentStepNb = offSet().StepNb;

		// The loop my be interrupted for when the child is evaluated to 1 or 0 depending if we are 
		// evaluating a globally or a fatally expression. The corresponding value that is used to interrupt
		// this loop is shortcut
		double shortcut = (expr.op == UnOp.Future) ? 1 : 0;
		loop: {
			while (currentStepNb <= localBound) {
				while (model.getTrace().size() <= currentStepNb)
					try {
						model.simulate();
					} catch (PlasmaSimulatorException e) {
						throw new PlasmaRuntimeException(e);
					}
				currentState = model.getTrace().get(currentStepNb);

				// Has the child been partially computed?
				if(offSet().Map.containsKey(expr.child))
					offSet().StepNb = offSet().Map.get(expr.child);
				else 
					offSet().StepNb = currentStepNb;
	
				expr.child.accept(this);
	
				if (stack.peek() == shortcut)
					break loop;
				else
					stack.pop(); 	// Else continue to explore the next states

				// Progress stepNb
				currentStepNb++;
				// If new stepNb outbounds the untilStep break from while
				if (currentStepNb > untilStep && untilStep >= 0)
					break;
			}

			// fatally: all states visited means expr is false
			// globally: all states visited means expr is true
			// the expected value at end of the exploration is stored in expected 
			double expected = (expr.op == UnOp.Future) ? 0 : 1;
			// If localbound not reached but end of trace
			if (untilStep>=0 && currentStepNb <= localBound)
				stack.push(2.);					// Push undecided
			else
				stack.push(expected);					// Push false
		}
		//Remember farthest exploration
		offSet().Map.put(expr, currentStepNb);
		//Resume currentStepNb
		currentStepNb = entryStepNb;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void visit(Temp2 expr) {


		//Remember time of arrival
		int entryStepNb = currentStepNb;
		//Compute upperBound
		int localBound = (int) expr.bound + currentStepNb;

		if (currentStepNb < offSet().StepNb) 
			currentStepNb = offSet().StepNb;

		untilLoop:{
			while (currentStepNb <= localBound) {
				while (model.getTrace().size() <= currentStepNb)
					try {
						model.simulate();
					} catch (PlasmaSimulatorException e) {
						throw new PlasmaRuntimeException(e);
					}
				currentState = model.getTrace().get(currentStepNb);

				// Has the child been partially computed?
				if(offSet().Map.containsKey(expr.left))
					offSet().StepNb = offSet().Map.get(expr.left);
				else 
					offSet().StepNb = currentStepNb;

				expr.right.accept(this);
				if (stack.peek() ==  1) {
					break untilLoop; // Until result is the result of the right side
				} else {
					stack.pop();
					expr.left.accept(this);
					if (stack.peek() == 0)
						break untilLoop;
					else
						stack.pop();
				}
				// Else continue
				// Progress stepNb
				// currentStepNb = map.sousExpr.time+1
				currentStepNb++;
				//If new stepNb outbound the untilStep break from while
				if (currentStepNb > untilStep && untilStep >= 0)
					break;
			}

			// If localbound not reached but end of trace
			if (untilStep >= 0 && currentStepNb <= localBound)
				stack.push(2.);					// Push undecided
			else
				switch (expr.op) {
				case Until:
					stack.push(0.); break;
				case WeakUntil:
					stack.push(1.); break;
				}
		}
		//Remember farthest exploration
		offSet().Map.put(expr, currentStepNb);
		//Resume currentStepNb
		currentStepNb = entryStepNb;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void visit(Expr1 expr) {

		expr.child.accept(this);

		switch (expr.op) {
		case Neg:
			stack.push(-stack.pop());
			return;
		case Not:
			double bool = stack.pop();
			bool = (bool == 0) ? 1 : 0;
			stack.push(bool);
			return;
			//default: other operators are unexpected there
		}
	}

	@Override
	public void visit(Expr2 expr) {
		//System.out.println(pp.of(expr));
		
		switch (expr.op) {
		case And:
			expr.left.accept(this);
			if (stack.peek() == 1) {
				stack.pop();
				expr.right.accept(this);
			} else
				; // false && _ == false	Nothing to do
			return;
		case Or:
			expr.left.accept(this);
			if (stack.peek() == 0) { // false || right == right
				stack.pop();
				expr.right.accept(this); // right == stack.peek();
			} else
				; // true && _ == true 		Nothing to do
			return ;
		case Imp:
			expr.left.accept(this);
			if (stack.peek() == 1) {
				stack.pop();
				expr.right.accept(this);
			} else if (stack.peek() == 0) { 	// (false => _) == true
				stack.pop();
				stack.push(1.);
			}
			return ;
		case Eq:
			expr.right.accept(this);
			expr.left.accept(this);
			stack.push((stack.pop() == stack.pop()) ? 1. : 0.);  //left == right ?
			return ;
		case Neq:
			expr.right.accept(this);
			expr.left.accept(this);
			stack.push((stack.pop() != stack.pop()) ? 1. : 0.);	//left != right ?
			return ;
		case Lt:
			expr.right.accept(this);
			expr.left.accept(this);
			stack.push((stack.pop() < stack.pop()) ? 1. : 0.);	//left < right ?
			return ;
		case Le:
			expr.right.accept(this);
			stack.push((stack.pop() <= stack.pop()) ? 1. : 0.);	//left <= right ?
			return ;
		case Gt:
			expr.right.accept(this);
			expr.left.accept(this);
			stack.push((stack.pop() > stack.pop()) ? 1. : 0.);	//left > right ?
			return ;
		case Ge:
			expr.right.accept(this);
			expr.left.accept(this);
			stack.push((stack.pop() >= stack.pop()) ? 1. : 0.);	//left >= right ?
			return ;
		case Add:
			expr.right.accept(this);
			expr.left.accept(this);
			stack.push(stack.pop() + stack.pop());
			return ;	
		case Min:
			expr.right.accept(this);
			expr.left.accept(this);
			stack.push(stack.pop() - stack.pop());
			return ;	
		case Mul:
			expr.right.accept(this);
			expr.left.accept(this);
			stack.push(stack.pop() * stack.pop());
			return ;	
		case Div:
			expr.right.accept(this);
			expr.left.accept(this);
			stack.push(stack.pop() / stack.pop());
			return ;	
		default:
			throw new RuntimeException("Internal Error: the operator " + expr.op + "is not supported");
		}
	}

	@Override
	public void visit(Floating expr) {
		stack.push(expr.value);
	}

	@Override
	public void visit(Int expr) {
		stack.push((double) expr.value);
	}

	@Override
	public void visit(Ident expr) {
		try {
			stack.push((Double) currentState.getValueOf(expr.value));
		} catch (PlasmaSimulatorException e) {
			throw new PlasmaRuntimeException(e);
		}
	}

	@Override
	public void visit(Bool expr) {
		stack.push(expr.value);
	}

	public void visit(Proba proba) {

		double prob = proba.proba;
		double	alpha1 = proba.alpha1,	// \alpha' 
				beta1 = proba.beta1, 	// \beta'
				delta = proba.delta;	// \delta' 
		
		double seqratio = getRatioOf(proba, currentState);
		
		final double p0 = (prob + delta) * (1 - alpha1),
					 p1 = 1 - (1 - (prob - delta)) * (1 - beta1); 

		final double	ratioT = p1 / p0, // p1 / p0
						ratioF = (1 - p1) / (1 - p0);  // (1 - p0) / (1 - p1)

		final AlphaBeta str = strength.strengthOf(proba);
		
		final double	strengthA = (1 - str.beta) / str.alpha,
						strengthB = str.beta / (1 - str.alpha);


		// long start = System.currentTimeMillis();
		// System.out.print("verifying " + model.getName());
		
		//Save the current trace
		List<InterfaceState> realTrace = model.getTrace();
		//Copy the trace and cut it to currentState
		List<InterfaceState> copyTrace = new ArrayList<InterfaceState>(realTrace);
		copyTrace = copyTrace.subList(0, copyTrace.indexOf(currentState));
		//As sublist sup bound is exclusive we add currentState
		copyTrace.add(currentState);
		//Copy current state
		InterfaceState savedPath = currentState;

		boolean again = seqratio < strengthA && seqratio > strengthB;
		// int posCount=0, ttCount=0;
		int offsetStepNb = offStack.peek().StepNb;
		int memoryOffSet = offsetStepNb;
		
		while (again) {
			//Call for a new trace we a copy of copytrace
			try {
				model.newPath(new ArrayList<InterfaceState>(copyTrace));
			} catch (PlasmaSimulatorException e) {
				throw new PlasmaRuntimeException(e);
			}	
			// check de la propriété
			offStack.push(new OffSet());
			
			offsetStepNb = currentStepNb;
			forgetOffset(proba.child);
			proba.child.accept(this);
			offStack.pop();
			double result = stack.pop(); //check_of_the_property();

			// if an error occured we leave...
			//if (parser.errors.count > 0) break;
			seqratio *= result == 0 ? ratioF : ratioT;
			
			again =  seqratio < strengthA && seqratio > strengthB;
		
		} // end of samples loop

		//forgetOffset(proba.child);
		offsetStepNb = memoryOffSet;
		// n = # of samples satisfying property
		// m = # of samples unsatisfying property
		//
		// seqratio = [p1 / p0]^n * [(1 - p1) / (1 - p0)]^m 

		//long consumed = System.currentTimeMillis() - start;
		//System.out.println("(" + consumed / 1000f + " seconds)");
		//System.out.println("Proba is "+(double)posCount/ttCount);
		double result = seqratio <= strengthB ? 1 : 0;
		setRatioOf(proba, savedPath, seqratio);
		currentState = savedPath;
		try {
			model.newPath(realTrace);
		} catch (PlasmaSimulatorException e) {
			throw new PlasmaRuntimeException(e);
		}
		stack.push(result);
	}

	public void apply(Expr expr) {
		strength = new Strength();
		AlphaBeta ab = new AlphaBeta(expr.init_alpha, expr.init_beta);
		strength.context.push(ab);
		expr.accept(strength);
		expr.accept(this);
	}
	
	public boolean getAnswer() {
		return stack.peek() != 0;
	}

	public double getNumericalAnswer() {
		return stack.peek();
	}
	
	private void forgetOffset(Expr expr){
		//offSetMap.remove(expr);
		if(expr instanceof Expr1){
			forgetOffset(((Expr1)expr).child);
		}else if(expr instanceof Expr2){
			forgetOffset(((Expr2)expr).left);
			forgetOffset(((Expr2)expr).right);
		}
	}

}
