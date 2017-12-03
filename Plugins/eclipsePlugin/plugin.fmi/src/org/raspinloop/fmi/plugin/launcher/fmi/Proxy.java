package org.raspinloop.fmi.plugin.launcher.fmi;

import org.raspinloop.fmi.plugin.launcher.RunnerLifeHandler;

public interface Proxy {

	public abstract String getName(); 
	public abstract boolean isTerminated();

	public abstract void setFmiRunnerLifeHandler(RunnerLifeHandler lifeHandler);

}