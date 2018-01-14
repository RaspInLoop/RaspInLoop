package org.raspinloop.fmi.testtools;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.raspinloop.fmi.HwEmulation;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;

public class FMU {

	public static void setVariable(HwEmulation hardware, String variableName, double d) {
		Integer ref = getVariableRef(hardware, variableName);
		if (ref != null) {
			Map<Integer, Double> ref_values = new HashMap<Integer, Double>();
			ref_values.put(ref, d);
			assertTrue("Cannot set Real Variable for " + variableName, hardware.setReal(ref_values));
		}
	}


	public static void setVariable(HwEmulation hardware, String variableName, int d) {
		Integer ref = getVariableRef(hardware, variableName);
		if (ref != null) {
			Map<Integer, Integer> ref_values = new HashMap<Integer, Integer>();
			ref_values.put(ref, d);
			assertTrue("Cannot set Integer Variable for " + variableName, hardware.setInteger(ref_values));
		}
	}
	
	public static void setVariable(HwEmulation hardware, String variableName, boolean d) {
		Integer ref = getVariableRef(hardware, variableName);
		if (ref != null) {
			Map<Integer, Boolean> ref_values = new HashMap<Integer, Boolean>();
			ref_values.put(ref, d);
			assertTrue("Cannot set Boolean Variable for " + variableName, hardware.setBoolean(ref_values));
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getVariable(HwEmulation hardware, String variableName, Class<T> type) {
		Integer ref = getVariableRef(hardware, variableName);
		if (type.isAssignableFrom(Double.class)) {
			List<Double> value = hardware.getReal(Arrays.asList(ref));
			if (value != null && !value.isEmpty())
				return (T) value.get(0);
		}
		if (type.isAssignableFrom(Integer.class)) {
			List<Integer> value = hardware.getInteger(Arrays.asList(ref));
			if (value != null && !value.isEmpty())
				return (T) value.get(0);
		}
		if (type.isAssignableFrom(Boolean.class)) {
			List<Boolean> value = hardware.getBoolean(Arrays.asList(ref));
			if (value != null && !value.isEmpty())
				return (T) value.get(0);
		}
		return null;
	}
	
	
	private static Map<String, Integer> cache = new HashMap<String, Integer>();

	private static Integer getVariableRef(HwEmulation hardware, String variableName) {
		if (!cache.containsKey(variableName))
		{
			for (Fmi2ScalarVariable modelVariable : hardware.getModelVariables()) {
				if (modelVariable.getName().equals(variableName)){
					cache.put(variableName, (int)modelVariable.getValueReference());
				}
			}
		}
		return cache.get(variableName);
	}
	
}
