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

import org.openmodelica.corba.ConnectException;
import org.raspinloop.server.modelica.mdt.core.IDefinitionLocation;
import org.raspinloop.server.modelica.mdt.core.IMoClassLoader;
import org.raspinloop.server.modelica.mdt.core.IModelicaElement;
import org.raspinloop.server.modelica.mdt.core.IModelicaExtends;
import org.raspinloop.server.modelica.mdt.core.compiler.InvocationError;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;

/**
 * An implementation of IModelicaExtends interface.
 * 
 * This is just a basicaly struct for storning extends info.
 */
public class ModelicaExtends extends ModelicaElement implements IModelicaExtends
{
	protected boolean DEBUG = false;
	private Visibility visibility;
	private IDefinitionLocation location;
	
	
	/*
	 * data needed to lazily load the extendsed package
	 */
	String extendedName;
	
	/**
	 * Create an extends of the qualified or unqualified type
	 * @param containerProject the project where the extends statment is defined
	 * @param isQualified wheter if this is a qualified extends
	 * @param extendsedElement the full name if the extendsed package/class
	 */
	public ModelicaExtends(
			IModelicaElement parent, 
			String extendedName,
			Visibility visibility, 
			IDefinitionLocation location) 
	{ 
		super(parent);
		this.extendedName = extendedName;
		this.visibility = visibility;
		this.location = location;
	}
		
	/**
	 * @see org.raspinloop.server.modelica.mdt.core.IModelicaExtends#getType()
	 */
	public String getElementName()
	{
		return extendedName;
	}
	
	/**
	 * @see org.raspinloop.server.modelica.mdt.core.IModelicaExtends#getType()
	 */
	public String getFullName()
	{
		return "extends " + extendedName + ";";
	}
	
	public Visibility getVisibility()
	{
		return visibility;
	}	
	
	/**
	 * @see org.raspinloop.server.modelica.mdt.core.IModelicaElement#getLocation()
	 */
	public IDefinitionLocation getLocation()
	{
		return location;
	}

	@Override
	public String getFilePath() 
		throws ConnectException, UnexpectedReplyException, InvocationError
	{
		return location.getPath();
	}
	
	@Override
	public String toString() {		
			return getFullName();
	}

	@Override
	public IMoClassLoader getMoClassLoader() {
		return null;
	}

	@Override
	public String getIconAnnotation() {
		return "";
	}
}
