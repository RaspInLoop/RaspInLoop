package org.raspinloop.fmi;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class VMRunner implements Runnable {

	
	
	
	private Set<String> classPath;
	private String vMArguments;
	private String programArguments;
	private String className;
	private String JSonConfigName;
	
	
	public VMRunner(String jSonConfigName, String vMArguments, String[] classPath, String className, String programArguments) {
		super();
		JSonConfigName = jSonConfigName;
		this.vMArguments = vMArguments;
		this.classPath =new HashSet<>(Arrays.asList(classPath));
		this.className = className;
		this.programArguments = programArguments;
	}


	@Override
	public void run() {
	    String separator = System.getProperty("file.separator");
	    String path = System.getProperty("java.home")
	                + separator + "bin" + separator + "java";
	    ProcessBuilder processBuilder; 
	    
	    if (StringUtils.isBlank( programArguments) ){
	    processBuilder = 
	                new ProcessBuilder(path, "-cp", 
	                classPath.stream().collect(Collectors.joining(":")), 
	                className);
	    } else {
	    processBuilder = 
                new ProcessBuilder(path, "-cp", 
                classPath.stream().collect(Collectors.joining(":")), 
                className,
                programArguments);
	    }
	    Process process;
		try {
			process = processBuilder.start();
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			
		}
	    
	}

}
