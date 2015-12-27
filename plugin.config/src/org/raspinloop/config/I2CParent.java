package org.raspinloop.config;

import java.util.Collection;

public interface I2CParent extends HardwareConfig {
	Collection<I2CComponent> getI2cComponent();  
	void addComponent(I2CComponent comp);
	void removeComponent(I2CComponent comp);
}
