package org.raspinloop.web.pages.jsondeserializer;

import org.raspinloop.web.pages.model.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Size.class)
public abstract  class ISizeMixin {

}
