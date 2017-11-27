package org.raspinloop.config;

import java.util.Collection;

public interface I2CParent extends HardwareProperties {
	Collection<I2CComponent> getI2CComponent();  
	void addComponent(I2CComponent comp);
	void removeComponent(I2CComponent comp);
	Collection<Pin> getI2CPins();
}
