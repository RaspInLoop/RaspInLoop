package org.raspinloop.fmi.plugin.launcher.fmi;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TTransport;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.raspinloop.fmi.plugin.launcher.ProxyRunnerJob;
import org.raspinloop.fmi.plugin.launcher.WaitForFMIContext;

public class FMIListenServerHandler implements TServerEventHandler {

	private ILaunch launch;
	private IVMRunner runner;
	private VMRunnerConfiguration runConfig;
	private TServer server;
	private WaitForFMIContext waitingProcess;
	private ProxyRunnerJob fmiProxyRunnerFMIJob;
	private IProgressMonitor monitor;
	private org.raspinloop.fmi.plugin.launcher.fmi.Proxy proxy;

	public FMIListenServerHandler(TServer server, org.raspinloop.fmi.plugin.launcher.fmi.Proxy proxy,  IVMRunner runner, VMRunnerConfiguration runConfig, ILaunch launch, IProgressMonitor monitor) {
		this.server = server;
		this.proxy = proxy;
		this.runner = runner;
		this.runConfig = runConfig;
		this.launch = launch;
		this.monitor = monitor;
	}

	@Override
	public ServerContext createContext(TProtocol arg0, TProtocol arg1) {
		if (waitingProcess != null)
			waitingProcess.connected();
		launch.removeProcess(waitingProcess);
		
		fmiProxyRunnerFMIJob = new FmiProxyRunnerJob(server, proxy, runner, runConfig, launch);
		fmiProxyRunnerFMIJob.setPriority(Job.SHORT);
		fmiProxyRunnerFMIJob.schedule();
		monitor.done();
		return fmiProxyRunnerFMIJob;
	}

	@Override
	public void deleteContext(ServerContext arg0, TProtocol arg1, TProtocol arg2) {
		if (arg0 instanceof ProxyRunnerJob) {
			ProxyRunnerJob job = (ProxyRunnerJob) arg0;
			job.cancel();
			server.stop();
		}
	}

	@Override
	public void preServe() {
		waitingProcess = new WaitForFMIContext(launch);
		launch.addProcess(waitingProcess);
		monitor.worked(1);
		monitor.subTask(waitingProcess.getLabel());
		try {
			waitingProcess.waitFmi(server, monitor);
		} catch (CoreException e) {
			monitor.done();
		} finally {
			
		}
	}

	@Override
	public void processContext(ServerContext arg0, TTransport arg1, TTransport arg2) {
	}

}
