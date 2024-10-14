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
package fr.inria.plasmalab.rmlbis.ast.location;

/** TODO: The class is unused at the moment. Expr and Identifier extend it, but no method call exists.
 * 
 * @author ltraonou
 *
 */
public class Location {
	public int absolute;
	public int col;
	public int line;
	public int length;
	
	
	public void setLocation(Location loc) {
		absolute = loc.absolute;
		col = loc.col;
		line = loc.line;
		length = loc.length;
	}
	
	public Location getLocation() {
		return this;
	}
	
	public String printLocation() {
		return 	"absolute: " + absolute + " column: " + col + " line: " + line + " length: " + length;
	}
}
