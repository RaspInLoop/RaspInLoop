package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.openmodelica.corba.parser.ParseException;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class Icon {
	
	public static Icon build(StringReader reader) throws IOException, ParseException {
		boolean cBraceOpen = ParserUtils.isOpeningBrace(reader);
		if (!ParserUtils.isClosingBrace(reader)) {
			CoordinateSystem coordinateSystem = CoordinateSystem.build(reader);	
			List<GraphicItem> graphics = ParserUtils.ParseList(reader, t -> {
				try {
					return GraphicItemFactory.build(t);
				} catch (IOException | ParseException e) {
					throw new RuntimeException(e);
				}
			});
			
			
			if (cBraceOpen )
				ParserUtils.isClosingBrace(reader);
			return new Icon(coordinateSystem, graphics) ;
		}
		return null;
	}
	
	CoordinateSystem coordinateSystem;
	List<GraphicItem> graphics;
}
