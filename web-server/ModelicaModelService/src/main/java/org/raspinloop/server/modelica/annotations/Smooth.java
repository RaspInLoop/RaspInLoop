package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

public enum Smooth {
	None, Bezier;
	
	private static final String SMOOTH = "Smooth";
	
	public static Smooth build(Reader reader) throws IOException, ParseException {
		ParserUtils.skipWhiteSpace(reader);
		reader.mark(SMOOTH.length());
		char[] cbuf = new char[SMOOTH.length()];
		reader.read(cbuf);
		String s = new String(cbuf);
		if (!SMOOTH.equals(s))
			throw new ParseException("current token is not a "+ SMOOTH);
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
		return Smooth.valueOf(enumString.toString());
	}
	
}
