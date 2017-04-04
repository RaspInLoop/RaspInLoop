package org.raspinloop.config;

public interface SPIComponent extends HardwareConfig {
	SPIParent getParent();
	void setParent(SPIParent parent);
}
