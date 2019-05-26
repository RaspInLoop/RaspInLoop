package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.raspinloop.server.modelica.annotations.CoordinateSystem;
import org.raspinloop.server.modelica.annotations.Point;

public class DrawinUnitConverter {

	private double scale;
	@SuppressWarnings("unused")
	private double dux;
	@SuppressWarnings("unused")
	private double duy;
	private DecimalFormat df;

	public DrawinUnitConverter(CoordinateSystem coordinateSystem) {
		scale = coordinateSystem.getInitialScale();		
		//scale =1;
		dux = coordinateSystem.getDrawingUnitX();
		duy = coordinateSystem.getDrawingUnitY(); 
		DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.getDefault());
		formatSymbols.setDecimalSeparator('.');
		df = new DecimalFormat("#.##", formatSymbols);	

	}
	
	public String convert(double x) {
		return df.format(x*scale);		
	}

	public String convertX(double x) {
		return convert(x);		
	}
	
	public String convertY(double y) {
		return convert(-y);	 // modelica uses a first quadrant coordinate system		
	}
	
	public String convert(Point p) {
		return convertX(p.getX()) + " " + convertY(p.getY());
	}

	public String convertThickness(double t) {
		return convert(t*20);	
	}	
	
	

}
