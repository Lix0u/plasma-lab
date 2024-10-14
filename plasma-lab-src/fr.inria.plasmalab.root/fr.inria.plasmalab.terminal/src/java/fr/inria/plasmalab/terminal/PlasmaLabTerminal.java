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
package fr.inria.plasmalab.terminal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.MissingCommandException;
import com.beust.jcommander.ParameterException;

import ch.qos.logback.core.joran.spi.JoranException;
import fr.inria.plasmalab.terminal.command.CommonCommand;
import fr.inria.plasmalab.terminal.command.InfoCommand;
import fr.inria.plasmalab.terminal.command.LaunchCommand;
import fr.inria.plasmalab.terminal.command.ServiceCommand;
import fr.inria.plasmalab.terminal.command.SimuCommand;
import fr.inria.plasmalab.terminal.commandhandler.InfoCommandHandler;
import fr.inria.plasmalab.terminal.commandhandler.LaunchCommandHandler;
import fr.inria.plasmalab.terminal.commandhandler.ServiceCommandHandler;
import fr.inria.plasmalab.terminal.commandhandler.SimuCommandHandler;

/**
 * 
 * Command line interface for PlasmaLab
 * 
 * @author msimonin
 *
 */

public class PlasmaLabTerminal {
	/** The logger. */
	final static Logger log_ = LoggerFactory.getLogger(LaunchCommandHandler.class);
	
	/**
	 * Main method.
	 * 
	 * @param args
	 *            Arguments
	 */
	public static void main(String[] args) {

		JCommander jcommander = new JCommander();
		LaunchCommand launchCommand = new LaunchCommand();
		jcommander.addCommand(LaunchCommand.name, launchCommand);
		SimuCommand simuCommand = new SimuCommand();
		jcommander.addCommand(SimuCommand.name, simuCommand);
		InfoCommand infoCommand = new InfoCommand();
		jcommander.addCommand(InfoCommand.name, infoCommand);
		ServiceCommand serviceCommand = new ServiceCommand();
		jcommander.addCommand(ServiceCommand.name, serviceCommand);
		try {
			jcommander.parse(args);
			if (jcommander.getParsedCommand() == null) {
				throw new MissingCommandException("Missing command");
			}
			if (jcommander.getParsedCommand().equals(LaunchCommand.name)) {
				checkHelp(jcommander, launchCommand);
				LaunchCommandHandler launchCommandHandler = new LaunchCommandHandler(launchCommand);
				launchCommandHandler.dispatchCommand();
				System.exit(0);
			} else if (jcommander.getParsedCommand().equals(SimuCommand.name)) {
				checkHelp(jcommander, simuCommand);
				SimuCommandHandler simuCommandHandler = new SimuCommandHandler(simuCommand);
				simuCommandHandler.dispatchCommand();
			} else if (jcommander.getParsedCommand().equals(InfoCommand.name)) {
				checkHelp(jcommander, infoCommand);
				InfoCommandHandler infoCommandHandler = new InfoCommandHandler(infoCommand);
				infoCommandHandler.dispatchCommand();
			} else if (jcommander.getParsedCommand().equals(ServiceCommand.name)) {
				checkHelp(jcommander, serviceCommand);
				ServiceCommandHandler ServiceCommandHandler = new ServiceCommandHandler(serviceCommand);
				ServiceCommandHandler.dispatchCommand();
			} else {
				throw new MissingCommandException("Wrong command");
			}
		} catch (MissingCommandException e) {
			System.err.println(e.getMessage());
			displayAvailableCommand(jcommander);
		} catch (ParameterException parameterException) {
			System.err.println("Wrong parameter description : " + parameterException.getMessage());
			if(jcommander.getParsedCommand() != null)
				jcommander.usage(jcommander.getParsedCommand());
			else {
				System.err.println("Missing command");
				displayAvailableCommand(jcommander);
			}
		} catch (JoranException e) {
			log_.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private static void displayAvailableCommand(JCommander jcommander) {
		System.out.println("Available commands are : ");
		for (String command : jcommander.getCommands().keySet()) {
			System.out.println("\t" + command);
		}

	}

	private static void checkHelp(JCommander jcommander, CommonCommand command) {
		if (command.isHelp()) {
			jcommander.usage(command.getName());
			System.exit(0);
		}
	}
}
