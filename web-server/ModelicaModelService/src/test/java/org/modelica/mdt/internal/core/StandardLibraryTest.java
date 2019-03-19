package org.modelica.mdt.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openmodelica.corba.ConnectException;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass;
import org.raspinloop.server.modelica.mdt.core.IModelicaComponent;
import org.raspinloop.server.modelica.mdt.core.IModelicaElement;
import org.raspinloop.server.modelica.mdt.core.IModelicaProject;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass.Restriction;
import org.raspinloop.server.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.raspinloop.server.modelica.mdt.core.compiler.InvocationError;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 * @author Motte
 *Those test need omc installed on system and OPENMODELICAHOME environment variable defined. 
 */
@Ignore("Need OMC installed")
@RunWith(SpringRunner.class)
@SpringBootTest(classes={org.raspinloop.server.modelica.mdt.Modelica.class})
@EnableCaching
public class StandardLibraryTest {

	@Resource
	IModelicaProject modelicaProject;
	
	
	@Test
	public void test() throws ConnectException, CompilerInstantiationException, InterruptedException, UnexpectedReplyException, InvocationError {
		
		Collection<? extends IModelicaClass> packages;
		do {
			packages = modelicaProject.getRootClasses();
			Thread.sleep(100);
		} while (packages == null); // hack before having started flag

		Collection<IModelicaClass> models = new ArrayList<>();
		getBlocksOrModels(packages, models );
		assertTrue(models.size() > 0);

	}
	
	
	@Test
	public void testCache() throws ConnectException, CompilerInstantiationException, InterruptedException, UnexpectedReplyException, InvocationError {		
		Collection<? extends IModelicaClass> packages;
		do {
			packages = modelicaProject.getRootClasses();
			Thread.sleep(100);
		} while (packages == null); // hack before having started flag

		Collection<IModelicaClass> models = new ArrayList<>();
		getBlocksOrModels(packages, models );
		assertTrue(models.size() > 0);
		
		do {
			packages = modelicaProject.getRootClasses();
			Thread.sleep(100);
		} while (packages == null); // hack before having started flag

		models = new ArrayList<>();
		getBlocksOrModels(packages, models );
		assertTrue(models.size() > 0);
		
	}

	private void getBlocksOrModels(Collection<? extends IModelicaElement> elements, Collection<IModelicaClass> models) throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
	for (IModelicaElement iModelicaElement : elements) {
		if (iModelicaElement instanceof IModelicaClass) {
			IModelicaClass modelicaClass = (IModelicaClass)iModelicaElement;
			if (isPackage(modelicaClass)) {
				  getBlocksOrModels(modelicaClass.getChildren(), models);
			} else if (isBlockOrModel(modelicaClass)) {
				models.add(modelicaClass);
			}
				
		}
	}
	}
	
	private boolean isPackage(IModelicaClass modelicaClass) {
		try {
			Restriction restriction = modelicaClass.getRestriction();

			return restriction.equals(Restriction.PACKAGE);
		} catch (ConnectException | CompilerInstantiationException | UnexpectedReplyException | InvocationError e1) {
			return false;
		}
	}

	public boolean isBlockOrModel(IModelicaClass modelicaClass) {
		try {
			Restriction restriction = modelicaClass.getRestriction();

			return restriction.equals(Restriction.BLOCK) || restriction.equals(Restriction.MODEL);
		} catch (ConnectException | CompilerInstantiationException | UnexpectedReplyException | InvocationError e1) {
			return false;
		}
	}
	
	@Test
	public void loadOneClassTest() throws ConnectException, CompilerInstantiationException, UnexpectedReplyException{
				
		IModelicaClass moClass = modelicaProject.getRootClasses()
				.stream()
				.map(p -> p.getMoClassLoader().getClass("Modelica.Thermal.FluidHeatFlow.Sources.VolumeFlow"))
				.findFirst()
				.get();
		assertEquals("Modelica.Thermal.FluidHeatFlow.Sources.VolumeFlow", moClass.getFullName());
		
		List<IModelicaComponent> connectors = new ArrayList<>();
		addConnectors(connectors, moClass);
		assertEquals(3, connectors.size());
		
		IModelicaComponent comp1 = connectors.iterator().next();
		String icon1 = comp1.getIconAnnotation();
		String annotation1 = comp1.getComponentAnnotation();
		
		moClass = modelicaProject.getRootClasses()
				.stream()
				.map(p -> p.getMoClassLoader().getClass("Modelica.Mechanics.Rotational.Components.Clutch"))
				.findFirst()
				.get();
		assertEquals("Modelica.Mechanics.Rotational.Components.Clutch", moClass.getFullName());
		
		connectors = new ArrayList<>();
		addConnectors(connectors, moClass);
		assertEquals(4, connectors.size());
		
		moClass = modelicaProject.getRootClasses()
				.stream()
				.map(p -> p.getMoClassLoader().getClass("Modelica.Electrical.Machines.Thermal.DCMachines.ThermalAmbientDCPM"))
				.findFirst()
				.get();
		assertEquals("Modelica.Electrical.Machines.Thermal.DCMachines.ThermalAmbientDCPM", moClass.getFullName());
		
		connectors = new ArrayList<>();
		addConnectors(connectors, moClass);
		assertEquals(3, connectors.size());
		
		moClass = modelicaProject.getRootClasses()
				.stream()
				.map(p -> p.getMoClassLoader().getClass("Modelica.Electrical.Analog.Semiconductors.HeatingPMOS"))
				.findFirst()
				.get();
		assertEquals("Modelica.Electrical.Analog.Semiconductors.HeatingPMOS", moClass.getFullName());
		
		connectors = new ArrayList<>();
		addConnectors(connectors, moClass);
		assertEquals(5, connectors.size());
	}
	
	
	private void addConnectors(List<IModelicaComponent> connectors, IModelicaClass moclass){		
		try {
			moclass.getInheritedClasses().forEach(c -> this.addConnectors(connectors, c));
			moclass.getChildren().stream().filter(IModelicaComponent.class::isInstance)
			.map(IModelicaComponent.class::cast)				
			.filter(IModelicaComponent::isConnector)
			.forEach(connectors::add);
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
			//TODO Logger
		}
	}
}
