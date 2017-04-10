package org.raspinloop.fmi.plugin.launcher;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IPersistableSourceLocator;
import org.eclipse.debug.core.sourcelookup.IPersistableSourceLocator2;
import org.eclipse.jdt.launching.AbstractJavaLaunchConfigurationDelegate;
import org.eclipse.jdt.launching.ExecutionArguments;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.osgi.util.NLS;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonConfig;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.plugin.Activator;
import org.raspinloop.fmi.plugin.Trace;
import org.raspinloop.fmi.plugin.configuration.SimulationType;
import org.raspinloop.fmi.CoSimulation;
import org.raspinloop.fmi.CoSimulation.Iface;
import org.raspinloop.fmi.plugin.launcher.fmi.FMIListenServerHandler;
import org.raspinloop.fmi.plugin.launcher.fmi.FmiProxy;
import org.raspinloop.fmi.plugin.launcher.standalone.StandaloneProxyRunnerJob;
import org.raspinloop.fmi.plugin.launcher.standalone.TimeSequencer;
import org.raspinloop.fmi.plugin.preferences.PluggedHardwareEnumerator;

public abstract class Launcher extends AbstractJavaLaunchConfigurationDelegate {

	@Override
	public String[] getClasspath(ILaunchConfiguration configuration) throws CoreException {

		ArrayList<String> cp = new ArrayList<String>(Arrays.asList(super.getClasspath(configuration)));
		cp.addAll(LauncherClasspathUtils.get(configuration));
		String[] cpStr = cp.toArray(new String[cp.size()]);
		Trace.launcherRunner("ClassPath to launch: " + configuration.getName() + ": " + Arrays.toString(cpStr));
		return cpStr;
	}

	private String getRilFmiAspectWeaverJar(ILaunchConfiguration configuration) {
		return LauncherClasspathUtils.getLauncherPath("target/dependency/aspectjweaver-1.8.4.jar");
	}

	@Override
	public String getVMArguments(ILaunchConfiguration configuration) throws CoreException {

		return "-javaagent:\"" + getRilFmiAspectWeaverJar(configuration) + "\" " + super.getVMArguments(configuration);
	}

	@Override
	public String getProgramArguments(ILaunchConfiguration configuration) throws CoreException {
		String simulatedMainClassName = super.getMainTypeName(configuration);
		return getHardwareConfigJsonName(configuration) + " " + simulatedMainClassName + " " + super.getProgramArguments(configuration);
	}

	private String getHardwareConfigJsonName(ILaunchConfiguration configuration) throws CoreException {
		Collection<HardwareConfig> hardwares = HardwareConfiguration.buildList();

		try {
			String selectedHardwareName = configuration.getAttribute(RilMainTab.ATTR_HARDWARE_CONFIG, "");
			for (HardwareConfig hardwareConfig : hardwares) {
				if (selectedHardwareName.equals(hardwareConfig.getComponentName()) && (hardwareConfig instanceof BoardHardware)) {
					GsonConfig conf = new GsonConfig(PluggedHardwareEnumerator.INSTANCE());
					Path temp = Files.createTempFile(null, ".json");

					Files.write(temp, conf.write((BoardHardware) hardwareConfig).getBytes("UTF-8"));
					return temp.toString();
				}
			}
			IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, "Hardware configuration not found");
			throw new CoreException(status);
		} catch (Exception e) {
			IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage());
			throw new CoreException(status);
		}
	}

	@Override
	public String getMainTypeName(ILaunchConfiguration configuration) throws CoreException {
		return "org.raspinloop.fmi.Boot";
	}

	public abstract void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException;

	protected VMRunnerConfiguration getVMRunnerConfig(ILaunchConfiguration configuration) throws CoreException {
		String mainTypeName = verifyMainTypeName(configuration);
		File workingDir = verifyWorkingDirectory(configuration);
		String workingDirName = null;
		if (workingDir != null) {
			workingDirName = workingDir.getAbsolutePath();
		}

		// Environment variables
		String[] envp = getEnvironment(configuration);

		// Program & VM arguments
		String pgmArgs = getProgramArguments(configuration);
		String vmArgs = getVMArguments(configuration);
		ExecutionArguments execArgs = new ExecutionArguments(vmArgs, pgmArgs);

		// VM-specific attributes
		Map<String, Object> vmAttributesMap = getVMSpecificAttributesMap(configuration);

		// Classpath
		String[] classpath = getClasspath(configuration);

		// Create VM config
		VMRunnerConfiguration runConfig = new VMRunnerConfiguration(mainTypeName, classpath);
		runConfig.setProgramArguments(execArgs.getProgramArgumentsArray());
		runConfig.setEnvironment(envp);
		runConfig.setVMArguments(execArgs.getVMArgumentsArray());
		runConfig.setWorkingDirectory(workingDirName);
		runConfig.setVMSpecificAttributesMap(vmAttributesMap);

		// Bootpath
		runConfig.setBootClassPath(getBootpath(configuration));
		return runConfig;
	}	
}
