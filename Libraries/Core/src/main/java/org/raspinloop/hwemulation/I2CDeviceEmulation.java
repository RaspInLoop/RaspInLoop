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
import java.util.Arrays;

public class I2CDeviceEmulation implements I2CDevice {

	private I2CBusEmulation bus;
	private int deviceAddress;

	public I2CDeviceEmulation(I2CBusEmulation bus, int address) {
        this.bus = bus;
        this.deviceAddress = address;
    }


	@Override
	public int getAddress() {
		return deviceAddress;
	}

    /**
     * This method writes one byte to i2c device.
     *
     * @param data byte to be written
     *
     * @throws IOException thrown in case byte cannot be written to the i2c device or i2c bus
     */
    @Override
    public void write(final byte data) throws IOException {
    	 if (! bus.write(getAddress(), new byte[]{data}))
    		 throw new IOException("Error writing to " + makeDescription() + ". Not implemented!");
    }

    /**
     * This method writes several bytes to the i2c device from given buffer at given offset.
     *
     * @param data buffer of data to be written to the i2c device in one go
     * @param offset offset in buffer
     * @param size number of bytes to be written
     *
     * @throws IOException thrown in case byte cannot be written to the i2c device or i2c bus
     */
    @Override
    public void write(final byte[] data, final int offset, final int size) throws IOException {
    	 if (! bus.write(getAddress(),Arrays.copyOfRange(data, offset, offset+size)))    	     
    	       throw new IOException("Error writing to " + makeDescription() + ". Not implemented!");
    }

    /**
     * This method writes all bytes included in the given buffer directly to the i2c device.
     *
     * @param buffer buffer of data to be written to the i2c device in one go
     *
     * @throws IOException thrown in case byte cannot be written to the i2c device or i2c bus
     */
    @Override
    public void write(byte[] buffer) throws IOException {
        bus.write(getAddress(),buffer);
    }

    /**
     * This method writes one byte to i2c device.
     *
     * @param address local address in the i2c device
     * @param data byte to be written
     *
     * @throws IOException thrown in case byte cannot be written to the i2c device or i2c bus
     */
    @Override
    public void write(final int address, final byte data) throws IOException {
    	if (! bus.write(getAddress(),address, new byte[]{data}))
            throw new IOException("Error writing to " + makeDescription(address) + ". Not implemented!");
    }

    /**
     * This method writes several bytes to the i2c device from given buffer at given offset.
     *
     * @param address local address in the i2c device
     * @param data buffer of data to be written to the i2c device in one go
     * @param offset offset in buffer
     * @param size number of bytes to be written
     *
     * @throws IOException thrown in case byte cannot be written to the i2c device or i2c bus
     */
    @Override
    public void write(final int address, final byte[] data, final int offset, final int size) throws IOException {
    	if (! bus.write(getAddress(),address, Arrays.copyOfRange(data, offset, offset+size)))
            throw new IOException("Error writing to " + makeDescription(address) + ". Not implemented!");
    }

    /**
     * This method writes all bytes included in the given buffer directoy to the register address on the i2c device
     *
     * @param address local address in the i2c device
     * @param buffer buffer of data to be written to the i2c device in one go
     *
     * @throws IOException thrown in case byte cannot be written to the i2c device or i2c bus
     */
    public void write(int address, byte[] buffer) throws IOException {
        bus.write(getAddress(),address, buffer);
    }

    /**
     * This method reads one byte from the i2c device. Result is between 0 and 255 if read operation was successful, else a negative number for an error.
     *
     * @return byte value read: positive number (or zero) to 255 if read was successful. Negative number if reading failed.
     *
     * @throws IOException thrown in case byte cannot be read from the i2c device or i2c bus
     */
    @Override
    public int read() throws IOException {
    	byte[] byteread = bus.read(getAddress(),1);
    	return byteread[0];
    }

    /**
     * <p>
     * This method reads bytes from the i2c device to given buffer at asked offset.
     * </p>
     *
     * <p>
     * Note: Current implementation calls {@link #read(int)}. That means for each read byte i2c bus will send (next) address to i2c device.
     * </p>
     *
     * @param data buffer of data to be read from the i2c device in one go
     * @param offset offset in buffer
     * @param size number of bytes to be read
     *
     * @return number of bytes read
     *
     * @throws IOException thrown in case byte cannot be read from the i2c device or i2c bus
     */
    @Override
    public int read(final byte[] data, final int offset, final int size) throws IOException {
    	byte[] byteread = bus.read(getAddress(),size);
    	for (int i = 0; i < byteread.length; i++) {
    		data[offset+i] = byteread[i];
		}
    	return byteread.length;
      }

    /**
     * This method reads one byte from the i2c device. Result is between 0 and 255 if read operation was successful, else a negative number for an error.
     *
     * @param address local address in the i2c device
     * @return byte value read: positive number (or zero) to 255 if read was successful. Negative number if reading failed.
     *
     * @throws IOException thrown in case byte cannot be read from the i2c device or i2c bus
     */
    @Override
    public int read(final int address) throws IOException {
    	byte[] byteread = bus.read(getAddress(),address, 1);
    	return byteread[0];
    }

    /**
     * <p>
     * This method reads bytes from the i2c device to given buffer at asked offset.
     * </p>
     *
     * <p>
     * Note: Current implementation calls {@link #read(int)}. That means for each read byte i2c bus will send (next) address to i2c device.
     * </p>
     *
     * @param address local address in the i2c device
     * @param data buffer of data to be read from the i2c device in one go
     * @param offset offset in buffer
     * @param size number of bytes to be read
     *
     * @return number of bytes read
     *
     * @throws IOException thrown in case byte cannot be read from the i2c device or i2c bus
     */
    @Override
    public int read(final int address, final byte[] data, final int offset, final int size) throws IOException {
    	byte[] byteread = bus.read(getAddress(),address, size);
    	for (int i = 0; i < byteread.length; i++) {
    		data[offset+i] = byteread[i];
		}
    	return byteread.length;
      }

    /**
     * This method writes and reads bytes to/from the i2c device in a single method call
     *
     * @param writeData buffer of data to be written to the i2c device in one go
     * @param writeOffset offset in write buffer
     * @param writeSize number of bytes to be written from buffer
     * @param readData buffer of data to be read from the i2c device in one go
     * @param readOffset offset in read buffer
     * @param readSize number of bytes to be read
     *
     * @return number of bytes read
     *
     * @throws IOException thrown in case byte cannot be read from the i2c device or i2c bus
     */
    @Override
    public int read(final byte[] writeData, final int writeOffset, final int writeSize, final byte[] readData, final int readOffset, final int readSize) throws IOException {
    		write(getAddress(),writeData, writeOffset, writeSize);
    		read(getAddress(),readData, readOffset, readSize);
            throw new IOException("Error reading from " + makeDescription() + ". Not implemented!");
    }
	
	protected String makeDescription() {
	        return "I2CDevice on " + bus + " at address 0x" + Integer.toHexString(deviceAddress);
	    }
	 
    protected String makeDescription(int address) {
        return "I2CDevice on " + bus + " at address 0x" + Integer.toHexString(deviceAddress) + " to address 0x" + Integer.toHexString(address);
    }
}
