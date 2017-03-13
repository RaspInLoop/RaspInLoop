package org.raspinloop.fmi.plugin.launcher;

import java.io.IOException;

import org.apache.thrift.server.TServer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.jdt.internal.launching.LaunchingPlugin;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.raspinloop.fmi.plugin.Activator;

public class WaitForFMIContext implements IProcess {

	public class WaitForFmiContextJob extends Job {

		private TServer server;

		public WaitForFmiContextJob(TServer server) {
			super(getLabel());
			this.server = server;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {

			while (!WaitForFMIContext.this.contextConnected) {
				if (monitor.isCanceled()) {
					stopServer();
					return Status.CANCEL_STATUS;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
			return Status.OK_STATUS;
		}

		public void stopServer() {
			server.stop();
		}

	}

	private ILaunch launch;
	private boolean terminated = false;
	private WaitForFmiContextJob waitForFmiContext;
	private boolean contextConnected;

	public WaitForFMIContext(ILaunch launch) {
		this.launch = launch;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean canTerminate() {
		return !terminated;
	}

	@Override
	public boolean isTerminated() {
		return terminated;
	}

	@Override
	public void terminate() throws DebugException {
		if (!terminated){
			terminated = true;	
			if (waitForFmiContext != null){
				waitForFmiContext.cancel();
				waitForFmiContext.stopServer();
				waitForFmiContext = null;
			}
			fireTerminateEvent();
		}
	}

	@Override
	public String getLabel() {
		return "Waiting for Simulation Start up...";
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	@Override
	public IStreamsProxy getStreamsProxy() {
		return null;
	}

	@Override
	public void setAttribute(String key, String value) {
	}

	@Override
	public String getAttribute(String key) {
		return null;
	}

	@Override
	public int getExitValue() throws DebugException {
		return 0;
	}

	public void waitFmi(TServer server, IProgressMonitor monitor) throws CoreException {
		if (isTerminated()) {
			throw new CoreException(getStatus("Cannot wait for an already terminated process", null,
					IJavaLaunchConfigurationConstants.ERR_REMOTE_VM_CONNECTION_FAILED));
		}
		waitForFmiContext = new WaitForFmiContextJob(server);
		waitForFmiContext.setPriority(Job.SHORT);
		waitForFmiContext.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void running(IJobChangeEvent event) {
			}

			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult().equals(Status.CANCEL_STATUS)) {
					try {
						terminate();
					} catch (DebugException e) {
					}
				}
			}
		});
		waitForFmiContext.schedule();

	}

	public void connected() {
		contextConnected = true;
	}

	protected static IStatus getStatus(String message, Throwable exception, int code) {
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, code, message, exception);
	}
	
	/**
	 * Fires a terminate event.
	 */
	protected void fireTerminateEvent() {
		DebugPlugin manager= DebugPlugin.getDefault();
		if (manager != null) {
			manager.fireDebugEventSet(new DebugEvent[]{new DebugEvent(this, DebugEvent.TERMINATE)});
		}
	}
	
	/**
	 * Fires a custom model specific event when this connector is ready to accept incoming
	 * connections from a remote VM.
	 */
	protected void fireReadyToAcceptEvent(){
		DebugPlugin manager= DebugPlugin.getDefault();
		if (manager != null) {
			manager.fireDebugEventSet(new DebugEvent[]{new DebugEvent(this, DebugEvent.MODEL_SPECIFIC, IJavaLaunchConfigurationConstants.DETAIL_CONFIG_READY_TO_ACCEPT_REMOTE_VM_CONNECTION)});
		}
	}
}
