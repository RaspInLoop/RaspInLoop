package org.raspinloop.web.pages.model;

import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;

public interface IModelFactory {

	public IModel createModel(String name) throws ConnectException, UnexpectedReplyException, CompilerInstantiationException, InvocationError;
	public IComponent createComponent(String name) throws ConnectException, UnexpectedReplyException, CompilerInstantiationException, InvocationError;

}
