package org.raspinloop.pi4j.io.components;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;

public class SimulatedStepperMotorPropertiesFactory implements IExecutableExtensionFactory {

	@Override
	public Object create() throws CoreException {		
		return new SimulatedStepperMotorProperties() ;
	}

}
