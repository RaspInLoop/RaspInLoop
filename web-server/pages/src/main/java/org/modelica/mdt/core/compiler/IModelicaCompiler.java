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

package org.modelica.mdt.core.compiler;

import java.io.File;
import java.util.Collection;

import org.modelica.mdt.core.ICompilerResult;
import org.modelica.mdt.core.IDefinitionLocation;
import org.modelica.mdt.core.IModelicaClass.Restriction;
import org.modelica.mdt.core.List;
import org.openmodelica.corba.ConnectException;

/**
 * The interface to a modelica compiler.
 *
 *  This interface must be implemented by the extender of the
 *  org.modelica.mdt.compiler point. This defines the methods used to
 *  access the modelica compiler by the MDT core plugin.
 */
public interface IModelicaCompiler
{
	/**
	 * @return symbolic name of the compiler suitable for end user consumption
	 */
	public String getCompilerName();

	/**
	 * Load the modelica source file into the internal database.
	 *
	 * The file must be parsed before it is stored into the database.
	 * The file may contain syntax and other errors that are discovered while
	 * it is parsed. The compiler must make the best effort to parse as much
	 * as possible even if errors are discovered. Compiler should return both
	 * parse errors and the contents of the file.
	 *
	 * @param file the modelica source code file to load
	 * @return the modelica elements and parsing errors found in the file
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */
	public IParseResults loadSourceFile(File file)
			throws ConnectException, UnexpectedReplyException;

	/**
	 * Fetches the list of name of subclasses in a given class
	 *
	 * @param className the name of the class where to look for subclasses
	 * @return the list of the names of subclasses
	 */
	public List getClassNames(String className)
			throws ConnectException, UnexpectedReplyException;

	/**
	 * Fetches the information on the elements of a class. Elements in
	 * a class are subclasses, components, import statements etc.
	 */
	public Collection<ElementInfo> getElements(String className)
			throws ConnectException, InvocationError, UnexpectedReplyException;

	/**
	 * Fetches the location of the definition of the class in the
	 * source code file.
	 *
	 * @param className the name of the class to fetch the location of
	 * @return the line number and the file path where the class is defined
	 */
	public IDefinitionLocation getClassLocation(String className)
			throws ConnectException, UnexpectedReplyException, InvocationError;

	/**
	 * Fetches the restriction type of the class.
	 *
	 * @param className the name of the class to fetch restriction type of
	 * @return the restriction type of the class
	 * @throws UnexpectedReplyException
	 */
	public Restriction getRestriction(String className)
			throws ConnectException, UnexpectedReplyException;
	/**
	 * The method returns a list of top level packages in the standard library.
	 *
	 * @return the list of names of top-level packages in the standard library
	 * @throws ConnectException
	 */
	public String[] getStandardLibrary() throws ConnectException;

	/**
	 * Fetches information about a class, such as encapsulated status,
	 * class definition location and so on.
	 *
	 * @param className the full name of the class to fetch info on
	 */
	public IClassInfo getClassInfo(String className)
		throws ConnectException, UnexpectedReplyException;

	/**
	 * Fetches the listing of a class
	 *
	 * @param className the full name of the class get the string for
	 */
	public ICompilerResult getClassString(String className)
		throws ConnectException, UnexpectedReplyException;

	/**
	 * Sends a command to the compiler and gets the result string
	 *
	 * @param command
	 */
	public ICompilerResult sendExpression(String command, boolean showInConsole)
		throws ConnectException, UnexpectedReplyException;


	
	/**
	 * Fetches the nth name of subclasses in a given class
	 *
	 * @param className is the name of the class where to look for subclasses, nth subclass
	 * @return the list of the names of subclasses
	 */
	public ICompilerResult getNthInheritedClass(String className, int n)
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Fetches the number of subclasses in a given class
	 *
	 * @param className is the name of the class where to look for amount of subclasses
	 * @return integer amount of the names of subclasses
	 */
	public int getInheritanceCount(String className) 
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Fetches the nth name of algorithm item in a given class
	 *
	 * @param className is the name of the class where to look for algorithm items, nth algorithm items
	 * @return the list of the names of algorithm items
	 */
	public ICompilerResult getNthAlgorithmItem(String className, int n)
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Fetches the number of algorithm items in a given class
	 *
	 * @param className is the name of the class where to look for amount of algorithm items
	 * @return integer amount of the names of algorithm items
	 */
	public int getAlgorithmItemsCount(String className) 
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Fetches the nth name of equation item in a given class
	 *
	 * @param className is the name of the class where to look for equation items, nth equation items
	 * @return the list of the names of algorithm items
	 */
	public ICompilerResult getNthEquationItem(String className, int n)
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Fetches the number of equation items in a given class
	 *
	 * @param className is the name of the class where to look for amount of equation items
	 * @return integer amount of the names of equation items
	 */
	public int getEquationItemsCount(String className) 
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Fetches the list of name of components in a given class
	 *
	 * @param className the name of the class where to look for components
	 * @return the list of the names of components
	 */
	public List getComponents(String className)
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Fetches a boolean about if given class exists
	 *
	 * @param className the name of the class where to look for information
	 * @return a boolean about the existence
	 */
	public boolean existClass(String className)
			throws ConnectException, UnexpectedReplyException;

	/**
	 * Fetches a boolean about if given class is a package
	 *
	 * @param className the name of the class where to look for information
	 * @return a boolean about the identity
	 */
	public boolean isPackage(String className)
			throws ConnectException, UnexpectedReplyException;
	

	public boolean isConnector(String className) throws ConnectException;
	
	/**
	 * Fetches the error-information of current run
	 *
	 * @param className the name of the class where to look for information
	 * @return a list consisting the error
	 */
	public ICompilerResult getErrorString()
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Loads a given file into the system
	 *
	 * @param classPath that is the files path
	 * @return the list of the information of the loading
	 */
	public ICompilerResult loadFile(String classPath)
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Fetches the list of information of given class
	 *
	 * @param className the name of the class we want to find source of
	 * @return the list with the name of the source file
	 */
	public ICompilerResult getSourceFile(String className)
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Fetches the list of classes of given file
	 *
	 * @param fileName the name of the file we want to find classes in
	 * @return the list with the names of the classes
	 */
	public List parseFile(String fileName)
			throws ConnectException, UnexpectedReplyException;

	/**
	 * Fetches the type of given class
	 *
	 * @param className the name of the class we want to find type of
	 * @return the list with the name of the type
	 */
	public ICompilerResult getClassRestriction(String className)
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Fetches the comment of given class
	 *
	 * @param className the name of the class we want to find comment of
	 * @return the list with the comment
	 */
	public ICompilerResult getClassComment(String className)
			throws ConnectException, UnexpectedReplyException;

	/**
	 * Builds the model with various output-files
	 *
	 * @param className the name of the class we want to build
	 * @return if the build was successful
	 */
	public ICompilerResult buildModel(String className)
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Fetches the nth name of imports in a given class
	 *
	 * @param className is the name of the class where to look for subclasses, nth subclass
	 * @return the list of the names of imports
	 */
	public ICompilerResult getNthImport(String className, int n)
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Fetches the number of imports in a given class
	 *
	 * @param className is the name of the class where to look for amount of subclasses
	 * @return integer amount of the names of imports
	 */
	public int getImportCount(String className) 
			throws ConnectException, UnexpectedReplyException;
	
	/**
	 * Returns the MSL path. 
	 * @param className
	 * @return
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */
	public String getModelicaPath() 
			throws ConnectException, UnexpectedReplyException;

	/**
	 * Instantiates a model/class and returns a string containing the flat class definition.
	 * Ex: instantiateModel(dcmotor)
	 *
	 * @param modelname the modelname
	 * @return Reply from OMC
	 * @throws ConnectException 
	 */
	public String instantiateModel(String modelname) throws ConnectException;

	public ICompilerResult searchClassNames(String className) throws ConnectException;

	public boolean isPrimitive(String className) throws ConnectException;


}
