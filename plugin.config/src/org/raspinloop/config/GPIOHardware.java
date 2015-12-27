package org.raspinloop.config;

import java.util.Collection;

public interface GPIOHardware {

	public abstract void useInputPin(Pin pin) throws AlreadyUsedPin;

	public abstract void useOutputPin(Pin pin) throws AlreadyUsedPin;

	public abstract void unUsePin(Pin pin);

	public abstract Collection<Pin> getOutputPins();

	public abstract Collection<Pin> getInputPins();

	public abstract Collection<Pin> getUnUsedPins();

}