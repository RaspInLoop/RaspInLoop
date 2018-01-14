package org.raspinloop.pi4j.io.spi;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;

public class MCP3008PropertiesFactory implements IExecutableExtensionFactory {

	@Override
	public Object create() throws CoreException {		
		return new MCP3008Properties() ;
	}

}
