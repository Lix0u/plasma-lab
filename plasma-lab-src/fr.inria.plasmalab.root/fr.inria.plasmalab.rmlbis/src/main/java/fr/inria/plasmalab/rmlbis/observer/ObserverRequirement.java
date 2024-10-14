/**
 * This file is part of fr.inria.plasmalab.rmlbis.
 *
 * fr.inria.plasmalab.rmlbis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.rmlbis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.rmlbis.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.rmlbis.observer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.RecognitionException;

import fr.inria.plasmalab.workflow.data.AbstractModel;
import fr.inria.plasmalab.workflow.data.AbstractRequirement;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaCheckerException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDataException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaDeadlockException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaRuntimeException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSyntaxException;

public class ObserverRequirement extends AbstractRequirement {
	
	//Model simulator
	protected AbstractModel model;
	//Requirement simulator
	protected ObserverSimulator simulator;
	
	public ObserverRequirement(String name, File file, String id){
		this.name = name;
		this.id = id;
		this.content = "";
		try {
			FileReader fr = new FileReader(file.getAbsoluteFile());
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				content = content+br.readLine()+"\n";
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.origin = file;
	}
	
	public ObserverRequirement(String name, String content, String id){
		this.name = name;
		this.id = id;
		this.content = content;
		this.origin = null;
	}

	@Override
	public void setModel(AbstractModel model) {
		this.model=model;
	}

	@Override
	/** Check a path starting from a state. The state comes either from the model or from the observer. 
	 *   
	 */
	public Double check(InterfaceState path) throws PlasmaCheckerException {
		// reinitialize the observer trace
		// simulate the observer until matching the length of the model trace
		// then simulate alternatively the model and the observer until a decision is made or a deadlock is reached
		// this means the time bound or step bound must be implemented in the observer
		double score = 0.0;
		try {
			simulator.newPath(path);
			score = simulator.getScore();
			double currentTime = 0;
			boolean decided = simulator.isDecided();
			while (!decided & simulator.getTrace().size() < model.getTraceLength()) {
				InterfaceState modelState = model.getStateAtPos(simulator.getTrace().size());
				double elapsedTime = 1;
				if (model.hasTime()) { 
					elapsedTime = modelState.getValueOf(model.getTimeId()) - currentTime;
					currentTime += elapsedTime;
				}
				simulator.simulate(modelState,elapsedTime);
				decided = simulator.isDecided();
				score = Math.max(score, simulator.getScore());
			}
			while (!decided) {
				model.simulate();
				double elapsedTime = 1;
				if (model.hasTime()) { 
					elapsedTime = model.getCurrentState().getValueOf(model.getTimeId()) - currentTime;
					currentTime += elapsedTime;
				}
				simulator.simulate(model.getCurrentState(),elapsedTime);
				decided = simulator.isDecided();
				score = Math.max(score, simulator.getScore());
			}
		}
		catch (PlasmaDeadlockException e) {
			// if deadlock the values score and decided remains unchanged, the trace is stopped.
		} catch (PlasmaSimulatorException e) {
			throw new PlasmaCheckerException(e);
		}
		// return the value of the score
		return score;
	}
	
	@Override
	public Double check(int untilStep, InterfaceState path) throws PlasmaCheckerException {
		// reinitialize the observer trace
		// simulate the observer until matching the length of the model trace or the time untilStep
		// then simulate alternatively the model and the observer until the step bound is reached or a deadlock is reached
		// this means the time bound or step bound must be implemented in the observer
		int nbSteps = 0;
		double score = 0.0;
		try {
			simulator.newPath(path);
			score = simulator.getScore();
			double currentTime = 0;
			while (simulator.getTrace().size() < model.getTraceLength() && nbSteps < untilStep) {
				InterfaceState modelState = model.getStateAtPos(simulator.getTrace().size());
				double elapsedTime = 1;
				if (model.hasTime()) { 
					elapsedTime = modelState.getValueOf(model.getTimeId()) - currentTime;
					currentTime += elapsedTime;
				}
				simulator.simulate(modelState,elapsedTime);
				score = Math.max(score, simulator.getScore());
				nbSteps++;
			}
			while (nbSteps < untilStep) {
				model.simulate();
				double elapsedTime = 1;
				if (model.hasTime()) { 
					elapsedTime = model.getCurrentState().getValueOf(model.getTimeId()) - currentTime;
					currentTime += elapsedTime;
				}
				simulator.simulate(model.getCurrentState(),elapsedTime);
				score = Math.max(score, simulator.getScore());
				nbSteps++;
			}
		}
		catch (PlasmaDeadlockException e) {
			// if deadlock the values score and decided remains unchanged, the trace is stopped.
		} catch (PlasmaSimulatorException e) {
			throw new PlasmaCheckerException(e);
		}
		// return the value of the score
		return score;
	}
	
	@Override
	public Double check(String id, double untilValue, InterfaceState path) throws PlasmaCheckerException {
		// reinitialize the observer trace
		// simulate the observer until matching the length of the model trace or the value
		// then simulate alternatively the model and the observer until the value is reached or a decision is made or a deadlock is reached
		// this means the time bound or step bound must be implemented in the observer
		double score = 0.0;
		try {
			simulator.newPath(path);
			score = simulator.getScore();
			double currentTime = 0;
			double currentValue = simulator.getCurrentState().getValueOf(id);
			boolean decided = simulator.isDecided();
			while (!decided & simulator.getTrace().size() < model.getTraceLength() && currentValue < untilValue) {
				InterfaceState modelState = model.getStateAtPos(simulator.getTrace().size());
				double elapsedTime = 1;
				if (model.hasTime()) { 
					elapsedTime = modelState.getValueOf(model.getTimeId()) - currentTime;
					currentTime += elapsedTime;
				}
				simulator.simulate(modelState,elapsedTime);
				currentValue = simulator.getCurrentState().getValueOf(id);
				decided = simulator.isDecided();
				score = Math.max(score, simulator.getScore());
			}		
			while (!decided && currentValue < untilValue) {
				model.simulate();
				double elapsedTime = 1;
				if (model.hasTime()) { 
					elapsedTime = model.getCurrentState().getValueOf(model.getTimeId()) - currentTime;
					currentTime += elapsedTime;
				}
				simulator.simulate(model.getCurrentState(),elapsedTime);
				currentValue = simulator.getCurrentState().getValueOf(id);
				decided = simulator.isDecided();
				score = Math.max(score, simulator.getScore());
			}
		}
		catch (PlasmaDeadlockException e) {
			// if deadlock the values score and decided remains unchanged, the trace is stopped.
		} catch (PlasmaSimulatorException e) {
			throw new PlasmaCheckerException(e); 
		}
		// return the value of the score
		return score;
	}

	@Override
	public List<InterfaceState> getLastTrace() {
		return simulator.getTrace();
	}

	@Override
	public boolean checkForErrors() {
		if(errors == null)
			errors = new ArrayList<PlasmaDataException>();
		else
			errors.clear();
		
		try {
			ObserverParserProcessing process = new ObserverParserProcessing(model, content);
			if (model != null)
				simulator = new ObserverSimulator(process.requirement,process.observers,process.externalIds);
		}
		catch (PlasmaSyntaxException e) {
			errors.add(e);
			return true;
		}
		catch (PlasmaRuntimeException e) {
			errors.add(new PlasmaSyntaxException(e));
			return true;
		} catch (RecognitionException e) {
			errors.add(new PlasmaSyntaxException(e));
			return true;
		}
		return false;
	}
}
