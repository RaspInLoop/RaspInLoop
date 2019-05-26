package org.raspinloop.server.modelica.annotations;

import java.io.IOException;
import java.io.Reader;

import org.openmodelica.corba.parser.ParseException;

import lombok.Value;

@Value
public class Extent {
	Point p1;
	Point p2;
	
	public static Extent build(Reader reader) throws IOException, ParseException {
		boolean cBraceOpen = ParserUtils.isOpeningBrace(reader);
		Point p1 = Point.build(reader);
		Point p2 = Point.build(reader);
		if (cBraceOpen && !ParserUtils.readDelimiter(reader,'}'))
			throw new ParseException("delimiter '}' not found!");		
		ParserUtils.readDelimiter(reader, ',');
		return  new Extent(p1,p2);
	}
	
	public double getWidth() {
		return Math.abs(p2.getX()-p1.getX());
	}
	
	public double getHeight() {
		return Math.abs(p2.getY()-p1.getY());
	}

	public Point getCenter() {
		return new Point(p1.getX()+(p2.getX()-p1.getX())/2,  p1.getY()+(p2.getY()-p1.getY())/2);
	}
	
	public Point getLower() {
		double x =Math.min(p1.getX(),p2.getX());
		double y =Math.min(p1.getY(),p2.getY()); 
		return new Point(x, y);
	}

	public boolean isValid() {		
		return p1.isValid() && p2.isValid();
	}
}
