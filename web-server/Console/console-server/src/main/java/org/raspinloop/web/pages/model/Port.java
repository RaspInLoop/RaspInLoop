package org.raspinloop.web.pages.model;

import org.raspinloop.server.model.IPoint;
import org.raspinloop.server.model.IPort;

import lombok.Data;
import lombok.NonNull;

@Data
public class Port implements IPort {

	String id;
	IPoint position;
	String description;

	public Port() {
	}

}
