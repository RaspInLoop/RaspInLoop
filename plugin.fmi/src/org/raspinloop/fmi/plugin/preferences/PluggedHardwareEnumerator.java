package org.raspinloop.fmi.plugin.preferences;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.config.HardwareEnumerator;
import org.raspinloop.fmi.plugin.Activator;

public class PluggedHardwareEnumerator implements HardwareEnumerator{

	public static PluggedHardwareEnumerator INSTANCE(){
		if (INSTANCE == null)
			INSTANCE = new PluggedHardwareEnumerator();
		return INSTANCE;
	}
	
	static private PluggedHardwareEnumerator INSTANCE;

	protected PluggedHardwareEnumerator() {};
	@Override
	public ArrayList<HardwareProperties> buildListImplementing(Class<? extends HardwareProperties	> class1) {
		ArrayList<HardwareProperties> list = new ArrayList<HardwareProperties>();
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(PreferenceConstants.HARDWARE_EXTENSION_POINT_ID);
		IConfigurationElement[] infos= extensionPoint.getConfigurationElements();
		for (int i = 0; i < infos.length; i++) {
			IConfigurationElement element = infos[i];
			try {
					HardwareProperties config = (HardwareProperties) element.createExecutableExtension("configClass"); //$NON-NLS-1$
					if (class1.isInstance(config))
						list.add(config);
				} catch (CoreException e) {
					Activator.getDefault().logError("Cannot instanciate "+element.getAttribute("configClass"), e);				}
		}
		return list;
	}

}
