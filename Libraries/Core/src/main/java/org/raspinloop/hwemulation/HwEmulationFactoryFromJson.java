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

import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonProperties;
import org.raspinloop.config.HardwareBuilder;


public class HwEmulationFactoryFromJson implements HwEmulationFactory {

	private GpioProviderHwEmulation hd;

    
	@Override
	public GpioProviderHwEmulation create(String jsonHwDescription) throws Exception {
		GsonProperties conf = new GsonProperties(HardwareClassFactory.INSTANCE());
		
		BoardHardware hardwareProperties = conf.read(jsonHwDescription);
		ClassLoaderBuilderFactory clbf = new ClassLoaderBuilderFactory();
		HardwareBuilder builder = clbf.createBuilder(hardwareProperties);
        hd = (GpioProviderHwEmulation)builder.build();
	
		return hd;
	}

	@Override
	public GpioProviderHwEmulation get() {
		return hd;
	}
}
