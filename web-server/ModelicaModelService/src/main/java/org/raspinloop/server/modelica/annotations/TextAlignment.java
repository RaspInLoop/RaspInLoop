package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

public enum TextAlignment {
	Center, Right, Left;
	
	private static final String TEXT_A = "TextAlignment";

	public static TextAlignment build(Reader reader) throws IOException, ParseException {
		ParserUtils.skipWhiteSpace(reader);
		reader.mark(TEXT_A.length());
		char[] cbuf = new char[TEXT_A.length()];
		reader.read(cbuf);
		String s = new String(cbuf);
		if (!TEXT_A.equals(s))
			throw new ParseException("current token is not a "+ TEXT_A);
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
		return TextAlignment.valueOf(enumString.toString());
	}
	
}
