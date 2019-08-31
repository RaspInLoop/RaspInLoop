package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.raspinloop.server.modelica.annotations.Text;

public class SvgTextBuilder implements SvgGraphicItemBuilder {

	private DrawinUnitConverter duc;
	private Text g;

	public SvgTextBuilder(DrawinUnitConverter duc, Text graphicItem) {
		this.duc = duc;
		this.g = graphicItem;
	}

	@Override
	public void build(XMLStreamWriter writer) throws XMLStreamException {
		double fontsize =  g.getFontSize();
		if (fontsize == 0) {
			fontsize = g.getExtent().getHeight();
		}
		
		String alignement = "text-align: ";
		switch (g.getHorizontalAlignment()) {
		case Center:
			alignement+= " center;";
			break;
		case Left:
			alignement+= " left;";
			break;
		case Right:			
		default:
			alignement+= " right;";
			break;			
		}
		
		writer.writeStartElement("text");
		writer.writeAttribute("transform"," translate("+ duc.convert(g.getOrigin())+") rotate("+(int)-g.getRotation()+")");
		writer.writeAttribute("x",duc.convertX(g.getExtent().getLower().getX()));
		writer.writeAttribute("y",duc.convertY(g.getExtent().getLower().getY()));			
		writer.writeAttribute("style","font: "+duc.convert(fontsize)+"px sans-serif;" + alignement);	
		writer.writeAttribute("fill", g.getLineColor().toRgbString());		
		// We do not display text as it is not yet correctly handled (especially size an font)
		//writer.writeCData(g.getTextString());		
		writer.writeEndElement();
	}
}
