package org.raspinloop.web.pages.jsondeserializer;

import org.raspinloop.web.pages.model.Component;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Component.class)
public abstract  class IComponentMixin {

}
