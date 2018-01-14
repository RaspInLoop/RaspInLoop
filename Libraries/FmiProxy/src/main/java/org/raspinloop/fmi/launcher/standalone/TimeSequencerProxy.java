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
package org.raspinloop.fmi.launcher.standalone;

import java.util.Date;

import org.apache.thrift.TException;
import org.raspinloop.fmi.Type;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.Proxy;
import org.raspinloop.fmi.launcher.RunnerLifeHandler;
import org.raspinloop.fmi.launcherRunnerIpc.RunnerService.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TimeSequencerProxy implements Proxy {

	Logger logger = LoggerFactory.getLogger(TimeSequencerProxy.class);
	final private String guid;
	final private double stepIncrement;
	final private double timeRatio;
	private double duration;

	private RunnerLifeHandler lifeHandler;

	private IProxyMonitor monitor;


	
	public TimeSequencerProxy(String guid, double stepIncrement, double timeRatio, double duration, IProxyMonitor monitor) {
		super();
		this.guid = guid;
		this.stepIncrement = stepIncrement;
		this.timeRatio = timeRatio;
		this.duration = duration;
		this.monitor = monitor;
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFmiRunnerLifeHandler(RunnerLifeHandler lifeHandler) {
		this.lifeHandler = lifeHandler;
	}

	private Client client;

	public void run() throws TException, InterruptedException {
		try {
			instanciate("standAlone Instance", Type.CoSimulation, guid, "", true, true);
		} catch (TException e1) {
			return;
		}

		try {
			logger.trace("-->setupExperiment");
			client.setupExperiment(false, 0.0, new Date().getTime() / 1000.0, false, 0.0);
			logger.trace("<--setupExperiment");

			double currentCommunicationPoint = 0;
			double communicationStepSize = stepIncrement;
			logger.trace("Simulated Time: Duration[" + duration + " s], StepIncrement[" + stepIncrement + " s], Ratio[" + timeRatio + "]");
			while (currentCommunicationPoint < duration) {
				Thread.sleep((long) (1000 * timeRatio * stepIncrement));
				currentCommunicationPoint += stepIncrement;

				logger.trace("-->doStep " + currentCommunicationPoint + " " + communicationStepSize);
				client.doStep(currentCommunicationPoint, communicationStepSize, false);
				logger.trace("<--doStep");
			}

		} catch (Exception e) {
			logger.trace("Client exception: " + e.getMessage());
		} finally {

			lifeHandler.stopVMRunner();
			lifeHandler.freeInstance(null);
		}
	}

	public void instanciate(String instanceName, Type fmuType, String fmuGUID, String fmuResourceLocation, boolean visible, boolean loggingOn)
			throws TException {
		if (lifeHandler == null) {
			// TODO log error
			throw new TException("lifeHandlerNotSet");
		}

		try {
			client = lifeHandler.startVMRunner(monitor);
			lifeHandler.instanciate(instanceName, fmuType, fmuGUID, fmuResourceLocation, visible, loggingOn);
		} catch (Exception e) {
			// TODO log error
			new TException(e.getMessage(), e);
		}
	}

	@Override
	public String getName() {
		return "Simulated Time: Duration[" + duration + " s], StepIncrement[" + stepIncrement + " s], Ratio[" + timeRatio + "]";
	}
}
