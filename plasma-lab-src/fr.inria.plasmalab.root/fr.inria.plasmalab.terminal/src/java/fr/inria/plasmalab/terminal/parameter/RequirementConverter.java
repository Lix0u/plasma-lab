/**
 * This file is part of fr.inria.plasmalab.terminal.
 *
 * fr.inria.plasmalab.terminal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.terminal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.terminal.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package fr.inria.plasmalab.terminal.parameter;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

import fr.inria.plasmalab.controler.Requirement;

/**
 * @author msimonin
 *
 */
public class RequirementConverter implements IStringConverter<Requirement> {

    
    private static final String separator_ = ":";

    @Override
    public Requirement convert(String requirementString) {
        String[] requirementArray = requirementString.split(separator_);
        if (requirementArray.length != 2){
            throw new ParameterException("The requirement description must follow this pattern file:type");
        }
        return new Requirement(requirementArray[0], requirementArray[1]);
    }
}
