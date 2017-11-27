package org.raspinloop.fmi;

import org.raspinloop.fmi.launcher.IProxyMonitor;

public class FmiProxyMonitor implements IProxyMonitor {

	private boolean canceled = false;

	@Override
	public void subTask(String string) {
		System.out.println("Subtask: " + string);
	}

	@Override
	public void done() {
		System.out.println("Done !");
	}

	@Override
	public void worked(int i) {
		System.out.println("working+++: " + i + "%");
	}

	@Override
	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void aborted(InterruptedException e) {
		System.out.println("Aborted: " + e.getMessage());
		canceled = true;
	}

}
