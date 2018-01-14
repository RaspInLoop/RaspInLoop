package org.raspinloop.fmi.testtools;

import org.junit.Assert;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;

public class AssertFMI extends Assert{

	public static void assertIsInputVariable(Fmi2ScalarVariable fmi2ScalarVariable) {
		assertEquals("Variable "+fmi2ScalarVariable.getName()+" must be declared as input", "input", fmi2ScalarVariable.getCausality());
		assertEquals("Variable "+fmi2ScalarVariable.getName()+" must be declared as continuous","continuous",fmi2ScalarVariable.getVariability());	
	}
	
	public static void assertIsOutputVariable(Fmi2ScalarVariable fmi2ScalarVariable) {
		assertEquals("Variable "+fmi2ScalarVariable.getName()+" must be declared as output", "output", fmi2ScalarVariable.getCausality());
		assertEquals("Variable "+fmi2ScalarVariable.getName()+" must be declared as continuous","continuous",fmi2ScalarVariable.getVariability());	
	}

	public static void assertIsRealVariable(Fmi2ScalarVariable fmi2ScalarVariable) {
		assertNotNull("Variable "+fmi2ScalarVariable.getName()+" must be Real", fmi2ScalarVariable.getReal() );		
	}
	
	public static void assertIsBooleanVariable(Fmi2ScalarVariable fmi2ScalarVariable) {
		assertNotNull("Variable "+fmi2ScalarVariable.getName()+" must be Boolean", fmi2ScalarVariable.getBoolean() );		
	}

	
}
