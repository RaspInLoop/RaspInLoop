package org.raspinloop.fmi.plugin.launcher.standalone;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.raspinloop.fmi.Instance;
import org.raspinloop.fmi.ModelState;
import org.raspinloop.fmi.launcherRunnerIpc.RunnerService.Client;
import org.raspinloop.fmi.plugin.Trace;
import org.raspinloop.fmi.plugin.launcher.BaseRunnerLifeHandler;
import org.raspinloop.fmi.plugin.launcher.ProxyRunnerJob;
import org.raspinloop.fmi.plugin.launcher.RunnerLifeHandler;
import org.raspinloop.fmi.plugin.launcher.ProxyRunnerJob.LauncherServerJob;

public class StandaloneRunnerLifeHandler extends BaseRunnerLifeHandler {


	public StandaloneRunnerLifeHandler(ProxyRunnerJob fmiProxyRunnerJob) {
		super(fmiProxyRunnerJob);
	}

	
	@Override
	public  org.raspinloop.fmi.launcherRunnerIpc.Status freeInstance(Instance c) {
		Trace.launcherRunner("FmiRunnerLifeHandler.freeInstance called");
		return org.raspinloop.fmi.launcherRunnerIpc.Status.OK;

	}

	@Override
	public org.raspinloop.fmi.Status reset(Instance c) throws TTransportException, CoreException {
		Trace.launcherRunner("FmiRunnerLifeHandler.reset called");
		return org.raspinloop.fmi.Status.Discard;
	}

}