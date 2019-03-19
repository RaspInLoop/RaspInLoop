package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.Test;
import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.modelica.annotations.Icon;
import org.xml.sax.SAXException;

public class SvgIconTest {

	private SvgFactory factory = new SvgFactory();
	

	@Test
	public void testBlockmath() throws IOException, XMLStreamException, ParseException, SAXException {
		StringReader sr = new StringReader(
				"{-100.0,-100.0,100.0,100.0,true,0.1,2.0,2.0,{Line(true, {0.0, 0.0}, 0, {{-80.0, -2.0}, {-68.7, 32.2}, {-61.5, 51.1}, {-55.1, 64.40000000000001}, {-49.4, 72.59999999999999}, {-43.8, 77.09999999999999}, {-38.2, 77.8}, {-32.6, 74.59999999999999}, {-26.9, 67.7}, {-21.3, 57.4}, {-14.9, 42.1}, {-6.83, 19.2}, {10.1, -32.8}, {17.3, -52.2}, {23.7, -66.2}, {29.3, -75.09999999999999}, {35.0, -80.40000000000001}, {40.6, -82.0}, {46.2, -79.59999999999999}, {51.9, -73.5}, {57.5, -63.9}, {63.9, -49.2}, {72.0, -26.8}, {80.0, -2.0}}, {95, 95, 95}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.Bezier)}}");
		String svg = factory.build(Icon.build(sr),"");
		assertTrue(svg.length() > 0);
	}
	
	@Test
	public void testComplexBlock() throws IOException, XMLStreamException, ParseException, SAXException {
		StringReader sr = new StringReader(
	"{-100.0,-100.0,100.0,100.0,true,0.1,2.0,2.0,{Rectangle(true, {0.0, 35.1488}, 0, {0, 0, 0}, {255, 255, 255}, LinePattern.Solid, FillPattern.Solid, 0.25, BorderPattern.None, {{-30.0, -20.1488}, {30.0, 20.1488}}, 0), Rectangle(true, {0.0, -34.8512}, 0, {0, 0, 0}, {128, 128, 128}, LinePattern.Solid, FillPattern.Solid, 0.25, BorderPattern.None, {{-30.0, -20.1488}, {30.0, 20.1488}}, 0), Line(true, {-51.25, 0.0}, 0, {{21.25, -35.0}, {-13.75, -35.0}, {-13.75, 35.0}, {6.25, 35.0}}, {0, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Polygon(true, {-40.0, 35.0}, 0, {0, 0, 0}, {0, 0, 0}, LinePattern.None, FillPattern.Solid, 0.25, {{10.0, 0.0}, {-5.0, 5.0}, {-5.0, -5.0}}, Smooth.None), Line(true, {51.25, 0.0}, 0, {{-21.25, 35.0}, {13.75, 35.0}, {13.75, -35.0}, {-6.25, -35.0}}, {0, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Polygon(true, {40.0, -35.0}, 0, {0, 0, 0}, {0, 0, 0}, LinePattern.None, FillPattern.Solid, 0.25, {{-10.0, 0.0}, {5.0, 5.0}, {5.0, -5.0}}, Smooth.None)}}");
		String svg = factory.build(Icon.build(sr),"");
		assertTrue(svg.length() > 0);
	}

	
	@Test
	public void testComplexMagnetic() throws IOException, XMLStreamException, ParseException, SAXException {
		StringReader sr = new StringReader(
	"{-100.0,-100.0,100.0,100.0,true,0.1,2.0,2.0,{Polygon(true, {-3.75, 0.0}, 0, {0, 0, 0}, {160, 160, 164}, LinePattern.Solid, FillPattern.Solid, 0.25, {{33.75, 50.0}, {-46.25, 50.0}, {-46.25, -50.0}, {33.75, -50.0}, {33.75, -30.0}, {-21.25, -30.0}, {-21.25, 30.0}, {33.75, 30.0}}, Smooth.None), Ellipse(true, {10.4708, 41.6771}, 0, {0, 0, 0}, {0, 0, 0}, LinePattern.Solid, FillPattern.None, 0.25, {{-86.0, -24.0}, {-78.0, -16.0}}, 0, 360), Line(true, {10.4708, 41.6771}, 0, {{-64.0, -20.0}, {-78.0, -20.0}}, {0, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {10.4708, 41.6771}, 0, {{-64.1812, -31.6229}, {-32.0, -40.0}}, {0, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {10.4708, 41.6771}, 0, {{-64.0, -20.0}, {-32.0, -28.0}}, {0, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Ellipse(true, {10.4708, 41.6771}, 0, {0, 0, 0}, {0, 0, 0}, LinePattern.Solid, FillPattern.None, 0.25, {{-86.0, -60.0}, {-78.0, -52.0}}, 0, 360), Line(true, {10.4708, 41.6771}, 0, {{-64.0, -56.0}, {-78.0, -56.0}}, {0, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {10.4708, 41.6771}, 0, {{-64.0, -44.0}, {-32.0, -52.0}}, {0, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {10.4708, 41.6771}, 0, {{-64.0, -56.0}, {-32.0, -64.0}}, {0, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Rectangle(true, {62.5, 0.0}, 0, {0, 0, 0}, {160, 160, 164}, LinePattern.Solid, FillPattern.Solid, 0.25, BorderPattern.None, {{-12.5, -50.0}, {12.5, 50.0}}, 0)}}");
		String svg = factory.build(Icon.build(sr),"");
		assertTrue(svg.length() > 0);
	}	
	
	@Test
	public void testModelicaThermalHeatTransferComponentsConvection() throws IOException, XMLStreamException, ParseException, SAXException {
		StringReader sr = new StringReader(
	"{-100.0,-100.0,100.0,100.0,true,0.1,2.0,2.0,{Rectangle(true, {0.0, 0.0}, 0, {255, 255, 255}, {255, 255, 255}, LinePattern.Solid, FillPattern.Solid, 0.25, BorderPattern.None, {{-62, 80}, {98, -80}}, 0), Rectangle(true, {0.0, 0.0}, 0, {0, 0, 0}, {192, 192, 192}, LinePattern.Solid, FillPattern.Backward, 0.25, BorderPattern.None, {{-90, 80}, {-60, -80}}, 0), Text(true, {0.0, 0.0}, 0, {0, 0, 255}, {0, 0, 0}, LinePattern.Solid, FillPattern.None, 0.25, {{-150, -90}, {150, -130}}, \"%name\", 0, TextAlignment.Center), Line(true, {0.0, 0.0}, 0, {{100, 0}, {100, 0}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{-60, 20}, {76, 20}}, {191, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{-60, -20}, {76, -20}}, {191, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{-34, 80}, {-34, -80}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{6, 80}, {6, -80}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{40, 80}, {40, -80}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{76, 80}, {76, -80}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{-34, -80}, {-44, -60}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{-34, -80}, {-24, -60}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{6, -80}, {-4, -60}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{6, -80}, {16, -60}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{40, -80}, {30, -60}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{40, -80}, {50, -60}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{76, -80}, {66, -60}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{76, -80}, {86, -60}}, {0, 127, 255}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{56, -30}, {76, -20}}, {191, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{56, -10}, {76, -20}}, {191, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{56, 10}, {76, 20}}, {191, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Line(true, {0.0, 0.0}, 0, {{56, 30}, {76, 20}}, {191, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Text(true, {0.0, 0.0}, 0, {0, 0, 0}, {0, 0, 0}, LinePattern.Solid, FillPattern.None, 0.25, {{22, 124}, {92, 98}}, \"Gc\", 0, TextAlignment.Center)}}");
		String svg = factory.build(Icon.build(sr),"");
		assertTrue(svg.length() > 0);
	}

	@Test
	public void testModelicaIconsUtilitiesPackage() throws IOException, XMLStreamException, ParseException, SAXException {
		StringReader sr = new StringReader(
	"{-100.0,-100.0,100.0,100.0,true,0.1,2.0,2.0,{Polygon(true, {1.3835, -4.1418}, 45.0, {0, 0, 0}, {64, 64, 64}, LinePattern.None, FillPattern.Solid, 0.25, {{-15.0, 93.333}, {-15.0, 68.333}, {0.0, 58.333}, {15.0, 68.333}, {15.0, 93.333}, {20.0, 93.333}, {25.0, 83.333}, {25.0, 58.333}, {10.0, 43.333}, {10.0, -41.667}, {25.0, -56.667}, {25.0, -76.667}, {10.0, -91.667}, {0.0, -91.667}, {0.0, -81.667}, {5.0, -81.667}, {15.0, -71.667}, {15.0, -61.667}, {5.0, -51.667}, {-5.0, -51.667}, {-15.0, -61.667}, {-15.0, -71.667}, {-5.0, -81.667}, {0.0, -81.667}, {0.0, -91.667}, {-10.0, -91.667}, {-25.0, -76.667}, {-25.0, -56.667}, {-10.0, -41.667}, {-10.0, 43.333}, {-25.0, 58.333}, {-25.0, 83.333}, {-20.0, 93.333}}, Smooth.None), Polygon(true, {10.1018, 5.218}, -45.0, {0, 0, 0}, {255, 255, 255}, LinePattern.Solid, FillPattern.Solid, 0.25, {{-15.0, 87.273}, {15.0, 87.273}, {20.0, 82.273}, {20.0, 27.273}, {10.0, 17.273}, {10.0, 7.273}, {20.0, 2.273}, {20.0, -2.727}, {5.0, -2.727}, {5.0, -77.727}, {10.0, -87.727}, {5.0, -112.727}, {-5.0, -112.727}, {-10.0, -87.727}, {-5.0, -77.727}, {-5.0, -2.727}, {-20.0, -2.727}, {-20.0, 2.273}, {-10.0, 7.273}, {-10.0, 17.273}, {-20.0, 27.273}, {-20.0, 82.273}}, Smooth.None)}}");
		String svg = factory.build(Icon.build(sr),"");
		assertTrue(svg.length() > 0);
	}
	
	@Test
	public void testModelicaIconsSensors() throws IOException, XMLStreamException, ParseException, SAXException {
		StringReader sr = new StringReader(
	"{-100.0,-100.0,100.0,100.0,false,0.1,2.0,2.0,{Ellipse(true, {0.0, -30.0}, 0, {0, 0, 0}, {255, 255, 255}, LinePattern.Solid, FillPattern.None, 0.25, {{-90.0, -90.0}, {90.0, 90.0}}, 20.0, 160.0), Ellipse(true, {0.0, -30.0}, 0, {0, 0, 0}, {128, 128, 128}, LinePattern.None, FillPattern.Solid, 0.25, {{-20.0, -20.0}, {20.0, 20.0}}, 0, 360), Line(true, {0.0, -30.0}, 0, {{0.0, 60.0}, {0.0, 90.0}}, {0, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None), Ellipse(true, {-0.0, -30.0}, 0, {0, 0, 0}, {64, 64, 64}, LinePattern.None, FillPattern.Solid, 0.25, {{-10.0, -10.0}, {10.0, 10.0}}, 0, 360), Polygon(true, {-0.0, -30.0}, -35.0, {0, 0, 0}, {64, 64, 64}, LinePattern.None, FillPattern.Solid, 0.25, {{-7.0, 0.0}, {-3.0, 85.0}, {0.0, 90.0}, {3.0, 85.0}, {7.0, 0.0}}, Smooth.None)}}");
		String svg = factory.build(Icon.build(sr),"");
		assertTrue(svg.length() > 0);
	}
	
	@Test
	public void testModelicaIconsMotor() throws IOException, XMLStreamException, ParseException, SAXException {
		StringReader sr = new StringReader(
	"{-100.0,-100.0,100.0,100.0,true,0.1,2.0,2.0,{Rectangle(true, {0.0, 0.0}, 0, {82, 0, 2}, {252, 37, 57}, LinePattern.Solid, FillPattern.HorizontalCylinder, 0.25, BorderPattern.None, {{-100.0, -50.0}, {30.0, 50.0}}, 10.0), Polygon(true, {0.0, 0.0}, 0, {0, 0, 0}, {64, 64, 64}, LinePattern.Solid, FillPattern.Solid, 0.25, {{-100.0, -90.0}, {-90.0, -90.0}, {-60.0, -20.0}, {-10.0, -20.0}, {20.0, -90.0}, {30.0, -90.0}, {30.0, -100.0}, {-100.0, -100.0}, {-100.0, -90.0}}, Smooth.None), Rectangle(true, {0.0, 0.0}, 0, {64, 64, 64}, {255, 255, 255}, LinePattern.Solid, FillPattern.HorizontalCylinder, 0.25, BorderPattern.None, {{30.0, -10.0}, {90.0, 10.0}}, 0)}}");
		String svg = factory.build(Icon.build(sr),"");
		assertTrue(svg.length() > 0);
	}

	
	@Test
	public void testModelicaDiagram() throws IOException, XMLStreamException, ParseException, SAXException {
		StringReader sr = new StringReader(
	"{-100.0,-100.0,100.0,100.0,true,0.1,2.0,2.0,{Polygon(true, {0.0, 0.0}, 0, {160, 160, 164}, {192, 192, 192}, LinePattern.Solid, FillPattern.Solid, 0.25, {{0, 67}, {-20, 63}, {-40, 57}, {-52, 43}, {-58, 35}, {-68, 25}, {-72, 13}, {-76, -1}, {-78, -15}, {-76, -31}, {-76, -43}, {-76, -53}, {-70, -65}, {-64, -73}, {-48, -77}, {-30, -83}, {-18, -83}, {-2, -85}, {8, -89}, {22, -89}, {32, -87}, {42, -81}, {54, -75}, {56, -73}, {66, -61}, {68, -53}, {70, -51}, {72, -35}, {76, -21}, {78, -13}, {78, 3}, {74, 15}, {66, 25}, {54, 33}, {44, 41}, {36, 57}, {26, 65}, {0, 67}}, Smooth.None), Polygon(true, {0.0, 0.0}, 0, {0, 0, 0}, {160, 160, 164}, LinePattern.Solid, FillPattern.Solid, 0.25, {{-58, 35}, {-68, 25}, {-72, 13}, {-76, -1}, {-78, -15}, {-76, -31}, {-76, -43}, {-76, -53}, {-70, -65}, {-64, -73}, {-48, -77}, {-30, -83}, {-18, -83}, {-2, -85}, {8, -89}, {22, -89}, {32, -87}, {42, -81}, {54, -75}, {42, -77}, {40, -77}, {30, -79}, {20, -81}, {18, -81}, {10, -81}, {2, -77}, {-12, -73}, {-22, -73}, {-30, -71}, {-40, -65}, {-50, -55}, {-56, -43}, {-58, -35}, {-58, -25}, {-60, -13}, {-60, -5}, {-60, 7}, {-58, 17}, {-56, 19}, {-52, 27}, {-48, 35}, {-44, 45}, {-40, 57}, {-58, 35}}, Smooth.None), Ellipse(true, {0.0, 0.0}, 0, {255, 0, 0}, {191, 0, 0}, LinePattern.Solid, FillPattern.Solid, 0.25, {{-6, -1}, {6, -12}}, 0, 360), Text(true, {0.0, 0.0}, 0, {0, 0, 0}, {0, 0, 0}, LinePattern.Solid, FillPattern.None, 0.25, {{11, 13}, {50, -25}}, \"T\", 0, TextAlignment.Center), Line(true, {0.0, 0.0}, 0, {{0, -12}, {0, -96}}, {255, 0, 0}, LinePattern.Solid, 0.25, {Arrow.None, Arrow.None}, 3, Smooth.None)}}");
		String svg = factory.build(Icon.build(sr),"");
		assertTrue(svg.length() > 0);
	}


}
