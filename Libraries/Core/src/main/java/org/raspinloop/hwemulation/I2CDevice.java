package org.raspinloop.hwemulation;

import java.io.IOException;

public interface I2CDevice {

	int getAddress();

	void write(byte data) throws IOException;

	void write(byte[] data, int offset, int size) throws IOException;

	void write(byte[] buffer) throws IOException;

	void write(int address, byte data) throws IOException;

	void write(int address, byte[] data, int offset, int size) throws IOException;

	int read() throws IOException;

	int read(byte[] data, int offset, int size) throws IOException;

	int read(int address) throws IOException;

	int read(int address, byte[] data, int offset, int size) throws IOException;

	int read(byte[] writeData, int writeOffset, int writeSize, byte[] readData, int readOffset, int readSize) throws IOException;

}
