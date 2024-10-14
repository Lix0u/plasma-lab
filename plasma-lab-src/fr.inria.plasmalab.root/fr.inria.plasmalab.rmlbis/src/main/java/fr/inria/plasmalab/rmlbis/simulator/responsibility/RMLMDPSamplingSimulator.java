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
package fr.inria.plasmalab.rmlbis.simulator.responsibility;

import java.util.ArrayList;
import java.util.Random;

import fr.inria.plasmalab.rmlbis.ast.models.ModelType;
import fr.inria.plasmalab.rmlbis.simulator.RMLModel;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceState;
import fr.inria.plasmalab.workflow.exceptions.PlasmaSimulatorException;

public class RMLMDPSamplingSimulator extends RMLSamplingSimulator {
	
	// MDP implementation
	private long schedulerSeed;
	protected Random schedulerRng; // Rng computed by nonDeterministicHash function 
	private static final long hashprime = 0x2fffffffffffff77l;
		
	public RMLMDPSamplingSimulator(RMLModel rmlModel) {
		super(rmlModel);
	}
	
	@Override
	public InterfaceState newPath() {
		// Create a new scheduler
		return newPath(mainRng.nextLong());
	}
	
	@Override
	public InterfaceState newPath(long seed){
		this.trace = new ArrayList<InterfaceState>();
		this.schedulerSeed = seed;
		this.schedulerRng = new Random(schedulerSeed);
		this.actionRng = new Random(mainRng.nextLong());
		this.initialState = rmlModel.createInitialState(model.getDefaultSystem(),0.0,schedulerRng);
		this.trace.add(initialState);
		this.currentState = initialState;
		this.deadlockPos = -1;
		return initialState;
	}
	
	@Override
	public InterfaceState simulate() throws PlasmaSimulatorException {
		//Compute schedulerRng via nonDetHash
		nonDeterministicHash();
		return super.simulate();
	}
	
	@Override
	protected double selectRandomTransition(double maxValue) {
		double ran = schedulerRng.nextDouble();		
		return ran*maxValue;
	}
	
	public void nonDeterministicHash(){
		long seed = schedulerSeed;
		for(InterfaceIdentifier id : currentState.getSystem().getVariables()) {
			for (int j = 0; j < 6; j++)
				{    // seed = (seed^64) mod hashprime
					seed = ((seed%hashprime) + (seed%hashprime))%hashprime;
				}
				try {
					seed = (seed + (Double.doubleToLongBits(currentState.getValueOf(id))%hashprime))%hashprime;
				} catch (PlasmaSimulatorException e) {
					logger.warn(e.getMessage());
				}
		}
		if (rmlModel.hasTime()) {
			for (int j = 0; j < 6; j++)
			{    // seed = (seed^64) mod hashprime
				seed = ((seed%hashprime) + (seed%hashprime))%hashprime;
			}
			try {
				seed = (seed + (Double.doubleToLongBits(currentState.getValueOf(rmlModel.getTimeId()))%hashprime))%hashprime;
			} catch (PlasmaSimulatorException e) {
				logger.warn(e.getMessage());
			}
		}
		
		//If History Dependant
		if(model.getType().equals(ModelType.mdpshd)){
			//Time information
			for(int j=0; j<6; j++){
				seed = ((seed%hashprime) + (seed%hashprime))%hashprime;
			}
			seed = (seed + (Double.doubleToLongBits(rmlModel.getTrace().size())%hashprime))%hashprime;
		}
		
		//Randomize seed because of 48bits Java implementation for seed
		long seedh = seed >> 32;			                        // seed high bits
		long seedl = seed & ((1l << 32)-1);                              	// seed low bits
		seed = ((seedh*seedh) << 32) + (seedh*seedl*2) + ((seedl*seedl) >> 32); // mid-square of seed

		schedulerRng = new Random(seed);		
	}
}
