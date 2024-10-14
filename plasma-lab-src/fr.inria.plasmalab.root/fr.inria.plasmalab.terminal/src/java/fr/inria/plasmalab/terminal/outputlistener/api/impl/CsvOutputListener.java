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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.terminal.outputlistener.api.OutputListener;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

/**
 * @author msimonin
 *
 *
 *
 */
public class CsvOutputListener extends OutputListener {
    
	
	/** The logger. */
	final static Logger log_ = LoggerFactory.getLogger(CsvOutputListener.class);
	  
    /** Separator to use.*/
    private final static String separator_ = ",";
  
    /** Keep the results until they are re-published or the algorithm is completed */
    private List<ResultInterface> results;
    
    /**
     * @param output
     * @param progress 
     */
    public CsvOutputListener(File output, boolean progress, List<String> headers) {
    	super(output, progress, headers);
    }

    /* (non-Javadoc)
     * @see fr.inria.plasmalab.workflow.listener.ExperimentationListener#publishResults(java.lang.String, java.util.List)
     *
     *
     */
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
    	  	
    	// configure headers line
    	String headers_line = "\n --- \n";
    	headers_line += "Name";
        for (String h : headers_){
        	headers_line += separator_;
        	headers_line += h;
        }

        // configure results lines
        String result_line = "";
        for (ResultInterface result : results) {
        	result_line += result.getCategory();
            for (String h : headers_){
            	result_line += separator_;
                try {
                	result_line += result.getValueOf(h);
				} catch (PlasmaExperimentException  e) {
					log_.warn("No value found for header: " + h);
				}
            }
            result_line += "\n";
        }
        
        writer_.println(headers_line);
        writer_.println(result_line);
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
