package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

public enum FillPattern {

	None, 
	Solid, 
	Horizontal, 
	Vertical,
	Cross,
	Forward,
	Backward,
	CrossDiag,
	HorizontalCylinder,
	VerticalCylinder,
	Sphere;

	private static final String FILL_PATTERN = "FillPattern";

	public static FillPattern build(Reader reader) throws IOException, ParseException {
		ParserUtils.skipWhiteSpace(reader);
		reader.mark(FILL_PATTERN.length());
		char[] cbuf = new char[FILL_PATTERN.length()];
		reader.read(cbuf);
		String s = new String(cbuf);
		if (!FILL_PATTERN.equals(s))
			throw new ParseException("current token is not a "+ FILL_PATTERN);
		char c = (char)reader.read(); // skip '.'
		StringBuilder enumString = new StringBuilder();
		c = (char)reader.read();
		while (Character.isAlphabetic(c)){
			enumString.append(c);
			reader.mark(1);
			c = (char)reader.read();
		}
		reader.reset();
		ParserUtils.readDelimiter(reader, ',');
		return FillPattern.valueOf(enumString.toString());
	}

}
