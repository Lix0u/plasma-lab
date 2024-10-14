/**
 * This file is part of fr.inria.plasmalab.nested.
 *
 * fr.inria.plasmalab.nested is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.nested is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.nested.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.nested.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.InterfaceAlgorithmWorker;
import fr.inria.plasmalab.algorithm.data.SMCParameter;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaNotDistibutedException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class NestedAlgorithmFactory implements InterfaceAlgorithmFactory  {
	private final static String id ="nestedalgo";
	private final static String name ="Seq. nested";
	private final static String description ="Nested hypothesis testing algorithm.";
	private final static String paramAlpha = "Alpha", tipAlpha = "Probability of false positives, ]0,1[";
	private final static String paramBeta = "Beta", tipBeta = "Probability of false negatives, ]0,1[";
	private final static String paramDelta = "Delta", tipDelta = "Indifference region, <= Proba and in (0,1-Proba)";
	
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
	public boolean isDistributed() {
		return false;
	}

	@Override
	public List<SMCParameter> getParametersList() {
		if(parameters == null){
			parameters = new ArrayList<SMCParameter>();
			parameters.add(new SMCParameter(paramAlpha, tipAlpha, false));
			parameters.add(new SMCParameter(paramBeta, tipBeta, false));
			parameters.add(new SMCParameter(paramDelta, tipDelta, false));
		}
		return parameters;
	}

	@Override
	public void fillParametersMap(Map<String, Object> parametersMap, String[] parameters)
			throws PlasmaParameterException {
		try{
			if(parameters.length>=3){
				parametersMap.put(paramAlpha, Double.parseDouble(parameters[0]));
				parametersMap.put(paramBeta, Double.parseDouble(parameters[1]));
				parametersMap.put(paramDelta, Double.parseDouble(parameters[2]));
			}
			else
				throw new PlasmaParameterException("Wrong number of parameters for the " + name + " algorithm.");
		} catch(NumberFormatException e){
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
	}

	@Override
	public InterfaceAlgorithmScheduler createScheduler(AbstractModel model, List<AbstractRequirement> requirements,
			Map<String, Object> parameters) throws PlasmaParameterException {
		double alpha=-1., beta=-1., delta=-1.;
		try{
			if(parameters.containsKey(paramAlpha))
				alpha = Double.parseDouble(parameters.get(paramAlpha).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramAlpha);
			
			if(parameters.containsKey(paramBeta))
				beta = Double.parseDouble(parameters.get(paramBeta).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramBeta);

			if(parameters.containsKey(paramDelta))
				delta = Double.parseDouble(parameters.get(paramDelta).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramDelta);
		}
		catch(NumberFormatException e) {
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
		
		if(alpha<=0||alpha>=1)
			throw new PlasmaParameterException(paramAlpha + " must be in ]0,1[.");
		if(beta<=0||beta>=1)
			throw new PlasmaParameterException(paramBeta + " must be in ]0,1[.");
		if(delta<=0||delta>=1)
			throw new PlasmaParameterException(paramDelta + " must be in ]0,1[.");
		
		boolean distributed = false;
		if(parameters.containsKey("distributed"))
			distributed = Boolean.parseBoolean(parameters.get("distributed").toString());
		if(distributed)
			throw new PlasmaNotDistibutedException();
		
		return new NestedAlgorithm(model, requirements, alpha, beta, delta, id);
	}
	
	@Override
	public InterfaceAlgorithmWorker createWorker(AbstractModel model, List<AbstractRequirement> requirements)
			throws PlasmaParameterException {
		throw new PlasmaNotDistibutedException();
	}

	@Override
	public Class<?> getResourceHandler() {
		return null;
	}

}
