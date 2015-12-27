package org.raspinloop.config;

import java.util.Collection;

public interface UARTParent extends HardwareConfig {
		Collection<UARTComponent> getUARTComponent();  
		void addComponent(UARTComponent comp);
		void removeComponent(UARTComponent comp);
	}
