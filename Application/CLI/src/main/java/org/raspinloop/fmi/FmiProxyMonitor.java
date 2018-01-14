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
package org.raspinloop.fmi;

import org.raspinloop.fmi.launcher.ProcessStatus;
import org.raspinloop.fmi.launcher.SimulationToolStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FmiProxyMonitor extends AbstractProxyMonitor  {

	Logger logger = LoggerFactory.getLogger(FmiProxyMonitor.class);

	@Override
	public void worked(int i) {
		logger.info("++++++++++++++++++++++++++++ working+++: " + i + "% +++++++++++++++++++++++++++++++++++");
	}

	@Override
	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void processStatusChanged(ProcessStatus status) {
		logger.info("++++++++++++++++++++++++++++ PROCESS: "+ status +" +++++++++++++++++++++++++++++++++++");
	}

	@Override
	public void simulationToolsStatusChanged(SimulationToolStatus status) {
		logger.info("++++++++++++++++++++++++++++ SIMULATION TOOL: "+ status +" +++++++++++++++++++++++++++++++++++");
	}

	@Override
	public void aborted(Throwable e) {
		super.aborted(e);
		logger.info("++++++++++++++++++++++++++++ Aborted: " + e.getMessage() + " +++++++++++++++++++++++++++++++++++");

	}

}
