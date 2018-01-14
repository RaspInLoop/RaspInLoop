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
import java.util.concurrent.TimeUnit;

import org.raspinloop.fmi.I2CCompHwEmulation;


public class I2CBusEmulation implements I2CBus {

    /** Used to identify the i2c bus within Pi4J **/
    protected int busNumber;

    protected long lockAquireTimeout;

    protected TimeUnit lockAquireTimeoutUnit;

	
	Map<Integer, I2CDeviceEmulation> devices = new HashMap<>();
	Map<Integer, I2CCompHwEmulation> EmulatedDevicesOnBus = new HashMap<>();

	public I2CBusEmulation(int busNumber) {
	        this.busNumber = busNumber;
	}

	public void setLockAquireTimeout(long lockAquireTimeout, TimeUnit lockAquireTimeoutUnit) {
	
        if (lockAquireTimeout < 0) {
            this.lockAquireTimeout = I2CFactory.DEFAULT_LOCKAQUIRE_TIMEOUT;
        } else {
            this.lockAquireTimeout = lockAquireTimeout;
        }

        if (lockAquireTimeoutUnit == null) {
            this.lockAquireTimeoutUnit = I2CFactory.DEFAULT_LOCKAQUIRE_TIMEOUT_UNITS;
        } else {
            this.lockAquireTimeoutUnit = lockAquireTimeoutUnit;
        }
    }

    /**
     * Returns i2c device implementation ({@link I2CDeviceImpl}).
     *
     * @param address address of i2c device
     *
     * @return implementation of i2c device with given address
     *
     * @throws IOException never in this implementation
     */
    @Override
    public I2CDevice getDevice(int address) throws IOException {
    	if (! devices.containsKey(address)){
    		devices.put(address, new I2CDeviceEmulation(this, address));
    	}
    	return devices.get(address);
    }

	public void register(I2CCompHwEmulation hw) {
		EmulatedDevicesOnBus.put(hw.getAddress(), hw);		
	}
	
    /**
     * Opens the bus.
     *
     * @throws IOException thrown in case there are problems opening the i2c bus.
     */
    protected void open() throws IOException {
    }

    /**
     * Closes this i2c bus
     *
     * @throws IOException never in this implementation
     */
    @Override
    public void close() throws IOException {
    }


    @Override
    public int getBusNumber() {
        return busNumber;
    }

    @Override
    public String toString() {
        return "Emulation of I2CBus '" + busNumber ;
    }

	public boolean write(int devAddress, byte[] bs) {
		I2CCompHwEmulation dev = EmulatedDevicesOnBus.get(devAddress);
		return dev.write(bs);	
	}

	public boolean write(int devAddress, int address, byte[] bs) {
		I2CCompHwEmulation dev = EmulatedDevicesOnBus.get(devAddress);
		return dev.write(address, bs);
	}

	public byte[] read(int devAddress, int size) {
		I2CCompHwEmulation dev = EmulatedDevicesOnBus.get(devAddress);
		return dev.read(size);
	}

	public byte[] read(int devAddress, int address, int size) {
		I2CCompHwEmulation dev = EmulatedDevicesOnBus.get(devAddress);
		return dev.read(address, size);
	}
}
