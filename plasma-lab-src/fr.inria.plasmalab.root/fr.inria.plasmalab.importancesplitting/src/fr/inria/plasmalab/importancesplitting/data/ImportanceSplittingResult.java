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
package fr.inria.plasmalab.importancesplitting.data;

import fr.inria.plasmalab.distributed.algorithm.common.Result;

public class ImportanceSplittingResult implements Result {

	private String uid;
	private int budget;
	private int nbLevels;
	private double proba;
	
	
	public ImportanceSplittingResult() {
		super();
	}

	public ImportanceSplittingResult(String uid, 
			int budget, 
			int nbLevels,
			double proba) {
		this.uid = uid;
		this.budget = budget;
		this.nbLevels = nbLevels;
		this.proba = proba;
	}	
	
	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public void setNbLevels(int nbLevels) {
		this.nbLevels = nbLevels;
	}

	public void setProba(double proba) {
		this.proba = proba;
	}

	public String getUid() {
		return uid;
	}
	
	public int getBudget() {
		return budget;
	}
	
	public int getNbLevels() {
		return nbLevels;
	}
	
	public double getProba() {
		return proba;
	}
}
