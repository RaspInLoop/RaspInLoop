package org.raspinloop.web.pages.jsondeserializer;

import org.raspinloop.web.pages.model.Parameter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Parameter.class)
public abstract  class IParameterMixin {
}
