package org.raspinloop.modelica.annotations;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.modelica.annotations.Color;

public class ColorTest {

	@Test
	public void build() throws IOException, ParseException {
		StringReader sr = new StringReader("255,128,0");
		Color c = Color.build(sr);
		assertEquals(255, c.getRed());
		assertEquals(128, c.getGreen());
		assertEquals(0, c.getBlue());
	}
	
	@Test
	public void buildWithbrace() throws IOException, ParseException {
		StringReader sr = new StringReader("{255,128,0}");
		Color c = Color.build(sr);
		assertEquals(255, c.getRed());
		assertEquals(128, c.getGreen());
		assertEquals(0, c.getBlue());
	}
	
	@Test
	public void buildWithWithespaces() throws IOException, ParseException {
		StringReader sr = new StringReader("{ 255, 128, 0 } ");
		Color c = Color.build(sr);
		assertEquals(255, c.getRed());
		assertEquals(128, c.getGreen());
		assertEquals(0, c.getBlue());
	}
	
}
