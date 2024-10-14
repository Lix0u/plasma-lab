/**
 * This file is part of fr.inria.plasmalab.importancesampling.
 *
 * fr.inria.plasmalab.importancesampling is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.importancesampling is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.importancesampling.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.importancesampling.data;

import fr.inria.plasmalab.distributed.algorithm.common.Result;

public class CE_Result implements Result {

	private String uid;
	private int total;
	private double sum_res;
	private double[] numerator;
	private double[] denominator;
		
	public CE_Result() {
		super();
	}

	public CE_Result(String uid, int t, double r, double[] n, double[] d) {
		this.uid = uid;
		this.total = t;
		this.sum_res = r;
		this.numerator = n;
		this.denominator = d;
	}	
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getUid() {
		return uid;
	}

	public void setTotal(int t) {
		this.total = t;
	}

	public int getTotal() {
		return total;
	}
	
	public void setResult(double r) {
		this.sum_res = r;	
	}
	
	public double getResult() {
		return sum_res;
	}
	
	public void setNumerator(double[] n) {
		this.numerator = n;
	}
	
	public double[] getNumerator() {
		return numerator;
	}
	
	public void setDenominator(double[] d) {
		this.denominator = d;
	}
	
	public double[] getDenominator() {
		return denominator;
	}
}
