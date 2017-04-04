package org.raspinloop.pi4j.io.gpio;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.raspinloop.config.AlreadyUsedPin;
import org.raspinloop.config.BoardExtentionHardware;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.BoardHardwareDelegate;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.config.I2CComponent;
import org.raspinloop.config.Pin;
import org.raspinloop.config.PinImpl;
import org.raspinloop.config.SPIComponent;
import org.raspinloop.config.UARTComponent;


public class RaspiGpioSimulatorProperties implements BoardHardware {

	public static final int NB_PIN = 21;
	public static final String TYPE = "raspberryPiGpioSimulator";
	public static final String DISPLAY_NAME = "RaspberryPi GPIO Simulator";
	public static final String SIMULATED_PROVIDER_NAME = "RaspberryPi GPIO Provider";

	public static final String GUID = "{5571c639-6438-4eee-839e-ff8442e3bbbc}"; 
	
	static Collection<Pin> spiPins = Arrays.asList((Pin)new PinImpl(SIMULATED_PROVIDER_NAME, "MOSI", 12),
														new PinImpl(SIMULATED_PROVIDER_NAME, "MISO", 13),
														new PinImpl(SIMULATED_PROVIDER_NAME, "SCLK", 14),
														new PinImpl(SIMULATED_PROVIDER_NAME, "CE0", 10),
														new PinImpl(SIMULATED_PROVIDER_NAME, "CE1", 11));
	static Collection<Pin> i2cPins  = Arrays.asList((Pin)  new PinImpl(SIMULATED_PROVIDER_NAME, "SDA", 8),
			   new PinImpl(SIMULATED_PROVIDER_NAME, "SCL", 9));
	
	static Collection<Pin> uartPins  = Arrays.asList((Pin)new PinImpl(SIMULATED_PROVIDER_NAME, "TX", 15),
			   new PinImpl(SIMULATED_PROVIDER_NAME, "RX", 16));
	
	BoardHardwareDelegate boardHardwareDelegate = new BoardHardwareDelegate(GUID);
	private String name = DISPLAY_NAME;

	static Pin[] pins = new Pin[NB_PIN];
	static {
	for (int i = 0; i < pins.length; i++) {
		pins[i] = new PinImpl(SIMULATED_PROVIDER_NAME, "GPIO " + i, i);		
		}
	}

	
	public RaspiGpioSimulatorProperties() {
				
	}
	
	
	@Override
	public String getName() {
		return name ;
	}

	@Override
	public HardwareConfig setName(String string) {
		this.name = string;
		return this;
	}

	@Override
	public String getType() {		
		return TYPE;
	}

	@Override
	public String getImplementationClassName() {		
		return RaspiGpioSimulator.class.getName();
	}

	@Override
	public String getSimulatedProviderName() {
		return SIMULATED_PROVIDER_NAME;
	}

	@Override
	public Collection<I2CComponent> getI2cComponent() {
		return boardHardwareDelegate.getI2cComponent();
	}

	@Override
	public void addComponent(I2CComponent comp) {
		boardHardwareDelegate.addComponent(comp);
	}

	@Override
	public void removeComponent(I2CComponent comp) {
		boardHardwareDelegate.removeComponent(comp);

	}

	@Override
	public Collection<UARTComponent> getUARTComponent() {
		return boardHardwareDelegate.getUARTComponent();
	}

	@Override
	public void addComponent(UARTComponent comp) {
		boardHardwareDelegate.addComponent(comp);

	}

	@Override
	public void removeComponent(UARTComponent comp) {
		boardHardwareDelegate.removeComponent(comp);

	}

	@Override
	public Collection<SPIComponent> getSPIComponent() {
		return boardHardwareDelegate.getSPIComponent();
	}

	@Override
	public void addComponent(SPIComponent comp) {
		boardHardwareDelegate.addComponent(comp);
	}

	@Override
	public void removeComponent(SPIComponent comp) {
		boardHardwareDelegate.removeComponent(comp);

	}

	@Override
	public void useInputPin(Pin pin) throws AlreadyUsedPin {
		boardHardwareDelegate.useInputPin(pin);
	}

	@Override
	public void useOutputPin(Pin pin) throws AlreadyUsedPin {
		boardHardwareDelegate.useOutputPin(pin);
	}

	@Override
	public void unUsePin(Pin pin) {
		boardHardwareDelegate.unUsePin(pin);
	}

	@Override
	public Collection<Pin> getOutputPins() {		
		return boardHardwareDelegate.getOutputPins();
	}

	@Override
	public Collection<Pin> getInputPins() {		
		return boardHardwareDelegate.getInputPins();
	}

	@Override
	public Collection<Pin> getUnUsedPins() {		
		Set<Pin> remainingPins = new  HashSet<Pin>(getSupportedPin());
		
		remainingPins.removeAll(getUsedByCompPins());
		remainingPins.removeAll(getInputPins());
		remainingPins.removeAll(getOutputPins());
		return Collections.unmodifiableCollection(remainingPins);
		
	}

	@Override
	public Collection<Pin> getUsedByCompPins() {
		return boardHardwareDelegate.getUsedByCompPins();
	}

	@Override
	public void addComponent(BoardExtentionHardware ext) throws AlreadyUsedPin {
		boardHardwareDelegate.addComponent(ext);

	}

	@Override
	public void removeComponent(BoardExtentionHardware ext) {
		boardHardwareDelegate.removeComponent(ext);

	}

	@Override
	public Collection<BoardExtentionHardware> getComponents() {
		return boardHardwareDelegate.getComponents();
	}


	@Override
	public Collection<Pin> getSupportedPin() {
		return new HashSet<Pin>(Arrays.asList(pins));
	}


	@Override
	public String getGuid() {
		return boardHardwareDelegate.getGuid();
	}


	@Override
	public Collection<HardwareConfig> getAllComponents() {
		return boardHardwareDelegate.getAllComponents();
	}


	@Override
	public Collection<Pin> getUsedPins() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Collection<Pin> getSpiPins() {
		return spiPins;
	}



}
