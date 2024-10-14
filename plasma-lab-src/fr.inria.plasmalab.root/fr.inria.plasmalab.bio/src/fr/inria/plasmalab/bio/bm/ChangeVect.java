/**
 * This file is part of fr.inria.plasmalab.bio :: Biological models.
 *
 * fr.inria.plasmalab.bio :: Biological models is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bio :: Biological models is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bio :: Biological models.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * This file is part of fr.inria.plasmalab.bio.
 *
 * fr.inria.plasmalab.bio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.bio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.bio.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.bio.bm;

/**
 * Simple vector to update species
 * @author Sean Sedwards
 * @version 6/12/2012
 */
class ChangeVect {
	ChangeVect next;
	int index = -1;
	int change = 0;
	
	public ChangeVect(int index, int change)
	{
		this.index = index;
		this.change = change;
	}
}
