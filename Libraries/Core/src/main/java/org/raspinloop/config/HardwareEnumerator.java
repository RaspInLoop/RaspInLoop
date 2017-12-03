package org.raspinloop.config;

import java.util.Collection;

public interface HardwareEnumerator {

	public abstract Collection<HardwareProperties> buildListImplementing(Class<? extends HardwareProperties	> class1);	
}
