/**
 * This file is part of fr.inria.plasmalab.comparison.
 *
 * fr.inria.plasmalab.comparison is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.comparison is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.comparison.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * This file is part of fr.inria.plasmalab.root.
 *
 * fr.inria.plasmalab.root is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.root is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.root.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.comparison;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.inria.plasmalab.algorithm.AbstractAlgorithm;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class ComparisonAlgorithm extends AbstractAlgorithm {
	/** The logger used by the algorithm */
	protected final static Logger logger = LoggerFactory.getLogger(AbstractAlgorithm.class);
	
	//parameters
	private double ratioT, ratioF, strengthA, strengthB, accept, reject, c;
	private AbstractRequirement lowerReq, greaterReq;
	
	//results 
	protected List<ResultInterface> results;
	
	public ComparisonAlgorithm(AbstractModel model, AbstractRequirement greaterReq, AbstractRequirement lowerReq,
			double alpha, double beta, double theta, double delta, double u1, double u0, String nodeURI) {
		this.model = model;
		this.greaterReq = greaterReq;
		this.lowerReq = lowerReq;
		
		double p0 = theta+delta;
		double p1 = theta-delta;
		this.ratioT = Math.log(p1 / p0);
		this.ratioF = Math.log((1 - p1) / (1 - p0));
		this.strengthA = Math.log((1 - beta) / alpha);
		this.strengthB = Math.log(beta / (1 - alpha));
		double ratioU = Math.log(u1/u0);
		this.accept = strengthB / ratioU;
		this.reject = strengthA / ratioU;
		this.c = Math.log((1+u1)/(1+u0)) / ratioU;

		this.nodeURI = nodeURI;
	}
	
	@Override
	public void run() {
		initializeAlgorithm();	
	
		listener.notifyAlgorithmStarted(nodeURI);
		logger.info("Starting Comparison algorithm between " + lowerReq.getName() + " and " + greaterReq.getName());
		this.results = new ArrayList<ResultInterface>(optimNState);
		
		try {
			for(int st = 0; st<optimNState; st++){
				if(optimActivated)
					model.setValueOf(optimInitialStates.get(st));
				run(st);			
			}
		}
		catch (PlasmaStopOrder e) { 
			//results.add(new Comparison_SMCResult(0,0,optimInitialStates.get(st), getOptimId(), lowerReq, greaterReq, model));
		}
		catch (PlasmaExperimentException e) {
			//results.add(new Comparison_SMCResult(0,0,optimInitialStates.get(st), getOptimId(), lowerReq, greaterReq, model));
			//logger.error(e.getMessage(),e);
			listener.notifyAlgorithmError(nodeURI, e.toString());
			errorOccured = true;
		}
		if(!errorOccured){
			if(stopOrderReceived)
				listener.notifyAlgorithmStopped(nodeURI);
			else
				listener.notifyAlgorithmCompleted(nodeURI);
		}
	}
	

	private void run(int st) throws PlasmaStopOrder, PlasmaSimulatorException, PlasmaCheckerException {
		boolean checkEquality = true;
		double ratioEquality = 0.0;
		double ratioComparison = 0.0;
		int totalcount = 0;
		int result = 0; // -1: reject, 0: indifferent, 1: accept
		logger.debug("strengthA: " + strengthA);
		logger.debug("strengthB: " + strengthB);
		logger.debug("ratioT: " + ratioT);
		logger.debug("ratioF: " + ratioF);
		logger.debug("c: " + c);

		run: {
			do {
				if(stopOrderReceived)
					throw new PlasmaStopOrder();
				InterfaceState path1 = model.newPath();
				double x1 = lowerReq.check(path1);
				InterfaceState path2 = model.newPath();
				double x2 = greaterReq.check(path2);
				totalcount += 2;
							
				if (checkEquality) {
					if (x1 == x2)
						ratioEquality += ratioT;					
					else
						ratioEquality += ratioF;
					logger.debug("ratioEquality: " + ratioEquality);
					if (ratioEquality <= strengthB) {
						result = 0;
						break run;
					}
					if (ratioEquality >= strengthA)
						checkEquality = false;
				}
				if (x1 != x2) {
					accept += c;
					reject += c;
					logger.debug("accept: " + accept);
					logger.debug("reject: " + reject);

					if (x1 == 0.0 && x2 == 1.0)
						ratioComparison += 1;
					logger.debug("ratioComparison: " + ratioComparison);
					if (ratioComparison <= accept) {
						result = -1;
						break run;
					}
					if (ratioComparison >= reject) {
						result = 1;
						break run;
					}
				}
			} while(true);
		}
		
		// publish results
		results.add(new Comparison_SMCResult(totalcount,result,optimInitialStates.get(st), getOptimId(), lowerReq, greaterReq, model));
		listener.publishResults(nodeURI, results);
	}
}
