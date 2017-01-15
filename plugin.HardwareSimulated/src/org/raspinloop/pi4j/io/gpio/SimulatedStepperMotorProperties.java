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
		

	private org.raspinloop.config.PinState onState = PinState.HIGH;		
	private org.raspinloop.config.PinState offState = PinState.LOW;		
	
	private int stepsPerRotation = 200; 					
	private double initalPosition = 0;						
	
	private BoardHardware parent;
	private String name = DISPLAY_NAME;
	protected ArrayList<PinImpl> pins = new ArrayList<PinImpl>();
	private boolean averageMode;
	
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public SimulatedStepperMotorProperties setName(String string) {
		this.name = string;		
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
	public BoardExtentionHardware setParent(BoardHardware sd) {
		parent = sd;
		return this;
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
	
	private double holdingTorque = 0.44;
	/**
	 * The maximum torque produced by the motor at standstill
	 * @return
	 */
    public double getHoldingTorque() {
		return holdingTorque;
	}

	public void setHoldingTorque(double holdingTorque) {
		this.holdingTorque = holdingTorque;
	}

	
	//will be used in step mode (opposite of average mode)
	private double detentTorque = 0.015;//N.m		
	private double maximumSlewFrequency = 0;	
	private double pulloutTorque = 0;			
	private double pullinTorque = 0;		
	private double RotorInertia = 0.00057; //kg.m²	
	
	public double getDetentTorque() {
		return detentTorque;
	}

	public void setDetentTorque(double detentTorque) {
		this.detentTorque = detentTorque;
	}
	
	/**
	 * The maximum operation frequency of the motor with no load applied
	 * @return
	 */
	public double getMaximumSlewFrequency() {
		return maximumSlewFrequency;
	}

	public void setMaximumSlewFrequency(double maximumSlewFrequency) {
		this.maximumSlewFrequency = maximumSlewFrequency;
	}

	public double getPulloutTorque() {
		return pulloutTorque;
	}

	public void setPulloutTorque(double pulloutTorque) {
		this.pulloutTorque = pulloutTorque;
	}

	public double getPullinTorque() {
		return pullinTorque;
	}

	public void setPullinTorque(double pullinTorque) {
		this.pullinTorque = pullinTorque;
	}
					
	public double getRotorInertia() {
		return RotorInertia;
	}

	public void setRotorInertia(double rotorInertia) {
		RotorInertia = rotorInertia;
	}

	public org.raspinloop.config.PinState getOnState() {
		return onState;
	}
	public SimulatedStepperMotorProperties setOnState(org.raspinloop.config.PinState onState) {
		this.onState = onState;
		return this;
	}
	public org.raspinloop.config.PinState getOffState() {
		return offState;
	}
	public SimulatedStepperMotorProperties setOffState(org.raspinloop.config.PinState offState) {
		this.offState = offState;
		return this;
	}
	public int getStepsPerRotation() {
		return stepsPerRotation;
	}
	public SimulatedStepperMotorProperties setStepsPerRotation(int stepsPerRotation) {
		this.stepsPerRotation = stepsPerRotation;
		return this;
	}
	public double getInitalPosition() {
		return initalPosition;
	}
	public SimulatedStepperMotorProperties setInitalPosition(double initalPosition) {
		this.initalPosition = initalPosition;
		return this;
	}
	//
	public SimulatedStepperMotorProperties setPins(Collection<Pin> newPins) {
		pins.clear();
		for (Pin pin : newPins) {
			pins.add(new PinImpl(pin));
		}
		return this;
	}

	public boolean isAverageMode() {
		return this.averageMode;
	}

	public SimulatedStepperMotorProperties setAverageMode(boolean averageMode) {
		this.averageMode = averageMode;
		return this;
	}
}
