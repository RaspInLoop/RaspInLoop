package org.raspinloop.fmi.testtools;

import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.HardwareBuilder;
import org.raspinloop.config.HardwareBuilderFactory;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.hwemulation.ClassLoaderBuilderFactory;

public enum Builder {
	INSTANCE;
	
	public static HardwareBuilder getBuilderFor(String component) {
		return INSTANCE.getBuilder(component);	
	}
	
	public static HardwareBuilder getBuilderFor(HardwareProperties props) {
		return INSTANCE.getBuilder(props);	
	}

	public static void setConfig(HardwareProperties baseConfig){
		INSTANCE.baseConfig = baseConfig;
	}
	
	private HardwareProperties baseConfig;		
		
	private HardwareBuilderFactory pcbf = new ClassLoaderBuilderFactory(); //PluggedClassBuilderFactory	
	
	private HardwareBuilder getBuilder(String component) {
		HardwareProperties componentProps = getSubProperties(baseConfig, component) ;
		if (componentProps != null)
			return  pcbf.createBuilder(componentProps );
		else
			throw new RuntimeException("Cannot find '" + component + "' in config of " + baseConfig.getComponentName());		
	}
	
	private HardwareBuilder getBuilder(HardwareProperties props) {
		return  pcbf.createBuilder(props );
	}
	
	private HardwareProperties getSubProperties(HardwareProperties properties, String component) {
		if (properties.getComponentName().equals(component))
			return properties;
		if (properties instanceof BoardHardware){
			for (HardwareProperties extension : ((BoardHardware)properties).getAllComponents()) {
				HardwareProperties subConfig = getSubProperties(extension, component);
				if (subConfig != null)
					return subConfig;
			}
		}
		return null;
	}

}
