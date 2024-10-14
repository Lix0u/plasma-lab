/**
 * This file is part of fr.inria.plasmalab.uiterminal.
 *
 * fr.inria.plasmalab.uiterminal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.uiterminal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.uiterminal.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.uiterminal.commandhandler;

import java.awt.EventQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.spi.JoranException;
import fr.inria.plasmalab.terminal.commandhandler.CommonCommandHandler;
import fr.inria.plasmalab.ui.PlasmaLabGUI;
import fr.inria.plasmalab.uiterminal.command.LaunchCommand;

public class LaunchCommandHandler extends CommonCommandHandler {

	final static Logger log_ = LoggerFactory.getLogger(LaunchCommandHandler.class);

	public LaunchCommandHandler(LaunchCommand launchCommand) throws JoranException {
		super(launchCommand);
	}

	public void dispatchCommand() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlasmaLabGUI window = new PlasmaLabGUI(controler_);
					window.initialize();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
