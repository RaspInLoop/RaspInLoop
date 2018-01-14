package org.raspinloop.fmi;

import org.raspinloop.fmi.launcher.IProxyMonitor;

public abstract class AbstractProxyMonitor implements IProxyMonitor {

	protected boolean canceled;
	protected Throwable e;

	public AbstractProxyMonitor() {
		super();
	}

	@Override
	public void aborted(Throwable e) {
		this.e = e;
			canceled = true;
	}

	@Override
	public Throwable getAbortedCause() {
		return this.e;
	}

}