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
import org.apache.thrift.transport.TTransportException;
import org.raspinloop.fmi.Instance;
import org.raspinloop.fmi.launcher.BaseRunnerLifeHandler;
import org.raspinloop.fmi.launcher.ProxyRunnerJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FmiRunnerLifeHandler extends  BaseRunnerLifeHandler {


	private TServer server;
    private Logger logger = LoggerFactory.getLogger(FmiRunnerLifeHandler.class);

	public FmiRunnerLifeHandler(ProxyRunnerJob fmiProxyRunnerJob, TServer server) {
		super(fmiProxyRunnerJob);
		this.server = server;
	}
	
	@Override
	public  org.raspinloop.fmi.launcherRunnerIpc.Status freeInstance(Instance c) {
		logger.trace("FmiRunnerLifeHandler.terminate called");
		return org.raspinloop.fmi.launcherRunnerIpc.Status.OK;

	}

	@Override
	public org.raspinloop.fmi.Status reset(Instance c) throws TTransportException {
		logger.trace("FmiRunnerLifeHandler.reset called");
		return org.raspinloop.fmi.Status.Discard;
	}

	public void stopFMIServer(){
		if (server != null )
			server.stop();
	}

	public org.raspinloop.fmi.launcherRunnerIpc.Status stopVMRunner() {
		return super.stopVMRunner();				
	}
}
