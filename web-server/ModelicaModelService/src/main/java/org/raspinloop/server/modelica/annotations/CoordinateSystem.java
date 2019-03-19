package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

import lombok.Value;

@Value
public class CoordinateSystem {
	Extent extent;
	boolean preserveAspectRatio;
	double initialScale;
	double drawingUnitX;
	double drawingUnitY;

	public static CoordinateSystem build(Reader reader) throws IOException, ParseException {
		boolean cBraceOpen = ParserUtils.isOpeningBrace(reader);
		Extent extent = Extent.build(reader);
		boolean preserveAspectRatio = ParserUtils.parseBooleanToken(reader, ',');	
		double initialScale = ParserUtils.parseRealToken(reader, ',');
		double drawingUnitX = ParserUtils.parseRealToken(reader, ',');
		double drawingUnitY = ParserUtils.parseRealToken(reader);
		if (cBraceOpen && !ParserUtils.isClosingBrace(reader)) {
			throw new ParseException("delimiter '}' not found!");
		} else
			ParserUtils.readDelimiter(reader, ',');
		return new CoordinateSystem(extent, preserveAspectRatio, initialScale, drawingUnitX, drawingUnitY);
	}
}
