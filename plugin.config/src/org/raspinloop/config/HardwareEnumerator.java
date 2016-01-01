package org.raspinloop.config;

import java.util.Collection;

public interface HardwareEnumerator {

	public abstract Collection<HardwareConfig> buildListImplementing(Class<? extends HardwareConfig	> class1);	
}
