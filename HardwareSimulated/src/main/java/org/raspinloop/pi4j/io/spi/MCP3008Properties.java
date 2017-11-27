package org.raspinloop.pi4j.io.spi;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.raspinloop.config.HardwareProperties;
import org.raspinloop.config.Pin;
import org.raspinloop.config.SPIComponent;
import org.raspinloop.config.SPIParent;
import org.raspinloop.hwemulation.SpiChannel;

public class MCP3008Properties implements SPIComponent {

	public static final String TYPE = "MCP3008";
	public static final String DISPLAY_NAME = "MCP3008(SPI 8-Channel 10-Bit A/D Converters)";
	public static final String SIMULATED_PROVIDER_NAME = "MCP3008";
	public static final String IMPLEMENTATION_CLASS_NAME = MCP3008.class.getName();

	private String name = DISPLAY_NAME;
	private SPIParent parent;
	
	private double vref = 3.3;
	private boolean useVrefPin= false;
	private SpiChannel channel = SpiChannel.CS0;
	private  Collection<Pin> availablesPins = Collections.emptyList();	
	
	@Override
	public String getComponentName() {
		return name;
	}

	@Override
	public HardwareProperties setComponentName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getImplementationClassName() {
		return IMPLEMENTATION_CLASS_NAME;
	}

	@Override
	public String getSimulatedProviderName() {
		return SIMULATED_PROVIDER_NAME;
	}

	@Override
	public SPIParent getParent() {
		return parent;
	}

	@Override
	public void setParent(SPIParent parent) {
		this.parent = parent;
		availablesPins = parent.getSpiPins();
	}
	

	@Override
	public Collection<Pin> getUsedPins() {
		List<Pin> used = new LinkedList<>();
		for (Pin pin : availablesPins) {
			if (   "MOSI".equalsIgnoreCase(pin.getName()) 
				|| "MISO".equalsIgnoreCase(pin.getName())
				|| "SCLK".equalsIgnoreCase(pin.getName())) {
				used.add(pin);
			}
			if ("CE0".equalsIgnoreCase(pin.getName()) && getChannel() == SpiChannel.CS0){
				used.add(pin);
			}
			if ("CE1".equalsIgnoreCase(pin.getName()) && getChannel() == SpiChannel.CS1){
				used.add(pin);
			}
		}
		return used;
	}

	public double getVref() {
		return vref;
	}

	public void setVref(double vref) {
		this.vref = vref;
	}

	public SpiChannel getChannel() {
		return channel;
	}

	public void setChannel(SpiChannel channel) {
		this.channel = channel;
	}

	public boolean isUseVrefPin() {
		return useVrefPin;
	}

	public void setUseVrefPin(boolean useVrefPin) {
		this.useVrefPin = useVrefPin;
	}


}
