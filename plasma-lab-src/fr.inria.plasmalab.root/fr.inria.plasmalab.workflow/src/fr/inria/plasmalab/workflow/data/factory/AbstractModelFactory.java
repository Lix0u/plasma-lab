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
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

/**
 * 
 * @author kevin.corre@inria.fr
 * 
 * This class contains the basic functions of a Model Factory.</p>
 * 
 * A Model Factory will instantiate a {@link AbstractData} of the sub-type {@link AbstractModel}.
 */
public abstract class AbstractModelFactory implements AbstractDataFactory {
	
	@Override
	public String toString() {
		return getName();
	}
	
	public AbstractData createAbstractData(String name) throws PlasmaDataException {
		return createAbstractModel(name);
	}
	
	public AbstractData createAbstractData(String name, File file) throws PlasmaDataException {
		return createAbstractModel(name, file);
	}
	
	public AbstractData createAbstractData(String name, String content) throws PlasmaDataException {
		return createAbstractModel(name, content);		
	}
	
	/** Create an empty model.
	 *  This constructor is used when creating a new model.
	 *  
	 *  @param name the name of the model
	 */
	public abstract AbstractModel createAbstractModel(String name) throws PlasmaDataException;
	/** Create a model from a file.
	 *  This constructor is used when importing a model from a file.
	 *  
	 *  @param name the name of the model
	 *  @param file the file that contains the model
  	 *  @throws PlasmaDataException	if an error occurs while opening the file
	 */
	public abstract AbstractModel createAbstractModel(String name, File file) throws PlasmaDataException ;
	/** Create a model from a content.
	 *  This constructor is used when opening a saved project.
	 *  
	 *  @param name the name of the model
	 *  @param content the content of the model
	 */
	public abstract AbstractModel createAbstractModel(String name, String content) throws PlasmaDataException;
}
