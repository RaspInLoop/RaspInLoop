package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

import lombok.Value;

@Value
public class Color {

	int red;
	int green;
	int blue;
	
	public static Color build(Reader r) throws ParseException, IOException {
		boolean cBraceOpen = ParserUtils.isOpeningBrace(r);		
		 int red =ParserUtils.parseIntToken(r, ',');		
		 int green = ParserUtils.parseIntToken(r, ',');
		 int blue = ParserUtils.parseIntToken(r);
		 if (cBraceOpen && ! ParserUtils.readDelimiter(r, '}')){
			 throw new ParseException("delimiter '}' not found!");				 
		 }
		 else 
			 ParserUtils.readDelimiter(r, ',');		 		
		return new Color(red, green, blue);
	}
	
	public String toRgbString() {
		return "rgb("+red+", "+green+", "+blue+")";
	}
}
