package org.raspinloop.fmi.internal.hwemulation;

import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonConfig;
import org.raspinloop.fmi.hwemulation.GpioProviderHwEmulation;
import org.raspinloop.fmi.hwemulation.HardwareBuilder;
import org.raspinloop.fmi.internal.fmu.HardwareClassFactory;

public class HwEmulationFactoryFromJson implements HwEmulationFactory {

	private GpioProviderHwEmulation hd;

    
	@Override
	public GpioProviderHwEmulation create(String jsonHwDescription) throws Exception {
		GsonConfig conf = new GsonConfig(HardwareClassFactory.INSTANCE());
		
		BoardHardware hardwareProperties = conf.read(jsonHwDescription);
		ClassLoaderBuilderFactory clbf = new ClassLoaderBuilderFactory();
		HardwareBuilder builder = clbf.createBuilder(hardwareProperties);
        hd = (GpioProviderHwEmulation)builder.build();
	
		return hd;
	}

	@Override
	public GpioProviderHwEmulation get() {
		return hd;
	}
}
