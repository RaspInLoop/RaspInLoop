package org.raspinloop.fmi.plugin.launcher.standalone;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.osgi.util.NLS;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.fmi.VMRunnerUncheckedException;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.Runner;
import org.raspinloop.fmi.launcher.standalone.StandaloneProxyRunnerJob;
import org.raspinloop.fmi.launcher.standalone.TimeSequencerProxy;
import org.raspinloop.fmi.plugin.Activator;
import org.raspinloop.fmi.plugin.launcher.HardwareConfiguration;
import org.raspinloop.fmi.plugin.launcher.Launcher;
import org.raspinloop.fmi.plugin.launcher.RaspinloopProgressMonitor;
import org.raspinloop.fmi.plugin.launcher.RilMainTab;

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
			VMRunnerConfiguration runConfig = getVMRunnerConfig(configuration, mode);

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
		
		IProxyMonitor proxyMonitor = RaspinloopProgressMonitor.wrap(monitor);
		double stepIncrement = getTimeIncrement(configuration);
		double duration = getEndTime(configuration);
		double timeRatio = getTimeRatio(configuration);
		String guid = getHardwareGuid(configuration);
		Runner vmRunner = new Runner(){

			@Override
			public void run() {
				try {
					runner.run(runConfig, launch, monitor);
				} catch (CoreException e) {
					throw new VMRunnerUncheckedException(e);
				}
				
			}

			@Override
			public void terminate() {
				try {
					launch.terminate();
				} catch (DebugException e) {
					throw new VMRunnerUncheckedException(e);
				}
				
			}};
			
			TimeSequencerProxy proxy = new TimeSequencerProxy(guid, stepIncrement, timeRatio, duration, proxyMonitor);
			StandaloneProxyRunnerJob standaloneProxyRunnerFMIJob = new StandaloneProxyRunnerJob(proxy, vmRunner);
			Job job = Job.create("Standalone FMI", new ICoreRunnable() {
				
				@Override
				public void run(IProgressMonitor monitor) throws CoreException {
					try {
						proxy.run();
					} catch (TException | InterruptedException e) {
						IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage());
						throw new CoreException(status);
					}
					
				}
			});
			job.schedule();
		monitor.done();
	}
	
	private String getHardwareGuid(ILaunchConfiguration configuration) throws CoreException {
		Collection<HardwareProperties> hardwares = HardwareConfiguration.buildList();

		try {
			String selectedHardwareName = configuration.getAttribute(RilMainTab.ATTR_HARDWARE_CONFIG, "");
			for (HardwareProperties hardwareConfig : hardwares) {
				if (selectedHardwareName.equals(hardwareConfig.getComponentName()) && (hardwareConfig instanceof BoardHardware)) {
					return ((BoardHardware) hardwareConfig).getGuid();
				}
			}
			// hardware not found
			IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, "Simulated hardware named '" + selectedHardwareName + "' not found!");
			throw new CoreException(status);
		} catch (Exception e) {
			IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage());
			throw new CoreException(status);
		}
	}

	private double getTimeIncrement(ILaunchConfiguration configuration) throws NumberFormatException, CoreException {
		TimeUnit standAloneTimeIncUnit = TimeUnit.valueOf(configuration.getAttribute(RilMainTab.ATTR_STANDALONE_TIME_INCREMENT_UNIT,
				TimeUnit.SECONDS.toString()));
		long standAloneTimeInc = Long.parseLong(configuration.getAttribute(RilMainTab.ATTR_STANDALONE_TIME_INCREMENT, "1"));

		return TimeUnit.NANOSECONDS.convert(standAloneTimeInc, standAloneTimeIncUnit) / 1E9D;

	}

	private double getEndTime(ILaunchConfiguration configuration) throws CoreException {
		TimeUnit standAloneEndTimeUnit = TimeUnit.valueOf(configuration.getAttribute(RilMainTab.ATTR_STANDALONE_END_TIME_UNIT, TimeUnit.SECONDS.toString()));
		long standAloneEndTime = Long.parseLong(configuration.getAttribute(RilMainTab.ATTR_STANDALONE_END_TIME, "60"));

		return TimeUnit.NANOSECONDS.convert(standAloneEndTime, standAloneEndTimeUnit) / 1E9D;

	}

	private double getTimeRatio(ILaunchConfiguration configuration) throws CoreException {
		return Double.parseDouble(configuration.getAttribute(RilMainTab.ATTR_STANDALONE_TIME_RATIO, "1"));
	}

}
