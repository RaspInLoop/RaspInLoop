package org.raspinloop.fmi.plugin.launcher.fmi;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.osgi.util.NLS;
import org.raspinloop.fmi.CoSimulation;
import org.raspinloop.fmi.CoSimulation.Iface;
import org.raspinloop.fmi.plugin.launcher.Launcher;

public class FmiLauncher extends Launcher {

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
			monitor.worked(1);
			// set the default source locator if required
			setDefaultSourceLocator(launch, configuration);

			fmiLaunch(launch, monitor, runner, runConfig);

		} finally {
			monitor.done();
		}
	}

	private void fmiLaunch(ILaunch launch, IProgressMonitor monitor, IVMRunner runner, VMRunnerConfiguration runConfig) throws CoreException {
		
		FmiProxy fmiProxy = new FmiProxy(monitor);
		CoSimulation.Processor<Iface> processor = new CoSimulation.Processor<Iface>(fmiProxy);

		
		TServerTransport serverTransport;
		try {
			serverTransport = new TServerSocket(9090);
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

	
			server.setServerEventHandler(new FMIListenServerHandler(server, fmiProxy, runner, runConfig, launch, monitor));

			monitor.subTask("Starting the FMI-Proxy server...");

			server.serve();
		} catch (TTransportException e) {
			abort("Communication with the simulation tool interrupted.", e, IJavaLaunchConfigurationConstants.ERR_INTERNAL_ERROR);
		}
	}
}
