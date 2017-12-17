package org.raspinloop.fmi.launcher;

import java.util.concurrent.CompletableFuture;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.raspinloop.fmi.Instance;
import org.raspinloop.fmi.ModelState;
import org.raspinloop.fmi.launcher.ProxyRunnerJob.LauncherServerJob;
import org.raspinloop.fmi.launcherRunnerIpc.RunnerService.Client;
import org.raspinloop.fmi.launcherRunnerIpc.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseRunnerLifeHandler implements RunnerLifeHandler {
	Logger logger = LoggerFactory.getLogger(BaseRunnerLifeHandler.class);
	private static final int RUNNER_SERVER_PORT = 9091;
	private org.raspinloop.fmi.launcherRunnerIpc.RunnerService.Client client;
	private LauncherServerJob launcherServer;
	private Instance inst;
	private ProxyRunnerJob fmiProxyRunnerJob;

	private Throwable errorDuringStart;

	public BaseRunnerLifeHandler(ProxyRunnerJob fmiProxyRunnerJob) {
		this.fmiProxyRunnerJob = fmiProxyRunnerJob;
	}

	@Override
	public Instance instanciate(String instanceName, org.raspinloop.fmi.Type fmuType, String fmuGUID, String fmuResourceLocation, boolean visible,
			boolean loggingOn) throws TException {
		// FMI launcher (agent of process to debug )
		logger.trace("FmiRunnerLifeHandler.instanciate called");
		inst = new Instance(instanceName, 0, fmuGUID, ModelState.modelInstantiated);
		return inst;
	}

	@Override
	public org.raspinloop.fmi.launcherRunnerIpc.Status freeInstance(Instance c) {
		logger.trace("FmiRunnerLifeHandler.freeInstance called");
		return org.raspinloop.fmi.launcherRunnerIpc.Status.OK;

	}

	@Override
	public org.raspinloop.fmi.Status reset(Instance c) throws TTransportException {
		logger.trace("FmiRunnerLifeHandler.reset called");
		return org.raspinloop.fmi.Status.Discard;
	}

	@Override
	public Client startVMRunner(IProxyMonitor monitor) throws TTransportException, InterruptedException, RunnerLifeException {
		logger.trace("FmiRunnerLifeHandler.startVMRunner called");

		launcherServer = fmiProxyRunnerJob.new LauncherServerJob(RUNNER_SERVER_PORT, monitor);
		CompletableFuture.runAsync(launcherServer).exceptionally(e -> {
			this.setStartInError(e);
			return null;
		});

		// wait for for ready message
		while (fmiProxyRunnerJob.getRunnerPort() == 0 && !monitor.isCanceled() && !isStartedInError()) {
			Thread.sleep(100);
		}

		if (isStartedInError()) {
			throw new RunnerLifeException(errorDuringStart);
		}

		if (monitor.isCanceled()) {
			if (monitor.getCancelCause() == null)
				throw new InterruptedException("VMRunner start canceled");
			else
				throw new RunnerLifeException(monitor.getCancelCause());
		}

		logger.trace("runner started and listened on port " + fmiProxyRunnerJob.getRunnerPort());
		TSocket transport = new TSocket("localhost", fmiProxyRunnerJob.getRunnerPort(), 0); // NO
																							// timeout
																							// because
																							// debug
																							// is
																							// allowed!
		transport.open();
		TProtocol protocol = new TBinaryProtocol(transport);
		client = new Client(protocol);
		logger.trace(" launcher connected on port " + fmiProxyRunnerJob.getRunnerPort());
		return client;
	}

	private void setStartInError(Throwable e) {
		logger.error("Cannot start Application", e);
		errorDuringStart = e;
	}

	private boolean isStartedInError() {
		return errorDuringStart != null;
	}

	@Override
	public org.raspinloop.fmi.launcherRunnerIpc.Status stopVMRunner() {
		logger.trace("FmiRunnerLifeHandler.stopVMRunner called" + fmiProxyRunnerJob.getRunnerPort());
		try {
			Status result;
			if (client != null) {
				result = client.terminate();
				fmiProxyRunnerJob.cancel();
				return result;
			} else
				return org.raspinloop.fmi.launcherRunnerIpc.Status.Discard;
		} catch (TException e) {
			return org.raspinloop.fmi.launcherRunnerIpc.Status.Error;
		}

	}
}