package org.raspinloop.web.pages.jsondeserializer;

import org.raspinloop.web.pages.model.Port;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Port.class)
public abstract  class IPortMixin {
	

}