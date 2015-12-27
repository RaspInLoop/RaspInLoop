package org.raspinloop.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;


public  class  BoardHardwareDelegate implements BoardHardware {
	

	@Override
	public String getType() {		
		throw new RuntimeException("getType not implemented by BoardHardwareDelegate");
	}

	@Override
	public String getSimulatedProviderName() {		
		throw new RuntimeException("getSimulatedProviderName not implemented by BoardHardwareDelegate");
	}

	@Override
	public String getName() {		
		throw new RuntimeException("getName not implemented by BoardHardwareDelegate");
	}

	@Override
	public String getImplementationClassName() {
		throw new RuntimeException("getImplementationClassName not implemented by BoardHardwareDelegate");
	}	
	
	@Override
	public void setName(String string) {
		throw new RuntimeException("getImplementationClassName not implemented by BoardHardwareDelegate");
	}
	
	protected ArrayList<PinImpl> outputPins = new ArrayList<PinImpl>();

	protected ArrayList<PinImpl> inputPins = new ArrayList<PinImpl>();

	protected ArrayList<BoardExtentionHardware> ExtentionComponents = new ArrayList<BoardExtentionHardware>();
	protected ArrayList<UARTComponent> UartComponents = new ArrayList<UARTComponent>();
	protected ArrayList<SPIComponent> SPIComponents = new ArrayList<SPIComponent>();
	protected ArrayList<I2CComponent> I2CComponents = new ArrayList<I2CComponent>();

	boolean usedByComp(Pin pin) {
		for (BoardExtentionHardware simulatedComponent : ExtentionComponents) {
			for (Pin usedpin : simulatedComponent.getUsedPins()) {
				if (usedpin != null && usedpin.equals(pin))
					return true;
			}
			//TODO: check if an SPI component is plugged
			//TODO: check if an UART component is plugged
			//TODO: check if an I2C component is plugged
		}
		return false;
	}
	
	@Override
	public void useInputPin(Pin pin) throws AlreadyUsedPin {
		if (usedByComp(pin))
			throw new AlreadyUsedPin(pin);
		//TODO: check if an SPI component is plugged
		//TODO: check if an UART component is plugged
		//TODO: check if an I2C component is plugged
		inputPins.add(new PinImpl(pin));
	}

	@Override
	public void useOutputPin(Pin pin) throws AlreadyUsedPin {
		if (usedByComp(pin))
			throw new AlreadyUsedPin(pin);
		//TODO: check if an SPI component is plugged
		//TODO: check if an UART component is plugged
		//TODO: check if an I2C component is plugged
		outputPins.add(new PinImpl(pin));
	}

	@Override
	public void unUsePin(Pin pin) {
		if (pin == null)
			return;
		outputPins.remove(pin);
		inputPins.remove(pin);
	}

	@Override
	public void addComponent(BoardExtentionHardware sd ) {
		ExtentionComponents.add(sd);
	}

	@Override
	public void removeComponent(BoardExtentionHardware sd) {
		ExtentionComponents.remove(sd);
	}

	@Override
	public Collection<Pin> getInputPins() {
		return Collections.<Pin>unmodifiableCollection(inputPins);
	}

	@Override
	public Collection<Pin> getOutputPins() {
		return Collections.<Pin>unmodifiableCollection(outputPins);
	}

	@Override
	public Collection<Pin> getUsedByCompPins() {
		LinkedList<Pin> usedPins = new LinkedList<Pin>();
		for (BoardExtentionHardware simulatedComponent : ExtentionComponents) {
			usedPins.addAll(simulatedComponent.getUsedPins());		
		}
		return Collections.unmodifiableCollection(usedPins);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Pin> getUnUsedPins() {
		return Collections.EMPTY_LIST;
	}
	
	
	@Override
	public Collection<BoardExtentionHardware> getComponents() {
		return Collections.unmodifiableCollection(ExtentionComponents);
	}



	@Override
	public Collection<I2CComponent> getI2cComponent() {
		return Collections.unmodifiableCollection(I2CComponents);		
	}

	@Override
	public void addComponent(I2CComponent comp) {
		I2CComponents.add(comp);
	}

	@Override
	public void removeComponent(I2CComponent comp) {
		I2CComponents.remove(comp);
	}

	@Override
	public Collection<UARTComponent> getUARTComponent() {
		return Collections.unmodifiableCollection(UartComponents);
	}

	@Override
	public void addComponent(UARTComponent comp) {
		UartComponents.add(comp);
	}

	@Override
	public void removeComponent(UARTComponent comp) {
		UartComponents.remove(comp);
	}

	@Override
	public Collection<SPIComponent> getSPIComponent() {
		return Collections.unmodifiableCollection(SPIComponents);
	}

	@Override
	public void addComponent(SPIComponent comp) {
		SPIComponents.add(comp);
	}

	@Override
	public void removeComponent(SPIComponent comp) {
		SPIComponents.remove(comp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Pin> getSupportedPin() {
		return Collections.EMPTY_LIST;
	}
}