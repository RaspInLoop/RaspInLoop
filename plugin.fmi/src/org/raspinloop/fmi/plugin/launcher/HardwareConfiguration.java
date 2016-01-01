package org.raspinloop.fmi.plugin.launcher;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.raspinloop.config.GsonConfig;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.plugin.preferences.PluggedHardwareEnumerator;

public class HardwareConfiguration {	

	static public Collection<HardwareConfig> buildList() {
		IEclipsePreferences preferences = ConfigurationScope.INSTANCE.getNode("org.raspinloop.fmi.preferences.configuredhardware");
		ArrayList<HardwareConfig> hardwares = buildListFromPref(preferences);
		
		IEclipsePreferences defaultPref = DefaultScope.INSTANCE.getNode("org.raspinloop.fmi.preferences.configuredhardware");
		hardwares.addAll(buildListFromPref(defaultPref));
		
		return hardwares;
	}

	private static ArrayList<HardwareConfig> buildListFromPref(IEclipsePreferences preferences) {
		ArrayList<HardwareConfig> hardwares = new ArrayList<HardwareConfig>();
		String listStr = preferences.get("HwList", "");
		String[] hwNames = listStr.split(":");
		for (String hwName : hwNames) {
			String bytes = preferences.get(hwName, "");
			if (bytes.trim().length() != 0) {
				try {
				GsonConfig conf = new GsonConfig(PluggedHardwareEnumerator.INSTANCE());
				HardwareConfig hw = conf.read(bytes);
				if (hw != null)
					hardwares.add(hw);
				} catch (Exception e)
				{
				}
			}
		}
		return hardwares;
	}
}