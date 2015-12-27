package org.raspinloop.config;

public interface UARTComponent extends HardwareConfig {
	UARTParent getParent();

	void setParent(UARTParent parent);
	
}
