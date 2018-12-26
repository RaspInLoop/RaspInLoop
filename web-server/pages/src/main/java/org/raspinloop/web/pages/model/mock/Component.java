package org.raspinloop.web.pages.model.mock;

import java.util.Set;

import org.raspinloop.web.pages.model.IComponent;
import org.raspinloop.web.pages.model.IParameter;
import org.raspinloop.web.pages.model.IPoint;
import org.raspinloop.web.pages.model.IPortGroup;
import org.raspinloop.web.pages.model.ISize;

import lombok.Data;
import lombok.NonNull;

@Data
public class Component implements IComponent {

	@NonNull String id;
	@NonNull String name;
	@NonNull String Description;
	@NonNull String svgContent;
	@NonNull String htmlDocumentation;
	@NonNull IPoint position = new Point(0,0);
	@NonNull ISize size = new Size(100,100);
	@NonNull Set<IParameter> parameters;
	@NonNull Set<IPortGroup> portGroups;
	
}

