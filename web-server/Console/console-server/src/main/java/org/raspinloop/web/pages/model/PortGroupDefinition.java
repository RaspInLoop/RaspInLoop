package org.raspinloop.web.pages.model;

import org.raspinloop.server.model.IPortGroupDefinition;

import lombok.Data;
import lombok.NonNull;

@Data
public class PortGroupDefinition implements IPortGroupDefinition {
	String Name;
	String svg;

	public PortGroupDefinition() {
	}

}
