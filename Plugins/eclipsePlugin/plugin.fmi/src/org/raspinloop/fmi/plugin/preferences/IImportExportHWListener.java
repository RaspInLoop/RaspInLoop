package org.raspinloop.fmi.plugin.preferences;

import org.raspinloop.config.HardwareProperties;

public interface IImportExportHWListener {
	HardwareProperties importHardware(String serializedHw);
	void exportHardware(HardwareProperties hw);
}
