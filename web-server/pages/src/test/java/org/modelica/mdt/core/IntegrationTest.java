package org.modelica.mdt.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.internal.core.CompilerProxy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.legacy.PowerMockRunner;




@RunWith(PowerMockRunner.class)
@PrepareForTest(CompilerProxy.class)
public class IntegrationTest {

	@Before
	public void setUp() throws CompilerInstantiationException{
		PowerMockito.spy(CompilerProxy.class);
		Mockito.when(CompilerProxy.getCompiler()).thenReturn(new MockCompiler());		
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
