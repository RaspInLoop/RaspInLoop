package org.modelica.mdt.internal.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import org.modelica.mdt.core.IDefinitionLocation;
import org.modelica.mdt.core.IMoClassLoader;
import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IModelicaClass.Restriction;
import org.modelica.mdt.core.IModelicaElement;
import org.modelica.mdt.core.IModelicaImport;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoClassLoader implements IMoClassLoader {

	private IModelicaClass ownerClass;
	private IMoClassLoader parentClassLoader;
	private Map<String, IModelicaClass> cache = new HashMap<>();

	@Override
	public String toString() {
		return "MoClassLoader of " + ownerClass.getFullName() + " cache size[" + cache.size() + "]";
	}

	public MoClassLoader(IModelicaClass ownerClass) {
		this.ownerClass = ownerClass;
		if (ownerClass.getParentNamespace() != null)
			this.parentClassLoader = ownerClass.getParentNamespace().getMoClassLoader();
	}

	private IModelicaClass getInSubPackage(IModelicaClass basePackage, String packageName)
			throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {

		LinkedList<? super IModelicaElement> currentChildren = new LinkedList<IModelicaElement>();
		currentChildren.addAll(basePackage.getChildren());
		packageName = Packages.relativize(basePackage.getFullName(), packageName);
		/* iterate over separate package names */
		StringTokenizer pkgNames = new StringTokenizer(packageName, ".");
		String subname;
		IModelicaClass currentParent = null;

		while (pkgNames.hasMoreTokens()) {
			subname = pkgNames.nextToken();

			/* look among packages to find the subname */
			currentParent = null;
			while (!currentChildren.isEmpty()) {
				Object o = currentChildren.removeFirst();
				if (!(o instanceof IModelicaClass)) {
					/* skip other elements (files, components, etc) */
					continue;
				}

				/* here we know that o is of type IModelicaClass */
				IModelicaClass p = (IModelicaClass) o;

				if (p.getElementName().equals(subname)) {
					/*
					 * we found our next subpackage, continiue to look among
					 * it's children
					 */
					currentChildren.clear();
					currentChildren.addAll(p.getChildren());
					currentParent = p;
					break;
				}
			}

			if (currentParent == null) {
				/*
				 * we failed to find our subpackage, the requested packages does
				 * not exsits, bail out
				 */
				break;
			}
		}

		return currentParent;
	}

	@Override
	public IModelicaClass getPackage(String packageName) {
		log.debug("--> getPackage {} from {}", packageName, this);

		if (Packages.isSamePackage(this.ownerClass.getFullName(), packageName)) {
			log.debug("<-- getPackage {}: same package", packageName);
			return this.ownerClass;
		}

		if (this.ownerClass.getFullName().equals(packageName)) {
			return this.ownerClass;
		}

		IModelicaClass cached = cache.get(packageName);
		if (cached != null) {
			log.debug("<-- getPackage {} found in cache", packageName);
			return cached;
		}

		try {
			IModelicaClass found = getInSubPackage(this.ownerClass, packageName);
			if (found != null) {
				cache.put(packageName, found);
				log.debug("<-- getPackage {} found in subPackage", packageName);
				return found;
			}

			for (IModelicaImport moImport : ownerClass.getImports()) {
				IModelicaClass importedPackage = moImport.getImportedPackage();
				if (importedPackage == null)
					continue;
				switch (moImport.getType()) {
				case QUALIFIED:
					if (importedPackage.getFullName().equals(packageName)) {
						log.debug("<-- getPackage {} found Qualified Import", packageName);
						return importedPackage;
					}
					break;
				case RENAMING:
					if (Packages.isSubPackage(moImport.getAlias(), packageName)) {
						String renamedPackageName = Packages.rename(packageName, moImport.getAlias(), importedPackage.getFullName());
						if (importedPackage.getFullName().equals(renamedPackageName)) {
							log.debug("<-- getPackage {} found Renamed Import", packageName);
							return importedPackage;
						}
						log.debug("<-- getPackage {} returned from Imported CL", packageName);
						return importedPackage.getMoClassLoader().getPackage(renamedPackageName);
					}
					break;
				case UNQUALIFIED:
					if (Packages.isSubPackage(importedPackage.getFullName(), packageName)) {
						log.debug("<-- getPackage {} returned from Imported CL", packageName);
						return importedPackage.getMoClassLoader().getPackage(packageName);
					}
					break;
				default:
					return null;
				}
			}
			// ask to the parent
			if (parentClassLoader != null) {
				log.debug("<-- getPackage {} returned from Parent CL", packageName);
				return parentClassLoader.getPackage(packageName);
			}
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
			// TODO logger
		}
		return null;
	}

	@Override
	public IModelicaClass getClass(String className) {
		log.debug("--> getClass {} from {}", className, this);
		if (className.equals(ownerClass.getFullName()))
			return ownerClass;
		
		IModelicaClass cached = cache.get(className);
		if (cached != null) {
			log.debug("<-- getClass {}: returned from cache", className);
			return cached;
		}
		try {
			IModelicaClass moPackage;
			String pkgName = Packages.getPackage(className);
			if (StringUtils.isBlank(pkgName)) {
				// Maybe a primitive type
				try {
					if (CompilerProxy.isPrimitive(className)) {
						log.debug("<-- getClass {}: primitive returned", className);
						return Primitives.create(className);
					} else {
						moPackage = ownerClass.getParentNamespace();
					}
					
				} catch (UnexpectedReplyException e) {
					log.debug("{} doesn't seem to be a primitive: ", className, e.getMessage());
					moPackage = ownerClass;
				}
				
			} else {
				moPackage = getPackage(pkgName);

				if (moPackage == null) {
					log.debug("<-- getClass {} NO FOUND!", className);
					return null;
				}
				// maybe a renamed import
				className = Packages.rename(className, pkgName, moPackage.getFullName());
			}
			
			String finalClassName = className;

			IModelicaClass result = moPackage.getChildren().stream().filter(IModelicaClass.class::isInstance).map(IModelicaClass.class::cast).filter(e -> {
				return Packages.isSamePackage(e.getFullName(), finalClassName);
			}).findFirst().orElse(null);
			cache.put("className", result);
			log.debug("<-- getClass {} returned with {} ", className, result);
			return result;
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
			log.error("cannot get class {} :", className, e.getMessage());
			return null;
		}
	}

}
