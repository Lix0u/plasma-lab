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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.data.SMCResult;
import fr.inria.plasmalab.terminal.outputlistener.api.OutputListener;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

/** Only show a single result (the probability)
 * 
 * @author ltraonou
 */
public class ProbaOutputListener extends OutputListener {
    
	/** The logger. */
	final static Logger log_ = LoggerFactory.getLogger(ProbaOutputListener.class);
	  
    /** Keep the results until they are re-published or the algorithm is completed */
    private List<ResultInterface> results;
    
    /**
     * @param output
     * @param progress 
     */
    public ProbaOutputListener(File output, boolean progress, List<String> headers) {
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
        for (ResultInterface result : results) {
        	SMCResult resultSMC = (SMCResult)result;
        	double proba = resultSMC.getPr();
        	writer_.println(proba);
        }
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
