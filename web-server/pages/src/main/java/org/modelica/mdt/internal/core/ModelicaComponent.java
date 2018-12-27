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
import org.modelica.mdt.core.IMoClassLoader;
import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IModelicaComponent;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Homer Simpson
 */
@Slf4j
public class ModelicaComponent extends ModelicaElement implements IModelicaComponent {
	private String name;
	private Visibility visibility;
	private IDefinitionLocation location;
	private String direction;
	private IModelicaClass type; //lazy evaluation
	private String typeName;	
	private String componentAnnotation; 

	/**
	 * Create class component
	 * 
	 * @param parent the class where this component is defined
	 * @param name name if this component
	 * @param connector 
	 * @param visibility whatever this component is public or protected
	 * @param location location in the source code file
	 * @param direction 
	 * @param componentIdx 
	 */
	public ModelicaComponent(IModelicaClass parent, String name, String typeName, Visibility visibility, IDefinitionLocation location, String direction,  String annotation) {
		
		super(parent);
		this.name = name;
		this.typeName = typeName;
		this.direction = direction;
		this.visibility = visibility;
		this.location = location;
		this.componentAnnotation = annotation;
		
		log.trace("ModelicaComponent {} created", name);
	}

	@Override
	public void lookUpTypeName(){
		log.trace("lookup {}", typeName);
		type = getMoClassLoader().getClass(typeName);
	}
	
	@Override
	public String getElementName() {
		return name;
	}
	
	@Override
	public String getDirection() {
		return direction;
	}

	@Override
	public Visibility getVisibility() {
		return visibility;
	}


	/**
	 * @see org.modelica.mdt.core.IModelicaElement#getLocation()
	 */
	@Override
	public IDefinitionLocation getLocation() {
		return location;
	}

	@Override
	public String getFilePath() throws ConnectException, UnexpectedReplyException, InvocationError {
		return location.getPath();
	}

	@Override
	public String getFullName() {
		return getParent().getFullName() + "." + name;
	}

	@Override
	public String getTypeName() {
		if (type == null)
			lookUpTypeName();
		if (type == null)
			return typeName;
		else
			return type.getFullName();
	}

	
	@Override
	public String toString() {
		String ret = "ModelicaComponent - name = \"" + name + "\", typeName = \"" + getTypeName() + "\".\n" +
				"location: \"" + location + "\".";
		
		return ret;
	}

	@Override
	public boolean isConnector() {
		if (type == null)
			lookUpTypeName();
		return type != null && type.isConnector();	
	}
	
	@Override
	public String getIconAnnotation() {
			if (type == null) {
				lookUpTypeName();
			}
			if (type != null) {
				return type.getIconAnnotation();
			}
			else return "";
	}


	@Override
	public IMoClassLoader getMoClassLoader() {
		return getParent().getMoClassLoader();
	}

	@Override
	public String getComponentAnnotation() {
		
		return componentAnnotation;
	}

}
