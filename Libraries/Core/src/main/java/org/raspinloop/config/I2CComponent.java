package org.raspinloop.config;

public interface I2CComponent extends HardwareProperties {
	I2CParent getParent();
	void setParent(I2CParent parent);
}
