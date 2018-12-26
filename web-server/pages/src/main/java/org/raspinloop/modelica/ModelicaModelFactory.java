package org.raspinloop.modelica;

import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IStandardLibrary;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.modelica.mdt.internal.core.StandardLibrary;
import org.openmodelica.corba.ConnectException;
import org.raspinloop.web.pages.model.IComponent;
import org.raspinloop.web.pages.model.IModel;
import org.raspinloop.web.pages.model.IModelFactory;


public class ModelicaModelFactory implements IModelFactory {

	// Currently only builtin model are supported
	IStandardLibrary std;
	
	public ModelicaModelFactory() {	
		try {
			std = new StandardLibrary(null);
		} catch (ConnectException | CompilerInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public IModel createModel(String name) throws ConnectException, UnexpectedReplyException, CompilerInstantiationException, InvocationError {
		IModelicaClass moClass = std.getPackages()
									.stream()
									.map(p -> p.getMoClassLoader().getClass(name))
									.findFirst()
									.get();
		return new ModelAdapter(std, moClass);
	}

	@Override
	public IComponent createComponent(String name) throws ConnectException, UnexpectedReplyException, CompilerInstantiationException, InvocationError {
		IModelicaClass moClass =std.getPackages()
				.stream()
				.map(p -> p.getMoClassLoader().getClass(name))
				.findFirst()
				.get();
		return new ComponentAdapter(std, moClass);
	}

}
