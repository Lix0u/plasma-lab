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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.restlet.Client;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmWorker;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.distributed.heartbeat.client.HeartbeatProducer;
import fr.inria.plasmalab.distributed.restlet.common.RootResource;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractModelFactory;
import fr.inria.plasmalab.workflow.data.factory.AbstractRequirementFactory;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;

/**
 * 
 * @author kevin.corre@inria.fr
 * 
 *         The service class is the main entry point of Plasma Lab Service.
 * 
 *         Plasma Lab Service is the distributed component of Plasma Lab and
 *         will connect to a Plasma Lab Controler in order to run and check
 *         model and requirement using algorithm. *
 */
public class Service implements Runnable {
	
	private static int nbService = 0;
	private static Client client; 
	private static Lock serviceLock;

	/** The logger. */
	final static Logger logger = LoggerFactory.getLogger(Service.class);
	private List<AbstractDataFactory> adfList;
	private List<InterfaceAlgorithmFactory> aafList;
	private HeartbeatProducer HBP;
	
	private String host, port, rootURI; // nodeURI is always null
	
	public static void startService(String host, String port, String rootURI,
			List<AbstractDataFactory> adfList, List<InterfaceAlgorithmFactory> aafList) {
		if (client == null)
			client = new Client(Protocol.HTTP);
		Service service = new Service(host, port, rootURI, adfList, aafList);
		nbService++;
		serviceLock = new ReentrantLock();
		//ThreadGroup serviceGroup = new ThreadGroup("serviceGroup-" + nbService);
		Thread t = new Thread(service);
		t.start();
	}
	
	private Service(String host, String port, String rootURI, List<AbstractDataFactory> adfList,
			List<InterfaceAlgorithmFactory> aafList) {
		// Get Data
		this.adfList = adfList;
		this.aafList = aafList;
		this.HBP = null;

		this.host = host;
		this.port = port;
		this.rootURI = rootURI;

	}

	private void connect() throws Exception {
		ClientResource clientResource = new ClientResource("http://" + host + ":" + port + rootURI);
		clientResource.setNext(client); 
		RootResource rr = clientResource.wrap(RootResource.class);
		
		String algorithmID = rr.getAlgorithmId();
		String modelID = rr.getModelId();

		int nbRequirements = rr.getRequirementsNb();
		List<String> reqIdList = new ArrayList<String>();
		for (int i = 0; i < nbRequirements; i++) {
			reqIdList.add(i, rr.getRequirementId(new Integer(i)));
		}

		InterfaceAlgorithmFactory algoFactory = null;
		AbstractModelFactory modelFactory = null;
		List<AbstractRequirementFactory> requirementFactories = new ArrayList<AbstractRequirementFactory>();
		boolean reqFactoryFound = false;

		logger.debug("Package required:");
		// Check algorithm package
		for (InterfaceAlgorithmFactory aaf : aafList) {
			if (aaf.getId().equals(algorithmID)) {
				algoFactory = aaf;
				logger.debug(algorithmID + "  ... Ok");
				break;
			}
		}
		if (algoFactory == null) {
			logger.error(algorithmID + " ... Failure. Abort.");
			return;
		}

		// Check model package
		for (AbstractDataFactory adf : adfList) {
			if (adf.getId().equals(modelID) && adf instanceof AbstractModelFactory) {
				modelFactory = (AbstractModelFactory) adf;
				logger.debug(modelID + "  ... Ok");
				break;
			}
		}
		if (modelFactory == null) {
			logger.error(modelID + " ... Failure. Abort.");
			return;
		}

		// Check requirement package
		for (String requirementID : reqIdList) {
			reqFactoryFound = false;
			for (AbstractDataFactory adf : adfList) {
				if (adf.getId().equals(requirementID) && adf instanceof AbstractRequirementFactory) {
					reqFactoryFound = true;
					requirementFactories.add((AbstractRequirementFactory) adf);
					logger.debug(requirementID + "  ... Ok");
					break;
				}
			}
			if (!reqFactoryFound) {
				logger.error(requirementID + " ... Failure. Abort.");
				return;
			}
		}
		
		// get model and check it
		String modelContent = rr.getModelContent();
		AbstractModel model;
		try {
			model = modelFactory.createAbstractModel("model", modelContent);
			if (model.checkForErrors()) {
				logger.error("Cannot parse model. Abort.");
				return;
			}
		} catch (PlasmaDataException e) {
			logger.error("Cannot parse model. Abort.",e);
			return;
		}
		
		// get requirements and check them
		List<AbstractRequirement> requirements = new ArrayList<AbstractRequirement>();
		for (int i = 0; i < nbRequirements; i++) {
			String content = rr.getRequirementContent(i);
			AbstractRequirement req = requirementFactories.get(i).createAbstractRequirement(rr.getRequirementName(i), content);
			req.setModel(model);
			if (req.checkForErrors())
				logger.warn("Error when parsing requirement " + i + ".");
			else
				requirements.add(req);
		}
		
		// create worker and connect it
		InterfaceAlgorithmWorker worker;
		try {
			worker = algoFactory.createWorker(model, requirements);
		} catch (PlasmaParameterException e) {
			logger.error("Error while creating worker. Abort.",e);
			return;
		}
		String workerUid = worker.connect(client, host, port, algorithmID);
		if (workerUid == null)
			throw new PlasmaExperimentException("Cannot connect the worker");
		
		// start Heartbeat producer
		HBP = new HeartbeatProducer(host, workerUid);
		new Thread(HBP).start();
		
		// start the worker
		logger.info("Launching worker with algorithm: " + algorithmID + " on model: " + modelID);
		worker.start();
		
		// stop the heartbeat when the worker is finished
		HBP.stopTransmitting();		
	}

	@Override
	public void run() {
		boolean connected = false;
		int attempt = 0;
		int maxAttempt = 60;

		logger.info("Connecting service");
		while(! connected && attempt < maxAttempt) {
			try {
				this.connect();
				connected = true;
			} catch (Exception e) {
				if (e.getMessage() != null)
					logger.warn(e.getMessage());
				if (HBP != null) // stop the heartbeat  
					HBP.stopTransmitting();
				attempt ++;
				logger.warn("Retrying (" + attempt + ") ...");					
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}
		
		serviceLock.lock();
		nbService--;
		logger.debug("Service disconnected -- " + nbService + "active services remaining." );
		if (nbService == 0) {
			try {
				client.stop();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		serviceLock.unlock();

		if (attempt < maxAttempt)
			logger.info("Service thread " + Thread.currentThread().getName() + " ended correctly");
		else
			logger.info("Service thread " + Thread.currentThread().getName() + " ended at maxAttempt");

	}

}
