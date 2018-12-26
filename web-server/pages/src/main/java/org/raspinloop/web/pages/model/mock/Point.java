package org.raspinloop.web.pages.model.mock;

import org.raspinloop.web.pages.model.IPoint;

import lombok.Data;

@Data
public class Point implements IPoint {

	private int x;
	private int y;
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	
	
}
