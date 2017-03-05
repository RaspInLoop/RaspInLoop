package org.raspinloop.fmi.hwemulation;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.PinListener;



public interface GpioCompHwEmulation extends HwEmulation {
	
	boolean usePin(Pin pin);
    void setState(Pin pin, PinState state);
    PinState getState(Pin pin);
}
