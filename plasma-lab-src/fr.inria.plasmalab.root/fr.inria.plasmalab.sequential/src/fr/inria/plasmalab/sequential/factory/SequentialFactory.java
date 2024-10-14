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
package fr.inria.plasmalab.sequential.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.restlet.resource.ServerResource;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.InterfaceAlgorithmWorker;
import fr.inria.plasmalab.algorithm.data.SMCOption;
import fr.inria.plasmalab.algorithm.data.SMCParameter;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.sequential.algorithm.PrunningMDPAlgorithm;
import fr.inria.plasmalab.sequential.algorithm.SequentialAlgorithm;
import fr.inria.plasmalab.sequential.algorithm.SequentialMDPAlgorithm;
import fr.inria.plasmalab.sequential.restlet.server.SequentialServerResource;
import fr.inria.plasmalab.sequential.scheduler.OutOrderSequentialScheduler;
import fr.inria.plasmalab.sequential.scheduler.SequentialScheduler;
import fr.inria.plasmalab.sequential.worker.SequentialWorker;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class SequentialFactory implements InterfaceAlgorithmFactory {
	private final static String id ="sequential";
	private final static String name ="Sequential";
	private final static String description ="Sequential hypothesis testing algorithm .";
	private final static String paramAlpha = "Alpha", tipAlpha = "Probability of false positives, ]0,1[";
	private final static String paramBeta = "Beta", tipBeta = "Probability of false negatives, ]0,1[";
	private final static String paramDelta = "Delta", tipDelta = "Indifference region, <= Proba and in (0,1-Proba)";
	private final static String paramProba = "Proba", tipProba = "Probability, ]0,1[";
	private final static String paramMDP = "MDP", tipMDP = "Use the lightweight SPRT algorithm for Markov Decision Processes";
	private final static String paramSched = "M", tipSched = "Number of scheduler (positive)";
	private final static String paramOoO = "Out of Order", tipOoO = "Process observations out of order";

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
		return new SequentialWorker(model, requirements);
	}

	@Override
	public InterfaceAlgorithmScheduler createScheduler(AbstractModel model, List<AbstractRequirement> requirements,
			Map<String, Object> parametersMap) throws PlasmaParameterException {
		double alpha=-1., beta=-1., delta=-1., proba=-1.;
		boolean mdp = false, prunning =false, oorder = false;
		int m = 1;
		try{
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

			if(parametersMap.containsKey(paramSched)) {
				mdp = true;
				m = Integer.parseInt(parametersMap.get(paramSched).toString());
			}
			//if(parametersMap.containsKey("Prunning"))
			//	prunning = Boolean.parseBoolean(parametersMap.get("Prunning").toString());
			if(parametersMap.containsKey(paramOoO))
				oorder = Boolean.parseBoolean(parametersMap.get(paramOoO).toString());			
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
		if(m<1)
			throw new PlasmaParameterException(paramSched + " must be a positive integer.");
		
		boolean distributed = false;
		if(parametersMap.containsKey("distributed"))
			distributed = Boolean.parseBoolean(parametersMap.get("distributed").toString());
		
		if(distributed){
			if(mdp)
				throw new PlasmaParameterException("The MDP algorithm cannot be used in distributed mode yet.");
			if(oorder)
				return new OutOrderSequentialScheduler(model, requirements, alpha, beta, delta, proba, id);
			else
				return new SequentialScheduler(model, requirements, alpha, beta, delta, proba, id);
		}
		else {
			if(prunning)
				return new PrunningMDPAlgorithm(model, requirements, alpha, beta, delta, proba, m, id);
			if(mdp)
				return new SequentialMDPAlgorithm(model, requirements, alpha, beta, delta, proba, m, id);
			else
				return new SequentialAlgorithm(model, requirements, alpha, beta, delta, proba, id);
		}
	}

	@Override
	public List<SMCParameter> getParametersList() {
		if(parameters == null){
			parameters = new ArrayList<SMCParameter>();
			parameters.add(new SMCParameter(paramAlpha, tipAlpha, false));
			parameters.add(new SMCParameter(paramBeta, tipBeta, false));
			parameters.add(new SMCParameter(paramDelta, tipDelta, false));
			parameters.add(new SMCParameter(paramProba, tipProba, false));
			List<SMCParameter> options = new ArrayList<SMCParameter>();
			options.add(new SMCParameter(paramSched, tipSched, false));
//			options.add(new SMCOption("Prunning", "try to refine schedulers for each properties", new ArrayList<SMCParameter>()));
//			List<SMCParameter> alternatives = new ArrayList<SMCParameter>();
//			alternatives.add(new SMCParameter("Pr >? Proba", "Test if the probability is superior to the threshold Proba", true));
//			alternatives.add(new SMCParameter("Pr <? Proba", "Test if the probability is inferior to the threshold Proba", true));
//			options.add(new SMCAlternatives("alt", "", alternatives));
			parameters.add(new SMCOption(paramMDP, tipMDP, options));
			parameters.add(new SMCOption(paramOoO, tipOoO, new ArrayList<SMCParameter>()));
		}
		return parameters;
	}
	
	@Override
	public void fillParametersMap(Map<String, Object> parametersMap, String[] parameters) throws PlasmaParameterException {
		try{
			if(parameters.length>=4){
				parametersMap.put(paramAlpha, Double.parseDouble(parameters[0]));
				parametersMap.put(paramBeta, Double.parseDouble(parameters[1]));
				parametersMap.put(paramDelta, Double.parseDouble(parameters[2]));
				parametersMap.put(paramProba, Double.parseDouble(parameters[3]));
				if(parameters.length>=5)
					parametersMap.put(paramSched, Integer.parseInt(parameters[4]));
			}
			else
				throw new PlasmaParameterException("Wrong number of parameters for the " + name + " algorithm.");
		} catch(NumberFormatException e){
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
	}

	@Override
	public Class<? extends ServerResource> getResourceHandler() {
		return SequentialServerResource.class;
	}

	@Override
	public boolean isDistributed() {
		return true;
	}

}
