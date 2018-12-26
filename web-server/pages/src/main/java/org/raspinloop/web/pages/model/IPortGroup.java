package org.raspinloop.web.pages.model;

import java.util.Set;

import org.raspinloop.web.pages.model.mock.Port;

/**
 * 
 * @author Motte
 *Interface of Json structure exchanged with the front-end code (jointJS)
 */
public interface IPortGroup {

	Set<IPort> getPorts();

	IPortGroupDefinition getDefinition();

}