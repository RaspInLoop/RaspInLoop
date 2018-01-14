package org.raspinloop.pi4j.io.i2c;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;

public class TSL256xPropertiesFactory implements IExecutableExtensionFactory {

	@Override
	public Object create() throws CoreException {		
		return new TSL256xProperties() ;
	}

}
