package org.raspinloop.fmi.plugin;

import org.eclipse.core.runtime.Platform;

public class Trace {
	
	public static void  launcherRunner(String msg){
		 String debugOption = Platform.getDebugOption(Activator.PLUGIN_ID+".plugin.fmi/debug/launcherRunnerIPC");
		   if ("true".equalsIgnoreCase(debugOption))
		      System.out.println(msg);
	}
	
	public static void  timeSequencer(String msg){
		 String debugOption = Platform.getDebugOption(Activator.PLUGIN_ID+".plugin.fmi/debug/timeSequencer");
		   if ("true".equalsIgnoreCase(debugOption))
		      System.out.println(msg);
	}

	public static void fmiProxy(String msg) {
		 String debugOption = Platform.getDebugOption(Activator.PLUGIN_ID+".plugin.fmi/debug/FmiProxy");
		   if ("true".equalsIgnoreCase(debugOption))
		      System.out.println(msg);		
	}
}
