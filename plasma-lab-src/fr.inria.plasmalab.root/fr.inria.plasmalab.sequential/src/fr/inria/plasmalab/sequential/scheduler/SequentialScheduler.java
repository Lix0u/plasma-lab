/**
 * This file is part of fr.inria.plasmalab.sequential.
 *
 * fr.inria.plasmalab.sequential is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.sequential is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.sequential.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.sequential.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.distributed.DistributedBay;
import fr.inria.plasmalab.distributed.Service;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.algorithm.common.Result;
import fr.inria.plasmalab.distributed.algorithm.server.NodeTaskManager;
import fr.inria.plasmalab.distributed.algorithm.server.TaskScheduler;
import fr.inria.plasmalab.montecarlo.data.MontecarloResult;
import fr.inria.plasmalab.sequential.data.SequentialOrder;
import fr.inria.plasmalab.sequential.data.Sequential_SMCResult;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.listener.ExperimentationListener;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class SequentialScheduler implements InterfaceAlgorithmScheduler {
	final static Logger logger = LoggerFactory.getLogger(SequentialScheduler.class);

	/** The id of the factory used to instantiate this algorithm */
	protected String nodeURI;

	protected int batch;
	protected int frequency;

	//NODES AND TASKS MANAGEMENT
	protected NodeTaskManager nodeTaskManager;
	//TASK SCHEDULING
	protected TaskScheduler taskScheduler;
	
	//MULTI THREAD SYNC
	protected Lock batchCreationLock;
	
	protected  List<List<Boolean>> again;
	boolean continueSimulation;
	
	private double	alpha;
	private double beta;
	private double delta;
	private double proba;

	protected int totalToDo;
	protected int remainingToDo;
	protected int[][] totalDone;
	
	private int[][] positiveMatrix;
	private boolean[][] satisfied;
	private List<AbstractRequirement> requirements;
	private OptimVariables optimVariables;
	protected InterfaceIdentifier[] optimId;
	protected List<Map<InterfaceIdentifier, Double>> optimInitialStates;
	private AbstractModel model;
	
	protected ExperimentationListener listener;

	protected boolean stopOrderReceived;
	private boolean errorOccured;
	
	private String port;
	private int nbThread;

	private List<AbstractDataFactory> adfList;
//	private List<AbstractRequirementFactory> arfList;
	private List<InterfaceAlgorithmFactory> aafList;

	public SequentialScheduler(AbstractModel model,
			List<AbstractRequirement> requirements, double alpha, double beta, double delta, double proba, String nodeURI) {
		this.alpha = alpha;
		this.beta = beta;
		this.delta = delta;
		this.proba = proba;
		
		this.requirements = requirements;
		this.model = model;
		this.nodeURI = nodeURI;
		
		//NodeTask manager
		nodeTaskManager = new NodeTaskManager();
		//Task scheduler
		taskScheduler = new TaskScheduler();
		
		//Lock
		batchCreationLock = new ReentrantLock();
	}
	
	public AbstractModel getModel(){
		return model;
	}

	@Override
	public void run() {
		try{
			//Set listening bay
			DistributedBay dbBay = DistributedBay.getDistributedBay(aafList);
			dbBay.setExperiment(model, requirements, this);
			dbBay.startServer(Integer.parseInt(port));

			//Optimization procedure
			//Optimization IDs for display
			this.optimId = getOptimVariables().generateOptimIdentifiers(model).toArray(new InterfaceIdentifier[0]);
			//Optimization initial states
			//optimInitialStates of size 1 with an empty map means no optimization
			optimInitialStates = getOptimVariables().getOptimVariablesRange(model);
			boolean optimActivated = !(getOptimVariables().getVars() == null || getOptimVariables().getVars().size()==0);
			//But it is still one initial state to test
			int optimNState = optimActivated? optimInitialStates.size(): 1;
			
			totalDone = new int[requirements.size()][optimNState];
			again = new ArrayList<List<Boolean>>(requirements.size());
			satisfied = new boolean[requirements.size()][optimNState];
			positiveMatrix = new int[requirements.size()][optimNState];
			for(int i=0; i<requirements.size(); i++){
				again.add(new ArrayList<Boolean>(optimNState));
				for(int j=0; j<optimNState; j++)
					again.get(i).add(true);
				Arrays.fill(totalDone[i], 0);
				Arrays.fill(satisfied[i], false);
				Arrays.fill(positiveMatrix[i], 0);
			}
			continueSimulation=true;
			errorOccured=false;
			
			listener.notifyAlgorithmStarted(nodeURI);
			listener.publishResults(nodeURI, getResults());
			stopOrderReceived = false;
			//Fill seqratio
			double seqratio[][] = new double[positiveMatrix.length][positiveMatrix[0].length];
			for(double[] array:seqratio)
				Arrays.fill(array, 1.);
			
			final double p0 = (getProba() + getDelta()),
						 p1 = (getProba() - getDelta()); 
	
	
			final double	ratioT = p1 / p0,
							ratioF = (1 - p1) / (1 - p0);
			
			final double	strengthA = (1 - getBeta()) / getAlpha(),
							strengthB = getBeta() / (1 - getAlpha());
			
			for(int i=0; i<nbThread; i++)
				Service.startService("localhost", port, "/", adfList, aafList);
			run: do{
				if(stopOrderReceived)
					break run;
				//Get latest result in queue
				MontecarloResult result = (MontecarloResult) taskScheduler.getNextResult();
				if(result != null){
					//Remove from nodeTaskManager
					nodeTaskManager.removeTask(result.getUid());
					int[][] updateMatrix = result.getPositiveMatrix();
					for (int i = 0; i < positiveMatrix.length; i++) {
						for (int j = 0; j < positiveMatrix[i].length; j++) {
							// We doesn't take unneeded results
							if (again.get(i).get(j)) {
								totalDone[i][j] += result.getTotal();
								positiveMatrix[i][j] = positiveMatrix[i][j]
										+ updateMatrix[i][j];
								// update seqratio matrix
								int pos = updateMatrix[i][j];
								int neg = result.getTotal()
										- updateMatrix[i][j];
							seqratio[i][j] *= Math.pow(ratioT, pos)
										* Math.pow(ratioF, neg);
							}
						}
					}
					continueSimulation=false;
					//Test "again" stop condition
					for(int i=0; i<seqratio.length; i++){
						double[] array = seqratio[i];
						for(int j=0; j<array.length; j++){
							double seq = array[j];
							if(seq < strengthA && seq > strengthB){
								//We continue at least for one
								continueSimulation = true;
							}else{
								again.get(i).set(j,false);
								satisfied[i][j] = seq <= strengthB;
							}
					}
					}
				//Notify new results
				listener.publishResults(nodeURI, getResults());
				}else{
					try {
						Thread.sleep(frequency);
					} catch (InterruptedException e) {
					e.printStackTrace();
					}
				}
			}while(continueSimulation);		
			if(!errorOccured){
				// Notify new results
				listener.publishResults(nodeURI, getResults());
				// Notify completed
				if(stopOrderReceived)
					listener.notifyAlgorithmStopped(nodeURI);
				else
					listener.notifyAlgorithmCompleted(nodeURI);	
			}
		}catch(Exception e){
			listener.notifyAlgorithmError(nodeURI, e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public String getNodeURI() {
		return nodeURI;
	}

	public String registerNewNode(){
		String uid = UUID.randomUUID().toString();
		nodeTaskManager.registerNode(uid);
		listener.notifyNewServiceConnected(nodeURI);
		return uid;
	}
	
	public Order schedule(String uid) throws IOException{
		Order order;
		if(nodeTaskManager.nodeIsRegistered(uid)){
			try{
				batchCreationLock.lock();
				String taskUID = nodeTaskManager.getUnassignedTask();
				//Is there an unassigned task
				if(taskUID != null){
					order = nodeTaskManager.gerOrder(taskUID);
					//Register affectation
					nodeTaskManager.registerAffectation(uid, taskUID);
					//Doesn't need to reschedule the task
				}
				//Else generate a new order
				else{
					//If the worker should stop
					if(stopOrderReceived || !continueSimulation){
						order = Order.createTerminateOrder(null);
						logger.debug("Sending terminate order to "+uid);
						//Node will be disconnected
						nodeTaskManager.removeNode(uid);
					}
					//Else the worker will receive a new work
					else {
						order = SequentialOrder.createSequentialBatchOrder(batch,again, null);
						//Register order
						nodeTaskManager.registerOrder(order);
						taskUID = order.getUid();
						//Register affectation
						nodeTaskManager.registerAffectation(uid, taskUID);
						//Schedule task
						taskScheduler.scheduleTask(taskUID);
						//batch += LINEARINCR;
					}
				}
			}finally{
				batchCreationLock.unlock();
				logger.debug("unlock batch creation lock");
			}
		}else{
			//If the node never registered with this scheduler
			order = Order.createTerminateOrder("0");
			logger.debug("Sending terminate order to unknown "+uid);
			listener.notifyServiceDisconnected(nodeURI);
			//We don't register the affectation or the order if its sent to an unknow node
		}
		return order;
	}
	
	public void putResult(Result result){
		taskScheduler.insertResult(result);
	}

	public List<ResultInterface> getResults() {
		List<ResultInterface> results = new ArrayList<ResultInterface>();
		for(int j=0; j<optimInitialStates.size();j++){
			for(int i=0; i<requirements.size();i++){
				AbstractRequirement req = requirements.get(i);
				Sequential_SMCResult result = new Sequential_SMCResult(totalDone[i][j], positiveMatrix[i][j], satisfied[i][j],
						optimInitialStates.get(j), optimId, req, model);
				results.add(result);
			}
		}
		return results;
	}

	@Override
	public void setExpListener(ExperimentationListener listener) {
		this.listener = listener;
	}

	@Override
	public void abortScheduling() {
		stopOrderReceived = true;
	}

	@Override
	public void setOptimizationVariables(OptimVariables  optimVariables) {
		this.optimVariables = optimVariables;
	}
	
	/**
	 * Post error message to the experimentation manager and abort experimentation.
	 */
	public void postErrorMessage(String errorMessage){
		errorOccured = true;
		listener.notifyAlgorithmError(nodeURI, errorMessage);
		abortScheduling();
	}

	@Override
	public void setServices(String port, int nbThread, int batch, int frequency,
			List<AbstractDataFactory> adfList,
//			List<AbstractRequirementFactory> arfList,
			List<InterfaceAlgorithmFactory> aafList) {
		this.port = port;
		this.nbThread = nbThread;
		this.batch = batch;
		this.frequency = frequency;
		this.adfList = adfList;
		this.aafList = aafList;
		
	}

	public double getAlpha() {
		return alpha;
	}
	public double getBeta() {
		return beta;
	}
	public double getDelta() {
		return delta;
	}
	public double getProba() {
		return proba;
	}
	public OptimVariables getOptimVariables() {
		return optimVariables;
	}
}
