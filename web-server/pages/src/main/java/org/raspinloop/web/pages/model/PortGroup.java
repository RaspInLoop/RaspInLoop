package org.raspinloop.web.pages.model;

import java.util.Set;

import org.raspinloop.server.model.IPort;
import org.raspinloop.server.model.IPortGroup;
import org.raspinloop.server.model.IPortGroupDefinition;

import lombok.Data;
import lombok.NonNull;

@Data
public class PortGroup implements IPortGroup {

	Set<IPort> ports;
	IPortGroupDefinition definition;

	public PortGroup() {
	}

}
