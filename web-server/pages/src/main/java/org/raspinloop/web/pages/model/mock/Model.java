package org.raspinloop.web.pages.model.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.raspinloop.web.pages.model.IModel;
import org.raspinloop.web.pages.model.IParameter;
import org.raspinloop.web.pages.model.IPoint;
import org.raspinloop.web.pages.model.IPortGroup;
import org.raspinloop.web.pages.model.ISize;

import lombok.Data;

import org.raspinloop.web.pages.model.IComponent;
import org.raspinloop.web.pages.model.ILink;

@Data
public class Model implements IModel {

	String id;
	ArrayList<IComponent> components;
	ArrayList<ILink> links;

	public Model(String id, List<IComponent> components, List<ILink> links) {
		super();
		this.id = id;
		this.components = new ArrayList<>(components);
		this.links  = new ArrayList<>(links);
	}
}
