package org.raspinloop.fmi.launcher;

public interface IProxyMonitor {

	void subTask(String string);

	void done();

	void worked(int i);

	boolean isCanceled();

	void aborted(Throwable e);

	Throwable getCancelCause();

}
