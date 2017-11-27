package org.raspinloop.fmi.launcher.fmi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TTransport;
import org.raspinloop.fmi.WaitForFMIContext;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.Proxy;
import org.raspinloop.fmi.launcher.ProxyRunnerJob;


public class FMIListenServerHandler implements TServerEventHandler {


	private TServer server;
	private WaitForFMIContext waitingProcess;
	private ProxyRunnerJob fmiProxyRunnerFMIJob;
	private IProxyMonitor monitor;
	private Proxy proxy;
	private Runnable runner;
	ExecutorService executor = Executors.newScheduledThreadPool(3);
	private Future<?> future;
	

	public FMIListenServerHandler(TServer server,Proxy proxy,  Runnable runner, IProxyMonitor monitor) {
		this.server = server;
		this.proxy = proxy;
		this.runner = runner;
		this.monitor = monitor;
		
	}

	@Override
	public ServerContext createContext(TProtocol arg0, TProtocol arg1) {
		if (waitingProcess != null)
			waitingProcess.connected();
		
		fmiProxyRunnerFMIJob = new FmiProxyRunnerJob(server, proxy, runner, monitor);
		future = executor.submit(fmiProxyRunnerFMIJob);
		
		monitor.done();
		return fmiProxyRunnerFMIJob;
	}

	@Override
	public void deleteContext(ServerContext arg0, TProtocol arg1, TProtocol arg2) {
		if (arg0 instanceof ProxyRunnerJob) {
			ProxyRunnerJob job = (ProxyRunnerJob) arg0;
			future.cancel(true);
			server.stop();
		}
	}

	@Override
	public void preServe() {
		waitingProcess = new WaitForFMIContext(executor);
		monitor.subTask(waitingProcess.getLabel());
		monitor.worked(1);
		monitor.subTask(waitingProcess.getLabel());
		try {
			waitingProcess.waitFmi(server, monitor);
		} catch (Exception e) {
			monitor.done();
		} finally {
			
		}
	}

	@Override
	public void processContext(ServerContext arg0, TTransport arg1, TTransport arg2) {
	}

}
