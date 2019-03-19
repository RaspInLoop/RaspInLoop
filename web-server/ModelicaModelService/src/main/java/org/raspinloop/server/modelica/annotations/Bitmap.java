package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

import lombok.Value;

@Value
public class Bitmap implements GraphicItem {

	private static final String GRAPHIC_NAME = "Bitmap";
	// Graphic Item
	boolean visible;
	Point origin;
	double rotation;	

	//Bitmap specific
	Extent extent;
	String filenameOrSource;

	
	public static Bitmap build(Reader reader) throws IOException, ParseException {
		ParserUtils.skipWhiteSpace(reader);
		reader.mark(GRAPHIC_NAME.length() + 1);
		char cbuf[] = new char[GRAPHIC_NAME.length() + 1];
		reader.read(cbuf);
		String s = new String(cbuf);
		if (!s.equalsIgnoreCase(GRAPHIC_NAME + "(")) {
			reader.reset();
			return null;
		}

		boolean visible = ParserUtils.parseBooleanToken(reader, ',');
		Point origin = Point.build(reader);
		double rotation = ParserUtils.parseRealToken(reader, ',');
		
		Extent extent = Extent.build(reader);
		
		String filenameOrSource = ParserUtils.parseStringToken(reader, ')'); 
		
		ParserUtils.readDelimiter(reader, ',');

		return new Bitmap(visible, origin, rotation, extent, filenameOrSource);
	}
}
