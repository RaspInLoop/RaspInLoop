package org.raspinloop.hwemulation;

import org.raspinloop.config.HardwareBuilder;
import org.raspinloop.config.HardwareBuilderFactory;
import org.raspinloop.config.HardwareProperties;

public class ClassLoaderBuilderFactory implements HardwareBuilderFactory {

	@Override
	public HardwareBuilder createBuilder(HardwareProperties hwConfig) {		
		ClassLoaderBuilder builder = new ClassLoaderBuilder(hwConfig.getImplementationClassName());
		builder.setBuilderFactory(this);
		builder.setProperties(hwConfig);		
		return  builder;
	}

}
