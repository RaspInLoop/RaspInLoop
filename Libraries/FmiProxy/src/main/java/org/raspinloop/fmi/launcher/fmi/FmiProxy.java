package org.raspinloop.fmi.launcher.fmi;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.raspinloop.fmi.CoSimulation.Iface;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.Proxy;
import org.raspinloop.fmi.launcher.RunnerLifeHandler;
import org.raspinloop.fmi.launcher.SimulationToolStatus;
import org.raspinloop.fmi.Instance;
import org.raspinloop.fmi.Status;
import org.raspinloop.fmi.StatusKind;
import org.raspinloop.fmi.Type;
import org.raspinloop.fmi.launcherRunnerIpc.RunnerService.Client;
import org.raspinloop.fmi.modeldescription.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FmiProxy implements Iface, Proxy {

	Logger logger = LoggerFactory.getLogger(FmiProxy.class);

	private boolean terminated;
	private RunnerLifeHandler lifeHandler;
	private Client client;

	private IProxyMonitor monitor;
	public FmiProxy(IProxyMonitor monitor) {
		this.monitor = monitor;
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
	public Instance instanciate(String instanceName, Type fmuType, String fmuGUID, String fmuResourceLocation, boolean visible, boolean loggingOn)
			throws TException {
		if (lifeHandler == null ){
			logger.error("LifeHandler is null");
			throw new TException("lifeHandlerNotSet");
		}

		try {
			client = lifeHandler.startVMRunner(monitor);
     		return lifeHandler.instanciate(instanceName, fmuType, fmuGUID, fmuResourceLocation, visible, loggingOn);
		} catch (Exception e) {
			logger.error("Cannot Start VMRunner : "+e.getMessage());
			lifeHandler.stopVMRunner();		
			return null;
		}
	}

	@Override
	public Status setupExperiment(Instance c, boolean toleranceDefined, double tolerance, double startTime, boolean stopTimeDefined, double stopTime)
			throws TException {
		if (client == null){
			// TODO log error
			throw new TException("DebugTarget net yet started");
		}
		logger.trace("-->setupExperiment");
		org.raspinloop.fmi.launcherRunnerIpc.Status status = client.setupExperiment(toleranceDefined, tolerance, startTime, stopTimeDefined, stopTime);
		logger.trace("-->setupExperiment" + status);
		return convertStatus(status);
	}



	@Override
	public Status enterInitializationMode(Instance c) throws TException {
		if (client == null){
			// TODO log error
			throw new TException("DebugTarget not yet started");
		}
		return Status.OK;
	}

	@Override
	public Status exitInitializationMode(Instance c) throws TException {
		if (client == null){
			// TODO log error
			throw new TException("DebugTarget not yet started");
		}
		monitor.simulationToolsStatusChanged(SimulationToolStatus.SIMULATING);
		return Status.OK;
	}

	@Override
	public Status terminate(Instance c) throws TException {
		if (client == null) {
			// TODO log error
			throw new TException("DebugTarget not yet started");
		}
		org.raspinloop.fmi.launcherRunnerIpc.Status status = org.raspinloop.fmi.launcherRunnerIpc.Status.OK;
		logger.trace("--> terminate");		
		lifeHandler.stopVMRunner();		
		terminated=true;
		logger.trace("<-- terminate " + status);
		return convertStatus(status);
	}

	@Override
	public Status reset(Instance c) throws TException {
		// TODO Auto-generated method stub
		return Status.Discard;
	}

	@Override
	public void freeInstance(Instance c) throws TException {
		logger.trace("--> free");		
		lifeHandler.freeInstance(c);
		logger.trace("<-- free");	
	}

	@Override
	public List<Double> getReal(Instance c, List<Integer> refs) throws TException {
		if (client == null){
			// TODO log error
			throw new TException("DebugTarget not yet started");
		}
		return client.getReal( refs);
	}

	@Override
	public List<Integer> getInteger(Instance c, List<Integer> refs) throws TException {
		if (client == null){
			// TODO log error
			throw new TException("DebugTarget not yet started");
		}
		return client.getInteger( refs);
	}

	@Override
	public List<Boolean> getBoolean(Instance c, List<Integer> refs) throws TException {
		if (client == null){
			// TODO log error
			throw new TException("DebugTarget not yet started");
		}
		return client.getBoolean(refs);
	}

	@Override
	public List<String> getString(Instance c, List<Integer> refs) throws TException {
		try {
//TODO logger.warn("getString(NOT supported) was called " + c + " "
//					+ refs.toString());
			return Collections.emptyList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	

	@Override
	public Status setReal(Instance c, Map<Integer, Double> ref_values) throws TException {
		if (client == null){
			// TODO log error
			throw new TException("DebugTarget not yet started");
		}
		return convertStatus( client.setReal( ref_values));
	}

	@Override
	public Status setInteger(Instance c, Map<Integer, Integer> ref_values) throws TException {
		if (client == null){
			// TODO log error
			throw new TException("DebugTarget not yet started");
		}
		return convertStatus( client.setInteger(ref_values));
	}

	@Override
	public Status setBoolean(Instance c, Map<Integer, Boolean> ref_values) throws TException {
		if (client == null){
			// TODO log error
			throw new TException("DebugTarget net yet started");
		}
		return convertStatus(client.setBoolean( ref_values));
	}

	@Override
	public Status setString(Instance c, Map<Integer, String> ref_values) throws TException {
//TODO		logger.warn("setString(NOT supported) was called " + c + " "
//				+ ref_values.toString());
		// not supported
		return Status.Discard;
	}

	@Override
	public Status setRealInputDerivatives(Instance c, Map<Integer, Integer> ref_orders, Map<Integer, Double> ref_values) throws TException {
		//TODO logger.warn("setRealInputDerivatives(NOT supported canInterpolateInputs=\"fmi2False\") was called " + c);
		// NOT supported canInterpolateInputs="fmi2False"
		return Status.Error;
	}

	@Override
	public Status setRealOutputDerivatives(Instance c, Map<Integer, Integer> ref_orders, Map<Integer, Double> ref_values) throws TException {
		//TODO logger.warn("setRealOutputDerivatives(NOT supported MaxOutputDerivativeOrder=\"0\") was called "+ c);
		// This model cannot compute derivatives of outputs:
		// MaxOutputDerivativeOrder="0"
		return Status.Error;
	}

	@Override
	public Status cancelStep(Instance c) throws TException {
		//TODO logger.warn("cancelStep(NOT supported) was called " + c);
		// always fmi2CancelStep is invalid, because model is never in
		// modelStepInProgress state.
		return Status.Error;
	}

	@Override
	public Status doStep(Instance c, double currentCommunicationPoint, double communicationStepSize, boolean noSetFMUStatePriorToCurrentPoint)
			throws TException {
		if (client == null){
			// TODO log error
			throw new TException("DebugTarget net yet started");
		}
		//logger.trace("--> doStep");
		org.raspinloop.fmi.launcherRunnerIpc.Status status = client.doStep( currentCommunicationPoint, communicationStepSize, noSetFMUStatePriorToCurrentPoint);
		//logger.trace("<-- doStep " + status);
		return convertStatus(status);
	}

	@Override
	public Status getStatus(Instance c, StatusKind s) throws TException {
		if (client == null){
			// TODO log error
			throw new TException("DebugTarget net yet started");
		}
		return convertStatus(client.getStatus( convertStatusKind(s)));
	}



	@Override
	public int getIntegerStatus(Instance c, StatusKind s) throws TException {
		return 0;
	}

	@Override
	public double getRealStatus(Instance c, StatusKind s) throws TException {
		return 0.0;
	}

	@Override
	public boolean getBooleanStatus(Instance c, StatusKind s) throws TException {
		if (s == StatusKind.Terminated) {
			return isTerminated();
		}
		return false;
	}

	@Override
	public String getStringStatus(Instance c, StatusKind s) throws TException {
		return "";
	}

	private org.raspinloop.fmi.launcherRunnerIpc.StatusKind convertStatusKind(StatusKind s) {
		switch(s) {
		case DoStepStatus:
			return org.raspinloop.fmi.launcherRunnerIpc.StatusKind.DoStepStatus;
		case LastSuccessfulTime:
			return org.raspinloop.fmi.launcherRunnerIpc.StatusKind.LastSuccessfulTime;
		case PendingStatus:
			return org.raspinloop.fmi.launcherRunnerIpc.StatusKind.PendingStatus;
		case Terminated:
			return org.raspinloop.fmi.launcherRunnerIpc.StatusKind.Terminated;
		default:
			throw new RuntimeException("Unknown statusKind: "+s);		
		}
	}
	
	private Status convertStatus(org.raspinloop.fmi.launcherRunnerIpc.Status status) {
		switch (status) {
		case OK:
			return Status.OK;
		case Discard:
			return Status.Discard;
		case Error:
			return Status.Error;
		case Fatal:
			return Status.Fatal;
		case Pending:
			return Status.Pending;
		case Warning:
			return Status.Warning;
		default:
			return Status.Error;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.raspinloop.fmi.plugin.launcher.fmi.proxy#isTerminated()
	 */
	@Override
	public boolean isTerminated() {
		return terminated;
	}

	/* (non-Javadoc)
	 * @see org.raspinloop.fmi.plugin.launcher.fmi.proxy#setFmiRunnerLifeHandler(org.raspinloop.fmi.plugin.launcher.RunnerLifeHandler)
	 */
	@Override
	public void setFmiRunnerLifeHandler(RunnerLifeHandler lifeHandler) {
		this.lifeHandler = lifeHandler;		
	}

	@Override
	public String getName() {		
		return "Remote simulation";
	}


}
