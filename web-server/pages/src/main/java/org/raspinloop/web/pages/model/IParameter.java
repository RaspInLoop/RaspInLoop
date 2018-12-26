package org.raspinloop.web.pages.model;

/**
 * 
 * @author Motte
 *Interface of Json structure exchanged with the front-end code (jointJS)
 */
public interface IParameter {

	String getName();
	Object getValue();
	String getType();
	String getDescription();
}
