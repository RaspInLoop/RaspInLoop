package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

import lombok.Value;

@Value
public class Rectangle implements GraphicItem, FilledShape{

	private static final String GRAPHIC_NAME = "Rectangle";
	boolean visible;
	Point origin;
	double rotation;
	
	 Color lineColor;
	 Color fillColor;
	 LinePattern linePattern;
	 FillPattern fillPattern;
	 double lineThickness;
	 BorderPattern borderPattern;
	 Extent extent;
	 double radius;
	 
	 public static Rectangle build(Reader reader) throws IOException, ParseException {
		ParserUtils.skipWhiteSpace(reader);
		reader.mark(GRAPHIC_NAME.length() + 1);
		char cbuf[] = new char[GRAPHIC_NAME.length() + 1];
		reader.read(cbuf);
		String s = new String(cbuf);
		if (!s.equalsIgnoreCase(GRAPHIC_NAME + "(")) {
			reader.reset();
			return null;
		}
		
		boolean visible = ParserUtils.parseBooleanToken(reader, ',');	
		Point origin = Point.build(reader);
		double rotation = ParserUtils.parseRealToken(reader, ',');
		
		 Color lineColor = Color.build(reader);
		 Color fillColor  = Color.build(reader);
		 LinePattern pattern = LinePattern.build(reader);
		 FillPattern fillPattern = FillPattern.build(reader);
		 double lineThickness = ParserUtils.parseRealToken(reader,',');
		 BorderPattern borderPattern = BorderPattern.build(reader);
		 Extent extent = Extent.build(reader);
		 double radius = ParserUtils.parseRealToken(reader);
		 if (!ParserUtils.readDelimiter(reader, ')'))
			 throw new ParseException("Could not find delimiter of end of Rectangle: ')' ");
		 
		 ParserUtils.readDelimiter(reader, ',');
		
		return new Rectangle(visible, origin, rotation, lineColor, fillColor, pattern, fillPattern, lineThickness, borderPattern, extent, radius);
	 }
}
