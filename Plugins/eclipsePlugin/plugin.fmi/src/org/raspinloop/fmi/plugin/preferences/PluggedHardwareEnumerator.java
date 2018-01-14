/*******************************************************************************
 * Copyright (C) 2018 RaspInLoop
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.raspinloop.fmi.plugin.preferences;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.config.HardwareEnumerator;
import org.raspinloop.fmi.plugin.Activator;

public class PluggedHardwareEnumerator implements HardwareEnumerator {

	public static PluggedHardwareEnumerator INSTANCE() {
		if (INSTANCE == null)
			INSTANCE = new PluggedHardwareEnumerator();
		return INSTANCE;
	}

	static private PluggedHardwareEnumerator INSTANCE;

	protected PluggedHardwareEnumerator() {
	};

	@Override
	public ArrayList<HardwareProperties> buildListImplementing(Class<? extends HardwareProperties> class1) {
		ArrayList<HardwareProperties> list = new ArrayList<HardwareProperties>();
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(PreferenceConstants.HARDWARE_EXTENSION_POINT_ID);
		IConfigurationElement[] infos = extensionPoint.getConfigurationElements();
		for (int i = 0; i < infos.length; i++) {
			IConfigurationElement element = infos[i];
			try {
				Object execExt = element.createExecutableExtension("configClass"); //$NON-NLS-1$
				if (execExt instanceof HardwareProperties) {
					HardwareProperties config = (HardwareProperties) execExt;
					if (class1.isInstance(config))
						list.add(config);
				} else {
					Activator.getDefault().logError("Cannot instanciate " + element.getAttribute("configClass"));
				}
			} catch (CoreException e) {
				Activator.getDefault().logError("Cannot instanciate " + element.getAttribute("configClass"), e);
			}
		}
		return list;
	}

}
