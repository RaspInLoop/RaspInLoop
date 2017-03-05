package org.raspinloop.fmi;

import org.raspinloop.fmi.internal.timeemulation.SimulatedTime;

import com.pi4j.io.gpio.GpioProvider;

public interface ExperimentListener {

	void notifyStart(SimulatedTime inst,
			GpioProvider gpioProvider);

	void notifyStop(GpioProvider gpioProvider);

}
