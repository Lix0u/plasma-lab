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
package fr.inria.plasmalab.terminal.commandhandler;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import fr.inria.plasmalab.algorithm.data.SMCAlternatives;
import fr.inria.plasmalab.algorithm.data.SMCOption;
import fr.inria.plasmalab.algorithm.data.SMCParameter;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.controler.Controler;
import fr.inria.plasmalab.terminal.command.CommonCommand;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;

/**
 * @author msimonin
 *
 */
public class CommonCommandHandler {

	/** String format to pretty print information.*/
	protected final static String format1 = "| %-40s |%n";
	protected final static String format2 = "| %-20s | %-17s | %s%n";
	protected final static String format3 = "+----------------------+-------------------+%n";

	/** The command.*/
    private CommonCommand command_;
    
    /** The controller.*/
	protected Controler controler_;

	public CommonCommandHandler(CommonCommand command) throws JoranException {
		command_ = command;
        configureLogger(command.getLog());
        controler_ = Controler.createControler(command_.getConf());
	}
	
	protected InterfaceAlgorithmFactory getInterfaceAlgorithm(String algorithm) {
        
        for(InterfaceAlgorithmFactory aaf2:controler_.getAAFList()){
            if(aaf2.getId().equals(algorithm)){
                return aaf2;
            }
        }
		return null;
        
	}
	
	/** Print information about the loaded plugins */
	protected void printPluginsInfo(PrintStream writer) {
		writer.format(format3);
		printAlgorithmsInfo(writer);
		printModelsInfo(writer);
		printRequirementsInfo(writer);
	}
	
	/** Print information about the algorithms */
	protected void printAlgorithmsInfo(PrintStream writer) {
		writer.format(format1, "Algorithms");
		writer.format(format3);
		writer.format(format2, "Name", "ID", "Description");
		writer.format(format3);
		for (InterfaceAlgorithmFactory aaf : controler_.getAAFList()) {
			writer.format(format2, aaf.getName(), aaf.getId(), aaf.getDescription());
		}
		writer.format(format3);
	}
	
	/** Print information about the models */
	protected void printModelsInfo(PrintStream writer) {
		writer.format(format1,"Models");
		writer.format(format3);
		writer.format(format2,"Name","ID", "Description");
		writer.format(format3);
		for (AbstractDataFactory adf : controler_.getADFList()) {
	        if(adf instanceof AbstractModelFactory) {
	        	writer.format(format2, adf.getName(), adf.getId(), adf.getDescription());
	        }
		}
		writer.format(format3);
	}
	
	/** Print information about the requirements */
	protected void printRequirementsInfo(PrintStream writer) {
		writer.format(format1,"Requirements");
		writer.format(format3);
		writer.format(format2,"Name","ID", "Description");
		writer.format(format3);
		for (AbstractDataFactory adf : controler_.getADFList()) {
	        if(adf instanceof AbstractRequirementFactory) {
	        	writer.format(format2, adf.getName(), adf.getId(), adf.getDescription());
	        }
		}
		writer.format(format3);
	}
	
	/** Print information about an algorithm */
    protected void printSingleAlgorithmInfo(InterfaceAlgorithmFactory interfaceAlgorithm, PrintStream writer) {
        writer.println("Algorithm:\t" + interfaceAlgorithm.getName());
        writer.println("ID:\t\t" + interfaceAlgorithm.getId());
        writer.println("Description:\t" + interfaceAlgorithm.getDescription());
        writer.println("Parameters:");

        for (SMCParameter p : interfaceAlgorithm.getParametersList()) {
        	printParameterInfo("  ", p,writer);
        }
    }
	
	/** Print information about a parameter of an algorithm
	 *  @param p SMCParameter 
	 */
	protected void printParameterInfo(String indent, SMCParameter p, PrintStream writer) {
		writer.format("%-30s %s %n", indent + "* " + p.getName(), p.getTip());
		if (p instanceof SMCAlternatives)
			printParameterInfo(indent, (SMCAlternatives) p, writer);
		if (p instanceof SMCOption)
			printParameterInfo(indent, (SMCOption) p, writer);
	}

	/** Print information about a parameter of an algorithm
	 *  @param p SMCAlternatives 
	 */
	protected void printParameterInfo(String indent, SMCAlternatives a, PrintStream writer) {
		for (SMCParameter p : a.getAlternatives())
			printParameterInfo(indent + "  ", p, writer);
		if (a.getNext() != null) {
			printParameterInfo(indent, (SMCParameter) a.getNext(), writer);
		}
	}

	/** Print information about a parameter of an algorithm
	 *  @param p SMCOption 
	 */
	protected void printParameterInfo(String indent, SMCOption o, PrintStream writer) {
		for (SMCParameter p : o.getOptions())
			printParameterInfo(indent + "  ", p, writer);
	}
	
	/**
     * 
     * Configure the logger.
     * If a file is passed with the command line it is loaded
     * otherwise we rely on the level.
     * 
     * @throws JoranException	JoranException
     */
    protected void configureLogger(String logFile) throws JoranException {
    	LoggerContext loggerContext = (LoggerContext) LoggerFactory
                .getILoggerFactory();
    	loggerContext.reset();
    	JoranConfigurator configurator = new JoranConfigurator();
    	configurator.setContext(loggerContext);
    	if (logFile != null) {
    		configurator.doConfigure(new File(logFile));
    	} else {
    		int level = command_.getDebug();
            ClassLoader cl = this.getClass().getClassLoader();
            URL url = cl.getResource(String.format("logback-%s.xml", level));
            configurator.doConfigure(url);
    	}
    	
    }
}
