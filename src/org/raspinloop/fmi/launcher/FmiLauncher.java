package org.raspinloop.fmi.launcher;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate2;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.internal.launching.LaunchingMessages;
import org.eclipse.jdt.internal.launching.LaunchingPlugin;
import org.eclipse.jdt.launching.AbstractJavaLaunchConfigurationDelegate;
import org.eclipse.jdt.launching.ExecutionArguments;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IVMConnector;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaLaunchDelegate;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

public class FmiLauncher extends JavaLaunchDelegate {

	Bundle bundle = Platform.getBundle("org.raspinloop.fmi");

	@Override
	public String[] getClasspath(ILaunchConfiguration configuration)
			throws CoreException {

		ArrayList<String> cp = new ArrayList<>(Arrays.asList(super
				.getClasspath(configuration)));
		cp.addAll(getRilFmiClasspath(configuration));
		return cp.toArray(new String[cp.size()]);
	}

	private Collection<? extends String> getRilFmiClasspath(
			ILaunchConfiguration configuration) {
		return Arrays.asList(getPath("runtimeLibs/aspectjrt.jar"),
				getPath("runtimeLibs/commons-codec-1.6.jar"),
				getPath("runtimeLibs/commons-logging-1.1.1.jar"),
				getPath("runtimeLibs/httpclient-4.2.5.jar"),
				getPath("runtimeLibs/httpcore-4.2.4.jar"),
				getPath("runtimeLibs/jna.jar"),
				getPath("runtimeLibs/libthrift-0.9.2.jar"),
				getPath("runtimeLibs/log4j-1.2.14.jar"),
				getPath("runtimeLibs/pi4j-core.jar"),
				getPath("runtimeLibs/pi4j-device.jar"),
				getPath("runtimeLibs/pi4j-example.jar"),
				getPath("runtimeLibs/pi4j-gpio-extension.jar"),
				getPath("runtimeLibs/pi4j-service.jar"),
				getPath("runtimeLibs/ril-launcher.jar"),
				getPath("runtimeLibs/aspectjrt.jar"),
				getPath("runtimeLibs/slf4j-api-1.5.8.jar"),
				getPath("runtimeLibs/slf4j-log4j12-1.5.8.jar"));
	}

	private String getPath(String string) {
		try {
			return new File(FileLocator.resolve(bundle.getEntry(string)).getFile()).getAbsolutePath();
		} catch (IOException e) {
			// TODO log error
			return "";
		}
	}

	private String getRilFmiAspectWeaverJar(ILaunchConfiguration configuration) {
		return getPath("runtimeLibs/aspectjweaver.jar");
	}

	@Override
	public String getVMArguments(ILaunchConfiguration configuration)
			throws CoreException {

		return "-javaagent:\"" + getRilFmiAspectWeaverJar(configuration)
				+ "\" " + super.getVMArguments(configuration);
	}

	@Override
	public String getProgramArguments(ILaunchConfiguration configuration)
			throws CoreException {
		String simulatedMaintype = super.getMainTypeName(configuration);
		return simulatedMaintype + " "
				+ super.getProgramArguments(configuration);
	}

	@Override
	public String getMainTypeName(ILaunchConfiguration configuration)
			throws CoreException {
		return "org.raspinloop.fmi.Boot";
	}
}
