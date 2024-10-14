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
package fr.inria.plasmalab.rmlbis.ast;

import java.util.List;

import fr.inria.plasmalab.rmlbis.ast.expr.Expr;
import fr.inria.plasmalab.rmlbis.exceptions.WrongParametersNumber;

/** A system associated to a list of expressions to initialize its parameters. */
public class PlasmaSystemInstance {

	public PlasmaSystem sys;
	public List<Expr> parameters;

	public PlasmaSystemInstance(PlasmaSystem sys, List<Expr> parameters) throws WrongParametersNumber {
		this.sys = sys;
		this.parameters = parameters;
		if (parameters.size() < sys.getParameters().size())
			throw new WrongParametersNumber(sys, parameters.size(), sys.getParameters().size());
	}

	public void initialize() {
		sys.initialize(parameters);
	}

}
