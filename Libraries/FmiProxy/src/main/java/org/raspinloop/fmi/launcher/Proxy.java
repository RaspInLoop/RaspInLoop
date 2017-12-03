package org.raspinloop.fmi.launcher;

public interface Proxy {

	public abstract String getName(); 
	public abstract boolean isTerminated();

	public abstract void setFmiRunnerLifeHandler(RunnerLifeHandler lifeHandler);

}