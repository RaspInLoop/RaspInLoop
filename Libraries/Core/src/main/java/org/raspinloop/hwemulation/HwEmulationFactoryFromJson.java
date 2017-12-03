package org.raspinloop.hwemulation;

import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonProperties;
import org.raspinloop.config.HardwareBuilder;


public class HwEmulationFactoryFromJson implements HwEmulationFactory {

	private GpioProviderHwEmulation hd;

    
	@Override
	public GpioProviderHwEmulation create(String jsonHwDescription) throws Exception {
		GsonProperties conf = new GsonProperties(HardwareClassFactory.INSTANCE());
		
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
