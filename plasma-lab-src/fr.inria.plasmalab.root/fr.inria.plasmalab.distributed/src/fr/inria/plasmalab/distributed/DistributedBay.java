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
package fr.inria.plasmalab.distributed;

import java.util.List;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.distributed.restlet.ExperimentationServerComponent;
import fr.inria.plasmalab.distributed.restlet.server.RootServerResource;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;

public class DistributedBay  extends Application{
	
	/** The logger. */
	final static Logger logger = LoggerFactory.getLogger(DistributedBay.class);
	private ExperimentationServerComponent esc;
	private InterfaceAlgorithmScheduler algorithm;
	private AbstractModel model;
	private List<AbstractRequirement> requirements;
	
	private List<InterfaceAlgorithmFactory> iafList;
	
	private static DistributedBay singleton;
	
	private int port;
	private boolean running;

	public String getModelId(){
		return model.getId();
	}
	public String getModelContent(){
		return model.getContent();
	}
	public String getRequirementId(int nb){
		return requirements.get(nb).getId();
	}
	public String getRequirementContent(int nb){
		return requirements.get(nb).getContent();
	}
	public String getRequirementName(int nb){
		return requirements.get(nb).getName();
	}
	public int getRequirementsNb(){
		return requirements.size();
	}
	public String getAlgorithmId(){
		return algorithm.getNodeURI();
	}

	public static DistributedBay getDistributedBay(List<InterfaceAlgorithmFactory> iafList){
		if (singleton == null){
			singleton = new DistributedBay(iafList);
		}
		return singleton;
	}
	
	private DistributedBay(List<InterfaceAlgorithmFactory> iafList){
		setName("Plasma Lab experimentation manager portal");
		setDescription("Server receiving connection request to help on an experiment.");
		setOwner("INRIA Rennes");
		setAuthor("Plasma Lab");
		running = false;
		port = -1;
		this.iafList = iafList;
	}
	
	public void setExperiment(AbstractModel model, List<AbstractRequirement> requirements,
			InterfaceAlgorithmScheduler algorithm){
		this.model = model;
		this.requirements = requirements;
		this.algorithm = algorithm;
	}
	
	public void startServer(int port){
		try {
			//Setup server if not running
			if(!running){
				esc = new ExperimentationServerComponent(port);
				esc.attachApplication(this);
				this.port = port;
			}
			//Stop server if port changed
			else if(this.port != port){
				stopServer();
				logger.debug("Changing listening port to "+port+".");
				esc = new ExperimentationServerComponent(port);
				esc.attachApplication(this);
				this.port = port;
			}
			//Set context
			setContext(esc.getContext().createChildContext());
			getContext().getAttributes().put(algorithm.getNodeURI(), algorithm);
			
			//Start server if not running
			if(!running){
				esc.start();
				running = true;
			}

			try {
				esc.updateHosts();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopServer(){
		try {
			esc.stop();
			running = false;
			logger.debug("Disconnect");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public Restlet createInboundRoot(){
		logger.debug("inbound root created");
		Router router = new Router(getContext());
		router.attach("/", RootServerResource.class);
		attachAlgorithmToRouter(router);
		return router;
	}
	
	@SuppressWarnings("unchecked")
	private void attachAlgorithmToRouter(Router router){
		if(algorithm!=null){
			//Setup algorithm handler
			for(InterfaceAlgorithmFactory iaf :iafList){
				try{
					Class<?> handler = iaf.getResourceHandler();
					if(handler!=null && ServerResource.class.isAssignableFrom(handler))
						router.attach("/"+iaf.getId()+"/", (Class<? extends ServerResource>) handler);
				}catch(Exception e){
					logger.error("Error during deployment of "+iaf.getId()+" resource handler.");
					e.printStackTrace();
				}
			}
		}
	}
}
