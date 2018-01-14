package org.raspinloop.fmi.plugin.launcher;

import org.eclipse.core.runtime.IProgressMonitor;
import org.raspinloop.fmi.AbstractProxyMonitor;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.ProcessStatus;
import org.raspinloop.fmi.launcher.SimulationToolStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RaspinloopProgressMonitor extends AbstractProxyMonitor{

	private Logger log = LoggerFactory.getLogger(RaspinloopProgressMonitor.class);
	private IProgressMonitor monitor;


	public RaspinloopProgressMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	public static IProxyMonitor wrap(IProgressMonitor monitor) {
			return new RaspinloopProgressMonitor(monitor);
	}

	@Override
	public void worked(int work) {
		monitor.worked(work);
	}

	@Override
	public boolean isCanceled() {
		
		return monitor.isCanceled();
	}


	@Override
	public void processStatusChanged(ProcessStatus status) {
		log.debug("Status changed: " + status);
		switch (status) {
		case CANCELLED:
			break;
		case STARTED:
			monitor.done();
			break;
		case STARTING:
			monitor.subTask("Waiting for Process Start");
			break;
		case STOPPED:
			monitor.done();
			break;
		default:
			break;
		
		}
		
	}

	@Override
	public void simulationToolsStatusChanged(SimulationToolStatus status) {
		log.debug("Simulation tool Status changed: " + status);
		switch (status) {
		case WAITING:
			monitor.subTask("Waiting for Simulation Start...");
			break;
		case CONNECTED:
			monitor.done();			
			break;
		case SIMULATING:
			monitor.subTask("Simulating...");
			break;
		case STOPPED:
			monitor.done();
			break;
		default:
			break;
		
		}
	}

}
