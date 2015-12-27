package org.raspinloop.pi4j.io.gpio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.raspinloop.config.BoardExtentionHardware;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.Pin;
import org.raspinloop.config.PinImpl;
import org.raspinloop.config.PinState;


public class SimulatedStepperMotorProperties implements BoardExtentionHardware {

		
	public static final String TYPE = "simulatedStepperMotor";
	public static final String DISPLAY_NAME = "Simulated Stepper Motor";
	public static final String SIMULATED_PROVIDER_NAME = "Stepper Mottor";
	public static final String IMPLEMENTATION_CLASS_NAME = SimulatedStepperMotor.class.getName();
		
	public enum StepSequence {
		SINGLE_STEP , HALF_STEP , DOUBLE_STEP;
	}
	private StepSequence stepSequence = StepSequence.SINGLE_STEP;
	
	private org.raspinloop.config.PinState onState = PinState.HIGH;		
	private org.raspinloop.config.PinState offState = PinState.LOW;		
	
	private int stepsPerRotation = 200; 					//relative ref 3	
	private double initalPosition = 0;				//relative ref 4
	
	private BoardHardware parent;
	private String name = DISPLAY_NAME;
	protected ArrayList<PinImpl> pins = new ArrayList<PinImpl>();
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String string) {
		this.name = string;		
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
	public void setParent(BoardHardware sd) {
		parent = sd;
	}

	@Override
	public BoardHardware getParentComponent() {
		return parent;
	}

	@Override
	public Collection<Pin> getUsedPins() {
		return Collections.<Pin>unmodifiableCollection(pins);
	}	
	
	// additional param
	/*
	private double holdingTorque = 0;			//relative ref 5
	private double detentTorque = 0;			//relative ref 6
	private double maximumSlewFrequency = 0;	//relative ref 7
	private double pulloutTorque = 0;			//relative ref 8
	private double pullinTorque = 0;			//relative ref 9
	private double Accuracy = 0;				//relative ref 10
	*/
	
	public StepSequence getStepSequence(){
		return stepSequence;
	}
	
	public void setStepSequence(StepSequence sequence){
		stepSequence= sequence;
	}
	
	public org.raspinloop.config.PinState getOnState() {
		return onState;
	}
	public void setOnState(org.raspinloop.config.PinState onState) {
		this.onState = onState;
	}
	public org.raspinloop.config.PinState getOffState() {
		return offState;
	}
	public void setOffState(org.raspinloop.config.PinState offState) {
		this.offState = offState;
	}
	public int getStepsPerRotation() {
		return stepsPerRotation;
	}
	public void setStepsPerRotation(int stepsPerRotation) {
		this.stepsPerRotation = stepsPerRotation;
	}
	public double getInitalPosition() {
		return initalPosition;
	}
	public void setInitalPosition(double initalPosition) {
		this.initalPosition = initalPosition;
	}

	public void setPins(Collection<Pin> newPins) {
		pins.clear();
		for (Pin pin : newPins) {
			pins.add(new PinImpl(pin));
		}
	}
}
