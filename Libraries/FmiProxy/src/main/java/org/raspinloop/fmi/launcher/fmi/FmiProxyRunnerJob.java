package org.raspinloop.fmi.launcher.fmi;

import org.apache.thrift.server.TServer;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.Proxy;
import org.raspinloop.fmi.launcher.ProxyRunnerJob;



public class FmiProxyRunnerJob extends ProxyRunnerJob {


	private FmiRunnerLifeHandler lifeHandler;
	private IProxyMonitor monitor;

	public FmiProxyRunnerJob(TServer server, Proxy proxy, Runnable runner, IProxyMonitor monitor) {
		super(proxy, runner);
		this.monitor = monitor;
		
		lifeHandler = new FmiRunnerLifeHandler(this, server);
	}	
	

	protected void canceling() {
		lifeHandler.stopVMRunner();	
	}
	
	@Override
	public void run() {
		
			proxy.setFmiRunnerLifeHandler(lifeHandler );
			while(!proxy.isTerminated() ){
				if(monitor.isCanceled())
					return ;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					monitor.aborted(e);
				}
			}
	}
}

