/**
 * This file is part of fr.inria.plasmalab.distributed.
 *
 * fr.inria.plasmalab.distributed is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.distributed is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.distributed.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.distributed.restlet;

import org.restlet.Component;
import org.restlet.data.Protocol;

import fr.inria.plasmalab.distributed.DistributedBay;

public class ExperimentationServerComponent extends Component {
	public ExperimentationServerComponent(int port){
		
		setName("Plasma Lab experimentation manager portal");
		setDescription("Server receiving connection request to help on an experiment.");
		setOwner("INRIA Rennes");
		setAuthor("Plasma Lab");
		getLogService().setLoggerName("fr.inria.plasmalab.distributed.restlet.ExperimentationServerComponent");
		getServers().add(Protocol.HTTP, port);
	}
	
	public void attachApplication(DistributedBay distributedBay){
		getDefaultHost().attachDefault(distributedBay);
	}
}
