/*
 * This file is part of Modelica Development Tooling.
 *
 * Copyright (c) 2005, Link�pings universitet, Department of
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
 * * Neither the name of Link�pings universitet nor the names of its
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
import org.modelica.mdt.core.IModelicaElement;
import org.modelica.mdt.core.IModelicaProject;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;

/**
 * @author Adrian Pop [adrpo@ida.liu.se, http://www.ida.liu.se/~adrpo]
 * @author Elmir Jagudin
 */
abstract public class ModelicaElement  implements IModelicaElement 
{
	public static ModelicaElement[] NO_ELEMENTS = new ModelicaElement[0];
	protected static final Object NO_INFO = new Object();
	
	private IModelicaElement parent;
	
	protected ModelicaElement(IModelicaElement parent)
	{
		this.parent = parent;
	}
	
	public IModelicaElement getParent()
	{
		return parent;
	}
	
	public IModelicaProject getProject()
	{
		if (parent == null)
		{
			return null;
		}
		return parent.getProject();
	}

	public IDefinitionLocation getLocation()
		throws  ConnectException, 
			UnexpectedReplyException, InvocationError,
			CompilerInstantiationException
	{
		/* we don't have a definition region by default */
		return null;
	}

	public String getFilePath() 
		throws ConnectException, UnexpectedReplyException, InvocationError,
			CompilerInstantiationException
	{
		/* we are not defined in an external file by default */
		return null;
	}	
	
	public Visibility getVisibility()
	{
		return Visibility.NONE;
	}
	
	/**
	 * @author Adrian Pop
	 * @return the documentation for this element or null if there isn't any.
	 */
	public String getDocumentation() throws ConnectException, UnexpectedReplyException, InvocationError,
	CompilerInstantiationException
	{
		return null;
	}

}
