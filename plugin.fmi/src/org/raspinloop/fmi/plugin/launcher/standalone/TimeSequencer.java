package org.raspinloop.fmi.plugin.launcher.standalone;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.fmi.Instance;
import org.raspinloop.fmi.Type;
import org.raspinloop.fmi.launcherRunnerIpc.RunnerService.Client;
import org.raspinloop.fmi.plugin.Activator;
import org.raspinloop.fmi.plugin.Trace;
import org.raspinloop.fmi.plugin.launcher.HardwareConfiguration;
import org.raspinloop.fmi.plugin.launcher.RilMainTab;
import org.raspinloop.fmi.plugin.launcher.RunnerLifeHandler;
import org.raspinloop.fmi.plugin.launcher.fmi.Proxy;

//
public class TimeSequencer implements Proxy {
	
	final private String guid;
	final private double stepIncrement;
	final private double timeRatio;
	private double duration;

	private RunnerLifeHandler lifeHandler;

	private IProgressMonitor monitor;

	public TimeSequencer(ILaunchConfiguration configuration, IProgressMonitor monitor) throws NumberFormatException, CoreException {
		this.monitor = monitor;
		stepIncrement = getTimeIncrement(configuration);
		duration = getEndTime(configuration);
		timeRatio = getTimeRatio(configuration);
		guid = getHardwareGuid(configuration);
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

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFmiRunnerLifeHandler(RunnerLifeHandler lifeHandler) {
		this.lifeHandler = lifeHandler;
	}

	private Client client;

	public void run() throws TException, InterruptedException {
		try {
			instanciate("standAlone Instance", Type.CoSimulation, guid, "", true, true);						
		} catch (TException e1) {
			return;
		}

		try{
		Trace.launcherRunner("-->setupExperiment");
		client.setupExperiment(false, 0.0, new Date().getTime() / 1000.0, false, 0.0);
		Trace.launcherRunner("<--setupExperiment");
		
		double currentCommunicationPoint = 0;
		double communicationStepSize = stepIncrement;
		Trace.timeSequencer("Simulated Time: Duration[" + duration + " s], StepIncrement[" + stepIncrement + " s], Ratio[" + timeRatio + "]");
		while (currentCommunicationPoint < duration) {
			Thread.sleep((long) (1000 * timeRatio * stepIncrement));
			currentCommunicationPoint += stepIncrement;
			
			Trace.timeSequencer("-->doStep "+currentCommunicationPoint+ " " + communicationStepSize);
			client.doStep(currentCommunicationPoint, communicationStepSize, false);
			Trace.timeSequencer("<--doStep");
		}
		
		}
		catch(Exception e){
			Trace.launcherRunner("Client exception: " +e.getMessage());
		}
		finally {
		
			lifeHandler.stopVMRunner();
			lifeHandler.freeInstance(null);
		}
	}

	public void instanciate(String instanceName, Type fmuType, String fmuGUID, String fmuResourceLocation, boolean visible, boolean loggingOn)
			throws TException {
		if (lifeHandler == null) {
			// TODO log error
			throw new TException("lifeHandlerNotSet");
		}

		try {
			client = lifeHandler.startVMRunner(monitor);
			lifeHandler.instanciate(instanceName, fmuType, fmuGUID, fmuResourceLocation, visible, loggingOn);
		} catch (Exception e) {
			// TODO log error
			new TException(e.getMessage(), e);
		}		
	}

	@Override
	public String getName() {
		return "Simulated Time: Duration[" + duration + " s], StepIncrement[" + stepIncrement + " s], Ratio[" + timeRatio + "]";
	}
}
