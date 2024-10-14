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
package fr.inria.plasmalab.cusum.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.restlet.resource.ServerResource;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.InterfaceAlgorithmWorker;
import fr.inria.plasmalab.algorithm.data.SMCAlternatives;
import fr.inria.plasmalab.algorithm.data.SMCParameter;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.cusum.CuSumAlgorithm;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaNotDistibutedException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class CuSumFactory implements InterfaceAlgorithmFactory {
	private final static String id ="cusum";
	private final static String name ="CUSUM";
	private final static String description ="CUSUM algorithm.";

	private final static String paramSamples = "Samples", tipSamples = "Maximum number of samples to observe [0,+oo]";
	private final static String paramDelay = "Samples Delay", tipDelay = "Execution time delay between the samples [0,+oo]";
	private final static String paramInit = "Initial Proba", tipInit = "Initial probability [0,1]";
	private final static String paramDeviation = "Deviation", tipDeviation = "Expected deviation from Pinit [-1,1]";
	private final static String paramLambda = "Stopping Rule", tipLambda = "Sensitivity value used to apply the Stopping Rule";
	private final static String paramNbSimu = "Simulations", tipNbSimu = "Number of simulations of the CUSUM";
	private final static String paramFile = "Ouput file", tipFile = "CSV file to export the collected values of the CUSUM simulations";
	private final static String paramEpsilon = "Epsilon", tipEpsilon = "Absolute error ]0,1[";
	private final static String paramDelta = "Delta", tipDelta = "Confidence of epsilon ]0,1[";
	private final static String paramCUSUM = "CUSUM algorithm", tipCUSUM = "CUSUM detection algorithm";
	private final static String paramSimu = "Simulation mode", tipSimu="Simulate the CUSUM algorithm to estimate Lambda";
	private final static String paramInde = "Independence check", tipInde = "Check the independence between samples";
	
	private static ArrayList<SMCParameter> parameters;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString(){
		return getName();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public InterfaceAlgorithmWorker createWorker(AbstractModel model, List<AbstractRequirement> requirements) throws PlasmaNotDistibutedException {
		throw new PlasmaNotDistibutedException();
	}

	private static boolean isProba(double value) {
		return value >= 0 && value <= 1; 
	}
	
	@Override
	public InterfaceAlgorithmScheduler createScheduler(AbstractModel model, List<AbstractRequirement> requirements,
													   Map<String, Object> parametersMap) throws PlasmaParameterException {
		double sampleDelay = 0;
		int samplesNb = 0;
		double pInit = 0;
		double deviation = .5;
		double lambda = -1;
		int simuNb = -1;
		String outputFile = null;
		int totalToDo = 0;
		boolean monte_carlo_mode = false;
		boolean cusum_mode = false;
		boolean simulation_mode = false;
		
		try {
			samplesNb = Integer.parseInt(parametersMap.get(paramSamples).toString());
			sampleDelay = Double.parseDouble(parametersMap.get(paramDelay).toString());
			
			if(Boolean.parseBoolean(parametersMap.get("CUSUM algorithm").toString())) {
				cusum_mode = true;
				pInit = Double.parseDouble(parametersMap.get(paramInit).toString());
				deviation = Double.parseDouble(parametersMap.get(paramDeviation).toString());			
				lambda = Double.parseDouble(parametersMap.get(paramLambda).toString());
			}
			if(Boolean.parseBoolean(parametersMap.get("Simulation mode").toString())) {
				simulation_mode = true;
				pInit = Double.parseDouble(parametersMap.get(paramInit).toString());
				deviation = Double.parseDouble(parametersMap.get(paramDeviation).toString());
				simuNb = Integer.parseInt(parametersMap.get(paramSimu).toString());
				outputFile = parametersMap.get(paramFile).toString();
			}
			if(Boolean.parseBoolean(parametersMap.get("Independence check").toString())) {
				monte_carlo_mode = true;
				double epsilon = Double.parseDouble(parametersMap.get(paramEpsilon).toString());
				double delta = Double.parseDouble(parametersMap.get(paramDelta).toString());
				totalToDo = (int) Math.ceil(
						Math.log(2 / delta)
						/ (2 * Math.pow(epsilon, 2))
				);
			}
		} catch(Exception e){
			throw new PlasmaParameterException(e);
		}
		
		if ( !(samplesNb >= 0) )
			throw new PlasmaParameterException(paramSamples+" must be >= 0.");
		if ( !(sampleDelay > 0) )
			throw new PlasmaParameterException(paramDelay+" must be > 0.");
		if (requirements.size() != 1)
			throw new PlasmaParameterException("One requirement exactly has to be selected.");
		if (cusum_mode || simulation_mode) {
			if (!isProba(pInit))
				throw new PlasmaParameterException(paramInit+" must be in [0,1].");
			if (-1 > deviation || deviation > 1)
				throw new PlasmaParameterException(paramDeviation+" must be in [-1,1].");
			if (!isProba(deviation + pInit))
				throw new PlasmaParameterException(paramDeviation+"+"+paramInit+" must be in [0,1].");
		}
		if (monte_carlo_mode && totalToDo<=0) {
			throw new PlasmaParameterException(paramDelta + " and " + paramEpsilon + " must be in [0,1].");
		}
						
		if (cusum_mode)
			return new CuSumAlgorithm(model, requirements.get(0), sampleDelay, samplesNb,
					pInit, deviation, lambda);
		else if (simulation_mode)
			return new CuSumAlgorithm(model, requirements.get(0), sampleDelay, samplesNb,
					pInit, deviation, simuNb, outputFile);
		else if (monte_carlo_mode)
			return new CuSumAlgorithm(model, requirements.get(0), sampleDelay, samplesNb,
					totalToDo);
		else
			throw new PlasmaParameterException("CUSUM mode is undefined.");
	}

	@Override
	public List<SMCParameter> getParametersList() {
		if(parameters == null) {
			parameters = new ArrayList<SMCParameter>();
			parameters.add(new SMCParameter(paramSamples, tipSamples, false));
			parameters.add(new SMCParameter(paramDelay, tipDelay, false));
			
			List<SMCParameter> optionList = new ArrayList<SMCParameter>();
			optionList.add(new SMCParameter(paramInit, tipInit, false));
			optionList.add(new SMCParameter(paramDeviation, tipDeviation, false));
			optionList.add(new SMCParameter(paramLambda, tipLambda, false));
			SMCAlternatives alt1 = new SMCAlternatives(paramCUSUM, tipCUSUM, optionList, null);
			
			optionList = new ArrayList<SMCParameter>();
//			optionList.add(new SMCObjectParameter(pSafeStr, safeTipStr, SMCParameter.Text));
			optionList.add(new SMCParameter(paramInit, tipInit, false));
			optionList.add(new SMCParameter(paramDeviation, tipDeviation, false));
			optionList.add(new SMCParameter(paramNbSimu, tipNbSimu, false));
			optionList.add(new SMCParameter(paramFile, tipFile, false));
			SMCAlternatives alt2 = new SMCAlternatives(paramSimu, tipSimu, optionList, alt1);
			alt1.setNext(alt2);
			
			optionList = new ArrayList<SMCParameter>();
			optionList.add(new SMCParameter(paramEpsilon, tipEpsilon, false));
			optionList.add(new SMCParameter(paramDelta, tipDelta, false));
			SMCAlternatives alt3 = new SMCAlternatives(paramInde, tipInde, optionList, alt1);
			alt2.setNext(alt3);
			
			parameters.add(alt1);
		}
		return parameters;
	}

	@Override
	public Class<? extends ServerResource> getResourceHandler() {
		return null;
	}

	@Override
	public boolean isDistributed() {
		return false;
	}

	@Override
	public void fillParametersMap(Map<String, Object> parametersMap, String[] parameters) throws PlasmaParameterException {
		throw new PlasmaParameterException("CUSUM algorithm cannot be run in this interface.");
//
//		try{
//			if (parameters.length>=6) {
//				parametersMap.put(paramInit, Double.parseDouble(parameters[0]));
//				parametersMap.put(deviationStr, Double.parseDouble(parameters[1]));
//				parametersMap.put(lambdaStr, Double.parseDouble(parameters[2]));
//				parametersMap.put(samplesStr, Double.parseDouble(parameters[3]));
//				parametersMap.put(sampleDelayStr, Double.parseDouble(parameters[4]));
//				parametersMap.put(pSafeStr, parameters[5]);
//			} else
//				throw new PlasmaRunException("Not enough parameters given for the algorithm Cusum.");
//		} catch(NumberFormatException e) {
//			throw new PlasmaParameterException(e);
//		}
	}

}
