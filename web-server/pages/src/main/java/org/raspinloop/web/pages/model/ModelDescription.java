package org.raspinloop.web.pages.model;

import lombok.Data;
import lombok.NonNull;
import lombok.Value;

@Data
public class ModelDescription {

	String id;
	String description;
	String author;
	String creationDate;

	public ModelDescription() {
	}

}
