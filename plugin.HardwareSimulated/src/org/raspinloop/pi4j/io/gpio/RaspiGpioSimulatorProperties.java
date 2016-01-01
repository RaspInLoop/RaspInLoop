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

	
	BoardHardwareDelegate boardHardwareDelegate = new BoardHardwareDelegate();
	private String name = DISPLAY_NAME;

	static Pin[] pins = new Pin[NB_PIN];
	static {
	for (int i = 0; i < pins.length; i++) {
		pins[i] = new PinImpl();
		pins[i].setAddress(i);
		pins[i].setName("GPIO " + i);
		pins[i].setProvider(SIMULATED_PROVIDER_NAME);
		pins[i].getSupportedPinModes().clear();	
	}
	}
	
	public RaspiGpioSimulatorProperties() {
				
	}
	
	
	@Override
	public String getName() {
		return name ;
	}

	@Override
	public void setName(String string) {
		this.name = string;
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

}
