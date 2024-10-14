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
package fr.inria.plasmalab.importancesplitting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.algorithm.InterfaceAlgorithmScheduler;
import fr.inria.plasmalab.algorithm.InterfaceAlgorithmWorker;
import fr.inria.plasmalab.algorithm.data.SMCAlternatives;
import fr.inria.plasmalab.algorithm.data.SMCParameter;
import fr.inria.plasmalab.algorithm.factory.InterfaceAlgorithmFactory;
import fr.inria.plasmalab.importancesplitting.algorithm.ISAdaptiveAlgorithm;
import fr.inria.plasmalab.importancesplitting.algorithm.ISFixedLevelsAlgorithm;
import fr.inria.plasmalab.importancesplitting.algorithm.ImportanceSplittingScheduler;
import fr.inria.plasmalab.importancesplitting.algorithm.ImportanceSplittingWorker;
import fr.inria.plasmalab.importancesplitting.server.ISServerResource;
import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.exceptions.PlasmaParameterException;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class ImportanceSplittingFactory implements InterfaceAlgorithmFactory {
	private final static String id ="splitting";
	private final static String name ="Importance splitting";
	private final static String description ="Importance splitting algorithm for rare event estimation.";
	private final static String paramBudget = "Budget", tipBudget = "Number of simulations at each iteration.";
	private final static String paramAdaptive = "Adaptive algorithm", tipAdaptive = "Algorithm with an optimized number of levels.";
	private final static String paramScore = "Maximum score", tipScore = "The score the traces must reach in order to satisfy the property.";
	private final static String paramFixed = "Fixed levels algorithm", tipFixed = "Algorithm with a fixed number of levels.";
	private final static String paramLevels = "Levels", tipLevels = "Scores of the levels.";

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
		return new ImportanceSplittingWorker(model, requirements);
	}

	@Override
	public InterfaceAlgorithmScheduler createScheduler(AbstractModel model, List<AbstractRequirement> requirements,	Map<String, Object> parametersMap) throws PlasmaParameterException {
		int budget = -1; 
		double scorePhi = -1;
		double[] levels = null;
		try{
			if (parametersMap.containsKey(paramBudget))
				budget = Integer.parseInt(parametersMap.get(paramBudget).toString());
			else
				throw new PlasmaParameterException("Missing parameter: " + paramBudget);

			if(parametersMap.containsKey(paramScore))
				scorePhi = Double.parseDouble(parametersMap.get(paramScore).toString());
			if(parametersMap.containsKey(paramLevels)) {
				String[] stringLevels = parametersMap.get(paramLevels).toString().trim().split("\\s+");
				levels = new double[stringLevels.length];
				for (int i = 0; i < stringLevels.length; i++) {
					levels[i] = Double.parseDouble(stringLevels[i]);
				}
			}
		} catch(NumberFormatException e){
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
		
		if (budget<=0)
			throw new PlasmaParameterException("Budget must be positive value.");
		if (levels == null && scorePhi <=0 )
			throw new PlasmaParameterException("Score Property must be positive value.");
		if (levels != null) {
			for (int i = 0; i < levels.length; i++) {
				if (levels[i] <= 0)
					throw new PlasmaParameterException("Levels must be positive values.");
			}
		}
		
		boolean distributed = false;
		if(parametersMap.containsKey("distributed"))
			distributed = Boolean.parseBoolean(parametersMap.get("distributed").toString());
		
		if (distributed) {
			return new ImportanceSplittingScheduler(model, requirements, budget, levels, scorePhi, id);
		}
		else {
			if (levels == null)
				return new ISAdaptiveAlgorithm(model, requirements, budget, scorePhi, id);
			else
				return new ISFixedLevelsAlgorithm(model, requirements, budget, levels, id);
		}
	}

	@Override
	public List<SMCParameter> getParametersList() {
		if(parameters == null){
			parameters = new ArrayList<SMCParameter>();
			parameters.add(new SMCParameter(paramBudget,tipBudget, false));
			List<SMCParameter> optionList = new ArrayList<SMCParameter>();
			optionList.add(new SMCParameter(paramScore, tipScore, false));
			SMCAlternatives altAdaptive = new SMCAlternatives(paramAdaptive, tipAdaptive, optionList, null);
			optionList = new ArrayList<SMCParameter>();
			optionList.add(new SMCParameter(paramLevels, tipLevels, false));
			SMCAlternatives altFixed = new SMCAlternatives(paramFixed, tipFixed, optionList, altAdaptive);
			altAdaptive.setNext(altFixed);
			parameters.add(altAdaptive);
		}
		return parameters;
	}
	
	@Override
	public void fillParametersMap(Map<String, Object> parametersMap, String[] parameters) throws PlasmaParameterException {
		try{
			if(parameters.length>=2) {
				parametersMap.put(paramBudget, Integer.parseInt(parameters[0]));
				if(parameters.length==2) {
					parametersMap.put(paramScore, Integer.parseInt(parameters[1]));
				}
				else {
					String stringLevels = "";
					for (int i = 2; i < parameters.length; i++) {
						stringLevels += parameters[i];
					}
					parametersMap.put(paramLevels, stringLevels);
				}
			}
			else
				throw new PlasmaParameterException("Wrong number of parameters for the " + name + " algorithm.");
		} catch(NumberFormatException e){
			throw new PlasmaParameterException("Cannot parse parameters: " +  e.getMessage());
		}
	}
	
	@Override
	public Class<ISServerResource> getResourceHandler() {
		return ISServerResource.class;
	}

	@Override
	public boolean isDistributed() {
		return true;
	}


}
