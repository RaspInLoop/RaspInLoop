/******************************************************************************
 * @author Adrian Pop [adrpo@ida.liu.se, http://www.ida.liu.se/~adrpo]
 * Copyright (c) 2002-2006, Adrian Pop [adrpo@ida.liu.se],
 * Programming Environments Laboratory (PELAB),
 * Department of Computer and Information Science (IDA), 
 * Linköpings University (LiU). 
 * All rights reserved.
 *
 * http://www.ida.liu.se/~adrpo/license/
 *
 * NON-COMMERCIAL terms and conditions [NON-COMMERCIAL setting]:
 * Permission to use, copy, modify, and distribute this software and
 * its documentation in source or binary form (including products 
 * developed or generated using this software) for NON-COMMERCIAL 
 * purposes and without fee is hereby granted, provided that this 
 * copyright notice appear in all copies and that both the copyright 
 * notice and this permission notice and warranty disclaimer appear 
 * in supporting documentation, and that the name of The Author is not 
 * to be used in advertising or publicity pertaining to distribution 
 * of the software without specific, prior written permission.
 * 
 * COMMERCIAL terms and conditions [COMMERCIAL setting]:
 * COMMERCIAL use, copy, modification and distribution in source 
 * or binary form (including products developed or generated using
 * this software) is NOT permitted without prior written agreement 
 * from Adrian Pop [adrpo@ida.liu.se].
 * 
 * THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE,
 * INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, INDIRECT OR
 * CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS
 * OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE
 * OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE
 * USE OR PERFORMANCE OF THIS SOFTWARE.
 *****************************************************************************/
package org.modelica.mdt.internal.core;

import org.modelica.mdt.core.ISourceRegion;

/**
 * @author Adrian Pop
 */
public class DefinitionSourceRegion implements ISourceRegion
{
	private int startLine;
	private int startColumn;
	private int endLine;
	private int endColumn;		

	/**
	 * @author Adrian Pop
	 */	
	public DefinitionSourceRegion(int sl, int sc, int el, int ec)
	{
		this.startLine   = sl;
		this.startColumn = sc;
		this.endLine     = el;
		this.endColumn   = ec;
	}
	
	public int [] getPosition()
	{
		int[] pos = {startLine, startColumn, endLine, endColumn};
		return pos;
	}
	
	public void setPosition(int startLine, int startColumn, int endLine, int endColumn)
	{
		this.startLine   = startLine;
		this.startColumn = startColumn;
		this.endLine     = endLine;
		this.endColumn   = endColumn;
	}
	
	public int getStartLine()   { return startLine; }
	public int getStartColumn()	{ return startColumn; }
	public int getEndLine()     { return endLine; }
	public int getEndColumn()   { return endColumn; }

	/**
	 * @author Adrian Pop
	 * @param <code>ISourceRegion otherRegion</code>
	 * @return <code>true</code> if this region contains the region gived as parameter
	 * @since 0.6.8
	 */
	public boolean contains(ISourceRegion otherRegion)
	{
		// check lines first
		if (startLine < otherRegion.getStartLine() &&
			endLine > otherRegion.getEndLine())
			return true;

		// check lines first
		if (startLine <= otherRegion.getStartLine() &&
			endLine > otherRegion.getEndLine())
			return true;

		// check lines first
		if (startLine < otherRegion.getStartLine() &&
			endLine >= otherRegion.getEndLine())
			return true;		
		
		// lines are equal
		if (startLine == otherRegion.getStartLine() && endLine == otherRegion.getEndLine())
		{
			// all equal, return true!
			if (startColumn == otherRegion.getStartColumn() && endColumn == otherRegion.getEndColumn())
				return true;
		
			if (startColumn <= otherRegion.getStartColumn() && endColumn > otherRegion.getEndColumn())
				return true;

			if (startColumn < otherRegion.getStartColumn() && endColumn >= otherRegion.getEndColumn())
				return true;
		}
		
		return false;
	}
	
}
