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

import java.util.Collection;

public interface BoardHardware extends HardwareProperties, I2CParent, UARTParent, SPIParent, GPIOHardware{	

	public abstract Collection<Pin> getUsedByCompPins();

	public abstract void addComponent(BoardExtentionHardware ext) throws AlreadyUsedPin;

	public abstract void removeComponent(BoardExtentionHardware ext);

	public abstract Collection<BoardExtentionHardware> getGPIOComponents();

	String getType();
	
	public String getGuid();

	public abstract Collection<Pin> getSupportedPin();

	/* return BoardExtension, i2c, spi and uarts
	 * 
	 */
	Collection<HardwareProperties> getAllComponents();

}
