package org.raspinloop.fmi.plugin.launcher.standalone;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.osgi.util.NLS;
import org.raspinloop.fmi.plugin.launcher.Launcher;
import org.raspinloop.fmi.plugin.launcher.ProxyRunnerJob;

public class StandaloneLauncher extends Launcher {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}

		monitor.beginTask(NLS.bind("{0}...", new String[] { configuration.getName() }), 3); //$NON-NLS-1$
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}
		try {
			monitor.subTask("Verify launch attribute");

			IVMRunner runner = getVMRunner(configuration, mode);
			VMRunnerConfiguration runConfig = getVMRunnerConfig(configuration);

			// check for cancellation
			if (monitor.isCanceled()) {
				return;
			}
			// set the default source locator if required
			setDefaultSourceLocator(launch, configuration);
			
			monitor.worked(1);
			standaloneLaunch(launch, configuration, monitor, runner, runConfig);

		} finally {
			monitor.done();
		}

	}
	private void standaloneLaunch(ILaunch launch, ILaunchConfiguration configuration, IProgressMonitor monitor, IVMRunner runner,
			VMRunnerConfiguration runConfig) throws NumberFormatException, CoreException {
		ProxyRunnerJob standaloneProxyRunnerFMIJob = new StandaloneProxyRunnerJob(new TimeSequencer(configuration, monitor), runner, runConfig, launch);
		standaloneProxyRunnerFMIJob.setPriority(Job.SHORT);
		standaloneProxyRunnerFMIJob.schedule();
		monitor.done();
	}
}
