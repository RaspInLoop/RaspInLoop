package org.raspinloop.fmi.launcher;

public interface IProxyMonitor {


	void worked(int i);

	boolean isCanceled();

	void aborted(Throwable e);

	Throwable getAbortedCause();
	
	void processStatusChanged(ProcessStatus status);
	
	void simulationToolsStatusChanged(SimulationToolStatus status);
	
}
