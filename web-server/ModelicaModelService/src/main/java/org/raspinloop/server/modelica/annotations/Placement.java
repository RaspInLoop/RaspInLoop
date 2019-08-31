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
		Transformation transformation = Transformation.build(reader);
		Transformation iconTransformation = transformation;
		try {
			iconTransformation = Transformation.build(reader);
		} catch (IOException | ParseException e ) {
			log.debug("No icon transformation found, using diag transformation");
			log.trace("stacktrace:", e);
		}
		
			
		if (cBraceOpen )
			ParserUtils.isClosingBrace(reader);
		Placement p = new Placement(visible, transformation, iconTransformation) ;
		log.trace("new placement built:" + p.toString());
		return p;
		
	}
	
	boolean visible;
	Transformation transformation;
	Transformation iconTransformation;			
}
