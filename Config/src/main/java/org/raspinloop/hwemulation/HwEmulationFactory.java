package org.raspinloop.hwemulation;


/**
 * Used to create and store hardware Emulation.
 */
public interface HwEmulationFactory {

	/**
	 * 
	 * @param inst Instance contains hardware GUID (which comes from ModelDescritpion.xml (bundled in FMU)
	 */

	GpioProviderHwEmulation get();

	GpioProviderHwEmulation create(String xmlHwDescription)
			throws  Exception;


}
