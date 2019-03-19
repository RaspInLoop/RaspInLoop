package org.raspinloop.web.pages.model;

import org.raspinloop.server.model.IParameter;

import lombok.Data;
import lombok.NonNull;

@Data
public class Parameter implements IParameter {

	public Parameter() {
	}

	String name;
	Object value;
	String type;
	String description;

}
