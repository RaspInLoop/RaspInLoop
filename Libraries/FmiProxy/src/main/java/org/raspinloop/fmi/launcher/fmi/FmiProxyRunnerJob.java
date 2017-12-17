package org.raspinloop.fmi.launcher.fmi;

import org.apache.thrift.server.TServer;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.Proxy;
import org.raspinloop.fmi.launcher.ProxyRunnerJob;



public class FmiProxyRunnerJob extends ProxyRunnerJob {

	private FmiRunnerLifeHandler lifeHandler;

	public FmiProxyRunnerJob(TServer server, Proxy proxy, Runnable runner, IProxyMonitor monitor) {
		super(proxy, runner);
		
		lifeHandler = new FmiRunnerLifeHandler(this, server);
		proxy.setFmiRunnerLifeHandler(lifeHandler );
	}	
	
	protected void canceling() {
		lifeHandler.stopVMRunner();	
	}

}

