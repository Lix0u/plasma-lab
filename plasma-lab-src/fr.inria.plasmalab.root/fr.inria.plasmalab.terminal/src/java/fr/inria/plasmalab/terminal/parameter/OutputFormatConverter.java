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

/**
 * 
 * Converter to use to convert String to OutputFormat
 * 
 * @author msimonin
 *
 */
public class OutputFormatConverter implements IStringConverter<OutputFormat> {

    /* (non-Javadoc)
     * @see com.beust.jcommander.IStringConverter#convert(java.lang.String)
     */
    @Override
    public OutputFormat convert(String value) {
        return OutputFormat.fromString(value);
    }

}
