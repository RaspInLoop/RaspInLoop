package org.raspinloop.fmi.plugin.launcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.osgi.util.ManifestElement;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.raspinloop.fmi.plugin.Activator;
import org.raspinloop.fmi.plugin.Trace;
import org.raspinloop.fmi.plugin.preferences.PreferenceConstants;

public class LauncherClasspathUtils {

	static Bundle runnerBundle = Platform.getBundle("org.raspinloop.fmi.plugin.pi4j-runner");
	static Bundle configBundle = Platform.getBundle("org.raspinloop.fmi.plugin.config");

	
	/**
	 * Used to get all classPath required by the launcher
	 * @param configuration
	 * @return
	 */
	
	public static Collection<? extends String> get(ILaunchConfiguration configuration) {
		
		Set<String> allCP = new HashSet<String>();
		
		allCP.addAll(getLauncherCP(configuration));
		allCP.addAll(getHardwareSimulatedExtentionsCP(configuration));
		return allCP;
	}

	private static Collection<? extends String> getHardwareSimulatedExtentionsCP(ILaunchConfiguration configuration) {
		HashSet<String> extentionsClasspath = new HashSet<String>();
		
		HashSet<String> pluginsAndDependenciesIds = new HashSet<String>();
		//add plug-ins and their dependencies
		for (String pluginId : getExtenstionPluginIds()) {
			try {
				Trace.launcherRunner("found Extension: "+pluginId);
				pluginsAndDependenciesIds.add(pluginId);
				pluginsAndDependenciesIds.addAll( getDependencyPluginIds(pluginId));
			} catch (Exception e) {
				Activator.getDefault().logError("extention classPath skipped for "+ pluginId ,e);
			}
		}
		
		
		for (String pluginExtentionIds : pluginsAndDependenciesIds) {
			try {
				extentionsClasspath.addAll(getClasspathPaths(pluginExtentionIds));
			}
			catch (Exception e){
				Activator.getDefault().logError("extention classPath skipped for "+ pluginExtentionIds ,e);
			}
		}
		return extentionsClasspath;
		
	}

	private static Collection<? extends String> getLauncherCP(ILaunchConfiguration configuration) {
		return Arrays.asList(getLauncherPath("target/classes"), //debug 
							 getLauncherPath("target/dependency/aspectjrt-1.8.4.jar"), 
							 getLauncherPath("target/dependency/commons-codec-1.9.jar"),
							 getLauncherPath("target/dependency/commons-logging-1.2.jar"), 
							 getLauncherPath("target/dependency/httpclient-4.4.1.jar"),
							 getLauncherPath("target/dependency/httpcore-4.4.1.jar"), 
							 getLauncherPath("target/dependency/jna-4.2.1.jar"), 
							 getLauncherPath("target/dependency/libthrift-0.10.0.jar"),
							 getLauncherPath("target/dependency/log4j-1.2.17.jar"), 
							 getLauncherPath("target/dependency/pi4j-core-1.0.jar"),
							 getLauncherPath("target/dependency/pi4j-device-1.0.jar"), 
							 getLauncherPath("target/dependency/pi4j-example-1.0.jar"),
							 getLauncherPath("target/dependency/pi4j-gpio-extension-1.0.jar"), 
							 getLauncherPath("target/dependency/pi4j-service-1.0.jar"),
							 getLauncherPath("target/dependency/slf4j-api-1.7.12.jar"),
							 getLauncherPath("target/dependency/slf4j-log4j12-1.7.24.jar"),
							 getLauncherPath("target/dependency/gson-2.5.jar"),
							 getLauncherPath("target/dependency/reflections-0.9.10.jar"),
							 getLauncherPath("target/dependency/guava-15.0.jar"),
							 getLauncherPath("target/dependency/javassist-3.19.0-GA.jar"),
							 getLauncherPath("target/dependency/annotations-2.0.1.jar"));
	}
	
	public static String getLauncherPath(String string) {
		return getPath(runnerBundle, string);
	}
	
	private static Set<String> getExtenstionPluginIds(){
		HashSet<String> pluginIds = new HashSet<String>();
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(PreferenceConstants.HARDWARE_EXTENSION_POINT_ID);
		IConfigurationElement[] infos= extensionPoint.getConfigurationElements();
		for (int i = 0; i < infos.length; i++) {
			IConfigurationElement element = infos[i];
			pluginIds.add(element.getDeclaringExtension().getContributor().getName());			
		}
		return pluginIds;
	}
	
	private static String getPath(Bundle bundle, String string) {
		try {
			URL entry = bundle.getEntry(string);
			if (entry == null) {
				Activator.getDefault().logError("could not find : " + string);
				return "";
			}

			return new File(FileLocator.resolve(entry).getFile()).getAbsolutePath();
		} catch (IOException e) {
			Activator.getDefault().logError("could not find: " + string, e);
			return "";
		}
	}
	
	 private static List<String> getClasspathPaths(String pluginID) throws Exception
	  {
	    List<String> result = new ArrayList<String>();

	      Bundle bundle = Platform.getBundle(pluginID);
	      String requires = (String)bundle.getHeaders().get(Constants.BUNDLE_CLASSPATH);
	      if (requires == null)
	      {
	        requires = ".";
	      }
	      ManifestElement[] elements = ManifestElement.parseHeader(Constants.BUNDLE_CLASSPATH, requires);
	      if (elements != null){
	    	for (ManifestElement element : elements) {
				
	          String value = element.getValue();
	          if (".".equals(value))
	          {
	            value = "/";
	          }
	
	            URL url = bundle.getEntry(value);
	            if (url != null)
	            {
	              URL resolvedURL = FileLocator.resolve(url);
	              String resolvedURLString = resolvedURL.toString();
	              if (resolvedURLString.endsWith("!/"))
	              {
	                resolvedURLString = resolvedURL.getFile();
	                resolvedURLString = resolvedURLString.substring(0,resolvedURLString.length() - "!/".length());
	              }
	              if (resolvedURLString.startsWith("file:"))
	              {
	            	  // in debug in IDE, we have to point to target/class/
	            	  //but when used in production, we don not have to do that...
	            	  // How can we detect that ?
	            	 if ("/".equals(value)){
	            		 result.add(resolvedURLString.substring("file:".length())+"target/classes");
	            	 }
	                result.add(resolvedURLString.substring("file:".length()));
	              }
	              else
	              {
	            	  if ("/".equals(value)){
	            		  result.add(FileLocator.toFileURL(url).getFile()+"target/classes");
		            	 }
	                result.add(FileLocator.toFileURL(url).getFile());
	              }
	            }
	          break;
	        }
	      }

	    return result;
	  } 
	 
	 private static List<String> getDependencyPluginIds(String pluginID) throws Exception
	  {
	    List<String> result = new ArrayList<String>();

	      Bundle bundle = Platform.getBundle(pluginID);
	      String requires = (String)bundle.getHeaders().get(Constants.REQUIRE_BUNDLE);
	      ManifestElement[] elements = ManifestElement.parseHeader(Constants.REQUIRE_BUNDLE, requires);
	      if (elements != null){
	    	for (ManifestElement element : elements) {
				
	          String value = element.getValue();
	          Trace.launcherRunner("Extension: "+pluginID+ " requirement found: "+value);
	          result.add(value);
	        }
	      }

	    return result;
	  } 
}
	