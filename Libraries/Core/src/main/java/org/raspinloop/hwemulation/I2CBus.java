package org.raspinloop.hwemulation;

import java.io.IOException;

public interface I2CBus {

	int BUS_0 = 0;

	I2CDevice getDevice(int address) throws IOException;

	void close() throws IOException;

	int getBusNumber();

}
