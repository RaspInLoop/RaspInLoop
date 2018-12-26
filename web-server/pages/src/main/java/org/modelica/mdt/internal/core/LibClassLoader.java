package org.modelica.mdt.internal.core;

import java.util.Collection;
import java.util.Optional;

import org.modelica.mdt.core.IMoClassLoader;
import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IStandardLibrary;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.openmodelica.corba.ConnectException;

public class LibClassLoader implements IMoClassLoader {

	private Collection<IModelicaClass> knowPackages;

	public LibClassLoader(IStandardLibrary standardLibrary) throws ConnectException, CompilerInstantiationException {
		knowPackages = standardLibrary.getPackages();
	}

	@Override
	public IModelicaClass getPackage(String packageName) {
		 Optional<IModelicaClass> mopackage = knowPackages.stream()
					.filter(p -> Packages.isSubPackage(p.getFullName(), packageName))
					.findFirst();
		if (mopackage.isPresent()){	
		 	return mopackage.get()
		 			.getMoClassLoader()
					.getPackage(packageName);
		}
		else {
			return null;
		}
	}

	@Override
	public IModelicaClass getClass(String className) {
		 Optional<IModelicaClass> mopackage = knowPackages.stream()
					.filter(p -> Packages.isSubPackage(p.getFullName(), className))
					.findFirst();
		if (mopackage.isPresent()){	
		 	return mopackage.get()
		 			.getMoClassLoader()
					.getClass(className);
		}
		else {
			return null;
		}
	}

}
