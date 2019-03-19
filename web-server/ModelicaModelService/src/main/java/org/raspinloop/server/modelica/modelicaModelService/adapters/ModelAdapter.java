package org.raspinloop.server.modelica.modelicaModelService.adapters;

import java.util.ArrayList;

import org.raspinloop.server.model.IComponent;
import org.raspinloop.server.model.ILink;
import org.raspinloop.server.model.IModel;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass;
import org.raspinloop.server.modelica.mdt.core.IModelicaProject;
import org.raspinloop.server.modelica.modelicaModelService.adapters.svg.SvgFactory;

public class ModelAdapter implements IModel {

	private IModelicaClass moClass;
	private IModelicaProject std;
	private SvgFactory svgFactory;


	public ModelAdapter(SvgFactory svgFactory, IModelicaProject std2, IModelicaClass moClass2) {
		this.svgFactory = svgFactory;
		this.std = std2;
		moClass = moClass2;
	}

	@Override
	public String getId() {
		return "test";
	}

	@Override
	public ArrayList<IComponent> getComponents() {
		ArrayList<IComponent> components = new ArrayList<>();
		// TODO remove this test stuff
		try {
			components.add(new ComponentAdapter(svgFactory, std, moClass));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return components;
	}

	@Override
	public ArrayList<ILink> getLinks() {
		// TODO remove this test stuff
		return new ArrayList<>();
	}

}
