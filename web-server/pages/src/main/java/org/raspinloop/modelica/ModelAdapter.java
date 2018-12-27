package org.raspinloop.modelica;

import java.util.ArrayList;
import java.util.Collections;

import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IModelicaProject;
import org.modelica.mdt.core.IStandardLibrary;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;
import org.raspinloop.web.pages.model.IComponent;
import org.raspinloop.web.pages.model.ILink;
import org.raspinloop.web.pages.model.IModel;

public class ModelAdapter implements IModel {

	private IModelicaClass moClass;
	private IModelicaProject std;


	public ModelAdapter(IModelicaProject std2, IModelicaClass moClass2) {
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
			components.add(new ComponentAdapter(std, moClass));
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
