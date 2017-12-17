package org.raspinloop.config;

import java.util.EnumSet;


public enum PinMode {
	DIGITAL_INPUT(0, "input"), 
	DIGITAL_OUTPUT(1, "output"), 
	PWM_OUTPUT(2, "pwm_output"), 
	SOFT_PWM_OUTPUT(4, "soft_pwm_output");

	final private int value;
	final private String name;

	PinMode(int value, String name) {
		this.value = value;
		this.name = name;

	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static EnumSet<PinMode> allDigital() {
		return EnumSet.of(PinMode.DIGITAL_INPUT, PinMode.DIGITAL_OUTPUT);
	}

	public static EnumSet<PinMode> all() {
		return EnumSet.allOf(PinMode.class);
	}

	public static EnumSet<PinMode> allInputs() {
		return EnumSet.of(PinMode.DIGITAL_INPUT);
	}
	

    public static EnumSet<PinMode> allOutput() {
        return EnumSet.of(PinMode.DIGITAL_OUTPUT,
                          PinMode.PWM_OUTPUT);
    }
}
