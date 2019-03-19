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

package org.raspinloop.server.modelica.mdt.internal.core;

import java.util.stream.Collectors;

import org.openmodelica.corba.ConnectException;
import org.raspinloop.server.modelica.mdt.core.IDefinitionLocation;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass;
import org.raspinloop.server.modelica.mdt.core.IModelicaElement;
import org.raspinloop.server.modelica.mdt.core.IModelicaExtends;
import org.raspinloop.server.modelica.mdt.core.IModelicaImport;
import org.raspinloop.server.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.raspinloop.server.modelica.mdt.core.compiler.IClassInfo;
import org.raspinloop.server.modelica.mdt.core.compiler.IModelicaCompiler;
import org.raspinloop.server.modelica.mdt.core.compiler.InvocationError;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;

/**
 * Superclass of all modelica class/package representation, collects
 * generic package handling code.
 *
 * @author Adrian Pop [adrpo@ida.liu.se, http://www.ida.liu.se/~adrpo]
 * @author Elmir Jagudin
 * @author Andreas Remar
 * @author Kent Beck
 */
abstract public class ModelicaClass extends ModelicaElement implements IModelicaClass {


	/**
	 * The namespace where this class is defined or null if
	 * defined in top-level namespace
	 */
	protected IModelicaClass parentNamespace;

	/**
	 * the short name of this class
	 */
	protected String name;

	/**
	 * the fully qualified name of this class e.g. foo.bar.hej
	 */
	protected String fullName;


	// class attributes (type of restriction, encapsulated status, etc)
	private IClassInfo classAttributes = null;

	private IDefinitionLocation fLocation = null;

	private Restriction fRestriction = null;

	protected IModelicaCompiler compilerProxy;


	public ModelicaClass(IModelicaElement parent, Restriction restriction, IDefinitionLocation location, IModelicaCompiler compilerProxy) {
		super(parent);
		this.compilerProxy = compilerProxy;
		fLocation = location;
		fRestriction = restriction;
	}

	/**
	 * calculate the base name of this package
	 */
	protected void setFullName() {
		if (parentNamespace == null) {
			// special case for packages that are direct children of the root package
			fullName = name;
		}
		else { // general case
			fullName = parentNamespace.getFullName() + "." + name;
		}
	}

	@Override
	public String getPrefix() {
		String prefix = "";

		if (parentNamespace != null) {
			prefix = parentNamespace.getFullName();
		}

		return prefix;
	}

	@Override
	public String getElementName() {
		return name;
	}

	@Override
	public String getFullName() {
		return fullName;
	}

	
	@Override
	public IModelicaClass getParentNamespace() {
		return parentNamespace;
	}

	/**
	 * handles the lazyloading of class attributes
	 */
	protected IClassInfo getAttributes()
		throws CompilerInstantiationException, ConnectException, UnexpectedReplyException,  InvocationError {
		if (classAttributes == null) {
			classAttributes = compilerProxy.getClassInfo(fullName);
		}

		return classAttributes;
	}

	@Override
	public IDefinitionLocation getLocation()
			throws ConnectException, UnexpectedReplyException, InvocationError,  CompilerInstantiationException {
		IDefinitionLocation location = null;

		if (fLocation != null) {
			location = fLocation;
		}
		else {
			IClassInfo classInfo = getAttributes();
			location = classInfo.getDefinitionLocation();
		}

		return location;
	}

	@Override
	public String getFilePath()
		throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
		IClassInfo classInfo = getAttributes();
		IDefinitionLocation location = classInfo.getDefinitionLocation();
		String path = location.getPath();

		return path;
	}

	@Override
	public Restriction getRestriction()
		throws ConnectException, CompilerInstantiationException, UnexpectedReplyException,  InvocationError {
		Restriction restriction = null;

		if (fRestriction != null) {
			restriction = fRestriction;
		}
		else {
			IClassInfo classInfo = getAttributes();
			restriction = classInfo.getRestriction();
		}

		return restriction;
	}

	@Override
	public boolean isEncapsulated()
		throws CompilerInstantiationException, ConnectException, UnexpectedReplyException,  InvocationError {
		IClassInfo classInfo = getAttributes();
		boolean isEncapsulated = classInfo.getEncapsulated();

		return isEncapsulated;
	}


	@Override
	public String getDocumentation()
			throws ConnectException, InvocationError, UnexpectedReplyException, CompilerInstantiationException {
		IClassInfo classInfo = getAttributes();
		String documentation = classInfo.getDocumentation();

		return documentation;
	}
	
	@Override
	public String toString() {
		
		try {
			return getElementName()+
					getExtends().stream().map(IModelicaExtends::toString).collect(Collectors.joining(", ","[extends:","] "))+
					getImports().stream().map(IModelicaImport::toString).collect(Collectors.joining(", ","[import","] "))+
					getChildren().size() + " children";
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
			return super.toString();
		}						
	}
}
