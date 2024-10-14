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

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

import fr.inria.plasmalab.controler.Model;
import fr.inria.plasmalab.controler.Requirement;
import fr.inria.plasmalab.terminal.parameter.ModelConverter;
import fr.inria.plasmalab.terminal.parameter.RequirementConverter;

/**
 * 
 * JCommander Plasma Command
 * 
 * @author ltraonou
 *
 */
public class SimuCommand extends CommonCommand {
    
	public final static String name = "simu";
	
    @Parameter(names = {"-m", "--model"},
            description = "model description must follow this pattern file:type",
            converter = ModelConverter.class,
            required = true)
    private Model model_;
    
    // -r requirement_file:requirement_type
    @Parameter(names = {"-r", "--requirements"},
            description = "requirement description must follow this pattern file:type",
            converter = RequirementConverter.class,
            required = false)
    private List<Requirement> requirements_;
        
    @Parameter(names = {"-h", "--headers"}, 
    		description = "variable that must be observed during the simulations (replace the default headers).",
    		required = false)
    private List<String> headers_;
    
    /**
     * Constructor.
     */
    public SimuCommand(){
    	requirements_ = new ArrayList<Requirement>();
    	headers_ =  new ArrayList<String>();
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
     * @return the headers
     */
    public List<String> getHeaders() {
        return headers_;
    }

	@Override
	public String getName() {
		return name;
	}

}
