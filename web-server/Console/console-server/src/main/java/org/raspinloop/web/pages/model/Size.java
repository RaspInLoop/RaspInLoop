package org.raspinloop.web.pages.model;


import org.raspinloop.server.model.ISize;

import lombok.Data;

@Data
public class Size implements ISize {
	
	double width;
	double height;
	
	public Size() {
	}
	
	public Size(double width, double height) {
		super();
		this.width = width;
		this.height = height;
	}
}
