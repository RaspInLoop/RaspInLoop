/*******************************************************************************
 * Copyright (C) 2018 RaspInLoop
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.raspinloop.fmi.plugin.launcher;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.AbstractJavaLaunchConfigurationDelegate;
import org.eclipse.jdt.launching.ExecutionArguments;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.osgi.framework.Bundle;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonProperties;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.fmi.VMRunnerUtils;
import org.raspinloop.fmi.plugin.Activator;
import org.raspinloop.fmi.plugin.preferences.PluggedHardwareEnumerator;

public abstract class Launcher extends AbstractJavaLaunchConfigurationDelegate {

	
	private static final String TARGET_DEPENDENCY_PATH = "target/dependency/";


	@Override
	public String getVMArguments(ILaunchConfiguration configuration) throws CoreException {

		 List<File> list = new LinkedList<>();
		 Bundle pluginBundle = Platform.getBundle("org.raspinloop.fmi.plugin.fmi");
		 Enumeration<String> entryPaths = pluginBundle.getEntryPaths(TARGET_DEPENDENCY_PATH);
		 while (entryPaths.hasMoreElements()) {
			URL entrypath =  pluginBundle.getEntry( (String) entryPaths.nextElement());
			try {
				list.add (new File(FileLocator.resolve(entrypath).getFile()));
			} catch (IOException e) {
				// Nothing to do with unresolvable URL
			}
		}

		try {
			//Today only pi4JAgent will be found...
			return VMRunnerUtils. getRunnerAgentArgument(list, getHardwareConfigJsonName(configuration), true)
				+  " "
				+  VMRunnerUtils.getWeaverAgentArgument(list, true);
		} catch (ClassNotFoundException | URISyntaxException | IOException e) {
			IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, "Cannot get VMRunner Aruments :"+e.getMessage());
			throw new CoreException(status);
		}
	}


	private String getHardwareConfigJsonName(ILaunchConfiguration configuration) throws CoreException {
		Collection<HardwareProperties> hardwares = HardwareConfiguration.buildList();

		try {
			String selectedHardwareName = configuration.getAttribute(RilMainTab.ATTR_HARDWARE_CONFIG, "");
			for (HardwareProperties hardwareConfig : hardwares) {
				if (selectedHardwareName.equals(hardwareConfig.getComponentName()) && (hardwareConfig instanceof BoardHardware)) {
					GsonProperties conf = new GsonProperties(PluggedHardwareEnumerator.INSTANCE());
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

	protected VMRunnerConfiguration getVMRunnerConfig(ILaunchConfiguration configuration, String mode) throws CoreException {
		
		String mainTypeName = verifyMainTypeName(configuration);
	
		File workingDir = verifyWorkingDirectory(configuration);
		String workingDirName = null;
		if (workingDir != null) {
			workingDirName = workingDir.getAbsolutePath();
		}
		
		// Environment variables
		String[] envp= getEnvironment(configuration);
		
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
	
	public abstract void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException;

}
