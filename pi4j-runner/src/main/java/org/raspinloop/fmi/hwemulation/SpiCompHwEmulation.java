package org.raspinloop.fmi.hwemulation;


public interface SpiCompHwEmulation extends HwEmulation {
	
	int spiDataRW(short[] buffer);
	int spiDataRW(byte[] buffer);
}
