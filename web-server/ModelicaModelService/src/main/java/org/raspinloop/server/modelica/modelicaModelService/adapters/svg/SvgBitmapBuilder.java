package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.raspinloop.server.modelica.annotations.Bitmap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SvgBitmapBuilder implements SvgGraphicItemBuilder {

	private DrawinUnitConverter duc;
	private Bitmap g;

	public SvgBitmapBuilder(DrawinUnitConverter duc,  Bitmap graphicItem) {
		this.duc = duc;
		this.g = graphicItem;
	}

	@Override
	public void build(XMLStreamWriter writer) throws XMLStreamException {
		
		String dataImg = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAABK0lEQVQ4T6XTPSjuYRjH8c/DMUkGL4uXEmXzsigTyWCTMDBR6pQTy1kUs1IGRTIqBhFJyqIzmJWTQSklp5OyMNkk3br/9ZfnefrLvdzdL9f3/l3X9btzvjlyMb4Ra2hAslcM/Q/T+J9cPsIyzjIK6sMMhhLABTqLBA/jNx7wC/f4i44E8L4oAKjFMfrRhZ8Y/QqgFYsIKqpwgJ5igB+ow11K0TYqYpHncVIIEFLaRDfGcZ6CNOMRT3Evbw2W8IoN7GMOpylIJUoi5BMgvBwKOREDqnEY/bGDFoQ5qBzDbroLV7jFIF5SL5ZjD9foxWQ830Ip2pI2Xsa8n/O0sgwLWI8+CFdqYmrtWXxQyF8fahCsvII/Ga08gCmMJArqsYqmjIAbzAZLZ/l5RZlvOH0/EdWLa3MAAAAASUVORK5CYII=";
		
		writer.writeStartElement("image");
		writer.writeAttribute("transform", "translate("+ duc.convert(g.getOrigin())+") rotate("+(int)-g.getRotation()+")");
		writer.writeAttribute("x",duc.convertX(g.getExtent().getLower().getX()));
		writer.writeAttribute("y",duc.convertY(g.getExtent().getLower().getY()));		
		writer.writeAttribute("xlink:href",dataImg );
		log.warn("Conversion of Bitmap {} to SVG is not implemented yet", g.getFilenameOrSource());
		writer.writeEndElement();
	}

}
