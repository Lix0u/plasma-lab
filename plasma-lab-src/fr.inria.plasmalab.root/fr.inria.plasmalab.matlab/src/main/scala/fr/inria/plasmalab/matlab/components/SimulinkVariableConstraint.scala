/**
 * This file is part of fr.inria.plasmalab.matlab.
 *
 * fr.inria.plasmalab.matlab is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.matlab is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.matlab.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.matlab.components

import fr.inria.plasmalab.workflow.concrete.VariableConstraint

class SimulinkVariableConstraint (varlow: String, varhigh: String, sign: Boolean) 
	extends VariableConstraint(varlow, varhigh, sign){
}