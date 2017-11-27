package org.raspinloop.hwemulation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.raspinloop.config.HardwareProperties;
import org.raspinloop.config.HardwareEnumerator;
import org.raspinloop.fmi.HwEmulation;
import org.reflections.Reflections;

public class HardwareClassFactory implements HardwareEnumerator {

	public static HardwareClassFactory INSTANCE(){
		if (INSTANCE == null)
			INSTANCE = new HardwareClassFactory();
		return INSTANCE;
	}
	
	static private HardwareClassFactory INSTANCE;
	private Reflections reflections;
	private Map<HardwareProperties, HwEmulation> cache = new HashMap<HardwareProperties, HwEmulation>();
	
	private HardwareClassFactory() {
		reflections = new Reflections("org.raspinloop");
	}

	@Override
	public Collection<HardwareProperties> buildListImplementing(Class<? extends HardwareProperties> class1) {
		Set<?> propertiesClasses = reflections.getSubTypesOf(class1);
		
		HashSet<HardwareProperties> propertiesObjects = new HashSet<HardwareProperties>();
		for (Object hardwareConfigClases : propertiesClasses) {
			if (hardwareConfigClases instanceof Class<?>){
				Object object;
				try {
					object = ((Class<?>)hardwareConfigClases).newInstance();				
				if (object instanceof HardwareProperties)
					propertiesObjects.add((HardwareProperties) object);
				} catch (InstantiationException | IllegalAccessException e) {					
					e.printStackTrace();
				}
			}
		}		
		return propertiesObjects;
	}

}
