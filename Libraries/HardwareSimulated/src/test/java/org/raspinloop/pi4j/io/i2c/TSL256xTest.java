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

import static org.junit.Assert.assertEquals;
import static org.raspinloop.fmi.testtools.AssertFMI.assertIsInputVariable;
import static org.raspinloop.fmi.testtools.AssertFMI.assertIsRealVariable;
import static org.raspinloop.fmi.testtools.Builder.getBuilderFor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;
import org.raspinloop.fmi.testtools.FMU;
import org.raspinloop.fmi.testtools.HarwareSimulatedTest;
import org.raspinloop.fmi.testtools.RaspInLoopHardwareTestRunner;
import org.raspinloop.pi4j.io.i2c.TSL256xProperties.PackageType;

@RunWith(RaspInLoopHardwareTestRunner.class)
public class TSL256xTest {

	
    // TSL2561 registers
    public static final byte TSL2561_REG_ID = (byte)0x8A;
    public static final byte TSL2561_REG_DATA_0 = (byte)0x8C;
    public static final byte TSL2561_REG_DATA_1 = (byte)0x8E;
    public static final byte TSL2561_REG_CONTROL = (byte)0x80;

    // TSL2561 power control values
    public static final byte TSL2561_POWER_UP = (byte)0x03;
    public static final byte TSL2561_POWER_DOWN = (byte)0x00;
    
    
	@Test
	public void testDescription() {
		TSL256x cut = new TSL256x(); // with default properties
		assertEquals("Should contains only 1 varibale", 1, cut.getModelVariables().size());
		Fmi2ScalarVariable variable = cut.getModelVariables().get(0);
		assertIsInputVariable(variable);
		assertIsRealVariable(variable);				
		assertEquals("TLS2560 lux" , variable.getName());
		assertEquals("TLS2560" , cut.getType());
	}

	@Test
	@HarwareSimulatedTest(hwPropertiesFile="test.json")
	public void testId() {
		TSL256x cut = new TSL256x(getBuilderFor("TSL2560 (LIGHT-TO-DIGITAL CONVERTER)")); //from properties file
		cut.enterInitialize();
		int response = cut.read(TSL2561_REG_ID, 1)[0];
	    assertEquals("TSL2561 ID", 0x50,response);
	}
	
	@Test
	public void testMeasure() {
		TSL256xProperties props = new TSL256xProperties();
		props.setIrBroadbandRatio(0.119);
		TSL256x cut = new TSL256x(getBuilderFor(props)); // with programmatically defined properties
		cut.enterInitialize();
	    /*unit test data from: https://www.elecrow.com/wiki/index.php?title=Luminosity_Sensor-_TSL2561_Breakout
	     * integration will be 402ms
	     * gain = 0
	     * 
	     * data0	data1	lux	ratio
	     *---------------------------
 	     *163	33		62.00	0.202
	     *163	33		62.53	0.202
	     *241	48		92.25	0.199
	     *1194	175		500.23	0.146
	     *1511	180		658.71	0.119
	     *1545	182		674.74	0.118
	     *1496	180		651.11	0.120
	     *1454	177		631.60	0.121
	     *1480	177		644.78	0.120
	     */	 
		
		
		FMU.setVariable(cut, "TLS2560 lux", 658.71);
		
		byte[] data0b = cut.read(TSL2561_REG_DATA_0, 2);
		int data0 =((data0b[0]&0x0ff) + ((data0b[1]&0x0ff) <<8))& 0x0000ffff;
		
		byte[] data1b = cut.read(TSL2561_REG_DATA_1, 2);
	    int data1 = ((data1b[0]&0x0ff) + ((data1b[1]&0x0ff) <<8))& 0x0000ffff;

	    assertEquals("TSL2561 DATA 0 ", 1516, data0 );
	    assertEquals("TSL2561 DATA 1 ", 180, data1);		    
	    
	    assertEquals( 658.71, calculateLux(data0, data1, 402, false, PackageType.TSL2561_PACKAGE_T_FN_CL), 1);
   
	}
	
	@Test
	public void testLux() {
		TSL256xProperties props = new TSL256xProperties();
		props.setIrBroadbandRatio(0.119);
		TSL256x cut = new TSL256x(getBuilderFor(props)); // with programmatically defined properties
		cut.enterInitialize();
		
		for (double lux = 0.0; lux < 1000.0; lux+=50.0){
			FMU.setVariable(cut, "TLS2560 lux", lux);
			byte[] data0b = cut.read(TSL2561_REG_DATA_0, 2);
			int data0 =((data0b[0]&0x0ff) + ((data0b[1]&0x0ff) <<8))& 0x0000ffff;			
			byte[] data1b = cut.read(TSL2561_REG_DATA_1, 2);
		    int data1 = ((data1b[0]&0x0ff) + ((data1b[1]&0x0ff) <<8))& 0x0000ffff;
		    assertEquals(lux, calculateLux(data0, data1, 402, false, PackageType.TSL2561_PACKAGE_T_FN_CL), 1);		    
		}
	}
	
	@Test
	public void testLuxRatio() {
		TSL256xProperties props = new TSL256xProperties();
		props.setIrBroadbandRatio(0.119);
		TSL256x cut = new TSL256x(getBuilderFor(props)); // with programmatically defined properties
		cut.enterInitialize();
		
		for (double ratio = 0.1; ratio < 1.0; ratio+=0.1){
			props.setIrBroadbandRatio(ratio);
			FMU.setVariable(cut, "TLS2560 lux", 10.0);
			byte[] data0b = cut.read(TSL2561_REG_DATA_0, 2);
			int data0 =((data0b[0]&0x0ff) + ((data0b[1]&0x0ff) <<8))& 0x0000ffff;			
			byte[] data1b = cut.read(TSL2561_REG_DATA_1, 2);
		    int data1 = ((data1b[0]&0x0ff) + ((data1b[1]&0x0ff) <<8))& 0x0000ffff;
		    assertEquals("lux should be equals @ratio: "+ratio, 10, calculateLux(data0, data1, 402, false, PackageType.TSL2561_PACKAGE_T_FN_CL), 1);		    
		}
	}
	
	// code inspired of https://github.com/adafruit/TSL2561-Arduino-Library/blob/master/TSL2561.cpp
	private int  TSL2561_LUX_CHSCALE_TINT0 = (0x7517);  // 322/11 * 2^TSL2561_LUX_CHSCALE
	private int  TSL2561_LUX_CHSCALE_TINT1 = (0x0FE7); // 322/81 * 2^TSL2561_LUX_CHSCALE

	private int  TSL2561_LUX_LUXSCALE =     (14);      // Scale by 2^14
	private int  TSL2561_LUX_RATIOSCALE =   (9);       // Scale ratio by 2^9
	private int  TSL2561_LUX_CHSCALE =(10); // Scale channel values by 2^10
	
	// T, FN and CL package values
	private int TSL2561_LUX_K1T    =       (0x0040);  // 0.125 * 2^RATIO_SCALE
	private int TSL2561_LUX_B1T    =       (0x01f2);  // 0.0304 * 2^LUX_SCALE
	private int TSL2561_LUX_M1T    =       (0x01be);  // 0.0272 * 2^LUX_SCALE
	private int TSL2561_LUX_K2T    =       (0x0080);  // 0.250 * 2^RATIO_SCALE
	private int TSL2561_LUX_B2T    =       (0x0214);  // 0.0325 * 2^LUX_SCALE
	private int TSL2561_LUX_M2T    =       (0x02d1);  // 0.0440 * 2^LUX_SCALE
	private int TSL2561_LUX_K3T    =       (0x00c0);  // 0.375 * 2^RATIO_SCALE
	private int TSL2561_LUX_B3T    =       (0x023f);  // 0.0351 * 2^LUX_SCALE
	private int TSL2561_LUX_M3T    =       (0x037b);  // 0.0544 * 2^LUX_SCALE
	private int TSL2561_LUX_K4T    =       (0x0100);  // 0.50 * 2^RATIO_SCALE
	private int TSL2561_LUX_B4T    =       (0x0270);  // 0.0381 * 2^LUX_SCALE
	private int TSL2561_LUX_M4T    =       (0x03fe);  // 0.0624 * 2^LUX_SCALE
	private int TSL2561_LUX_K5T    =       (0x0138);  // 0.61 * 2^RATIO_SCALE
	private int TSL2561_LUX_B5T    =       (0x016f);  // 0.0224 * 2^LUX_SCALE
	private int TSL2561_LUX_M5T    =       (0x01fc);  // 0.0310 * 2^LUX_SCALE
	private int TSL2561_LUX_K6T    =       (0x019a);  // 0.80 * 2^RATIO_SCALE
	private int TSL2561_LUX_B6T    =       (0x00d2);  // 0.0128 * 2^LUX_SCALE
	private int TSL2561_LUX_M6T    =       (0x00fb);  // 0.0153 * 2^LUX_SCALE
	private int TSL2561_LUX_K7T    =       (0x029a); // 1.3 * 2^RATIO_SCALE
	private int TSL2561_LUX_B7T    =       (0x0018);  // 0.00146 * 2^LUX_SCALE
	private int TSL2561_LUX_M7T    =       (0x0012);  // 0.00112 * 2^LUX_SCALE
	private int TSL2561_LUX_K8T    =       (0x029a);  // 1.3 * 2^RATIO_SCALE
	private int TSL2561_LUX_B8T    =       (0x0000);  // 0.000 * 2^LUX_SCALE
	private int TSL2561_LUX_M8T    =       (0x0000);   // 0.000 * 2^LUX_SCALE
	

	// CS package values
	private int TSL2561_LUX_K1C    =       (0x0043);  // 0.130 * 2^RATIO_SCALE
	private int TSL2561_LUX_B1C    =       (0x0204);  // 0.0315 * 2^LUX_SCALE
	private int TSL2561_LUX_M1C    =       (0x01ad);  // 0.0262 * 2^LUX_SCALE
	private int TSL2561_LUX_K2C    =       (0x0085);  // 0.260 * 2^RATIO_SCALE
	private int TSL2561_LUX_B2C    =       (0x0228);  // 0.0337 * 2^LUX_SCALE
	private int TSL2561_LUX_M2C    =       (0x02c1);  // 0.0430 * 2^LUX_SCALE
	private int TSL2561_LUX_K3C    =       (0x00c8);  // 0.390 * 2^RATIO_SCALE
	private int TSL2561_LUX_B3C    =       (0x0253);  // 0.0363 * 2^LUX_SCALE
	private int TSL2561_LUX_M3C    =      (0x0363);  // 0.0529 * 2^LUX_SCALE
	private int TSL2561_LUX_K4C    =       (0x010a);  // 0.520 * 2^RATIO_SCALE
	private int TSL2561_LUX_B4C    =       (0x0282);  // 0.0392 * 2^LUX_SCALE
	private int TSL2561_LUX_M4C    =       (0x03df);  // 0.0605 * 2^LUX_SCALE
	private int TSL2561_LUX_K5C    =       (0x014d);  // 0.65 * 2^RATIO_SCALE
	private int TSL2561_LUX_B5C    =       (0x0177);  // 0.0229 * 2^LUX_SCALE
	private int TSL2561_LUX_M5C    =       (0x01dd);  // 0.0291 * 2^LUX_SCALE
	private int TSL2561_LUX_K6C    =       (0x019a);  // 0.80 * 2^RATIO_SCALE
	private int TSL2561_LUX_B6C    =       (0x0101);  // 0.0157 * 2^LUX_SCALE
	private int TSL2561_LUX_M6C    =       (0x0127);  // 0.0180 * 2^LUX_SCALE
	private int TSL2561_LUX_K7C    =       (0x029a);  // 1.3 * 2^RATIO_SCALE
	private int TSL2561_LUX_B7C    =       (0x0037);  // 0.00338 * 2^LUX_SCALE
	private int TSL2561_LUX_M7C    =       (0x002b);  // 0.00260 * 2^LUX_SCALE
	private int TSL2561_LUX_K8C    =       (0x029a);  // 1.3 * 2^RATIO_SCALE
	private int TSL2561_LUX_B8C    =       (0x0000);  // 0.000 * 2^LUX_SCALE
	private int TSL2561_LUX_M8C    =       (0x0000);  // 0.000 * 2^LUX_SCALE
	private int calculateLux(int ch0, int ch1, int integrationTimeMs, boolean useGain, TSL256xProperties.PackageType pt)
	{
	  long chScale;
	  long channel1;
	  long channel0;

	  if (integrationTimeMs < 14)
		  chScale = TSL2561_LUX_CHSCALE_TINT0;
	  else if (integrationTimeMs < 102)
	  	chScale = TSL2561_LUX_CHSCALE_TINT1;
	  else 
	      chScale = (1 << TSL2561_LUX_CHSCALE);	   

	  // Scale for gain (1x or 16x)
	  if (!useGain) chScale = chScale << 4;

	  // scale the channel values
	  channel0 = (ch0 * chScale) >> TSL2561_LUX_CHSCALE;
	  channel1 = (ch1 * chScale) >> TSL2561_LUX_CHSCALE;

	  // find the ratio of the channel values (Channel1/Channel0)
	  long ratio1 = 0;
	  if (channel0 != 0) ratio1 = (channel1 << (TSL2561_LUX_RATIOSCALE+1)) / channel0;

	  // round the ratio value
	  long ratio = (ratio1 + 1) >> 1;

	  int b =0, m=0;

	if (pt.equals(PackageType.TSL2561_PACKAGE_CS)){
	  if ((ratio >= 0) && (ratio <= TSL2561_LUX_K1C))
	    {b=TSL2561_LUX_B1C; m=TSL2561_LUX_M1C;}
	  else if (ratio <= TSL2561_LUX_K2C)
	    {b=TSL2561_LUX_B2C; m=TSL2561_LUX_M2C;}
	  else if (ratio <= TSL2561_LUX_K3C)
	    {b=TSL2561_LUX_B3C; m=TSL2561_LUX_M3C;}
	  else if (ratio <= TSL2561_LUX_K4C)
	    {b=TSL2561_LUX_B4C; m=TSL2561_LUX_M4C;}
	  else if (ratio <= TSL2561_LUX_K5C)
	    {b=TSL2561_LUX_B5C; m=TSL2561_LUX_M5C;}
	  else if (ratio <= TSL2561_LUX_K6C)
	    {b=TSL2561_LUX_B6C; m=TSL2561_LUX_M6C;}
	  else if (ratio <= TSL2561_LUX_K7C)
	    {b=TSL2561_LUX_B7C; m=TSL2561_LUX_M7C;}
	  else if (ratio > TSL2561_LUX_K8C)
	    {b=TSL2561_LUX_B8C; m=TSL2561_LUX_M8C;}
	}
	else {
	  if ((ratio >= 0) && (ratio <= TSL2561_LUX_K1T))
	    {b=TSL2561_LUX_B1T; m=TSL2561_LUX_M1T;}
	  else if (ratio <= TSL2561_LUX_K2T)
	    {b=TSL2561_LUX_B2T; m=TSL2561_LUX_M2T;}
	  else if (ratio <= TSL2561_LUX_K3T)
	    {b=TSL2561_LUX_B3T; m=TSL2561_LUX_M3T;}
	  else if (ratio <= TSL2561_LUX_K4T)
	    {b=TSL2561_LUX_B4T; m=TSL2561_LUX_M4T;}
	  else if (ratio <= TSL2561_LUX_K5T)
	    {b=TSL2561_LUX_B5T; m=TSL2561_LUX_M5T;}
	  else if (ratio <= TSL2561_LUX_K6T)
	    {b=TSL2561_LUX_B6T; m=TSL2561_LUX_M6T;}
	  else if (ratio <= TSL2561_LUX_K7T)
	    {b=TSL2561_LUX_B7T; m=TSL2561_LUX_M7T;}
	  else if (ratio > TSL2561_LUX_K8T)
	    {b=TSL2561_LUX_B8T; m=TSL2561_LUX_M8T;}
	}

	  long temp;
	  temp = ((channel0 * b) - (channel1 * m));

	  // do not allow negative lux value
	  if (temp < 0) temp = 0;

	  // round lsb (2^(LUX_SCALE-1))
	  temp += (1 << (TSL2561_LUX_LUXSCALE-1));

	  // strip off fractional portion
	  int lux = (int)(temp >> TSL2561_LUX_LUXSCALE);

	  // Signal I2C had no errors
	  return lux;
	}

}
