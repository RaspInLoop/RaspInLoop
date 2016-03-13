package org.raspinloop.fmi.plugin.launcher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.JavaLaunchDelegate;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonConfig;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.plugin.Activator;
import org.raspinloop.fmi.plugin.configuration.SimulationType;
import org.raspinloop.fmi.plugin.preferences.PluggedHardwareEnumerator;

public class FmiLauncher extends JavaLaunchDelegate {

	

	@Override
	public String[] getClasspath(ILaunchConfiguration configuration) throws CoreException {

		ArrayList<String> cp = new ArrayList<String>(Arrays.asList(super.getClasspath(configuration)));
		cp.addAll(LauncherClasspathUtils.get(configuration));
		String[] cpStr = cp.toArray(new String[cp.size()]);
		Activator.getDefault().log("ClassPath to launch: "+configuration.getName()+": "+Arrays.toString(cpStr));
		return cpStr;
	}
	

	private String getRilFmiAspectWeaverJar(ILaunchConfiguration configuration) {
		return LauncherClasspathUtils.getFmiPath("target/dependency/aspectjweaver-1.8.2.jar");
	}

	@Override
	public String getVMArguments(ILaunchConfiguration configuration) throws CoreException {

		return "-javaagent:\"" + getRilFmiAspectWeaverJar(configuration) + "\" " + super.getVMArguments(configuration);
	}

	@Override
	public String getProgramArguments(ILaunchConfiguration configuration) throws CoreException {
		String simulatedMainClassName = super.getMainTypeName(configuration);

		SimulationType simulationType = SimulationType.valueOf(configuration.getAttribute(RilfmiMainTab.ATTR_SIMULATION_TYPE, SimulationType.FMU.toString()));
		switch (simulationType) {
			case FMU: 
				return getHardwareConfigJsonName(configuration) + " " + simulatedMainClassName + " " + super.getProgramArguments(configuration);
			case STAND_ALONE: 
				return getHardwareConfigJsonName(configuration) + " " +getHardwareGuid(configuration)+" "+ getTimeIncrement(configuration)+" " + getEndTime(configuration)+" "+ getTimeRatio(configuration)+" "+ simulatedMainClassName + " " + super.getProgramArguments(configuration);
			default:
				throw new CoreException(new Status(Status.ERROR, Activator.PLUGIN_ID,"invalid simulation type:" + simulationType));
		}
	}

	private String getTimeIncrement(ILaunchConfiguration configuration) throws NumberFormatException, CoreException {
		TimeUnit standAloneTimeIncUnit = TimeUnit.valueOf(configuration.getAttribute(RilfmiMainTab.ATTR_STANDALONE_TIME_INCREMENT_UNIT, TimeUnit.SECONDS.toString()));
		long standAloneTimeInc = Long.parseLong(configuration.getAttribute(RilfmiMainTab.ATTR_STANDALONE_TIME_INCREMENT, "1"));
		
		return Double.toString(TimeUnit.NANOSECONDS.convert(standAloneTimeInc, standAloneTimeIncUnit)/1E9D);

	}


	private String getEndTime(ILaunchConfiguration configuration) throws CoreException {
		TimeUnit standAloneEndTimeUnit = TimeUnit.valueOf(configuration.getAttribute(RilfmiMainTab.ATTR_STANDALONE_END_TIME_UNIT, TimeUnit.SECONDS.toString()));
		long standAloneEndTime = Long.parseLong(configuration.getAttribute(RilfmiMainTab.ATTR_STANDALONE_END_TIME, "60"));
		
		return Double.toString(TimeUnit.NANOSECONDS.convert(standAloneEndTime, standAloneEndTimeUnit)/1E9D);

	}


	private String getTimeRatio(ILaunchConfiguration configuration) throws CoreException {
		return configuration.getAttribute(RilfmiMainTab.ATTR_STANDALONE_TIME_RATIO, "1");
	}


	private String getHardwareGuid(ILaunchConfiguration configuration) throws CoreException {
		Collection<HardwareConfig> hardwares = HardwareConfiguration.buildList();

		try {
			String selectedHardwareName = configuration.getAttribute(RilfmiMainTab.ATTR_HARDWARE_CONFIG, "");
			for (HardwareConfig hardwareConfig : hardwares) {
				if (selectedHardwareName.equals(hardwareConfig.getName()) && (hardwareConfig instanceof BoardHardware)) {
					return ((BoardHardware)hardwareConfig).getGuid();
				}
			}
			// hardware not found
			IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, "Simulated hardware named '"+selectedHardwareName+"' not found!");
			throw new CoreException(status);
		} catch (Exception e) {
			IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage());
			throw new CoreException(status);
		}		
	}


	private String getHardwareConfigJsonName(ILaunchConfiguration configuration) throws CoreException {
		Collection<HardwareConfig> hardwares = HardwareConfiguration.buildList();

		try {
			String selectedHardwareName = configuration.getAttribute(RilfmiMainTab.ATTR_HARDWARE_CONFIG, "");
			for (HardwareConfig hardwareConfig : hardwares) {
				if (selectedHardwareName.equals(hardwareConfig.getName()) && (hardwareConfig instanceof BoardHardware)) {
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
		SimulationType simulationType = SimulationType.valueOf(configuration.getAttribute(RilfmiMainTab.ATTR_SIMULATION_TYPE, SimulationType.FMU.toString()));
		switch (simulationType) {
			case FMU: return "org.raspinloop.fmi.Boot";
			case STAND_ALONE: return "org.raspinloop.fmi.BootStandAlone";
			default:
				throw new CoreException(new Status(Status.ERROR, Activator.PLUGIN_ID,"invalid simulation type:" + simulationType));
		}

	}
}
