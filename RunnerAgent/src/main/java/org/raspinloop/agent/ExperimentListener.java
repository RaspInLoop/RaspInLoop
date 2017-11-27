package org.raspinloop.agent;

import org.raspinloop.hwemulation.GpioProvider;
import org.raspinloop.timeemulation.SimulatedTime;

public interface ExperimentListener {

	void notifyStart(SimulatedTime inst,
			GpioProvider gpioProvider);

	void notifyStop(GpioProvider gpioProvider);

}
