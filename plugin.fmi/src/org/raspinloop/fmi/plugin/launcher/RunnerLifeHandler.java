package org.raspinloop.fmi.plugin.launcher;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.raspinloop.fmi.Instance;
import org.raspinloop.fmi.Status;
import org.raspinloop.fmi.Type;
import org.raspinloop.fmi.launcherRunnerIpc.RunnerService.Client;


public interface RunnerLifeHandler {

	Client startVMRunner(IProgressMonitor monitor) throws TTransportException, CoreException, InterruptedException;
	
	Instance instanciate(String instanceName, Type fmuType,
			String fmuGUID, String fmuResourceLocation, boolean visible,
			boolean loggingOn) throws CoreException, TTransportException, TException;
	
	org.raspinloop.fmi.launcherRunnerIpc.Status freeInstance(Instance c);
	
	org.raspinloop.fmi.launcherRunnerIpc.Status stopVMRunner();
	
	Status reset(Instance c) throws TTransportException, CoreException;
}
