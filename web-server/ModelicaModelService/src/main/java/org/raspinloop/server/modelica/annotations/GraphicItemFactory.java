package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;


public class GraphicItemFactory {
	
	public static GraphicItem build(Reader reader) throws IOException, ParseException{
		//try each of the known graphicItem builder
		// each of them return null if there aren't able to handle the annotation and reset the reader
		GraphicItem item = Line.build(reader);
		if (item != null)
			return item;
		item = Polygon.build(reader);
		if (item != null)
			return item;
		item = Ellipse.build(reader);
		if (item != null)
			return item;
		item = Rectangle.build(reader);
		if (item != null)
			return item;
		item = Text.build(reader);
		if (item != null)
			return item;
		item = Bitmap.build(reader);
		if (item != null)
			return item;
		return null;
	}
	
}
