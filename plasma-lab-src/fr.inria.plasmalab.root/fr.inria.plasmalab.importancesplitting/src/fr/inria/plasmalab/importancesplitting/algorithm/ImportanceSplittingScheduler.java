/**
 * This file is part of fr.inria.plasmalab.importancesplitting.
 *
 * fr.inria.plasmalab.importancesplitting is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.importancesplitting is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.importancesplitting.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.importancesplitting.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.distributed.algorithm.AbstractScheduler;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.algorithm.common.Order.Type;
import fr.inria.plasmalab.importancesplitting.data.ISDistributed_SMCResult;
import fr.inria.plasmalab.importancesplitting.data.ImportanceSplittingResult;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.shared.ResultInterface;


/** Distributed importance splitting algorithm that performs the same work on each workers and computes the average results.
 *  The budget is divided among all the workers (using the BATCH parameter).
 *  Each worker runs entirely the importance splitting algorithm (fixed or adaptive) and sends the results.
 *  The scheduler outputs the average of the results of all the workers.
 *  
 * @author ltraonou
 *
 */
public class ImportanceSplittingScheduler extends AbstractScheduler {
	
	protected static final int SLEEP_ORDER = 1000;
	
	// parameters
	protected int budget;
	protected double[] levels;
	protected double scorePhi;
	
	// results
	protected List<ResultInterface> results;
	
	// data
	protected int currentSt, currentReq, remainingToDo, done;
	
	public ImportanceSplittingScheduler (AbstractModel model, List<AbstractRequirement> reqs, int budget, double levels[], double scorePhi, String nodeURI) {
		this.model = model;
		this.requirements = reqs;
		this.nodeURI = nodeURI;
		
		this.budget = budget;
		this.levels = levels;
		this.scorePhi = scorePhi;
	}

	@Override
	public void run() {
		initializeServer();

		listener.notifyAlgorithmStarted(nodeURI);
	
		this.results = new ArrayList<ResultInterface>(optimNState*requirements.size());
		this.done = optimNState*requirements.size();
		try {
			for(currentSt=0; currentSt<optimNState; currentSt++) {
				for(currentReq=0; currentReq<requirements.size(); currentReq++) {
					runCurrent();
				}
			}
		}
		catch(PlasmaStopOrder e) { }
		catch(PlasmaExperimentException e) {
			errorOccured = true;
			listener.notifyAlgorithmError(nodeURI, e.getMessage());
		}
		this.done = 0; // terminate all workers
		
		if(!errorOccured) {
			// Notify completed
			if(stopOrderReceived)
				listener.notifyAlgorithmStopped(nodeURI);
			else
				listener.notifyAlgorithmCompleted(nodeURI);
		}
		logger.info("Experiment completed");
		terminateServer();
	}
	
	private void runCurrent() throws PlasmaExperimentException {
		this.remainingToDo = budget; // start the workers
		int totalReceived = 0;
		double nbLevels = 0;
		double proba = 1.0;
		while (totalReceived < budget) {
			ImportanceSplittingResult result = (ImportanceSplittingResult) getNextResult();
			int wreceived = result.getBudget();
			int newtotalreceived = wreceived + totalReceived;
			int wnblevels = result.getNbLevels();
			double wproba = result.getProba();
			logger.info("Received result: " + wreceived + " simulations; proba: " + wproba);
			proba = proba * ((double) totalReceived / newtotalreceived) + wproba * ((double) wreceived / newtotalreceived);
			nbLevels = nbLevels * ((double) totalReceived / newtotalreceived) + wnblevels * ((double) wreceived / newtotalreceived);
			totalReceived = newtotalreceived;
		}
		results.add(new ISDistributed_SMCResult(model,requirements.get(currentReq),nbLevels,proba));
		listener.publishResults(nodeURI, results);
	}

	@Override
	protected Order generateOrder() {
		//If the worker should stop
		if(stopOrderReceived || done==0)
			return Order.createTerminateOrder(null);
		//If the worker should wait
		else if (remainingToDo <= 0)
			return Order.createWaitOrder(SLEEP_ORDER, null);
		//Else the worker will receive a new work
		else  {
			int currentBatch = Math.min(remainingToDo, batch);
			remainingToDo -= currentBatch;
			if (remainingToDo <= 0)
				done--;
			return createImportanceSplittingBatchOrder(currentReq,currentSt,levels,scorePhi,currentBatch,null);
		}
	}
	
	public static Order createImportanceSplittingBatchOrder(int req, int st, double[] levels, double score, int budget, String uid){
		Map<String, Object> content = new HashMap<String, Object>();
		content.put("req", req);
		content.put("st", st);
		content.put("levels", levels);
		content.put("score", score);
		content.put("budget", budget);
		return new Order(Type.BATCH, content, uid);
	}

}
