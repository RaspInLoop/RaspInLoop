package org.raspinloop.hwemulation;

import org.raspinloop.config.Pin;
import org.raspinloop.config.PinEdge;
import org.raspinloop.config.PinState;


public class PinDigitalStateChangeEvent implements PinEvent {

	  private final PinState state;
	    private final PinEdge edge;
		private PinEventType pinEventType;
		private Pin pin;
		private Pin pin2;

	    /**
	     * Default event constructor
	     *
	     * @param pin GPIO pin number (not header pin number; not wiringPi pin number)
	     * @param state New GPIO pin state.
	     */
	    public PinDigitalStateChangeEvent(Pin pin, PinState state) {
	      
			this.pin = pin;
			this.state = state;
	        // set pin edge caused by the state change
	        this.edge = (state == PinState.HIGH) ? PinEdge.RISING : PinEdge.FALLING;
	    }

	    /**
	     * Get the new pin state raised in this event.
	     *
	     * @return GPIO pin state (HIGH, LOW)
	     */
	    public PinState getState() {
	        return state;
	    }


	    /**
	     * Get the pin edge for the state change caused by this event.
	     *
	     * @return
	     */
	    public PinEdge getEdge() {
	        return this.edge;
	    }

		@Override
		public Pin getPin() {
			return pin;
		}

		@Override
		public PinEventType getEventType() {
			return pinEventType;
		}

}
