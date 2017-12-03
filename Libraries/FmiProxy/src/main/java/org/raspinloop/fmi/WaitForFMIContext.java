package org.raspinloop.fmi;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.thrift.server.TServer;
import org.raspinloop.fmi.launcher.IProxyMonitor;

public class WaitForFMIContext {

	public class WaitForFmiContextJob implements Runnable {

		private TServer server;
		private String name;
		private IProxyMonitor monitor;
		LinkedList<IJobDoneListener> doneListerners= new LinkedList<>();

		public WaitForFmiContextJob(TServer server, IProxyMonitor monitor) {
			this.monitor = monitor;
			this.setName(getLabel());
			this.server = server;
		}

		public void stopServer() {
			server.stop();
		}

		@Override
		public void run() {
			while (!WaitForFMIContext.this.contextConnected) {
				if (monitor.isCanceled()) {
					stopServer();
					return;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					doneListerners.forEach(l -> l.done(new IJobDoneEvent() {
						
						@Override
						public JobStatus getResult() {
							return JobStatus.CANCEL_STATUS;
						}
					}));
				}
				
			}

		}

		public void addDoneListener(IJobDoneListener iJobDoneListener) {
			doneListerners.add(iJobDoneListener);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	private boolean terminated = false;
	private WaitForFmiContextJob waitForFmiContext;
	private boolean contextConnected;
	private ExecutorService executor;
	private Future<?> future;

	public WaitForFMIContext(ExecutorService executor) {
		this.executor = executor;

	}

	public boolean canTerminate() {
		return !terminated;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public void terminate() {
		if (!terminated) {
			terminated = true;
			if (waitForFmiContext != null) {
				if (future != null)
					future.cancel(true);
				waitForFmiContext.stopServer();
				waitForFmiContext = null;
			}
		}
	}

	public String getLabel() {
		return "Waiting for Simulation Start up...";
	}

	public void waitFmi(TServer server, IProxyMonitor monitor) {
		if (isTerminated()) {
			throw new RuntimeException("Cannot wait for an already terminated process");
		}
		waitForFmiContext = new WaitForFmiContextJob(server, monitor);
		waitForFmiContext.addDoneListener(new IJobDoneListener() {
			
			@Override
			public void done(IJobDoneEvent event) {
				if (event.getResult().equals(JobStatus.CANCEL_STATUS)) {
					try {
						terminate();
					} catch (Exception e) {
					}
				}
			}
		});
		future = executor.submit(waitForFmiContext);
	}

	public void connected() {
		contextConnected = true;
	}
}
