/**
 * This file is part of fr.inria.plasmalab.llvm :: LLVM plugin.
 *
 * fr.inria.plasmalab.llvm :: LLVM plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.llvm :: LLVM plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.llvm :: LLVM plugin.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.csvsimulator;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;
import fr.inria.plasmalab.workflow.concrete.GenericIdentifier;
import fr.inria.plasmalab.workflow.concrete.GenericState;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDeadlockException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class CSVSimulator extends AbstractModel {
    
	final static Logger logger = LoggerFactory.getLogger(CSVSimulator.class);
	
	private final static char SEPARATOR = ',';
    
	/** Array of identifiers in the order given in the csv file*/
    private InterfaceIdentifier[] identifiers;
    /** Identifier for continuous time */
    private InterfaceIdentifier timeId;
    /** Current state */
	private GenericState currentState;
	/** List of states of the current simulation */
	private List<InterfaceState> trace;
	/** -1 if no deadlock.
	 *  Size of the trace otherwise.
	 */
	private int deadlockPos;
	
	
	/** List of csv files. Each one of them stores a trace. */
	private List<File> csvfiles;
	/** Index of the current csv file being read */
	private int currentFile;
	/** Random generator for selecting file; null if iterating over all the files. */
	private Random rand;
	/** Reader for the current csv file */
	private CSVReader csvReader;
	
    public CSVSimulator(String name, String content, String id) {
		this.name = name;
		this.id = id;
		this.content = content;
		this.origin = null;
		this.errors = new ArrayList<PlasmaDataException>();
	}
	
	public CSVSimulator(String name, File file, String id) throws PlasmaDataException {
		this.name = name;
		this.id = id;
		this.content = "";
		this.origin = file;
		
		try {
			FileReader fr = new FileReader(file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				content = content+br.readLine()+"\n";
			br.close();
		} catch (FileNotFoundException e) {
			throw new PlasmaDataException(e);
		} catch (IOException e) {
			throw new PlasmaDataException(e);
		}  
		this.errors = new ArrayList<PlasmaDataException>();
	}
	
	/** Construct the list of csv of files from the content:
	 *  - either content is a path to a directory: each files ending with .csv is used for the simulations
	 *  - or content is a list of path to csv files
	 */
	@Override
	public boolean checkForErrors() {
		errors.clear();
		csvfiles = new ArrayList<File>();
		currentFile = -1;
		identifiers = null;
		
		InputStream is = new ByteArrayInputStream(content.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		check: {
			try {
				// read first line (random or all)
		    	String line = br.readLine();
		    	if (line != null) {
		    		if (line.equals("random"))
		    			rand = new Random();
		    		else if (line.equals("all"))
			    		rand = null;
		    		else {
			    		errors.add(new PlasmaSyntaxException("No parameter \"random\" or \"all\" at the begining of the model"));
			    		break check;
			    	}
		    	}
		    	else {
		    		errors.add(new PlasmaSyntaxException("Empty model."));
		    		break check;
		    	}
		    	// read second line
		    	line = br.readLine();
		    	if (line == null) {
		    		errors.add(new PlasmaSyntaxException("Empty model: no path to csv files"));
		    		break check;
		    	}
		    	File f = new File(line);
		    	if (f.isDirectory()) {
		    		// list all csv files from the directory
		    		File[] listOfFiles = f.listFiles(new FilenameFilter() {
		    		    public boolean accept(File dir, String name) {
		    		        return name.toLowerCase().endsWith(".csv");
		    		    }
		    		});
		    		csvfiles = Arrays.asList(listOfFiles);
		    	}
		    	else {
		    		do {
		    			f = new File(line);
		    			if (f.exists())
		    				csvfiles.add(f);
		    			else
		    				logger.warn("File: " + line + " does not exist. Skipped.");
		    			// read other lines
		    			line = br.readLine();
		    		} while (line != null);
		    	}
		    	if (csvfiles.isEmpty()) {
		    		errors.add(new PlasmaSyntaxException("Empty model: no csv file found"));
		    		break check;
		    	}
		    	// read the identifiers from the first file
		    	File file = csvfiles.get(0);
				logger.debug("Reading identifiers from csv file: " + file.getPath());
		        FileReader reader = new FileReader(file);
				csvReader = new CSVReader(reader, SEPARATOR);
				readIdentifiers();
			} catch (IOException e) {
				errors.add(new PlasmaDataException(e));
			} catch (PlasmaDataException e) {
				errors.add(e);
			}
		}
		return !errors.isEmpty();
	}
	
	/** Start a new simulation from a csv file
	 */
	@Override
	public InterfaceState newPath() throws PlasmaSimulatorException {
		if(rand != null)
			currentFile = rand.nextInt(csvfiles.size());
		else
			currentFile = currentFile < csvfiles.size()-1 ? currentFile+1 : 0;
		deadlockPos = 1000000;
		try {
			File file = csvfiles.get(currentFile);
			logger.debug("New path from csv file: " + file.getPath());
	        FileReader reader = new FileReader(file);
			csvReader = new CSVReader(reader, SEPARATOR);
			csvReader.readNext();  // skip the first line
	        currentState = readState();
		} catch (IOException e) {
			throw new PlasmaSimulatorException(e);
		}
        trace = new ArrayList<InterfaceState>();
		trace.add(currentState);
		return currentState;
	}

	@Override
	public InterfaceState newPath(List<InterfaceState> initTrace) throws PlasmaSimulatorException {
		return newPath();
	}

	@Override
	public InterfaceState newPath(long seed) throws PlasmaSimulatorException {
		return newPath();
	}

	/** Simulate a new state form the current state. */
	@Override
	public InterfaceState simulate() throws PlasmaSimulatorException {
		currentState = readState();
		trace.add(currentState);
		return currentState;
	}

	@Override
	public void backtrack() throws PlasmaSimulatorException {
		currentState = (GenericState) trace.get(trace.size()-1);
		trace.remove(trace.size()-1);
		
		// restore the reader
		try {
			File file = csvfiles.get(currentFile);
	        FileReader reader = new FileReader(file);
	        csvReader = new CSVReader(reader, SEPARATOR);
	        for (int i = 1; i <= trace.size()+1; i++) {
	        	csvReader.readNext();
	        }
		} catch (IOException e) {
			throw new PlasmaSimulatorException(e);
		}
	}

	@Override
	public void cut(int stateNb) throws PlasmaSimulatorException {
		if(stateNb >= trace.size())
			throw new PlasmaSimulatorException("Cannot cut at a position superior than trace length.");
		for(int i=0; i<stateNb; i++ )
			trace.remove(0);
	}

	/** Load a previous state. It will be the current state of the simulator for the next simulations */
	@Override
	public void setValueOf(Map<InterfaceIdentifier, Double> update) throws PlasmaSimulatorException {
		throw new PlasmaSimulatorException("setValueOf is not supported by the CSV simulator.");

	}
	
	@Override
	public Map<String, InterfaceIdentifier> getIdentifiers() {
		 Map<String, InterfaceIdentifier> ret = new HashMap<String, InterfaceIdentifier>(identifiers.length);
		 for (InterfaceIdentifier id : identifiers) {
			 ret.put(id.getName(), id);
		 }
		return ret;
	}
	
	@Override
	public InterfaceIdentifier[] getHeaders() {
		return identifiers;
	}

	@Override
	public InterfaceState getCurrentState() {
		return currentState;
	}

	@Override
	public InterfaceState getStateAtPos(int pos) {
		if (pos > deadlockPos) {
			return trace.get(deadlockPos);
		}
		return trace.get(pos);
	}

	@Override
	public List<InterfaceState> getTrace() {
		return trace;
	}

	@Override
	public int getDeadlockPos() {
		return deadlockPos;
	}

	@Override
	public InterfaceIdentifier getTimeId() {
		return timeId;
	}

	@Override
	public boolean hasTime() {
		return timeId != null;
	}

	@Override
	public List<InterfaceIdentifier> getStateProperties() {
		return new ArrayList<InterfaceIdentifier>(0);
	}


	// Private methods *********************************************************************************** 

	/** Read the identifiers and store from the current csv file 
	 * @throws IOException 
	 * @throws PlasmaSimulatorException */
	private void readIdentifiers() throws PlasmaDataException {
		timeId = null;
		String[] nextLine;
		try {
			nextLine = csvReader.readNext();
		}
		catch(IOException e) {
        	throw new PlasmaDataException(e);
		}
        if (nextLine == null)
        	throw new PlasmaDataException("Empty csv file");
        
        int size = nextLine.length;
        identifiers = new InterfaceIdentifier[size];
        for (int i = 0; i < nextLine.length; i++) {
        	String name = nextLine[i].trim();
        	if (name.isEmpty())
        		name = "field_" + i;
        	GenericIdentifier id = new GenericIdentifier(name);
        	if (name.equals("Time") || name.equals("time"))
            	timeId = id;   			
			identifiers[i] = id;
        }
	}
	
	/** Read the next state from csv file 
	 * @throws IOException 
	 * @throws PlasmaSimulatorException */
	private GenericState readState() throws PlasmaSimulatorException {
        String[] nextLine;
		try {
			nextLine = csvReader.readNext();
        }
        catch(IOException e) {
        	throw new PlasmaSimulatorException(e);
		}
        if (nextLine == null || (nextLine.length==1 && nextLine[0].trim().isEmpty())) {
			deadlockPos = trace.size()-1;
			logger.warn("Deadlock at line" + this.currentState.getValueOf(timeId));
        	throw new PlasmaDeadlockException(currentState, trace.size());
        }	
        if (nextLine.length != identifiers.length)
        	logger.warn("A state has not the same number of values (" + nextLine.length + ") as the number of identifiers (" + identifiers.length + ").");

        HashMap<InterfaceIdentifier, Double> newValues = new HashMap<InterfaceIdentifier, Double>(Math.min(nextLine.length, identifiers.length));
        for (int i = 0; i < Math.min(nextLine.length, identifiers.length); i++) {
        	if (nextLine[i].trim().isEmpty()) {
        		newValues.put(identifiers[i], 0.0);
        	} else {
        		try {
        			double value = Double.parseDouble(nextLine[i]);
        			newValues.put(identifiers[i], value);
        		}
        		catch(NumberFormatException e) {
        			throw new PlasmaSimulatorException("Cannot parse value:", e); 
        		}
        	}
        }
    	// Construct the new State		
		return new GenericState(newValues); 
	}
	
	
}
