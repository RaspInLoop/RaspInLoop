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
package org.raspinloop.fmi.launcher.fmi;

import org.apache.thrift.server.TServer;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.Proxy;
import org.raspinloop.fmi.launcher.ProxyRunnerJob;
import org.raspinloop.fmi.launcher.Runner;
import org.raspinloop.fmi.launcher.SimulationToolStatus;



public class FmiProxyRunnerJob extends ProxyRunnerJob {

	private FmiRunnerLifeHandler lifeHandler;
	private IProxyMonitor monitor;

	public FmiProxyRunnerJob(TServer server, Proxy proxy, Runner runner, IProxyMonitor monitor) {
		super(proxy, runner);
		this.monitor = monitor;
		monitor.simulationToolsStatusChanged(SimulationToolStatus.CONNECTED);
		lifeHandler = new FmiRunnerLifeHandler(this, server);
		proxy.setFmiRunnerLifeHandler(lifeHandler );
	}	
	
	public void cancel() {
		
		super.cancel();
		monitor.simulationToolsStatusChanged(SimulationToolStatus.STOPPED);
	}

}

