package org.raspinloop.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;


public  class  BoardHardwareDelegate implements BoardHardware {	
	
	private String guid;

	public BoardHardwareDelegate(String guid) {

		this.setGuid(guid);
	}
	
	public BoardHardwareDelegate() {
	}

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
	public BoardHardwareDelegate setName(String string) {
		throw new RuntimeException("getImplementationClassName not implemented by BoardHardwareDelegate");
	}
	
	protected ArrayList<PinImpl> outputPins = new ArrayList<PinImpl>();

	protected ArrayList<PinImpl> inputPins = new ArrayList<PinImpl>();

	protected ArrayList<BoardExtentionHardware> extentionComponents = new ArrayList<BoardExtentionHardware>();
	protected ArrayList<UARTComponent> uartComponents = new ArrayList<UARTComponent>();
	protected ArrayList<SPIComponent> spiComponents = new ArrayList<SPIComponent>();
	protected ArrayList<I2CComponent> i2cComponents = new ArrayList<I2CComponent>();

	boolean usedByComp(Pin pin) {
		if (pin == null)
			return false;
		
		for (HardwareConfig simulatedComponent : getAllComponents()) {
			for (Pin usedpin : simulatedComponent.getUsedPins()) {
				if (usedpin != null && usedpin.equals(pin))
					return true;
			}			
		}
	
		return false;
	}
	
	@Override
	public void useInputPin(Pin pin) throws AlreadyUsedPin {
		if (usedByComp(pin))
			throw new AlreadyUsedPin(pin);

		inputPins.add(new PinImpl(pin));
	}

	@Override
	public void useOutputPin(Pin pin) throws AlreadyUsedPin {
		if (usedByComp(pin))
			throw new AlreadyUsedPin(pin);
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
		extentionComponents.add(sd);
	}

	@Override
	public void removeComponent(BoardExtentionHardware sd) {
		extentionComponents.remove(sd);
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
		
		for (HardwareConfig simulatedComponent : getAllComponents()) {
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
		return Collections.unmodifiableCollection(extentionComponents);
	}

	@Override
	public Collection<I2CComponent> getI2cComponent() {
		return Collections.unmodifiableCollection(i2cComponents);		
	}

	@Override
	public void addComponent(I2CComponent comp) {
		i2cComponents.add(comp);
	}

	@Override
	public void removeComponent(I2CComponent comp) {
		i2cComponents.remove(comp);
	}

	@Override
	public Collection<UARTComponent> getUARTComponent() {
		return Collections.unmodifiableCollection(uartComponents);
	}

	@Override
	public void addComponent(UARTComponent comp) {
		uartComponents.add(comp);
	}

	@Override
	public void removeComponent(UARTComponent comp) {
		uartComponents.remove(comp);
	}

	@Override
	public Collection<SPIComponent> getSPIComponent() {
		return Collections.unmodifiableCollection(spiComponents);
	}

	@Override
	public void addComponent(SPIComponent comp) {
		spiComponents.add(comp);
	}

	@Override
	public void removeComponent(SPIComponent comp) {
		spiComponents.remove(comp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Pin> getSupportedPin() {
		return Collections.EMPTY_LIST;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Override
	public Collection<HardwareConfig> getAllComponents() {
		HashSet<HardwareConfig> col = new HashSet<HardwareConfig>(getComponents());
		col.addAll(getSPIComponent());
		col.addAll(getI2cComponent());
		col.addAll(getUARTComponent());
		return col;
	}

	@Override
	public Collection<Pin> getUsedPins() {
		return getUsedByCompPins();
	}

	@Override
	public Collection<Pin> getSpiPins() {
		return Collections.emptyList();
	}
}