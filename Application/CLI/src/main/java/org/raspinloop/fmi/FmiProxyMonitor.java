package org.raspinloop.fmi;

import org.raspinloop.fmi.launcher.ProcessStatus;
import org.raspinloop.fmi.launcher.SimulationToolStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FmiProxyMonitor extends AbstractProxyMonitor  {

	Logger logger = LoggerFactory.getLogger(FmiProxyMonitor.class);

	@Override
	public void worked(int i) {
		logger.info("++++++++++++++++++++++++++++ working+++: " + i + "% +++++++++++++++++++++++++++++++++++");
	}

	@Override
	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void processStatusChanged(ProcessStatus status) {
		logger.info("++++++++++++++++++++++++++++ PROCESS: "+ status +" +++++++++++++++++++++++++++++++++++");
	}

	@Override
	public void simulationToolsStatusChanged(SimulationToolStatus status) {
		logger.info("++++++++++++++++++++++++++++ SIMULATION TOOL: "+ status +" +++++++++++++++++++++++++++++++++++");
	}

	@Override
	public void aborted(Throwable e) {
		super.aborted(e);
		logger.info("++++++++++++++++++++++++++++ Aborted: " + e.getMessage() + " +++++++++++++++++++++++++++++++++++");

	}

}
