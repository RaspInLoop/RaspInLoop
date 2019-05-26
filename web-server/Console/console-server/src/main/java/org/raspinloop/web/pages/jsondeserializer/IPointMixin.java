package org.raspinloop.web.pages.jsondeserializer;

import org.raspinloop.web.pages.model.Point;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Point.class)
public abstract  class IPointMixin {
}