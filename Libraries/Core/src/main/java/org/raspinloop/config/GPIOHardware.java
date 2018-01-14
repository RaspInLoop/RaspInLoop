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

public interface GPIOHardware {

	public abstract void useInputPin(Pin pin) throws AlreadyUsedPin;

	public abstract void useOutputPin(Pin pin) throws AlreadyUsedPin;

	public abstract void unUsePin(Pin pin);

	public abstract Collection<Pin> getOutputPins();

	public abstract Collection<Pin> getInputPins();

	public abstract Collection<Pin> getUnUsedPins();

}
