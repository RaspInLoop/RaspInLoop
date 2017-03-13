package org.raspinloop.fmi.plugin.launcher;

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
import org.raspinloop.fmi.plugin.launcher.ProxyRunnerJob.LauncherServerJob;

public class BaseRunnerLifeHandler implements RunnerLifeHandler {
	private static final int RUNNER_SERVER_PORT = 9091;
	private org.raspinloop.fmi.launcherRunnerIpc.RunnerService.Client client;
	private LauncherServerJob launcherServer;
	private Instance inst;
	private ProxyRunnerJob fmiProxyRunnerJob;

	public BaseRunnerLifeHandler(ProxyRunnerJob fmiProxyRunnerJob) {
		this.fmiProxyRunnerJob = fmiProxyRunnerJob;
	}

	@Override
	public Instance instanciate(String instanceName, org.raspinloop.fmi.Type fmuType, String fmuGUID, String fmuResourceLocation, boolean visible,
			boolean loggingOn) throws TException {
		// FMI launcher (agent of process to debug )
		Trace.launcherRunner("FmiRunnerLifeHandler.instanciate called");
		inst = new Instance(instanceName, 0, fmuGUID, ModelState.modelInstantiated);
		return inst;
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

	@Override
	public Client startVMRunner(IProgressMonitor monitor) throws TTransportException, CoreException, InterruptedException {
		Trace.launcherRunner("FmiRunnerLifeHandler.startVMRunner called");

		launcherServer = fmiProxyRunnerJob.new LauncherServerJob(RUNNER_SERVER_PORT, monitor);
		launcherServer.schedule();

		// wait for for ready message
		while (fmiProxyRunnerJob.getRunnerPort() == 0 && !monitor.isCanceled()) {
			Thread.sleep(100);
			// TODO ADD timeout ?
		}
		Trace.launcherRunner("runner started and listened on port " + fmiProxyRunnerJob.getRunnerPort());
		TSocket transport = new TSocket("localhost", fmiProxyRunnerJob.getRunnerPort(), 10000, 5000);
		transport.open();
		TProtocol protocol = new TBinaryProtocol(transport);
		client = new Client(protocol);
		Trace.launcherRunner(" launcher connected on port " + fmiProxyRunnerJob.getRunnerPort());			
		return client;
	}

	@Override
	public org.raspinloop.fmi.launcherRunnerIpc.Status stopVMRunner() {
		Trace.launcherRunner("FmiRunnerLifeHandler.stopVMRunner called" + fmiProxyRunnerJob.getRunnerPort());
		try {
			if (client != null)
				return client.terminate();
			else
				return org.raspinloop.fmi.launcherRunnerIpc.Status.Discard;
		} catch (TException e) {
			return  org.raspinloop.fmi.launcherRunnerIpc.Status.Error;
		}
		finally {
			if (launcherServer != null)
				launcherServer.cancel();				
		}
		
	}
}