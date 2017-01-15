package org.raspinloop.fmi;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.raspinloop.fmi.CoSimulation.Iface;
import org.raspinloop.fmi.internal.hwemulation.HwEmulationFactory;
import org.raspinloop.fmi.internal.timeemulation.SimulatedTime;
import org.raspinloop.fmi.modeldescription.Constants;

public class CSHandler implements Iface {

	final static Logger logger = Logger.getLogger(CSHandler.class);

	private HwEmulationFactory hwEmulationFactory;

	private List<ExperimentListener> experimentListenerList = new LinkedList<ExperimentListener>();

	private String hdDescriptionJson;;
		

	public CSHandler(String hdDescriptionjson) {		
		this.hdDescriptionJson = hdDescriptionjson;
	}

	public void registerHardware(HwEmulationFactory hwEmulationFactory) {
		this.hwEmulationFactory = hwEmulationFactory;

	}

	// TODO: should left on C++ ril-fmi.dll
	@Override
	public String getVersion() throws TException {
		return Constants.version;
	}

	// TODO: should left on C++ ril-fmi.dll
	@Override
	public String getTypesPlatform() throws TException {
		return "default";
	}

	@Override
	public Instance instanciate(String instanceName, Type fmuType,
			String fmuGUID, String fmuResourceLocation, boolean visible,
			boolean loggingOn) throws TException {
		Instance inst = new Instance();
		inst.GUID = fmuGUID;
		inst.instanceName = instanceName;
		inst.state = ModelState.modelInstantiated;
		inst.componentRef = 0;
		try {
		if (hwEmulationFactory.create(inst, hdDescriptionJson) != null)
			return inst;
		else{
			logger.fatal("Cannot create instance for GUID["+inst.GUID+"] and json["+hdDescriptionJson+"]");
			return null;
		}
		}catch(Exception e){
			logger.fatal("Cannot create instance", e);
			return null;
		}
	}

	@Override
	public Status setupExperiment(Instance c, boolean toleranceDefined,
			double tolerance, double startTime, boolean stopTimeDefined,
			double stopTime) throws TException {
		if (stopTimeDefined)
			SimulatedTime.INST.setup(startTime, stopTime);
		else
			SimulatedTime.INST.setup(startTime);

		for (ExperimentListener listener : experimentListenerList) {
			listener.notifyStart(SimulatedTime.INST, hwEmulationFactory.get(c));
		}
		// An FMU for Co-Simulation might ignore this argument.
		// ( see p22 of
		// https://svn.modelica.org/fmi/branches/public/specifications/v2.0/FMI_for_ModelExchange_and_CoSimulation_v2.0.pdf)
		return Status.OK;
	}

	@Override
	public Status enterInitializationMode(Instance c) throws TException {
		return hwEmulationFactory.get(c).enterInitialize() ? Status.OK
				: Status.Error;
	}

	@Override
	public Status exitInitializationMode(Instance c) throws TException {
		try {
			if (!SimulatedTime.INST.waitForApplicationStarting())
				return Status.Error;
			else
				return hwEmulationFactory.get(c).exitInitialize() ? Status.OK : Status.Error;
		} catch (InterruptedException e) {
			logger.fatal("Waiting for application start is interrupted" + e.getLocalizedMessage());
			return Status.Error;
		}

	}

	@Override
	public Status terminate(Instance c) throws TException {
		hwEmulationFactory.get(c).terminate();
		for (ExperimentListener listener : experimentListenerList) {
			listener.notifyStop(hwEmulationFactory.get(c));
		}
		return Status.OK;
	}

	@Override
	public Status reset(Instance c) throws TException {
		hwEmulationFactory.get(c).reset();
		for (ExperimentListener listener : experimentListenerList) {
			listener.notifyStop( hwEmulationFactory.get(c));
			listener.notifyStart(SimulatedTime.INST, hwEmulationFactory.get(c));
		}		
		return Status.OK;
	}

	@Override
	public void freeInstance(Instance c) throws TException {
		hwEmulationFactory.remove(c);
	}

	@Override
	public List<Double> getReal(Instance c, List<Integer> refs)
			throws TException {
		try {
			return hwEmulationFactory.get(c).getReal(refs);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<Integer> getInteger(Instance c, List<Integer> refs)
			throws TException {
		try {
			return hwEmulationFactory.get(c).getInteger(refs);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<Boolean> getBoolean(Instance c, List<Integer> refs)
			throws TException {
		try {
			return hwEmulationFactory.get(c).getBoolean(refs);
		} catch (Exception e) {
			return Collections.emptyList();
		}

	}

	@Override
	public List<String> getString(Instance c, List<Integer> refs)
			throws TException {
		try {
			logger.warn("getString(NOT supported) was called " + c + " "
					+ refs.toString());
			return Collections.emptyList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Status setReal(Instance c, Map<Integer, Double> ref_values)
			throws TException {
		return hwEmulationFactory.get(c).setReal(ref_values) ? Status.OK
				: Status.Error;
	}

	@Override
	public Status setInteger(Instance c, Map<Integer, Integer> ref_values)
			throws TException {
		return hwEmulationFactory.get(c).setInteger(ref_values) ? Status.OK
				: Status.Error;
	}

	@Override
	public Status setBoolean(Instance c, Map<Integer, Boolean> ref_values)
			throws TException {
		return hwEmulationFactory.get(c).setBoolean(ref_values) ? Status.OK
				: Status.Error;
	}

	@Override
	public Status setString(Instance c, Map<Integer, String> ref_values)
			throws TException {
		logger.warn("setString(NOT supported) was called " + c + " "
				+ ref_values.toString());
		// not supported
		return Status.Discard;
	}

	@Override
	public Status setRealInputDerivatives(Instance c,
			Map<Integer, Integer> ref_orders, Map<Integer, Double> ref_values)
			throws TException {
		logger.warn("setRealInputDerivatives(NOT supported canInterpolateInputs=\"fmi2False\") was called "
				+ c);
		// NOT supported canInterpolateInputs="fmi2False"
		return Status.Error;
	}

	@Override
	public Status setRealOutputDerivatives(Instance c,
			Map<Integer, Integer> ref_orders, Map<Integer, Double> ref_values)
			throws TException {
		logger.warn("setRealOutputDerivatives(NOT supported MaxOutputDerivativeOrder=\"0\") was called "
				+ c);
		// This model cannot compute derivatives of outputs:
		// MaxOutputDerivativeOrder="0"
		return Status.Error;
	}

	@Override
	public Status cancelStep(Instance c) throws TException {
		logger.warn("cancelStep(NOT supported) was called " + c);
		// always fmi2CancelStep is invalid, because model is never in
		// modelStepInProgress state.
		return Status.Error;
	}

	@Override
	public Status doStep(Instance c, double currentCommunicationPoint,
			double communicationStepSize,
			boolean noSetFMUStatePriorToCurrentPoint) throws TException {
		SimulatedTime.INST.doStep(communicationStepSize);
		return Status.OK;
	}

	@Override
	public Status getStatus(Instance c, StatusKind s) throws TException {
		return Status.Discard;
	}

	@Override
	public int getIntegerStatus(Instance c, StatusKind s) throws TException {
		return 0;
	}

	@Override
	public double getRealStatus(Instance c, StatusKind s) throws TException {
		if (s == StatusKind.LastSuccessfulTime) {
			return SimulatedTime.INST.getCurrentTimeNano()/1000000000.0;
		}
		return 0;
	}
	
	@Override
	public boolean getBooleanStatus(Instance c, StatusKind s) throws TException {
		if (s == StatusKind.Terminated) {
			return hwEmulationFactory.get(c).isTerminated();
		}
		return false;
	}

	@Override
	public String getStringStatus(Instance c, StatusKind s) throws TException {
		return "";
	}

	public void addExperimentListener(
			ExperimentListener experimentListener) {
		experimentListenerList.add(experimentListener);
	}

	public HwEmulationFactory getHwEmulationFactory(){
		return hwEmulationFactory;
	}
}
