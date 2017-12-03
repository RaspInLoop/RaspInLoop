package org.raspinloop.hwemulation;

import org.raspinloop.config.Pin;

public interface PinEvent {
	public Pin getPin();
	public PinEventType getEventType();
}
