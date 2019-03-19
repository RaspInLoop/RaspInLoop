package org.raspinloop.web.pages.model;

import java.util.Set;

import org.raspinloop.server.model.IComponent;
import org.raspinloop.server.model.IParameter;
import org.raspinloop.server.model.IPoint;
import org.raspinloop.server.model.IPortGroup;
import org.raspinloop.server.model.ISize;

import lombok.Data;
import lombok.NonNull;

@Data
public class Component implements IComponent {

	public Component() {	
	}
	 String id;
	 String name;
	 String description;
	 String svgContent;
	 String svgIcon;
	 String htmlDocumentation;
	 IPoint position = new Point(0,0);
	 ISize size = new Size(100,100);
	 Set<IParameter> parameters;
	 Set<IPortGroup> portGroups;	
}

