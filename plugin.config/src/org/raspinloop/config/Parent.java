package org.raspinloop.config;

import java.util.Collection;

public interface Parent<T> {
	Collection<T> getComponents(Class<T> type);  
	void addComponent(T comp);
	void removeComponent(T comp);
}
