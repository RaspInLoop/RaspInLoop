package org.raspinloop.fmi.plugin.launcher;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.raspinloop.fmi.launcherRunnerIpc.LauncherService;
import org.raspinloop.fmi.launcherRunnerIpc.LauncherService.Iface;
import org.raspinloop.fmi.launcherRunnerIpc.ReportType;
import org.raspinloop.fmi.plugin.Activator;
import org.raspinloop.fmi.plugin.Trace;

public abstract class ProxyRunnerJob extends Job implements ServerContext {

	private VMRunnerConfiguration runConfig;
	private IVMRunner runner;
	private ILaunch launch;
	protected org.raspinloop.fmi.plugin.launcher.fmi.Proxy proxy;

	private int runnerPort = 0;

	public ProxyRunnerJob(org.raspinloop.fmi.plugin.launcher.fmi.Proxy proxy, IVMRunner runner, VMRunnerConfiguration runConfig, ILaunch launch) {
		super(proxy.getName() + " running....");
		this.proxy = proxy;
		this.runner = runner;
		this.runConfig = runConfig;
		this.launch = launch;
	}



	public int getRunnerPort() {
		return runnerPort;
	}

	public void setRunnerPort(int runnerPort) {
		this.runnerPort = runnerPort;
	}



	class LauncherserviceHanlder implements Iface {

		@Override
		public org.raspinloop.fmi.launcherRunnerIpc.Status Report(ReportType type, String message) throws TException {
			Trace.launcherRunner("IPC: Report called by runner");
			Activator.getDefault().getLog().log(new Status(getSeverity(type), Activator.PLUGIN_ID, message));
			return org.raspinloop.fmi.launcherRunnerIpc.Status.OK;
		}

		int getSeverity(ReportType type) {
			switch (type) {
			case Error:
				return IStatus.ERROR;
			case Info:
				return IStatus.INFO;
			default:
				return IStatus.INFO;
			}
		}

		@Override
		public org.raspinloop.fmi.launcherRunnerIpc.Status ReadyToStart(int runnerClientPort) throws TException {
			Trace.launcherRunner("IPC: ReadyToStart called by runner");
			ProxyRunnerJob.this.setRunnerPort(runnerClientPort);
			return org.raspinloop.fmi.launcherRunnerIpc.Status.OK;
		}
	}

	public class LauncherServerJob extends Job {

		private TServer server;


		public LauncherServerJob(int port, IProgressMonitor monitor) throws TTransportException {
			super("Launcher Server");

			TServerTransport serverTransport = new TServerSocket(port); 
			// timeout in seconds?
			Iface launcherServiceHandler = new ProxyRunnerJob.LauncherserviceHanlder();
			LauncherService.Processor<LauncherService.Iface> processor = new LauncherService.Processor<LauncherService.Iface>(launcherServiceHandler);
			this.server = new TSimpleServer(new TSimpleServer.Args(serverTransport).processor(processor));

			server.setServerEventHandler(new LauncherServerEventHandler(port, monitor));
		}

		@Override
		protected void canceling() {
			server.stop();
			super.canceling();
		}
		@Override
		protected IStatus run(IProgressMonitor monitor) {			
			server.serve();
			return new Status(IStatus.OK, Activator.PLUGIN_ID, "");
		}
	}

	public class LauncherServerEventHandler implements TServerEventHandler {

		private IProgressMonitor monitor;
		private int port;

		public LauncherServerEventHandler(int port, IProgressMonitor monitor) {
			this.port = port;
			this.monitor = monitor;
			
		}

		@Override
		public ServerContext createContext(TProtocol arg0, TProtocol arg1) {
			Trace.launcherRunner("createContext for VMRunner connection");
			return null;
		}

		@Override
		public void deleteContext(ServerContext arg0, TProtocol arg1, TProtocol arg2) {
			Trace.launcherRunner("deleteContext for VMRunner connection");
			monitor.done();
		}

		@Override
		public void preServe() {
			try {
				Trace.launcherRunner("runner connector server listening on port " + port);
				runner.run(runConfig, launch, monitor);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void processContext(ServerContext arg0, TTransport arg1, TTransport arg2) {
			Trace.launcherRunner("processContext for VMRunner connection");
		}

	}
}
