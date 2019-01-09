package org.raspinloop.fmi.plugin.utils;

import com.intellij.ide.util.PropertiesComponent;
import org.raspinloop.config.GsonProperties;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.hwemulation.HardwareClassFactory;

import java.util.ArrayList;
import java.util.Collection;

public class PreferenceHardwareAcesseor implements HardwareAccessor {

    private PropertiesComponent preferences = PropertiesComponent.getInstance();

    @Override
    public Collection<HardwareProperties> getList() {

        return buildListFromPref(preferences);
    }

    @Override
    public HardwareProperties add(HardwareProperties newHarware){
        return null;
    }

    @Override
    public boolean delete(HardwareProperties newHarware){

        return false;
    }

    @Override
    public HardwareProperties update(HardwareProperties newHarware){

        return newHarware;
    }

    private ArrayList<HardwareProperties> buildListFromPref(PropertiesComponent preferences ) {
        ArrayList<HardwareProperties> hardwares = new ArrayList<HardwareProperties>();
        String listStr = preferences.getValue("org.raspinloop.fmi.preferences.configuredhardware.HwList", "");
        String[] hwNames = listStr.split(":");
        for (String hwName : hwNames) {
            String bytes = preferences.getValue("org.raspinloop.fmi.preferences.configuredhardware."+hwName, "");
            if (bytes.trim().length() != 0) {
                try {
                    GsonProperties conf = new GsonProperties(HardwareClassFactory.INSTANCE());
                    HardwareProperties hw = conf.read(bytes);
                    if (hw != null)
                        hardwares.add(hw);
                } catch (Exception e)
                {
                }
            }
        }
        return hardwares;
    }
}
