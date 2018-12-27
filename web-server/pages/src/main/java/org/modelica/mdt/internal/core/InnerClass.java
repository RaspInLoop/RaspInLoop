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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.modelica.mdt.core.IDefinitionLocation;
import org.modelica.mdt.core.IMoClassLoader;
import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IModelicaComponent;
import org.modelica.mdt.core.IModelicaElement;
import org.modelica.mdt.core.IModelicaExtends;
import org.modelica.mdt.core.IModelicaImport;
import org.modelica.mdt.core.IModelicaProject;
import org.modelica.mdt.core.IParameter;
import org.modelica.mdt.core.ISignature;
import org.modelica.mdt.core.IllegalRestrictionException;
import org.modelica.mdt.core.IllegalVisibilityException;
import org.modelica.mdt.core.List;
import org.modelica.mdt.core.ModelicaParserException;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.ElementInfo;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.ModelicaParser;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;
import org.springframework.data.annotation.Transient;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * A package that is defined inside a modelica file or in the system library.
 *
 * @author Adrian Pop
 * @author Elmir Jagudin
 */
@Slf4j
public class InnerClass extends ModelicaClass {
	/*
	 * Contents of a class are loaded lazily when they are first queried and
	 * 'cached' by storing them as member fields.
	 *
	 * currently following contents of a modelica class are available for
	 * queries:
	 *
	 * - restriction of the class - encapsulated status of the class - class
	 * definition location in the source file - subclasses/subpackages - class
	 * components - special input/output components - import statements
	 *
	 * The above information is fetched via the following methods in
	 * CompilerProxy:
	 *
	 * getElements() provides: - subclasses/subpackages - class components -
	 * special input/output components - import statements
	 *
	 * This information is collectively called for 'class components'.
	 *
	 * getClassInfo() provides: - restriction of the class - encapsulated status
	 * of the class - class definition location in the source file This
	 * information is collectively called for 'class attributes'.
	 *
	 * Whenever it is detected that the source file where this modelica class is
	 * defined have changed on the disk this class is notified by having its
	 * reload() method invoked.
	 */

	/* subpackages and subclasses hashed by the their short name */
	private Hashtable<String, IModelicaElement> children = null;
	IDefinitionLocation fLocation = null;
	Restriction fRestriction;
	/* the import statements */
	private Collection<IModelicaImport> imports;
	/* the extends statements */
	private Collection<IModelicaExtends> extendsStmt;
	private Collection<IModelicaClass> components;
	private int componentIdx =0;

	/* input and output parameters found in the signature */
	private LinkedList<IParameter> inputParams = null;
	private LinkedList<IParameter> outputParams = null;
	
	private ArrayList<String> componentAnnotations =  new ArrayList<>();
	
	@Transient
	private IMoClassLoader classLoader;
	
	@Transient
	private InnerClassFactory innerClassFactory;


	public InnerClassFactory getInnerClassFactory() {
		if (innerClassFactory == null){
			
		}
		return innerClassFactory;
	}

	public InnerClass(InnerClassFactory innerClassFactory, IModelicaProject project, String name, Restriction restriction, IDefinitionLocation location)  {
		this(innerClassFactory, project, null, name, restriction,location);
	}

	public InnerClass(InnerClassFactory innerClassFactory, IModelicaClass parent, String name, Restriction restriction, IDefinitionLocation location)  {
		this(innerClassFactory, parent, parent, name, restriction,location);
	}

	public InnerClass(InnerClassFactory innerClassFactory, IModelicaElement parent, IModelicaClass parentNamespace, String name, Restriction restriction, IDefinitionLocation location)  {
		super(parent, restriction, location);
		this.innerClassFactory = innerClassFactory;			
		this.parentNamespace = parentNamespace;		
		this.name = name;
		setFullName();
		fLocation = location;
		fRestriction = restriction;
		log.trace("--> InnerClass {}[{}] creation",this.getFullName(), this.hashCode());
		if (children == null) {
			try {
				children = loadElements();
			} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
				log.error("Cannot LoadElement of {}: {}",name, e.getMessage() );				
			}				
		}
		lookUpComponentType();	
		log.trace("<-- InnerClass {} created",this.getFullName());
	}

	private void lookUpComponentType() {
		components.stream()
				  .filter(IModelicaComponent.class::isInstance)
				  .map(IModelicaComponent.class::cast)
				  .forEach(c -> c.lookUpTypeName());
		
	}
	
	/**
	 * @see org.modelica.mdt.core.IParent#getChildren()
	 */
	@Override
	public Collection<IModelicaElement> getChildren() throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
		if (children == null) {
			children = loadElements();
		}
		return children.values();
	}

	private Hashtable<String, IModelicaElement> loadElements()
			throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
		Hashtable<String, IModelicaElement> elements = new Hashtable<String, IModelicaElement>();

		log.trace("-->loadElements for {}[]{}",this.getFullName(), this.hashCode());
		imports = Collections.synchronizedList(new LinkedList<IModelicaImport>());
		inputParams = new LinkedList<IParameter>();
		outputParams = new LinkedList<IParameter>();
		extendsStmt = new LinkedList<IModelicaExtends>();
		components = new LinkedList<IModelicaClass>();
 
		Restriction restriction = getRestriction();

		if (restriction == IModelicaClass.Restriction.RECORD) {
			IModelicaElement p = getParentNamespace();

			if (p != null && p instanceof IModelicaClass) {
				IModelicaClass modelicaClass = (IModelicaClass) p;
				String typeName = modelicaClass.getElementName();
				Restriction parentRestriction = modelicaClass.getRestriction();

				if (parentRestriction == IModelicaClass.Restriction.UNIONTYPE) {
					Parameter parameter = new Parameter("parent", typeName);

					outputParams.add(parameter);
				}
			}
		}
		// used to parser Import, extends, comonents etc...
		Collection<ElementInfo> fullNameElements = CompilerProxy.getElements(fullName);

		log.trace("   load Extends Elements");
		fullNameElements.stream()
						.filter(e -> "extends".equals(e.getElementType()))
						.map(this::toExtendsElement)
						.filter(Objects::nonNull)
						.forEach(i -> {
							extendsStmt.add(i);
							elements.put(i.getElementName(), i);
						});

		log.trace("   load Import Elements");
		fullNameElements.stream()
						.filter(e -> "import".equals(e.getElementType()))
						.map(this::toImportElement)
						.filter(Objects::nonNull)
						.forEach(i -> {
							imports.add(i);
							elements.put(i.getElementName(), i);
						});

		// need to have import and extends processed before processing
		// components.
		log.trace("   load Components Elements");
		componentIdx = 0;
		componentAnnotations=getComponentAnnotations();
		fullNameElements.stream()
						.filter(e -> "component".equals(e.getElementType()))
						.map(this::toComponent)
						.filter(Objects::nonNull)
						.forEach(i -> {
							elements.put(i.getElementName(), i);
							if (restriction == IModelicaClass.Restriction.FUNCTION || restriction == IModelicaClass.Restriction.BLOCK) {
								if (i.getVisibility().equals("public") && i.getDirection().equals("input")) {
									Parameter parameter = new Parameter(i.getElementName(), i.getTypeName());

									inputParams.add(parameter);
								} else if (i.getVisibility().equals("public") && i.getDirection().equals("output")) {
									Parameter parameter = new Parameter(i.getElementName(), i.getTypeName());
									
								outputParams.add(parameter);
								}
							} else if (restriction == IModelicaClass.Restriction.RECORD) {
								if (i.getVisibility().equals("public")) {
									Parameter parameter = new Parameter(i.getElementName(), i.getTypeName());
									
									inputParams.add(parameter);
								}
							}
						});

		log.trace("   load ClassDef Elements");
		fullNameElements.stream()
						.filter(e -> "classdef".equals(e.getElementType()))
						.map(this::toSubPackage)
						.forEach(i -> {
							components.add(i);
							elements.put(i.getElementName(), i);
							getMoClassLoader().classCreated(i);
							});
		
		log.trace("<--loadElements done for {}, {} found",this.getFullName(), elements.size() );
		return elements;
	}

	private ArrayList<String> getComponentAnnotations() {
		try {
			return CompilerProxy.getComponentAnnotations(this.getFullName());
		} catch (ConnectException | CompilerInstantiationException | ModelicaParserException e) {
			log.warn("Cannot getAnnotation for components of {}: {}", this, e.getMessage());
			return new ArrayList<>();
		}
		
	}

	private IModelicaExtends toExtendsElement(ElementInfo info) {

		IModelicaElement.Visibility vis;

		try {
			vis = IModelicaElement.Visibility.parse(info.getElementVisibility(), true);
		} catch (IllegalVisibilityException e) {
			// TODO Logger("Unexpected visibility: " + e.getMessage());
			return null;
		}
		String elementFile = info.getElementFile();
		int startLine = info.getElementStartLine();
		int startCol = info.getElementStartColumn();
		int endLine = info.getElementEndLine();
		int endCol = info.getElementEndColumn();
		IDefinitionLocation location = new DefinitionLocation(elementFile, startLine, startCol, endLine, endCol);
		String path = info.getPath();
		IModelicaExtends ext = new ModelicaExtends(this, path, vis, location);

		return ext;
	}

	private IModelicaComponent toComponent(ElementInfo info) {

		/*
		 * names have following format:
		 * names={component_name,"component_comment"} we need to get the
		 * component name
		 */

		try {
			IModelicaElement.Visibility vis = IModelicaElement.Visibility.parse(info.getElementVisibility(), true);

			List comp = ModelicaParser.parseList(info.getNames());

			String elementFile = info.getElementFile();
			int startLine = info.getElementStartLine();
			int startCol = info.getElementStartColumn();
			int endLine = info.getElementEndLine();
			int endCol = info.getElementEndColumn();
			String direction = info.getDirection();
			IDefinitionLocation location = new DefinitionLocation(elementFile, startLine, startCol, endLine, endCol);

			String componentName = comp.elementAt(0).toString();
			String typeName = info.getTypeName();	
			String annotation = componentAnnotations.get(componentIdx++);			
			
			ModelicaComponent modelicaComponent = new ModelicaComponent(this, componentName, typeName,  vis, location, direction, annotation);
			return modelicaComponent;
		} catch (ModelicaParserException | IllegalVisibilityException e) {
			// TODO Logger new UnexpectedReplyException("Unable to parse
			// returned list: " + e.getMessage());
			return null;
		}
	}

	private IModelicaImport toImportElement(ElementInfo info) {

		IModelicaElement.Visibility vis;

		try {
			vis = IModelicaElement.Visibility.parse(info.getElementVisibility(), true);
		} catch (IllegalVisibilityException e) {
			// TODO Logger("Unexpected visibility: " + e.getMessage());
			return null;
		}
		String importType = info.getKind();
		IModelicaImport imp = null;
		String path = info.getPath();

		String elementFile = info.getElementFile();
		int startLine = info.getElementStartLine();
		int startCol = info.getElementStartColumn();
		int endLine = info.getElementEndLine();
		int endCol = info.getElementEndColumn();
		IDefinitionLocation location = new DefinitionLocation(elementFile, startLine, startCol, endLine, endCol);
		IModelicaProject project = getProject();

		if (importType.equals("qualified")) {
			imp = new ModelicaImport(this, project, true, path, vis, location);
		} else if (importType.equals("unqualified")) {
			imp = new ModelicaImport(this, project, false, path, vis, location);
		} else if (importType.equals("named")) {
			String id = info.getId();

			imp = new ModelicaImport(this, project, id, path, vis, location);
		} else {
			// TODO: error Log/Manager
			// ErrorManager.logBug(CorePlugin.getSymbolicName(), "import
			// statment of unexpected type '" + importType + "' encountered");
		}
		return imp;
	}

	private InnerClass toSubPackage(ElementInfo info) {
		String elementFile = info.getElementFile();
		int startLine = info.getElementStartLine();
		int startCol = info.getElementStartColumn();
		int endLine = info.getElementEndLine();
		int endCol = info.getElementEndColumn();
		IDefinitionLocation location = new DefinitionLocation(elementFile, startLine, startCol, endLine, endCol);
		String className = info.getClassName();
		Restriction restr = null;

		try {
			restr = Restriction.parse(info.getClassRestriction());
		} catch (IllegalRestrictionException e) {
			restr = null;
		}

		InnerClass innerClass = getInnerClassFactory().build(this, className, restr, location);
		
		return innerClass;
	}	

	/**
	 * @see org.modelica.mdt.core.IParent#hasChildren()
	 */
	@Override
	public boolean hasChildren() throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
		boolean hasChildren = getChildren().size() > 0;

		return hasChildren;
	}

	@Override
	public Collection<IModelicaImport> getImports() throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
		if (children == null) {
			children = loadElements();
		}

		return imports;
	}

	@Override
	public Collection<IModelicaExtends> getExtends() throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
		if (children == null) {
			children = loadElements();
		}

		return extendsStmt;
	}

	@Override
	public Collection<IModelicaClass> getInheritedClasses() throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
		if (children == null) {
			children = loadElements();
		}
		int nbInherit = CompilerProxy.getInheritanceCount(this.getFullName());
		return IntStream.range(1, nbInherit+1).mapToObj(i -> {
			try {
				return CompilerProxy.getNthInheritedClass(this.getFullName(), i);
			} catch (ConnectException | UnexpectedReplyException | CompilerInstantiationException e) {
				return new CompilerResult(new String[0], e.getMessage());
			}
		}).filter(cr -> {
			return StringUtils.isBlank(cr.getError());
		}).map(cr -> {
			return cr.getFirstResult();
		})
		.map(getMoClassLoader()::getClass)
		.filter(Objects::nonNull)
		.collect(Collectors.toList());
	}

	@Override
	public IModelicaElement getParent() {
		IModelicaElement p = super.getParent();
		return p;
	}

	@Override
	public ISignature getSignature() throws ConnectException, InvocationError, UnexpectedReplyException, CompilerInstantiationException {
		if (inputParams == null || outputParams == null) {
			loadElements();
		}

		IParameter[] inputParamsArray = inputParams.toArray(new IParameter[0]);
		IParameter[] outputParamsArray = outputParams.toArray(new IParameter[0]);
		Signature signature = new Signature(inputParamsArray, outputParamsArray);

		return signature;
	}

	@Override
	public IDefinitionLocation getLocation() throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
		IDefinitionLocation location = fLocation;

		if (fLocation == null) {
			location = super.getLocation();
		}

		return location;
	}

	@Override
	public boolean isConnector(){
		try {
			return CompilerProxy.isConnector(fullName);
		} catch (ConnectException | UnexpectedReplyException | CompilerInstantiationException e) {
			log.warn("Cannot determine connector for {}: {}", fullName, e.getMessage());
			return false;
		}
	}

	@Override
	public String toString() {

		try {
			return getElementName() + getExtends().stream().map(IModelicaExtends::toString).collect(Collectors.joining(", ", "[", "] "))
					+ getImports().stream().map(IModelicaImport::toString).collect(Collectors.joining(", ", "[", "] ")) + getChildren().size() + " children";
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
			return super.toString();
		}
	}

	@Override
	public IMoClassLoader getMoClassLoader() {	
		if (classLoader == null)
			classLoader = getInnerClassFactory().getMoClassLoaderInstance(this);
		return classLoader;
	}

	@Override
	public String getIconAnnotation() {
		try {
			return CompilerProxy.getIconAnnotation(fullName);
		} catch (ConnectException | UnexpectedReplyException | CompilerInstantiationException e) {
			log.warn("Cannot load icon of {}: {}", fullName, e.getMessage());
			return "";
		}		
	}
}
