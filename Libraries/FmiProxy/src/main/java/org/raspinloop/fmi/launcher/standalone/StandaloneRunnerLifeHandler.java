package org.raspinloop.fmi.launcher.standalone;

import org.raspinloop.fmi.launcher.BaseRunnerLifeHandler;
import org.raspinloop.fmi.launcher.ProxyRunnerJob;
import org.raspinloop.fmi.launcher.RunnerLifeHandler;

public class StandaloneRunnerLifeHandler extends BaseRunnerLifeHandler implements RunnerLifeHandler {

	public StandaloneRunnerLifeHandler(ProxyRunnerJob proxyRunnerJob) {
		super(proxyRunnerJob);
	}
}
