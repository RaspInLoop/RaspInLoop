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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.modelica.mdt.core.ICompilerResult;
import org.modelica.mdt.core.IDefinitionLocation;
import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.List;
import org.modelica.mdt.core.ModelicaParserException;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.ElementInfo;
import org.modelica.mdt.core.compiler.IClassInfo;
import org.modelica.mdt.core.compiler.IModelicaCompiler;
import org.modelica.mdt.core.compiler.IParseResults;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;

/**
 * This class provides one to one mapping to IModelicaCompiler interface,
 * however it hides the details of lazy loading and instantiating of the
 * compiler object via the extension points.
 *
 * All access to the modelica compiler should be made through this class.
 *
 */
//TODO move this to org.modelica.mdt.internal.core package !
public class CompilerProxy {
	
	private static IModelicaCompiler compiler = null;
	//final static ILock lock = Platform.getJobManager().newLock();

	public static IModelicaCompiler getCompiler() throws CompilerInstantiationException {
		if (compiler == null) {
			compiler = loadCompiler();
		}

		return compiler;
	}
	/**
	 * Load the first best modelica compiler proxy contributed by
	 * some other plugin via the org.modelica.mdt.core.compiler extension point.
	 *
	 * @return
	 * @throws CompilerInstantiationException
	 */
	private static IModelicaCompiler loadCompiler() throws CompilerInstantiationException {
		
		return new OMCProxyWrapper();
	}

	public synchronized static String getCompilerName() throws CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		String compilerName = compiler.getCompilerName();
		return compilerName;
	}

	public synchronized static IParseResults loadSourceFile(File file)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		IParseResults parseResults = compiler.loadSourceFile(file);
		return parseResults;
	}

	public synchronized static List getClassNames(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		List classNames = compiler.getClassNames(className);
		return classNames;
	}

	public synchronized static Collection<ElementInfo> getElements(String className)
			throws ConnectException, InvocationError, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		Collection<ElementInfo> elements = compiler.getElements(className);
		return elements;
	}

	public synchronized static IDefinitionLocation getClassLocation(String className)
			throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		IDefinitionLocation definitionLocation = compiler.getClassLocation(className);
		return definitionLocation;
	}

	public synchronized static IModelicaClass.Restriction getRestriction(String className)
			throws ConnectException, CompilerInstantiationException, UnexpectedReplyException {
		return getCompiler().getRestriction(className);
	}

	/**
	 * @return the top classes in the standard library
	 */
	public synchronized static String[] getStandardLibrary() throws ConnectException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		String[] stdlib = compiler.getStandardLibrary();
		return stdlib;
	}

	public synchronized static IClassInfo getClassInfo(String className)
			throws CompilerInstantiationException, ConnectException, UnexpectedReplyException {
		return getCompiler().getClassInfo(className);
	}

	public synchronized static ICompilerResult getClassString(String className)
			throws CompilerInstantiationException, ConnectException, UnexpectedReplyException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult classString = compiler.getClassString(className);
		return classString;
	}

	/**
	 * @author Adrian Pop
	 * @param command
	 * @return the result of the command execution
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */
	public synchronized static ICompilerResult sendExpression(String command, boolean showInConsole)
			throws CompilerInstantiationException, ConnectException, UnexpectedReplyException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult res = compiler.sendExpression(command, showInConsole);
		return res;
	}

	/**
	 * @author Magnus Sj�strand
	 * @param command
	 * @return the result of the command execution
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */

	public synchronized static ICompilerResult getNthInheritedClass(String className, int n)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult res = compiler.getNthInheritedClass(className, n);
		return res;
	}

	/**
	 * @author Magnus Sj�strand
	 * @param command
	 * @return the result of the command execution
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */

	public synchronized static int getInheritanceCount(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		int resNum = compiler.getInheritanceCount(className);

		return resNum;
	}
	
	
	/**
	 * @author Magnus Sj�strand
	 * @param command
	 * @return the result of the command execution
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */

	public synchronized static ICompilerResult getNthAlgorithmItem(String className, int n)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult res = compiler.getNthAlgorithmItem(className, n);
		return res;
	}

	/**
	 * @author Magnus Sj�strand
	 * @param command
	 * @return the result of the command execution
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */

	public synchronized static int getAlgorithmItemsCount(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		int resNum = compiler.getAlgorithmItemsCount(className);

		return resNum;
	}

	/**
	 * @author Magnus Sj�strand
	 * @param command
	 * @return the result of the command execution
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */

	public synchronized static ICompilerResult getNthEquationItem(String className, int n)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult res = compiler.getNthEquationItem(className, n);
		return res;
	}

	/**
	 * @author Magnus Sj�strand
	 * @param command
	 * @return the result of the command execution
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */

	public synchronized static int getEquationItemsCount(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		int resNum = compiler.getEquationItemsCount(className);

		return resNum;
	}

	/**
	 * @author Magnus Sj�strand
	 * @param command
	 * @return the result of the command execution
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */

	public synchronized static List getComponents(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		List componentList = compiler.getComponents(className);
		return componentList;
	}

	/**
	 * @author Magnus Sj�strand
	 * @param command
	 * @return the result of the command execution
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */

	public synchronized static boolean existClass(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		boolean exist = compiler.existClass(className);
		return exist;
	}

	public synchronized static boolean isPackage(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		boolean exist = compiler.isPackage(className);
		return exist;
	}
	
	public synchronized static boolean isConnector(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		boolean exist = compiler.isConnector(className);
		return exist;
	}

	public synchronized static ICompilerResult getErrorString()
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult res = compiler.getErrorString();
		return res;
	}

	public synchronized static ICompilerResult loadFile(String classPath)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult res = compiler.loadFile(classPath);
		return res;
	}

	public synchronized static ICompilerResult getSourceFile(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult res = compiler.getSourceFile(className);
		return res;
	}

	public synchronized static List parseFile(String fileName)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		List res = compiler.parseFile(fileName);
		return res;
	}

	public synchronized static ICompilerResult getClassRestriction(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult res = compiler.getClassRestriction(className);
		return res;
	}

	public synchronized static ICompilerResult getClassComment(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult res = compiler.getClassComment(className);
		return res;
	}

	public synchronized static ICompilerResult buildModel(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult res = compiler.buildModel(className);
		return res;
	}

	/**
	 * @author Magnus Sj�strand
	 * @param command
	 * @return the result of the command execution
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */

	public synchronized static ICompilerResult getNthImport(String className, int n)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult res = compiler.getNthImport(className, n);

		return res;
	}
	
	public synchronized static ICompilerResult searchClassNames(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		ICompilerResult res = compiler.searchClassNames(className);

		return res;
	}
	

	/**
	 * @author Magnus Sj�strand
	 * @param command
	 * @return the result of the command execution
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */

	public synchronized static int getImportCount(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		int resNum = compiler.getImportCount(className);
		System.out.println("WE IMPORTED: " + resNum + " AMOUNT!");
		return resNum;
	}
	
	/**
	 * Returns the MSL path.
	 * @return
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */
	public synchronized static String getModelicaPath()
			throws CompilerInstantiationException, ConnectException, UnexpectedReplyException {
		return getCompiler().getModelicaPath();
	}

	/**
	 * @param command
	 * @return the result of the command execution
	 * @throws CompilerInstantiationException
	 * @throws ConnectException
	 * @throws UnexpectedReplyException
	 */

	public synchronized static String instantiateModel(String className)
			throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
		IModelicaCompiler compiler = getCompiler();
		String res = compiler.instantiateModel(className);		
		return res;
	}
	public static boolean isPrimitive(String className) 
		throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
	IModelicaCompiler compiler = getCompiler();
	boolean exist = compiler.isPrimitive(className);
	return exist;
	}
	public static String getIconAnnotation(String className) 
		throws ConnectException, UnexpectedReplyException, CompilerInstantiationException {
	IModelicaCompiler compiler = getCompiler();
	String res = compiler.getIconAnnotation(className);		
	return res;
	}
	public static ArrayList<String> getComponentAnnotations(String className)
			throws ConnectException, CompilerInstantiationException, ModelicaParserException {
		IModelicaCompiler compiler = getCompiler();
		ArrayList<String> res = compiler.getComponentAnnotations(className);		
		return res;
	}
	
	
}
