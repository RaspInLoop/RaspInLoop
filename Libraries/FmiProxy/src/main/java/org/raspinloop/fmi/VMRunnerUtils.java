package org.raspinloop.fmi;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;

public class VMRunnerUtils {

	public static String getWeaverAgentArgument(String basePath, boolean escaped) throws URISyntaxException, ClassNotFoundException, IOException {

		Path agentPath = getJarContainingClass(basePath, "org.aspectj.weaver.Constants");
		if (agentPath == null)
			throw new ClassNotFoundException("No jar found with class named org.aspectj.weaver.Constants");
		if (escaped)
			return toEscapedCliString(agentPath, "");
		else
			return toCliString(agentPath, "");
	}

	public static String getRunnerAgentArgument(String basePath, String jSonConfigName, boolean escaped) throws URISyntaxException, ClassNotFoundException, IOException {

		Path agentPath = getJarContainingClass(basePath, "org.raspinloop.agent.PreMain");
		if (agentPath == null)
			throw new ClassNotFoundException("No jar found with class named org.raspinloop.agent.PreMain");

		if (escaped)
			return toEscapedCliString(agentPath, jSonConfigName);
		else
			return toCliString(agentPath, jSonConfigName);
	}
	
	public static String getWeaverAgentArgument(Collection<File> jars, boolean escaped) throws URISyntaxException, ClassNotFoundException, IOException {

		Path agentPath = filterJarContainingClass(jars, "org.aspectj.weaver.Constants");
		if (agentPath == null)
			throw new ClassNotFoundException("No jar found with class named org.aspectj.weaver.Constants");
		if (escaped)
			return toEscapedCliString(agentPath, "");
		else
			return toCliString(agentPath, "");
	}

	public static String getRunnerAgentArgument(Collection<File> jars, String jSonConfigName, boolean escaped) throws URISyntaxException, ClassNotFoundException, IOException {

		Path agentPath = filterJarContainingClass(jars, "org.raspinloop.agent.PreMain");
		if (agentPath == null)
			throw new ClassNotFoundException("No jar found with class named org.raspinloop.agent.PreMain");

		if (escaped)
			return toEscapedCliString(agentPath, jSonConfigName);
		else
			return toCliString(agentPath, jSonConfigName);
	}
   
	private static String toCliString(Path agentPath, String param) {
		if (param != null && !param.isEmpty()) {
			return"-javaagent:" + agentPath.toString() + "=" + param;
		} else {
			return"-javaagent:" + agentPath.toString();
		}
	}
	
	private static String toEscapedCliString(Path agentPath, String param) {
		if (param != null && !param.isEmpty()) {
			return"-javaagent:\"" + agentPath.toString() + "\"=\"" + param+"\"";
		} else {
			return"-javaagent:\"" + agentPath.toString() + "\"";
		}
	}
	

	private static  Path getJarContainingClass(String path, String className) throws IOException, ClassNotFoundException {
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
	
	private static  Path filterJarContainingClass(Collection<File> jarsPaths, String className) throws IOException, ClassNotFoundException {
	
		for (File file : jarsPaths) {
			
			JarFile jarFile = new JarFile(file);
			if (containsClass(jarFile, className)) {
				return file.toPath();
			}
		}
		// not found
		return null;
	}

	private static boolean containsClass(JarFile jarFile, String agentRunnerClassName) {

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
