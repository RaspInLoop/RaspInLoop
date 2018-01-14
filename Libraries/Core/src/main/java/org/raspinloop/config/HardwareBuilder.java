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
package org.raspinloop.config;

import org.raspinloop.fmi.HwEmulation;


/**
 * Builders are required to build Hardware classes based on their properties.
 * They contain properties required by the created Hardware class
 * @author Motte
 *
 */
public interface HardwareBuilder {
	HardwareBuilder setProperties(Object properties);
	
	/**
	 * Register component in simulated board. baseref is provided to offset all internal ref. 
	 * 
	 * @param baseref: the offset to apply to all variable references
	 * @return the number of var used in this component.
	 */	
	HardwareBuilder setBaseReference(int base);
	
	
	HardwareBuilder setBuilderFactory(HardwareBuilderFactory factory);
	Object getProperties();
	HardwareBuilderFactory getBuilderFactory();
	int getBaseReference();
	HwEmulation build() throws Exception;
}
