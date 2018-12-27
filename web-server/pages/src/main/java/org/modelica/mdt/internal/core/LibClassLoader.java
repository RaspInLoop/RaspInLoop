package org.modelica.mdt.internal.core;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.modelica.mdt.core.IMoClassLoader;
import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IStandardLibrary;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.openmodelica.corba.ConnectException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LibClassLoader implements IMoClassLoader {
	
//	@Resource 
//	public CacheManager cacheManager;
	
	private Collection<IModelicaClass> knowPackages;

	private IStandardLibrary standardLibrary;
	
	public LibClassLoader(IStandardLibrary standardLibrary) {
		this.standardLibrary = standardLibrary;
	}
	
	@Override
//	@Cacheable("moPackage")
	public IModelicaClass getPackage(String packageName) {
		if (knowPackages == null )
			try {
				knowPackages = standardLibrary.getPackages();
			} catch (ConnectException | CompilerInstantiationException e) {
				log.error("Cannot get lib packages: {}", e.getMessage());
			}
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
//	@Cacheable("moPackage")
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

	@Override
	public void classCreated(IModelicaClass moclass) {
//		cacheManager.getCache("moClasses").put(moclass.getFullName(), moclass);
	}

}
