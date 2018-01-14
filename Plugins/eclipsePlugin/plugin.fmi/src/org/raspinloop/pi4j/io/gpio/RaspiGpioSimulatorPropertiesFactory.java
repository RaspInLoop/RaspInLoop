package org.raspinloop.pi4j.io.gpio;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;

public class RaspiGpioSimulatorPropertiesFactory implements IExecutableExtensionFactory {

	@Override
	public Object create() throws CoreException {		
		return new RaspiGpioSimulatorProperties() ;
	}

}
