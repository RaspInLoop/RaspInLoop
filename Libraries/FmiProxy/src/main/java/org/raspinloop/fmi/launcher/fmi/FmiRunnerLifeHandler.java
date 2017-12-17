package org.raspinloop.fmi.launcher.fmi;

import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TTransportException;
import org.raspinloop.fmi.Instance;
import org.raspinloop.fmi.launcher.BaseRunnerLifeHandler;
import org.raspinloop.fmi.launcher.ProxyRunnerJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FmiRunnerLifeHandler extends  BaseRunnerLifeHandler {


	private TServer server;
    private Logger logger = LoggerFactory.getLogger(FmiRunnerLifeHandler.class);

	public FmiRunnerLifeHandler(ProxyRunnerJob fmiProxyRunnerJob, TServer server) {
		super(fmiProxyRunnerJob);
		this.server = server;
	}
	
	@Override
	public  org.raspinloop.fmi.launcherRunnerIpc.Status freeInstance(Instance c) {
		logger.trace("FmiRunnerLifeHandler.terminate called");
		return org.raspinloop.fmi.launcherRunnerIpc.Status.OK;

	}

	@Override
	public org.raspinloop.fmi.Status reset(Instance c) throws TTransportException {
		logger.trace("FmiRunnerLifeHandler.reset called");
		return org.raspinloop.fmi.Status.Discard;
	}

	public void stopFMIServer(){
		if (server != null )
			server.stop();
	}

	public org.raspinloop.fmi.launcherRunnerIpc.Status stopVMRunner() {
		return super.stopVMRunner();				
	}
}