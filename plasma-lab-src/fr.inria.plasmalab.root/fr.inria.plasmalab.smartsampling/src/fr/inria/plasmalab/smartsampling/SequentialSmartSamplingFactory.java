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

import org.restlet.resource.ServerResource;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.InterfaceAlgorithmWorker;
import fr.inria.plasmalab.algorithm.data.SMCAlternatives;
import fr.inria.plasmalab.algorithm.data.SMCParameter;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.smartsampling.algorithm.SequentialSmartSamplingAlgorithm;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaNotDistibutedException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class SequentialSmartSamplingFactory implements InterfaceAlgorithmFactory {
	private final static String id ="seqsmartsampling";
	
	private final static String name ="Seq. smart sampling";
	private final static String description ="Smart sampling sequential hypothesis testing for non-deterministic models.";
	private final static String paramAlpha = "Alpha", tipAlpha = "Probability of false positives, ]0,1[";
	private final static String paramBeta = "Beta", tipBeta = "Probability of false negatives, ]0,1[";
	private final static String paramDelta = "Delta", tipDelta = "Indifference region, <= Proba and in (0,1-Proba)";
	private final static String paramProba = "Proba", tipProba = "Probability, ]0,1[";
	private final static String paramBudget = "Budget", tipBudget = "Number of simulations for all the schedulers at each iteration.";
	private final static String paramMin = "Minimum", tipMin = "Check if probability is below the threshold.";
	private final static String paramMax = "Maximum", tipMax = "Check if probability is above the threshold.";
	
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

	@Override
	public InterfaceAlgorithmScheduler createScheduler(AbstractModel model, List<AbstractRequirement> requirements,
			Map<String, Object> parametersMap) throws PlasmaParameterException {
		double alpha=-1., beta=-1., delta=-1., proba=-1.;
		int budget = 0;
		boolean maximum = true;
		try{
			if (parametersMap.containsKey(paramMin))
				maximum = !(Boolean.parseBoolean(parametersMap.get(paramMin).toString()));
			else if (parametersMap.containsKey(paramMax))
				maximum = Boolean.parseBoolean(parametersMap.get(paramMax).toString());
			
			if(parametersMap.containsKey(paramAlpha))
				alpha = Double.parseDouble(parametersMap.get(paramAlpha).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramAlpha);
			
			if(parametersMap.containsKey(paramBeta))
				beta = Double.parseDouble(parametersMap.get(paramBeta).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramBeta);

			if(parametersMap.containsKey(paramDelta))
				delta = Double.parseDouble(parametersMap.get(paramDelta).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramDelta);

			if(parametersMap.containsKey(paramProba))
				proba = Double.parseDouble(parametersMap.get(paramProba).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramProba);
			
			if(parametersMap.containsKey(paramBudget))
				budget = Integer.parseInt(parametersMap.get(paramBudget).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramBudget);
		} 
		catch(NumberFormatException e) {
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}		
		
		if(alpha<=0||alpha>=1)
			throw new PlasmaParameterException(paramAlpha + " must be in ]0,1[.");
		if(beta<=0||beta>=1)
			throw new PlasmaParameterException(paramBeta + " must be in ]0,1[.");
		if(proba<=0||proba>=1)
			throw new PlasmaParameterException(paramProba + " must be in ]0,1[.");
		if(delta>proba||delta<0||delta>(1-proba))
			throw new PlasmaParameterException(paramDelta + " must be <= Proba and in (0,1-Proba).");
		if(budget<1)
			throw new PlasmaParameterException(paramBudget + " must be a positive integer.");
		
		boolean distributed = false;
		if(parametersMap.containsKey("distributed"))
			distributed = Boolean.parseBoolean(parametersMap.get("distributed").toString());
		
		if(distributed)
			throw new PlasmaNotDistibutedException();
		else
			return new SequentialSmartSamplingAlgorithm(model, requirements, alpha, beta, delta, proba, budget, maximum, id);
	}

	@Override
	public List<SMCParameter> getParametersList() {
		if(parameters == null){
			parameters = new ArrayList<SMCParameter>();
			SMCAlternatives alt1 = new SMCAlternatives(paramMin, tipMin, new ArrayList<SMCParameter>(), null);
			SMCAlternatives alt2 = new SMCAlternatives(paramMax, tipMax, new ArrayList<SMCParameter>(), alt1);
			alt1.setNext(alt2);
			parameters.add(alt1);
			parameters.add(new SMCParameter(paramAlpha, tipAlpha, false));
			parameters.add(new SMCParameter(paramBeta, tipBeta, false));
			parameters.add(new SMCParameter(paramDelta, tipDelta, false));
			parameters.add(new SMCParameter(paramProba, tipProba, false));
			parameters.add(new SMCParameter(paramBudget, tipBudget, false));	
		}
		return parameters;
	}
	
	@Override
	public void fillParametersMap(Map<String, Object> parametersMap, String[] parameters) throws PlasmaParameterException {
		try{
			if(parameters.length>=6){
				parametersMap.put(paramMax, Boolean.parseBoolean(parameters[0]));
				parametersMap.put(paramAlpha, Double.parseDouble(parameters[1]));
				parametersMap.put(paramBeta, Double.parseDouble(parameters[2]));
				parametersMap.put(paramDelta, Double.parseDouble(parameters[3]));
				parametersMap.put(paramProba, Double.parseDouble(parameters[4]));
				parametersMap.put(paramBudget, Integer.parseInt(parameters[5]));
			}
			else
				throw new PlasmaParameterException("Wrong number of parameters for the " + name + " algorithm.");
		} catch(NumberFormatException e){
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
	}

	@Override
	public Class<? extends ServerResource> getResourceHandler() {
		return null;
	}

	@Override
	public boolean isDistributed() {
		return false;
	}

}
