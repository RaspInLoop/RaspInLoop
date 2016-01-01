package org.raspinloop.pi4j.io.gpio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.raspinloop.config.AlreadyUsedPin;
import org.raspinloop.fmi.hwemulation.HardwareBuilder;
import org.raspinloop.fmi.hwemulation.HwEmulation;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable.Real;

import com.pi4j.component.Component;
import com.pi4j.component.ComponentBase;

public class SimulatedStepperMotor extends ComponentBase implements Component, HwEmulation {	
	

	final static Logger logger = Logger.getLogger(SimulatedStepperMotor.class);
	
	private double position; //relative ref 0
	private double speed; //relative ref 1 
	private double torque; //relative ref 2.  Input: opposite torque from the system on the shaft
	//param
		
	static final int NB_VAR = 4; // too old fashion style, it smells like C code...  
	
	private int baseref;

	public SimulatedStepperMotor(HardwareBuilder builder){
		if (builder.getProperties() instanceof SimulatedStepperMotorProperties)
			properties = (SimulatedStepperMotorProperties)builder.getProperties();
		baseref = builder.getBaseReference();
	}

	private SimulatedStepperMotorProperties properties;
	
	private Double getVar(Integer ref) {
		switch (ref - baseref) {
		case 0:
			return position;
		case 1:
			return speed;
		case 2:
			return torque;
		case 3:
			return (double)properties.getStepsPerRotation();
		case 4:
			return  properties.getInitalPosition();
		default:
			return null;
		}
	}


	public SimulatedStepperMotor() {
		this.properties = new SimulatedStepperMotorProperties();			
	}
	
	public SimulatedStepperMotor(String name, org.raspinloop.config.PinState onState, org.raspinloop.config.PinState offState) throws AlreadyUsedPin {
		setName( name);
		properties.setOnState(onState);
		properties.setOffState(offState);
	}
	
	
	public String getHWGuid() {
		return null;
	}

	public boolean enterInitialize() {
		position =  properties.getInitalPosition(); 
		return false;
	}

	public boolean exitInitialize() {
		return true;
	}

	public void terminate() {
	}

	public void reset() {
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
			Double var = getVar(entry.getKey());
			if (var != null) {				
				var = entry.getValue();
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
		result.add(createRealOutput(getType()+" position", "", baseref+0));
		result.add(createRealOutput(getType()+" speed", "", baseref+1));
		result.add(createRealInput(getType()+" torque", "", baseref+2));
		return result;
	}
	
	
	private Fmi2ScalarVariable createRealOutput(String name, String descritpion, long ref){
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
	
	private Fmi2ScalarVariable createRealInput(String name, String descritpion, long ref){
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

	//@Override
	public int registerBaseref(int baseref) {
		this.baseref = baseref;
		return NB_VAR;
	}	

	@Override
	public String getType() {		
		return properties.getType();
	}
}
