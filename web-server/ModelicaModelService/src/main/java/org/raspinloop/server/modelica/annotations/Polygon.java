package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.openmodelica.corba.parser.ParseException;

import lombok.Value;

@Value
public class Polygon implements GraphicItem, FilledShape {

	private static final String GRAPHIC_NAME = "Polygon";
	boolean visible;
	Point origin;
	double rotation;

	Color lineColor;
	Color fillColor;
	LinePattern linePattern;
	FillPattern fillPattern;
	double lineThickness;
	List<Point> points;
	Smooth smooth;

	public static Polygon build(Reader reader) throws IOException, ParseException {
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
		Color fillColor = Color.build(reader);
		LinePattern linePattern = LinePattern.build(reader);
		FillPattern fillPattern = FillPattern.build(reader);
		double lineThickness = ParserUtils.parseRealToken(reader, ',');

		List<Point> points = ParserUtils.ParseList(reader, t -> {
			try {
				return Point.build(t);
			} catch (IOException | ParseException e) {
				return null;
			}
		});
		if (!ParserUtils.readDelimiter(reader, ',')) // not include in parseList
			throw new ParseException("delimiter ',' not found!");
		Smooth smooth = Smooth.build(reader);

		if (!ParserUtils.readDelimiter(reader, ')'))
			throw new ParseException("Could not find delimiter of end of Polygon: ')' ");

		ParserUtils.readDelimiter(reader, ',');

		return new Polygon(visible, origin, rotation, lineColor, fillColor, linePattern, fillPattern, lineThickness, points, smooth);
	}
}
