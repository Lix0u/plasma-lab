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
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.restlet.Client;
import org.restlet.resource.ClientResource;

import fr.inria.plasmalab.distributed.algorithm.AbstractWorker;
import fr.inria.plasmalab.distributed.algorithm.common.Order;
import fr.inria.plasmalab.distributed.restlet.common.InterfaceServerResource;
import fr.inria.plasmalab.importancesplitting.data.GlobalState;
import fr.inria.plasmalab.importancesplitting.data.ImportanceSplittingResult;
import fr.inria.plasmalab.importancesplitting.data.Trace;
import fr.inria.plasmalab.importancesplitting.server.InterfaceISServerRessource;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

/** Distributed importance splitting worker that runs the splitting algorithm entirely for the allocated budget and send the estimation to the scheduler.
 *  This works both for the fixed levels and the adaptive levels algorithm.
 *  
 * @author ltraonou
 *
 */
public class ImportanceSplittingWorker extends AbstractWorker {

	protected InterfaceISServerRessource mcr;
	
	// common data for one batch
	private AbstractRequirement requirement;
	private int budget;
	private double proba;
	private int nbLevels;
	
	public ImportanceSplittingWorker(AbstractModel model, List<AbstractRequirement> req) {
		this.model = model;
		this.requirements = req;
	}

	@Override
	public String connect(Client c, String host, String port, String uri) {
		ClientResource client = new ClientResource("http://"+host+":"+port+"/"+uri+"/");
		client.setNext(c); 
		mcr = client.wrap(InterfaceISServerRessource.class);
		
		uid = mcr.register();
		return uid;
	}

	@Override
	protected InterfaceServerResource getServerResource() {
		return mcr;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void processBatchOrder(Order order) throws PlasmaSimulatorException, PlasmaCheckerException {
		int req = (Integer) order.getParam("req");
		int st = (Integer) order.getParam("st");
		budget = (Integer) order.getParam("budget");
		List<Double> levels = (ArrayList<Double>) order.getParam("levels");
		double scorePhi = (Double) order.getParam("score");
		logger.info(uid + " received batch order of " + budget);
	
		requirement = requirements.get(req);
		if(optimActivated)
			model.setValueOf(optimInitialStates.get(st));	
		else
			model.checkForErrors(); // this is a bit too much, but it does the work...
		proba = 1.0;
		nbLevels = 0;
				
		if (levels != null)
			fixed(levels);
		else
			adaptive(scorePhi);
		
		//Post
		ImportanceSplittingResult result = new ImportanceSplittingResult(order.getUid(), budget, nbLevels, proba);
		logger.info(uid+" Batch completed, post results");
		mcr.postISResults(result);
	}
	
	private void fixed(List<Double> levels) throws PlasmaSimulatorException, PlasmaCheckerException {
		List<GlobalState>  paths = new LinkedList<GlobalState>();
		double 	scorePath = 0;
		Random rebranchingRng = new Random();
		int accepted = 0;		
		nbLevels = levels.size();
		
		// 1 generate first set of traces
		for (int i = 0; i < budget; i++) {
			InterfaceState initState = model.newPath();
			scorePath = requirement.check("score",levels.get(0),initState);		
			if (scorePath >= levels.get(0)) {
				paths.add(new GlobalState(model.getCurrentState(), requirement.getLastTrace().get(requirement.getLastTrace().size()-1)));
				accepted++;
			}
		}
		proba *= (double) accepted/budget;
		if (accepted == 0) {
			logger.info(uid+" 0 trace accepted at first iteration");
			return;
		}
		
		// 2 for each level restarts the simulations until reaching the next level
		for (int level = 1; level < levels.size(); level++) {
			List<GlobalState> newPaths = new LinkedList<GlobalState>();
			List<GlobalState> orderedPaths = new LinkedList<GlobalState>();
			accepted = 0;
			ListIterator<GlobalState> it = paths.listIterator(); 
			for (int i = 0; i < budget; i++) {
				GlobalState initState;
				if (i < paths.size()) { // first restart all the accepted paths
					initState = it.next();
					if (!it.hasNext())
						it = orderedPaths.listIterator(); /* empty here */
				}
				else if (paths.size() > 0) { // then restart some random accepted paths, while there are paths
					int index = rebranchingRng.nextInt(paths.size());
					initState = paths.remove(index); /* ... choose randomly from paths */
					orderedPaths.add(initState); /* ... and add to an empty list */
					it = orderedPaths.listIterator();
				} else { // finally if all paths are used, re-cycle
					initState = it.next();
					if (!it.hasNext())
						it = orderedPaths.listIterator();
				}
//									Map<InterfaceIdentifier,Double> update = new HashMap<InterfaceIdentifier, Double>(headers.length);
//									for (InterfaceIdentifier id : headers) {
//										update.put(id, initState.modelState.getValueOf(id.getName()));
//									}
				model.setValueOf(initState.modelState.getValues());
				model.newPath();
				
				scorePath = requirement.check("score",levels.get(level),initState.observerState);
				if (scorePath >= levels.get(level)) {
					newPaths.add(new GlobalState(model.getCurrentState(), requirement.getLastTrace().get(requirement.getLastTrace().size()-1)));
					accepted++;
				}
			}
			proba *= (double) accepted/budget;
			if (accepted == 0) {
				logger.info(uid+" 0 trace accepted at iteration " + level);
				return;
			}
	
			// prepare next level
			paths = newPaths;
		}
	}
	
	private void adaptive(double scorePhi) throws PlasmaCheckerException, PlasmaSimulatorException {
		List<Trace> paths = new LinkedList<Trace>(); 
		double score = 0, scorePath = 0, scoreMin = scorePhi;
		Random rebranchingRng = new Random();
	
		// 1 generate first set of traces
		for (int i = 0; i < budget; i++) {
			InterfaceState initState = model.newPath();
			scorePath = requirement.check(initState);
			if (scorePath < scoreMin)
				scoreMin = scorePath;
			paths.add(new Trace(model.getTrace(), requirement.getLastTrace(), scorePath));
		}

		score = scoreMin;
		List<Trace> newPaths = new LinkedList<Trace>();
		List<Trace> orderedPaths = new LinkedList<Trace>();
		while (score < scorePhi) {
			// 2 determine accepted traces
			scoreMin = scorePhi;
			int accepted = 0;
			ListIterator<Trace> it = paths.listIterator();
			while (it.hasNext()) {
				Trace t = it.next();
				if (t.getScore() > score) {
					accepted++;
					if (t.getScore() < scoreMin)
						scoreMin = t.getScore();
				} else {
					it.remove();
				}
			}
			proba *= (double) accepted/budget;
			if (accepted == 0) {
				logger.info(uid+" 0 trace accepted at iteration " + nbLevels);
				return;
			}
			nbLevels++;

			// 3 rebranch the non accepted traces
			for (int i = 0; i < budget - accepted; i++) {
				Trace oldPath;
				if (paths.size() > 0) { /* while there are paths ... */
					int index = rebranchingRng.nextInt(paths.size());
					oldPath = paths.remove(index); /* ... choose randomly from paths */
					orderedPaths.add(oldPath); /* ... and add to an empty list */
					it = orderedPaths.listIterator(); /* empty here */
				} else { /* used all paths, so re-cycle */
					oldPath = it.next();
					if (!it.hasNext())
						it = orderedPaths.listIterator();
				}
				
				GlobalState initState = oldPath.getBranchingState(score);
//									Map<InterfaceIdentifier, Double> update = new HashMap<InterfaceIdentifier, Double>(headers.length);
//									for (InterfaceIdentifier id : headers) {
//										update.put(id, initState.modelState.getValueOf(id.getName()));
//									}
				model.setValueOf(initState.modelState.getValues());
				model.newPath();

				scorePath = requirement.check(initState.observerState);
				if (scorePath < scoreMin)
					scoreMin = scorePath;
				newPaths.add(new Trace(model.getTrace(), requirement.getLastTrace(), scorePath)); /* store new paths and add later */
			}
			if (paths.size() < newPaths.size()) {
				List<Trace> temp = paths;
				paths = newPaths;
				newPaths = temp;
			}
			paths.addAll(newPaths);
			paths.addAll(orderedPaths);
			newPaths.clear(); /* clear newPaths and orderedPaths ready for next level */
			orderedPaths.clear();

			score = scoreMin;
		}
	}

}
