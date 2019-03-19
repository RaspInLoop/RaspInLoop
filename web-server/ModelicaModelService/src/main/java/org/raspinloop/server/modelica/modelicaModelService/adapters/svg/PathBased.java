package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import java.util.List;
import java.util.stream.Collectors;

import org.raspinloop.server.modelica.annotations.Point;
import org.raspinloop.server.modelica.annotations.Smooth;

public class PathBased  {

	 
	 
	private DrawinUnitConverter duc;
	private List<Point> points;
	private Smooth smooth;
	public PathBased(DrawinUnitConverter duc, List<Point> points, Smooth smooth) {
		this.duc = duc;
		this.points = points;
		this.smooth = smooth;

	}


	protected String getPathOrigin() {
		return "M "+duc.convert(points.get(0));
	}


	protected String getPathPoints() {
		if (smooth == Smooth.Bezier) {
			StringBuilder pathPointBuilder = new StringBuilder();		
			for (int i = 0; i < points.size(); i++) {
				if (i == 0) {
					
					continue; // skip it has been fetched for pathOrigin
				} 
				Point p1 = points.get(i-1);
				Point controlPoint = points.get(i-1);
				Point  middlePoint = findMiddlePoint(p1,controlPoint);				
				pathPointBuilder.append(" Q "+ duc.convert(controlPoint) + "," +  duc.convert(middlePoint));					
			}
			return pathPointBuilder.toString();			
		} else {
			return
					points.stream().skip(1) // skip it has been fetched for pathOrigin
					.map(duc::convert)
					.map("L "::concat)
					.collect(Collectors.joining(" "));	
		}				
	}
	
	private Point findMiddlePoint(Point p1, Point p2) {
		double x = (p1.getX() + p2.getX()) /2;
		double y = (p1.getY() + p2.getY()) /2;
		return new Point(x, y);
	}
}
