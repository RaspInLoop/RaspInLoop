package org.raspinloop.server.model;

/**
 * 
 * @author Motte
 *Interface of Json structure exchanged with the front-end code (jointJS)
 */
public interface IPort {


	String getDescription();

	IPoint getPosition();

	String getId();

}