package org.raspinloop.web.pages.model.mock;

import org.raspinloop.web.pages.model.IPoint;
import org.raspinloop.web.pages.model.IPort;

import lombok.Data;

@Data
public class Port implements IPort {

	String  id;
	IPoint position;
	String description;
	
	public Port(String id, IPoint position, String description) {
		super();
		this.id = id;
		this.position = position;
		this.description = description;
	}

	
}
