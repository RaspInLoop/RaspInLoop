package org.raspinloop.modelica.annotations;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.modelica.annotations.Extent;

public class ExtentTest {

	@Test
	public void build() throws IOException, ParseException {
		StringReader sr = new StringReader("12,34,56,78,");
		Extent e = Extent.build(sr);
		assertEquals(12, e.getP1().getX(), 0.00001);
		assertEquals(34, e.getP1().getY(), 0.00001);
		assertEquals(56, e.getP2().getX(), 0.00001);
		assertEquals(78, e.getP2().getY(), 0.00001);
	}
	
	@Test
	public void buildWithbrace() throws IOException, ParseException {
		StringReader sr = new StringReader("{{-100,100},{99,-99}},");
		Extent e = Extent.build(sr);
		assertEquals(-100, e.getP1().getX(), 0.00001);
		assertEquals(100, e.getP1().getY(), 0.00001);
		assertEquals(99, e.getP2().getX(), 0.00001);
		assertEquals(-99, e.getP2().getY(), 0.00001);
	}
	
	@Test
	public void buildWithWithespaces() throws IOException, ParseException {
		StringReader sr = new StringReader("{{-100, 100}, {99, -99}}, ");
		Extent e = Extent.build(sr);
		assertEquals(-100, e.getP1().getX(), 0.00001);
		assertEquals(100, e.getP1().getY(), 0.00001);
		assertEquals(99, e.getP2().getX(), 0.00001);
		assertEquals(-99, e.getP2().getY(), 0.00001);
	}
	
	@Test
	public void buildWithCrsAndTabs() throws IOException, ParseException {
		StringReader sr = new StringReader("{\n\t{-100, 100},\n\t{99, -99}\n}, ");
		Extent e = Extent.build(sr);
		assertEquals(-100, e.getP1().getX(), 0.00001);
		assertEquals(100, e.getP1().getY(), 0.00001);
		assertEquals(99, e.getP2().getX(), 0.00001);
		assertEquals(-99, e.getP2().getY(), 0.00001);
	}
	
	@Test(expected=ParseException.class)
	public void buildWithWrongTypeException() throws IOException, ParseException {
		StringReader sr = new StringReader("{{-100,100E-5},{true,-99}},");
		Extent.build(sr);
	}
	

	@Test(expected=ParseException.class)
	public void buildWithUnexptectedDelimiter2Exception() throws IOException, ParseException {
		StringReader sr = new StringReader("{{-100, 100}, {99, -99}, ");
		Extent.build(sr);
	}
	
}
