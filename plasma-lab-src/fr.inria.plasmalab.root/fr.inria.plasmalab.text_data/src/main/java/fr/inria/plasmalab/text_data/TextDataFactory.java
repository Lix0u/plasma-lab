/**
 * This file is part of fr.inria.plasmalab.text_data.
 *
 * fr.inria.plasmalab.text_data is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.text_data is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.text_data.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.text_data;

import java.io.File;

import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

/**
 * @author ltraonou
 *
 */
public class TextDataFactory implements AbstractDataFactory {
	
	
	private static final String NAME="Text";
	private static final String DESCRIPTION="Handles plain text data.";
	private static final String ID="text";
	/** Count the number of unnamed TextData */
	private static int count = 0;

	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public String toString() {
		return NAME;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public String getId() {
		return ID;
	}
	
	@Override
	public AbstractData createAbstractData(String name){
		return new TextData(name, "", ID);
	}
	@Override
	public AbstractData createAbstractData(String name, File file) throws PlasmaDataException{
		return new TextData(name, file, ID);
	}
	@Override
	public AbstractData createAbstractData(String name, String content){
		return new TextData(name, content, ID);
	}
	
	/** Create a text data from an incorrect data.
	 * 
	 * @param name 			name of the data
	 * @param content		content of the data
	 * @param exception		error found in the data
	 * @param id			id of the factory originally used to generate the data
	 * @return				TextData
	 */
	public AbstractData createTextData(String name, String content, PlasmaDataException exception, String id) {
		if(name.equals("")){
			name = NAME+"_"+count;
			count++;
		}
		return new TextData(name, content, id, exception);
	}
	
	/** Create a text data from an incorrect data.
	 * 
	 * @param name 			name of the data
	 * @param file			file that contains the data
	 * @param exception		error found in the data
	 * @param id			id of the factory originally used to generate the data
	 * @return				TextData
	 * @throws PlasmaDataException 
	 */
	public AbstractData createTextData(String name, File file, PlasmaDataException exception, String id) throws PlasmaDataException {
		if(name.equals("")){
			name = NAME+"_"+count;
			count++;
		}
		return new TextData(name, file, id, exception);
	}
}
