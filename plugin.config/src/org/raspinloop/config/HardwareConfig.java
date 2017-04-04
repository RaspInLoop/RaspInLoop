package org.raspinloop.config;

import java.util.Collection;

public interface HardwareConfig {

	/**
	 * 	
	 * @return the name of this instance of hardware simulator
	 */
	public String getName();
	
	/**
	 * 
	 * @param the name of this instance of hardware simulator
	 */
	HardwareConfig setName(String string);
	/**
	 * 
	 * @return the name of this kind of hardware simulator
	 */
	public String getType();
	
	/**
	 * 
	 * @return the fully qualified Class name of the implementation of this class 
	 */
	public String getImplementationClassName();
	
	/**
	 * 
	 * @return the name of the board or component which is simulated by this class
	 */
	public String getSimulatedProviderName();
	
	/**
	 * 
	 * @return the Pins used by this hardware board/extension
	 */
	Collection<Pin>  getUsedPins();
}
