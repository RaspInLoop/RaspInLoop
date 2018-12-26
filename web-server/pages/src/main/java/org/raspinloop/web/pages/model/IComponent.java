package org.raspinloop.web.pages.model;

import java.util.Set;
/**
 * 
 * @author Motte
 *Interface of Json structure exchanged with the front-end code (jointJS)
 */
public interface IComponent {

	String getId();

	String getName();

	String getDescription();
	
	String getHtmlDocumentation();

	String getSvgContent();

	IPoint getPosition();

	ISize getSize();

	Set<IPortGroup> getPortGroups();

	Set<IParameter> getParameters();

}