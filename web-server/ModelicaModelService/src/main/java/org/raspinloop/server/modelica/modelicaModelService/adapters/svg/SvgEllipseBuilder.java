package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.raspinloop.server.modelica.annotations.Ellipse;
import org.raspinloop.server.modelica.annotations.Point;

public class SvgEllipseBuilder implements SvgGraphicItemBuilder {

	private Ellipse g;
	private DrawinUnitConverter duc;
	private StyleBuilder stylebuilder;
	
	public SvgEllipseBuilder(DrawinUnitConverter duc, StyleBuilder stylebuilder, Ellipse graphicItem) {
		this.stylebuilder = stylebuilder;
		this.g = graphicItem;
		this.duc = duc; 
	}

	@Override
	public void build(XMLStreamWriter writer) throws XMLStreamException {
		 
		if (g.getStartAngle() == 0.0 && g.getEndAngle() == 360.0 ) {
			drawEclipse(writer);
		} else {
			drawArc(writer);
		}
	}
	
	public void drawArc(XMLStreamWriter writer) throws XMLStreamException {
		
		writer.writeStartElement("path");
		writer.writeAttribute("transform", "translate("+ duc.convert(g.getOrigin())+") rotate("+(int)-g.getRotation()+")");		
		writer.writeAttribute("d", describeArc(g.getExtent().getCenter(), 
											   g.getExtent().getWidth()/2, 
											   g.getExtent().getHeight()/2, 
											   g.getStartAngle(), g.getEndAngle()));	
		stylebuilder.build(writer);
		writer.writeEndElement();
	}
	
	public void drawEclipse(XMLStreamWriter writer) throws XMLStreamException {
		
		writer.writeStartElement("ellipse");
		writer.writeAttribute("transform", "translate("+ duc.convert(g.getOrigin())+") rotate("+(int)-g.getRotation()+")");
		writer.writeAttribute("cx",duc.convertX(g.getExtent().getCenter().getX()));
		writer.writeAttribute("cy",duc.convertY(g.getExtent().getCenter().getY()));	
		writer.writeAttribute("rx",duc.convert(g.getExtent().getWidth()/2));
		writer.writeAttribute("ry",duc.convert(g.getExtent().getHeight()/2));		
		stylebuilder.build(writer);
		writer.writeEndElement();
	}

	private Point polarToCartesian(Point center, double radiusx, double radiusy, double angleInDegrees) {
		  double angleInRadians = (angleInDegrees) * Math.PI / 180.0;

		  return new Point(
		    center.getX() + (radiusx * Math.cos(angleInRadians)),
		    center.getY() + (radiusy * Math.sin(angleInRadians)));
		}

	
	private String describeArc(Point center, double radiusx, double radiusy, double startAngle, double endAngle) {

	    Point start = polarToCartesian(center, radiusx, radiusy, startAngle);
	    Point end = polarToCartesian(center, radiusx, radiusy, endAngle);

	    String largeArcFlag = endAngle - startAngle <= 180 ? "0" : "1";

	 String d= "M" + duc.convert(start) + 
	        "A" + duc.convert(radiusx)+ " "+duc.convert(radiusy) +" 0 "+ largeArcFlag+" 0 "+ duc.convert(end)+
	        "L" + duc.convert(center) + " Z";

	    return d;       
	}
}
