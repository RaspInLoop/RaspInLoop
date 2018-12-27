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

package org.modelica.mdt.core;

import java.io.Serializable;

import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;

/**
 * Common protocol for all elements provided by the Modelica root.
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 * @author adrpo
 * @author Elmir Jagudin
 *
 */
public interface IModelicaElement  extends Serializable
{
	enum Visibility 
	{
		PUBLIC, PROTECTED, NONE;
		
		public static Visibility parse(String text, boolean forceCorrect) throws IllegalVisibilityException
		{
			if (text.equals("public"))
			{
				return PUBLIC;
			}
			else if (text.equals("protected"))
			{
				return PROTECTED;
			}
			if (forceCorrect) throw new IllegalVisibilityException("Unknown visibility qualifier");
			return NONE;	
		}
	};
	
	public Visibility getVisibility();
	
	public String getElementName();

	/**
	 * return the element's full name, that is if element's full name is
	 * foo.bar.hej then the prefix is foo.bar and short name is hej
	 * 
	 * @return the full name of this package
	 */
	public String getFullName();

	
	
	/**
	 * For elements that are defined inside a file this method returns
	 * the region of the file where the location is is defined.
	 * 
	 * If the element is not defined in a file, null is returned.
	 * 
	 * @throws UnexpectedReplyException 
	 * @throws ConnectException 
	 * @throws CoreException 
	 * @throws CompilerInstantiationException 
	 * 
	 */
	public IDefinitionLocation getLocation() 
		throws ConnectException, UnexpectedReplyException, 
			InvocationError,  CompilerInstantiationException;

	/**
	 * If this element is external then this method returns full path
	 * to the file where this element is defined. If this element is not
	 * external (e.g. defined inside the workspace) or is not defined in a
	 * file null is returned.
	 */
	public String getFilePath() 
		throws ConnectException, UnexpectedReplyException, InvocationError,
			CompilerInstantiationException;
		
	
	public IModelicaElement getParent();
		
	/**
	 * @return the project where this element is defined,
	 * elements from standard library returns null here
	 */
	public IModelicaProject getProject();

	/**
	 * @author Adrian Pop
	 * @return the documentation for this element or null if there isn't any.
	 */
	public String getDocumentation()  throws ConnectException, UnexpectedReplyException, InvocationError,
	CompilerInstantiationException;
	
	public IMoClassLoader getMoClassLoader();
	
	public String getIconAnnotation(); 
}
