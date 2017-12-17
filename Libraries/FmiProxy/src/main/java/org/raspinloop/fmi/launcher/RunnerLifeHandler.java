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
