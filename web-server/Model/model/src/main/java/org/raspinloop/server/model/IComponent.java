package org.raspinloop.server.model;

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
	

	IPoint getPosition();

	ISize getSize();

	Set<IPortGroup> getPortGroups();

	Set<IParameter> getParameters();

	String getSvgIcon();

}