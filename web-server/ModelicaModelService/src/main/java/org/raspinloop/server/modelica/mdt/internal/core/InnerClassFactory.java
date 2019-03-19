package org.raspinloop.server.modelica.mdt.internal.core;

import javax.annotation.Resource;

import org.raspinloop.server.modelica.mdt.core.IDefinitionLocation;
import org.raspinloop.server.modelica.mdt.core.IMoClassLoader;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass;
import org.raspinloop.server.modelica.mdt.core.IModelicaProject;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass.Restriction;
import org.raspinloop.server.modelica.mdt.core.compiler.IModelicaCompiler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class InnerClassFactory {

	@Autowired
	BeanFactory beanFactory;	 
	
	@Resource
	private IModelicaCompiler compilerProxy;
	
	public IMoClassLoader getMoClassLoaderInstance(IModelicaClass ownerClass) {
        return (IMoClassLoader) beanFactory.getBean("moClassLoader", ownerClass);
    }
	
	public InnerClass build(IModelicaProject project, String name, Restriction restriction, IDefinitionLocation location)
	{
		return new InnerClass(this, project, name, restriction, location, compilerProxy);
	}
	
	public InnerClass build(IModelicaClass parent, String name, Restriction restriction, IDefinitionLocation location)
	{
		return new InnerClass(this,parent,name,restriction,location, compilerProxy);		
	}
}
