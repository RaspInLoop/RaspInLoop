package org.raspinloop.fmi.plugin.utils;

import org.raspinloop.config.HardwareProperties;

import java.util.Collection;

public interface HardwareAccessor {
    Collection<HardwareProperties> getList();

    HardwareProperties add(HardwareProperties newHarware);

    boolean delete(HardwareProperties newHarware);

    HardwareProperties update(HardwareProperties newHarware);
}
