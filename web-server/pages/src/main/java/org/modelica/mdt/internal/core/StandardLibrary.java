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

import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.modelica.mdt.core.IDefinitionLocation;
import org.modelica.mdt.core.IMoClassLoader;
import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IModelicaElement;
import org.modelica.mdt.core.IModelicaProject;
import org.modelica.mdt.core.IParent;
import org.modelica.mdt.core.IStandardLibrary;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;
import org.springframework.stereotype.Component;


public class StandardLibrary  extends ModelicaElement implements IStandardLibrary, IParent
{
	@Resource
	private InnerClassFactory innerClassFactory;
	
	@Resource
	private IMoClassLoader libClassLoader;
	
	private IModelicaProject project;
	
	LinkedList<IModelicaClass> packages = null;
		
	public StandardLibrary( IModelicaProject project){
		super(project);	
	}

	public Collection<IModelicaClass> getPackages() throws ConnectException, CompilerInstantiationException
	{
		if (packages == null)
		{
			packages = new LinkedList<IModelicaClass>();

			for (String packageName : CompilerProxy.getStandardLibrary())
			{
				packages.add(innerClassFactory.build(project, packageName,  IModelicaClass.Restriction.PACKAGE, null));
			}
		}

		return packages;
	}

	public String getElementName()
	{
		String oml = null;
		try {
			oml = CompilerProxy.getModelicaPath();
		} catch (CompilerInstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnexpectedReplyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (oml == null)
			return "Libraries";
		return "Libraries: " + oml;
	}

	public String getFullName()
	{
		return getElementName();
	}

	public IDefinitionLocation getLocation()
	{
		return null;
	}

	public String getFilePath()
	{
		return null;
	}

	/**
	 * A utilit methods to look-up a package by it's full name.
	 * 
	 * This method looks in all subtrees of the top elements provided.
	 * 
	 * @param topElements the roots of the elements subtrees to look among
	 * @param packageName the package's full name that we want to locate
	 * @return the package found or null if there were no such package
	 */
	public IModelicaClass getPackage(String packageName)
		throws ConnectException, CompilerInstantiationException, 
			UnexpectedReplyException, InvocationError
	{
		/*
		 * split up the full package names in separate package names
		 * e.g. foo.bar.gazonk into foo, bar and gazonk
		 * 
		 * look up then foo package among the root packages, bar
		 * among the foo's children and gazonk among bar's offspring 
		 */
		LinkedList<? super IModelicaElement> currentChildren =	new LinkedList<IModelicaElement>();
		currentChildren.addAll(getPackages());

		/* iterate over separate package names */
		StringTokenizer pkgNames = new StringTokenizer(packageName, ".");
		String subname;
		IModelicaClass currentParent = null;
		
		while(pkgNames.hasMoreTokens())
		{
			subname = pkgNames.nextToken();
			
			/* look among packages to find the subname */
			currentParent = null;
			while (!currentChildren.isEmpty())
			{
				Object o = currentChildren.removeFirst();
				if (!(o instanceof IModelicaClass))
				{
					/* skip other elements (files, components, etc) */
					continue;
				}
				
				/* here we know that o is of type IModelicaClass */
				IModelicaClass p = (IModelicaClass) o;

				if (p.getElementName().equals(subname))
				{
					/*
					 * we found our next subpackage,
					 * continiue to look among it's children
					 */
					currentChildren.clear();
					currentChildren.addAll(p.getChildren());
					currentParent = p;
					break;
				}
			}
			
			if (currentParent == null)
			{
				/*
				 * we failed to find our subpackage, the requested packages
				 * does not exsits, bail out
				 */
				break;
			}
		}
		
		return currentParent;
	}


	public Collection<? extends IModelicaElement> getChildren()
		throws ConnectException, CompilerInstantiationException
	{
		return getPackages();
	}

	public boolean hasChildren()
	{
		return true;
	}

	@Override
	public IMoClassLoader getMoClassLoader() {
		return libClassLoader;
	}

	@Override
	public String getIconAnnotation() {
		// TODO Auto-generated method stub
		return "";
	}


}
