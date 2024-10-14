/**
 * This file is part of fr.inria.plasmalab.montecarlo.
 *
 * fr.inria.plasmalab.montecarlo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.montecarlo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.montecarlo.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.montecarlo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.InterfaceAlgorithmWorker;
import fr.inria.plasmalab.algorithm.data.SMCOption;
import fr.inria.plasmalab.algorithm.data.SMCParameter;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.montecarlo.algorithm.ChernoffMDPAlgortihm;
import fr.inria.plasmalab.montecarlo.algorithm.MontecarloAlgorithm;
import fr.inria.plasmalab.montecarlo.scheduler.ChernoffMDPScheduler;
import fr.inria.plasmalab.montecarlo.scheduler.MontecarloScheduler;
import fr.inria.plasmalab.montecarlo.server.MontecarloServerResource;
import fr.inria.plasmalab.montecarlo.worker.ChernoffMDPWorker;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class MontecarloFactory implements InterfaceAlgorithmFactory {
	private final static String id ="montecarlo";
	private final static String name ="Monte Carlo";
	private final static String description ="Monte Carlo estimation algorithm.";
	private final static String paramTotalSamples = "Total samples", tipTotalSamples = "Number of simulations (needed if delta or epsilon are empty).";
	private final static String paramEpsilon = "Epsilon", tipEpsilon = "Absolute error ]0,1[ (needed if total samples is empty).";
	private final static String paramDelta = "Delta", tipDelta = "Confidence of epsilon ]0,1[ (needed if total samples is empty).";
	private final static String paramMDP = "MDP", tipMDP = "Use the lightweight estimation algorithm for Markov Decision Processes";
	private final static String paramSched = "M", tipSched = "Number of schedulers (positive)";
	
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
		return new ChernoffMDPWorker(model, requirements);
	}

	@Override
	public InterfaceAlgorithmScheduler createScheduler(AbstractModel model, 
			List<AbstractRequirement> requirements, Map<String, Object> parametersMap) throws PlasmaParameterException {
		int totalcount = -1;
		int m = 1;
		boolean mdp = false;
		double delta = 0.0;
		double epsilon = 0.0;
		try{
			if(parametersMap.containsKey(paramTotalSamples) && !parametersMap.get(paramTotalSamples).toString().isEmpty()) 
				totalcount = Integer.parseInt(parametersMap.get(paramTotalSamples).toString());
			if(parametersMap.containsKey(paramDelta) && !parametersMap.get(paramDelta).toString().isEmpty())
				delta = Double.parseDouble(parametersMap.get(paramDelta).toString());
			if(parametersMap.containsKey(paramEpsilon) && !parametersMap.get(paramEpsilon).toString().isEmpty())
				epsilon = Double.parseDouble(parametersMap.get(paramEpsilon).toString());
			if(parametersMap.containsKey(paramSched)) {
				mdp = true;
				m = Integer.parseInt(parametersMap.get(paramSched).toString());
			}	
		}
		catch(NumberFormatException e){
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
		if (totalcount == -1 && (delta == 0 || epsilon == 0))
			throw new PlasmaParameterException("Not enough parameters.");

		if (totalcount == -1) {
			if (m == 1) {
				// apply normal chernoff bound
				totalcount = (int) Math.ceil(
						Math.log(2 / delta)
						/ (2 * Math.pow(epsilon, 2))
					);
			}
			else {
				// apply refined chernoff bound for multiple scheduler
				// N = ceil(-log(1-exp(log(1-delta)/M))/(2*epsilon*epsilon))
				totalcount = (int) 	Math.ceil(
										-Math.log( 
											1-Math.exp(Math.log(1-delta)/m)
										)
										/(2* Math.pow(epsilon, 2))
									);
			}
		}
		
		if(totalcount<=0)
			throw new PlasmaParameterException("Cannot compute a positive number of simulations from the parameters. ");

		boolean distributed = false;
		if(parametersMap.containsKey("distributed"))
			distributed = Boolean.parseBoolean(parametersMap.get("distributed").toString());
		
		if(distributed){
			if(mdp)
				return new ChernoffMDPScheduler(model, requirements, totalcount, m, id);
			else
				return new MontecarloScheduler(model, requirements, totalcount, id);
		}
		else{
			if(mdp)
				return new ChernoffMDPAlgortihm(model, requirements, totalcount, m, id);
			else
				return new MontecarloAlgorithm(model, requirements, totalcount, id);
		}
	}

	@Override
	public List<SMCParameter> getParametersList() {
		if(parameters == null) {
			parameters = new ArrayList<SMCParameter>();
			parameters.add(new SMCParameter(paramTotalSamples,tipTotalSamples, false));
			parameters.add(new SMCParameter(paramEpsilon, tipEpsilon, false));
			parameters.add(new SMCParameter(paramDelta, tipDelta, false));
			List<SMCParameter> options = new ArrayList<SMCParameter>();
			options.add(new SMCParameter(paramSched, tipSched, false));
			parameters.add(new SMCOption(paramMDP, tipMDP, options));
		}
		return parameters;
	}
	
	@Override
	public void fillParametersMap(Map<String, Object> parametersMap, String[] parameters) throws PlasmaParameterException {
		try{
			if(parameters.length==1)
				parametersMap.put(paramTotalSamples, Integer.parseInt(parameters[0]));
			else if(parameters.length>=2){
				parametersMap.put(paramEpsilon, Double.parseDouble(parameters[0]));
				parametersMap.put(paramDelta, Double.parseDouble(parameters[1]));
				if(parameters.length>=3){
					parametersMap.put(paramSched, Integer.parseInt(parameters[2]));
				}
			}
			else
				throw new PlasmaParameterException("Wrong number of parameters for the " + name + " algorithm.");
		} catch(NumberFormatException e){
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
	}
	
	@Override
	public Class<MontecarloServerResource> getResourceHandler() {
		return MontecarloServerResource.class;
	}

	@Override
	public boolean isDistributed() {
		return true;
	}

}
