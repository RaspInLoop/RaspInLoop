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

import org.eclipse.core.runtime.IProgressMonitor;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.fmi.plugin.preferences.HardwareSelectionDialog.HardwareItemsFilter;
import org.raspinloop.fmi.plugin.preferences.HardwareSelectionDialog.HwSearchRequestor;

public class SearchHardware {

	public void searchAllHardwareNames(HwSearchRequestor requestor, int waitUntilReadyToSearch, IProgressMonitor progressMonitor) {
	try {
		PluggedHardwareEnumerator enumerator = new PluggedHardwareEnumerator();
		for (HardwareProperties hwConfig : enumerator.buildListImplementing(HardwareProperties.class)) {
			requestor.acceptHardware(hwConfig);	
		}			
	} finally {
		if (progressMonitor != null) {
			progressMonitor.done();
		}
	}
		
	}

}
