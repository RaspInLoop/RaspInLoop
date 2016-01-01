package org.raspinloop.fmi.plugin.launcher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.JavaLaunchDelegate;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonConfig;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.plugin.Activator;
import org.raspinloop.fmi.plugin.preferences.PluggedHardwareEnumerator;

public class FmiLauncher extends JavaLaunchDelegate {

	

	@Override
	public String[] getClasspath(ILaunchConfiguration configuration) throws CoreException {

		ArrayList<String> cp = new ArrayList<String>(Arrays.asList(super.getClasspath(configuration)));
		cp.addAll(LauncherClasspathUtils.get(configuration));
		return cp.toArray(new String[cp.size()]);
	}
	

	private String getRilFmiAspectWeaverJar(ILaunchConfiguration configuration) {
		return LauncherClasspathUtils.getFmiPath("target/dependency/aspectjweaver-1.8.4.jar");
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
		return "org.raspinloop.fmi.Boot";
	}
}
