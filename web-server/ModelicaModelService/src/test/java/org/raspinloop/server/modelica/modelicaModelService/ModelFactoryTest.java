package org.raspinloop.server.modelica.modelicaModelService;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openmodelica.corba.ConnectException;
import org.raspinloop.server.model.IComponent;
import org.raspinloop.server.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.raspinloop.server.modelica.mdt.core.compiler.InvocationError;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelFactoryTest {

	@Resource
	IModelFactory modeLFactory;

	@Ignore
	@Test
	public void test() throws ConnectException, UnexpectedReplyException, CompilerInstantiationException, InvocationError {
		IComponent component = modeLFactory.createComponent("Modelica.Icons.UtilitiesPackage");
		assertEquals("UtilitiesPackage", component.getName());
	}

}
