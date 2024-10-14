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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.inria.plasmalab.workflow.data.AbstractData;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

public class TextData extends AbstractData {
	
	private boolean placeHolder;
	
	public TextData(String name, File file, String id) throws PlasmaDataException {
		this.name = name;
		this.id = id;
		content = "";
		try {
			FileReader fr = new FileReader(file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				content = content+br.readLine()+"\n";
			br.close();
		} catch (IOException e) {
			throw new PlasmaDataException(e);
		}
		//content.substring(0, content.length() - 2);
		this.origin = file;
		
		this.placeHolder = false;
		errors = new ArrayList<PlasmaDataException>();
	}
	
	public TextData(String name, String content, String id){
		this.placeHolder = false;
		this.name = name;
		this.id = id;
		this.content = content;
		errors = new ArrayList<PlasmaDataException>();
	}
	
	public TextData(String name, String content, String id, PlasmaDataException error){
		this.placeHolder = true;
		errors = new ArrayList<PlasmaDataException>();
		errors.add(error);
		this.name = name;
		this.id = id;
		this.content = content;
	}
	
	public TextData(String name, File file, String id, PlasmaDataException error) throws PlasmaDataException {
		this.placeHolder = true;
		errors = new ArrayList<PlasmaDataException>();
		errors.add(error);
		this.origin = file;
		this.name = name;
		this.id = id;
		content = "";
		try {
			FileReader fr = new FileReader(file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				content = content+br.readLine()+"\n";
			br.close();
		} catch (IOException e) {
			throw new PlasmaDataException(e);
		}
		//content.substring(0, content.length() - 2);
	}
	
	public boolean isPlaceHolder(){
		return placeHolder;
	}

	@Override
	public boolean checkForErrors() {
		return placeHolder;
	}

	@Override
	public List<PlasmaDataException> getErrors() {
		return errors;
	}
}
