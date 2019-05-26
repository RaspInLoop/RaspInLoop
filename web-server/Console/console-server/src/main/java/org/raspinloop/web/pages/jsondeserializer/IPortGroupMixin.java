package org.raspinloop.web.pages.jsondeserializer;

import org.raspinloop.web.pages.model.PortGroup;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(as = PortGroup.class)
public abstract  class IPortGroupMixin {
}