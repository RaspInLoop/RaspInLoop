package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.raspinloop.server.modelica.annotations.BorderPattern;
import org.raspinloop.server.modelica.annotations.Rectangle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SvgRectangleBuilder implements SvgGraphicItemBuilder {

	private DrawinUnitConverter duc;
	private StyleBuilder stylebuilder;
	private Rectangle g;

	public SvgRectangleBuilder(DrawinUnitConverter duc, StyleBuilder stylebuilder, Rectangle graphicItem) {
		this.duc = duc;
		this.stylebuilder = stylebuilder;
		this.g = graphicItem;
	}

	@Override
	public void build(XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("rect");
		writer.writeAttribute("transform", "translate("+ duc.convert(g.getOrigin())+") rotate("+(int)-g.getRotation()+")");
		writer.writeAttribute("x",duc.convertX(g.getExtent().getLower().getX()));
		writer.writeAttribute("y",duc.convertX(g.getExtent().getLower().getY()));
		writer.writeAttribute("width",duc.convert(g.getExtent().getWidth()));
		writer.writeAttribute("height",duc.convert(g.getExtent().getHeight()));	
		writer.writeAttribute("rx",duc.convertX(g.getRadius()));
		writer.writeAttribute("ry",duc.convertY(g.getRadius()));
		if (g.getBorderPattern() != BorderPattern.None)
			log.warn("Conversion of BorderPattern {} to SVG is not implemented yet", g.getBorderPattern());
		stylebuilder.build(writer);
		writer.writeEndElement();

	}

}
