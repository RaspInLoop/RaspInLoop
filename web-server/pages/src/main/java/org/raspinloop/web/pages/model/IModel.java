package org.raspinloop.web.pages.model;

import java.util.ArrayList;

import org.raspinloop.web.pages.model.mock.Component;
/**
 * 
 * @author Motte
 *Interface of Json structure exchanged with the front-end code (jointJS)
 */
public interface IModel {

	String getId();

	ArrayList<IComponent> getComponents();

	ArrayList<ILink> getLinks();

}