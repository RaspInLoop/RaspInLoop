package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

public enum TextStyle {
	Bold, Italic, UnderLine;
	
	private static final String TEXT_STYLE = "TextStyle";

	public static TextStyle build(Reader reader) throws IOException, ParseException {
		reader.mark(TEXT_STYLE.length());
		char[] cbuf = new char[TEXT_STYLE.length()];
		reader.read(cbuf);
		String s = new String(cbuf);
		if (!TEXT_STYLE.equals(s))
			throw new ParseException("current token is not a "+ TEXT_STYLE);
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
		return TextStyle.valueOf(enumString.toString());
	}
	
}
