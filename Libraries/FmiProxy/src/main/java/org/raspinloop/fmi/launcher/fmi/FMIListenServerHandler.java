package org.raspinloop.fmi.launcher.fmi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TTransport;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.Proxy;
import org.raspinloop.fmi.launcher.ProxyRunnerJob;
import org.raspinloop.fmi.launcher.Runner;


public class FMIListenServerHandler implements TServerEventHandler {


	private TServer server;
	private IProxyMonitor monitor;
	private Proxy proxy;
	private Runner runner;
	ExecutorService executor = Executors.newScheduledThreadPool(3);
	

	public FMIListenServerHandler(TServer server,Proxy proxy,  Runner runner,IProxyMonitor monitor) {
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
		//simple server, so stop the server
		server.stop();
	}

	@Override
	public void preServe() {
	}

	@Override
	public void processContext(ServerContext arg0, TTransport arg1, TTransport arg2) {
	}

}
