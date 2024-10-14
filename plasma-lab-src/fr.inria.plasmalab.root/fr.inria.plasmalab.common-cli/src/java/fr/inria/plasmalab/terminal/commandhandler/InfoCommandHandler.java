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

import ch.qos.logback.core.joran.spi.JoranException;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.terminal.command.InfoCommand;

public class InfoCommandHandler extends CommonCommandHandler {

	private InfoCommand infoCommand_;

	public InfoCommandHandler(InfoCommand infoCommand) throws JoranException {
		super(infoCommand);
		infoCommand_ = infoCommand;
	}

	public void dispatchCommand() {

		
        /** If no algorithms given, 
         * 	we display some general information.
         * else we display the algorithm information.*/
        if (infoCommand_.getAlgorithm() == null) {
            printPluginsInfo(System.out);
        } else {
        	InterfaceAlgorithmFactory interfaceAlgorithm = getInterfaceAlgorithm(infoCommand_.getAlgorithm());
        	if (interfaceAlgorithm == null) {
    			System.err.println("Wrong algorithm ID: " + infoCommand_.getAlgorithm() + ". Check algorithms ID list:");
    			System.err.format(format3);
    			printAlgorithmsInfo(System.err);
        	} else {
        		printSingleAlgorithmInfo(interfaceAlgorithm, System.out);
        	}
        }	
	}
}
