package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.raspinloop.server.modelica.annotations.Line;

public class SvgLineBuilder extends PathBased implements SvgGraphicItemBuilder {

	private DrawinUnitConverter duc;
	private Line g;
	private StyleBuilder styleBuilder;

	public SvgLineBuilder(DrawinUnitConverter duc, StyleBuilder styleBuilder, Line graphicItem) {
		super( duc, graphicItem.getPoints(), graphicItem.getSmooth());
		this.duc = duc;
		this.styleBuilder = styleBuilder;
		this.g = graphicItem;
	}

	@Override
	public void build(XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("path");
		writer.writeAttribute("transform", "translate("+ duc.convert(g.getOrigin())+") rotate("+(int)-g.getRotation()+")");
		
		String pathOrigin = getPathOrigin();		
		String pathPoint = getPathPoints();
				
		writer.writeAttribute("d",pathOrigin+pathPoint);
		writer.writeAttribute("fill","transparent");
			
		styleBuilder.build(writer);

		writer.writeEndElement();
	}


	

}
