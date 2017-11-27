package org.raspinloop.fmi.plugin.launcher;

import org.raspinloop.config.HardwareBuilder;
import org.raspinloop.config.HardwareBuilderFactory;
import org.raspinloop.config.HardwareProperties;

public class PluggedClassBuilderFactory implements HardwareBuilderFactory {

	@Override
	public HardwareBuilder createBuilder(HardwareProperties hwProps) {
		HardwareBuilder builder = new PluggedClassBuilder(hwProps.getClass().getName());
		builder.setBuilderFactory(this);
		builder.setProperties(hwProps);
		return builder;
	}

}
