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

import org.raspinloop.config.Pin;
import org.raspinloop.config.PinMode;
import org.raspinloop.config.PinPullResistance;
import org.raspinloop.config.PinState;


public interface GpioProvider {
	public void addListener(Pin arg0, PinListener arg1);

	public void export(Pin arg0, PinMode arg1);

	public void export(org.raspinloop.config.Pin arg0, PinMode arg1, PinState arg2);

	public org.raspinloop.config.PinMode getMode(Pin arg0);

	public String getName();

	public org.raspinloop.config.PinPullResistance getPullResistance(Pin arg0);

	public int getPwm(Pin arg0);

	public PinState getState(Pin arg0);

	public double getValue(Pin arg0);

	public boolean hasPin(Pin arg0);

	public boolean isExported(Pin arg0);

	public boolean isShutdown();

	public void removeAllListeners();

	public void removeListener(Pin arg0, PinListener arg1);

	public void setMode(Pin arg0, PinMode arg1);

	public void setPullResistance(Pin arg0, PinPullResistance arg1);

	public void setPwm(Pin arg0, int arg1);

	public void setPwmRange(Pin arg0, int arg1);

	public void setState(Pin arg0, PinState arg1);

	public void setValue(Pin arg0, double arg1);

	public void shutdown();

	public void unexport(Pin arg0);
}
