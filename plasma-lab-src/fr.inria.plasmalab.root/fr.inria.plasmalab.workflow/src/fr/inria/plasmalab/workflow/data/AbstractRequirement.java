/**
 * This file is part of fr.inria.plasmalab.workflow.
 *
 * fr.inria.plasmalab.workflow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.workflow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.workflow.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.workflow.data;


import java.util.ArrayList;
import java.util.List;

import fr.inria.plasmalab.workflow.concrete.Variable;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;

public abstract class AbstractRequirement extends AbstractData {
	
	/**
	 * Associates this requirement to a model.
	 * 
	 * @param model the model that is being checked.
	 */
	public abstract void setModel(AbstractModel model);
	
	/**
	 * Check the requirement against the trace that starts from the initial state.
	 * This method doesn't generate a new path. It uses a pre-computed initial state
	 * and it will call @Link{model.simulate()} to add new state to the path when needed.
	 * 
	 * @param path the initial state of the trace.
	 * @return the Double value of the results. If the results is a Boolean, 1.0 is used for true and 0.0 for false.
	 */
	public abstract Double check(InterfaceState path) throws PlasmaCheckerException;
	
	/**
	 * Check the requirement on the trace until the trace length matches the untilStep value.
	 * This method doesn't generate a new path. It uses a pre-computed initial state
 	 * and it will call @Link{model.simulate()} to add new state to the path when needed.
	 * 
	 * This method can return a NaN value if the requirement's value is undetermined for the provided bound.
	 * 
	 * @return the Double value of the results.  If the results is a Boolean, 1.0 is used for true and 0.0 for false. NaN means undetermined.
	 * @param untilStep, the length of the trace we have to check.
	 * @param path, the initial state of the trace to check.
	 */
	public abstract Double check(int untilStep, InterfaceState path) throws PlasmaCheckerException;
	
	/**
	 * Check the requirement until a given variable reaches a certain value or a deadlock is reached.
	 * WARNING This method doesn't guarantee termination in case the goal value is never reached.
	 * This method doesn't generate a new path. It uses a pre-computed initial state
	 * and it will call @Link{model.simulate()} to add new state to the path when needed.
	 * 
	 * This method can return a NaN value if the requirement's value is undetermined when reaching the untilValue.
	 * 
	 * @return the Double value of the results. If the results is a Boolean, 1.0 is used for true and 0.0 for false. NaN means undetermined.
	 * @param id the name of the identifier used to stop the trace generation.
	 */
	public abstract Double check(String id, double untilValue, InterfaceState path)  throws PlasmaCheckerException;
	
	/**
	 * Clean procedure after execution of an experiment.
	 */
	public void clean(){};
	
	/**
	 * Return the last trace that has been checked.
	 * @return the last trace that has been checked.
	 */
	public abstract List<InterfaceState> getLastTrace();
	
	/* OPTIMIZATION and REQUIREMENT VARIABLES DECLARATION */
	/**
	 * This method returns a list of Variable for the optimization experiments.
 	 * Empty by default.
	 * @return a List of optimization Variable
	 */
	public List<Variable> getOptimizationVariables() {
		return new ArrayList<Variable>(0);
	}

	/**
	 * This method instantiates several requirements from the original one using the range of requirement variables.
	 * In the case where there is no requirement variable, it returns an empty list and this requirement is used as the requirement to check.
 	 * Empty by default.
	 * @return a List of requirement.
	 */
	public List<AbstractRequirement> generateInstantiatedRequirement() throws PlasmaDataException {
		return new ArrayList<AbstractRequirement>(0);
	}
}
