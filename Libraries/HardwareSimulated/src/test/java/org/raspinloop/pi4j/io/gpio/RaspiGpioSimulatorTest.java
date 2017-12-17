package org.raspinloop.pi4j.io.gpio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.raspinloop.fmi.testtools.AssertFMI.assertIsBooleanVariable;
import static org.raspinloop.fmi.testtools.AssertFMI.assertIsInputVariable;
import static org.raspinloop.fmi.testtools.AssertFMI.assertIsOutputVariable;

import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.raspinloop.config.AlreadyUsedPin;
import org.raspinloop.config.PinState;
import org.raspinloop.fmi.HwEmulation;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;
import org.raspinloop.fmi.testtools.Builder;
import org.raspinloop.fmi.testtools.FMU;
import org.raspinloop.hwemulation.GpioProvider;
import org.raspinloop.hwemulation.PinDigitalStateChangeEvent;
import org.raspinloop.hwemulation.PinListener;

public class RaspiGpioSimulatorTest {

	private RaspiGpioSimulatorProperties buildProperty() throws AlreadyUsedPin {
		RaspiGpioSimulatorProperties prop = new RaspiGpioSimulatorProperties();
		prop.useOutputPin(RaspiPin.GPIO_01);
		prop.useOutputPin(RaspiPin.GPIO_03);
		prop.useInputPin(RaspiPin.GPIO_02);		
		return prop;
	}

	@Test
	public void testGetModelVariables() throws AlreadyUsedPin {
		
		// HwEmulation is the interface used by the simulation tool.
		// to get the description (model)
		// to get the ID 
		// to initialize, reset and terminate the simulation
		// and to read and write variable (setBoolean, setInteger,..., getBoolean, getInteger,...)
		HwEmulation dut = new RaspiGpioSimulator(Builder.getBuilderFor(buildProperty()));
			
		List<Fmi2ScalarVariable> variables = dut.getModelVariables();
		
		assertEquals(3,variables.size() );
		
		Fmi2ScalarVariable input1Var = variables.get(0);
		
		Fmi2ScalarVariable output1Var = variables.get(1);
		
		Fmi2ScalarVariable output2Var = variables.get(2);
		
		for (Fmi2ScalarVariable fmi2ScalarVariable : variables) {
			assertIsBooleanVariable(fmi2ScalarVariable);			
		}
		
		assertEquals("GPIO 2",input1Var.getName() );
		assertIsInputVariable(input1Var);
		
		assertEquals("GPIO 1",output1Var.getName() );
		assertIsOutputVariable(output1Var);
		
		assertEquals("GPIO 3",output2Var.getName() );
		assertIsOutputVariable(output2Var);
	}
	
	@Test
	public void GPIOTest() throws AlreadyUsedPin {
		RaspiGpioSimulator dut = new RaspiGpioSimulator(Builder.getBuilderFor(buildProperty()));
		
		// HwEmulation is the interface used by the simulation tool.
				// to get the description (model)
				// to get the ID 
				// to initialize, reset and terminate the simulation
				// and to read and write variable (setBoolean, setInteger,..., getBoolean, getInteger,...)
		HwEmulation usedBySimulator = dut; 
		
		// GpioProvider is the interface used by the code to debug ( ie: the Pi4J library) 
		GpioProvider usedByCode = dut;
		
		FMU.setVariable(usedBySimulator, "GPIO 2", true);
	
		assertTrue(usedByCode.hasPin(RaspiPin.GPIO_02) );
		assertEquals(PinState.HIGH, usedByCode.getState(RaspiPin.GPIO_02));
		
		assertTrue(usedByCode.hasPin(RaspiPin.GPIO_01) );
		assertFalse(usedByCode.isExported(RaspiPin.GPIO_01) );
		usedByCode.setState(RaspiPin.GPIO_01, PinState.HIGH);
		assertTrue("variable GPIO 1 should be set", FMU.getVariable(usedBySimulator, "GPIO 1", Boolean.class));
		
		usedByCode.setState(RaspiPin.GPIO_01, PinState.LOW);
		assertFalse("variable GPIO 1 should be reset", FMU.getVariable(usedBySimulator, "GPIO 1", Boolean.class));
		
	}
	
	@Test
	public void GPIOListenerTest() throws AlreadyUsedPin {
		RaspiGpioSimulator dut = new RaspiGpioSimulator(Builder.getBuilderFor(buildProperty()));
		
		// HwEmulation is the interface used by the simulation tool.
				// to get the description (model)
				// to get the ID 
				// to initialize, reset and terminate the simulation
				// and to read and write variable (setBoolean, setInteger,..., getBoolean, getInteger,...)
		HwEmulation usedBySimulator = dut; 
		
		// GpioProvider is the interface used by the code to debug ( ie: the Pi4J library) 
		GpioProvider usedByCode = dut;		
	
		PinListener listener = Mockito.mock(PinListener.class);
		usedByCode.addListener(RaspiPin.GPIO_02, listener);		
		
		FMU.setVariable(usedBySimulator, "GPIO 2", true);
		Mockito.verify(listener).handlePinEvent(Mockito.argThat(ev -> ((PinDigitalStateChangeEvent)ev).getState() == PinState.HIGH ));
		
		FMU.setVariable(usedBySimulator, "GPIO 2", false);
		Mockito.verify(listener).handlePinEvent(Mockito.argThat(ev -> ((PinDigitalStateChangeEvent)ev).getState() == PinState.LOW ));
	
		
	}
	
}
