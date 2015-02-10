package org.raspinloop.fmi.plugin.launcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.JavaLaunchDelegate;
import org.osgi.framework.Bundle;

public class FmiLauncher extends JavaLaunchDelegate {

	Bundle bundle = Platform.getBundle("org.raspinloop.fmi.plugin.fmi");

	@Override
	public String[] getClasspath(ILaunchConfiguration configuration)
			throws CoreException {

		ArrayList<String> cp = new ArrayList<String>(Arrays.asList(super
				.getClasspath(configuration)));
		cp.addAll(getRilFmiClasspath(configuration));
		return cp.toArray(new String[cp.size()]);
	}

	private Collection<? extends String> getRilFmiClasspath(
			ILaunchConfiguration configuration) {
		return Arrays.asList(getPath("target/dependency/aspectjrt-1.8.4.jar"),
				getPath("target/dependency/commons-codec-1.6.jar"),
				getPath("target/dependency/commons-logging-1.1.1.jar"),
				getPath("target/dependency/httpclient-4.2.5.jar"),
				getPath("target/dependency/httpcore-4.2.4.jar"),
				getPath("target/dependency/jna-3.5.2.jar"),
				getPath("target/dependency/libthrift-0.9.2.jar"),
				getPath("target/dependency/log4j-1.2.17.jar"),
				getPath("target/dependency/pi4j-core-0.0.5.jar"),
				getPath("target/dependency/pi4j-device-0.0.5.jar"),
				getPath("target/dependency/pi4j-example-0.0.5.jar"),
				getPath("target/dependency/pi4j-gpio-extension-0.0.5.jar"),
				getPath("target/dependency/pi4j-service-0.0.5.jar"),
				getPath("target/dependency/rilFmiLauncher-1.0.0-SNAPSHOT.jar"),
				getPath("target/dependency/slf4j-api-1.5.8.jar"),
				getPath("target/dependency/slf4j-log4j12-1.7.9.jar") );
	}

	private String getPath(String string) {
		try {
			URL entry = bundle.getEntry(string);
			if (entry == null) {
				System.err.println("coulnd find : "+string);
				return "";
			}
				
			return new File(FileLocator.resolve(entry).getFile()).getAbsolutePath();
		} catch (IOException e) {
			System.err.println("coulnd find : "+string+": "+ e.getMessage());
			return "";
		}
	}

	private String getRilFmiAspectWeaverJar(ILaunchConfiguration configuration) {
		return getPath("target/dependency/aspectjweaver-1.8.4.jar");
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
