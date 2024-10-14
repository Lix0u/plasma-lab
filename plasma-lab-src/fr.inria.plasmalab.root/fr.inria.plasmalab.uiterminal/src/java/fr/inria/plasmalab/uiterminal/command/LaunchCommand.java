/**
 * This file is part of fr.inria.plasmalab.uiterminal.
 *
 * fr.inria.plasmalab.uiterminal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.uiterminal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.uiterminal.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.uiterminal.command;

import fr.inria.plasmalab.terminal.command.CommonCommand;

/**
 * 
 * @author msimonin
 *
 */
public class LaunchCommand extends CommonCommand {

	public static String name = "launch";
	
	@Override
	public String getName() {
		return name;
	}
 
}
