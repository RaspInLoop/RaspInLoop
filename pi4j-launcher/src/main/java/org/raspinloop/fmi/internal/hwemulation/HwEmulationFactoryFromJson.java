package org.raspinloop.fmi.internal.hwemulation;

import java.util.HashMap;

import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonConfig;
import org.raspinloop.fmi.Instance;
import org.raspinloop.fmi.hwemulation.GpioProviderHwEmulation;
import org.raspinloop.fmi.hwemulation.HardwareBuilder;
import org.raspinloop.fmi.internal.fmu.HardwareClassFactory;

public class HwEmulationFactoryFromJson implements HwEmulationFactory {

	private HashMap<String, GpioProviderHwEmulation> map = new HashMap<String, GpioProviderHwEmulation>();

    
	@Override
	public GpioProviderHwEmulation create(Instance inst, String jsonHwDescription) throws Exception {
		GsonConfig conf = new GsonConfig(HardwareClassFactory.INSTANCE());
		
		BoardHardware hardwareProperties = conf.read(jsonHwDescription);
		ClassLoaderBuilderFactory clbf = new ClassLoaderBuilderFactory();
		HardwareBuilder builder = clbf.createBuilder(hardwareProperties);
		GpioProviderHwEmulation hd = (GpioProviderHwEmulation)builder.build();
	
		if (inst.GUID.equalsIgnoreCase(hd.getHWGuid())) {
			map.put(inst.instanceName, hd);
			return map.get(inst.instanceName);
		}
		return null;
	}

	@Override
	public GpioProviderHwEmulation get(Instance c) {
		return map.get(c.instanceName);
	}

	@Override
	public void remove(Instance c) {
		if (map.get(c.instanceName) != null)
			map.remove(c.instanceName);
	}



}
