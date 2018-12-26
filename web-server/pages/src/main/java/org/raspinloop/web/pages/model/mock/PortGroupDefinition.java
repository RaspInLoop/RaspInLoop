package org.raspinloop.web.pages.model.mock;

import org.raspinloop.web.pages.model.IPortGroupDefinition;

import lombok.Data;

@Data
public class PortGroupDefinition implements IPortGroupDefinition {
	String Name;
	String svg;
	
	public PortGroupDefinition(String name, String svg) {
		super();
		Name = name;
		this.svg = svg;
	}

	
	
}
