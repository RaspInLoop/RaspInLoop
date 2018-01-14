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
package org.raspinloop.pi4j.io.i2c;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.raspinloop.config.HardwareBuilder;
import org.raspinloop.fmi.I2CCompHwEmulation;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable.Real;
import org.raspinloop.hwemulation.I2CEmulation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TSL256x implements I2CCompHwEmulation {

	enum Gain {
		LOW, HIGH
	};

	final static Logger logger = LoggerFactory.getLogger(TSL256x.class);
	private I2CProtocolHandler i2cProtoHanlder;
	TSL256xProperties properties;

	private int baseref;
	private double lux;
	protected Gain gain;
	protected boolean powered;
	protected int data0;
	protected int data1;

	static final int NB_VAR = 1;

	public TSL256x() {
		this.properties = new TSL256xProperties();
		i2cProtoHanlder = new I2CProtocolHandler(getRegisterBank());
	}

	public TSL256x(HardwareBuilder builder) {
		if (builder.getProperties() instanceof TSL256xProperties)
			properties = (TSL256xProperties) builder.getProperties();
		baseref = builder.getBaseReference();
		i2cProtoHanlder = new I2CProtocolHandler(getRegisterBank());
	}

	@Override
	public String getHWGuid() {
		// Only for Base board
		return null;
	}

	@Override
	public String getType() {
		return properties.getType();
	}

	@Override
	public List<Fmi2ScalarVariable> getModelVariables() {
		ArrayList<Fmi2ScalarVariable> result = new ArrayList<Fmi2ScalarVariable>(NB_VAR);
		result.add(createRealInput(getType() + " lux", "", baseref));
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
		try {
			I2CEmulation.registerHwEmulation(properties.getBusId(), this);
			return true;
		} catch (Exception e) {
			logger.error("Cannot register " + properties.getType());
			return false;
		}

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
		if (ref - baseref == 0) {
			lux = value;
			startAquisition();
		}
	}

	private double getVar(Integer ref) {
		if (ref - baseref == 0)
			return lux;
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
			setVar(entry.getKey(), entry.getValue());
		}
		return true;
	}

	@Override
	public boolean setInteger(Map<Integer, Integer> ref_values) {
		// No Integer variable in this component
		return false;
	}

	@Override
	public boolean setBoolean(Map<Integer, Boolean> ref_values) {
		// No Boolean variable in this component
		return false;
	}

	@Override
	public boolean isTerminated() {
		return false;
	}

	@Override
	public Integer getAddress() {
		return properties.gettSelecteAddress().getAddress();
	}

	@Override
	public boolean write(byte[] buffer) {
		return i2cProtoHanlder.write(buffer);
	}

	@Override
	public boolean write(int address, byte[] buffer) {
		return i2cProtoHanlder.write(address, buffer);
	}

	@Override
	public byte[] read(int size) {
		return i2cProtoHanlder.read(size);
	}

	@Override
	public byte[] read(int address, int size) {
		return i2cProtoHanlder.read(address, size);
	}

	IRegister controlRegister = new Register("CONTROL", (byte) 0) {

		@Override
		public byte read() {
			if (powered)
				return (byte) 0x03;
			else
				return (byte) 0x00;
		}

		@Override
		public void write(byte value) {
			if ((value & 0x03) == 0)
				stopAquisition();
			else if ((value & (byte) 0x03) == 0x03)
				startAquisition();
		}
	};
	protected double integrationTimeMs;
	protected double scale = 1;
	protected BitSet timmingbs;

	IRegister timmingRegister = new Register("TIMMING", (byte) 0x01) {
		@Override
		public byte read() {
			return timmingbs.toByteArray()[0];
		}

		@Override
		public void write(byte value) {
			timmingbs = BitSet.valueOf(new byte[] { value });
			gain = (timmingbs.get(4)) ? Gain.HIGH : Gain.LOW;
			integrationTimeMs = 0.0;
			scale = 0.0;
			if (!timmingbs.get(1)) {
				if (!timmingbs.get(0)) {
					integrationTimeMs = 13.7;
					scale = 0.034;
				} else {
					integrationTimeMs = 101;
					scale = 0.252;
				}
			} else {
				if (!timmingbs.get(0)) {
					integrationTimeMs = 402;
					scale = 1;
				}
			}
			if (scale <= 0 && integrationTimeMs <= 0.0) {
				if (timmingbs.get(3))
					startIntegration();
				else
					stopIntegration();
			}
		}
	};

	IRegister idRegister = new RoRegister("ID", (byte) 0x0A) {
		@Override
		public byte read() {
			return (byte) properties.getPartNumber_ID();
		}
	};

	IRegister data0LowRegister = new RoRegister("DATA0LOW", (byte) 0x0C) {
		@Override
		public byte read() {
			if (data0 > 0xffff)
				return (byte) 0xff; // saturation
			return (byte) (data0 & 0x00ff);
		}
	};

	IRegister data0HighRegister = new RoRegister("DATA0HIGH", (byte) 0x0D) {
		@Override
		public byte read() {
			if (data0 > 0xffff)
				return (byte) 0xff; // saturation
			return (byte) ((data0 & 0xff00) >> 8);
		}
	};

	IRegister data1LowRegister = new RoRegister("DATA1LOW", (byte) 0x0E) {
		@Override
		public byte read() {
			if (data1 > 0xffff)
				return (byte) 0xff; // saturation
			return (byte) (data1 & 0x00ff);
		}
	};

	IRegister data1HighRegister = new RoRegister("DATA1HIGH", (byte) 0x0F) {
		@Override
		public byte read() {
			if (data1 > 0xffff)
				return (byte) 0xff; // saturation
			return (byte) ((data1 & 0xff00) >> 8);
		}
	};

	private RegisterBank getRegisterBank() {
		RegisterBank rb = new RegisterBank().add(controlRegister).add(timmingRegister).add(idRegister).add(data0LowRegister).add(data0HighRegister)
				.add(data1LowRegister).add(data1HighRegister);

		return rb;
	}

	protected void stopIntegration() {
		// TODO Auto-generated method stub

	}

	protected void startIntegration() {
		// TODO Auto-generated method stub

	}

	protected void stopAquisition() {
		// TODO Auto-generated method stub

	}

	protected void startAquisition() {
		// we do not really integrate, we have to compute data0 and data1 base
		// on provided lux value.

		// computation based on TSL256x.pdf from TAOS (www.taosinc.com)
		double ratio = properties.getIrBroadbandRatio();
		double b = getB(ratio);
		double m = getM(ratio);
		double channel0 = lux / (b - (m * ratio));
		double channel1 = ratio * channel0;

		long chScale = 1 << 4;

		if (gain == Gain.HIGH)
			chScale = chScale << 4;

		data0 = (int) Math.round((channel0) / chScale * scale) & 0x0ffffffff;
		data1 = (int) Math.round((channel1) / chScale * scale) & 0x0ffffffff;

	}

	// T, FN and CL package values
	private static double TSL2561_LUX_K1T = 0.125; // (0x0040); // 0.125 *
													// 2^RATIO_SCALE
	private static double TSL2561_LUX_B1T = 0.0304; // (0x01f2); // 0.0304 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_M1T = 0.0272;// (0x01be); // 0.0272 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_K2T = 0.250;// (0x0080); // 0.250 *
													// 2^RATIO_SCALE
	private static double TSL2561_LUX_B2T = 0.0325;// (0x0214); // 0.0325 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_M2T = 0.0440;// (0x02d1); // 0.0440 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_K3T = 0.375;// (0x00c0); // 0.375 *
													// 2^RATIO_SCALE
	private static double TSL2561_LUX_B3T = 0.0351;// (0x023f); // 0.0351 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_M3T = 0.0544;// (0x037b); // 0.0544 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_K4T = 0.5;// (0x0100); // 0.50 *
												// 2^RATIO_SCALE
	private static double TSL2561_LUX_B4T = 0.0381;// (0x0270); // 0.0381 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_M4T = 0.0624;// (0x03fe); // 0.0624 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_K5T = 0.61;// (0x0138); // 0.61 *
													// 2^RATIO_SCALE
	private static double TSL2561_LUX_B5T = 0.0224;// (0x016f); // 0.0224 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_M5T = 0.0310;// (0x01fc); // 0.0310 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_K6T = 0.8;// (0x019a); // 0.80 *
												// 2^RATIO_SCALE
	private static double TSL2561_LUX_B6T = 0.0128;// (0x00d2); // 0.0128 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_M6T = 0.0153;// (0x00fb); // 0.0153 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_K7T = 1.3;// (0x029a); // 1.3 *
												// 2^RATIO_SCALE
	private static double TSL2561_LUX_B7T = 0.00146;// (0x0018); // 0.00146 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_M7T = 0.00112;// (0x0012); // 0.00112 *
													// 2^LUX_SCALE
	private static double TSL2561_LUX_K8T = 1.3;// (0x029a); // 1.3 *
												// 2^RATIO_SCALE
	private static double TSL2561_LUX_B8T = 0.0;// (0x0000); // 0.000 *
												// 2^LUX_SCALE
	private static double TSL2561_LUX_M8T = 0.0;// (0x0000); // 0.000 *
												// 2^LUX_SCALE

	private double getM(double ratio) {
		if ((ratio >= 0) && (ratio <= TSL2561_LUX_K1T))
			return TSL2561_LUX_M1T;
		else if (ratio <= TSL2561_LUX_K2T)
			return TSL2561_LUX_M2T;
		else if (ratio <= TSL2561_LUX_K3T)
			return TSL2561_LUX_M3T;
		else if (ratio <= TSL2561_LUX_K4T)
			return TSL2561_LUX_M4T;
		else if (ratio <= TSL2561_LUX_K5T)
			return TSL2561_LUX_M5T;
		else if (ratio <= TSL2561_LUX_K6T)
			return TSL2561_LUX_M6T;
		else if (ratio <= TSL2561_LUX_K7T)
			return TSL2561_LUX_M7T;
		else if (ratio > TSL2561_LUX_K8T)
			return TSL2561_LUX_M8T;
		return 0;
	}

	private double getB(double ratio) {
		if ((ratio >= 0) && (ratio <= TSL2561_LUX_K1T))
			return TSL2561_LUX_B1T;
		else if (ratio <= TSL2561_LUX_K2T)
			return TSL2561_LUX_B2T;
		else if (ratio <= TSL2561_LUX_K3T)
			return TSL2561_LUX_B3T;
		else if (ratio <= TSL2561_LUX_K4T)
			return TSL2561_LUX_B4T;
		else if (ratio <= TSL2561_LUX_K5T)
			return TSL2561_LUX_B5T;
		else if (ratio <= TSL2561_LUX_K6T)
			return TSL2561_LUX_B6T;
		else if (ratio <= TSL2561_LUX_K7T)
			return TSL2561_LUX_B7T;
		else if (ratio > TSL2561_LUX_K8T)
			return TSL2561_LUX_B8T;
		return 0;
	}

}
