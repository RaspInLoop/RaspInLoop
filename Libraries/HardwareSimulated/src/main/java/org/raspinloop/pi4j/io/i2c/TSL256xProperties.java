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
		TSL2561_PACKAGE_CS((byte)1),
		TSL2561_PACKAGE_T_FN_CL((byte)5);
		
		private byte partNo;
		
		PackageType(byte partNo){
			this.partNo = partNo;			
		}

		public byte getPartNo() {
			return partNo;
		}
	}
	
	public enum AddressSelectionPin {
		GND(0x29),
		FLOATING(0x39),
		VDD(0x49);
		
		private int address;

		AddressSelectionPin(int address){
			this.address = address;			
		}
		
		int getAddress(){
			return address;
		}
		
		@Override
		public String toString() {
			return super.toString()+(" (0x"+Integer.toHexString(getAddress())+")");
		}
	}
	
		
	public static final String TYPE = "TLS2560";
	public static final String DISPLAY_NAME = "TSL2560 (LIGHT-TO-DIGITAL CONVERTER)";
	public static final String SIMULATED_PROVIDER_NAME = "TSL2560";
	public static final String IMPLEMENTATION_CLASS_NAME = TSL256x.class.getName();

	private String name = DISPLAY_NAME;

	private byte revNumber = 0x0;
	private int busId = I2CBus.BUS_0;
	private double irBroadbandRatio = 0.12;
	private I2CParent parent;
	private Collection<Pin> availablesPins = Collections.emptyList();
	private PackageType packageType = PackageType.TSL2561_PACKAGE_T_FN_CL; // only this package is supported now
	private AddressSelectionPin selectedAddress = AddressSelectionPin.FLOATING; // default: not connected

	public TSL256xProperties() {
	}
	
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

	public AddressSelectionPin gettSelecteAddress() {
		return selectedAddress;
	}

	//either 0x29 0x39 or àx49
	public void setSelectedAddress(AddressSelectionPin sel) {
		this.selectedAddress = sel;
	}

	public int getPartNumber_ID() {
		return packageType.getPartNo()*0x10 + revNumber;
	}
	
	public PackageType getPackageType(){
		return packageType;
	}

	public void setRevNumber(byte revNumber ) {
		this.revNumber = revNumber;
	}
	
	public byte getRevNumber() {
		return revNumber;
	}

	public double getIrBroadbandRatio() {
		return irBroadbandRatio;
	}

 	//ration CH1/CH0  infrared light / visible light  from 0 to 1.3
	public void setIrBroadbandRatio(double irBroadbandRatio) {
		this.irBroadbandRatio = irBroadbandRatio;
	}

}
