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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.raspinloop.fmi.SpiCompHwEmulation;

public enum SpiEmulation {
	// Singleton
	INST;

	private SpiDeviceEmulation spiDevices[] = new SpiDeviceEmulation[SpiChannel.values().length];
		
	public static SpiDevice getInstance(SpiChannel channel) throws IOException {
		return INST.get(channel);
	}
	
	public static SpiDevice getInstance(SpiChannel channel, Integer speed, SpiMode mode ) throws IOException {
		SpiDeviceEmulation instance = INST.get(channel);
		instance.setMode(mode);		
		instance.setSpeed(speed);
		return instance;
	}
	
	public static SpiDevice getInstance(SpiChannel channel, SpiMode mode) throws IOException {
		SpiDeviceEmulation instance = INST.get(channel);
		instance.setMode(mode);		
		return instance;
	}
	
	public static SpiDevice getInstance(SpiChannel channel, Integer speed) throws IOException {
		SpiDeviceEmulation instance = INST.get(channel);
		instance.setSpeed(speed);	
		return instance;
	}

	private SpiDeviceEmulation get(SpiChannel channel) {
		if( spiDevices[channel.getChannel()] == null)
			spiDevices[channel.getChannel()] = new SpiDeviceEmulation(this, channel);
		return spiDevices[channel.getChannel()];
	}

	
	Map<Short, SpiCompHwEmulation> devOnChannel = new HashMap<>();
	
	public void registerHwEmulation(short channel, SpiCompHwEmulation hw){
		devOnChannel.put(channel, hw);
	}
	
	public int wiringPiSPIDataRW(short channel, byte[] buffer) {
		try {
			if (devOnChannel.containsKey(channel))
				return devOnChannel.get(channel).spiDataRW(buffer);
		} catch (Exception e) {
		}
		return 0;
	}

	public int wiringPiSPIDataRW(short channel, short[] buffer) {
		try {
			if (devOnChannel.containsKey(channel))
				return devOnChannel.get(channel).spiDataRW(buffer);
		} catch (Exception e) {
		}
		return 0;
	}
	
	

}
