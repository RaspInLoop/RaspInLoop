package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

import lombok.Value;

@Value
public class Transformation {
	 Point origin;
	 Extent extent;
	 Double rotation;
	
		
	public static Transformation build(Reader r) throws IOException, ParseException {
		boolean cBraceOpen = ParserUtils.isOpeningBrace(r);
		Point origin = Point.build(r);
		if (!origin.isValid())
			origin = Point.origin;
		Extent extent = Extent.build(r);
		Double rotation = ParserUtils.parseRealToken(r);
		if (Double.isNaN(rotation))
			rotation = 0.0; //Default value for transformation
		if (cBraceOpen && !ParserUtils.readDelimiter(r, '}')) {
			throw new ParseException("delimiter '}' not found!");
		}
		ParserUtils.readDelimiter(r, ',');

		return new Transformation(origin, extent, rotation);
	}
}
