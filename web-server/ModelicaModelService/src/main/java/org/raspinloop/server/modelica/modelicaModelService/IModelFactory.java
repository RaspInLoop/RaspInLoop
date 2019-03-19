package org.raspinloop.server.modelica.modelicaModelService;

import org.openmodelica.corba.ConnectException;
import org.raspinloop.server.model.IComponent;
import org.raspinloop.server.model.IModel;
import org.raspinloop.server.model.IPackage;
import org.raspinloop.server.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.raspinloop.server.modelica.mdt.core.compiler.InvocationError;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;

import lombok.NonNull;

public interface IModelFactory {

	public IModel createModel(String name) throws ConnectException, UnexpectedReplyException, CompilerInstantiationException, InvocationError;
	public IComponent createComponent(@NonNull String name) throws ConnectException, UnexpectedReplyException, CompilerInstantiationException, InvocationError;
	public IPackage createPackage(@NonNull String fullName) throws ConnectException, UnexpectedReplyException, CompilerInstantiationException, InvocationError;

}
