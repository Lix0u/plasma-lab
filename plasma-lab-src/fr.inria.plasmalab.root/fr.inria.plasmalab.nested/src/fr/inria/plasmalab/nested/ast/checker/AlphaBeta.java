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


public class AlphaBeta {

	public double alpha = 0;
	public double beta = 0;
	
	public AlphaBeta(double alpha, double beta) {
		this.alpha = alpha;
		this.beta = beta;
	}

	public boolean strongerThan(AlphaBeta strength) {
		return this.alpha <= strength.alpha &&
				this.beta <= strength.beta;
	}
}
