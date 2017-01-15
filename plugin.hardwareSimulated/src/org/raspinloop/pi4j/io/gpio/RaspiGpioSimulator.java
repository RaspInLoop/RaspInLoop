package org.raspinloop.pi4j.io.gpio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.raspinloop.config.BoardExtentionHardware;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.hwemulation.GpioCompHwEmulation;
import org.raspinloop.fmi.hwemulation.GpioProviderHwEmulation;
import org.raspinloop.fmi.hwemulation.HardwareBuilder;
import org.raspinloop.fmi.hwemulation.HardwareBuilderFactory;
import org.raspinloop.fmi.hwemulation.HwEmulation;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;

import com.pi4j.io.gpio.GpioProviderBase;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinEdge;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class RaspiGpioSimulator extends GpioProviderBase implements GpioProviderHwEmulation {

	static public final String GUID = RaspiGpioSimulatorProperties.GUID;

	final static Logger logger = Logger.getLogger(RaspiGpioSimulator.class);

	protected final Map<Pin, PinEdge> edgeDetectionCache = new ConcurrentHashMap<Pin, PinEdge>();

	private RaspiGpioSimulatorProperties properties = new RaspiGpioSimulatorProperties();

	private HardwareBuilderFactory builderFactory;

	private Map<HardwareConfig, HwEmulation> classCache = new HashMap<HardwareConfig, HwEmulation>();

	
	// currently, we reserve all refs from 0 to 40 for pin (I/O)
	// other component will be base after this range (even if they use pin)
	private int nextAvailableBase = RaspiPin.GPIO_20.getAddress() + 20;

	public RaspiGpioSimulator(HardwareBuilder builder) {
		this.builderFactory = builder.getBuilderFactory();
		if (builder.getProperties() instanceof RaspiGpioSimulatorProperties)
			this.properties = (RaspiGpioSimulatorProperties) builder.getProperties();
	}

	@Override
	public String getType() {
		return properties.getType();
	}

	
	
	
	@Override
	public boolean hasPin(Pin pin) {
		return (pin.getProvider() == properties.getSimulatedProviderName());
	}

	@Override
	public void setMode(Pin pin, PinMode mode) {
		super.setMode(pin, mode);
		// if this is an input pin, then configure edge detection
		if (PinMode.allInputs().contains(mode)) {
			System.out.println("-- set setEdgeDetection [" + pin + "] to  [" + PinEdge.BOTH + "]");
			edgeDetectionCache.put(pin, PinEdge.BOTH);
		}
	}

	@Override
	public void setValue(Pin pin, double value) {
		super.setValue(pin, value);
		throw new RuntimeException("This GPIO provider does not support analog pins.");
	}

	@Override
	public double getValue(Pin pin) {
		super.getValue(pin);
		throw new RuntimeException("This GPIO provider does not support analog pins.");
	}
	
	@Override
	public void setState(Pin pin, PinState state) {
		super.setState(pin, state);
		
		for (BoardExtentionHardware comp : properties.getComponents()) {			
				HwEmulation emulationComp = getEmulationInstance(comp);
					if (emulationComp instanceof GpioCompHwEmulation) {
						GpioCompHwEmulation compHwE = ((GpioCompHwEmulation)emulationComp);
						if (compHwE.usePin(pin))
							compHwE.setState(pin, state);
					}
		}
	}
	
	@Override
	public PinState getState(Pin pin) {

		for (BoardExtentionHardware comp : properties.getComponents()) {
			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp instanceof GpioCompHwEmulation) {
				GpioCompHwEmulation compHwE = ((GpioCompHwEmulation) emulationComp);
				if (compHwE.usePin(pin))
					return compHwE.getState(pin);
			}
		}
		return super.getState(pin);

	}

	@Override
	public String getHWGuid() {
		return GUID;
	}

	@Override
	public boolean enterInitialize() {
		boolean result = true;
		for (BoardExtentionHardware comp : properties.getComponents()){
			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp != null)
				result &= emulationComp.enterInitialize();
		}
		return result;
	}

	@Override
	public boolean exitInitialize() {
		boolean result = true;
		for (BoardExtentionHardware comp : properties.getComponents()){
			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp != null)
				result &= emulationComp.exitInitialize();
		}
		return result;
	}

	@Override
	public void terminate() {
		for (BoardExtentionHardware comp : properties.getComponents()) {
			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp != null)
				emulationComp.terminate();
		}
		shutdown();
	}

	@Override
	public void reset() {
		for (BoardExtentionHardware comp : properties.getComponents()) {
			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp != null)
				emulationComp.reset();
		}
		shutdown();
	}

	@Override
	public List<Double> getReal(List<Integer> refs) {
		// NO check in pin: there is no pin with real value

		List<Double> result = new ArrayList<Double>(refs.size());
		for (Integer ref : refs) {
			// TODO: we should group refs by component in order to make only one
			// call
			HwEmulation comp = getSimulatedCompUsingRef(ref);
			if (comp != null) {
				List<Double> compResult = comp.getReal(Arrays.asList(ref));
				for (Double real1 : compResult) {
					result.add(real1);
				}
			} else {
				logger.warn("ref:" + ref + " not used in application");
				result.add(0.0); // Invalid value defined for real ?
			}
		}
		return result;
	}

	@Override
	public List<Integer> getInteger(List<Integer> refs) {
		// NO check in pin: there is no pin with integer value
		List<Integer> result = new ArrayList<Integer>(refs.size());
		for (Integer ref : refs) {
			// TODO: we should group refs by component in order to make only one
			// call
			HwEmulation comp = getSimulatedCompUsingRef(ref);
			if (comp != null) {
				List<Integer> compResult = comp.getInteger(Arrays.asList(ref));
				for (Integer int1 : compResult) {
					result.add(int1);
				}
			} else {
				logger.warn("ref:" + ref + " not used in application");
				result.add(0); // Invalid value defined for integer ?
			}
		}
		return result;
	}

	@Override
	public boolean setReal(Map<Integer, Double> ref_values) {
		// NO check in pin: there is no pin with real value
		for (Entry<Integer, Double> ref_value : ref_values.entrySet()) {
			// TODO: we should group ref_value by component in order to make
			// only one call
			HwEmulation comp = getSimulatedCompUsingRef(ref_value.getKey());
			if (comp != null) {
				HashMap<Integer, Double> map = new HashMap<>(1);
				map.put(ref_value.getKey(), ref_value.getValue());
				comp.setReal(map);
			} else {
				logger.warn("PIN[ref:" + ref_value.getKey() + "] not used in application");
				return true;
			}
		}
		return true;
	}

	@Override
	public boolean setInteger(Map<Integer, Integer> ref_values) {
		// NO check in pin: there is no pin with integer value
		for (Entry<Integer, Integer> ref_value : ref_values.entrySet()) {
			// TODO: we should group ref_value by component in order to make
			// only one call
			HwEmulation comp = getSimulatedCompUsingRef(ref_value.getKey());
			if (comp != null) {
				HashMap<Integer, Integer> map = new HashMap<>(1);
				map.put(ref_value.getKey(), ref_value.getValue());
				comp.setInteger(map);
			} else {
				logger.warn("PIN[ref:" + ref_value.getKey() + "] not used in application");
				return true;
			}
		}
		return true;
	}

	@Override
	public List<Boolean> getBoolean(List<Integer> refs) {
		// Boolean value may be either a pin state or a value given by a
		// component
		List<Boolean> result = new LinkedList<Boolean>();
		for (Integer ref : refs) {
			Pin pin = getPin(ref);
			if (pin != null)
				result.add(super.getState(pin) == PinState.HIGH);
			else {
				// TODO: we should group refs by component in order to make only
				// one call
				HwEmulation comp = getSimulatedCompUsingRef(ref);
				if (comp != null) {
					List<Boolean> compResult = comp.getBoolean(Arrays.asList(ref));
					for (Boolean boolean1 : compResult) {
						result.add(boolean1);
					}
				} else {
					logger.warn("ref:" + ref + " not used in application");
					result.add(false); // default pull up/down resistor ??
				}
			}
		}
		return result;
	}

	@Override
	public boolean setBoolean(Map<Integer, Boolean> ref_values) {
		// Boolean value may be either a pin state or a component value to set.
		for (Entry<Integer, Boolean> ref_value : ref_values.entrySet()) {
			Pin pin = getPin(ref_value.getKey());
			if (pin != null) {
				PinState state = ref_value.getValue().equals(Boolean.TRUE) ? PinState.HIGH : PinState.LOW;
				if (getMode(pin) == PinMode.DIGITAL_OUTPUT)
					super.setState(pin, state);
				else if (getMode(pin) == PinMode.DIGITAL_INPUT && getPinCache(pin).getState() != state) {
					if (edgeDetectionCache.get(pin) == null || edgeDetectionCache.get(pin) == PinEdge.BOTH
							|| (edgeDetectionCache.get(pin) == PinEdge.RISING && state == PinState.HIGH)
							|| (edgeDetectionCache.get(pin) == PinEdge.FALLING && state == PinState.LOW))
						dispatchPinDigitalStateChangeEvent(pin, state);
					// cache pin state
					getPinCache(pin).setState(state);
				}
			} else {
				// TODO: we should group ref_value by component in order to make
				// only one call
				HwEmulation comp = getSimulatedCompUsingRef(ref_value.getKey());
				if (comp != null) {
					HashMap<Integer, Boolean> map = new HashMap<>(1);
					map.put(ref_value.getKey(), ref_value.getValue());
					comp.setBoolean(map);
				} else {
					logger.warn("PIN[ref:" + ref_value.getKey() + "] not used in application");
					return true;
				}
			}
		}
		return true;
	}

	/**
	 * Return the pin given its address
	 * 
	 * output pin references equals its address. input pin refs equals address +
	 * max output pin address (20)
	 * 
	 * @param ref
	 * @return
	 */
	private Pin getPin(Integer ref) {
		//
		//
		for (Pin pin : super.cache.keySet()) {
			if (ref > RaspiPin.GPIO_20.getAddress()) { // input pin
				if (ref.equals(RaspiPin.GPIO_20.getAddress() + 1 + pin.getAddress()))
					return pin;
			} else { // Output pin
				if (ref.equals(pin.getAddress()))
					return pin;
			}
		}
		return null;
	}

	@Override
	public boolean isTerminated() {
		return isShutdown();
	}

	// TODO: use a utility class to handle those pin/modelVariable handling code

	@Override
	public List<Fmi2ScalarVariable> getModelVariables() {
		LinkedList<Fmi2ScalarVariable> list = new LinkedList<Fmi2ScalarVariable>();
		for (org.raspinloop.config.Pin pin : properties.getInputPins()) {
			list.add(createBooleanInput(pin.getName(), "Input signal related to pin " + pin.getName(), getInputReference(pin)));
		}

		for (org.raspinloop.config.Pin pin : properties.getOutputPins()) {
			list.add(createBooleanOutput(pin.getName(), "Output signal related to pin " + pin.getName(), getOutputReference(pin)));
		}

		for (BoardExtentionHardware comp : properties.getComponents()) {

			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp != null)
				list.addAll(emulationComp.getModelVariables());
		}

		return list;
	}

	private HwEmulation getEmulationInstance(BoardExtentionHardware comp) {
		if (classCache.containsKey(comp))
			return classCache.get(comp);

		HwEmulation hardware;
		try {
			hardware = builderFactory.createBuilder(comp).setBaseReference(nextAvailableBase ).build();
			nextAvailableBase+=hardware.getModelVariables().size();
		} catch (Exception e) {
			logger.error("Cannot build class for board extension named " + comp.getName() + " reason:" + e.getMessage());
			return null;
		}
		classCache.put(comp, hardware);
		return hardware;
	}

	private Fmi2ScalarVariable createBooleanOutput(String name, String descritpion, long ref) {
		Fmi2ScalarVariable sc = new Fmi2ScalarVariable();
		org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable.Boolean scb = new Fmi2ScalarVariable.Boolean();
		sc.setBoolean(scb);
		sc.setName(name);
		sc.setValueReference(ref);
		sc.setDescription(descritpion);
		sc.setCausality("output");
		sc.setVariability("continuous");
		return sc;
	}

	private Fmi2ScalarVariable createBooleanInput(String name, String descritpion, long ref) {
		Fmi2ScalarVariable sc = new Fmi2ScalarVariable();
		org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable.Boolean scb = new Fmi2ScalarVariable.Boolean();
		sc.setBoolean(scb);
		sc.setName(name);
		sc.setValueReference(ref);
		sc.setDescription(descritpion);
		sc.setCausality("input");
		sc.setVariability("continuous");
		return sc;
	}

	private static int getOutputReference(org.raspinloop.config.Pin pin) {
		return pin.getAddress();
	}

	private static int getInputReference(org.raspinloop.config.Pin pin) {
		return RaspiPin.GPIO_20.getAddress() + 1 + pin.getAddress();
	}

	private HwEmulation getSimulatedCompUsingRef(long ref) {
		for (BoardExtentionHardware comp : properties.getComponents()) {
			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp != null) {
				for (Fmi2ScalarVariable modelVariable : emulationComp.getModelVariables()) {
					if (modelVariable.getValueReference() == ref)
						return emulationComp;
				}
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return properties.getName();
	}

}
