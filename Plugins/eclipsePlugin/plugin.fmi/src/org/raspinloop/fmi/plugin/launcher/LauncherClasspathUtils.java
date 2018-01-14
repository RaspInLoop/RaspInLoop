package org.raspinloop.fmi.plugin.launcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.raspinloop.fmi.plugin.Activator;

public class LauncherClasspathUtils {

	static Bundle pluginBundle = Platform.getBundle("org.raspinloop.fmi.plugin.fmi");
		

	public static String getPluginPath(String string) {
		return getPath(pluginBundle, string);
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
	

}
	