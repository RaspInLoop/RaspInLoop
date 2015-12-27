package org.raspinloop.fmi.plugin.preferences;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.plugin.Activator;

public class HardwareUtils {

	public static ArrayList<HardwareConfig> buildHardwareListImplementing(Class<? extends HardwareConfig	> class1) {
		ArrayList<HardwareConfig> list = new ArrayList<HardwareConfig>();
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(PreferenceConstants.HARDWARE_EXTENSION_POINT_ID);
		IConfigurationElement[] infos= extensionPoint.getConfigurationElements();
		for (int i = 0; i < infos.length; i++) {
			IConfigurationElement element = infos[i];
			try {
					HardwareConfig config = (HardwareConfig) element.createExecutableExtension("configClass"); //$NON-NLS-1$
					if (class1.isInstance(config))
						list.add(config);
				} catch (CoreException e) {
					Activator.getDefault().logError("Cannot instanciate "+element.getAttribute("configClass"), e);				}
		}
		return list;
	}
}
