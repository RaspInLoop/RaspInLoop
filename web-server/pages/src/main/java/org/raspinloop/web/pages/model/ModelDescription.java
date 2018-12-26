package org.raspinloop.web.pages.model;

import lombok.Value;

@Value
public class ModelDescription {


	String id;
	String description;
	String author;
	String creationDate;
	
	public ModelDescription(String id, String description, String author, String creationDate) {
		super();
		this.id = id;
		this.description = description;
		this.author = author;
		this.creationDate = creationDate;
	}	
}
