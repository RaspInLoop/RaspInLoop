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
package org.raspinloop.pi4j.io.spi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.raspinloop.config.HardwareBuilder;
import org.raspinloop.fmi.SpiCompHwEmulation;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable.Real;
import org.raspinloop.hwemulation.SpiEmulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MCP3008 implements SpiCompHwEmulation {

	final static Logger logger = LoggerFactory.getLogger(MCP3008.class);

	private MCP3008Properties properties;
	private int baseref;
	private double[] ch = new double[8];// relative ref 0-7. Input: Voltage to
										// measure
	private double vref;// relative ref 8. Input: Voltage reference for ADC
	// param
	static final int NB_VAR = 9; // too old fashion style, it smells like C

	// code...

	public MCP3008() {
		this.properties = new MCP3008Properties();
		
	}

	public MCP3008(HardwareBuilder builder) {
		if (builder.getProperties() instanceof MCP3008Properties)
			properties = (MCP3008Properties) builder.getProperties();
		baseref = builder.getBaseReference();
	}

	@Override
	public String getHWGuid() {
		return null;
	}

	@Override
	public String getType() {
		return properties.getType();
	}

	@Override
	public List<Fmi2ScalarVariable> getModelVariables() {
		ArrayList<Fmi2ScalarVariable> result = new ArrayList<Fmi2ScalarVariable>(NB_VAR);
		for (int i = 0; i < ch.length; i++) {
			result.add(createRealInput(getType() + " CH" + i, "", baseref + i));
		}
		if (properties.isUseVrefPin())
			result.add(createRealInput(getType() + " Vref", "", baseref + ch.length));
		return result;
	}

	private Fmi2ScalarVariable createRealInput(String name, String descritpion, long ref) {
		Fmi2ScalarVariable sc = new Fmi2ScalarVariable();
		Real scr = new Fmi2ScalarVariable.Real();
		sc.setReal(scr);
		sc.setName(name);
		sc.setValueReference(ref);
		sc.setDescription(descritpion);
		sc.setCausality("input");
		sc.setVariability("continuous");
		return sc;
	}

	@Override
	public boolean enterInitialize() {
		SpiEmulation.INST.registerHwEmulation(properties.getChannel().getChannel(), this);
		if (! properties.isUseVrefPin())
			vref= properties.getVref();
		return true;
	}

	@Override
	public boolean exitInitialize() {
		return true;
	}

	@Override
	public void terminate() {
	}

	@Override
	public void reset() {

	}

	private void setVar(Integer ref, double value) {
		if (ref - baseref < ch.length)
			ch[ref - baseref] = value;
		else if  (ref - baseref == ch.length && properties.isUseVrefPin())
			vref = value;
	}

	private double getVar(Integer ref) {
		if (ref - baseref < ch.length)
			return ch[ref - baseref];
		else if (ref - baseref == ch.length && properties.isUseVrefPin())
			return vref;
		else
			return 0;
	}

	@Override
	public List<Double> getReal(List<Integer> refs) {
		LinkedList<Double> result = new LinkedList<Double>();
		for (Integer ref : refs) {
			result.add(getVar(ref));
		}
		return null;
	}

	@Override
	public List<Integer> getInteger(List<Integer> refs) {
		return Collections.emptyList();
	}

	@Override
	public List<Boolean> getBoolean(List<Integer> refs) {
		return Collections.emptyList();
	}

	@Override
	public boolean setReal(Map<Integer, Double> ref_values) {
		for (Entry<Integer, Double> entry : ref_values.entrySet()) {
			logger.trace("setting real value: " + entry.getKey() + " = " + entry.getValue());
			Double var = getVar(entry.getKey());
			if (var != null) {
				setVar(entry.getKey(), entry.getValue());
			} else {
				logger.warn("ref:" + entry.getKey() + " not used in this component");
			}
		}
		return true;
	}

	@Override
	public boolean setInteger(Map<Integer, Integer> ref_values) {
		return true;
	}

	@Override
	public boolean setBoolean(Map<Integer, Boolean> ref_values) {
		return true;
	}

	@Override
	public boolean isTerminated() {
		return false;
	}

	@Override
	public int spiDataRW(short[] buffer) {
		byte[] bbuffer = new byte[buffer.length];
		for (int i = 0; i < buffer.length; i++) {
			bbuffer[i] = (byte) buffer[i];
		}
		int nbread = spiDataRW(bbuffer);
		for (int i = 0; i < nbread; i++) {
			buffer[i] = bbuffer[i];
		}
		return nbread;
	}

	@Override
	public int spiDataRW(byte[] buffer) {
		if ((buffer[0] & 0b00000001) != 0) {
			boolean single = (buffer[1] & 0b10000000) != 0;
			int channel = (buffer[1] & 0b01110000) >> 4;
			long value = getValue(single, channel);
			buffer[0] = 0;
			buffer[1] = (byte) ((value & 0x3ff) >> 8);
			buffer[2] = (byte) (value & 0xff);
			return buffer.length;
		} else return 0;
	}

	private long getValue(boolean single, int channel) {
		long value = 0;
		if (single) {
			value = Math.round(ch[channel] * 1024.0 / vref);
			if (value >= 1024)
				value = 1024;
			if (value <=0)
				value =0;
		}
		return value;
	}
}
