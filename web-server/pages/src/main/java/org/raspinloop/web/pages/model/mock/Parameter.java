package org.raspinloop.web.pages.model.mock;

import org.raspinloop.web.pages.model.IParameter;

import lombok.Data;
import lombok.NonNull;

@Data
public class Parameter implements IParameter {

	@NonNull String name;
	Object value;
	@NonNull String type;
	@NonNull String description;
	
}
