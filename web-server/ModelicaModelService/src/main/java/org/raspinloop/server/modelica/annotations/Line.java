package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.openmodelica.corba.parser.ParseException;

import lombok.Value;

@Value
public class Line implements GraphicItem{

	private static final String GRAPHIC_NAME = "Line";
	// Graphic Item
		boolean visible;
		Point origin;
		double rotation;
		
	// line
	List<Point> points;
	Color color;
	LinePattern pattern;
	double thickness;
	Arrow arrow[];
	double arrowSize;
	Smooth smooth;
	
	public static Line build(Reader reader) throws IOException, ParseException {
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
		
		List<Point> points = ParserUtils.ParseList(reader, t -> {
			try {
				return Point.build(t);
			} catch (IOException | ParseException e) {
				return null;
			}
		});
		if (!ParserUtils.readDelimiter(reader, ',')) // not include in parseList
			throw new ParseException("delimiter ',' not found!");
		Color color = Color.build(reader);
		LinePattern pattern = LinePattern.build(reader);
		double thickness = ParserUtils.parseRealToken(reader,',');
		Arrow arrow[] = new Arrow[2];
		List<Arrow> arrows =  ParserUtils.ParseList(reader, t -> {
			try {
				return Arrow.build(t);
			} catch (IOException | ParseException e) {
				throw new RuntimeException(e);
			}
		});
		for (int i = 0; i < arrow.length; i++) {
			arrow[i] = arrows.get(i);			
		}
		if (!ParserUtils.readDelimiter(reader, ',')) // not include in parseList
			throw new ParseException("delimiter ',' not found!");
		double arrowSize = ParserUtils.parseRealToken(reader,',');
		Smooth smooth = Smooth.build(reader);		
		if (!ParserUtils.readDelimiter(reader, ')'))
			 throw new ParseException("Could not find delimiter of end of Ellipse: ')' ");
		 
		 ParserUtils.readDelimiter(reader, ',');
		 return new Line(visible, origin, rotation, points, color, pattern, thickness, arrow, arrowSize, smooth);
	}
}
