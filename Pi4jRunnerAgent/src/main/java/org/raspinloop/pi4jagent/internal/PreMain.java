package org.raspinloop.pi4jagent.internal;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.raspinloop.agent.CSHandler;
import org.raspinloop.agent.internal.aspect.Adapters;
import org.raspinloop.agent.internal.timeemulation.SimulatedTimeExecutorServiceFactory;
import org.raspinloop.agent.launcherRunnerIpc.IpcConnector;

import com.pi4j.io.gpio.GpioFactory;

public class PreMain {

	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("starting pi4j-Runner Agent ");
		System.out.println("org.raspinloop.agent.PreMain " + agentArgs);

		// we have to set simulated raspberry provider
		GpioFactory.setDefaultProvider(Adapters.forPi4j( CSHandler.getInstance().getHwEmulationFactory().get()));
		// executors need to be cleared before each run
		final SimulatedTimeExecutorServiceFactory simulatedTimeExecutorFactory = new SimulatedTimeExecutorServiceFactory();
		simulatedTimeExecutorFactory.reinit();
		GpioFactory.setExecutorServiceFactory(simulatedTimeExecutorFactory);
		// calling the main class
		
	}
}
