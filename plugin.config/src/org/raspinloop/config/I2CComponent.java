package org.raspinloop.config;

public interface I2CComponent extends HardwareConfig {
	I2CParent getParent();
	void setParent(I2CParent parent);
	
}
