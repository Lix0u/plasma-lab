/**
 * This file is part of fr.inria.plasmalab.importancesampling.
 *
 * fr.inria.plasmalab.importancesampling is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.importancesampling is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.importancesampling.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.importancesampling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.InterfaceAlgorithmWorker;
import fr.inria.plasmalab.algorithm.data.SMCOption;
import fr.inria.plasmalab.algorithm.data.SMCParameter;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.importancesampling.algorithm.CrossEntropyAlgorithm;
import fr.inria.plasmalab.importancesampling.algorithm.CrossEntropyScheduler;
import fr.inria.plasmalab.importancesampling.algorithm.CrossEntropyWorker;
import fr.inria.plasmalab.importancesampling.server.CEServerResource;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.InterfaceSamplingSimulator;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class CrossEntropyFactory implements InterfaceAlgorithmFactory {
	private final static String id ="crossentropy";
	private final static String name ="Cross entropy";
	private final static String description ="Cross entropy algorithm for rare event estimation with importance sampling.";
	private final static String 
			paramTotalSamples = "Total samples",
			tipTotalSamples   = "Number of simulation to run at each iteration.",
			paramSmoothing = "Smoothing",
			tipSmoothing = "Reduce the probability of commands that have never been taken.",
			paramMaxIteration = "Iterations",
			tipMaxIteration = "Maximum number of iteration of the algorithm.",
			paramNbParameters = "Nb parameters",
			tipNbParameters = "Number of sampling parameters (their name are lambda1, lambda2 ...).",
			paramInit = "Initial values",
			tipInit = "Compute initial values of the parameters.";
		
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
		return new CrossEntropyWorker(model, requirements);
	}

	@Override
	public InterfaceAlgorithmScheduler createScheduler(AbstractModel model, 
			List<AbstractRequirement> requirements, Map<String, Object> parametersMap) throws PlasmaParameterException {
		int totalcount = -1;
		double smoothing = 0.9;
		int maxIteration = 0;
		int nbParameters = 0;
		boolean init = false;
		
		try{
			if (parametersMap.containsKey(paramTotalSamples))
				totalcount = Integer.parseInt(parametersMap.get(paramTotalSamples).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramTotalSamples);
			if (parametersMap.containsKey(paramSmoothing))
				smoothing = Double.parseDouble(parametersMap.get(paramSmoothing).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramSmoothing);
			if (parametersMap.containsKey(paramMaxIteration))
				maxIteration = Integer.parseInt(parametersMap.get(paramMaxIteration).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramMaxIteration);
			if (parametersMap.containsKey(paramNbParameters))
				nbParameters = Integer.parseInt(parametersMap.get(paramNbParameters).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramNbParameters);
			if (parametersMap.containsKey(paramInit))
				init = 	Boolean.parseBoolean(parametersMap.get(paramInit).toString());
		}
		catch(NumberFormatException e){
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
				
		if(totalcount<=0)
			throw new PlasmaParameterException(paramTotalSamples + " must be a positive value.");
		if(smoothing>=1.0 || smoothing<0.0 )
			throw new PlasmaParameterException(paramSmoothing + " must be in [0,1[.");
		if(maxIteration<=0)
			throw new PlasmaParameterException(paramMaxIteration + " must be a positive value.");
		if (nbParameters == 0)
			throw new PlasmaParameterException(paramNbParameters + " must be a positive value.");
		if (init && !(model instanceof InterfaceSamplingSimulator))
			throw new PlasmaParameterException("This model does not support parameters initialization.");
		
		boolean distributed = false;
		if(parametersMap.containsKey("distributed"))
			distributed = Boolean.parseBoolean(parametersMap.get("distributed").toString());
		if(distributed)
			return new CrossEntropyScheduler(model, requirements, totalcount, smoothing, maxIteration, nbParameters, init, id);
		else
			return new CrossEntropyAlgorithm(model, requirements, totalcount, smoothing, maxIteration, nbParameters, init, id);
	}

	@Override
	public List<SMCParameter> getParametersList() {
		if(parameters == null){
			parameters = new ArrayList<SMCParameter>();
			parameters.add(new SMCParameter(paramTotalSamples,tipTotalSamples, false));
			parameters.add(new SMCParameter(paramSmoothing,tipSmoothing, false));
			parameters.add(new SMCParameter(paramMaxIteration,tipMaxIteration, false));
			parameters.add(new SMCParameter(paramNbParameters,tipNbParameters, false));
			List<SMCParameter> options = new ArrayList<SMCParameter>();
			parameters.add(new SMCOption(paramInit, tipInit, options));
		}
		return parameters;
	}
	
	@Override
	public void fillParametersMap(Map<String, Object> parametersMap, String[] parameters) throws PlasmaParameterException {
		try{
			if(parameters.length==4) {
				parametersMap.put(paramTotalSamples, Integer.parseInt(parameters[0]));
				parametersMap.put(paramSmoothing, Double.parseDouble(parameters[1]));
				parametersMap.put(paramMaxIteration, Integer.parseInt(parameters[2]));
				parametersMap.put(paramNbParameters, Integer.parseInt(parameters[3]));
			}
			else
				throw new PlasmaParameterException("Wrong number of parameters for the " + name + " algorithm.");
		} catch(NumberFormatException e){
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
	}
	
	@Override
	public Class<CEServerResource> getResourceHandler() {
		return CEServerResource.class;
	}

	@Override
	public boolean isDistributed() {
		return true;
	}


}

