package org.raspinloop.pi4j.io.i2c;

public interface IRegister {

	byte getAddress();
	String getName();
	byte read();
	void write(byte value);
}
