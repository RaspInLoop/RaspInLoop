package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import java.util.UUID;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang3.StringUtils;
import org.raspinloop.server.modelica.annotations.Arrow;
import org.raspinloop.server.modelica.annotations.Color;
import org.raspinloop.server.modelica.annotations.LinePattern;

public class LineStyleBuilder implements StyleBuilder {

	private String arrowStartName;
	private String arrowEndName;
	private DrawinUnitConverter duc;
	private LinePattern patern;
	private Color color;
	private double thickness;
	private Arrow[] arrows;
	private String[] arrowsName = new String[2];
	private double arrowSize;

	public LineStyleBuilder(LinePattern patern, Color color, double thickness, Arrow[] arrows, double arrowSize, DrawinUnitConverter duc) {
		this.patern = patern;
		this.color = color;
		this.thickness = thickness;
		this.arrows = arrows;
		this.arrowSize = arrowSize;
		this.duc = duc;

	}

	@Override
	public void build(XMLStreamWriter writer) throws XMLStreamException {
		StringBuilder style = new StringBuilder();
		style.append("stroke:" + color.toRgbString() + ";");
		switch (patern) {
		case Dash:
			style.append("stroke-dasharray:5;");
			style.append("stroke-width:" + duc.convertThickness(thickness) + ";");
			break;
		case DashDot:
			style.append("stroke-dasharray:5 3 2 3;");
			style.append("stroke-width:" + duc.convertThickness(thickness) + ";");
			break;
		case DashDotDot:
			style.append("stroke-dasharray:5 3 2 3 2 3;");
			style.append("stroke-width:" + duc.convertThickness(thickness) + ";");
			break;
		case Dot:
			style.append("stroke-dasharray: 2 2;");
			style.append("stroke-width:" + duc.convertThickness(thickness) + ";");
			break;
		case None:
			style.append("stroke-width:0;");
			break;
		case Solid:
		default:
			style.append("stroke-width:" + duc.convertThickness(thickness) + ";");
			break;
		}

		if (StringUtils.isNotBlank(arrowStartName)) {
			writer.writeAttribute("marker-start", "url(#" + arrowStartName + ")");
		}
		if (StringUtils.isNotBlank(arrowEndName)) {
			writer.writeAttribute("marker-end", "url(#" + arrowEndName + ")");
		}

		writer.writeAttribute("style", style.toString());
	}

	public void addArrows(XMLStreamWriter writer) throws XMLStreamException {
		for (int i = 0; i < arrows.length; i++) {					
			switch (arrows[i]) {
			case Filled:
				arrowsName[i] = addFilledArrowMarker(writer);
				break;
			case Half:
				arrowsName[i] =  addHalfArrowMarker(writer);
				break;
			case None:
				arrowsName[i] = "";
				break;
			case Open:
				arrowsName[i] = addOpenArrowMarker(writer);
				break;
			default:
				break;		
			}
		}
	}
			
	private String addArrowMarker(XMLStreamWriter writer, String path) throws XMLStreamException {
		String name = UUID.randomUUID().toString();	
		writer.writeStartElement("marker");
		writer.writeAttribute("id", name);
		writer.writeAttribute("markerWidth", duc.convert(arrowSize));
		writer.writeAttribute("markerHeight", duc.convert(arrowSize));
		writer.writeAttribute("refX", "0");
		writer.writeAttribute("refY", "3");
		writer.writeAttribute("orient", "auto");
		writer.writeAttribute("markerUnits", "userSpaceOnUse");
		writer.writeStartElement("path");
		writer.writeAttribute("d", path);
		writer.writeAttribute("fill", color.toRgbString());
		writer.writeEndElement();				
	    writer.writeEndElement();
		return name;
	}

	private String addOpenArrowMarker(XMLStreamWriter writer) throws XMLStreamException {
		return addArrowMarker(writer, "M0,0 L9,3 L0,6");
	}
	
	private String addHalfArrowMarker(XMLStreamWriter writer) throws XMLStreamException {
		return addArrowMarker(writer, "M0,0 L9,3 ");
	}

	private String addFilledArrowMarker(XMLStreamWriter writer) throws XMLStreamException {
		return addArrowMarker(writer, "M0,0 L0,6 L9,3 z");
	}

	public String getArrowStartName() {
		return arrowsName [0];
	}

	public String getArrowEndName() {
		return  arrowsName[1];
	}

}
