package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.StringReader;

import org.openmodelica.corba.parser.ParseException;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class Placement {
	//{Placement(true,
	//-,-,
	//-110.0,-10.0,-90.0,10.0,
	//-,-,
	//-,-,-,-,
	//-,)}
	
	private static final String ANNOTATION_NAME = "Placement";
	
	public static Placement build(StringReader reader) throws IOException, ParseException {
		boolean cBraceOpen = ParserUtils.isOpeningBrace(reader);
		ParserUtils.skipWhiteSpace(reader);
		reader.mark(ANNOTATION_NAME.length()+1);
		char cbuf[] = new char[ANNOTATION_NAME.length()+1];
		reader.read(cbuf);
		String s = new String(cbuf);
		if (!s.equalsIgnoreCase(ANNOTATION_NAME+"(")) {
			reader.reset();
			return null;
		}
		
		boolean visible = ParserUtils.parseBooleanToken(reader, ',');	
		Point diagOrigin = Point.build(reader);
		Extent diagTransformation = Extent.build(reader);
		double diagRotation = ParserUtils.parseRealToken(reader, ',');
		
		
		Point iconOrigin = null;
		Extent iconTransformation = null;
		double iconRotation =0.0;
		try {
			 iconOrigin = Point.build(reader);
			 iconTransformation = Extent.build(reader);
			 iconRotation = ParserUtils.parseRealToken(reader);
		} catch (Exception e) {
			// optional fields, may thrown an exception if doesn't exist
			log.trace("Cannot decode further Placement information {}", e.getMessage());
		}
		
		if (cBraceOpen )
			ParserUtils.isClosingBrace(reader);
		return new Placement(visible, diagOrigin, diagTransformation, diagRotation, iconOrigin, iconTransformation, iconRotation) ;
	}
	
	boolean visible;
	Point diagOrigin;
	Extent diagTransformation;
	double diagRotation;
	Point iconOrigin;
	Extent iconTransformation;
	double iconRotation;
	
}
