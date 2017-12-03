package org.raspinloop.config;

public interface SPIComponent extends HardwareProperties {
	SPIParent getParent();
	void setParent(SPIParent parent);
}
