package org.raspinloop.config;

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
	void setName(String string);
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
	
}
