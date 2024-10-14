/**
 * This file is part of fr.inria.plasmalab.altl.
 *
 * fr.inria.plasmalab.altl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.altl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.altl.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.altl.prismparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.inria.plasmalab.rmlbis.simulator.RMLState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class PRISMReachabilityChecker {

	private RMLState state;
	/** Store the winning states of the reachability analysis */
	private PRISMResult results;
	private List<String> IDsOrdering;
	
	public PRISMReachabilityChecker(RMLState state) {
		this.state = state;
		this.results = new PRISMResult();
		this.IDsOrdering = new ArrayList<String>();
	}
	
	public void check(String property) throws PlasmaCheckerException {
		File modelFile = writeModel();
		if (modelFile == null)
			return;
		File propertyFile = writeProperty(property);
		if (propertyFile == null)
			return;
		
		Process child = process(modelFile,propertyFile);
		if (child == null)
			return;
		
		processOutputs();
		modelFile.delete();
	}
	
	public boolean isWinning(RMLState state) throws PlasmaSimulatorException {
		return results.contains(state, IDsOrdering, 0);
	}
	
	/** Write the PRISM model to a new file 
	 * @throws PlasmaSyntaxException 
	 * @throws PlasmaCheckerException */
	private File writeModel() throws PlasmaCheckerException {	
		File modelFile = null;
		try {
			String content = state.getSystem().toPrism(state.getSystem().getName() + "_", IDsOrdering); 
			modelFile = new File("temp.pm");
 
			// if file doesn't exists, then create it
			if (!modelFile.exists()) {
				modelFile.createNewFile();
			}
 
			FileWriter fw = new FileWriter(modelFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close(); 
		} catch (IOException e) {
			throw new PlasmaCheckerException("IO exception while write PRISM model to file");
		} catch (PlasmaSyntaxException e) {
			throw new PlasmaCheckerException(e);
		}
		return modelFile;
	}
	
	/** Write the property in a file 
	 * @throws PlasmaCheckerException */
	private File writeProperty(String property) throws PlasmaCheckerException {	
		File propertyFile = null;
		try {
			String content = "filter(print,E[F " + property.replace(".","_") + "])"; 
			propertyFile = new File("temp.pctl");
 
			// if file doesn't exists, then create it
			if (!propertyFile.exists()) {
				propertyFile.createNewFile();
			}
 
			FileWriter fw = new FileWriter(propertyFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close(); 
		} catch (IOException e) {
			throw new PlasmaCheckerException("IO exception while write property to file");
		}
		return propertyFile;
	}
	
	/** Run PRISM to check the reachability property 
	 * @throws PlasmaCheckerException */
	private Process process(File modelFile, File propertyFile) throws PlasmaCheckerException {
		Process child = null;
		String execline = System.getProperty("prism_reach") + modelFile.getAbsoluteFile() + " " + propertyFile.getAbsolutePath(); 
		try {
			child = Runtime.getRuntime().exec(execline);
			child.waitFor();
		} catch (IOException e) {
			throw new PlasmaCheckerException("IO exception while running PRISM");
		} catch (InterruptedException e) {
			throw new PlasmaCheckerException("InterruptedException exception while running PRISM");			
		}
		return child;
	}
	
	/** Process the outputs of PRISM 
	 * @throws PlasmaCheckerException */
	private void processOutputs() throws PlasmaCheckerException {
		File outputs = new File("results.txt");
		try {			
			BufferedReader br = new BufferedReader(new FileReader(outputs));
			String line = br.readLine();
			while (line != null) {
				if (line.equals("Satisfying states:")) {
					break;
				}
				line = br.readLine();
			}
			line = br.readLine();
			while (line != null && !line.isEmpty()) {
				processLine(line);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			throw new PlasmaCheckerException("IO exception while processing PRISM results");
		}
	}
	
	/** Process the line to extract the state and add it to the list. */ 
	private void processLine(String line) {
		PRISMResult currentResults = results;
		String[] splits = line.split(":\\(|\\)|\\,");
		for (int i=1; i<splits.length; i++) {
			String s = splits[i];
			if (s.equals("true"))
				currentResults = currentResults.insertValue(1);
			else if (s.equals("false"))
				currentResults = currentResults.insertValue(0);
			else
				currentResults = currentResults.insertValue(Integer.parseInt(s));
		}
	}
	
}
