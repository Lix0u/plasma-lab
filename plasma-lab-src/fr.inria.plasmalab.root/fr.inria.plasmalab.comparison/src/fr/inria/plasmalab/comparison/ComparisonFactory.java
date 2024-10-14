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
package fr.inria.plasmalab.comparison;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.restlet.resource.ServerResource;

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
public class ComparisonFactory implements InterfaceAlgorithmFactory {
	private final static String id ="comparison";
	private final static String name ="Comparison";
	private final static String description ="Compare the probabilities of two different properties.";
	private final static String paramAlpha = "Alpha", tipAlpha = "Probability of false positives, ]0,1[";
	private final static String paramBeta = "Beta", tipBeta = "Probability of false negatives, ]0,1[";
	private final static String paramTheta = "Theta", tipTheta = "Probability of equality, ]0,1[";
	private final static String paramDelta = "Delta", tipDelta = "Indifference region for equality (<= Theta) and (<= 1-Theta)";
	private final static String paramU1 = "Upper ratio", tipU1 = "Upper value for the odds ratio (u1)";
	private final static String paramU0 = "Lower ratio", tipU0 = "Lower value for the odds ratio (u0 < u1)";
	private final static String paramGreaterReq = "Greater req.", tipGreaterReq = "Name of the greater requirement";
	private final static String paramLowerReq = "Lower req.", tipLowerReq = "Name of the lower requirement";

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
	public InterfaceAlgorithmScheduler createScheduler(AbstractModel model, List<AbstractRequirement> requirements,	Map<String, Object> parametersMap) throws PlasmaParameterException {
		double alpha, beta, theta, delta, u1, u0;
		String greaterReqName, lowerReqName;
		AbstractRequirement greaterReq = null;
		AbstractRequirement lowerReq = null;
		
		try{
			if(parametersMap.containsKey(paramAlpha))
				alpha = Double.parseDouble(parametersMap.get(paramAlpha).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramAlpha);
			
			if(parametersMap.containsKey(paramBeta))
				beta = Double.parseDouble(parametersMap.get(paramBeta).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramBeta);
			
			if(parametersMap.containsKey(paramTheta))
				theta = Double.parseDouble(parametersMap.get(paramTheta).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramTheta);
			
			if(parametersMap.containsKey(paramDelta))
				delta = Double.parseDouble(parametersMap.get(paramDelta).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramDelta);

			if(parametersMap.containsKey(paramU1))
				u1 = Double.parseDouble(parametersMap.get(paramU1).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramU1);

			if(parametersMap.containsKey(paramU0))
				u0 = Double.parseDouble(parametersMap.get(paramU0).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramU0);		

			if(parametersMap.containsKey(paramGreaterReq))
				greaterReqName = parametersMap.get(paramGreaterReq).toString();
			else
				throw new PlasmaParameterException("Missing parameter: " + paramGreaterReq);
			
			if(parametersMap.containsKey(paramLowerReq))
				lowerReqName = parametersMap.get(paramLowerReq).toString();
			else
				throw new PlasmaParameterException("Missing parameter: " + paramLowerReq);
		} catch(NumberFormatException e){
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
			
		if(alpha<=0||alpha>=1)
			throw new PlasmaParameterException(paramAlpha + " must be in ]0,1[.");
		if(beta<=0||beta>=1)
			throw new PlasmaParameterException(paramBeta + " must be in ]0,1[.");
		if(theta<=0||theta>=1)
			throw new PlasmaParameterException(paramTheta + " must be in ]0,1[.");
		if(delta>theta||delta<0||delta>(1-theta))
			throw new PlasmaParameterException(paramDelta + " must be positive, lower than Theta and lower than 1-Theta.");
		
		if (requirements.size() < 2 || requirements.size() > 2)
			throw new PlasmaParameterException("The algorithm requires two requirements.");
		for (AbstractRequirement req : requirements) {
			if (req.getName().equals(greaterReqName))
				greaterReq = req;
			if (req.getName().equals(lowerReqName))
				lowerReq = req;
		}
		if (greaterReq == null)
			throw new PlasmaParameterException(paramGreaterReq + " " + greaterReqName + " has not been found in the selected requirements.");
		if (lowerReq == null)
			throw new PlasmaParameterException(paramLowerReq + " " + greaterReqName + " has not been found in the selected requirements.");
		
		boolean distributed = false;
		if(parametersMap.containsKey("distributed"))
			distributed = Boolean.parseBoolean(parametersMap.get("distributed").toString());
		if(distributed)
			throw new PlasmaNotDistibutedException();
		
		return new ComparisonAlgorithm(model, greaterReq, lowerReq, alpha, beta, theta, delta, u1, u0, id);
	}

	@Override
	public List<SMCParameter> getParametersList() {
		if(parameters == null){
			parameters = new ArrayList<SMCParameter>();
			parameters.add(new SMCParameter(paramAlpha, tipAlpha, false));
			parameters.add(new SMCParameter(paramBeta, tipBeta, false));
			parameters.add(new SMCParameter(paramTheta, tipTheta, false));
			parameters.add(new SMCParameter(paramDelta, tipDelta, false));
			parameters.add(new SMCParameter(paramU0, tipU0, false));
			parameters.add(new SMCParameter(paramU1, tipU1, false));
			parameters.add(new SMCParameter(paramLowerReq, tipLowerReq, false));
			parameters.add(new SMCParameter(paramGreaterReq, tipGreaterReq, false));
		}
		return parameters;
	}
	
	@Override
	public void fillParametersMap(Map<String, Object> parametersMap, String[] parameters) throws PlasmaParameterException {
		try{
			if(parameters.length>=8) {
				parametersMap.put(paramAlpha, Double.parseDouble(parameters[0]));
				parametersMap.put(paramBeta, Double.parseDouble(parameters[1]));
				parametersMap.put(paramTheta, Double.parseDouble(parameters[2]));
				parametersMap.put(paramDelta, Double.parseDouble(parameters[3]));
				parametersMap.put(paramU0, Double.parseDouble(parameters[4]));
				parametersMap.put(paramU1, Double.parseDouble(parameters[5]));
				parametersMap.put(paramLowerReq, parameters[6]);
				parametersMap.put(paramGreaterReq, parameters[7]);
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
