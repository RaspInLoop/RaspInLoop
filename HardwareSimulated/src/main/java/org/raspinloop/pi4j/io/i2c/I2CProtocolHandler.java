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