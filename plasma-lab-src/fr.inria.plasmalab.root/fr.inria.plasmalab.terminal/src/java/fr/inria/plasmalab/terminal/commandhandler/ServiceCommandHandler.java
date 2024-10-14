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
package fr.inria.plasmalab.terminal.commandhandler;

import java.util.List;

import ch.qos.logback.core.joran.spi.JoranException;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.distributed.Service;
import fr.inria.plasmalab.terminal.command.ServiceCommand;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;

public class ServiceCommandHandler extends CommonCommandHandler {

	/** The service command.*/
	private ServiceCommand serviceCommand_;

	public ServiceCommandHandler(ServiceCommand serviceCommand) throws JoranException {
		super(serviceCommand);
		serviceCommand_ = serviceCommand;
	}
	
	/**
	 * Dispatches the client command.
	 *  
	 */
	public void dispatchCommand() {
		String host = serviceCommand_.getHost();
    	int port = serviceCommand_.getPort();
		int nbWorkers = serviceCommand_.getWorkers();
		
		List<InterfaceAlgorithmFactory> aaf = controler_.getAAFList();
		List<AbstractDataFactory> adf = controler_.getADFList();
		for (int t = 0; t < nbWorkers; t++) {
			Service.startService(host, String.valueOf(port), "/", adf, aaf);
		}
	}

}
