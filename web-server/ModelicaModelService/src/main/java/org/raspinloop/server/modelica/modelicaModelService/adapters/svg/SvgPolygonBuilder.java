package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.raspinloop.server.modelica.annotations.Polygon;

public class SvgPolygonBuilder extends PathBased implements SvgGraphicItemBuilder {

	private DrawinUnitConverter duc;
	private StyleBuilder stylebuilder;
	private Polygon g;

	public SvgPolygonBuilder(DrawinUnitConverter duc, StyleBuilder stylebuilder, Polygon graphicItem) {
		super( duc, graphicItem.getPoints(), graphicItem.getSmooth());
		this.duc = duc;
		this.stylebuilder = stylebuilder;
		this.g = graphicItem;
	}

	@Override
	public void build(XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("path");
		writer.writeAttribute("transform","translate("+ duc.convert(g.getOrigin())+") rotate("+(int)-g.getRotation()+")");
		
		String pathOrigin = getPathOrigin();		
		String pathPoint = getPathPoints();
		String pathEnd = "Z";
		writer.writeAttribute("d",pathOrigin+pathPoint+pathEnd);

			
		stylebuilder.build(writer);
		writer.writeEndElement();
	}

}
