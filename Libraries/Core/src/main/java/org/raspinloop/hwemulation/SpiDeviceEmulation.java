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
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;


public class SpiDeviceEmulation implements SpiDevice {

	private SpiMode spiMode = SpiDevice.DEFAULT_SPI_MODE;
	private Integer integer = SpiDevice.DEFAULT_SPI_SPEED;
	private SpiEmulation spiEmulation;
	private SpiChannel channel;

	public SpiDeviceEmulation(SpiEmulation spiEmulation, SpiChannel channel) {
		this.spiEmulation = spiEmulation;
		this.channel = channel;		
	}

	public void setMode(SpiMode spiMode) {
		this.spiMode = spiMode;
	}

	public void setSpeed(Integer integer) {
		this.integer = integer;
	}

    @Override
    public String write(String data, String charset) throws IOException {
        byte[] buffer = data.getBytes(charset);
        return new String(write(buffer), charset);
    }

    @Override
    public String write(String data, Charset charset) throws IOException {
        byte[] buffer = data.getBytes(charset);
        return new String(write(buffer), charset);
    }

    @Override
    public ByteBuffer write(ByteBuffer data) throws IOException {
        return ByteBuffer.wrap(write(data.array()));
    }

    @Override
    public byte[] write(InputStream input) throws IOException {

        // ensure bytes are available
        if(input.available() <= 0){
            throw new IOException("No available bytes in input stream to write to SPI channel: " + channel.getChannel());
        }
        else if(input.available() > MAX_SUPPORTED_BYTES){
            throw new IOException("Number of bytes in stream exceed the maximum bytes allowed to write SPI channel in a single call");
        }

        // create a temporary buffer to store read bytes from stream
        byte[] buffer = new byte[MAX_SUPPORTED_BYTES];

        // read maximum number of supported bytes
        int length = input.read(buffer, 0 , MAX_SUPPORTED_BYTES);

        // write bytes to SPI channel
        return write(buffer, 0, length);
    }

    @Override
    public int write(InputStream input, OutputStream output) throws IOException {
        // write stream data to SPI device
        byte[] buffer = write(input);

        //write resulting byte array to output stream
        output.write(buffer);

        // return data length
        return buffer.length;
    }

    @Override
    public byte[] write(byte... data) throws IOException {
        return write(data, 0, data.length);
    }

    @Override
    public short[] write(short... data) throws IOException {
        return write(data, 0, data.length);
    }

    @Override
    public byte[] write(byte[] data, int start, int length) throws IOException {

        // ensure the length does not exceed the data array
        length = Math.min(data.length - start, length);

        // validate max length allowed
        if (length > MAX_SUPPORTED_BYTES) {
            throw new IOException("Number of bytes in data to write exceed the maximum bytes allowed to write SPI channel in a single call");
        }

        // we make a copy of the data argument because we don't want to modify the original source data
        byte[] buffer = new byte[length];
        System.arraycopy(data, start, buffer, 0, length);

        synchronized (channel) {
                // write the bytes from the temporary buffer to the SPI channel
                if (spiEmulation.wiringPiSPIDataRW(channel.getChannel(), buffer) <= 0) {
                    throw new IOException("Failed to write data to SPI channel: " + channel.getChannel());
                }
            }
        // return the updated byte buffer as the SPI read results
        return buffer;
    }

    @Override
    public short[] write(short[] data, int start, int length) throws IOException {

        // ensure the length does not exceed the data array
        length = Math.min(data.length - start, length);

        // validate max length allowed
        if (length > MAX_SUPPORTED_BYTES) {
            throw new IOException("Number of bytes in data to write exceed the maximum bytes allowed to write SPI channel in a single call");
        }

        // we make a copy of the data argument because we don't want to modify the original source data
        short[] buffer = new short[length];
        System.arraycopy(data, start, buffer, 0, length);

        synchronized (channel) {
            // write the bytes from the temporary buffer to the SPI channel
            if (spiEmulation.wiringPiSPIDataRW(channel.getChannel(), buffer) <= 0) {
                throw new IOException("Failed to write data to SPI channel: " + channel.getChannel());
            }

            // return the updated byte buffer as the SPI read results
            return buffer;
        }
    }


}
