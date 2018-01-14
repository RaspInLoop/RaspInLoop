/*******************************************************************************
 * Copyright 2018 RaspInLoop
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.raspinloop.hwemulation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.raspinloop.config.HardwareEnumerator;
import org.raspinloop.config.HardwareProperties;
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
