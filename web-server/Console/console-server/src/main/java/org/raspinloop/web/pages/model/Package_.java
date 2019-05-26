package org.raspinloop.web.pages.model;

import java.util.Collection;
import java.util.Set;

import org.raspinloop.server.model.IComponent;
import org.raspinloop.server.model.IPackage;
import org.raspinloop.server.model.IParameter;
import org.raspinloop.server.model.IPoint;
import org.raspinloop.server.model.IPortGroup;
import org.raspinloop.server.model.ISize;

import lombok.Data;
import lombok.NonNull;

@Data
public class Package_ implements IPackage {

	public Package_() {	
	}
	 String id;
	 String name;
	 String description;
	 String svgIcon;
	 String htmlDocumentation;
	 Collection<String> componentsName;
	 Collection<String> packagesNames;
		
}

