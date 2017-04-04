package org.raspinloop.config;

import java.util.Collection;

public interface BoardHardware extends HardwareConfig, I2CParent, UARTParent, SPIParent, GPIOHardware{	

	public abstract Collection<Pin> getUsedByCompPins();

	public abstract void addComponent(BoardExtentionHardware ext) throws AlreadyUsedPin;

	public abstract void removeComponent(BoardExtentionHardware ext);

	public abstract Collection<BoardExtentionHardware> getComponents();

	String getType();
	
	public String getGuid();

	public abstract Collection<Pin> getSupportedPin();

	/* return BoardExtension, i2c, spi and uarts
	 * 
	 */
	Collection<HardwareConfig> getAllComponents();

}