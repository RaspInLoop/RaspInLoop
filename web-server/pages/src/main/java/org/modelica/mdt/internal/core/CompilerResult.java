package org.modelica.mdt.internal.core;

import org.modelica.mdt.core.ICompilerResult;
import org.openmodelica.corba.Result;

public class CompilerResult implements ICompilerResult 
{
	private String[] result;
	private String error;

	public CompilerResult(String[] result, String error)
	{
		this.result = result;
		this.error = error;
	}
	
	public String getError() {
		return error;
	}

	public String[] getResult() {
		return result;
	}

	public String getFirstResult() {
		return result[0];
	}

	public void trimFirstResult() {
		result[0] = result[0].trim();
	}
	
	public static ICompilerResult makeResult(String[] result, String error)
	{
		return new CompilerResult(result, error);
	}
	
	public static ICompilerResult makeResult(Result result )
	{
		String[] retval = { result.res };
		return new CompilerResult(retval , result.err);
	}
	

}
