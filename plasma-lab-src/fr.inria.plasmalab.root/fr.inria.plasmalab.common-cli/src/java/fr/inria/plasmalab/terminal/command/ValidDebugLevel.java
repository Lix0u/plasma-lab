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
/**
 * This file is part of hocl-workflow.
 *
 * hocl-workflow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * hocl-workflow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with hocl-workflow.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.terminal.command;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class ValidDebugLevel implements IParameterValidator {

    public static int maxDebugLevel = 3;
    public static int minDebugLevel = 0;
    
    @Override
    public void validate(String name, String value) throws ParameterException {
        int n = Integer.parseInt(value);
        if (n < minDebugLevel ||  n > maxDebugLevel ) {
            throw new ParameterException(String.format("Debug level must be between %s an %s", minDebugLevel, maxDebugLevel));
        }
    }

}
