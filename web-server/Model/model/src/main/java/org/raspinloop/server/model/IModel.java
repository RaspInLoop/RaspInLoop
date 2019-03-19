package org.raspinloop.server.model;

import java.util.ArrayList;

/**
 * Interface of Json structure exchanged with the front-end code (jointJS)
 *
 * @author Motte
 */
public interface IModel {

	String getId();

	ArrayList<IComponent> getComponents();

	ArrayList<ILink> getLinks();

}