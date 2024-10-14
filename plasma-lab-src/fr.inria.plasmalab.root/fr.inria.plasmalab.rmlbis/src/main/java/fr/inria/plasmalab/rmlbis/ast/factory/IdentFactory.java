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
package fr.inria.plasmalab.rmlbis.ast.factory;

import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.expr.types.Type;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Formula;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Label;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Parameter;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.ast.rewards.Reward;
import fr.inria.plasmalab.rmlbis.exceptions.DuplicateIdentifier;
import fr.inria.plasmalab.rmlbis.exceptions.UndeclaredRef;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class IdentFactory extends ExprFactory {

	private IdentValidator iv = new IdentValidator();
	private Substitution subst = new Substitution();
	private RootSubst rsubst = new RootSubst();
	private int nbRewards = 0; 
	
	
	private void rsubst(Ref target, Expr body) {
		for (Ref owner: iv.owners(target)) {
			if (owner.target instanceof Identifier)
			rsubst.apply((Identifier) owner.target, target, body);
		}
	}


	private void assign(Ref ref, InterfaceIdentifier ident) throws DuplicateIdentifier {
		if (ref.target == null)
			ref.target = ident;
		else
			throw new DuplicateIdentifier(ref.target, ident);
	}

	protected Ref makeFreshRef(String name) {
		Ref found = idents.get(name);

		if (found == null) {
			found = new Ref(name);
			idents.put(name, found);
		}
		return found;
	}

	public Label makeLabel(String name, Expr body) {
		name = "\"" + name + "\""; 
		return new Label(name, body);
	}

	public Reward makeReward(String name) {
		nbRewards++;
		if (name.isEmpty())
			name = "Reward"+nbRewards;
		name = "\"" + name + "\""; 
		return new Reward(name);
	}


	/** Variable and parameter registration:
	 *  - assign the variable to the reference
	 *  - register the variable
	 *  - check that the variable is not defined by itself (the lower or upper bounds, the initial value)
	 * @throws DuplicateIdentifier 
	 */
	private void registration(Ref ref, Variable var) {
		assign(ref, var);
		iv.register(var);
		iv.checkCycleFreeness(ref);
	}

	public Variable makeVariable(String name, Type type) {
		Ref ref = makeFreshRef(name);
		Variable var = new Variable(ref, type, this);
		registration(ref, var);
		return var;
	}

	public Variable makeVariable(String name, Type type, Expr initValue) {
		Ref ref = makeFreshRef(name);
		Variable var = new Variable(ref, type, initValue);
		registration(ref, var);
		return var;
	}

	public Variable makeVariable(String name, Expr lower, Expr upper) { 
		Ref ref = makeFreshRef(name);
		Variable var = new Variable(ref, lower, upper);
		registration(ref, var);
		return var;
	}

	public Variable makeVariable(String name, Expr lower, Expr upper, Expr initValue) {
		Ref ref = makeFreshRef(name);
		Variable var = new Variable(ref, lower, upper, initValue);
		registration(ref, var);
		return var;
	}
	
	public Parameter makeParameter(String name, Type type, boolean constant) {
		Ref ref = makeFreshRef(name);
		Parameter parameter = new Parameter(ref, type, constant, this);
		registration(ref, parameter);
		return parameter;
	}

	public Parameter makeParameter(String name, Type type, Expr initValue, boolean constant) {
		Ref ref = makeFreshRef(name);
		Parameter parameter = new Parameter(ref, type, initValue, constant);
		registration(ref, parameter);
		return parameter;
	}

	public Parameter makeParameter(String name, Expr lower, Expr upper, boolean constant) { 
		Ref ref = makeFreshRef(name);
		Parameter parameter = new Parameter(ref, lower, upper, constant);
		registration(ref, parameter);
		return parameter;
	}

	public Parameter makeParameter(String name, Expr lower, Expr upper, Expr initValue, boolean constant) throws PlasmaSyntaxException {
		Ref ref = makeFreshRef(name);
		Parameter parameter = new Parameter(ref, lower, upper, initValue, constant);
		registration(ref, parameter);
		return parameter;
	}
	
	/** Make a ref to an external identifier (not declared in the model). 
	 * @throws DuplicateIdentifier */
	public Identifier makeExternalId(String name, Type type) throws DuplicateIdentifier {
		Ref ref = makeFreshRef(name);
		Identifier id = new Identifier(ref, type);
		assign(ref, id);
		return id;
	}

	/** Constant registration:
	 *  - assign the constant to the reference
	 *  - register the constant
	 *  - check that the constant value is not defined by itself
	 * @throws DuplicateIdentifier 
	 */
	private void registration(Ref ref, Constant cst) {
		assign(ref, cst);
		iv.register(cst);
		iv.checkCycleFreeness(ref);
		// louis: I comment the 2 substitutions: constants are no longer substituted
		//	subst.apply(cst);
		//	rsubst(cst.ref, cst.value);
		// louis: I comment the update deps. If I understand correctly it updates the dependencies after substituion were performed. So it's no longer needed. 
		//	iv.updateDeps(ref);
		// louis: consequently the second checkCycleFreeness is useless
		//	iv.checkCycleFreeness(ref);
	}

	public Constant makeConstant(String name, Type type, Expr value) {
		Ref ref = makeFreshRef(name);
		Constant cst = new Constant(ref, type, value);
		registration(ref, cst);
		return cst;
	}

	public Constant makeConstant(String name, Type type) {
		Ref ref = makeFreshRef(name);
		Constant cst = new Constant(ref, type, this);
		registration(ref, cst);
		return cst;
	}
	
	
	public Formula makeFormula(String name, Expr body) {
		Ref ref = makeFreshRef(name);
		Formula formula = new Formula(ref, body);
		assign(ref, formula);
		iv.register(formula);
		iv.checkCycleFreeness(ref);
		subst.apply(formula);
		rsubst(ref, formula.body);
		iv.updateDeps(ref);
		iv.checkCycleFreeness(ref);
		return formula;
	}

	/** 
	 * Must be absolutely invoked when the factory finished its job:
	 * it ensures the closure and the consistency of dependencies and types in the built Expr. 
	 * @throws UndeclaredRef 
	 */
	public void check() throws PlasmaSyntaxException {
		for (Ref ref: idents.values())
			if (ref.target == null)
				throw new UndeclaredRef(ref);
			
		iv.checkDefConsistency(); //both closure and consistency. 
		iv.checkTypes();
		// iv.eval(); // don't do this it creates a bug!
	}
}
