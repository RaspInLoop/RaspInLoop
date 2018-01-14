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
package org.raspinloop.pi4j.io.components;

import static org.junit.Assert.assertEquals;
import static org.raspinloop.fmi.testtools.AssertFMI.assertIsInputVariable;
import static org.raspinloop.fmi.testtools.AssertFMI.assertIsRealVariable;
import static org.raspinloop.fmi.testtools.Builder.getBuilderFor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import org.junit.Test;
import org.raspinloop.config.Pin;
import org.raspinloop.config.PinImpl;
import org.raspinloop.config.PinMode;
import org.raspinloop.config.PinState;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;
import org.raspinloop.timeemulation.SimulatedTime;

public class SimulatedStepperMotorTest {
	
	static Pin[] pins = new Pin[4];
	static Pin[] pi4jPins = new PinImpl[4];
	static {
	for (int i = 0; i < pins.length; i++) {
		pins[i] = new PinImpl();
		pins[i].setAddress(i);
		pins[i].setName("GPIO " + i);
		pins[i].setProvider("");
		pins[i].getSupportedPinModes().clear();	
		pi4jPins[i] = new PinImpl("", i, "GPIO " + i, EnumSet.of(PinMode.DIGITAL_OUTPUT));
	}
	}

	private int sequenceIndex;
	
	private SimulatedStepperMotorProperties buildProperty() {
		SimulatedStepperMotorProperties properties = new SimulatedStepperMotorProperties();
		properties.setAverageMode(true);				
		properties.setPins(new ArrayList<Pin>(Arrays.asList(pins)));
		return properties;
	}

	
	@Test
	public void testGetModelVariables() {
		SimulatedStepperMotor dut = new SimulatedStepperMotor(getBuilderFor(buildProperty()));
		
		List<Fmi2ScalarVariable> variables = dut.getModelVariables();
		
		assertEquals(2,variables.size() );
		
		Fmi2ScalarVariable outputVar = variables.get(0);
		
		assertIsRealVariable(outputVar);			
		assertEquals("simulatedStepperMotor position",outputVar.getName() );
		assertEquals("output",outputVar.getCausality() );
		assertEquals(0,outputVar.getValueReference());
		assertEquals("continuous",outputVar.getVariability());
		
		Fmi2ScalarVariable inputVar = variables.get(1);
		
		assertEquals("simulatedStepperMotor torque",inputVar.getName() );
		
		assertEquals(1,inputVar.getValueReference());
		assertEquals("continuous",inputVar.getVariability());		
		
		assertIsInputVariable(inputVar);
	}

	
	@Test
	public void testInitals() throws Exception {

		SimulatedStepperMotor dut = new SimulatedStepperMotor(getBuilderFor(buildProperty().setInitalPosition(0.5)));
		dut.enterInitialize();
		
		List<Double> values = dut.getReal(Arrays.asList(0,1));
		assertEquals((Double)0.5,values.get(0)); // position
		
		dut = new SimulatedStepperMotor(getBuilderFor(buildProperty().setInitalPosition(345)));
		dut.enterInitialize();
		
		values = dut.getReal(Arrays.asList(0,1));
		assertEquals((Double)345.0,values.get(0)); // position
		assertEquals((Double)0.0,values.get(1)); // torque
	}
	
	@Test
	public void testStep() throws Exception {
		SimulatedStepperMotor dut = new SimulatedStepperMotor(getBuilderFor(buildProperty().setStepsPerRotation(1024)));
		
		assertPosition(0.0, dut);
		
		dut.setState(pi4jPins[0] , PinState.LOW);
		dut.setState(pi4jPins[1] , PinState.LOW);
		dut.setState(pi4jPins[2] , PinState.LOW);
		dut.setState(pi4jPins[3] , PinState.LOW);
		dut.onRequestingSleep(2*1000*1000*1000); // simulate sleep of 2ms
		
		dut.setState(pi4jPins[0] , PinState.HIGH);
		dut.setState(pi4jPins[1] , PinState.LOW);
		dut.setState(pi4jPins[2] , PinState.LOW);
		dut.setState(pi4jPins[3] , PinState.LOW);
		dut.onRequestingSleep(2*1000*1000*1000); // simulate sleep of 2ms		
		
		dut.setState(pi4jPins[0] , PinState.LOW);
		dut.setState(pi4jPins[1] , PinState.HIGH);
		dut.setState(pi4jPins[2] , PinState.LOW);
		dut.setState(pi4jPins[3] , PinState.LOW);
		dut.onRequestingSleep(2*1000*1000*1000); // simulate sleep of 2ms		

		dut.setState(pi4jPins[0] , PinState.LOW);
		dut.setState(pi4jPins[1] , PinState.LOW);
		dut.setState(pi4jPins[2] , PinState.HIGH);
		dut.setState(pi4jPins[3] , PinState.LOW);
		dut.onRequestingSleep(2*1000*1000*1000); // simulate sleep of 2ms		

		dut.setState(pi4jPins[0] , PinState.LOW);
		dut.setState(pi4jPins[1] , PinState.LOW);
		dut.setState(pi4jPins[2] , PinState.LOW);
		dut.setState(pi4jPins[3] , PinState.HIGH);
		dut.onRequestingSleep(2*1000*1000*1000); // simulate sleep of 2ms		
		
		dut.setState(pi4jPins[0] , PinState.HIGH);
		dut.setState(pi4jPins[1] , PinState.LOW);
		dut.setState(pi4jPins[2] , PinState.LOW);
		dut.setState(pi4jPins[3] , PinState.LOW);
		dut.onRequestingSleep(2*1000*1000*1000); // simulate sleep of 2ms
		
		assertPosition(4*360.0/1024.0, dut);
	}

	@Test
	public void testSteps() throws Exception {
		SimulatedStepperMotor dut = new SimulatedStepperMotor(getBuilderFor(buildProperty().setStepsPerRotation(1024)));
		
		byte[] single_step_sequence = new byte[4];
        single_step_sequence[0] = (byte) 0b0001;  
        single_step_sequence[1] = (byte) 0b0010;
        single_step_sequence[2] = (byte) 0b0100;
        single_step_sequence[3] = (byte) 0b1000;

        for (int i = 0; i < single_step_sequence.length+1; i++) {
        	 doStep(dut, true, single_step_sequence, PinState.HIGH, PinState.LOW);			
		}
       
		assertPosition(single_step_sequence.length*360.0/1024.0, dut);
	}
		
	@Test
	public void testDoubleSteps() throws Exception {
		SimulatedStepperMotor dut =new SimulatedStepperMotor(getBuilderFor(buildProperty().setStepsPerRotation(1024)));
		
		   byte[] double_step_sequence = new byte[4];
	        double_step_sequence[0] = (byte) 0b0011;  
	        double_step_sequence[1] = (byte) 0b0110;
	        double_step_sequence[2] = (byte) 0b1100;
	        double_step_sequence[3] = (byte) 0b1001;

        for (int i = 0; i < double_step_sequence.length+1; i++) {
        	 doStep(dut, true, double_step_sequence, PinState.HIGH, PinState.LOW);			
		}
       
		assertPosition(double_step_sequence.length*360.0/1024.0, dut);
	}
	
	@Test
	public void testHalfSteps() throws Exception {
		SimulatedStepperMotor dut = new SimulatedStepperMotor(getBuilderFor(buildProperty().setStepsPerRotation(1024)));
		
		   byte[] half_step_sequence = new byte[8];
		   half_step_sequence[0] = (byte) 0b0001;  
	        half_step_sequence[1] = (byte) 0b0011;
	        half_step_sequence[2] = (byte) 0b0010;
	        half_step_sequence[3] = (byte) 0b0110;
	        half_step_sequence[4] = (byte) 0b0100;
	        half_step_sequence[5] = (byte) 0b1100;
	        half_step_sequence[6] = (byte) 0b1000;
	        half_step_sequence[7] = (byte) 0b1001;

        for (int i = 0; i < half_step_sequence.length+1; i++) {
        	 doStep(dut, true, half_step_sequence, PinState.HIGH, PinState.LOW);			
		}
       
		assertPosition(half_step_sequence.length
				*360.0/1024.0, dut);
	}
	
	@Test
	public void testHalfStepsLoop() throws Exception {
		SimulatedStepperMotor dut = new SimulatedStepperMotor(getBuilderFor(buildProperty().setStepsPerRotation(1024)));
		
		   byte[] half_step_sequence = new byte[8];
		   half_step_sequence[0] = (byte) 0b0001;  
	        half_step_sequence[1] = (byte) 0b0011;
	        half_step_sequence[2] = (byte) 0b0010;
	        half_step_sequence[3] = (byte) 0b0110;
	        half_step_sequence[4] = (byte) 0b0100;
	        half_step_sequence[5] = (byte) 0b1100;
	        half_step_sequence[6] = (byte) 0b1000;
	        half_step_sequence[7] = (byte) 0b1001;

        for (int i = 0; i < 1024+1; i++) {
        	 doStep(dut, true, half_step_sequence, PinState.HIGH, PinState.LOW);			
		}       
		assertPosition(360.0, dut);
	}
	
	@Test
	public void testHalfStepsbackAndForward() throws Exception {
		SimulatedStepperMotor dut = new SimulatedStepperMotor(getBuilderFor(buildProperty().setStepsPerRotation(1024)));
		
		   byte[] half_step_sequence = new byte[8];
		   half_step_sequence[0] = (byte) 0b0001;  
	        half_step_sequence[1] = (byte) 0b0011;
	        half_step_sequence[2] = (byte) 0b0010;
	        half_step_sequence[3] = (byte) 0b0110;
	        half_step_sequence[4] = (byte) 0b0100;
	        half_step_sequence[5] = (byte) 0b1100;
	        half_step_sequence[6] = (byte) 0b1000;
	        half_step_sequence[7] = (byte) 0b1001;

        for (int i = 0; i < 1024+1; i++) {
        	 doStep(dut, false, half_step_sequence, PinState.HIGH, PinState.LOW);			
		}
        
        for (int i = 0; i < 1024; i++) {
       	 doStep(dut, true, half_step_sequence, PinState.HIGH, PinState.LOW);			
		}
       
		assertPosition(0.0, dut);
	}
	
	private void assertPosition(double pos, SimulatedStepperMotor dut) {
		List<Double> values = dut.getReal(Arrays.asList(0));
		assertEquals((Double)pos,values.get(0)); // position
	}
	
	private void doStep(SimulatedStepperMotor dut, boolean forward,  byte[] stepSequence, PinState onState, PinState offState ) throws InterruptedException
	{
		 // increment or decrement sequence
        if(forward)
            sequenceIndex++;
        else
            sequenceIndex--;
        
        // check sequence bounds; rollover if needed
        if(sequenceIndex >= stepSequence.length)
            sequenceIndex = 0;
        else if(sequenceIndex < 0)
            sequenceIndex = (stepSequence.length - 1);
		 // start cycling GPIO pins to move the motor forward or reverse
        for(int pinIndex = 0; pinIndex < pins.length; pinIndex++) {
            // apply step sequence 
            double nib = Math.pow(2, pinIndex);
            if((stepSequence[sequenceIndex] & (int)nib) > 0)
                dut.setState(pi4jPins[pinIndex], onState);
            else
            	dut.setState(pi4jPins[pinIndex], offState);
        }             
        SimulatedTime.sleep(100);
	}
}
