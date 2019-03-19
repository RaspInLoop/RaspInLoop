package org.raspinloop.modelica.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.modelica.annotations.CoordinateSystem;

public class CoordinateSystemTest {

	@Test
	public void build() throws IOException, ParseException {
		StringReader sr = new StringReader("-100.0,-99.0,100.0,99.0, true,0.1,2.0,2.0,");
		CoordinateSystem c = CoordinateSystem.build(sr);		
		assertEquals(-100.0, c.getExtent().getP1().getX(), 0.00001);
		assertEquals(-99.0, c.getExtent().getP1().getY(), 0.00001);
		assertEquals(100.0, c.getExtent().getP2().getX(), 0.00001);
		assertEquals(99.0, c.getExtent().getP2().getY(), 0.00001);
		
		assertTrue( c.isPreserveAspectRatio());
		assertEquals(0.1, c.getInitialScale(), 0.00001);
		assertEquals(2, c.getDrawingUnitX(), 0.00001);
		assertEquals(2, c.getDrawingUnitY(), 0.00001);
	}
	
	@Test
	public void buildWithbrace() throws IOException, ParseException {
		StringReader sr = new StringReader("{-100.0,-99.0,100.0,99.0, true,0.1,2.0,2.0},");
		CoordinateSystem c = CoordinateSystem.build(sr);		
		assertEquals(-100.0, c.getExtent().getP1().getX(), 0.00001);
		assertEquals(-99.0, c.getExtent().getP1().getY(), 0.00001);
		assertEquals(100.0, c.getExtent().getP2().getX(), 0.00001);
		assertEquals(99.0, c.getExtent().getP2().getY(), 0.00001);
		
		assertTrue( c.isPreserveAspectRatio());
		assertEquals(0.1, c.getInitialScale(), 0.00001);
		assertEquals(2, c.getDrawingUnitX(), 0.00001);
		assertEquals(2, c.getDrawingUnitY(), 0.00001);
	}
	
	@Test
	public void buildWithWithespaces() throws IOException, ParseException {
		StringReader sr = new StringReader(" {  -100.0  ,-99.0  ,100.0,  99.0,  true,0.1,2.0,2.0}, ");
		CoordinateSystem c = CoordinateSystem.build(sr);		
		assertEquals(-100.0, c.getExtent().getP1().getX(), 0.00001);
		assertEquals(-99.0, c.getExtent().getP1().getY(), 0.00001);
		assertEquals(100.0, c.getExtent().getP2().getX(), 0.00001);
		assertEquals(99.0, c.getExtent().getP2().getY(), 0.00001);
		
		assertTrue( c.isPreserveAspectRatio());
		assertEquals(0.1, c.getInitialScale(), 0.00001);
		assertEquals(2, c.getDrawingUnitX(), 0.00001);
		assertEquals(2, c.getDrawingUnitY(), 0.00001);
	}	
	
	@Test(expected=ParseException.class)
	public void buildWithWrongTypeException() throws IOException, ParseException {
		StringReader sr = new StringReader(" {  -100.0  ,-99.0  ,100.0,  99.0,  27 ,0.1,2.0,2.0}, ");
		CoordinateSystem.build(sr);				
	}
	

	@Test(expected=ParseException.class)
	public void buildWithUnexptectedDelimiter2Exception() throws IOException, ParseException {
		StringReader sr = new StringReader(" {  -100.0  ,-99.0  ,100.0,  99.0,  false ,0.1,2.0,2.0, ");
		CoordinateSystem.build(sr);
	}
	
}
