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
import java.util.Collection;

import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;

/**
 * @author Elmir Jagudin
 * @author Adrian Pop [adrpo@ida.liu.se, http://www.ida.liu.se/~adrpo]
 */
public interface IModelicaClass extends IModelicaElement, IParent
{
	public enum Restriction
	{
		CLASS, MODEL, FUNCTION, RECORD, CONNECTOR, EXPANDABLE_CONNECTOR, BLOCK, TYPE, PACKAGE, UNIONTYPE;

		/**
		 * @param text the type of restriction
		 * @return the restriction provided
		 * @throws IllegalRestrictionException if unable to parse
		 * restriction
		 */
		public static Restriction parse(String text) throws IllegalRestrictionException
		{
			if (text.equalsIgnoreCase("CLASS"))
			{
				return CLASS;
			}
			else if (text.equalsIgnoreCase("PACKAGE"))
			{
				return PACKAGE;
			}
			else if (text.equalsIgnoreCase("MODEL"))
			{
				return MODEL;
			}
			else if (text.equalsIgnoreCase("CONNECTOR"))
			{
				return CONNECTOR;
			}
			else if (text.equalsIgnoreCase("EXPANDABLE CONNECTOR"))
			{
				return EXPANDABLE_CONNECTOR;
			}
			else if (text.equalsIgnoreCase("RECORD"))
			{
				return RECORD;
			}
			else if (text.equalsIgnoreCase("METARECORD"))
			{
				return RECORD;
			}
			else if (text.equalsIgnoreCase("BLOCK"))
			{
				return BLOCK;
			}
			else if (text.equalsIgnoreCase("FUNCTION"))
			{
				return FUNCTION;
			}
			else if (text.equalsIgnoreCase("TYPE"))
			{
				return TYPE;
			}
			else if (text.equalsIgnoreCase("UNIONTYPE"))
			{
				return UNIONTYPE;
			}

			/*
			 * this is an error condition, classRestriction is of unexpected
			 * type
			 */
			throw new IllegalRestrictionException(text);
		}
	};

	/**
	 * return the class' prefix, that is if class' full name is
	 * foo.bar.hej then the prefix is foo.bar and short name is hej
	 *
	 * @return the prefix of this package
	 */
	public String getPrefix();

	/**
	 * @return the restriction type of this class
	 */
	public Restriction getRestriction()
		throws ConnectException, CompilerInstantiationException,
				UnexpectedReplyException,  InvocationError;

	/**
	 * @return the imports made in this class
	 */
	public Collection<IModelicaImport> getImports()
		throws ConnectException, UnexpectedReplyException, InvocationError,
			CompilerInstantiationException;

	/**
	 * @return the imports made in this class
	 */
	public Collection<IModelicaExtends> getExtends()
		throws ConnectException, UnexpectedReplyException, InvocationError,
			CompilerInstantiationException;

	/**
	 * @return the class that defines the parent namespace or null if this class
	 * is defined in top namespace
	 */
	public IModelicaClass getParentNamespace();

	/**
	 *
	 * @return the signature of this class (input and output parameters)
	 */
	public ISignature getSignature()
		throws ConnectException, InvocationError, UnexpectedReplyException,
			CompilerInstantiationException;

	/**
	 * @return true if the class is encapsulated (prefix with keyword encapsulated)
	 */
	public boolean isEncapsulated()
		throws CompilerInstantiationException, ConnectException,
			UnexpectedReplyException, InvocationError;

	public boolean isConnector(); 
		

	Collection<IModelicaClass> getInheritedClasses() throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException;

	public String getIconAnnotation();
}
