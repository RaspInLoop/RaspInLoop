package org.raspinloop.fmi;



public interface SpiCompHwEmulation extends HwEmulation {
	
	int spiDataRW(short[] buffer);
	int spiDataRW(byte[] buffer);
}
