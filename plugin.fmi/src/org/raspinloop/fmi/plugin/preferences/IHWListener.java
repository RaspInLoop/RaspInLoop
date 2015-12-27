package org.raspinloop.fmi.plugin.preferences;

import org.raspinloop.config.HardwareConfig;

public interface IHWListener {
	void addOrRemoveHW(HardwareConfig hw);
}
