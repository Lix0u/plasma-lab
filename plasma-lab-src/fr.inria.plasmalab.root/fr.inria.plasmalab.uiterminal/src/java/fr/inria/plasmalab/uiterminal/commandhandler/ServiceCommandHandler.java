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

import ch.qos.logback.core.joran.spi.JoranException;
import fr.inria.plasmalab.service.ServiceWindow;
import fr.inria.plasmalab.terminal.commandhandler.CommonCommandHandler;
import fr.inria.plasmalab.uiterminal.command.ServiceCommand;


public class ServiceCommandHandler extends CommonCommandHandler {

	private ServiceCommand serviceCommand_;

	public ServiceCommandHandler(ServiceCommand command) throws JoranException {
		super(command);
		serviceCommand_ = command;
	}

	public void dispatchCommand() {
		ServiceWindow sw = new ServiceWindow(controler_);
		sw.setVisible(true);
	}

}
