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
package org.raspinloop.server.modelica.mdt.core;

import java.nio.file.Path;
import java.util.Collection;

import org.openmodelica.corba.ConnectException;
import org.raspinloop.server.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.raspinloop.server.modelica.mdt.core.compiler.InvocationError;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;

/**
 * @author Elmir Jagudin
 *
 */
public interface IModelicaProject extends IModelicaElement
{
	
	/**
	 * get package in this project by name
	 * 
	 * @param packageName the name of the project to fetch
	 * @return the package or null if no package is found
	 */
	public IModelicaClass getClass(String className) 
		throws ConnectException, CompilerInstantiationException, 
			UnexpectedReplyException,  InvocationError;
	
	/**
	 * get all packages/classes defined in the root namespace in this project
	 *
	 * @return ze root packages
	 */
	public Collection<? extends IModelicaClass> getRootClasses() 
		throws ConnectException, CompilerInstantiationException,
			UnexpectedReplyException;

	/**
	 * Find modelica wraped resource by path in this project
	 * 
	 * @param resourcePath project relative path to the resource to fetch
	 * @return the resource or null if the resource is not found
	 */
	public IModelicaElement findElement(Path resourcePath) 
		throws ConnectException, UnexpectedReplyException, 
			CompilerInstantiationException, InvocationError;

}
