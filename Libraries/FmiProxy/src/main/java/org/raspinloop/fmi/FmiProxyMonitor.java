package org.raspinloop.fmi;

import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FmiProxyMonitor implements IProxyMonitor {

	private boolean canceled = false;
	Logger logger = LoggerFactory.getLogger(FmiProxyMonitor.class);
	private Throwable e;

	@Override
	public void subTask(String string) {
		logger.info("++++++++++++++++++++++++++++ Subtask: " + string + " +++++++++++++++++++++++++++++++++++");
	}

	@Override
	public void done() {
		logger.info("++++++++++++++++++++++++++++ Done ! +++++++++++++++++++++++++++++++++++");
	}

	@Override
	public void worked(int i) {
		logger.info("++++++++++++++++++++++++++++ working+++: " + i + "% +++++++++++++++++++++++++++++++++++");
	}

	@Override
	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void aborted(Throwable e) {
		this.e = e;
		logger.info("++++++++++++++++++++++++++++ Aborted: " + e.getMessage() + " +++++++++++++++++++++++++++++++++++");
		canceled = true;
	}

	@Override
	public Throwable getCancelCause() {
		return this.e;
	}

}
