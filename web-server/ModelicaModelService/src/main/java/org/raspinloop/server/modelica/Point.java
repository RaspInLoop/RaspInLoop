package org.raspinloop.server.modelica;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.modelica.annotations.ParserUtils;

import lombok.Value;

@Value
public class Point {
	double x;
	double y;
	
	public static final Point origin = new Point(0.0, 0.0);
	
	public static Point build(Reader r) throws IOException, ParseException {
		boolean cBraceOpen = ParserUtils.isOpeningBrace(r);

		double x= ParserUtils.parseRealToken(r, ',');		
		double y = ParserUtils.parseRealToken(r);
		if (cBraceOpen && !ParserUtils.readDelimiter(r, '}')) {
			throw new ParseException("delimiter '}' not found!");
		}
		ParserUtils.readDelimiter(r, ',');

		return new Point(x, y);
	}

	public boolean isValid() {
		return ! Double.isNaN(x) && ! Double.isNaN(y);
	}
}
