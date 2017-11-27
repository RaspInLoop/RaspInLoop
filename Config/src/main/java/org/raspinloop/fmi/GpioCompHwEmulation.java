package org.raspinloop.fmi;


import org.raspinloop.config.Pin;
import org.raspinloop.config.PinState;


public interface GpioCompHwEmulation extends HwEmulation {
	
	boolean usePin(Pin pin);
    void setState(Pin pin, PinState state);
    PinState getState(Pin pin);
}
