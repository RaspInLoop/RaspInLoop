package org.raspinloop.pi4j.io.i2c;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.raspinloop.config.HardwareProperties;
import org.raspinloop.config.I2CComponent;
import org.raspinloop.config.I2CParent;
import org.raspinloop.config.Pin;
import org.raspinloop.hwemulation.I2CBus;

public class TSL256xProperties implements I2CComponent {

	public enum PackageType{
		TSL2561_PACKAGE_CS,
		TSL2561_PACKAGE_T_FN_CL
	}
	
	public static final String TYPE = "TLS2560";
	public static final String DISPLAY_NAME = "TSL2560 (LIGHT-TO-DIGITAL CONVERTER)";
	public static final String SIMULATED_PROVIDER_NAME = "TSL2560";
	public static final String IMPLEMENTATION_CLASS_NAME = TSL256x.class.getName();

	private String name = DISPLAY_NAME;

	private int address = 0x39; // default: address pin not connected (FLOATING)
	private int partNumber_ID = 0x50;
	private int busId = I2CBus.BUS_0;
	private double irBroadbandRatio = 0.12;
	private I2CParent parent;
	private Collection<Pin> availablesPins = Collections.emptyList();
	private PackageType packageType = PackageType.TSL2561_PACKAGE_T_FN_CL; // only this package is supported now

	@Override
	public String getComponentName() {
		return name;
	}

	@Override
	public HardwareProperties setComponentName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getImplementationClassName() {
		return IMPLEMENTATION_CLASS_NAME;
	}

	@Override
	public String getSimulatedProviderName() {
		return SIMULATED_PROVIDER_NAME;
	}

	@Override
	public Collection<Pin> getUsedPins() {
		List<Pin> used = new LinkedList<>();
		if (busId == I2CBus.BUS_0) {
			for (Pin pin : availablesPins) {
				used.add(pin);
			}
		}
		return used;
	}

	@Override
	public I2CParent getParent() {
		return parent;

	}

	@Override
	public void setParent(I2CParent parent) {
		this.parent = parent;
		availablesPins = parent.getI2CPins();
	}

	public short getBusId() {
		return (short) busId;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public int getPartNumber_ID() {
		return partNumber_ID;
	}

	public void setPartNumber_ID(int partNumber_ID) {
		this.partNumber_ID = partNumber_ID;
	}

	public double getIrBroadbandRatio() {
		return irBroadbandRatio;
	}

	public void setIrBroadbandRatio(double irBroadbandRatio) {
		this.irBroadbandRatio = irBroadbandRatio;
	}

}
