/**
 * This file is part of fr.inria.plasmalab.common-cli.
 *
 * fr.inria.plasmalab.common-cli is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.common-cli is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.common-cli.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.terminal.command;

import java.io.File;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;


public abstract class CommonCommand {
	
	
    @Parameter(names = {"--help"}, description = "Print help.", help = true)
    private boolean help_;
    
    @Parameter(names = {"--conf"}, 
    		description = "Path to Plasma Lab configuration file.",
            converter = FileConverter.class,
    		required = false)    
    private File conf_;
    
    @Parameter(names = {"-d", "--debug"}, 
    		description = "Debug level", 
    		required = false,
    		validateWith = ValidDebugLevel.class)
    private Integer debug = 0;
    
    @Parameter(names = {"--log"},
    		description = "Use a logback xml file",
    		required = false)
    private String log_;	
    
	public abstract String getName();
	
	public String getLog() {
		return log_;
	}
    
	public boolean isHelp() {
		return help_;
	}

	public File getConf() {
	    return conf_;
	}
	 
    public Integer getDebug() {
		return debug;
	}

	
	
}
