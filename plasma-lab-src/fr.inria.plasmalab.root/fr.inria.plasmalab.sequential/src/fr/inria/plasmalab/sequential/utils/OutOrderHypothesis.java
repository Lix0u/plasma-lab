/**
 * This file is part of fr.inria.plasmalab.sequential.
 *
 * fr.inria.plasmalab.sequential is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * fr.inria.plasmalab.sequential is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with fr.inria.plasmalab.sequential.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.inria.plasmalab.sequential.utils;

import java.util.concurrent.locks.ReentrantLock;

public class OutOrderHypothesis {
	
	// Sim batch for the associated order
	private int nsim;
	// Positive sims for the associated result
	private int npos;
	// Hypothesis ratio
	private double A_H0, R_H1;
	// Precomputed acceptance value
	private static double H0;
	// Precomputed rejection value
	private static double H1;
	// Precomputed strength value
	private static double STR;
	
	// Will be reevaluated with previous results
	private int dmin;
	private int dmax;
	
	// Next hypothesis in queue
	private OutOrderHypothesis next;
	private static OutOrderHypothesis first, last;
	
	//Lock
	private ReentrantLock lock;
	private static ReentrantLock queueLock = new ReentrantLock();
	
	public static void setSeqParam(double H0, double H1, double STR){
		OutOrderHypothesis.H0 = H0;
		OutOrderHypothesis.H1 = H1;
		OutOrderHypothesis.STR = STR;
		

		System.out.println("HO = "+H0);
		System.out.println("H1 = "+H1);
		System.out.println("STR = "+STR);
		
	}
	
	public OutOrderHypothesis(int nsim, int M){
		lock = new ReentrantLock();
		this.nsim = nsim;
		//result not yet commited
		this.npos = -1;
		// M is the total number of observation expected at this point
		A_H0 = H0 + M*STR;
		R_H1 = H1 + M*STR;

		System.out.println("HO = "+A_H0);
		// Queue
		queueLock.lock();
		if(first == null){
			first = this;
			last = this;
			lock.lock();
			dmin = 0;
			dmax = nsim;
			lock.unlock();
		} else {
			lock.lock();
			last.lock.lock();
			dmin = last.dmin;
			dmax = last.dmax + nsim;
			last.next = this;
			last.lock.unlock();
			lock.unlock();
			last = this;
		}
		queueLock.unlock();
		this.next = null;
	}
	
	public void commitResult(int npos){
		this.npos = npos;
		this.update(npos, nsim, true, true);
		
		System.out.println("----------------------------\n\n-------------------------------");
	}
	
	/**
	 * 
	 * @param npos positive observation for update
	 * @param nsim total observation for update
	 * @param canH0 can still accept H0, dmini > ri for each i
	 * @param canH1 can still accept H1, dmaxi < ai for each i
	 */
	public void update(int npos, int nsim, boolean canH0, boolean canH1){
		boolean hasNext = false;
		lock.lock();
		dmin += npos;
		dmax += npos - nsim;
		
		if(canH0)
			canH0 = canH0 && dmin > R_H1;
		if(canH1)
			canH1 = canH1 && dmax < A_H0;

		if(canH0 && dmin >= A_H0)
			System.out.println("Accept H0 with "+dmin);
		else
			System.out.println("Reject H0 with "+dmin);
		if(canH1 && dmax <= R_H1)
			System.out.println("Accept H1 with "+dmax);
		else
			System.out.println("Reject H1 with "+dmax);
		//To avoid updating twice if next is in construction
		// we check next != null inside the lock
		hasNext = next != null;
		lock.unlock();
		System.out.println("-------------------------------");
		if(hasNext)
			next.update(npos, nsim, canH0, canH1);
	}
	
	public static void poll(){
		queueLock.lock();
		first = first.next;
		if(first == null)
			last = null;
		queueLock.unlock();
	}
	
	public static void clear(){
		queueLock.lock();
		first = null;
		last = null;
		queueLock.unlock();
	}
}
