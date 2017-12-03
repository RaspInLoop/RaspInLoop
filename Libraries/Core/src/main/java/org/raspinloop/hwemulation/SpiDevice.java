package org.raspinloop.hwemulation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;


public interface SpiDevice {

    public static final SpiMode DEFAULT_SPI_MODE = SpiMode.MODE_0;
    public static final int DEFAULT_SPI_SPEED = 1000000; // 1MHz (range is 500kHz - 32MHz)
    public static final int MAX_SUPPORTED_BYTES = 2048;
    
	String write(String data, String charset) throws IOException;
	String write(String data, Charset charset) throws IOException;
	ByteBuffer write(ByteBuffer data) throws IOException;
	byte[] write(InputStream input) throws IOException;
	int write(InputStream input, OutputStream output) throws IOException;
	byte[] write(byte[] data) throws IOException;
	short[] write(short[] data) throws IOException;
	byte[] write(byte[] data, int start, int length) throws IOException;
	short[] write(short[] data, int start, int length) throws IOException;

}
