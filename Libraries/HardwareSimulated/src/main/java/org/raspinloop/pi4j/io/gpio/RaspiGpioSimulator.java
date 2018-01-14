/*******************************************************************************
 * Copyright 2018 RaspInLoop
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.raspinloop.pi4j.io.gpio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.raspinloop.config.HardwareBuilder;
import org.raspinloop.config.HardwareBuilderFactory;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.config.Pin;
import org.raspinloop.config.PinEdge;
import org.raspinloop.config.PinMode;
import org.raspinloop.config.PinPullResistance;
import org.raspinloop.config.PinState;
import org.raspinloop.fmi.GpioCompHwEmulation;
import org.raspinloop.fmi.HwEmulation;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;
import org.raspinloop.hwemulation.GpioProvider;
import org.raspinloop.hwemulation.GpioProviderHwEmulation;
import org.raspinloop.hwemulation.PinDigitalStateChangeEvent;
import org.raspinloop.hwemulation.PinEvent;
import org.raspinloop.hwemulation.PinEventType;
import org.raspinloop.hwemulation.PinListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class act has the Raspberry board itself. It implements
 * GpioProviderHwEmulation which is compound of {@link HwEmulation} and
 * {@link GpioProvider} <br>
 * <br>
 * {@link HwEmulation} is the interface used by the simulation tool. To get the
 * description (model), to get the ID, to initialize, reset and terminate the
 * simulation and to read and write variable (setBoolean, setInteger,...,
 * getBoolean, getInteger,...) <br>
 * While {@link GpioProvider} is the interface used by the code to debug. (it is
 * adapted by an adapter at runtime and replace the library which access the
 * hardware)
 * 
 * A lot of this code is inspired from the
 * {@link com.pi4j.io.gpio.GpioProviderBase} , the Pi4j abstract class emulated
 * by this one.
 */
public class RaspiGpioSimulator implements GpioProviderHwEmulation {

	static public final String GUID = RaspiGpioSimulatorProperties.GUID;

	final static Logger logger = LoggerFactory.getLogger(RaspiGpioSimulator.class);

	private static final int DEFAULT_CACHE_SIZE = 100;

	protected final Map<Pin, PinEdge> edgeDetectionCache = new ConcurrentHashMap<Pin, PinEdge>();

	private RaspiGpioSimulatorProperties properties = new RaspiGpioSimulatorProperties();

	private HardwareBuilderFactory builderFactory;

	private Map<HardwareProperties, HwEmulation> classCache = new HashMap<HardwareProperties, HwEmulation>();

	protected PinCache[] cache = new PinCache[DEFAULT_CACHE_SIZE];
	protected final Map<Pin, List<PinListener>> listeners = new ConcurrentHashMap<>();

	// currently, we reserve all refs from 0 to 40 for pin (I/O)
	// other component will be base after this range (even if they use pin)
	private int nextAvailableBase = RaspiPin.GPIO_20.getAddress() + 20;

	private Set<Pin> exportedPin = new HashSet<>();

	private boolean isshutdown;

	public RaspiGpioSimulator(HardwareBuilder builder) {
		this.builderFactory = builder.getBuilderFactory();
		if (builder.getProperties() instanceof RaspiGpioSimulatorProperties)
			this.properties = (RaspiGpioSimulatorProperties) builder.getProperties();
	}

	@Override
	public String getType() {
		return properties.getType();
	}

	public boolean hasPin(Pin pin) {
		return (pin.getProvider() == properties.getSimulatedProviderName());
	}

	public void setMode(Pin pin, PinMode mode) {
		// if this is an input pin, then configure edge detection
		if (PinMode.allInputs().contains(mode)) {
			System.out.println("-- set setEdgeDetection [" + pin + "] to  [" + PinEdge.BOTH + "]");
			edgeDetectionCache.put(pin, PinEdge.BOTH);
		}
	}

	public void setState(Pin pin, PinState state) {
		
		getPinCache(pin).setState(state);
		for (HardwareProperties comp : properties.getAllComponents()) {
			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp instanceof GpioCompHwEmulation) {
				GpioCompHwEmulation compHwE = ((GpioCompHwEmulation) emulationComp);
				if (compHwE.usePin(pin))
					compHwE.setState(pin, state);
			}
		}
	}

	public PinState getState(Pin pin) {

		for (HardwareProperties comp : properties.getAllComponents()) {
			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp instanceof GpioCompHwEmulation) {
				GpioCompHwEmulation compHwE = ((GpioCompHwEmulation) emulationComp);
				if (compHwE.usePin(pin))
					return compHwE.getState(pin);
			}
		}
		PinState state = getPinCache(pin).getState();
		
		return state;
	}

	@Override
	public String getHWGuid() {
		return GUID;
	}

	@Override
	public boolean enterInitialize() {
		boolean result = true;
		for (HardwareProperties comp : properties.getAllComponents()) {
			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp != null)
				result &= emulationComp.enterInitialize();
		}
		return result;
	}

	@Override
	public boolean exitInitialize() {
		boolean result = true;
		for (HardwareProperties comp : properties.getAllComponents()) {
			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp != null)
				result &= emulationComp.exitInitialize();
		}
		return result;
	}

	@Override
	public void terminate() {
		for (HardwareProperties comp : properties.getAllComponents()) {
			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp != null)
				emulationComp.terminate();
		}
	}

	@Override
	public void reset() {
		for (HardwareProperties comp : properties.getAllComponents()) {
			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp != null)
				emulationComp.reset();
		}
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
				result.add(getState(pin) == PinState.HIGH);
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

	public PinMode getMode(Pin pin) {
		if (!hasPin(pin)) {
			throw new RuntimeException("unknown Pin");
		}

		// return cached mode value
		return getPinCache(pin).getMode();
	}

	protected PinCache getPinCache(Pin pin) {

		int address = pin.getAddress();

		// dynamically resize pin cache storage if needed based on pin address
		if (address > cache.length) {
			// create a new array with existing contents
			// that is 100 elements larger than the requested address
			// (we add the extra 100 elements to provide additional overhead
			// capacity in
			// an attempt to minimize further array expansion)
			cache = Arrays.copyOf(cache, address + 100);
		}

		// get the cached pin object from the cache
		PinCache pc = cache[address];

		// if no pin object is found in the cache, then we need to create one at
		// this address index in the cache array
		if (pc == null) {
			pc = cache[pin.getAddress()] = new PinCache(pin);
		}
		return pc;
	}

	@Override
	public boolean setBoolean(Map<Integer, Boolean> ref_values) {
		// Boolean value may be either a pin state or a component value to set.
		for (Entry<Integer, Boolean> ref_value : ref_values.entrySet()) {
			Pin pin = getPin(ref_value.getKey());
			if (pin != null) {
				PinState state = ref_value.getValue().equals(Boolean.TRUE) ? PinState.HIGH : PinState.LOW;
				PinState oldState = getPinCache(pin).getState();				
				getPinCache(pin).setState(state);
				if (!state.equals(oldState))
					dispatchPinDigitalStateChangeEvent(pin,state);

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
	 * max output pin address (20) // for raspberry A,B
	 * 
	 * @param ref
	 * @return
	 */
	private Pin getPin(Integer ref) {
		//
		for (Pin pin : RaspiPin.allPins()) {
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
		return false;
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

		for (HardwareProperties comp : properties.getAllComponents()) {

			HwEmulation emulationComp = getEmulationInstance(comp);
			if (emulationComp != null)
				list.addAll(emulationComp.getModelVariables());
		}

		return list;
	}

	private HwEmulation getEmulationInstance(HardwareProperties comp) {
		if (classCache.containsKey(comp))
			return classCache.get(comp);

		HwEmulation hardware;
		try {
			hardware = builderFactory.createBuilder(comp).setBaseReference(nextAvailableBase).build();
			nextAvailableBase += hardware.getModelVariables().size();
		} catch (Exception e) {
			logger.error("Cannot build class for Hardware extension named " + comp.getComponentName() + " reason:" + e.getMessage());
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
		for (HardwareProperties comp : properties.getAllComponents()) {
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

	public String getName() {
		return properties.getSimulatedProviderName();
	}

	@Override
	public void addListener(Pin pin, PinListener listener) {
		synchronized (listeners) {
			// create new pin listener entry if one does not already exist
			if (!listeners.containsKey(pin)) {
				listeners.put(pin, new ArrayList<PinListener>());
			}

			// add the listener instance to the listeners map entry
			List<PinListener> lsnrs = listeners.get(pin);
			if (!lsnrs.contains(listener)) {
				lsnrs.add(listener);
			}
		}
	}

	@Override
	public void removeListener(Pin pin, PinListener listener) {
		synchronized (listeners) {
			// lookup to pin entry in the listeners map
			if (listeners.containsKey(pin)) {
				// remote the listener instance from the listeners map entry if
				// found
				List<PinListener> lsnrs = listeners.get(pin);
				if (lsnrs.contains(listener)) {
					lsnrs.remove(listener);
				}

				// if the listener list is empty, then remove the listener pin
				// from the map
				if (lsnrs.isEmpty()) {
					listeners.remove(pin);
				}
			}
		}
	}

	@Override
	public void removeAllListeners() {
		synchronized (listeners) {
			// iterate over all listener pins in the map
			List<Pin> pins_copy = new ArrayList<>(listeners.keySet());
			for (Pin pin : pins_copy) {
				if (listeners.containsKey(pin)) {
					// iterate over all listener handler in the map entry
					// and remove each listener handler instance
					List<PinListener> lsnrs = listeners.get(pin);
					if (!lsnrs.isEmpty()) {
						List<PinListener> lsnrs_copy = new ArrayList<>(lsnrs);
						for (int index = lsnrs_copy.size() - 1; index >= 0; index--) {
							PinListener listener = lsnrs_copy.get(index);
							removeListener(pin, listener);
						}
					}
				}
			}
		}
	}

	protected void dispatchPinDigitalStateChangeEvent(Pin pin, PinState state) {
		// if the pin listeners map contains this pin, then dispatch event
		if (listeners.containsKey(pin)) {
			// dispatch this event to all listener handlers
			for (PinListener listener : listeners.get(pin)) {
				listener.handlePinEvent(new PinDigitalStateChangeEvent(pin, state));
			}
		}
	}

	/***
	 * get userspace control over GPIOs
	 */
	@Override
	public void export(Pin pin, PinMode mode) {
		exportedPin.add(pin);
	}

	/***
	 * get userspace control over GPIOs
	 */
	@Override
	public void export(Pin pin, PinMode arg1, PinState arg2) {
		exportedPin.add(pin);
	}

	/***
	 * remove userspace control over GPIOs
	 */
	@Override
	public void unexport(Pin pin) {
		exportedPin.remove(pin);
	}

	/***
	 * check if userspace has control overthis GPIO'pin
	 */
	@Override
	public boolean isExported(Pin pin) {
		return exportedPin.contains(pin);
	}

	@Override
	public void setPullResistance(Pin pin, PinPullResistance resistance) {
		if (!hasPin(pin)) {
			throw new InvalidPinException(pin);
		}

		if (!pin.getSupportedPinPullResistance().contains(resistance)) {
			throw new UnsupportedPinPullResistanceException(pin, resistance);
		}

		// cache resistance
		getPinCache(pin).setResistance(resistance);
	}

	@Override
	public PinPullResistance getPullResistance(Pin pin) {
		if (!hasPin(pin)) {
			throw new InvalidPinException(pin);
		}

		// return cached resistance
		return getPinCache(pin).getResistance();
	}

	@Override
	public void setPwm(Pin pin, int value) {
		if (!hasPin(pin)) {
			throw new InvalidPinException(pin);
		}

		PinMode mode = getMode(pin);

		if (mode != PinMode.PWM_OUTPUT || mode != PinMode.SOFT_PWM_OUTPUT) {
			throw new InvalidPinModeException(pin, "Invalid pin mode [" + mode.getName() + "]; unable to setPwm(" + value + ")");
		}

		// cache pin PWM value
		getPinCache(pin).setPwmValue(value);
	}

	@Override
	public void setPwmRange(Pin pin, int range) {
		if (!hasPin(pin)) {
			throw new InvalidPinException(pin);
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public int getPwm(Pin pin) {
		if (!hasPin(pin)) {
			throw new InvalidPinException(pin);
		}

		// return cached pin PWM value
		return getPinCache(pin).getPwmValue();
	}

	@Override
	public void setValue(Pin pin, double value) {

		// the getMode() will validate the pin exists with the hasPin() function
		PinMode mode = getMode(pin);

		// this simulator implementation has only digital mode
		throw new InvalidPinModeException(pin,
				"Invalid pin mode on pin [" + pin.getName() + "]; cannot setValue(" + value + ") when pin mode is [" + mode.getName() + "]");

	}

	@Override
	public double getValue(Pin pin) {
		// the getMode() will validate the pin exists with the hasPin() function
		PinMode mode = getMode(pin);

		// this simulator implementation has only digital mode
		throw new InvalidPinModeException(pin, "Invalid pin mode on pin [" + pin.getName() + "]; cannot getValue() when pin mode is [" + mode.getName() + "]");
	}

	@Override
	public void shutdown() {
		// prevent reentrant invocation
		if (isShutdown())
			return;

		// remove all listeners
		removeAllListeners();

		// set shutdown tracking state variable
		isshutdown = true;

	}

	@Override
	public boolean isShutdown() {
		return isshutdown;
	}

}
