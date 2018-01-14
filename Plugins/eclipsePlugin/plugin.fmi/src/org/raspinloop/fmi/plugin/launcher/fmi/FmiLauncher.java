/*******************************************************************************
 * Copyright (C) 2018 RaspInLoop
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.raspinloop.fmi.plugin.launcher.fmi;


import javax.inject.Inject;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.equinox.log.Logger;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.osgi.util.NLS;
import org.raspinloop.fmi.CoSimulation;
import org.raspinloop.fmi.CoSimulation.Iface;
import org.raspinloop.fmi.VMRunnerUncheckedException;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.Runner;
import org.raspinloop.fmi.launcher.SimulationToolStatus;
import org.raspinloop.fmi.launcher.fmi.FMIListenServerHandler;
import org.raspinloop.fmi.launcher.fmi.FmiProxy;
import org.raspinloop.fmi.launcher.fmi.FmiProxyServer;
import org.raspinloop.fmi.plugin.launcher.Launcher;
import org.raspinloop.fmi.plugin.launcher.RaspinloopProgressMonitor;

public class FmiLauncher extends Launcher {

	@Inject Logger logger;
	
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
			monitor.worked(1);
			// set the default source locator if required
			setDefaultSourceLocator(launch, configuration);

			fmiLaunch(launch, monitor, runner, runConfig);

		} finally {
			monitor.done();
		}
	}



	private void fmiLaunch(final ILaunch launch, final IProgressMonitor monitor, final IVMRunner runner, final VMRunnerConfiguration runConfig) throws CoreException {
		
			// will be responsible to start subtask and animate eclipse progress bar regarding FMIServer events
			IProxyMonitor proxyMonitor = RaspinloopProgressMonitor.wrap(monitor);
	
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
				
				
				FmiProxyServer server = new FmiProxyServer(vmRunner, proxyMonitor);
				try {
					server.start();
				} catch (Exception e) {
					abort("Communication with the simulation tool interrupted.", e, IJavaLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
				}
	}
}
