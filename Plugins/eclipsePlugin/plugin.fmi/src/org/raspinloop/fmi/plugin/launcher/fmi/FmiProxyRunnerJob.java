package org.raspinloop.fmi.plugin.launcher.fmi;

import org.apache.thrift.server.TServer;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.raspinloop.fmi.plugin.launcher.ProxyRunnerJob;


public class FmiProxyRunnerJob extends ProxyRunnerJob {


	private FmiRunnerLifeHandler lifeHandler;

	public FmiProxyRunnerJob(TServer server, org.raspinloop.fmi.plugin.launcher.fmi.Proxy proxy, IVMRunner runner, VMRunnerConfiguration runConfig, ILaunch launch) {
		super(proxy, runner, runConfig, launch);
		
		lifeHandler = new FmiRunnerLifeHandler(this, server);
	}	
	
	@Override
	protected void canceling() {
		lifeHandler.stopVMRunner();
		super.canceling();
		
	}
	
	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		try {
			proxy.setFmiRunnerLifeHandler(lifeHandler );
			while(!proxy.isTerminated() ){
				if(monitor.isCanceled())
					return Status.CANCEL_STATUS;
				Thread.sleep(100);
			}
						
			return Status.OK_STATUS;
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		}
	}
	

}

