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
package fr.inria.plasmalab.terminal.command;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import fr.inria.plasmalab.controler.Model;
import fr.inria.plasmalab.controler.Requirement;
import fr.inria.plasmalab.terminal.parameter.ModelConverter;
import fr.inria.plasmalab.terminal.parameter.OutputFormat;
import fr.inria.plasmalab.terminal.parameter.OutputFormatConverter;
import fr.inria.plasmalab.terminal.parameter.RequirementConverter;

/**
 * 
 * JCommander Plasma Command
 * 
 * @author msimonin
 *
 */
public class LaunchCommand extends CommonCommand {
    
	public final static String name = "launch";
	
    @Parameter(names = {"-m", "--model"},
            description = "Model description must follow this pattern file:type",
            converter = ModelConverter.class,
            required = true)
    private Model model_;
    
    // TODO handle multiple requirements
    // -r requirement_file:requirement_type
    @Parameter(names = {"-r", "--requirements"},
            description = "requirement description must follow this pattern file:type",
            converter = RequirementConverter.class,
            required = true)
    private List<Requirement> requirements_;
    
	@Parameter(names = {"-a", "--algorithm"}, 
            description = "Algorithm of the experiment.", 
            required = true)
    private String algorithm_;
        
    @DynamicParameter(names = "-A", description = "Algorithm parameters")
    private  Map<String, String> algorithmParameters_;

    @Parameter(names = {"-p", "--port"}, description = "Port to use", 
            required = false)
    private Integer port_ = 8111;
    
    @Parameter(names = {"-b", "--batch"}, description = "Size of the batch for distributed work", 
            required = false)
    private Integer batch_ = 500;
    
    @Parameter(names = {"-q", "--freq"}, description = "Frequency (in ms) at which results are fetched", 
            required = false)
    private Integer frequency = 3000;
    
    /** Known in the Gui as Threads.*/ 
    @Parameter(names = {"-j", "--jobs"}, description = "Number of workers to run simultaneously", 
            required = false)
    private Integer jobs_ = 1;
    
    @Parameter(names = {"-o", "--output"}, description = "File to use as output, default to STDOUT",
            converter = FileConverter.class,
    		required = false)
    private File output_;
    
    @Parameter(names = {"-f", "--format"}, 
            description = "Format of the output, accepted values are text, csv or proba.",
            converter = OutputFormatConverter.class,
            required = false)
    private OutputFormat format_ = OutputFormat.text; 
    
    @Parameter(names = "--progress", description = "show a progress bar", required = false)
    private boolean progress_ = false;
    
    @Parameter(names = {"-h", "--headers"}, 
    		description = "result that must be printed (replace the default headers).",
    		required = false)
    private List<String> headers_;
    
        
    /**
     * Constructor.
     */
    public LaunchCommand(){
        algorithmParameters_ = new HashMap<String, String>();
        requirements_ = new ArrayList<Requirement>();
        headers_ = new ArrayList<String>();
    }
    
	@Override
	public String getName() {
		return name;
	}

    /**
     * @return the model
     */
    public Model getModel() {
        return model_;
    }

    /**
     * @return the requirements
     */
    public List<Requirement> getRequirements() {
        return requirements_;
    }
    
    /**
     * @return the algorithm
     */
	public String getAlgorithm() {
		return algorithm_;
	}
    
    /**
     * @return the algorithmParameters
     */
    public Map<String, String> getAlgorithmParameters() {
        return algorithmParameters_;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port_;
    }
    
    /**
     * @return the batch
     */
    public Integer getBatch() {
        return batch_;
    }
    
    /**
     * @return the beat frequency
     */
    public Integer getFrequency() {
        return batch_;
    }
    
    /**
     * @return the output
     */
    public File getOutput() {
        return output_;
    }
    
    /**
     * @return the format
     */
    public OutputFormat getFormat() {
        return format_;
    }

    /**
     * @return the progress
     */
    public boolean isProgress() {
        return progress_;
    }

    /**
     * @return the jobs
     */
    public Integer getJobs() {
        return jobs_;
    }
	
	 /**
     * @return the algorithmParameters weakly typed.
     */
    public Map<String, Object> getAlgorithmParametersWeak() {
        Map<String, Object> params = new HashMap<String, Object>();
        for(Entry<String, String> entry : algorithmParameters_.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        return params;
    }
    
    /** 
     * @return the headers that must be printed instead of the normal result header
     */
    public List<String> getHeaders() {
    	return headers_;
    }

	

}
