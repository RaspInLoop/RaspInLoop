package org.raspinloop.web.pages.model.mock;

import java.util.Set;

import org.raspinloop.web.pages.model.IPort;
import org.raspinloop.web.pages.model.IPortGroup;
import org.raspinloop.web.pages.model.IPortGroupDefinition;

import lombok.Data;

@Data
public class PortGroup implements IPortGroup {

	Set<IPort> ports;
	IPortGroupDefinition definition;
	public PortGroup(Set<IPort> ports, IPortGroupDefinition definition) {
		super();
		this.ports = ports;
		this.definition = definition;
	}
	
	

	
}
