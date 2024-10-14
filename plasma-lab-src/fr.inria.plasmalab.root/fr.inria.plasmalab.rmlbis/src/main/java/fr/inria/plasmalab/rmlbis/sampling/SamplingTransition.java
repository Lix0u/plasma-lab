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
package fr.inria.plasmalab.rmlbis.sampling;

import java.util.List;
import java.util.Map;

import fr.inria.plasmalab.rmlbis.ast.Command;
import fr.inria.plasmalab.rmlbis.simulator.RMLTransition;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceIdentifier;
import fr.inria.plasmalab.workflow.data.simulation.InterfaceSamplingTransition;

public class SamplingTransition extends RMLTransition implements InterfaceSamplingTransition {
	
	private double likelihoodRatio;
	private double totalRate;
	
	public SamplingTransition(String c, List<Command> selectedSynchro, Map<InterfaceIdentifier, Double> u, double l, double r) {
		super(c, selectedSynchro, u);
		this.likelihoodRatio = l;
		this.totalRate = r;
	}

	@Override
	public double getLikelihoodRatio() {
		return likelihoodRatio;
	}

	@Override
	public double getTotalRate() {
		return totalRate;
	}
	
}
