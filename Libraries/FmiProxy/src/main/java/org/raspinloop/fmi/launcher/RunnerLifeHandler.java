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
package org.raspinloop.fmi.launcher;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.raspinloop.fmi.Instance;
import org.raspinloop.fmi.Status;
import org.raspinloop.fmi.Type;
import org.raspinloop.fmi.launcherRunnerIpc.RunnerService.Client;

/**
 * 
 * @author Motte
 * Handle the 'life' of an Runner Instance.
 * 
 * - Start/stop runner
 * - Create and free Instance
 */
public interface RunnerLifeHandler {

	Client startVMRunner(IProxyMonitor monitor) throws TTransportException, InterruptedException, RunnerLifeException;
	
	Instance instanciate(String instanceName, Type fmuType,
			String fmuGUID, String fmuResourceLocation, boolean visible,
			boolean loggingOn) throws  TTransportException, TException;
	
	org.raspinloop.fmi.launcherRunnerIpc.Status freeInstance(Instance c);
	
	org.raspinloop.fmi.launcherRunnerIpc.Status stopVMRunner();
	
	Status reset(Instance c) throws TTransportException;
}
