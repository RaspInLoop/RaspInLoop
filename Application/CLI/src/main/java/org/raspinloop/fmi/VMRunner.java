package org.raspinloop.fmi;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.raspinloop.fmi.launcher.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class will start the java application to debug
 * @author Motte
 *
 */
public class VMRunner implements Runner {

	Logger logger = LoggerFactory.getLogger(VMRunner.class);

	private Set<String> classPath;
	private String vMArguments;
	private String programArguments;
	private String className;
	private String jSonConfigName;

	private Process process;

	public VMRunner(String jSonConfigName, String vMArguments, String[] classPath, String className, String programArguments) {
		super();
		this.jSonConfigName = jSonConfigName;
		this.vMArguments = StringUtils.isBlank(vMArguments) ? "" : vMArguments;
		;
		this.classPath = new HashSet<>(Arrays.asList(classPath));
		this.className = className;
		this.programArguments = StringUtils.isBlank(programArguments) ? "" : programArguments;
	}

	@Override
	public void run() {
		String separator = System.getProperty("file.separator");
		String path = System.getProperty("java.home") + separator + "bin" + separator + "java";

		ProcessBuilder processBuilder;
		try {
			processBuilder = new ProcessBuilder(path, VMRunnerUtils.getRunnerAgentArgument(".", jSonConfigName, false), VMRunnerUtils.getWeaverAgentArgument(".",false), vMArguments, "-cp",
				classPath.stream().collect(Collectors.joining(":")), className, programArguments);
		}catch (Exception e) {
			logger.error("Cannot configure process: " + e.getMessage());
			throw new VMRunnerUncheckedException(e);
		}
		
		if (System.getProperty("mock") == null || !System.getProperty("mock").equalsIgnoreCase("true")) {
			try {
				
				logger.debug("==> starting JVM " + processBuilder.command().stream().collect(Collectors.joining(" ")));
				process = processBuilder.inheritIO().start();
			} catch (Exception e) {
				logger.error("Cannot start process: " + e.getMessage());
				throw new VMRunnerUncheckedException(e);
			}
			CompletableFuture.supplyAsync(() -> {
				try {
					return process.waitFor();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}).thenAccept( i -> logger.info("<== JVM Stopped code("+i+")") );

		} else {
			logger.info("PLEASE RUN " + processBuilder.command().stream().collect(Collectors.joining(" ")));
			while (true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					logger.info("PLEASE STOP ");
					return;
				}
			}
		}
	}

	@Override
	public void terminate() {
		process.destroy();
		logger.info("<== JVM Interrupted: ");
	}


}
