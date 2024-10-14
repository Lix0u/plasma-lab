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
package fr.inria.plasmalab.rmlbis.ast.processing;

import fr.inria.plasmalab.rmlbis.ast.expr.Ref;


public class PrismPrinter extends Printer {

	private String sysPrefix;
	
	public PrismPrinter(String p) {
		this.value = "";
		this.sysPrefix = p;
	}
	
	public PrismPrinter(String p, String init) {
		this.value = init;
		this.sysPrefix = p;
	}
	

	@Override
	public void visit(Ref id) {
		value += id.getPrismName(sysPrefix);
	}
}
