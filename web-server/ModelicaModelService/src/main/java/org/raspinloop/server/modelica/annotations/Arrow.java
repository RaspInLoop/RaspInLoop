package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

public enum Arrow {
	None, Open, Filled, Half;
	
	private static final String ARROW = "Arrow";

	public static Arrow build(Reader reader) throws IOException, ParseException {
		ParserUtils.skipWhiteSpace(reader);
		reader.mark(ARROW.length());
		char[] cbuf = new char[ARROW.length()];
		reader.read(cbuf);
		String s = new String(cbuf);
		if (!ARROW.equals(s))
			throw new ParseException("current token is not a "+ ARROW);
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
		return Arrow.valueOf(enumString.toString());
	}
	
}
