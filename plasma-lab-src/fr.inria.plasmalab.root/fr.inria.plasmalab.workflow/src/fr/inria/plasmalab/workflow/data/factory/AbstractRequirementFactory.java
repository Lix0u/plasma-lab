/**
 * This file is part of fr.inria.plasmalab.workflow.
 *
 * fr.inria.plasmalab.workflow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.workflow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.workflow.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.workflow.data.factory;

import java.io.File;

import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;


/**
 * 
 * @author kevin.corre@inria.fr
 * 
 * This class contains the basic functions of a Requirement Factory.</p>
 * 
 * A Requirement Factory will instantiate an {@link AbstractData} of the sub-type {@link AbstractRequirement}.
 */
public abstract class AbstractRequirementFactory implements AbstractDataFactory {
	
	@Override
	public String toString() {
		return getName();
	}
	
	public AbstractData createAbstractData(String name) {
		return createAbstractRequirement(name);
	}
	
	public AbstractData createAbstractData(String name, File file) throws PlasmaDataException {
		return createAbstractRequirement(name, file);
	}
	
	public AbstractData createAbstractData(String name, String content) {
		return createAbstractRequirement(name, content);		
	}
	
	/** Create an empty requirement.
	 *  This constructor is used when creating a new requirement.
	 *  
  	 *  @param name the name of the requirement
	 */
	public abstract AbstractRequirement createAbstractRequirement(String name);
	/** Create a requirement from a file.
	 *  This constructor is used when importing a requirement from a file.
	 *  
	 *  @param name the name of the requirement
	 *  @param file the file that contains the requirement
  	 * @throws PlasmaDataException	if an error occurs while opening the file
	 */
	public abstract AbstractRequirement createAbstractRequirement(String name, File file) throws PlasmaDataException; 
	/** Create a requirement from a content.
	 *  This constructor is used when opening a saved project.
	 *  
	 *  @param name the name of the requirement
	 *  @param content the content of the requirement
	 */
	public abstract AbstractRequirement createAbstractRequirement(String name, String content);

}
