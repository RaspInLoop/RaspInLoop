package org.raspinloop.web.pages.model.mock;

import org.raspinloop.web.pages.model.ISize;

import lombok.Data;

@Data
public class Size implements ISize {

	private final double width;
	private final double height;
	
	public Size(double x, double y) {
		super();
		this.width = x;
		this.height = y;
	}

}
