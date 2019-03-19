package org.raspinloop.modelica.annotations;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.modelica.annotations.FillPattern;
import org.raspinloop.server.modelica.annotations.LinePattern;

public class EnumTest {

	@Test
	public void testLinePaternSolid() throws IOException, ParseException {
		StringReader sr = new StringReader("LinePattern.Solid");
		LinePattern l = LinePattern.build(sr);
		assertEquals(LinePattern.Solid, l);	
	}
	
	@Test
	public void testLinePaternNone() throws IOException, ParseException {
		StringReader sr = new StringReader("LinePattern.None");
		LinePattern l = LinePattern.build(sr);
		assertEquals(LinePattern.None, l);	
	}
	
	@Test
	public void testFillPatternSolid() throws IOException, ParseException {
		StringReader sr = new StringReader("FillPattern.Solid");
		FillPattern l = FillPattern.build(sr);
		assertEquals(FillPattern.Solid, l);	
	}
	
	@Test
	public void testFillPatternNone() throws IOException, ParseException {
		StringReader sr = new StringReader("FillPattern.None");
		FillPattern l = FillPattern.build(sr);
		assertEquals(FillPattern.None, l);	
	}
}
