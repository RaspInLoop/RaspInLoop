package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang3.StringUtils;
import org.raspinloop.server.modelica.annotations.Color;
import org.raspinloop.server.modelica.annotations.FillPattern;
import org.raspinloop.server.modelica.annotations.FilledShape;

public class FilledShapeStyleBuilder  implements StyleBuilder{

	private FilledShape f;
	private String patternName;
	private DrawinUnitConverter duc;

	public FilledShapeStyleBuilder(FilledShape f, DrawinUnitConverter duc) {
		this.f = f;
		this.duc = duc;
		
	}

	@Override
	public void build(XMLStreamWriter writer) throws XMLStreamException {
		StringBuilder style = new StringBuilder();
        style.append("stroke:"+f.getLineColor().toRgbString()+";");        
        switch (f.getLinePattern()) {
		case Dash:
			 style.append("stroke-dasharray:5;");
			 style.append("stroke-width:"+duc.convertThickness(f.getLineThickness())+";");
			break;
		case DashDot:
			style.append("stroke-dasharray:5 3 2 3;");
			style.append("stroke-width:"+duc.convertThickness(f.getLineThickness())+";");
			break;
		case DashDotDot:
			style.append("stroke-dasharray:5 3 2 3 2 3;");
			style.append("stroke-width:"+duc.convertThickness(f.getLineThickness())+";");
			break;
		case Dot:
			style.append("stroke-dasharray: 2 2;");
			style.append("stroke-width:"+duc.convertThickness(f.getLineThickness())+";");
			break;
		case None:		
			style.append("stroke-width:0;");
			break;
		case Solid:			
		default:
			style.append("stroke-width:"+duc.convertThickness(f.getLineThickness())+";");
			break;        
        }	
		
		if (StringUtils.isNotBlank(patternName))
			style.append("fill:url(#"+patternName+");");
		else if (f.getFillPattern() == FillPattern.Solid)
			style.append("fill:"+f.getFillColor().toRgbString()+";");	
		else style.append("fill:transparent;");	
		
	
		writer.writeAttribute("style", style.toString());
	}

	private String addDefBackwardPattern(XMLStreamWriter writer, Color fillColor, Color lineColor) throws XMLStreamException {
		return addDefPathPattern(writer, fillColor, lineColor, 8, Collections.singleton("M-1,1 l2,-2 M0,8 l8,-8 M7,9 l2,-2"));	
	}
	
	private String addDefForwardPattern(XMLStreamWriter writer, Color fillColor, Color lineColor) throws XMLStreamException {
		return addDefPathPattern(writer, fillColor, lineColor, 8, Collections.singleton("M7,-1 l2,2 M0,0 l8,8 M-1,7 l2,2"));				
	}
	
	private String addDefCrossDiagPattern(XMLStreamWriter writer, Color fillColor, Color lineColor) throws XMLStreamException {
		return addDefPathPattern(writer, fillColor, lineColor, 8, Arrays.asList("M7,-1 l2,2 M0,0 l8,8 M-1,7 l2,2","M-1,1 l2,-2 M0,8 l8,-8 M7,9 l2,-2") );				
	}
	
	private String addDefCrossPattern(XMLStreamWriter writer, Color fillColor, Color lineColor) throws XMLStreamException {
		return addDefPathPattern(writer, fillColor, lineColor, 8, Arrays.asList("M3,0 l0,5","M0,3 l5,0") );				
	}
	
	private String addDefVerticalPattern(XMLStreamWriter writer, Color fillColor, Color lineColor) throws XMLStreamException {		
		return addDefPathPattern(writer, fillColor, lineColor, 5, Collections.singleton("M3,0 l0,5"));		
	}
	
	private String addDefHorizontalPattern(XMLStreamWriter writer, Color fillColor, Color lineColor) throws XMLStreamException {
		return addDefPathPattern(writer, fillColor, lineColor, 5, Collections.singleton("M0,3 l5,0"));	
	}
	
	private String addDefPathPattern(XMLStreamWriter writer, Color fillColor, Color lineColor, int size, Collection<String> paths) throws XMLStreamException {
		String sizeStr = Integer.toString(size);
		String name = UUID.randomUUID().toString();		
		writer.writeStartElement("pattern");
		writer.writeAttribute("id", name);
		writer.writeAttribute("patternUnits", "userSpaceOnUse");
		writer.writeAttribute("patternTransform", "scale("+duc.convert(1.0)+")");
		writer.writeAttribute("width", sizeStr);
		writer.writeAttribute("height", sizeStr);
		writer.writeStartElement("rect");	
		writer.writeAttribute("width", sizeStr);
		writer.writeAttribute("height", sizeStr);
		writer.writeAttribute("fill", fillColor.toRgbString());
		writer.writeEndElement();	
		for (String path : paths) {
			writer.writeStartElement("path");		
			writer.writeAttribute("d" ,path);
			writer.writeAttribute("style", "stroke:"+lineColor.toRgbString()+"; stroke-width:1;");
		}		
		writer.writeEndElement();		
		writer.writeEndElement();		
		return name;
	}
	
	private String addDefHGradientPattern(XMLStreamWriter writer, Color fillColor, Color lineColor) throws XMLStreamException {		
		String name = UUID.randomUUID().toString();		
		writer.writeStartElement("linearGradient");
		writer.writeAttribute("id", name);
		writer.writeAttribute("x1", "0%");
		writer.writeAttribute("x2", "100%");
		writer.writeAttribute("y1", "0%");
		writer.writeAttribute("y2", "0%");	
		addstop(writer, "0%", lineColor.toRgbString());
		addstop(writer, "50%", fillColor.toRgbString());
		addstop(writer, "100%", lineColor.toRgbString());
		writer.writeEndElement();
		return name;
	}
	
	private String addDefVGradientPattern(XMLStreamWriter writer, Color fillColor, Color lineColor) throws XMLStreamException {		
		String name = UUID.randomUUID().toString();		
		writer.writeStartElement("linearGradient");
		writer.writeAttribute("id", name);
		writer.writeAttribute("x1", "0%");
		writer.writeAttribute("x2", "0%");
		writer.writeAttribute("y1", "0%");
		writer.writeAttribute("y2", "100%");	
		addstop(writer, "0%", lineColor.toRgbString());
		addstop(writer, "50%", fillColor.toRgbString());
		addstop(writer, "100%", lineColor.toRgbString());			
		writer.writeEndElement();
		return name;
	}
	
	private String addDefRadialGradientPattern(XMLStreamWriter writer, Color fillColor, Color lineColor) throws XMLStreamException {		
		String name = UUID.randomUUID().toString();		
		writer.writeStartElement("radialGradient");
		writer.writeAttribute("id", name);
		writer.writeAttribute("cx", "50%");
		writer.writeAttribute("cy", "50%");
		writer.writeAttribute("r", "50%");
		writer.writeAttribute("fx", "50%");	
		writer.writeAttribute("fy", "50%");	
		addstop(writer, "0%",  fillColor.toRgbString());
		addstop(writer, "100%",lineColor.toRgbString());			
		writer.writeEndElement();
		return name;
	}
	
	private void addstop(XMLStreamWriter writer, String offset, String collor) throws XMLStreamException {
		writer.writeStartElement("stop");
		writer.writeAttribute("offset", offset);
		writer.writeAttribute("stop-color", collor);
		writer.writeEndElement();		
	}

	public void addPattern(XMLStreamWriter writer) throws XMLStreamException {
		switch(f.getFillPattern()){
		case Backward:
			patternName = addDefBackwardPattern(writer, f.getFillColor(), f.getLineColor());			
			break;
		case Cross:
			patternName = addDefCrossPattern(writer, f.getFillColor(), f.getLineColor());		
			break;
		case CrossDiag:
			patternName = addDefCrossDiagPattern(writer, f.getFillColor(), f.getLineColor());	
			break;
		case Forward:
			patternName = addDefForwardPattern(writer, f.getFillColor(), f.getLineColor());					
			break;
		case Horizontal:
			patternName = addDefHorizontalPattern(writer, f.getFillColor(), f.getLineColor());
		break;
		case Vertical:
			patternName = addDefVerticalPattern(writer, f.getFillColor(), f.getLineColor());
			break;
		case Sphere:
			patternName = addDefRadialGradientPattern(writer, f.getFillColor(), f.getLineColor());			
			break;
		case VerticalCylinder:
			patternName = addDefHGradientPattern(writer, f.getFillColor(), f.getLineColor());
			break;
		case HorizontalCylinder:
			patternName = addDefVGradientPattern(writer, f.getFillColor(), f.getLineColor());
			break;
			
		case Solid:			
		case None:			
		default:
			patternName = "";
			break;		
		}
		
	}

	public String getPatternName() {	
		return patternName;
	}

}
