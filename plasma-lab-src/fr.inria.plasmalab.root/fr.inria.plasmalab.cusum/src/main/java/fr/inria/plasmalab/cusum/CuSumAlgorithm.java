/**
 * This file is part of fr.inria.plasmalab.cusum.
 *
 * fr.inria.plasmalab.cusum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.cusum is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.cusum.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.cusum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.cusum.data.CuSumResult;
import fr.inria.plasmalab.workflow.concrete.OptimVariables;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.factory.AbstractDataFactory;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaExperimentException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaStopOrder;
import fr.inria.plasmalab.workflow.listener.ExperimentationListener;
import fr.inria.plasmalab.workflow.shared.ResultInterface;

public class CuSumAlgorithm implements InterfaceAlgorithmScheduler {
	/** The id of the factory used to instantiate this algorithm */
	protected final static String nodeURI = "fr.inria.plasmalab.cusum";;

	private boolean cusum_mode = false;
	private boolean simulation_mode = false;
	private boolean monte_carlo_mode = false;
	
	// Shared parameters 
	private double delay;
	private int samplesNb;
	private AbstractRequirement requirement;
	private AbstractModel model;
	
	// Chernoff parameters
	private int totalToDo;
	private double minProba;
	private double maxProba;
	
	//Cusum parameters
	private boolean detection;
	private double lambda;
	private double changeTime = -1;
	private int changeSample = -1;
	private double ratioT, ratioF;
	
	//Cusum simulation parameters
	private int simuNb;
	private String outputFile;

	private ExperimentationListener listener;

	private boolean stopOrderReceived;
	private boolean errorOccured;


	public CuSumAlgorithm(AbstractModel model, AbstractRequirement requirement, double sampleDelay, int samplesNb,
			double pInit, double deviation, double lambda) {
		this.cusum_mode = true;

		this.delay = sampleDelay;
		this.samplesNb = samplesNb;
		double k = pInit + deviation;
		this.ratioT = Math.log(k / pInit);
		this.ratioF = Math.log((1 - k) / (1 - pInit));
		this.lambda = lambda;
		this.requirement = requirement;
		this.model = model;
	}
	
	public CuSumAlgorithm(AbstractModel model, AbstractRequirement requirement, double sampleDelay, int samplesNb,
			double pInit, double deviation, int simuNb, String outputFile) {
		this.simulation_mode = true;

		this.delay = sampleDelay;
		this.samplesNb = samplesNb;
		double k = pInit + deviation;
		this.ratioT = Math.log(k / pInit);
		this.ratioF = Math.log((1 - k) / (1 - pInit));
		this.simuNb = simuNb;
		this.outputFile = outputFile;
		this.requirement = requirement;
		this.model = model;
	}

	public CuSumAlgorithm(AbstractModel model, AbstractRequirement requirement, double sampleDelay, int samplesNb,
			int totalToDo) {
		this.monte_carlo_mode = true;

		this.delay = sampleDelay;
		this.samplesNb = samplesNb;
		this.totalToDo = totalToDo;
		this.requirement = requirement;
		this.model = model;
	}
	
	/** Generate a new sample from state s0 ?
	 * 
	 *  @param s0
	 *  @return
	 * @throws PlasmaSimulatorException 
	 */
	private InterfaceState nextSample(InterfaceState s0) throws PlasmaSimulatorException {
		InterfaceIdentifier timeId = model.getTimeId();
		int i = 0; // s0 is the first state in the trace
		double timeBound = s0.getValueOf(timeId) + delay;
		InterfaceState current = s0;
		InterfaceState previous = s0;
		
		while (current.getValueOf(timeId) <= timeBound) {
			i++;
			// New state required ?
			while (model.getTrace().size() <= i)
				model.simulate();
			previous = current;
			current = model.getStateAtPos(i);			
		}
		if (current.getValueOf(timeId) > timeBound) {
			current = previous;
			current.setValueOf(timeId, timeBound); // does not work in Matlab. Is it useful ?
			i--;
		}
		model.cut(i);
		return current;
	}



	private void CUSUM() throws PlasmaSimulatorException, PlasmaCheckerException, PlasmaStopOrder {
		double sumratio = 0.;
		double minratio = Double.NaN;
		InterfaceState s0 = model.newPath();	
		int sample = 0;

		do {
			if (stopOrderReceived) 		
				throw new PlasmaStopOrder();

			sumratio += requirement.check(s0) > 0 ? ratioT : ratioF;
			if (Double.isNaN(minratio))
				minratio = sumratio;
			else if (sumratio < minratio) 
				minratio = sumratio; 
			//Test the stop condition
			detection = sumratio >= minratio + lambda;   
			if (!detection) {
				s0 = nextSample(s0);
				sample++;
			}
		} while (!detection && sample < samplesNb);	

		changeSample = detection ? sample : -1;
		changeTime = detection ? s0.getValueOf(model.getTimeId()) : Double.NaN;
	}
	
	private Double[] simulateCuSuM() throws PlasmaSimulatorException, PlasmaCheckerException, PlasmaStopOrder {
		List<Double> results = new LinkedList<Double>();
		double sumratio = 0.;
		double minratio = Double.NaN;
		InterfaceState s0 = model.newPath();
		int sample = 0;

		do {
			if (stopOrderReceived) 		
				throw new PlasmaStopOrder();


			sumratio += requirement.check(s0) > 0 ? ratioT : ratioF;
			if (Double.isNaN(minratio))
				minratio = sumratio;
			else if (sumratio < minratio) 
				minratio = sumratio; 

			results.add(sumratio);

			s0 = nextSample(s0);
			sample++;
		} while (sample < samplesNb);

		return results.toArray(new Double[0]);
	}
	
	private void checkIndependence() throws PlasmaSimulatorException, PlasmaCheckerException {
		int [] results = new int[samplesNb];
		Arrays.fill(results, 0);
	
		for (int i = 0; i < totalToDo; i++) {
			InterfaceState s0 = model.newPath();
			int sample = 0;
			algo: do {
				if (stopOrderReceived) 		break algo;
				
				if (requirement.check(s0) > 0)
						results[sample]++;
								
				s0 = nextSample(s0);
				sample++;
			} while (sample < samplesNb);
		}
		
		maxProba = 0.0;
		minProba = 1.0;
		for (int sample = 0; sample < samplesNb; sample++) {
			double proba = (double) results[sample] / totalToDo;
			if (proba > maxProba)
				maxProba = proba;
			else if (proba < minProba)
				minProba = proba;
		}		
	}

	@Override
	public void run() {
		listener.notifyAlgorithmStarted(nodeURI);
		errorOccured = false;
		stopOrderReceived = false;

		try {
			if (cusum_mode) {
				CUSUM();
			}
			else if (simulation_mode) {
				File f = new File(outputFile);
				PrintWriter pw;
		
					if (!f.exists())
						try {
							f.createNewFile();
						} catch (IOException e) {
							throw new PlasmaParameterException(f.getAbsolutePath() + " cannot be created");
						}
					if (f.exists() & !f.canWrite())
						throw new PlasmaParameterException(f.getAbsolutePath() + " cannot be written");
					pw = new PrintWriter(f);
				
				Double[][] cusumSamples = new Double[simuNb][];
				//Generate samples
				for (int i = 0; i < simuNb; i++) {
					cusumSamples[i] = simulateCuSuM();
					int last = cusumSamples[i].length - 1;
		
					for (int j = 0; j < last; j++)
						pw.print(cusumSamples[i][j] + ", ");
					pw.println(cusumSamples[i][last]);
				}
				//TODO repeat set lambda, compute false alarm until user satisfied with false alarm pr
				//Get lambda
				//TODO
				//lambda = 0.5;
				//Compute estimated lambda value
				//double falseAlarmPr = computeFalseAlarmPr(cusumSamples);
				//logger.info("false alarm probability is " + falseAlarmPr);
				pw.close();
				//CUSUM();			
			}
			else if (monte_carlo_mode) {
				checkIndependence();
			}
		}
		catch(PlasmaStopOrder e) { }
		catch(PlasmaExperimentException e) {
			errorOccured = true;
			listener.notifyAlgorithmError(nodeURI, e.getMessage());
		} catch (FileNotFoundException e) {
			errorOccured = true;
			listener.notifyAlgorithmError(nodeURI, e.getMessage());
		} catch (PlasmaParameterException e) {
			errorOccured = true;
			listener.notifyAlgorithmError(nodeURI, e.getMessage());
		}

		//Publish results
		if (!errorOccured) {
			listener.publishResults(nodeURI, getResults());		// Notify new results

			if (stopOrderReceived)								// Notification completed
				listener.notifyAlgorithmStopped(nodeURI);
			else
				listener.notifyAlgorithmCompleted(nodeURI);		
		}
	}

	@Override
	public String getNodeURI() {
		return nodeURI;
	}

	@Override
	public void setExpListener(ExperimentationListener listener) {
		this.listener = listener;
	}

	@Override
	public void abortScheduling() {
		stopOrderReceived = true;
	}	

	public List<ResultInterface> getResults() {
		List<ResultInterface> results = new ArrayList<ResultInterface>();
		CuSumResult result = null;
		if (cusum_mode) {
			result = new CuSumResult(changeTime, changeSample, detection, requirement);
		}
		else if (simulation_mode) {
			result = new CuSumResult(changeTime, changeSample, detection, requirement);
		}
		else if (monte_carlo_mode) {
			result = new CuSumResult(minProba, maxProba, requirement);
		}
		
		results.add(result);
		return results;
	}

	/**
	 * Post error message to the experimentation manager and abort experimentation.
	 */
	public void postErrorMessage(String errorMessage){
		errorOccured = true;
		abortScheduling();
		listener.notifyAlgorithmError(nodeURI, errorMessage);
	}

	@Override
	public void setOptimizationVariables(OptimVariables optimVariables) {
		// TODO Stub de la méthode généré automatiquement

	}

	@Override
	public void setServices(String port, int nbThread, int batch, int frequency,
			List<AbstractDataFactory> adfList,
			List<InterfaceAlgorithmFactory> aafList) {
		// TODO Stub de la méthode généré automatiquement

	}

}
