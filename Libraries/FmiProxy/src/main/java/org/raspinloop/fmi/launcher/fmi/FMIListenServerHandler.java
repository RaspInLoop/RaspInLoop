package org.raspinloop.fmi.launcher.fmi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TTransport;
import org.raspinloop.fmi.FmiProxyMonitor;
import org.raspinloop.fmi.launcher.Proxy;
import org.raspinloop.fmi.launcher.ProxyRunnerJob;


public class FMIListenServerHandler implements TServerEventHandler {


	private TServer server;
	private FmiProxyMonitor monitor;
	private Proxy proxy;
	private Runnable runner;
	ExecutorService executor = Executors.newScheduledThreadPool(3);
	

	public FMIListenServerHandler(TServer server,Proxy proxy,  Runnable runner, FmiProxyMonitor monitor) {
		this.server = server;
		this.proxy = proxy;
		this.runner = runner;
		this.monitor = monitor;
		
	}

	@Override
	public ServerContext createContext(TProtocol arg0, TProtocol arg1) {		
		return new FmiProxyRunnerJob(server, proxy, runner, monitor);
	}

	@Override
	public void deleteContext(ServerContext arg0, TProtocol arg1, TProtocol arg2) {
		if (arg0 instanceof ProxyRunnerJob) {
			ProxyRunnerJob fmiProxyRunnerFMIJob = (ProxyRunnerJob)arg0;
				fmiProxyRunnerFMIJob.cancel(); // ensure no more runner for this FMI-Proxy context
		}
	}

	@Override
	public void preServe() {
	}

	@Override
	public void processContext(ServerContext arg0, TTransport arg1, TTransport arg2) {
	}

}
