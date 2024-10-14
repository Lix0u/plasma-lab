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
package fr.inria.plasmalab.terminal.outputlistener.api.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.terminal.outputlistener.api.OutputListener;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

/**
 * @author ltraonou
 *
 *
 *
 */
public class TextOutputListener extends OutputListener {
        
	
	/** The logger. */
	final static Logger log_ = LoggerFactory.getLogger(TextOutputListener.class);
			
    /** Keep the results until they are re-published or the algorithm is completed */
    private List<ResultInterface> results;
    
    /**
     * @param output
     * @param progress 
     */
    public TextOutputListener(File output, boolean progress, List<String> headers) {
    	super(output, progress, headers);
    }

    @Override
    public void publishResults(String nodeURI, List<ResultInterface> results) {
    	this.results = results;
    }
  
    @Override
    public void notifyAlgorithmCompleted(String nodeURI) {
    	if (results.isEmpty())
    		return;
    	if (headers_.isEmpty()) {
    		headers_ = new ArrayList<String>(results.get(0).getHeaders().length);
    		for (InterfaceIdentifier id: results.get(0).getHeaders())
    			headers_.add(id.getName());
    	}	
    	
    	// compute the size of the columns
    	int[] columns = new int[headers_.size()+1];
    	columns[0] = 4; // size of "Name"
    	for (int i = 1; i < columns.length; i++) {
			columns[i] = headers_.get(i-1).length();
		}
    	for (ResultInterface result : results) {
    		String[] parts = result.getCategory().split("/");	// TODO: split windows name as well
    		columns[0] = Math.max(columns[0],parts[parts.length-1].length());
        	for (int i = 1; i < columns.length; i++) {
        		Object val;
				try {
					val = result.getValueOf(headers_.get(i-1));
				} catch (PlasmaExperimentException e) {
					log_.warn("No value found for header ID: " + headers_.get(i-1));
					val = new String("error");
				}
        		columns[i] = Math.max(columns[i], val.toString().length());
        	}
    	}
    	
    	// format the columns
    	String format = "";
    	String separationLine = "";
    	for (int i = 0; i < columns.length; i++) {
			format += "| %-" + columns[i] + "s ";
			char[] array = new char[columns[i]+2];
		    Arrays.fill(array, '-');
			separationLine += "+" + new String(array);
		}
    	format += "|%n";
    	separationLine += "+";
    	
    	// print the headers from the first result
    	writer_.println("");
    	writer_.println(separationLine);
    	Object[] args = new Object[columns.length];
    	args[0] = "Name";
    	for (int i = 1; i < columns.length; i++) {
    		args[i] = headers_.get(i-1);
    	}
    	writer_.format(format, args);
    	writer_.println(separationLine);

    	// print the results
    	for (ResultInterface result : results) {
    		String[] parts = result.getCategory().split("/");	// TODO: split windows name as well
    		args[0] = parts[parts.length-1]; 
        	for (int i = 1; i < columns.length; i++) {
        		try {
					args[i] = result.getValueOf(headers_.get(i-1));
				} catch (PlasmaExperimentException e) {
					log_.warn("No value found for header ID: " + headers_.get(i-1));
					args[i] = new String("error");
				}
        	}
        	writer_.format(format, args);
    	}
    	writer_.println(separationLine);
        writer_.flush();
    }

    /* (non-Javadoc)
     * @see fr.inria.plasmalab.terminal.OutputListener#close()
     */
    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

   

}
