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
