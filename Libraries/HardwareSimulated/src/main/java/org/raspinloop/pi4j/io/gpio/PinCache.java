package org.raspinloop.pi4j.io.gpio;

import org.raspinloop.config.Pin;
import org.raspinloop.config.PinMode;
import org.raspinloop.config.PinPullResistance;
import org.raspinloop.config.PinState;

public class PinCache {
	
private Pin pin;
private PinMode mode;
private PinState state;
private PinPullResistance resistance;
private int pwmValue;

public PinCache(Pin pin) {
	this.pin = pin;	
}

public PinMode getMode() {
	return mode;
}

public void setState(PinState state) {
	this.state = state;
}

public PinState getState() {
	return state;
}

public void setResistance(PinPullResistance resistance) {
	this.resistance = resistance;	
}

public PinPullResistance getResistance() {
	// TODO Auto-generated method stub
	return null;
}

public void setPwmValue(int pwmValue) {
	this.pwmValue = pwmValue;
}

public int getPwmValue() {
	return pwmValue;
}


}
