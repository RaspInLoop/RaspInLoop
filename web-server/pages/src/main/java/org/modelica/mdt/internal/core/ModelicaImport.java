/*
 * This file is part of Modelica Development Tooling.
 *
 * Copyright (c) 2005, Linköpings universitet, Department of
 * Computer and Information Science, PELAB
 *
 * All rights reserved.
 *
 * (The new BSD license, see also
 * http://www.opensource.org/licenses/bsd-license.php)
 *
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * * Neither the name of Linköpings universitet nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.modelica.mdt.internal.core;

import org.modelica.mdt.core.IDefinitionLocation;
import org.modelica.mdt.core.IMoClassLoader;
import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IModelicaElement;
import org.modelica.mdt.core.IModelicaImport;
import org.modelica.mdt.core.IModelicaProject;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;

import lombok.extern.slf4j.Slf4j;

/**
 * An implementation of IModelicaImport interface.
 * 
 * This is just a basicaly struct for storing import info.
 */
@Slf4j
public class ModelicaImport extends ModelicaElement implements IModelicaImport
{
	protected boolean DEBUG = false;
	/* the import type this class is representing */ 
	private Type type;
	/* the imported package, loaded lazily to improve latency and be more eclipsish */
	private IModelicaClass importedPackage = null;
	/* for renaming inports this is the new name of the imported package */
	private String alias;
	private Visibility visibility;
	private IDefinitionLocation location;
	
	/*
	 * data needed to lazily load the imported package
	 */
	IModelicaProject importedPackageContainerProject;
	String importedPackageName;
		
	/**
	 * Create an import of the qualified or unqualified type
	 * @param containerProject the project where the import statment is defined
	 * @param isQualified wheter if this is a qualified import
	 * @param importedElement the full name if the imported package/class
	 */
	public ModelicaImport(
			IModelicaElement parent, 
			IModelicaProject containerProject, 
			boolean isQualified, 
			String importedElement,
			Visibility visibility, 
			IDefinitionLocation location) 
	{ 
		super(parent);
		this.type = isQualified ? Type.QUALIFIED : Type.UNQUALIFIED;		
		this.importedPackageContainerProject = containerProject;
		this.importedPackageName = importedElement;
		this.visibility = visibility;
		this.location = location;
	}
	
	/**
	 * Create an import if renaming type.
	 * 
	 * @param containerProject the project where the import statment is defined
	 * @param alias the new name of the imported package
	 * @param importedElement the full name if the imported package/class
	 */
	public ModelicaImport(
			IModelicaElement parent, 
			IModelicaProject containerProject, 
			String alias, 
			String importedElement,
			Visibility visibility, 
			IDefinitionLocation location) 
	{ 
		super(parent);
		this.type = Type.RENAMING;
		this.alias = alias;
		this.importedPackageContainerProject = containerProject;
		this.importedPackageName = importedElement;
		this.visibility = visibility;
		this.location = location;
	} 
		
	/**
	 * @see org.modelica.mdt.core.IModelicaImport#getType()
	 */
	public Type getType()
	{
		return type;
	}

	/**
	 * @see org.modelica.mdt.core.IModelicaImport#getElementName()
	 */
	public String getElementName()
	{
		String importName = importedPackageName;
		if (type == Type.UNQUALIFIED) importName += ".*";
		if (type == Type.RENAMING) importName += "=" + alias;
		return "import " + importName + ";";
	}
	
	/**
	 * @see org.modelica.mdt.core.IModelicaImport#getFullName()
	 */
	public String getFullName()
	{
		return getElementName();
	}
	
	
	/**
	 * @see org.modelica.mdt.core.IModelicaImport#getImportedPackage()
	 */
	public IModelicaClass getImportedPackage() 
		throws ConnectException, CompilerInstantiationException, 
			UnexpectedReplyException, InvocationError
	{
		if (importedPackage == null)
		{
			loadImportedPackage();
		}
		return importedPackage;
	}

	/**
	 * @see org.modelica.mdt.core.IModelicaImport#getAlias()
	 */
	public String getAlias()
	{
		return alias;
	}
	
	/**
	 * Loads a reference to the package that is imported by this statement.
	 *
	 * This function is used to implemet lazy loading of imported package
	 * when it is first requested.
	 */
	private synchronized void loadImportedPackage() 
		throws ConnectException, CompilerInstantiationException, 
			UnexpectedReplyException, InvocationError
	{
		if(importedPackageContainerProject != null)
		{
			/* a package from some workbench project imported */
			importedPackage = importedPackageContainerProject.getClass(importedPackageName);
		}
		else
		{
			importedPackage = getMoClassLoader().getClass(importedPackageName);			
		}
				
		if (importedPackage == null)
		{
			/* the package specified in this import statment does not exists */
			//TODO throw an exception or something
			log.warn("omg, omg, omg ! " + importedPackageName);
		}
}
	
	/**
	 * @see org.modelica.mdt.core.IModelicaElement#getLocation()
	 */
	public IDefinitionLocation getLocation() 
	{
		return location;
	}

	@Override
	public String getFilePath() throws ConnectException, UnexpectedReplyException, InvocationError
	{
		return location.getPath();
	}
	
	public Visibility getVisibility()
	{
		return visibility;
	}
	
	@Override
	public String toString() {		
			return getFullName() +"[type:"+getType()+"]";
	}

	@Override
	public IMoClassLoader getMoClassLoader() {
		// Return the Top Level class Loader
		IModelicaElement parent = getParent();
		while (parent.getParent() != null)
			parent = parent.getParent();
		return parent.getMoClassLoader();
	}

	@Override
	public String getIconAnnotation() {
		return "";
	}
}
