package org.raspinloop.fmi.plugin.launcher.standalone;

import org.apache.thrift.TException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.raspinloop.fmi.plugin.launcher.ProxyRunnerJob;
import org.raspinloop.fmi.plugin.launcher.fmi.FmiRunnerLifeHandler;


public class StandaloneProxyRunnerJob extends ProxyRunnerJob {

	public StandaloneProxyRunnerJob(org.raspinloop.fmi.plugin.launcher.fmi.Proxy proxy, IVMRunner runner, VMRunnerConfiguration runConfig, ILaunch launch) {
		super(proxy, runner, runConfig, launch);
	}	
	
	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		try {
			proxy.setFmiRunnerLifeHandler( new StandaloneRunnerLifeHandler(this));
			if (proxy instanceof TimeSequencer)
			((TimeSequencer)proxy).run();
			return Status.OK_STATUS;
		} catch (InterruptedException | TException e) {
			return Status.CANCEL_STATUS;
		}
	}

}

