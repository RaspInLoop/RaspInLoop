package org.raspinloop.fmi;



public interface I2CCompHwEmulation extends HwEmulation {
	
	boolean write(byte[] buffer);
	boolean write(int address, byte[] buffer);
	byte[] read(int size);
	byte[] read(int address, int size);
	Integer getAddress();
}
