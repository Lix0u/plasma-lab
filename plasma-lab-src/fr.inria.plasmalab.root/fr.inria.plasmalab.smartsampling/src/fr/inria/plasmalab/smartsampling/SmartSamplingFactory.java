/**
 * This file is part of fr.inria.plasmalab.smartsampling.
 *
 * fr.inria.plasmalab.smartsampling is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.smartsampling is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.smartsampling.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.smartsampling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.InterfaceAlgorithmWorker;
import fr.inria.plasmalab.algorithm.data.SMCAlternatives;
import fr.inria.plasmalab.algorithm.data.SMCOption;
import fr.inria.plasmalab.algorithm.data.SMCParameter;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.smartsampling.algorithm.SmartSamplingAlgorithm;
import fr.inria.plasmalab.smartsampling.algorithm.SmartSamplingScheduler;
import fr.inria.plasmalab.smartsampling.algorithm.SmartSamplingWorker;
import fr.inria.plasmalab.smartsampling.server.SmartSamplingServerResource;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class SmartSamplingFactory implements InterfaceAlgorithmFactory {
	private final static String id ="smartsampling";
	private final static String name ="Smart sampling";
	private final static String description ="Smart sampling Monte Carlo estimation algorithm for non-deterministic models.";
	
	// mandatory parameters
	private final static String paramEpsilon = "Epsilon", tipEpsilon = "Absolute error ]0,1[";
	private final static String paramDelta = "Delta", tipDelta = "Confidence of epsilon ]0,1[";
	private final static String paramBudget = "Budget", 
			tipBudget = "Number of simulations for all the schedulers at each iteration. Must be greater than the Chernoff bound.";
	private final static String paramMin = "Minimum", tipMin = "Compute minimum probability.";
			
	// optional parameters
	private final static String paramMaxBudget = "Max budget", 
			tipMaxBudget = "Maximum number of simulations for the second iterations. Must be greater than the simulation budget. Equal to Budget if empty.";
	private final static String paramFactor = "Reduction Factor", tipFactor = "Reduction factor between each iteration. Equal to 2 if empty.";
	private final static String paramInit = "Initial probability",
			tipInit = "Initial probability for the iterations. If 0 the initial probability is estimated in the first iteration."; // unused in the GUI
	private final static String paramRewardName = "Reward name", tipRewardName = "Name of the reward (with quotes). Leave empty to compute probabilities.";
	private final static String paramThreshold = "Threshold", tipThreshold = "Probability threshold that the formula is satisfied";
	private final static String paramAlpha = "Alpha", tipAlpha = "Confidence of the SPRT test";

	// unused parameters (these parameters are only added in the GUI)
	private final static String paramMax = "Maximum", tipMax = "Compute maximum probability."; // adding minimum with a true or false value is sufficient 
	//private final static String paramReward = "Reward", 
	//		tipReward = "Computing the minimum or maximum of a reward"; // adding a reward name is sufficient to enable rewards
	private final static String paramSPRT = "SPRT", 
			tipSPRT = "Perform an additional SPRT test when checking a reachability reward property"; // adding threshold and alpha are sufficient to enable SPRT

	
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
	public InterfaceAlgorithmWorker createWorker(AbstractModel model, List<AbstractRequirement> requirements) {
		return new SmartSamplingWorker(model, requirements);
	}

	@Override
	public InterfaceAlgorithmScheduler createScheduler(AbstractModel model, List<AbstractRequirement> requirements,
			Map<String, Object> parametersMap) throws PlasmaParameterException {
		int totalcount = -1;
		boolean maximum = false;
		double delta = 0;
		double epsilon = 0;
		double initProba = 0;
		boolean sprt = false;
		double threshold = 0;
		double alpha = 0;
		int schedBudget = 0;
		int budget = 0;
		int factor = 2;
		String reward = "";
		
		try{
			if (parametersMap.containsKey(paramMin))
				maximum = !(Boolean.parseBoolean(parametersMap.get(paramMin).toString()));
			else if (parametersMap.containsKey(paramMax))
				maximum = Boolean.parseBoolean(parametersMap.get(paramMax).toString());

			if (parametersMap.containsKey(paramEpsilon))
				epsilon = Double.parseDouble(parametersMap.get(paramEpsilon).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramEpsilon);
			
			if (parametersMap.containsKey(paramDelta))
				delta = Double.parseDouble(parametersMap.get(paramDelta).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramDelta);
			
			if (parametersMap.containsKey(paramBudget))
				budget = Integer.parseInt(parametersMap.get(paramBudget).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramBudget);
			schedBudget = budget;
						
			if(parametersMap.containsKey(paramMaxBudget) && !parametersMap.get(paramMaxBudget).toString().isEmpty())
				schedBudget = Integer.parseInt(parametersMap.get(paramMaxBudget).toString());
			if (parametersMap.containsKey(paramFactor) && !parametersMap.get(paramFactor).toString().isEmpty())
				factor = Integer.parseInt(parametersMap.get(paramFactor).toString());
			if(parametersMap.containsKey(paramInit) && !parametersMap.get(paramInit).toString().isEmpty())
				initProba = Double.parseDouble(parametersMap.get(paramInit).toString());
			if(parametersMap.containsKey(paramRewardName))
				reward = parametersMap.get(paramRewardName).toString();
			if(parametersMap.containsKey(paramThreshold) && parametersMap.containsKey(paramAlpha)) {
				sprt = true;
				threshold = Double.parseDouble(parametersMap.get(paramThreshold).toString());
				alpha = Double.parseDouble(parametersMap.get(paramAlpha).toString());
			}
			if(parametersMap.containsKey("sleep"))
				SmartSamplingScheduler.SLEEP_ORDER = Integer.parseInt(parametersMap.get("sleep").toString());
		}
		catch(NumberFormatException e){
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
		
		//Check decimal numbers
		if(delta<=0||delta>=1)
			throw new PlasmaParameterException(paramDelta + " must be in ]0,1[.");
		if(epsilon<=0||epsilon>=1)
			throw new PlasmaParameterException(paramEpsilon + " must be in ]0,1[.");
		if(initProba<0||initProba>1)
			throw new PlasmaParameterException(paramInit + " must be in [0,1].");
		if (sprt) {
			if(threshold<=0||threshold>=1)
				throw new PlasmaParameterException(paramThreshold + " must be in ]0,1[.");
			if(alpha<=0||alpha>=1)
				throw new PlasmaParameterException(paramAlpha + " must be in ]0,1[.");
		}
		
		//Check total count and budget
		totalcount = (int) Math.ceil(Math.log(2 / delta) / (2 * Math.pow(epsilon, 2)));
		if(budget < totalcount)
			throw new PlasmaParameterException("Not enough simulation " + paramBudget);
		if(schedBudget < budget)
			throw new PlasmaParameterException(paramMaxBudget + " must be greater than simulation " + paramBudget);
		if(factor <= 1)
			throw new PlasmaParameterException(paramFactor + " must be greater than 2.");
		
		// Create scheduler
		boolean distributed = false;
		if(parametersMap.containsKey("distributed")){
			distributed = Boolean.parseBoolean(parametersMap.get("distributed").toString());
		}
		if(distributed){
			if (reward.isEmpty())
				return new SmartSamplingScheduler(model, requirements, maximum, schedBudget, budget, factor, epsilon, delta, initProba, id);
			else if(sprt)
				return new SmartSamplingScheduler(model, requirements, maximum, schedBudget, budget, factor, epsilon, delta, reward, threshold, alpha, id);
			else
				return new SmartSamplingScheduler(model, requirements, maximum, schedBudget, budget, factor, epsilon, delta, reward, id);		
		}
		else {
			if (reward.isEmpty())
				return new SmartSamplingAlgorithm(model, requirements, maximum, schedBudget, budget, factor, epsilon, delta, initProba, id);
			else if(sprt)
				return new SmartSamplingAlgorithm(model, requirements, maximum, schedBudget, budget, factor, epsilon, delta, reward, threshold, alpha, id);
			else
				return new SmartSamplingAlgorithm(model, requirements, maximum, schedBudget, budget, factor, epsilon, delta, reward, id);
		}
	}

	@Override
	public List<SMCParameter> getParametersList() {
		if(parameters == null) {
			parameters = new ArrayList<SMCParameter>();
			
			// common parameters
			SMCAlternatives altmin = new SMCAlternatives(paramMin, tipMin, new ArrayList<SMCParameter>(), null);
			SMCAlternatives altmax = new SMCAlternatives(paramMax, tipMax, new ArrayList<SMCParameter>(), altmin);
			altmin.setNext(altmax);
			parameters.add(altmin);
			parameters.add(new SMCParameter(paramEpsilon, tipEpsilon, false));
			parameters.add(new SMCParameter(paramDelta, tipDelta, false));
			parameters.add(new SMCParameter(paramBudget, tipBudget, false));
			parameters.add(new SMCParameter(paramMaxBudget, tipMaxBudget, false));
			parameters.add(new SMCParameter(paramFactor, tipFactor, false));
			parameters.add(new SMCParameter(paramInit, tipInit, false));
			parameters.add(new SMCParameter(paramRewardName, tipRewardName, false));

			// sprt option
			List<SMCParameter> optionsList = new ArrayList<SMCParameter>();
			optionsList.add(new SMCParameter(paramThreshold, tipThreshold, false));
			optionsList.add(new SMCParameter(paramAlpha, tipAlpha, false));
			parameters.add(new SMCOption(paramSPRT, tipSPRT, optionsList));
		}
		return parameters;
	}
	
	@Override
	public void fillParametersMap(Map<String, Object> parametersMap, String[] parameters) throws PlasmaParameterException {
		try{
			if(parameters.length>=7){
				parametersMap.put(paramMax, Boolean.parseBoolean(parameters[0]));
				parametersMap.put(paramEpsilon, Double.parseDouble(parameters[1]));
				parametersMap.put(paramDelta, Double.parseDouble(parameters[2]));
				parametersMap.put(paramBudget, Double.parseDouble(parameters[3]));
				parametersMap.put(paramMaxBudget, Double.parseDouble(parameters[4]));
				parametersMap.put(paramFactor, Double.parseDouble(parameters[5]));
				parametersMap.put(paramInit, Double.parseDouble(parameters[6]));
				
				if(parameters.length>=8){
					parametersMap.put(paramRewardName, parameters[7]);
					if (parameters.length>=10) {
						parametersMap.put(paramThreshold, Double.parseDouble(parameters[8]));
						parametersMap.put(paramAlpha, Double.parseDouble(parameters[9]));
					}
				}
			}
			else
				throw new PlasmaParameterException("Wrong number of parameters for the " + name + " algorithm.");
		} catch(NumberFormatException e){
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
	}


	@Override
	public Class<SmartSamplingServerResource> getResourceHandler() {
		return SmartSamplingServerResource.class;
	}

	@Override
	public boolean isDistributed() {
		return true;
	}
}
