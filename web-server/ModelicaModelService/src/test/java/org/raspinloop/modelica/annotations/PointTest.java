package org.raspinloop.modelica.annotations;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.modelica.annotations.Point;

public class PointTest {

	@Test
	public void build() throws IOException, ParseException {
		StringReader sr = new StringReader("123.0,458.0,");
		Point p = Point.build(sr);
		assertEquals(123, p.getX(), 0.00001);
		assertEquals(458, p.getY(), 0.00001);
	}
	
	@Test
	public void buildWithbrace() throws IOException, ParseException {
		StringReader sr = new StringReader("{123,458},");
		Point p = Point.build(sr);
		assertEquals(123, p.getX(), 0.00001);
		assertEquals(458, p.getY(), 0.00001);
	}
	
	@Test
	public void buildWithWithespaces() throws IOException, ParseException {
		StringReader sr = new StringReader("  { 123  ,  458   },");
		Point p = Point.build(sr);
		assertEquals(123, p.getX(), 0.00001);
		assertEquals(458, p.getY(), 0.00001);
	}
	
	@Test
	public void buildWithCr() throws IOException, ParseException {
		StringReader sr = new StringReader("{123,\n458},");
		Point p = Point.build(sr);
		assertEquals(123, p.getX(), 0.00001);
		assertEquals(458, p.getY(), 0.00001);
		
		sr = new StringReader("\n123,\n458,\n");
		p = Point.build(sr);
		assertEquals(123, p.getX(), 0.00001);
		assertEquals(458, p.getY(), 0.00001);
	}
		
	public void buildWithWrongTypeException() throws IOException, ParseException {
		StringReader sr = new StringReader("12.3,458E10-2,");
		Point p = Point.build(sr);
		assertEquals(123, p.getX(), 0.00001);
		assertEquals(458, p.getY(), 0.00001);
	}
	
	@Test(expected=ParseException.class)
	public void buildWithBadDelimiterException() throws IOException, ParseException {
		StringReader sr = new StringReader("123/4580,");
		Point p = Point.build(sr);
		assertEquals(123, p.getX(), 0.00001);
		assertEquals(458, p.getY(), 0.00001);
	}
	
	@Test(expected=ParseException.class)
	public void buildWithUnexptectedDelimiterException() throws IOException, ParseException {
		StringReader sr = new StringReader("{123, 4580");
		Point p = Point.build(sr);
		assertEquals(123, p.getX(), 0.00001);
		assertEquals(458, p.getY(), 0.00001);
	}
	
	@Test(expected=ParseException.class)
	public void buildWithUnexptectedDelimiter2Exception() throws IOException, ParseException {
		StringReader sr = new StringReader("{123, 4580, 4587");
		Point p = Point.build(sr);
		assertEquals(123, p.getX(), 0.00001);
		assertEquals(458, p.getY(), 0.00001);
	}
	
}
