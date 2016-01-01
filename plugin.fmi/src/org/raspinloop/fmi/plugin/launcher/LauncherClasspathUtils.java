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
import org.raspinloop.fmi.plugin.preferences.PreferenceConstants;

public class LauncherClasspathUtils {

	static Bundle fmiBundle = Platform.getBundle("org.raspinloop.fmi.plugin.fmi");
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
				pluginsAndDependenciesIds.addAll( getDependencyPluginIds(pluginId));
			} catch (Exception e) {
				System.err.println("extention classPath skipped for "+ pluginId +" due to "+e.getMessage());
			}
		}
		
		
		for (String pluginExtentionIds : pluginsAndDependenciesIds) {
			try {
				extentionsClasspath.addAll(getClasspathPaths(pluginExtentionIds));
			}
			catch (Exception e){
				System.err.println("extention classPath skipped for "+ pluginExtentionIds +" due to "+e.getMessage());
			}
		}
		return extentionsClasspath;
		
	}

	private static Collection<? extends String> getLauncherCP(ILaunchConfiguration configuration) {
		return Arrays.asList(getFmiPath("target/dependency/aspectjrt-1.8.4.jar"), 
							 getFmiPath("target/dependency/commons-codec-1.6.jar"),
							 getFmiPath("target/dependency/commons-logging-1.1.1.jar"), 
							 getFmiPath("target/dependency/httpclient-4.2.5.jar"),
							 getFmiPath("target/dependency/httpcore-4.2.4.jar"), 
							 getFmiPath("target/dependency/jna-3.5.2.jar"), 
							 getFmiPath("target/dependency/libthrift-0.9.2.jar"),
							 getFmiPath("target/dependency/log4j-1.2.17.jar"), 
							 getFmiPath("target/dependency/pi4j-core-0.0.5.jar"),
							 getFmiPath("target/dependency/pi4j-device-0.0.5.jar"), 
							 getFmiPath("target/dependency/pi4j-example-0.0.5.jar"),
							 getFmiPath("target/dependency/pi4j-gpio-extension-0.0.5.jar"), 
							 getFmiPath("target/dependency/pi4j-service-0.0.5.jar"),
							 getFmiPath("target/dependency/pi4j-launcher-2.0.0-SNAPSHOT.jar"), 
							 getFmiPath("target/dependency/slf4j-api-1.5.8.jar"),
							 getFmiPath("target/dependency/slf4j-log4j12-1.7.9.jar"),
							 getFmiPath("target/dependency/gson-2.5.jar"),
							 getFmiPath("target/dependency/reflections-0.9.10.jar"),
							 getFmiPath("target/dependency/guava-15.0.jar"),
							 getFmiPath("target/dependency/javassist-3.19.0-GA.jar"),
							 getFmiPath("target/dependency/annotations-2.0.1.jar"));
	}
	
	public static String getFmiPath(String string) {
		return getPath(fmiBundle, string);
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
				System.err.println("could not find : " + string);
				return "";
			}

			return new File(FileLocator.resolve(entry).getFile()).getAbsolutePath();
		} catch (IOException e) {
			System.err.println("could not find: " + string + ": " + e.getMessage());
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
	                result.add(resolvedURLString.substring("file:".length()));
	              }
	              else
	              {
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
	          result.add(value);
	        }
	      }

	    return result;
	  } 
}
	