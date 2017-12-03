package org.raspinloop.pi4j.io.gpio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.raspinloop.config.HardwareBuilder;
import org.raspinloop.config.HardwareBuilderFactory;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.config.Pin;
import org.raspinloop.config.PinEdge;
import org.raspinloop.config.PinImpl;
import org.raspinloop.config.PinMode;
import org.raspinloop.config.PinPullResistance;
import org.raspinloop.config.PinState;
import org.raspinloop.fmi.GpioCompHwEmulation;
import org.raspinloop.fmi.HwEmulation;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;
import org.raspinloop.hwemulation.GpioProviderHwEmulation;
import org.raspinloop.hwemulation.PinListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class RaspiGpioSimulator  implements GpioProviderHwEmulation {

	static public final String GUID = RaspiGpioSimulatorProperties.GUID;

	final static Logger logger = LoggerFactory.getLogger(RaspiGpioSimulator.class);

	private static final int DEFAULT_CACHE_SIZE = 100;

	protected final Map<Pin, PinEdge> edgeDetectionCache = new ConcurrentHashMap<Pin, PinEdge>();

	private RaspiGpioSimulatorProperties properties = new RaspiGpioSimulatorProperties();

	private HardwareBuilderFactory builderFactory;

	private Map<HardwareProperties, HwEmulation> classCache = new HashMap<HardwareProperties, HwEmulation>();

    protected PinImpl[] cache = new PinImpl[DEFAULT_CACHE_SIZE];
	
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
			
		for (HardwareProperties comp : properties.getAllComponents()) {			
				HwEmulation emulationComp = getEmulationInstance(comp);
					if (emulationComp instanceof GpioCompHwEmulation) {
						GpioCompHwEmulation compHwE = ((GpioCompHwEmulation)emulationComp);
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
		return null;

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
	   
	   protected PinImpl getPinCache(Pin pin) {

	        int address = pin.getAddress();

	        // dynamically resize pin cache storage if needed based on pin address
	        if(address > cache.length){
	            // create a new array with existing contents
	            // that is 100 elements larger than the requested address
	            // (we add the extra 100 elements to provide additional overhead capacity in
	            //  an attempt to minimize further array expansion)
	            cache = Arrays.copyOf(cache, address + 100);
	        }

	        // get the cached pin object from the cache
	        PinImpl pc = cache[address];

	        // if no pin object is found in the cache, then we need to create one at this address index in the cache array
	        if(pc == null){
	            pc = cache[pin.getAddress()] = new PinImpl(pin);
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
					getPinCache(pin).setState(state);
				
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
			hardware = builderFactory.createBuilder(comp).setBaseReference(nextAvailableBase ).build();
			nextAvailableBase+=hardware.getModelVariables().size();
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
	public void addListener(Pin arg0, PinListener arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void export(Pin arg0, PinMode arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void export(Pin arg0, PinMode arg1, PinState arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PinPullResistance getPullResistance(Pin arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPwm(Pin arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getValue(Pin arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isExported(Pin arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isShutdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeAllListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(Pin arg0, PinListener arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPullResistance(Pin arg0, PinPullResistance arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPwm(Pin arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPwmRange(Pin arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(Pin arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unexport(Pin arg0) {
		// TODO Auto-generated method stub
		
	}

}
