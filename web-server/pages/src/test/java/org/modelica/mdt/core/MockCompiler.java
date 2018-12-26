package org.modelica.mdt.core;

import java.io.File;
import java.util.Collection;

import org.modelica.mdt.core.IModelicaClass.Restriction;
import org.modelica.mdt.core.compiler.ElementInfo;
import org.modelica.mdt.core.compiler.IClassInfo;
import org.modelica.mdt.core.compiler.IModelicaCompiler;
import org.modelica.mdt.core.compiler.IParseResults;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;

public class MockCompiler implements IModelicaCompiler{

	@Override
	public String getCompilerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IParseResults loadSourceFile(File file) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getClassNames(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ElementInfo> getElements(String className) throws ConnectException, InvocationError, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDefinitionLocation getClassLocation(String className) throws ConnectException, UnexpectedReplyException, InvocationError {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Restriction getRestriction(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getStandardLibrary() throws ConnectException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IClassInfo getClassInfo(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompilerResult getClassString(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompilerResult sendExpression(String command, boolean showInConsole) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompilerResult getNthInheritedClass(String className, int n) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInheritanceCount(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ICompilerResult getNthAlgorithmItem(String className, int n) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAlgorithmItemsCount(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ICompilerResult getNthEquationItem(String className, int n) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getEquationItemsCount(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List getComponents(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existClass(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPackage(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConnector(String className) throws ConnectException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ICompilerResult getErrorString() throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompilerResult loadFile(String classPath) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompilerResult getSourceFile(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List parseFile(String fileName) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompilerResult getClassRestriction(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompilerResult getClassComment(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompilerResult buildModel(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompilerResult getNthImport(String className, int n) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getImportCount(String className) throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getModelicaPath() throws ConnectException, UnexpectedReplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String instantiateModel(String modelname) throws ConnectException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompilerResult searchClassNames(String className) throws ConnectException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPrimitive(String className) throws ConnectException {
		// TODO Auto-generated method stub
		return false;
	}

}
