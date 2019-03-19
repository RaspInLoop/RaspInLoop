package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

public enum BorderPattern {

	None, 
	Raised, 
	Sunken, 
	Engraved;

	private static final String BORDER_PATTERN = "BorderPattern";

	public static BorderPattern build(Reader reader) throws IOException, ParseException {
		ParserUtils.skipWhiteSpace(reader);
		reader.mark(BORDER_PATTERN.length());
		char[] cbuf = new char[BORDER_PATTERN.length()];
		reader.read(cbuf);
		String s = new String(cbuf);
		if (!BORDER_PATTERN.equals(s))
			throw new ParseException("current token is not a "+ BORDER_PATTERN);
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
		return BorderPattern.valueOf(enumString.toString());
	}

}
