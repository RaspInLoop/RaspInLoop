package org.raspinloop.agent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.raspinloop.agent.launcherRunnerIpc.RunnerService.Iface;
import org.raspinloop.agent.launcherRunnerIpc.Status;
import org.raspinloop.agent.launcherRunnerIpc.StatusKind;
import org.raspinloop.hwemulation.HwEmulationFactory;
import org.raspinloop.timeemulation.SimulatedTime;

public class CSHandler implements Iface {

	final static Logger logger = Logger.getLogger(CSHandler.class);

	private static CSHandler INST;

	private static Object lock;

	private HwEmulationFactory hwEmulationFactory;

	private List<ExperimentListener> experimentListenerList = new LinkedList<ExperimentListener>();
		

	public void registerHardware(HwEmulationFactory hwEmulationFactory) {
		this.hwEmulationFactory = hwEmulationFactory;

	}

	public void addExperimentListener(
			ExperimentListener experimentListener) {
		experimentListenerList.add(experimentListener);
	}

	public HwEmulationFactory getHwEmulationFactory(){
		return hwEmulationFactory;
	}

	@Override
	public Status setupExperiment(boolean toleranceDefined, double tolerance, double startTime, boolean stopTimeDefined, double stopTime) throws TException {				
		
		if (stopTimeDefined)
			SimulatedTime.INST.setup(startTime, stopTime);
		else
			SimulatedTime.INST.setup(startTime);

		for (ExperimentListener listener : experimentListenerList) {
			listener.notifyStart(SimulatedTime.INST, hwEmulationFactory.get());
		}
		// An FMU for Co-Simulation might ignore this argument.
		// ( see p22 of
		// https://svn.modelica.org/fmi/branches/public/specifications/v2.0/FMI_for_ModelExchange_and_CoSimulation_v2.0.pdf)
		
		hwEmulationFactory.get().enterInitialize();
		return hwEmulationFactory.get().exitInitialize() ? Status.OK : Status.Error;

	}

	@Override
	public Status terminate() throws TException {
		hwEmulationFactory.get().terminate();
		for (ExperimentListener listener : experimentListenerList) {
			listener.notifyStop(hwEmulationFactory.get());
		}
		return Status.OK;
	}

	@Override
	public List<Double> getReal(List<Integer> refs) throws TException {
		try {
			return hwEmulationFactory.get().getReal(refs);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<Integer> getInteger(List<Integer> refs) throws TException {
		try {
			return hwEmulationFactory.get().getInteger(refs);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<Boolean> getBoolean(List<Integer> refs) throws TException {
		try {
			return hwEmulationFactory.get().getBoolean(refs);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Status setReal(Map<Integer, Double> ref_values) throws TException {
		return hwEmulationFactory.get().setReal(ref_values) ? Status.OK
				: Status.Error;
	}

	@Override
	public Status setInteger(Map<Integer, Integer> ref_values) throws TException {
		return hwEmulationFactory.get().setInteger(ref_values) ? Status.OK
				: Status.Error;
	}

	@Override
	public Status setBoolean(Map<Integer, Boolean> ref_values) throws TException {
		return hwEmulationFactory.get().setBoolean(ref_values) ? Status.OK
				: Status.Error;
	}

	@Override
	public Status cancelStep() throws TException {
		logger.warn("cancelStep(NOT supported) was called ");
		// always fmi2CancelStep is invalid, because model is never in
		// modelStepInProgress state.
		return Status.Error;
	}

	@Override
	public Status doStep(double currentCommunicationPoint, double communicationStepSize, boolean noSetFMUStatePriorToCurrentPoint) throws TException {
		SimulatedTime.INST.doStep(communicationStepSize);
		return Status.OK;
	}

	@Override
	public Status getStatus(StatusKind s) throws TException {
		return Status.Discard;
	}

	@Override
	public double getRealStatus(StatusKind s) throws TException {
		if (s == StatusKind.LastSuccessfulTime) {
			return SimulatedTime.INST.getCurrentTimeNano()/1000000000.0;
		}
		return 0;
	}

	@Override
	public boolean getBooleanStatus(StatusKind s) throws TException {
		return false;
	}

	public static CSHandler getInstance() {	
		if (INST ==null){
			synchronized(lock){
				if (INST ==null){
					INST = new CSHandler();
				}
			}
		}
		return INST; 
	}

}
