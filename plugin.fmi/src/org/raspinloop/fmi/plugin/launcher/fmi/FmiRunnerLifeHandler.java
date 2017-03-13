package org.raspinloop.fmi.plugin.launcher.fmi;

import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TTransportException;
import org.eclipse.core.runtime.CoreException;
import org.raspinloop.fmi.Instance;
import org.raspinloop.fmi.plugin.Trace;
import org.raspinloop.fmi.plugin.launcher.BaseRunnerLifeHandler;
import org.raspinloop.fmi.plugin.launcher.ProxyRunnerJob;

public class FmiRunnerLifeHandler extends  BaseRunnerLifeHandler {


	private TServer server;


	public FmiRunnerLifeHandler(ProxyRunnerJob fmiProxyRunnerJob, TServer server) {
		super(fmiProxyRunnerJob);
		this.server = server;
	}
	
	@Override
	public  org.raspinloop.fmi.launcherRunnerIpc.Status freeInstance(Instance c) {
		Trace.launcherRunner("FmiRunnerLifeHandler.terminate called");
		server.stop();
		return org.raspinloop.fmi.launcherRunnerIpc.Status.OK;

	}

	@Override
	public org.raspinloop.fmi.Status reset(Instance c) throws TTransportException, CoreException {
		Trace.launcherRunner("FmiRunnerLifeHandler.reset called");
		return org.raspinloop.fmi.Status.Discard;
	}


	public org.raspinloop.fmi.launcherRunnerIpc.Status stopVMRunner() {
		return super.stopVMRunner();				
	}
}