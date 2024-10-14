/**
 * This file is part of fr.inria.plasmalab.smartsampling.
 *
 * fr.inria.plasmalab.smartsampling is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.smartsampling is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.smartsampling.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.smartsampling.data;

import fr.inria.plasmalab.distributed.algorithm.common.Result;

public class IteratedMDPResult implements Result {
	
	private String uid;
	private int totalDone;
	private int[] positivePerScheduler;
	private double[] resultPerScheduler;

	
	public IteratedMDPResult(){
		
	}
	
	public IteratedMDPResult(
			String uid,
			int totalDone,
			int[] positivePerScheduler,
			double[] resultPerScheduler) {
		this.uid = uid;
		this.totalDone = totalDone;
		this.positivePerScheduler = positivePerScheduler;
		this.resultPerScheduler = resultPerScheduler;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setTotalDone(int totalDone) {
		this.totalDone = totalDone;
	}

	public void setPositivePerScheduler(int[] positivePerScheduler) {
		this.positivePerScheduler = positivePerScheduler;
	}

	public void setResultPerScheduler(double[] resultPerScheduler) {
		this.resultPerScheduler = resultPerScheduler;
	}

	public String getUid() {
		return uid;
	}
	
	public int getTotalDone() {
		return totalDone;
	}
	
	public int[] getPositivePerScheduler() {
		return positivePerScheduler;
	}

	public double[] getResultPerScheduler() {
		return resultPerScheduler;
	}
}
