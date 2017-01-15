package org.raspinloop.fmi.internal.hwemulation;

import org.raspinloop.fmi.Instance;
import org.raspinloop.fmi.hwemulation.GpioProviderHwEmulation;

/**
 * Used to create and store hardware Emulation.
 */
public interface HwEmulationFactory {

	/**
	 * 
	 * @param inst Instance contains hardware GUID (which comes from ModelDescritpion.xml (bundled in FMU)
	 */

	GpioProviderHwEmulation get(Instance c);

	void remove(Instance c);

	GpioProviderHwEmulation create(Instance inst, String xmlHwDescription)
			throws  Exception;


}
