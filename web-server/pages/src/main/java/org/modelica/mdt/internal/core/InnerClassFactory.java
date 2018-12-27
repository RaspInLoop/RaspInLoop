package org.modelica.mdt.internal.core;

import org.modelica.mdt.core.IDefinitionLocation;
import org.modelica.mdt.core.IMoClassLoader;
import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IModelicaClass.Restriction;
import org.modelica.mdt.core.IModelicaProject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class InnerClassFactory {

	@Autowired
	BeanFactory beanFactory;	 
	
	public IMoClassLoader getMoClassLoaderInstance(IModelicaClass ownerClass) {
        return (IMoClassLoader) beanFactory.getBean("moClassLoader", ownerClass);
    }
	
	public InnerClass build(IModelicaProject project, String name, Restriction restriction, IDefinitionLocation location)
	{
		return new InnerClass(this, project, name, restriction, location);
	}
	
	public InnerClass build(IModelicaClass parent, String name, Restriction restriction, IDefinitionLocation location)
	{
		return new InnerClass(this,parent,name,restriction,location);
		

	}
}
