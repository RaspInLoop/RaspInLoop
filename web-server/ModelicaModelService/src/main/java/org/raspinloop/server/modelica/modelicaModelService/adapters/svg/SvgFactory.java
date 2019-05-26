package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang3.StringUtils;
import org.raspinloop.server.modelica.annotations.Bitmap;
import org.raspinloop.server.modelica.annotations.Ellipse;
import org.raspinloop.server.modelica.annotations.FilledShape;
import org.raspinloop.server.modelica.annotations.GraphicItem;
import org.raspinloop.server.modelica.annotations.Icon;
import org.raspinloop.server.modelica.annotations.Line;
import org.raspinloop.server.modelica.annotations.Polygon;
import org.raspinloop.server.modelica.annotations.Rectangle;
import org.raspinloop.server.modelica.annotations.Text;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SvgFactory {
	XMLOutputFactory output = XMLOutputFactory.newInstance();

	/**
	 * 
	 * @param icons
	 *            , a list of Icon object build form modelica annotation
	 * @param cssClass
	 * @return the SVG representation of the Icon
	 * @throws IOException
	 * @throws XMLStreamException
	 */
	public String build(List<Icon> icons, String cssClass) throws IOException, XMLStreamException {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		XMLStreamWriter writer = output.createXMLStreamWriter(stream, "UTF-8");

		writer.writeStartDocument();
		if (icons.size()>1){
			writer.writeStartElement("g");
			icons.forEach(i -> build(i, cssClass, writer));
			writer.writeEndElement();
		} else {
			icons.forEach(i -> build(i, cssClass, writer));
		}		
			
		writer.writeEndDocument();
		writer.flush();
		return stream.toString();
	}

	private void build(Icon i, String cssClass, XMLStreamWriter writer) {
		try {
			writer.writeStartElement("g");
			if (i != null) {
				DrawinUnitConverter duc = new DrawinUnitConverter(i.getCoordinateSystem());
				// Extent size = i.getCoordinateSystem().getExtent();
				// writer.writeAttribute("viewBox",
				// duc.convert(size.getLower().getX()) + " " +
				// duc.convert(size.getLower().getY()) +" " +
				// duc.convert(size.getWidth())+" "+
				// duc.convert(size.getHeight()));
				if (StringUtils.isNotBlank(cssClass)) {
					writer.writeAttribute("class", cssClass);
				}

				// add custom defs for Hatching pattern, arrows, etc...
				List<SvgGraphicItemBuilder> builders = new ArrayList<>();
				writer.writeStartElement("defs");
				for (GraphicItem graphicItem : i.getGraphics()) {

					StyleBuilder stylebuilder = null;
					if (graphicItem instanceof FilledShape) {
						stylebuilder = new FilledShapeStyleBuilder((FilledShape) graphicItem, duc);
						((FilledShapeStyleBuilder) stylebuilder).addPattern(writer); // if
																						// any
					} else if (graphicItem instanceof Line) {
						Line l = (Line) graphicItem;
						stylebuilder = new LineStyleBuilder(l.getPattern(), l.getColor(), l.getThickness(), l.getArrow(), l.getArrowSize(), duc);
						((LineStyleBuilder) stylebuilder).addArrows(writer);
					}
					builders.add(findgraphicItemBuilder(graphicItem, stylebuilder, duc));
				}
				writer.writeEndElement();
				// let's build graphics
				builders.forEach(b -> {
					try {
						b.build(writer);
					} catch (XMLStreamException e) {
						log.error("Cannot build SVG for Icon grahicItem: {}", e.getMessage());
					}
				});
			}
			writer.writeEndElement();
		} catch (XMLStreamException e) {
			log.error("Cannot build SVG for Icon grahicItem: {}", e.getMessage());
		}
	}

	private SvgGraphicItemBuilder findgraphicItemBuilder(GraphicItem graphicItem, StyleBuilder stylebuilder, DrawinUnitConverter duc) {
		if (graphicItem instanceof Line)
			return new SvgLineBuilder(duc, stylebuilder, (Line) graphicItem);
		else if (graphicItem instanceof Polygon)
			return new SvgPolygonBuilder(duc, stylebuilder, (Polygon) graphicItem);
		else if (graphicItem instanceof Rectangle)
			return new SvgRectangleBuilder(duc, stylebuilder, (Rectangle) graphicItem);
		else if (graphicItem instanceof Ellipse)
			return new SvgEllipseBuilder(duc, stylebuilder, (Ellipse) graphicItem);
		else if (graphicItem instanceof Text)
			return new SvgTextBuilder(duc, (Text) graphicItem);
		else if (graphicItem instanceof Bitmap)
			return new SvgBitmapBuilder(duc, (Bitmap) graphicItem);
		return null;
	}
}
