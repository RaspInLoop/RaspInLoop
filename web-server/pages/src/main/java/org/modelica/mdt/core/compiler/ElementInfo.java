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

package org.modelica.mdt.core.compiler;

import org.modelica.mdt.core.Element;
import org.modelica.mdt.core.List;
import org.modelica.mdt.core.ListElement;


/**
 * Encapsulates information on an element and provides
 * a api to retrive this information.
 */
public class ElementInfo
{
	/* the 'raw unparsed' elements information list */
	private List elementsInfo;

	/*
	 * parsed fields from the list
	 */
	private String elementType = null;
	private Integer elementStartLine = null;
	private Integer elementStartColumn = null;
	private Integer elementEndLine = null;
	private Integer elementEndColumn = null;
	private String elementFile = null;
	private String className = null;
	private String classRestriction = null;
	private String classFileName = null;
	private String names = null;
	private String elementVisibility = null;
	private String kind = null;
	private String path = null;
	private String id = null;
	private String typeName = null;
	private String direction = null;
	
	public ElementInfo(List elementsInfo)
	{
		this.elementsInfo = elementsInfo;
	}
	
	/**
	 * @return the contents of 'elementtype' field
	 */
	public String getElementType()
	{
		if (elementType == null)
		{
			parseUntil("elementtype");
		}
		return elementType;
	}

	/**
	 * @return the contents of 'classname' field
	 */
	public String getClassName()
	{
		if (className == null)
		{
			parseUntil("classname");
		}
		return className;
	}
	
	/**
	 * @return the contents of 'elementStartLine' field
	 */
	public int getElementStartLine()
	{
		if (elementStartLine == null)
		{
			parseUntil("elementStartLine");
		}
		return elementStartLine;
	}

	/**
	 * @return the contents of 'classStartColumn' field
	 */
	public int getElementStartColumn()
	{
		if (elementStartColumn == null)
		{
			parseUntil("elementStartColumn");
		}
		return elementStartColumn;
	}

	/**
	 * @return the contents of 'classEndLine' field
	 */
	public int getElementEndLine()
	{
		if (elementEndLine == null)
		{
			parseUntil("elementEndLine");
		}
		return elementEndLine;
	}
	
	/**
	 * @return the contents of 'classEndColumn' field
	 */
	public int getElementEndColumn()
	{
		if (elementEndColumn == null)
		{
			parseUntil("elementEndColumn");
		}
		return elementEndColumn;
	}
	
	/**
	 * @return the contents of 'elementfile' field
	 */
	public String getElementFile()
	{
		if (elementFile == null)
		{
			parseUntil("elementfile");
		}
		return elementFile;
	}

	/**
	 * @return the contents of 'classrestriction' field
	 */
	public String getClassRestriction()
	{
		if (classRestriction  == null)
		{
			parseUntil("classrestriction");
		}
		return classRestriction;
	}

	/**
	 * @return the contents of 'names' field
	 */
	public String getNames()
	{
		if (names  == null)
		{
			parseUntil("names");
		}
		return names;
	}
	
	/**
	 * @return the contents of 'elementVisibility' field
	 */
	public String getElementVisibility()
	{
		if (elementVisibility  == null)
		{
			parseUntil("elementvisibility");
		}
		return elementVisibility;
	}

	/**
	 * @return the contents of 'kind' field
	 */
	public String getKind()
	{
		if (kind  == null)
		{
			parseUntil("kind");
		}
		return kind;
	}

	/**
	 * @return the contents of 'path' field
	 */
	public String getPath()
	{
		if (path  == null)
		{
			parseUntil("path");
		}
		return path;
	}

	/**
	 * @return the contents of 'classfilename' field
	 */
	public String getClassFile()
	{
		if (classFileName  == null)
		{
			parseUntil("classfilename");
		}
		return classFileName;
	}
	
	/**
	 * @return the contents of 'path' field
	 */
	public String getId()
	{
		if (id  == null)
		{
			parseUntil("id");
		}
		return id;
	}

	/**
	 * @return the contents of 'typename' field
	 */
	public String getTypeName()
	{
		if (typeName  == null)
		{
			parseUntil("typename");
		}
		return typeName;
	}
	
	/**
	 * @return the contents of 'direction' field
	 */
	public String getDirection()
	{
		if (direction  == null)
		{
			parseUntil("direction");
		}
		return direction;
	}


	/**
	 * parse the elements information list until the specified field
	 * is found
	 * 
	 * @param fieldName the name of the field where to stop parsing
	 */
	private void parseUntil(String fieldName)
	{
		String rawField;
		int nameLength;
		/* counter for parsed elements */
		int parsedElements = 0; 

		for (ListElement element : elementsInfo)
		{
			parsedElements++;
			rawField = ((Element)element).toString();

			if (rawField.startsWith("elementtype="))
			{
				nameLength = 11;
				elementType = rawField.substring(nameLength+1).trim();
			}
			else if (rawField.startsWith("elementStartLine="))
			{
				nameLength = 16;
				elementStartLine = 
					new Integer(rawField.substring(nameLength+1).trim());
			}
			else if (rawField.startsWith("elementStartColumn="))
			{
				nameLength = 18;
				elementStartColumn = 
					new Integer(rawField.substring(nameLength+1).trim());
			}
			else if (rawField.startsWith("elementEndLine="))
			{
				nameLength = 14;
				elementEndLine = 
					new Integer(rawField.substring(nameLength+1).trim());
			}
			else if (rawField.startsWith("elementEndColumn="))
			{
				nameLength = 16;
				elementEndColumn = 
					new Integer(rawField.substring(nameLength+1).trim());
			}
			else if (rawField.startsWith("classname="))
			{
				nameLength = 9;
				className = rawField.substring(nameLength+1).trim();
			}
			else if (rawField.startsWith("classfilename="))
			{
				nameLength = 13;
				classFileName = rawField.substring(nameLength+1).trim();
			}			
			else if (rawField.startsWith("elementfile="))
			{
				nameLength = 11;
				elementFile = rawField.substring(nameLength+1).trim();
				
				/*
				 * remove "" around the path by removing
				 * first and last character
				 */
				elementFile = elementFile.substring(1, elementFile.length() - 1);

			}
			else if (rawField.startsWith("classrestriction="))
			{
				nameLength = 16;
				classRestriction = rawField.substring(nameLength+1).trim();
			}
			else if (rawField.startsWith("names="))
			{
				nameLength = 5;
				names = rawField.substring(nameLength+1).trim();
			}
			else if (rawField.startsWith("elementvisibility="))
			{
				nameLength = 17;
				elementVisibility = rawField.substring(nameLength+1).trim();
			}
			else if (rawField.startsWith("kind="))
			{
				nameLength = 4;
				kind = rawField.substring(nameLength+1).trim();
			}
			else if (rawField.startsWith("path="))
			{
				nameLength = 4;
				path = rawField.substring(nameLength+1).trim();
			}
			else if (rawField.startsWith("id="))
			{
				nameLength = 2;
				id = rawField.substring(nameLength+1).trim();
			}
			else if (rawField.startsWith("typename="))
			{
				nameLength = 8;
				typeName = rawField.substring(nameLength+1).trim();
			}
			else if (rawField.startsWith("direction="))
			{
				nameLength = 9;
				direction = rawField.substring(nameLength+1).trim();
				
				/*
				 * remove "" around the fields value by removing
				 * first and last character
				 */
				direction = 
					direction.substring(1, direction.length() - 1);

			}
			else /* we are not interedted in this particular field at all */
			{
				continue;
			}
			
			/* check if it's time to abort parsing */
			if (rawField.substring(0,nameLength).equals(fieldName))
			{
				break;
			}
		}

		/* 'cut off' the parsed part of the elements info list */
		elementsInfo = 
			elementsInfo.subList(parsedElements, elementsInfo.size());
	
	}
}
