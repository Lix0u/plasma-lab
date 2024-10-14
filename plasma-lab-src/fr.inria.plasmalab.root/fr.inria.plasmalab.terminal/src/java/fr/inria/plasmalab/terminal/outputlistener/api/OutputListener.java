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
package fr.inria.plasmalab.terminal.outputlistener.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.workflow.listener.ExperimentationListener;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

/**
 * @author msimonin
 *
 */
public abstract class OutputListener implements ExperimentationListener {

	final static Logger log_ = LoggerFactory.getLogger(OutputListener.class);

    /** The writer to use.*/
    protected PrintWriter writer_;
    
    /** progress indicator.*/
    boolean progress_ = false;
    
    /** headers of the result.*/
    protected List<String> headers_;
    
    /**
     * @param output
     * @param progress 
     */
    public OutputListener(File output, boolean progress, List<String> headers) {
        progress_ =  progress;
        headers_ = headers;
        if (output == null) {
            writer_ = new PrintWriter(System.out);
        }
        else {
            try {
                writer_ = new PrintWriter(output);
            } catch (FileNotFoundException e) {
               log_.error(e.getMessage() + ". Using standard output.");
               writer_ = new PrintWriter(System.out);
            }
        }
    }


    /**
     * Closes the listener.
     * Typically close output writer.
     */
    public abstract void close();
    
    
    /**
     * Publish result callback
     */
    public abstract void publishResults(String nodeURI, List<ResultInterface> results);
    
    /**
     * 
     */
    public abstract void notifyAlgorithmCompleted(String nodeURI);
    
    
    /* (non-Javadoc)
     * @see fr.inria.plasmalab.workflow.listener.ExperimentationListener#notifyAlgorithmStarted(java.lang.String)
     */ 
    @Override
    public void notifyAlgorithmStarted(String nodeURI) {
        // TODO Auto-generated method stub

    }

    

    /* (non-Javadoc)
     * @see fr.inria.plasmalab.workflow.listener.ExperimentationListener#notifyAlgorithmStopped(java.lang.String)
     */
    @Override
    public void notifyAlgorithmStopped(String nodeURI) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see fr.inria.plasmalab.workflow.listener.ExperimentationListener#notifyAlgorithmError(java.lang.String, java.lang.String)
     */
    @Override
    public void notifyAlgorithmError(String nodeURI, String errorMessage) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see fr.inria.plasmalab.workflow.listener.ExperimentationListener#notifyNewServiceConnected(java.lang.String)
     */
    @Override
    public void notifyNewServiceConnected(String nodeURI) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see fr.inria.plasmalab.workflow.listener.ExperimentationListener#notifyServiceDisconnected(java.lang.String)
     */
    @Override
    public void notifyServiceDisconnected(String nodeURI) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see fr.inria.plasmalab.workflow.listener.ExperimentationListener#notifyProgress(int)
     * 
     * Display a progress bar : http://nakkaya.com/2009/11/08/command-line-progress-bar/
     * Displayed only if output isn't on stdout.
     */
    @Override
    public void notifyProgress(int percent) {
        if (!progress_){
            return;
        }
        StringBuilder bar = new StringBuilder("[");

        for(int i = 0; i < 50; i++){
            if( i < (percent/2)){
                bar.append("=");
            }else if( i == (percent/2)){
                bar.append(">");
            }else{
                bar.append(" ");
            }
        }

        bar.append("]   " + percent + "%     ");
        System.err.print("\r" + bar.toString());
    }

    /* (non-Javadoc)
     * @see fr.inria.plasmalab.workflow.listener.ExperimentationListener#notifyTimeRemaining(long)
     */
    @Override
    public void notifyTimeRemaining(long milliseconds) {
        // TODO Auto-generated method stub

    }
    
}
