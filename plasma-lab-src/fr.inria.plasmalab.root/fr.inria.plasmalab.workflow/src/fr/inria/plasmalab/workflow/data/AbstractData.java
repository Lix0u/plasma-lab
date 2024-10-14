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
package fr.inria.plasmalab.workflow.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

/**
 * 
 * @author kevin.corre@inria.fr
 *
 * This class contains the basic functions of an Plasma Lab data.
 * 
 * A Data represents a {@link Model} that Plasma Lab can simulate or a {@link Requirement} that can be checked.
 * The abstract class Data contains functions shared between Model and Requirement that will be used
 * in the Plasma Lab editor.
 */
public abstract class AbstractData {

	/** Name of the data */
	protected String name;
	/** Id of the factory that built this model */
	protected String id;
	/** Content of the data */
	protected String content;
	/** File from which the data is issued */
	protected File origin;
	/** Errors that concern this data */
	protected List<PlasmaDataException> errors;
	
	/** Return the name of the data */
	public String getName() {
		return name;
	}
	/** Change the name of the data */
	public void rename(String newName) {
		name = newName;
	}
	/** Return the id of the factory used to build this data */
	public String getId() {
		return id;
	}
	/** Return the content of the data */
	public String getContent() {
		return content;
	}
	/** Check if the content of the data is empty */
	public boolean isEmpty() {
		return content.isEmpty();
	}
	/** Check the origin file of the data (or null if not) */
	public File getOrigin() {
		return origin;
	}
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Returns the list of errors
	 * @return
	 */
	public List<PlasmaDataException> getErrors() {
		if(errors == null)
			errors = new ArrayList<PlasmaDataException>();
		return errors;
	}
	
	/**
	 * Update this data.content.
	 * @param newContent, the newContent to update this data.
	 */
	public void updateContent(String newContent) {
		content = newContent;
	}

	/** Write the content of the data into a file 
	 * 
	 * @param file to write the data
	 * @throws IOException 
	 */
	public void exportTo(File file) throws IOException {
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw= new BufferedWriter(fw);
		bw.write(content);
		bw.close();
	}
	
	/** Return the list of functions that can be applied on this data.
	 *  Empty by default. 
	 */
	public List<AbstractFunction> getFunctions() {
		return new ArrayList<AbstractFunction>(0);
	}
	
	/**
	 * Check the data for errors.
	 * @return true if error were found
	 */
	public abstract boolean checkForErrors();
		
}
