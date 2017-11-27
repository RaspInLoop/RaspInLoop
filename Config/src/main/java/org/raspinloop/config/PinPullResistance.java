package org.raspinloop.config;

import java.util.EnumSet;


public enum PinPullResistance {
	OFF, PULL_UP, PULL_DOWN;

	public static EnumSet<PinPullResistance> all() {
		 return EnumSet.allOf(PinPullResistance.class);
	}

}
