package org.raspinloop.fmi.launcher.standalone;

import org.raspinloop.fmi.launcher.Proxy;
import org.raspinloop.fmi.launcher.ProxyRunnerJob;
import org.raspinloop.fmi.launcher.Runner;
import org.raspinloop.fmi.launcher.RunnerLifeHandler;

public class StandaloneProxyRunnerJob extends ProxyRunnerJob {

	public StandaloneProxyRunnerJob(Proxy proxy, Runner runner) {
		super(proxy, runner);
		RunnerLifeHandler lifeHandler = new StandaloneRunnerLifeHandler(this);
		proxy.setFmiRunnerLifeHandler(lifeHandler );
		
	}

}
