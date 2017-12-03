package org.raspinloop.config;

import java.util.EnumSet;

public enum PinEdge {
	BOTH, RISING, FALLING
	;
	
    public static EnumSet<PinEdge> all() {
        return EnumSet.allOf(PinEdge.class);
    }
}
