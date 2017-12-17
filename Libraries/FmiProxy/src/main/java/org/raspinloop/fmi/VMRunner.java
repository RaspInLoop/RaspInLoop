package org.raspinloop.fmi;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VMRunner implements Runnable {

	Logger logger = LoggerFactory.getLogger(VMRunner.class);

	private Set<String> classPath;
	private String vMArguments;
	private String programArguments;
	private String className;
	private String jSonConfigName;

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
			processBuilder = new ProcessBuilder(path, getRunnerAgentArgument(), getWeaverAgentArgument(jSonConfigName), vMArguments, "-cp",
				classPath.stream().collect(Collectors.joining(":")), className, programArguments);
		}catch (Exception e) {
			logger.error("Cannot configure process: " + e.getMessage());
			throw new VMRunnerUncheckedException(e);
		}
		
		if (System.getProperty("mock") == null || !System.getProperty("mock").equalsIgnoreCase("true")) {
			Process process;
			try {
				
				logger.debug("==> starting JVM " + processBuilder.command().stream().collect(Collectors.joining(" ")));
				process = processBuilder.inheritIO().start();
			} catch (Exception e) {
				logger.error("Cannot start process: " + e.getMessage());
				throw new VMRunnerUncheckedException(e);
			}
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				process.destroy();
				logger.info("<== JVM Interrupted: ");
				return;
			}
			logger.info("<== JVM Stopped");
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

	private String getWeaverAgentArgument(String jSonConfigName) throws URISyntaxException, ClassNotFoundException, IOException {

		Path agentPath = getJarContainingClass(".", "org.aspectj.weaver.Constants");
		if (agentPath == null)
			throw new ClassNotFoundException("No jar found with class named org.aspectj.weaver.Constants");
		return "-javaagent:" + agentPath.toString();
	}

	private String getRunnerAgentArgument() throws URISyntaxException, ClassNotFoundException, IOException {

		Path agentPath = getJarContainingClass(".", "org.raspinloop.agent.PreMain");
		if (agentPath == null)
			throw new ClassNotFoundException("No jar found with class named org.raspinloop.agent.PreMain");

		return "-javaagent:" + agentPath.toString() + "=" + jSonConfigName;
	}

	private Path getJarContainingClass(String path, String className) throws IOException, ClassNotFoundException {
		File[] files = new File(path).listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory() || pathname.getName().endsWith(".jar");
			}
		});

		for (File file : files) {
			Path pathFound = null;
			if (file.isDirectory()) {
				pathFound = getJarContainingClass(file.getPath(), className);
				if (pathFound != null)
					return pathFound;
				else
					continue;
			}
			// else look for classes in jars from current dir.
			JarFile jarFile = new JarFile(file);
			if (containsClass(jarFile, className)) {
				return file.toPath();
			}
		}
		// not found
		return null;
	}

	private boolean containsClass(JarFile jarFile, String agentRunnerClassName) {

		Enumeration<JarEntry> e = jarFile.entries();
		while (e.hasMoreElements()) {
			JarEntry je = e.nextElement();
			if (je.isDirectory() || !je.getName().endsWith(".class")) {
				continue;
			}
			// -6 because of .class
			String className = je.getName().substring(0, je.getName().length() - 6);
			className = className.replace('/', '.');
			if (className.equals(agentRunnerClassName))
				return true;
		}
		return false;
	}
}
