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

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.raspinloop.config.AlreadyUsedPin;
import org.raspinloop.config.HardwareBuilder;
import org.raspinloop.config.Pin;
import org.raspinloop.config.PinImpl;
import org.raspinloop.config.PinMode;
import org.raspinloop.config.PinState;
import org.raspinloop.fmi.GpioCompHwEmulation;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable.Real;
import org.raspinloop.pi4j.io.gpio.PinCache;
import org.raspinloop.timeemulation.SimulatedTime;
import org.raspinloop.timeemulation.SimulatedTimeListerner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimulatedStepperMotor implements GpioCompHwEmulation, SimulatedTimeListerner {

	final static Logger logger = LoggerFactory.getLogger(SimulatedStepperMotor.class);		 

	private double position; // relative ref 0
	private double resistantTorque; // relative ref 1. Input: opposite torque
									// from the system on the shaft
	// param
	static final int NB_VAR = 2; // too old fashion style, it smells like C
									// code...

	
	private int baseref;
	protected final Map<org.raspinloop.config.Pin, PinCache> cachedPins = new ConcurrentHashMap<>();

	private PinState onState;
	@SuppressWarnings("unused")
	private PinState offState;

	Map<Byte, Integer> single_step_sequence = createSingleStepSeq();
	Map<Byte, Integer> double_step_sequence = createDoubleStepSeq();
	Map<Byte, Integer> half_step_sequence = createHalfStepSeq();

	public SimulatedStepperMotor(HardwareBuilder builder) {
		SimulatedTime.INST.addRequestingTimeListener(this);
		if (builder.getProperties() instanceof SimulatedStepperMotorProperties)
			properties = (SimulatedStepperMotorProperties) builder.getProperties();
		baseref = builder.getBaseReference();
		int pinIdx = 0;
		Iterator<org.raspinloop.config.Pin> it = properties.getUsedPins().iterator();
		while (it.hasNext() && pinIdx++ < 4) {
			org.raspinloop.config.Pin configuredPin = (org.raspinloop.config.Pin) it.next();
			cachedPins.put(configuredPin, new PinCache( new PinImpl(configuredPin.getProvider(), 
					configuredPin.getAddress(), configuredPin.getName(), EnumSet.of(PinMode.DIGITAL_OUTPUT))));
			if (properties.isAverageMode()) {
				// only if in Average mode
				SimulatedTime.INST.RegisterWaitingThreshold(SimulatedStepperMotor.class.getCanonicalName(), 100 * 1000000/*																										 */);
			}
		}
		onState = properties.getOnState() == org.raspinloop.config.PinState.HIGH ? PinState.HIGH : PinState.LOW;
		offState = properties.getOffState() == org.raspinloop.config.PinState.HIGH ? PinState.HIGH : PinState.LOW;
	}

	private Map<Byte, Integer> createHalfStepSeq() {
		HashMap<Byte, Integer> sequence = new HashMap<Byte, Integer>(8);
		sequence.put((byte) 0b0001, 0);
		sequence.put((byte) 0b0011, 1);
		sequence.put((byte) 0b0010, 2);
		sequence.put((byte) 0b0110, 3);
		sequence.put((byte) 0b0100, 4);
		sequence.put((byte) 0b1100, 5);
		sequence.put((byte) 0b1000, 6);
		sequence.put((byte) 0b1001, 7);
		return sequence;
	}

	private Map<Byte, Integer> createDoubleStepSeq() {
		HashMap<Byte, Integer> sequence = new HashMap<Byte, Integer>(4);
		sequence.put((byte) 0b0011, 0);
		sequence.put((byte) 0b0110, 1);
		sequence.put((byte) 0b1100, 2);
		sequence.put((byte) 0b1001, 3);
		return sequence;
	}

	private Map<Byte, Integer> createSingleStepSeq() {
		HashMap<Byte, Integer> sequence = new HashMap<Byte, Integer>(4);
		sequence.put((byte) 0b0001, 0);
		sequence.put((byte) 0b0010, 1);
		sequence.put((byte) 0b0100, 2);
		sequence.put((byte) 0b1000, 3);
		return sequence;
	}

	private SimulatedStepperMotorProperties properties;

	//will be used in step mode (opposite of average mode)
	@SuppressWarnings("unused")
	private Long previousChangeTime = 0L;

	private byte previousBinaryState;

	private double stepInc;


	private Double getVar(Integer ref) {
		switch (ref - baseref) {
		case 0:
			return position;
		case 1:
			return resistantTorque;
		default:
			return null;
		}
	}
	
	private void setVar(Integer ref, double value) {
		switch (ref - baseref) {
		case 0:
			position = value;
		case 1:
			resistantTorque = value;
		}
	}

	public SimulatedStepperMotor() {
		this.properties = new SimulatedStepperMotorProperties();
	}

	public SimulatedStepperMotor(org.raspinloop.config.PinState onState, org.raspinloop.config.PinState offState) throws AlreadyUsedPin {
		properties.setOnState(onState);
		properties.setOffState(offState);
	}



	public boolean enterInitialize() {
		previousChangeTime = 0L;
		position = properties.getInitalPosition();
		return true;
	}

	public boolean exitInitialize() {
		return true;
	}

	public void terminate() {
	}

	public void reset() {
		previousChangeTime = 0L;
	}

	public List<Double> getReal(List<Integer> refs) {
		List<Double> result = new ArrayList<Double>(refs.size());
		for (Integer ref : refs) {
			Double var = getVar(ref);
			if (var != null) {
				result.add(var);
			} else {
				logger.warn("ref:" + ref + " not used in this stepper motor component");
				result.add(0.0); // Invalid value defined for real ?
			}
		}
		return result;
	}

	public List<Integer> getInteger(List<Integer> refs) {
		return Collections.emptyList();
	}

	public List<Boolean> getBoolean(List<Integer> refs) {
		return Collections.emptyList();
	}

	public boolean setReal(Map<Integer, Double> ref_values) {
		
		for (Entry<Integer, Double> entry : ref_values.entrySet()) {
			logger.info("setting real value: "+ entry.getKey()+ " = "+ entry.getValue());
			Double var = getVar(entry.getKey());
			if (var != null) {
				setVar(entry.getKey(), entry.getValue());				
			} else {
				logger.warn("ref:" + entry.getKey() + " not used in this stepper motor component");
			}
		}
		return true;
	}

	public boolean setInteger(Map<Integer, Integer> ref_values) {
		return true;
	}

	public boolean setBoolean(Map<Integer, Boolean> ref_values) {
		return true;
	}

	public boolean isTerminated() {
		return false;
	}

	public List<Fmi2ScalarVariable> getModelVariables() {
		ArrayList<Fmi2ScalarVariable> result = new ArrayList<Fmi2ScalarVariable>(NB_VAR);
		result.add(createRealOutput(getType() + " position", "", baseref + 0));
		result.add(createRealInput(getType() + " torque", "", baseref + 1));
		return result;
	}

	private Fmi2ScalarVariable createRealOutput(String name, String descritpion, long ref) {
		Fmi2ScalarVariable sc = new Fmi2ScalarVariable();
		Real scr = new Fmi2ScalarVariable.Real();
		sc.setReal(scr);
		sc.setName(name);
		sc.setValueReference(ref);
		sc.setDescription(descritpion);
		sc.setCausality("output");
		sc.setVariability("continuous");
		return sc;
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

	// @Override
	public int registerBaseref(int baseref) {
		this.baseref = baseref;
		return NB_VAR;
	}

	@Override
	public String getType() {
		return properties.getType();
	}

	@Override
	public PinState getState(Pin pin) {
		org.raspinloop.config.Pin raspConfigPin = getPin(pin);
		if (raspConfigPin != null) {
			return cachedPins.get(raspConfigPin).getState();
		}
		return PinState.LOW;
	}

	
	@Override
	public void setState(Pin pin, PinState state) {
		logger.info("SimulatedStepperMotor state set for "+pin+" "+state );
		org.raspinloop.config.Pin raspConfigPin = getPin(pin);
		if (raspConfigPin != null) {
			cachedPins.get(raspConfigPin).setState(state);
		}
		else
			logger.warn("SimulatedStepperMotor pin not found "+pin);
	}

	@Override
	public void onRequestingSleep(long requestedNanos) {
		if (requestedNanos > 0)
			doStep();
	}
	
	private void doStep() {
		Long currentTime = SimulatedTime.INST.getRequestingTime();
		byte currentBinaryState = getBinaryState();
		if (logger.isTraceEnabled())
			logger.info("Do Step : previous["+previousBinaryState+"] current["+currentBinaryState+"] @ "+currentTime);
		if (single_step_sequence.containsKey(previousBinaryState) && single_step_sequence.containsKey(currentBinaryState)) {
			// nominal torque and current
			if (doStep(single_step_sequence.get(previousBinaryState), single_step_sequence.get(currentBinaryState), single_step_sequence.size())){
				logger.info("Stepping in single_step_sequence"+previousBinaryState+"->"+currentBinaryState+"  @"+currentTime );
				previousBinaryState = currentBinaryState;
				previousChangeTime = currentTime;			
			}
				
		} else if ( double_step_sequence.containsKey(previousBinaryState) && double_step_sequence.containsKey(currentBinaryState)) {
			// double torque and current
			if (doStep(double_step_sequence.get(previousBinaryState), double_step_sequence.get(currentBinaryState), double_step_sequence.size())){
				logger.info("Stepping in double_step_sequence"+previousBinaryState+"->"+currentBinaryState+"  @"+currentTime );
				previousBinaryState = currentBinaryState;
				previousChangeTime = currentTime;			
			}
		} else if ( half_step_sequence.containsKey(previousBinaryState) && half_step_sequence.containsKey(currentBinaryState)) {
			// double torque and current
			if (doStep(half_step_sequence.get(previousBinaryState), half_step_sequence.get(currentBinaryState), half_step_sequence.size())){
				logger.info("Stepping in half_step_sequence"+previousBinaryState+"->"+currentBinaryState+"  @"+currentTime );
				previousBinaryState = currentBinaryState;
				previousChangeTime = currentTime;			
			}
		} else if (previousBinaryState == 0 ) {
				
			logger.info("Stepping from init" );
			previousBinaryState = currentBinaryState;
			if (single_step_sequence.containsKey(currentBinaryState)) {
				// nominal torque and current
				if (doStep(single_step_sequence.get(previousBinaryState), single_step_sequence.get(currentBinaryState), single_step_sequence.size())){
					logger.info("Stepping in single_step_sequence"+previousBinaryState+"->"+currentBinaryState+"  @"+currentTime );
					previousBinaryState = currentBinaryState;
					previousChangeTime = currentTime;			
				}					
			} else if ( double_step_sequence.containsKey(previousBinaryState) && double_step_sequence.containsKey(currentBinaryState)) {
				// double torque and current
				if (doStep(double_step_sequence.get(previousBinaryState), double_step_sequence.get(currentBinaryState), double_step_sequence.size())){
					logger.info("Stepping in double_step_sequence"+previousBinaryState+"->"+currentBinaryState+"  @"+currentTime );
					previousBinaryState = currentBinaryState;
					previousChangeTime = currentTime;			
				}
			} else if ( half_step_sequence.containsKey(previousBinaryState) && half_step_sequence.containsKey(currentBinaryState)) {
				// double torque and current
				if (doStep(half_step_sequence.get(previousBinaryState), half_step_sequence.get(currentBinaryState), half_step_sequence.size())){
					logger.info("Stepping in half_step_sequence"+previousBinaryState+"->"+currentBinaryState+"  @"+currentTime );
					previousBinaryState = currentBinaryState;
					previousChangeTime = currentTime;			
				}
			}
		}							
	}

	// rotate shaft according to index difference
	private boolean doStep(int previousSeqIdx, int currentSeqIdx, int nbStep) {
		
		int theoricalCurrentIdxForward = previousSeqIdx+1;
		if (theoricalCurrentIdxForward == nbStep)
			theoricalCurrentIdxForward =0;
		
		int theoricalCurrentIdxReverse = previousSeqIdx-1;
		if (theoricalCurrentIdxReverse < 0)
			theoricalCurrentIdxReverse = nbStep-1;
		
		if (currentSeqIdx == theoricalCurrentIdxForward){
			logger.debug("Forward Direction: Resitstant Torque: "+resistantTorque+ " holding torque: "+ properties.getHoldingTorque());
			if (properties.getHoldingTorque()-resistantTorque > 0){
				stepInc += 1;
			}
			else
				logger.warn("Step lost: resitstant Torque: "+resistantTorque+ " holding torque: "+ properties.getHoldingTorque());
			if (stepInc >= 1)
			{
				stepInc = 0;
				position+=360.0/(double)properties.getStepsPerRotation();
				logger.info("Stepping to "+position);
				
			}
			return true;
		}
		else if (currentSeqIdx == theoricalCurrentIdxReverse){
			logger.debug("Reverse direction: resitstant Torque: "+resistantTorque+ " holding torque: "+ properties.getHoldingTorque());
			if (properties.getHoldingTorque()+resistantTorque > 0){
				stepInc -= 1;
			}
			else
				logger.warn("Step lost: resitstant Torque: "+resistantTorque+ " holding torque: "+ properties.getHoldingTorque());
			
			if (stepInc <= -1)
			{
				stepInc = 0;
				position-=360.0/properties.getStepsPerRotation();
				logger.info("Stepping to "+position);
			}
			return true;
			
		}
		return false;
	}

	private byte getBinaryState() {
		int pinIdx = 0;
		byte state = 0;
		Iterator<org.raspinloop.config.Pin> it = properties.getUsedPins().iterator();
		while (it.hasNext() && pinIdx++ < 4) {
			org.raspinloop.config.Pin configuredPin = (org.raspinloop.config.Pin) it.next();
			double nib = Math.pow(2, pinIdx-1);
			if (cachedPins.get(configuredPin).getState() == onState) {
				state ^= (int) nib;
			}
		}
		return state;
	}

	@Override
	public boolean usePin(Pin pin) {		
		for (org.raspinloop.config.Pin configuredPin : cachedPins.keySet()) {
			if (configuredPin.getAddress() == pin.getAddress())
				return true;
		}
		return false;
	}

	public org.raspinloop.config.Pin getPin(Pin pin) {		
		for (org.raspinloop.config.Pin configuredPin : cachedPins.keySet()) {
			if (configuredPin.getAddress() == pin.getAddress())
				return configuredPin;
		}
		return null;
	}
	
	@Override
	public String getHWGuid() {		
		return null;
	}


}
