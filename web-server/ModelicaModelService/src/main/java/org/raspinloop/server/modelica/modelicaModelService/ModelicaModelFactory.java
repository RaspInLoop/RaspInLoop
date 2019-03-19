package org.raspinloop.server.modelica.modelicaModelService;

import java.util.Collection;

import javax.annotation.Resource;

import org.openmodelica.corba.ConnectException;
import org.raspinloop.server.model.IComponent;
import org.raspinloop.server.model.IModel;
import org.raspinloop.server.model.IPackage;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass;
import org.raspinloop.server.modelica.mdt.core.IModelicaProject;
import org.raspinloop.server.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.raspinloop.server.modelica.mdt.core.compiler.InvocationError;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.raspinloop.server.modelica.modelicaModelService.adapters.ComponentAdapter;
import org.raspinloop.server.modelica.modelicaModelService.adapters.ModelAdapter;
import org.raspinloop.server.modelica.modelicaModelService.adapters.PackageAdapter;
import org.raspinloop.server.modelica.modelicaModelService.adapters.svg.SvgFactory;
import org.springframework.stereotype.Service;

@Service
public class ModelicaModelFactory implements IModelFactory {

	// Currently only builtin model are supported
	
	@Resource
	IModelicaProject std;
	
	@Resource
	SvgFactory svgFactory;
	
	public ModelicaModelFactory() {	

	}
	
	@Override
	public IModel createModel(String name) throws ConnectException, UnexpectedReplyException, CompilerInstantiationException, InvocationError {
		IModelicaClass moClass = std.getRootClasses()
									.stream()
									.map(p -> p.getMoClassLoader().getClass(name))
									.findFirst()
									.get();
		return new ModelAdapter(svgFactory, std, moClass);
	}

	@Override
	public IComponent createComponent(String name) throws ConnectException, UnexpectedReplyException, CompilerInstantiationException, InvocationError {
		Collection<? extends IModelicaClass> roots = std.getRootClasses();
				IModelicaClass moClass = roots		.stream()
				.map(p -> p.getMoClassLoader().getClass(name))
				.findFirst()
				.get();
		return new ComponentAdapter(svgFactory, std, moClass);
	}

	@Override
	public IPackage createPackage(String fullName) 	throws ConnectException, UnexpectedReplyException, CompilerInstantiationException, InvocationError {
		Collection<? extends IModelicaClass> roots = std.getRootClasses();
		IModelicaClass moClass = roots.stream()		
		.map(p -> p.getMoClassLoader().getClass(fullName))
		.findFirst()
		.get();
		return new PackageAdapter(svgFactory, moClass);
	}
}
