package org.raspinloop.server.model;

import java.util.Set;


/**
 * 
 * @author Motte
 *Interface of Json structure exchanged with the front-end code (jointJS)
 */
public interface IPortGroup {

	Set<IPort> getPorts();

	IPortGroupDefinition getDefinition();

}