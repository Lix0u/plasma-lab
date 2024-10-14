/**
 * This file is part of fr.inria.plasmalab.common-cli.
 *
 * fr.inria.plasmalab.common-cli is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.common-cli is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.common-cli.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.terminal.command;

import com.beust.jcommander.Parameter;

/**
 * @author msimonin
 *
 */
public class InfoCommand extends CommonCommand {
	
    public static final String name = "info";

    
	@Parameter(names = {"-a", "--algorithm"}, 
            description = "Display information about the algorithm", 
            required = false)
    private String algorithm_;
	
	@Override
	public String getName() {
		return name;
	}
	
	public String getAlgorithm() {
		return algorithm_;
	}

    
}
