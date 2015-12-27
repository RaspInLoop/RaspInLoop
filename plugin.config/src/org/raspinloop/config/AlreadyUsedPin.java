package org.raspinloop.config;


@SuppressWarnings("serial")
public class AlreadyUsedPin extends Exception {

	final private Pin usedpin;

	public AlreadyUsedPin(org.raspinloop.config.Pin pin) {
		super("Pin already used:"+ pin.getName());
		this.usedpin = pin;		
	}

	public Pin getUsedpin() {
		return usedpin;
	}

}
