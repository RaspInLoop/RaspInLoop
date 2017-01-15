package org.raspinloop.fmi.internal.fmu;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.raspinloop.config.HardwareConfig;
import org.raspinloop.config.HardwareEnumerator;
import org.raspinloop.fmi.hwemulation.HwEmulation;
import org.reflections.Reflections;

public class HardwareClassFactory implements HardwareEnumerator {

	public static HardwareClassFactory INSTANCE(){
		if (INSTANCE == null)
			INSTANCE = new HardwareClassFactory();
		return INSTANCE;
	}
	
	static private HardwareClassFactory INSTANCE;
	private Reflections reflections;
	private Map<HardwareConfig, HwEmulation> cache = new HashMap<HardwareConfig, HwEmulation>();
	
	private HardwareClassFactory() {
		reflections = new Reflections("org.raspinloop");
	}

	@Override
	public Collection<HardwareConfig> buildListImplementing(Class<? extends HardwareConfig> class1) {
		Set<?> propertiesClasses = reflections.getSubTypesOf(class1);
		
		HashSet<HardwareConfig> propertiesObjects = new HashSet<HardwareConfig>();
		for (Object hardwareConfigClases : propertiesClasses) {
			if (hardwareConfigClases instanceof Class<?>){
				Object object;
				try {
					object = ((Class<?>)hardwareConfigClases).newInstance();				
				if (object instanceof HardwareConfig)
					propertiesObjects.add((HardwareConfig) object);
				} catch (InstantiationException | IllegalAccessException e) {					
					e.printStackTrace();
				}
			}
		}		
		return propertiesObjects;
	}

}
