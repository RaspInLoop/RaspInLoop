package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

public enum LinePattern {
	None, 
	Solid, 
	Dash, 
	Dot, 
	DashDot, 
	DashDotDot;

	private static final String LINE_PATTERN = "LinePattern";

	public static LinePattern build(Reader reader) throws IOException, ParseException {
		ParserUtils.skipWhiteSpace(reader);
		reader.mark(LINE_PATTERN.length());
		char[] cbuf = new char[LINE_PATTERN.length()];
		reader.read(cbuf);
		String s = new String(cbuf);
		if (!LINE_PATTERN.equals(s))
			throw new ParseException("current token is not a "+ LINE_PATTERN);
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
		return LinePattern.valueOf(enumString.toString().trim());
	}

}
