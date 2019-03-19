package org.modelica.mdt.internal.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.raspinloop.server.modelica.mdt.internal.core.Packages;

public class PackagesTest {

	@Test
	public void testIsSubPackage() {
		assertTrue(Packages.isSubPackage("Modelica.Mechanics.Rotational.Components", "Modelica.Mechanics.Rotational.Components"));
		assertTrue(Packages.isSubPackage("Modelica.Mechanics.Rotational", "Modelica.Mechanics.Rotational.Components"));
		assertTrue(Packages.isSubPackage("Modelica.Mechanics", "Modelica.Mechanics.Rotational.Components"));
		assertTrue(Packages.isSubPackage("Modelica", "Modelica.Mechanics.Rotational.Components"));
		assertTrue(Packages.isSubPackage("Modelica", "Modelica.Mechanics.Rotational"));
		assertTrue(Packages.isSubPackage("Modelica", "Modelica.Mechanics"));
		assertFalse(Packages.isSubPackage("Modelica.Mechanics.Rotational", "Modelica"));
	}

	@Test
	public void testRename() {
		assertEquals("Modelica.SIUnit.Angle", Packages.rename("SI.Angle", "SI", "Modelica.SIUnit"));
	}

	@Test
	public void testRelativize() {
		assertEquals("Sources.VolumeFlow",Packages.relativize("Modelica.Thermal.FluidHeatFlow", "Modelica.Thermal.FluidHeatFlow.Sources.VolumeFlow"));
	}

	@Test
	public void testIsSamePackage() {
		assertTrue(Packages.isSamePackage("A.B.C.D", "A.B.C.D"));
		assertTrue(Packages.isSamePackage("A.B.C.D", "B.C.D"));
		assertFalse(Packages.isSamePackage("A.B.C.D", "A.B.C"));
	}

	@Test
	public void testGetPackage() {
		assertEquals("Modelica.Thermal.FluidHeatFlow.Sources",Packages.getPackage("Modelica.Thermal.FluidHeatFlow.Sources.VolumeFlow"));
		assertEquals("",Packages.getPackage("VolumeFlow"));
	}

}
