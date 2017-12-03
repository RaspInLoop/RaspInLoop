package org.raspinloop.config;

public interface UARTComponent extends HardwareProperties {
	UARTParent getParent();
	void setParent(UARTParent parent);	
}
