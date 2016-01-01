package org.raspinloop.fmi.plugin.launcher;

import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.hwemulation.HardwareBuilder;
import org.raspinloop.fmi.hwemulation.HardwareBuilderFactory;

public class PluggedClassBuilderFactory implements HardwareBuilderFactory {

	@Override
	public HardwareBuilder createBuilder(HardwareConfig hwConfig) {
		HardwareBuilder builder = new PluggedClassBuilder(hwConfig.getClass().getName());
		builder.setBuilderFactory(this);
		builder.setProperties(hwConfig);
		return builder;
	}

}
