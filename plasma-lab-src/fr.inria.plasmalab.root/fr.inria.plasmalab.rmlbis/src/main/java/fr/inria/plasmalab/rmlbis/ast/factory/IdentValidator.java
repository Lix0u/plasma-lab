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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import fr.inria.plasmalab.rmlbis.ast.expr.Ref;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Constant;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Identifier;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Parameter;
import fr.inria.plasmalab.rmlbis.ast.identifiers.Variable;
import fr.inria.plasmalab.rmlbis.exceptions.CircularDeclaration;
import fr.inria.plasmalab.rmlbis.exceptions.UndeclaredRef;
import fr.inria.plasmalab.rmlbis.exceptions.VarDependency;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class IdentValidator {


	private Map<Ref,Collection<Ref>> dependencies = new HashMap<Ref, Collection<Ref>>();
	private Map<Ref,Collection<Ref>> owners = new HashMap<Ref, Collection<Ref>>();


	public void register(Identifier ident) {
		Collection<Ref> found = Referencies.of(ident);

		dependencies.put(ident.ref, found);
		for (Ref dep: found) {
			Collection<Ref> parents = owners.get(dep);
			if (parents == null) {
				parents = new HashSet<Ref>();
				owners.put(dep, parents);
			}
			parents.add(ident.ref);
		}
	}

	public void unregister(Identifier ident) throws PlasmaSyntaxException {
		Collection<Ref> found = Referencies.of(ident);

		for (Ref dep: found) {
			Collection<Ref> parents = owners.get(dep);
			if (parents != null)
				parents.remove(ident.ref);
		}
		dependencies.remove(ident.ref);
	}

	public void updateDeps(Ref x) {

		for (Ref y: owners(x)) {
			Collection<Ref> depsY = deps(y);
			depsY.remove(x);
			depsY.addAll(deps(x));
		}
		owners.put(x, new HashSet<Ref>());

		for (Ref y: deps(x))
			owners(y).addAll(owners(x));
	}

	public Collection<Ref> deps(Ref ref) {
		Collection<Ref> found = dependencies.get(ref);

		if (found == null)
			found = new HashSet<Ref>();
		return found;

	}

	public Collection<Ref> owners(Ref ref) {
		Collection<Ref> found = owners.get(ref);

		if (found == null)
			found = new HashSet<Ref>();
		return found;
	}

	public void checkClosure(Ref ref) throws PlasmaSyntaxException {
		for(Ref dep: dependencies.get(ref)) {
			if (dep.target == null)
				throw new UndeclaredRef(ref);
			else if (dep.target instanceof Variable)
				throw new VarDependency(ref, dep);
			else
				throw new Error(dep + " not subsituted: internal error");
		}
	}

	public void checkCycleFreeness(Ref ref) throws CircularDeclaration {
		if (dependencies.get(ref).contains(ref))
			throw new CircularDeclaration(ref.target);				
	}

	public void checkNoVarInRefs(Ref ref) throws PlasmaSyntaxException {
		for (Ref dep: dependencies.get(ref)) {
			if (dep.target == null)
				throw new UndeclaredRef(dep);
			if (dep.target instanceof Variable)
				throw new VarDependency(ref, dep);
		}
	}

	public void checkDefConsistency() throws PlasmaSyntaxException {
		for (Entry<Ref,Collection<Ref>> entry: dependencies.entrySet()) {
			Ref ref = entry.getKey();

			if (ref.target instanceof Variable || ref.target instanceof Constant) {
				for (Ref dep: entry.getValue())
					if (dep.target == null)
						throw new UndeclaredRef(dep);
					else if (dep.target instanceof Variable) // A variable may hide 
						if (dep.target instanceof Parameter) {
							if (! ((Parameter) dep.target).constant ) {
								//System.out.println("\ndep " + dep.target + "; ref:" + ref.target + ".");
								throw new VarDependency(ref, dep);
							} 
						} else { //dep.target is actually a variable...
							//System.out.println("\ndep " + dep.target + "; ref:" + ref.target + ".");
							throw new VarDependency(ref, dep);
						} 
//					else {
//						//System.out.println("\ndep " + dep.target + "; ref:" + ref.target + ".");
//						throw new Error("Internal Error, it should not appear...");
//						// It can now, since constant are not substituted				
//					}
			}
			else { // Label or Formula
				for (Ref dep: entry.getValue())
					if (dep.target == null) throw new UndeclaredRef(dep);
			}
		}

	}

	public void checkTypes() throws PlasmaSyntaxException {
		TypeChecking tc = new TypeChecking();

		for (Ref ref: dependencies.keySet())
			if (ref.target != null && ref.target instanceof Identifier) 
				tc.of((Identifier) ref.target);
	}

}
