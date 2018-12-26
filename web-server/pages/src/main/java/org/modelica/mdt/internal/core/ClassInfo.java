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

import org.modelica.mdt.core.Element;
import org.modelica.mdt.core.IDefinitionLocation;
import org.modelica.mdt.core.IModelicaClass.Restriction;
import org.modelica.mdt.core.IllegalRestrictionException;
import org.modelica.mdt.core.List;
import org.modelica.mdt.core.ListElement;
import org.modelica.mdt.core.ModelicaParserException;
import org.modelica.mdt.core.compiler.IClassInfo;
import org.modelica.mdt.core.compiler.ModelicaParser;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;

/**
 * An object that converts output of OMC's getClassInformation(cref)
 * to IClassInfo interface, the interface that Core plugin understands
 *
 * @author Homer Simpson
 */
public class ClassInfo implements IClassInfo
{
	private Restriction restrictionType;
	private boolean isEncapsulated;
	private DefinitionLocation defLocation;
	private String documentation;

	/**
	 * Create a ClassInfo object from the raw output of
	 * OMC's getClassInformation() function.
	 *
	 * @param rawList the row output
	 * @throws ModelicaParserException if could not parse rawList
	 * @throws IllegalRestrictionException if could not parse provided restriction type
	 * @throws UnexpectedReplyException if the contents of the list are wierd
	 */
	public ClassInfo(String rawList) throws ModelicaParserException, IllegalRestrictionException, UnexpectedReplyException
	{
		String sourceFilePath = null;
		List defLocList = null;
		documentation = null;

		/*
		 * parse the interesting bits of getClassInformation() output, it's
		 * format is as follows:
		 *
		 * (copied from interactive_api.txt)
         * getClassInformation(cref)
         *      returns all known class information using this format:
         *
         *  {"restriction", "comment", "file.mo", {partial,final,encapsulated},{"(readonly|writable)",startLine, startColumn, endLine, endColumn}, {dimensions}}
         *  with the following type for the components:
         *  {"string","string","string","string",{bool,bool,bool},{"string",int,int,int,int}}
         *  example:
         *  {"package","Modelica Standard Library (Version 3.1)","F:/OpenModelica1.9.2-r24371/lib/omlibrary/Modelica 3.1/package.mo",{false,false,false},{"writable",2,1,5197,13},{}}
         *
         *  // this changed to:
         *  {"restriction", "comment", partial, final, encapsulated, "file.mo", writable:false|true, startLine, startColumn, endLine, endColumn, {dimensions}}
         *  ("package","Modelica Standard Library - Version 3.2.1 (Build 3)",false,false,false,"c:/bin/cygwin/home/adrpo/dev/OpenModelica/build/lib/omlibrary/Modelica 3.2.1/package.mo",false,2,1,7864,13,{})
         *
		 */

		int i = 0, startLine = 0, startColumn = 0, endLine = 0, endColumn = 0;
		String str = "";
		/*
		 * parse the list and then put stuff that we are interested in into
		 * member fields
		 */

		// check if we have the new way:
		if (rawList.trim().startsWith("("))
		{
			for (ListElement le : ModelicaParser.parseList(rawList))
			{
				switch (i)
				{
				case 0: /* class restriction */
					parseRestrictionType(le);
					break;
				case 1: /* class documentation */
					parseDocumentation(le);
					break;
				case 2: /* partial */
				case 3: /* final */
					break;
				case 4: /* encapsulated */
					str = ((Element)le).toString();
					isEncapsulated = str.equalsIgnoreCase("true");
					break;
				case 5: /* source file path */
					sourceFilePath = parseSourceFilePath(le);
					break;
				case 6: /* writable */
					break;
				case 7: // startLine
					startLine = Integer.parseInt(((Element)le).toString());
					break;
				case 8: // startColumn
					startColumn = Integer.parseInt(((Element)le).toString());
					break;
				case 9: // endLine
					endLine = Integer.parseInt(((Element)le).toString());
					break;
				case 10: // endColumn
					endColumn = Integer.parseInt(((Element)le).toString());
					break;
				case 11: // dimensions
					break;
				}
				i++;
			}

			if (i < 11)
			{
				throw new UnexpectedReplyException("class info list is to short");
			}

			if (startColumn == 0) {
				assert(startLine == 1);
				startColumn = 1;
			}
		}
		else if (rawList.trim().startsWith("{")) // the old way
		{
			for (ListElement le : ModelicaParser.parseList(rawList))
			{
				switch (i)
				{
				case 0: /* class restriction */
					parseRestrictionType(le);
					break;
				case 1: /* class documentation */
					parseDocumentation(le);
					break;
				case 2: /* list that contains restricted attribute value */
					parseEncapsulated(le);
					break;
				case 3: /* source file path */
					sourceFilePath = parseSourceFilePath(le);
					break;
				case 4: /* list that contains definitions file coordinates */
					defLocList = checkLocationList(le);
					break;
				}
				i++;
			}

			if (i < 5)
			{
				throw new UnexpectedReplyException("class info list is to short");
			}
			startLine = Integer.parseInt(((Element)defLocList.elementAt(1)).toString());
			startColumn = Integer.parseInt(((Element)defLocList.elementAt(2)).toString());
			endLine =  Integer.parseInt(((Element)defLocList.elementAt(3)).toString());
			endColumn = Integer.parseInt(((Element)defLocList.elementAt(4)).toString());

			if (startColumn == 0) {
				assert(startLine == 1);
				startColumn = 1;
			}
		}
		else
		{
			throw new UnexpectedReplyException("getClassInformation is not well formed");
		}

		defLocation = new DefinitionLocation(sourceFilePath, startLine, startColumn, endLine, endColumn);
	}

	/**
	 * Parse the element that represent restriction type
	 */
	private void parseDocumentation(ListElement le) throws UnexpectedReplyException, IllegalRestrictionException
	{
		if (!(le instanceof Element))
		{
			throw new UnexpectedReplyException("expected simple element, got a list");
		}

		String str = ((Element)le).toString();

		/*
		 * remove " around restriction type name
		 */
		if (str.length() < 2)
		{
			/*
			 * can't be two " around anything in a string with length
			 * less then 2, we don't quite expect this
			 */
			throw new UnexpectedReplyException(str);
		}
		str = str.substring(1, str.length() - 1);

		documentation = str;
	}


	/**
	 * Parse the element that represent restriction type
	 */
	private void parseRestrictionType(ListElement le)
		throws UnexpectedReplyException, IllegalRestrictionException
	{
		if (!(le instanceof Element))
		{
			throw new UnexpectedReplyException("expected simple element, got a list");
		}

		String str = ((Element)le).toString();

		/*
		 * remove " around restriction type name
		 */
		if (str.length() < 2)
		{
			/*
			 * can't be two " around anything in a string with length
			 * less then 2, we don't quite expect this
			 */
			throw new UnexpectedReplyException(str);
		}
		str = str.substring(1, str.length() - 1);

		restrictionType = Restriction.parse(str);
	}

	/**
	 * check that list representing definitions file coordinates looks sane
	 */
	private List checkLocationList(ListElement le)
		throws UnexpectedReplyException
	{
		if (!(le instanceof List))
		{
			throw new UnexpectedReplyException("expected list, got a simple element");
		}

		if (((List)le).size() < 5)
		{
			throw new UnexpectedReplyException("list to short");
		}

		return ((List)le);
	}

	/**
	 * parse the list element that represent encapsulates status of the class
	 */
	private void parseEncapsulated(ListElement le)
		throws UnexpectedReplyException
	{
		if (!(le instanceof List))
		{
			throw new UnexpectedReplyException("expected list, got a simple element");
		}

		Element element = null;
		try
		{
			element = (Element) ((List)le).elementAt(2);
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new UnexpectedReplyException("to short list");
		}
		catch (ClassCastException e)
		{
			throw new UnexpectedReplyException("expected simple element, " + "got a list");
		}
		isEncapsulated = element.toString().equalsIgnoreCase("true");
	}


	/**
	 * parse the element that contains source file path
	 */
	private String parseSourceFilePath(ListElement le)
		throws UnexpectedReplyException
	{
		if (!(le instanceof Element))
		{
			throw new UnexpectedReplyException("expected simple element, got a list");
		}

		String str = ((Element)le).toString();

		/*
		 * remove " around restriction type name
		 */
		if (str.length() < 2)
		{
			/*
			 * can't be two " around anything in a string with length
			 * less then 2, we don't quite expect this
			 */
			throw new UnexpectedReplyException(str);
		}
		return str.substring(1, str.length() - 1);
	}


	/**
	 * @see org.modelica.mdt.core.compiler.IClassInfo#getRestriction()
	 */
	public Restriction getRestriction()
	{
		return restrictionType;
	}

	/**
	 * @see org.modelica.mdt.core.compiler.IClassInfo#getEncapsulated()
	 */
	public boolean getEncapsulated()
	{
		return isEncapsulated;
	}

	/**
	 * @see org.modelica.mdt.core.compiler.IClassInfo#getDefinitionLocation()
	 */
	public IDefinitionLocation getDefinitionLocation()
	{
		return defLocation;
	}

	/**
	 * @author Adrian Pop
	 * @return the documentation for this element or null if there isn't any.
	 */
 	public String getDocumentation()
	{
		return documentation;
	}
}
