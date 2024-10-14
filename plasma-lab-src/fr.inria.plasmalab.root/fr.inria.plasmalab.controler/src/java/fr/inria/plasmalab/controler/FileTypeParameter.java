/**
 * This file is part of fr.inria.plasmalab.controler.
 *
 * fr.inria.plasmalab.controler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.controler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.controler.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package fr.inria.plasmalab.controler;

/**
 * @author msimonin
 *
 */
public class FileTypeParameter {
    
    /** File.*/
    String file_;
    
    /** Type.*/
    String type_;
    
    /**
     * @param file  The file.
     * @param type  The type.
     */
    public FileTypeParameter(String file, String type) {
        file_ = file;
        type_ = type;
    }

    /**
     * @return the file
     */
    public String getFile() {
        return file_;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type_;
    } 
}
