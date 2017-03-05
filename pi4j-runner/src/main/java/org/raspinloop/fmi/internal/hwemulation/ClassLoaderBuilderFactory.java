package org.raspinloop.fmi.internal.hwemulation;

import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.hwemulation.HardwareBuilderFactory;
import org.raspinloop.fmi.hwemulation.HardwareBuilder;

public class ClassLoaderBuilderFactory implements HardwareBuilderFactory {

	@Override
	public HardwareBuilder createBuilder(HardwareConfig hwConfig) {		
		ClassLoaderBuilder builder = new ClassLoaderBuilder(hwConfig.getImplementationClassName());
		builder.setBuilderFactory(this);
		builder.setProperties(hwConfig);		
		return  builder;
	}

}
