/**
 * This file is part of fr.inria.plasmalab.controler.
 *
 * fr.inria.plasmalab.controler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.controler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.controler.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.controler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.listener.ExperimentationListener;
import fr.inria.plasmalab.workflow.shared.ResultInterface;
/**
 * ExperimentationManager tool to launch new Experimentation.
 * 
 * Given a Model a Requirement and an Algorithm, its task is to setup a new experiment and run it.
 * It also manages inbound call from external Algorithm's Worker and establish a connection with 
 * the Algorithm's Scheduler. 
 * 
 * @author kevin.corre@inria.fr
 *
 */
public class ExperimentationManager implements ExperimentationListener{
	final static Logger logger = LoggerFactory.getLogger(ExperimentationManager.class);
	
	private InterfaceAlgorithmScheduler algorithm;
	private AbstractModel model;
	private List<AbstractRequirement> requirements;
	
	private List<ExperimentationListener> listeners;
	private Controler controler;
	
	private static ExperimentationManager myself=null;
	
	/* Used to start synchronously the experiment.*/
	private Object lockObject;
	
	public static ExperimentationManager createExperimentationManager(Controler controler){
		if(myself==null){
			myself = new ExperimentationManager(controler);
		}
		return myself;
	}

	private ExperimentationManager(Controler controler) {
		super();
		listeners = new ArrayList<ExperimentationListener>();
		this.controler = controler;
		this.lockObject = new Object();
	}
	
	public void addExperimentationListener(ExperimentationListener expListener){
		listeners.add(expListener);
	}
	public void removeExperimentationListener(ExperimentationListener expListener){
		listeners.remove(expListener);
	}
	
	/**
	 * This method setup and launch a new experiment
	 * @param algorithmFactory which will generate the AlgorithmScheduler
	 * @param model the AbstractModel we want to SMC
	 * @param requirements a List of AbstractRequirements we want to SMC
	 * @param port the port we want to use for distributed mode
	 * @param externalOptimVariables Optimization variables provided from an external source.
	 * They take over OptimVariables from requirements.
	 * @param nbThread the number of thread to use in local mode
	 * @param parameters for the algorithm
	 * @return
	 * @throws PlasmaDataException
	 * @throws PlasmaParameterException
	 */
	public void setupAnExperiment(InterfaceAlgorithmFactory algorithmFactory, 
									AbstractModel model,
									List<AbstractRequirement> requirements,
									int port,
									OptimVariables externalOptimVariables,
									int nbThread,
									int batch,
									int frequency,
									Map<String,Object> parameters) throws PlasmaDataException, PlasmaParameterException {
		//Set the model
		this.model = model;
		if(model.checkForErrors()){
			String errorMsg = "Error in "+model.getName();
			for(PlasmaDataException err:model.getErrors()){
				errorMsg += "\n"+err.getMessage();
			}
			throw new PlasmaDataException(errorMsg);
		}
		
		//Set the requirements
		this.requirements = requirements;
		for(AbstractRequirement requirement:this.requirements){
			requirement.setModel(model);
			if(requirement.checkForErrors()){
				String errorMsg = "Error in "+requirement.getName();
				for(PlasmaDataException err:requirement.getErrors()){
					errorMsg += "\n"+err.getMessage();
				}
				throw new PlasmaDataException(errorMsg);
			}
		}
		
		//Set OptimVariables
		OptimVariables optimVariables = externalOptimVariables;
		for(AbstractRequirement requirement:this.requirements){
			optimVariables.addVars(requirement.getOptimizationVariables());
		}
		if(model.getOptimizationVariables()!=null)
			optimVariables.addVars(model.getOptimizationVariables());
		if(model.getOptimizationConstraints()!=null)
			optimVariables.addConstraints(model.getOptimizationConstraints());
//		for(VarContext var:optimVariables.getVarsMap().values()){
//			System.out.println(var.getVar().getName());
//		}
		
		//Instantiate requirements with RequirementVariables
		requirements = new ArrayList<AbstractRequirement>();
		for(AbstractRequirement requirement:this.requirements){
			List<AbstractRequirement> instances = requirement.generateInstantiatedRequirement();
			if(instances==null)
				requirements.add(requirement);
			else if(instances.size()==0)
				requirements.add(requirement);
			else
				requirements.addAll(instances);
		}
		//Replace original requirement with newly instantiated requirements
		this.requirements = requirements;		
		
		//Set the algorithm
		this.algorithm = algorithmFactory.createScheduler(this.model, this.requirements, parameters);
		//ExpManager will listen to the experiment
		algorithm.setExpListener(this);
		//Distributed parameters
		algorithm.setServices(String.valueOf(port), nbThread, batch, frequency,
				controler.getADFList(),
//				controler.getARFList(),
				controler.getAAFList());
		//Give optimization variables to the algorithm
		algorithm.setOptimizationVariables(optimVariables);
	}
	
	/**
	 * Clean anything that should be after the experiment terminated or failed
	 */
	public void cleanExperiment(){
		//Clean model
		try {
			model.clean();
		} catch (PlasmaSimulatorException e) {
			logger.error("Error while cleaning model: ", e);
		}
		//Clean requirement
		for(AbstractRequirement req:requirements)
			req.clean();
		
		//Clear listeners
		listeners.clear();
		// release the execution.
		synchronized (lockObject) {
			lockObject.notify();
		}
		
	}
	
	public String startExperimentAsync() {
		return startExperiment();
	}
	
	public String startExperimentSync() throws InterruptedException{
		String exp = null;
		synchronized(lockObject) {
			exp = startExperiment();
			lockObject.wait();
		}
		return exp;
	}
	
	private String startExperiment(){
		Thread schedulerThread = new Thread(algorithm);
		schedulerThread.start();
		return algorithm.getNodeURI();
	}
	
	/**
	 * Tell the algorithm to stop the experiment.
	 */
	public void stopExperiment(){
		algorithm.abortScheduling();
	}	

	@Override
	public void notifyAlgorithmStarted(String nodeURI) {
		// Notify
		for (ExperimentationListener expListener : listeners) {
			expListener.notifyAlgorithmStarted(nodeURI);
		}
	}

	@Override
	public void notifyAlgorithmCompleted(String nodeURI) {
		// Notify
		for (ExperimentationListener expListener : listeners) {
			expListener.notifyAlgorithmCompleted(nodeURI);
		}
		cleanExperiment();
	}

	@Override
	public void notifyAlgorithmStopped(String nodeURI) {
		// Notify
		for (ExperimentationListener expListener : listeners) {
			expListener.notifyAlgorithmStopped(nodeURI);
		}
		cleanExperiment();

	}

	@Override
	public void notifyAlgorithmError(String nodeURI, String errorMessage) {
		//TODO A solution to this problem is needed
		//There is something causing the server to take time to stop in this situation.
		//To avoid a crash, we wait for the server to stop before doing anything else.
		// Notify
		for (ExperimentationListener expListener : listeners) {
			expListener.notifyAlgorithmError(nodeURI, errorMessage);
		}
		cleanExperiment();
	}

	@Override
	public void notifyNewServiceConnected(String nodeURI) {
		// Notify
		for (ExperimentationListener expListener : listeners) {
			expListener.notifyNewServiceConnected(nodeURI);
		}
		logger.info("New service connected");
	}

	@Override
	public void notifyServiceDisconnected(String nodeURI) {
		// Notify
		for (ExperimentationListener expListener : listeners) {
			expListener.notifyServiceDisconnected(nodeURI);
		}
		logger.info("Service disconnected");
	}

	@Override
	public void publishResults(String nodeURI, List<ResultInterface> results) {
		// Notify
		for (ExperimentationListener expListener : listeners) {
			expListener.publishResults(algorithm.getNodeURI(), results);
		}
	}

	@Override
	public void notifyProgress(int percent) {
		// Notify
		for (ExperimentationListener expListener : listeners) {
			expListener.notifyProgress(percent);
		}
	}

	@Override
	public void notifyTimeRemaining(long milliseconds) {
		// Notify
		for (ExperimentationListener expListener : listeners) {
			expListener.notifyTimeRemaining(milliseconds);
		}
	}
}
