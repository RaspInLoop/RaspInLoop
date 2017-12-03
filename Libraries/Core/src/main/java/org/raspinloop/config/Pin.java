package org.raspinloop.config;

import java.util.EnumSet;

public interface Pin {

	String getName();

	String getProvider();

	int getAddress();

	EnumSet<PinMode> getSupportedPinModes();

	EnumSet<PinPullResistance> getSupportedPinPullResistance();

	void setProvider(String provider);

	void setAddress(int address);

	void setName(String name);

}
