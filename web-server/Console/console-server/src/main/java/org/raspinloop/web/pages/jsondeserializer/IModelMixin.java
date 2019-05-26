package org.raspinloop.web.pages.jsondeserializer;

import org.raspinloop.web.pages.model.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Model.class)
public abstract class IModelMixin {
}