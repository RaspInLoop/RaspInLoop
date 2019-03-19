package org.raspinloop.server.modelica.mdt.internal.core;

import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.openmodelica.corba.ConnectException;
import org.raspinloop.server.modelica.mdt.core.IMoClassLoader;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass;
import org.raspinloop.server.modelica.mdt.core.IModelicaElement;
import org.raspinloop.server.modelica.mdt.core.IModelicaImport;
import org.raspinloop.server.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.raspinloop.server.modelica.mdt.core.compiler.IModelicaCompiler;
import org.raspinloop.server.modelica.mdt.core.compiler.InvocationError;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Scope(value = "prototype")
public class MoClassLoader implements IMoClassLoader {

	private IModelicaClass ownerClass;

	@Resource
	private IModelicaCompiler compilerProxy;

	@Override
	public String toString() {
		return "MoClassLoader of " + ownerClass.getFullName();
	}

	public MoClassLoader(IModelicaClass ownerClass) {
		this.ownerClass = ownerClass;

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

		try {
			IModelicaClass found = getInSubPackage(this.ownerClass, packageName);
			if (found != null) {
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
			if (ownerClass.getParentNamespace() != null) {
				log.debug("<-- getPackage {} returned from Parent CL", packageName);
				return ownerClass.getParentNamespace().getMoClassLoader().getPackage(packageName);
			}
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
			log.error("Cannot load package for {}:{}", packageName, e.getMessage());
		}
		return null;
	}

	@Override
	public IModelicaClass getClass(String className) {
		log.debug("--> getClass {} from {}", className, this);
		if (className.equals(ownerClass.getFullName()))
			return ownerClass;

		try {
			IModelicaClass moPackage;
			String pkgName = Packages.getPackage(className);
			if (StringUtils.isBlank(pkgName)) {
				// Maybe a primitive type
				if (compilerProxy.isPrimitive(className)) {
					log.debug("<-- getClass {}: primitive returned", className);
					return Primitives.create(className);
				} else {
					moPackage = ownerClass.getParentNamespace();
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
			log.debug("<-- getClass {} returned with {} ", className, result);
			return result;
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
			log.error("cannot get class {} :", className, e.getMessage());
			return null;
		}
	}

	@Override
	public void classCreated(IModelicaClass moclass) {	
	}

}
