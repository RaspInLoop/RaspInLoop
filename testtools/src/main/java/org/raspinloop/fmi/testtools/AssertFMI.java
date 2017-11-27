package org.raspinloop.fmi.testtools;

import org.junit.Assert;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;

public class AssertFMI extends Assert{

	public static void assertIsInputVariable(Fmi2ScalarVariable fmi2ScalarVariable) {
		assertEquals("input", fmi2ScalarVariable.getCausality());
		assertEquals("continuous",fmi2ScalarVariable.getVariability());	
	}

	public static void assertIsRealVariable(Fmi2ScalarVariable fmi2ScalarVariable) {
		assertNotNull(fmi2ScalarVariable.getReal() );
		
	}

	
}
