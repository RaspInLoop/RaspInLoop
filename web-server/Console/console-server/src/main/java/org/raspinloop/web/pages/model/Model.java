package org.raspinloop.web.pages.model;

import java.util.ArrayList;
import java.util.List;

import org.raspinloop.server.model.IComponent;
import org.raspinloop.server.model.ILink;
import org.raspinloop.server.model.IModel;

import lombok.Data;

@Data
public class Model implements IModel {

	String id;
	ArrayList<IComponent> components;
	ArrayList<ILink> links;

	public Model() {
	}
	
}
