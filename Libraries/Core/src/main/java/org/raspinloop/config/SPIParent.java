package org.raspinloop.config;

import java.util.Collection;

public interface SPIParent extends HardwareProperties {
		Collection<SPIComponent> getSPIComponent();  
		void addComponent(SPIComponent comp);
		void removeComponent(SPIComponent comp);
		Collection<Pin> getSpiPins();
	}
