package org.modelica.mdt.core;

public interface IMoClassLoader{

	// Load Package Class base on name and current package namespace and imports
	IModelicaClass getPackage(String packageName);

	// Load Class base on name and current package namespace and imports
	IModelicaClass getClass(String className);

	void classCreated(IModelicaClass moclass);


}
