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

import com.beust.jcommander.Parameter;

public class ServiceCommand extends CommonCommand {

	public final static String name = "service";

	@Parameter(names = { "-h",
			"--host" }, description = "Address of the host at which the service connects. Default to localhost", required = false)
	private String host_ = "localhost";

	@Parameter(names = { "-p", "--port" }, description = "Port to use. Default to 8111", required = false)
	private Integer port_ = 8111;

	/** Known in the Gui as Threads. */
	@Parameter(names = { "-w", "--workers" }, description = "Number of workers to run simultaneously", required = false)
	private Integer workers_ = 1;
	
	@Override
	public String getName() {
		return name;
	}

	public String getHost() {
		return host_;
	}

	public Integer getWorkers() {
		return workers_;
	}

	public Integer getPort() {
		return port_;
	}

}
