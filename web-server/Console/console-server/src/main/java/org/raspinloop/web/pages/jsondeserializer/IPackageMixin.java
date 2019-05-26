package org.raspinloop.web.pages.jsondeserializer;

import org.raspinloop.web.pages.model.Package_;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Package_.class)
public abstract  class IPackageMixin {

}
