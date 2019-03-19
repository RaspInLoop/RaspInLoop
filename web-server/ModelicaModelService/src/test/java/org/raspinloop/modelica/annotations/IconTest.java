package org.raspinloop.modelica.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;
import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.modelica.annotations.Bitmap;
import org.raspinloop.server.modelica.annotations.FillPattern;
import org.raspinloop.server.modelica.annotations.GraphicItem;
import org.raspinloop.server.modelica.annotations.Icon;
import org.raspinloop.server.modelica.annotations.LinePattern;
import org.raspinloop.server.modelica.annotations.Text;
import org.raspinloop.server.modelica.annotations.TextAlignment;

public class IconTest {

	@Test
	public void test() throws IOException, ParseException {
		StringReader sr = new StringReader("{-100.0,-100.0,100.0,100.0,true,0.1,2.0,2.0,{Text(true, {0.0, 0.0}, 0, {0, 0, 255}, {0, 0, 0}, LinePattern.Solid, FillPattern.None, 0.25, {{-150, 50}, {150, 90}}, \"%name\", 0, TextAlignment.Center), Bitmap(true, {0.0, 0.0}, 0, {{-100, -62}, {98, 58}}, \"modelica://Modelica/Resources/Images/Mechanics/MultiBody/Visualizers/PipeWithScalarFieldIcon.png\")}}");
		Icon i = Icon.build(sr);
		
		assertTrue(i.getCoordinateSystem().isPreserveAspectRatio());
		assertEquals(0.1,i.getCoordinateSystem().getInitialScale(), 0.0001);
		assertEquals(2,i.getCoordinateSystem().getDrawingUnitX(), 0.0001);
		assertEquals(2,i.getCoordinateSystem().getDrawingUnitY(), 0.0001);
		
		assertEquals(-100, i.getCoordinateSystem().getExtent().getP1().getX(), 0.0001);
		assertEquals( 100, i.getCoordinateSystem().getExtent().getP2().getY(), 0.0001);
		
		List<GraphicItem> gaphics = i.getGraphics();
		assertEquals(2, gaphics.size());
		if (gaphics.get(0) instanceof Text){
			Text t = (Text)gaphics.get(0);
			assertEquals(0, t.getOrigin().getX(), 0.0001);
			assertTrue( t.isVisible());
			assertEquals(0, t.getRotation(), 0.0001);
			assertEquals(-150, t.getExtent().getP1().getX(), 0.0001);
			assertEquals(90, t.getExtent().getP2().getY(), 0.0001);
			assertEquals(0, t.getFillColor().getRed());
			assertEquals(0, t.getFillColor().getGreen());
			assertEquals(0, t.getFillColor().getBlue());
			assertEquals(FillPattern.None, t.getFillPattern());
			assertEquals(LinePattern.Solid, t.getLinePattern());
			assertEquals("%name", t.getTextString());
			assertEquals(TextAlignment.Center, t.getHorizontalAlignment());
			assertEquals(0, t.getFontSize(), 0.0001);
		}
		
		if (gaphics.get(1) instanceof Bitmap){
			Bitmap b = (Bitmap)gaphics.get(1);
			assertEquals(0, b.getOrigin().getX(), 0.0001);
			assertTrue( b.isVisible());
			assertEquals(0, b.getRotation(), 0.0001);
			assertEquals(-100, b.getExtent().getP1().getX(), 0.0001);
			assertEquals(58, b.getExtent().getP2().getY(), 0.0001);
			assertEquals("modelica://Modelica/Resources/Images/Mechanics/MultiBody/Visualizers/PipeWithScalarFieldIcon.png", b.getFilenameOrSource());
		}
	}
}
