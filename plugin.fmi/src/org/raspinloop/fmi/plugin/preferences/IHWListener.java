package org.raspinloop.fmi.plugin.preferences;

import org.raspinloop.config.HardwareProperties;

public interface IHWListener {
	void addOrRemoveHW(HardwareProperties hw);
}
