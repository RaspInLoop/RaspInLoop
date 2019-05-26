package org.raspinloop.web.pages.model;

import org.raspinloop.server.model.IPoint;

import lombok.Data;
import lombok.NonNull;

@Data
public class Point implements IPoint {


	double x;
	double y;

	public Point(){
		
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
