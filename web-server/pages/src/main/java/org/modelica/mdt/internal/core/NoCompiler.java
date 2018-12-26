package org.modelica.mdt.internal.core;

import java.io.File;
import java.util.Collection;

import org.modelica.mdt.core.ICompilerResult;
import org.modelica.mdt.core.IDefinitionLocation;
import org.modelica.mdt.core.IModelicaClass.Restriction;
import org.modelica.mdt.core.List;
import org.modelica.mdt.core.compiler.ElementInfo;
import org.modelica.mdt.core.compiler.IClassInfo;
import org.modelica.mdt.core.compiler.IModelicaCompiler;
import org.modelica.mdt.core.compiler.IParseResults;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;

class NoCompiler implements IModelicaCompiler
{
	@Override
	public String getCompilerName()
	{
		return "Empty Compiler";
	}

	@Override
	public IParseResults loadSourceFile(File file)
	{
		return null;
	}

	@Override
	public List getClassNames(String className)
	{
		return null;
	}

	@Override
	public Collection<ElementInfo> getElements(String className)
	{
		return null;
	}

	@Override
	public IDefinitionLocation getClassLocation(String className)
	{
		return null;
	}

	@Override
	public Restriction getRestriction(String className)
	{
		return null;
	}

	@Override
	public String[] getStandardLibrary()
	{
		return null;
	}

	@Override
	public IClassInfo getClassInfo(String className)
	{
		return null;
	}

	@Override
	public ICompilerResult getClassString(String className)
	{
		return null;
	}

	@Override
	public ICompilerResult sendExpression(String command, boolean showInConsole)
	{
		return null;
	}

	
	@Override
	public ICompilerResult getNthInheritedClass(String className, int n)
	{
		return null;
	}

	@Override
	public int getInheritanceCount(String className)
	{
		return 0;
	}

	@Override
	public ICompilerResult getNthAlgorithmItem(String className, int n)
	{
		return null;
	}

	@Override
	public int getAlgorithmItemsCount(String className)
	{
		return 0;
	}

	@Override
	public ICompilerResult getNthEquationItem(String className, int n)
	{
		return null;
	}

	@Override
	public int getEquationItemsCount(String className)
	{
		return 0;
	}


	@Override
	public List getComponents(String className)
	{
		return null;
	}

	@Override
	public boolean existClass(String className)
	{
		return false;
	}

	@Override
	public boolean isPackage(String className)
	{
		return false;
	}

	@Override
	public ICompilerResult getErrorString()
	{
		return null;
	}

	@Override
	public ICompilerResult loadFile(String classPath) throws ConnectException, UnexpectedReplyException
	{
		return null;
	}

	@Override
	public ICompilerResult getSourceFile(String className) throws ConnectException, UnexpectedReplyException
	{
		return null;
	}

	@Override
	public List parseFile(String fileName) throws ConnectException, UnexpectedReplyException
	{
		return null;
	}

	@Override
	public ICompilerResult getClassRestriction(String className) throws ConnectException, UnexpectedReplyException
	{
		return null;
	}

	@Override
	public ICompilerResult getClassComment(String className) throws ConnectException, UnexpectedReplyException
	{
		return null;
	}


	@Override
	public ICompilerResult buildModel(String className) throws ConnectException, UnexpectedReplyException
	{
		return null;
	}

	@Override
	public ICompilerResult getNthImport(String className, int n) throws ConnectException, UnexpectedReplyException {
		return null;
	}

	@Override
	public int getImportCount(String className) throws ConnectException, UnexpectedReplyException {
		return 0;
	}

	@Override
	public String getModelicaPath() throws ConnectException, UnexpectedReplyException {
		return "";
	}

	@Override
	public String instantiateModel(String modelname) throws ConnectException {
		return "";
	}

	@Override
	public boolean isConnector(String className) {
		return false;
	}

	@Override
	public ICompilerResult searchClassNames(String className) {
		return null;
	}

	@Override
	public boolean isPrimitive(String className) throws ConnectException {
		// TODO Auto-generated method stub
		return false;
	}
}
