/*******************************************************************************
 * Copyright 2018 RaspInLoop
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.raspinloop.timeemulation;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum SimulatedTime {
	// Singleton
	INST;

	private final Lock lock = new ReentrantLock();
	private final Condition appStarted = lock.newCondition();

	long currentTimeNano = 0;
	long startDate = 0;
	private Set<SimulatedTimeListerner> listeners = new HashSet<>();
	@SuppressWarnings("unused")
	private Long stoptimeNano = null;
	private Long waitingThresholdNano = 0L;
	private boolean isStarted = false;
	private boolean stopped = false;
	private ThreadLocal<Long> requestingTime = new ThreadLocal<Long>() {
        @Override protected Long initialValue() { return 0L; }
	};

	//final static Logger logger = LoggerFactory.getLogger(SimulatedTime.class);

	/**
	 * The computation of a time step is started.
	 * 
	 * @param currentCommunicationPoint
	 *            is the current communication point of the master (t)
	 * @param communicationStepSize
	 *            is the communication step size (h).
	 * @param noSetFMUStatePriorToCurrentPoint
	 *            is true if fmi2SetFMUState will no longer be called for time
	 *            instants prior to currentCommunicationPoint in this simulation
	 *            run [the slave can use this flag to flush a result buffer].
	 * @return
	 */
	public synchronized void doStep(double time) {		
		//logger.trace("Do a step of  {} ms ",time*1000);
		long incrementInNano = sectoNano(time);
		currentTimeNano += incrementInNano;
		if (INST.getWaitingThreshold() >= incrementInNano)
			System.err.println("Simulator increment is lower than the maximun waiting treshold. You should check waiting threshold");
		INST.notifyAll();
	}

	/**
	 * 
	 * @param requesterName: identifier of component who do not want to get stuck waiting next simulation step. 
	 * @param waitingThresholdNano: below this threshold, we do not wait for next Simulator doStep,
	 *  waiting loop will be skipped. This value must be below the increment value of doDtep (configured in simulator) 
	 */
	public synchronized void RegisterWaitingThreshold(String requesterName, long waitingThresholdNano){
		if (this.waitingThresholdNano < waitingThresholdNano){
			//TODO: loggging of requester  
			this.waitingThresholdNano = waitingThresholdNano;
		}
	}
	
	public synchronized Long getWaitingThreshold(){
		return this.waitingThresholdNano;
	}
	
	/**
	 * 
	 * @return the sum in nano of all sleeps calls for the current thread.
	 * This is useful when waitingThresholdNano is set and we want to known 
	 * the theorical time elapsed between 2 doStep increment
	 */
	public synchronized Long getRequestingTime(){
		return this.requestingTime.get();
	}
		
	public void addRequestingTimeListener(SimulatedTimeListerner listener){
		listeners.add(listener);
	}
		
	private long sectoNano(double time) {
		return (long) (time * 1000000000.0);
	}

	public synchronized void setup(double startTime, double stoptime) {
		//logger.debug("setup simulation with startTime {} and stopTime {}",startTime, stoptime );
		this.stoptimeNano = sectoNano(stoptime);
		startDate = new Date().getTime();
		currentTimeNano = sectoNano(startTime);
		isStarted = false;
		stopped = false;
	}

	public synchronized void setup(double startTime) {
		//logger.debug("setup simulation with startTime {}",startTime);
		startDate = new Date().getTime();
		currentTimeNano = sectoNano(startTime);
		isStarted = false;
		stopped = false;
	}

	public synchronized void stop() {
		signalStopped();
		stopped = true;
		INST.notifyAll();
	}

	public boolean waitForApplicationStarting() throws InterruptedException {
		lock.lock();
		try {
			if (!isStarted)
				appStarted.await();							
		} finally {
			lock.unlock();
		}
		return isStarted;
	}

	private void signalStarted() {
		lock.lock();
		try {
			isStarted = true;
			appStarted.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	private void signalStopped() {
		lock.lock();
		try {
			isStarted = false;
			appStarted.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public long getCurrentTimeNano() {
		return currentTimeNano;
	}

	public static void sleep(long millis, int nano) throws InterruptedException {
		//logger.debug("Simulation sleep called for {} ms and {} ns",millis, nano);
		long nanos = millis * 1000000L + nano;
		for (SimulatedTimeListerner listener : INST.listeners) {
			listener.onRequestingSleep(nanos);
		}
		//All time spent in sleep is added 
		//in order to keep its trace 
		//even if we did'nt really wait during this time.
		INST.requestingTime.set(INST.requestingTime.get()+nanos);
				
		INST.signalStarted();
		if (nanos > INST.waitingThresholdNano) {
		// get stuck waiting doStep only if we have to wait more than waitingThresholdNano
			long startWaitingTime = INST.getCurrentTimeNano();
			long waitingTime = 0;
			do {
				synchronized (INST) {
					INST.wait(); // notify called at each doStep
					waitingTime = INST.getCurrentTimeNano() - startWaitingTime;
					if (INST.isStopped())
						System.exit(0); // Simulation stopped: close the app.
				}
			} while (waitingTime < nanos);
		}
	}
	
	static public void sleep(long millis) throws InterruptedException {
		sleep(millis, 0);
	}

	private boolean isStopped() {
		return stopped;
	}

	public static long nanotime() {
		long nano = INST.getCurrentTimeNano();
		//logger.trace("Simulation nanotime called at {} ms", nano/1000000.0);
		return nano;
	}

	public static long awaitNanos(long nanos, Condition target)
			throws InterruptedException {
		//logger.trace("Simulation awaitNanos called for {} ms", nanos/1000000.0);
		// we have to wait for do step and wait for target condition
		INST.lock.lock();
		try {
			if (!INST.isStarted) {
				return target.awaitNanos(nanos);
			}
		} finally {
			INST.lock.unlock();
		}

		long startWaitingTime = INST.getCurrentTimeNano();
		long waitingTime = 0;
		do {
			synchronized (INST) {
				INST.wait(); // notify called at each doStep
				waitingTime = INST.getCurrentTimeNano() - startWaitingTime;
				if (INST.isStopped())
					System.exit(0); // Simulation stopped: close the app.
			}
		} while ((waitingTime < nanos)
				&& !target.await(10/*
									 * step increment should be taken in account
									 */, TimeUnit.MICROSECONDS));

		return nanos - waitingTime;
	}
	
	/**
	 * Used to bypass Weavering of Thread.sleep
	 * @param millis
	 * @param nanos
	 * @throws InterruptedException
	 */
	static public void realTimeSleep(long millis, int nanos) throws InterruptedException {
		Thread.sleep(millis, nanos);
	}


}
