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

/**
 * @author msimonin
 *
 */
public enum OutputFormat {
	/**Text format.*/
    text,
	
	/**Csv format.*/
    csv,
	
	/**Probability format.*/
    proba;

    /**
     * 
     * Convert String to OutputFormat.
     * 
     * @param value     The String value to convert
     * @return  The corresponding OutputFomat, default to raw 
     */
    public static OutputFormat fromString(String value) {
       for (OutputFormat outputFormat : OutputFormat.values()){
           if (outputFormat.toString().equals(value)) {
               return outputFormat;
           }
       }
       // default to text
       return OutputFormat.text;
    }
}
