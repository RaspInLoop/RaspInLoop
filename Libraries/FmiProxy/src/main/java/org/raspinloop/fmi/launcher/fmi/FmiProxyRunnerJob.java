package org.raspinloop.fmi.launcher.fmi;

import org.apache.thrift.server.TServer;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.Proxy;
import org.raspinloop.fmi.launcher.ProxyRunnerJob;
import org.raspinloop.fmi.launcher.Runner;
import org.raspinloop.fmi.launcher.SimulationToolStatus;



public class FmiProxyRunnerJob extends ProxyRunnerJob {

	private FmiRunnerLifeHandler lifeHandler;
	private IProxyMonitor monitor;

	public FmiProxyRunnerJob(TServer server, Proxy proxy, Runner runner, IProxyMonitor monitor) {
		super(proxy, runner);
		this.monitor = monitor;
		monitor.simulationToolsStatusChanged(SimulationToolStatus.CONNECTED);
		lifeHandler = new FmiRunnerLifeHandler(this, server);
		proxy.setFmiRunnerLifeHandler(lifeHandler );
	}	
	
	public void cancel() {
		
		super.cancel();
		monitor.simulationToolsStatusChanged(SimulationToolStatus.STOPPED);
	}

}

