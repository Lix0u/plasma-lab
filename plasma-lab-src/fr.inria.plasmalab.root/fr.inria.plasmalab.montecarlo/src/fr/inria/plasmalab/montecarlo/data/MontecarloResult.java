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
package fr.inria.plasmalab.montecarlo.data;


import fr.inria.plasmalab.distributed.algorithm.common.Result;

public class MontecarloResult implements Result {
	private String uid;
	private int total;
	private int[][] positiveMatrix;
	private double[][] results;
		

	public MontecarloResult(){
		
	}
	
	public MontecarloResult(String uid, 
			int total, 
			int[][] positiveMatrix, 
			double[][] results) {
		this.uid = uid;
		this.total = total;
		this.positiveMatrix = positiveMatrix;
		this.results = results;
	}
	
	public String getUid() {
		return uid;
	}

	public int getTotal() {
		return total;
	}

	public int[][] getPositiveMatrix() {
		return positiveMatrix;
	}

	public double[][] getResults() {
		return results;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setPositiveMatrix(int[][] positiveMatrix) {
		this.positiveMatrix = positiveMatrix;
	}

	public void setResults(double[][] results) {
		this.results = results;
	}
}
