package org.raspinloop.server.model;

import java.util.Collection;

public interface IPackage {
	Collection<String> getComponentsName();
	Collection<String> getPackagesNames();
	
	String getName();

	String getDescription();
	
	String getHtmlDocumentation();

	String getSvgIcon();
	String getId();
	
}
