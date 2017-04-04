package org.raspinloop.fmi.plugin.preferences;

import org.eclipse.core.runtime.IProgressMonitor;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.plugin.preferences.HardwareSelectionDialog.HardwareItemsFilter;
import org.raspinloop.fmi.plugin.preferences.HardwareSelectionDialog.HwSearchRequestor;

public class SearchHardware {

	public void searchAllHardwareNames(HwSearchRequestor requestor, int waitUntilReadyToSearch, IProgressMonitor progressMonitor) {
	try {
		PluggedHardwareEnumerator enumerator = new PluggedHardwareEnumerator();
		for (HardwareConfig hwConfig : enumerator.buildListImplementing(HardwareConfig.class)) {
			requestor.acceptHardware(hwConfig);	
		}			
	} finally {
		if (progressMonitor != null) {
			progressMonitor.done();
		}
	}
		
	}

}
