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
package org.raspinloop.pi4j.io.i2c;

import java.util.BitSet;

public class I2CProtocolHandler {

	private RegisterBank rb;

	/**
	 * @param tsl256x
	 */
	I2CProtocolHandler(RegisterBank rb) {
		this.rb = rb;		
	}


	@SuppressWarnings("unused")
	private boolean CLEAR;

	@SuppressWarnings("unused")
	private boolean WORD;

	@SuppressWarnings("unused")
	private boolean BLOCK;

	private byte ADDRESS;
	
	private boolean setCommand(byte command) {
		BitSet commandBs = BitSet.valueOf(new byte[] { command });
		if (commandBs.get(7)) {
			this.CLEAR = commandBs.get(6);
			this.WORD = commandBs.get(5);
			this.BLOCK = commandBs.get(4);
			this.ADDRESS = commandBs.get(0, 4).toByteArray()[0];
			return true;
		} else
			return false;
	}

	public boolean write(int address, byte[] buffer) {
		if (setCommand((byte) address)) {
			for (int i = 0; i < buffer.length; i++) {
				rb.write((byte)(this.ADDRESS+i), buffer[i]);
			}			
			return true;
		}
		return false;
	}

	public byte[] read(int size) {
		// Cannot read from Control register
		return new byte[0];
	}

	public byte[] read(int address, int size) {
		if (setCommand((byte) address)) {
			byte[] result = new byte[size];
			for (int i = 0; i < size; i++) {
				result[i] = rb.read((byte)(this.ADDRESS+i));
			}
			return result;
		}
		return new byte[0];
	}

	public boolean write(byte[] buffer) {
		// this will set only the Command register, so nothing to do...
		if (buffer.length == 1) {
			return setCommand(buffer[0]);
		}
		return false;
	}
}
