package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

import lombok.Value;

@Value
public class Ellipse  implements GraphicItem, FilledShape{

	private static final String GRAPHIC_NAME = "Ellipse";
	// Graphic Item
	boolean visible;
	Point origin;
	double rotation;
	
	// filledShape
	 Color lineColor;
	 Color fillColor;
	 LinePattern linePattern;
	 FillPattern fillPattern;
	 double lineThickness;
	 
	 // ellipse
	 Extent extent;
	 double startAngle;
	 double endAngle;	 
	 
	 public static Ellipse build(Reader reader) throws IOException, ParseException{
		 	ParserUtils.skipWhiteSpace(reader);
		 	reader.mark(GRAPHIC_NAME.length()+1);
			char cbuf[] = new char[GRAPHIC_NAME.length()+1];
			reader.read(cbuf);
			String s = new String(cbuf);
			if (!s.equalsIgnoreCase(GRAPHIC_NAME+"(")) {
				reader.reset();
				return null;
			}
			
			boolean visible = ParserUtils.parseBooleanToken(reader, ',');	
			Point origin = Point.build(reader);
			double rotation = ParserUtils.parseRealToken(reader, ',');
			
			 Color lineColor = Color.build(reader);
			 Color fillColor  = Color.build(reader);
			 LinePattern linePattern = LinePattern.build(reader);
			 FillPattern fillPattern = FillPattern.build(reader);
			 double lineThickness = ParserUtils.parseRealToken(reader,',');
			 Extent extent = Extent.build(reader);
			 double startAngle = ParserUtils.parseRealToken(reader,',');
			 double endAngle = ParserUtils.parseRealToken(reader);
			 if (!ParserUtils.readDelimiter(reader, ')'))
				 throw new ParseException("Could not find delimiter of end of Ellipse: ')' ");
			 
			 ParserUtils.readDelimiter(reader, ',');
			 return new Ellipse(visible, origin, rotation, lineColor, fillColor, linePattern, fillPattern, lineThickness, extent, startAngle, endAngle);
	 }
}
