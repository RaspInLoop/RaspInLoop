package org.raspinloop.modelica.annotations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;
import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.modelica.annotations.Arrow;
import org.raspinloop.server.modelica.annotations.Bitmap;
import org.raspinloop.server.modelica.annotations.BorderPattern;
import org.raspinloop.server.modelica.annotations.Ellipse;
import org.raspinloop.server.modelica.annotations.FillPattern;
import org.raspinloop.server.modelica.annotations.Line;
import org.raspinloop.server.modelica.annotations.LinePattern;
import org.raspinloop.server.modelica.annotations.Placement;
import org.raspinloop.server.modelica.annotations.Polygon;
import org.raspinloop.server.modelica.annotations.Rectangle;
import org.raspinloop.server.modelica.annotations.Smooth;
import org.raspinloop.server.modelica.annotations.Text;
import org.raspinloop.server.modelica.annotations.TextAlignment;

public class GraphicItemTest {

	@Test
	public void buildRecatngle() throws IOException, ParseException {
		StringReader sr = new StringReader("Rectangle("
				+ "true,"
				+ "{0.0, 0.0},"
				+ "0,"
				+ "{255, 0, 0},"
				+ "{255, 255, 255},"
				+ "LinePattern.Solid,"
				+ "FillPattern.Solid,"
				+ "0.25,"
				+ "BorderPattern.None,"
				+ "{{-100, 100}, {100, -100}},"
				+ "0)");
		Rectangle r = Rectangle.build(sr);
		assertEquals(255, r.getFillColor().getRed());
		assertEquals(255, r.getFillColor().getGreen());
		assertEquals(255, r.getFillColor().getBlue());
		assertTrue( r.isVisible());
		assertEquals(255, r.getFillColor().getBlue());
		assertEquals(BorderPattern.None,r.getBorderPattern());
		assertEquals(0, r.getRadius(), 0.001);
		assertEquals(100, r.getExtent().getP1().getY(), 0.001);
	}

	@Test
	public void buildLine() throws IOException, ParseException {
		StringReader sr = new StringReader("Line("
				+ "true, "
				+ "{0.0, 0.0}, "
				+ "0, "
				+ "{{37.6, 13.7}, "
				+ "{65.8, 23.9}}, "
				+ "{0, 0, 0}, "
				+ "LinePattern.Solid,"
				+ " 0.25, "
				+ "{Arrow.None, Arrow.None}, "
				+ "3, "
				+ "Smooth.None)");
		Line l = Line.build(sr);
		assertEquals(0, l.getOrigin().getX(), 0.0001);
		assertTrue( l.isVisible());
		assertEquals(0, l.getRotation(), 0.0001);
		assertEquals(2, l.getPoints().size());
		assertEquals(37.6, l.getPoints().get(0).getX(), 0.0001);
		assertEquals(13.7, l.getPoints().get(0).getY(), 0.0001);
		assertEquals(0, l.getColor().getRed());
		assertEquals(0, l.getColor().getGreen());
		assertEquals(0, l.getColor().getBlue());
		assertEquals(Arrow.None, l.getArrow()[0]);
		assertEquals(Arrow.None, l.getArrow()[1]);
		assertEquals(3, l.getArrowSize(),0.001);
		assertEquals(Smooth.None, l.getSmooth());
	}
	
	@Test
	public void buildPolygon() throws IOException, ParseException {
		StringReader sr = new StringReader("Polygon(true,"
				+ "{0.0, 0.0}, "
				+ "-17.5, "
				+ "{0, 0, 0}, "
				+ "{64, 64, 64}, "
				+ "LinePattern.None, "
				+ "FillPattern.Solid, "
				+ "0.25, "
				+ "{{-5.0, 0.0}, "
				+ "{-2.0, 60.0}, "
				+ "{0.0, 65.0}, "
				+ "{2.0, 60.0}, "
				+ "{5.0, 0.0}}, "
				+ "Smooth.None)");
		Polygon l = Polygon.build(sr);
		assertEquals(0, l.getOrigin().getX(), 0.0001);
		assertTrue( l.isVisible());
		assertEquals(-17.5, l.getRotation(), 0.0001);
		assertEquals(5, l.getPoints().size());
		assertEquals(-5, l.getPoints().iterator().next().getX(), 0.0001);
		assertEquals(0, l.getPoints().iterator().next().getY(), 0.0001);
		assertEquals(64, l.getFillColor().getRed());
		assertEquals(64, l.getFillColor().getGreen());
		assertEquals(64, l.getFillColor().getBlue());
		assertEquals(FillPattern.Solid, l.getFillPattern());
		assertEquals(LinePattern.None, l.getLinePattern());
		assertEquals(Smooth.None, l.getSmooth());
	}
	
	@Test
	public void buildEllipse() throws IOException, ParseException {
		StringReader sr = new StringReader("Ellipse(true, "
				+ "{0.0, 0.0}, "
				+ "0, "
				+ "{0, 0, 0}, "
				+ "{245, 245, 245}, "
				+ "LinePattern.Solid, "
				+ "FillPattern.Solid, "
				+ "0.25, "
				+ "{{-70.0, -70.0}, "
				+ "{70.0, 70.0}}, "
				+ "0, "
				+ "360)");
		Ellipse e = Ellipse.build(sr);
		
		assertEquals(0, e.getOrigin().getX(), 0.0001);
		assertTrue( e.isVisible());
		assertEquals(0, e.getRotation(), 0.0001);
		assertEquals(-70, e.getExtent().getP1().getX(), 0.0001);
		assertEquals(70, e.getExtent().getP2().getY(), 0.0001);
		assertEquals(245, e.getFillColor().getRed());
		assertEquals(245, e.getFillColor().getGreen());
		assertEquals(245, e.getFillColor().getBlue());
		assertEquals(FillPattern.Solid, e.getFillPattern());
		assertEquals(LinePattern.Solid, e.getLinePattern());
		assertEquals(0, e.getStartAngle(), 0.0001);
		assertEquals(360, e.getEndAngle(), 0.0001);
	}
	
	@Test
	public void buildText() throws IOException, ParseException {
		StringReader sr = new StringReader("Text(true, {0.0, 0.0}, 0, {0, 0, 0}, {0, 0, 0}, LinePattern.Solid, FillPattern.None, 0.25, {{-150, 100}, {150, 40}}, \"%name\", 0, TextAlignment.Center)");
		Text t = Text.build(sr);
		assertEquals(0, t.getOrigin().getX(), 0.0001);
		assertTrue( t.isVisible());
		assertEquals(0, t.getRotation(), 0.0001);
		assertEquals(-150, t.getExtent().getP1().getX(), 0.0001);
		assertEquals(40, t.getExtent().getP2().getY(), 0.0001);
		assertEquals(0, t.getFillColor().getRed());
		assertEquals(0, t.getFillColor().getGreen());
		assertEquals(0, t.getFillColor().getBlue());
		assertEquals(FillPattern.None, t.getFillPattern());
		assertEquals(LinePattern.Solid, t.getLinePattern());
		assertEquals("%name", t.getTextString());
		assertEquals(TextAlignment.Center, t.getHorizontalAlignment());
		assertEquals(0, t.getFontSize(), 0.0001);
	}
	
	@Test
	public void buildBitmap() throws IOException, ParseException {
		StringReader sr = new StringReader("Bitmap(true, {0.0, 0.0}, 0, {{-100, -62}, {98, 58}}, \"modelica://Modelica/Resources/Images/Mechanics/MultiBody/Visualizers/PipeWithScalarFieldIcon.png\")");
		Bitmap b = Bitmap.build(sr);
		assertEquals(0, b.getOrigin().getX(), 0.0001);
		assertTrue( b.isVisible());
		assertEquals(0, b.getRotation(), 0.0001);
		assertEquals(-100, b.getExtent().getP1().getX(), 0.0001);
		assertEquals(-62, b.getExtent().getP1().getY(), 0.0001);
		assertEquals(98, b.getExtent().getP2().getX(), 0.0001);
		assertEquals(58, b.getExtent().getP2().getY(), 0.0001);
		assertEquals("modelica://Modelica/Resources/Images/Mechanics/MultiBody/Visualizers/PipeWithScalarFieldIcon.png", b.getFilenameOrSource());
	}
	
	@Test
	public void buildPlacement() throws IOException, ParseException {
		StringReader sr = new StringReader("Placement(true,60.0,120.0,-20.0,-20.0,20.0,20.0,270.0,28.0,98.0,-20.0,-20.0,20.0,20.0,270.0)");
		Placement p = Placement.build(sr);		
		assertTrue( p.isVisible());
		
		assertEquals(60, p.getDiagOrigin().getX(), 0.0001);
		assertEquals(120, p.getDiagOrigin().getY(), 0.0001);
		assertEquals(270, p.getDiagRotation(), 0.0001);
		assertEquals(-20, p.getDiagTransformation().getP1().getX(), 0.0001);
		assertEquals(-20, p.getDiagTransformation().getP1().getY(), 0.0001);
		assertEquals(20, p.getDiagTransformation().getP2().getX(), 0.0001);
		assertEquals(20, p.getDiagTransformation().getP2().getY(), 0.0001);
		
		assertEquals(28, p.getIconOrigin().getX(), 0.0001);
		assertEquals(98, p.getIconOrigin().getY(), 0.0001);
		assertEquals(270, p.getIconRotation(), 0.0001);
		assertEquals(-20, p.getIconTransformation().getP1().getX(), 0.0001);
		assertEquals(-20, p.getIconTransformation().getP1().getY(), 0.0001);
		assertEquals(20, p.getIconTransformation().getP2().getX(), 0.0001);
		assertEquals(20, p.getIconTransformation().getP2().getY(), 0.0001);
	}
	
	@Test
	public void buildPlacementNoicon() throws IOException, ParseException {
		StringReader sr = new StringReader("Placement(true,-60.0,-120.0,-20.0,-20.0,20.0,20.0,90.0,-,-,-,-,-,-,)");
		Placement p = Placement.build(sr);		
		assertTrue( p.isVisible());
		
		assertEquals(-60, p.getDiagOrigin().getX(), 0.0001);
		assertEquals(-120, p.getDiagOrigin().getY(), 0.0001);
		assertEquals(90, p.getDiagRotation(), 0.0001);
		assertEquals(-20, p.getDiagTransformation().getP1().getX(), 0.0001);
		assertEquals(-20, p.getDiagTransformation().getP1().getY(), 0.0001);
		assertEquals(20, p.getDiagTransformation().getP2().getX(), 0.0001);
		assertEquals(20, p.getDiagTransformation().getP2().getY(), 0.0001);
		
		assertTrue(Double.isNaN(p.getIconOrigin().getX()));
		assertTrue(Double.isNaN(p.getIconOrigin().getY()));
		assertTrue(Double.isNaN(p.getIconTransformation().getP1().getX()));
		assertTrue(Double.isNaN(p.getIconTransformation().getP1().getY()));
		assertTrue(Double.isNaN(p.getIconTransformation().getP2().getX()));
		assertTrue(Double.isNaN(p.getIconTransformation().getP2().getY()));	
		assertEquals(0.0,p.getIconRotation(),0.0001);
	}
	@Test
	public void buildPlacementDiscreteSiSo() throws IOException, ParseException {
		StringReader sr = new StringReader("Placement(true,-,-,100.0,-10.0,120.0,10.0,-,-,-,-,-,-,-,)");
		Placement p = Placement.build(sr);		
		assertTrue( p.isVisible());
		
		assertTrue(Double.isNaN(p.getDiagOrigin().getX()));
		assertTrue(Double.isNaN(p.getDiagOrigin().getY()));
		assertEquals(100, p.getDiagTransformation().getP1().getX(), 0.0001);
		assertEquals(-10, p.getDiagTransformation().getP1().getY(), 0.0001);
		assertEquals(120, p.getDiagTransformation().getP2().getX(), 0.0001);
		assertEquals(10, p.getDiagTransformation().getP2().getY(), 0.0001);
		
		assertTrue(Double.isNaN(p.getIconOrigin().getX()));
		assertTrue(Double.isNaN(p.getIconOrigin().getY()));
		assertTrue(Double.isNaN(p.getIconTransformation().getP1().getX()));
		assertTrue(Double.isNaN(p.getIconTransformation().getP1().getY()));
		assertTrue(Double.isNaN(p.getIconTransformation().getP2().getX()));
		assertTrue(Double.isNaN(p.getIconTransformation().getP2().getY()));
		assertEquals(0.0,p.getIconRotation(),0.0001);
	}	
}
