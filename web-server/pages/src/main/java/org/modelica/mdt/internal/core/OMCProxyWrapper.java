package org.modelica.mdt.internal.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import org.modelica.mdt.core.ICompilerResult;
import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IllegalRestrictionException;
import org.modelica.mdt.core.List;
import org.modelica.mdt.core.ListElement;
import org.modelica.mdt.core.ModelicaParserException;
import org.modelica.mdt.core.compiler.ComponentParser;
import org.modelica.mdt.core.compiler.ElementInfo;
import org.modelica.mdt.core.compiler.IClassInfo;
import org.modelica.mdt.core.compiler.IModelicaCompiler;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.ModelicaParser;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;
import org.openmodelica.corba.Result;
import org.openmodelica.corba.SmartProxy;


public class OMCProxyWrapper implements IModelicaCompiler {
	
	    SmartProxy smartProxy = new SmartProxy("Raspinloop", "Modelica", false, false);
		
	    /* indicates if the Modelica System Library has been loaded */
		private String[] omcLibraries = {"Modelica"};

		private boolean traceStatusPreferences = false;
		private boolean traceCommandsPreferences = false;
		private boolean traceErrorPreferences = false;
		private boolean traceReplyPrefereces = false;
		

		/**
		 * Send expression to OMC. If communication is not initialized, it is initialized here.
		 * @param command the expression to send to OMC
		 * @param showInConsole true or false
		 * @throws ConnectException if we're unable to start communicating with
		 * @return a String[] {result, answerToGetErrorString};
		 * the server
		 */
		// TODO add synchronization so that two threads don't fudge up each others
		// communication with OMC
		// old synchronization aka 'private synchronized String sendExpression(String command)'
		// doesn't work when there is possibility of multiple instances of OMCProxy objects
		@Override
		public ICompilerResult sendExpression(String command, boolean showInConsole) throws ConnectException {			
			Result result = smartProxy.sendExpression(command);			
			return CompilerResult.makeResult(result);
		}

		/**
		 * Get the classes contained in a class (a package is a class..)
		 *
		 *
		 * @param className full class name where to look for packages
		 * @return a <code>List</code> of subclasses defined (and loaded into OMC)
		 * inside the class named className.
		 *
		 * @throws ConnectException
		 * @throws UnexpectedReplyException
		 * @throws InitializationException
		 */
		
		public List getClassNames(String className) throws ConnectException, UnexpectedReplyException {
			ICompilerResult retval = sendExpression("getClassNames(" + className + ")", false);

			List list = null;
			try {
				list = ModelicaParser.parseList(retval.getFirstResult());
			}
			catch (ModelicaParserException e) {
				throw new UnexpectedReplyException("Unable to parse list: "
						+ e.getMessage());
			}

			return list;
		}

		/**
		 * Gets the type of restriction of a class.
		 *
		 * @param className fully qualified class name
		 * @return the type of restriction of the class or Type.CLASS if
		 *	   type can't be determined
		 * @throws ConnectException
		 * @throws UnexpectedReplyException
		 */
		@Override
		public IModelicaClass.Restriction getRestriction(String className)
				throws ConnectException, UnexpectedReplyException {
			ICompilerResult result = sendExpression("getClassRestriction(" + className + ")", false);

			String reply = result.getFirstResult();
			/* remove " around the reply */
			reply = reply.trim();

			if (reply.equals("")) {
				throw new UnexpectedReplyException("getClassRestriction(" + className + ") returned an empty result");
			}

			reply = reply.substring(1, reply.length() - 1);

			IModelicaClass.Restriction type = null;
			try {
				type = IModelicaClass.Restriction.parse(reply);
			}
			catch (IllegalRestrictionException e) {
				throw new UnexpectedReplyException("Illegal type: " + e.getMessage());
			}

			return type;
		}

		/**
		 * @author Adrian Pop [adrpo@ida.liu.se]
		 * @param retval, the string returned by the OMC compiler
		 * @return checks if the string is actually an error
		 */
		public boolean isError(String retval) {
			if (retval == null) {
				return false;
			}
			return retval.toLowerCase().contains("error");
		}

		protected class LazyLoadResult {
			ParseResults results;
			long lastModification;

			public LazyLoadResult(ParseResults results, long lastModification) {
				this.results = results;
				this.lastModification = lastModification;
			}
		}

		private static Map<Path, LazyLoadResult> lazyLoadList = new HashMap<Path, LazyLoadResult>();

		public static Map<Path, LazyLoadResult> getLazyLoadList() {
			return lazyLoadList;
		}

		/**
		 * Tries to load file into OMC which causes it to be parsed and the syntax
		 * checked.
		 * @param file the file we want to load
		 * @return a <code>ParseResult</code> containing the classes found in the
		 * file and the error messages from OMC
		 * @throws ConnectException
		 * @throws UnexpectedReplyException
		 * @throws InitializationException
		 */
		@Override
		public ParseResults loadSourceFile(File file) throws ConnectException, UnexpectedReplyException {
			final Path absoluteWorkspacePath = file.toPath();
			synchronized (getLazyLoadList()) {
				// activate lazy load
				if (getLazyLoadList().containsKey(absoluteWorkspacePath)) {
					if (file.exists()) {
						long lastModification = file.lastModified();
						if (lastModification != 0) {
							LazyLoadResult llr = getLazyLoadList().get(absoluteWorkspacePath);
							if (llr.lastModification >= lastModification) {
								return llr.results;
							}
						}
					}
				}
			}

			String fullName = file.getAbsolutePath();

	
			ParseResults res = new ParseResults();

			ICompilerResult ret = sendExpression("loadFileInteractiveQualified(\"" + fullName + "\")", false);

			String retval = ret.getFirstResult();
			/* Always keep your stuff nice and tidy! */
			retval = retval.trim();
			String errorString = ret.getError();

			if (isError(errorString)) {
				res.setClassNames(new List());
				res.setCompileErrors(OMCParser.parseErrorString(errorString));
			}
			else { // File loaded and parsed successfully
				try {
					res.setClassNames(ModelicaParser.parseList(retval));
				}
				catch (ModelicaParserException e) {
					System.out.println("Unable to parse list: " + e.getMessage());
					System.out.flush();
					throw new UnexpectedReplyException("Unable to parse list: " + e.getMessage());
				}

				/*
				 * If there were errors, but the compilation went through,
				 * collect the error messages. (Test if errorString != "")
				 */
				if (errorString.equals("") == false) {
					res.setCompileErrors(OMCParser.parseErrorString(errorString));
				}
			}

			synchronized (getLazyLoadList()) {
				if (file.exists()) {
					long lastModification = file.lastModified();
					if (lastModification != 0) {
						getLazyLoadList().put(file.toPath(), new LazyLoadResult(res, lastModification));
					}
				}
			}
			return res;
		}

		/**
		 * Gets the location (file, starting and ending line number and column
		 * number) of a Modelica element.
		 * @param className the element we want to get location of
		 * @return an <code>ElementLocation</code> containing the file, starting and
		 * ending line number and column number of the given class
		 * @throws ConnectException
		 * @throws UnexpectedReplyException
		 * @throws InvocationError
		 */
		@Override
		public DefinitionLocation getClassLocation(String className)
				throws ConnectException, UnexpectedReplyException, InvocationError {
			ICompilerResult res = sendExpression("getCrefInfo(" + className + ")", false);

			String retval = res.getFirstResult().trim();

			if (isError(retval)) {
				throw new InvocationError("Fetching file position of " + className, "getCrefInfo(" + className + ")");
			}

			/*
			 * The getCrefInfo reply has the following format:
			 *
			 * {<file path>,<something>,<start line>,<start column>,<end line>,<end column>}
			 *
			 * for example:
			 * {/foo/Modelica/package.mo,writable,1,1,1029,13}
			 */

			List tokens = null;
			try {
				tokens = ModelicaParser.parseList(retval);
			}
			catch (ModelicaParserException e) {
				throw new UnexpectedReplyException("Unable to parse list: " + e.getMessage());
			}

			String filePath = tokens.elementAt(0).toString();
			int startLine;
			int startColumn;
			int endLine;
			int endColumn;

			try {
				startLine = Integer.parseInt(tokens.elementAt(2).toString());
				startColumn = Integer.parseInt(tokens.elementAt(3).toString());
				endLine = Integer.parseInt(tokens.elementAt(4).toString());
				endColumn = Integer.parseInt(tokens.elementAt(5).toString());

				if (startColumn == 0) {
					assert(startLine == 1);
					startColumn = 1;
				}
			}
			catch (NumberFormatException e) {
				throw new UnexpectedReplyException("Can't parse getCrefInfo() reply, " +
						"unexpected format");
			}

			return new DefinitionLocation(filePath, startLine, startColumn, endLine, endColumn);
		}

		/**
		 * Queries the compiler if a particular modelica class/package is a package.
		 *
		 * @param className fully qualified name of the class/package
		 * @return true if className is a package, false otherwise
		 * @throws ConnectException
		 */
		@Override
		public boolean isPackage(String className) throws ConnectException {
			ICompilerResult res = sendExpression("isPackage(" + className + ")", false);
			String retval = res.getFirstResult();

			return retval.contains("true");
		}


		private HashMap<String, Boolean> connectorCache = new  HashMap<>();
		@Override
		public boolean isConnector(String className)
				throws ConnectException {
			if (connectorCache.containsKey(className))
				return connectorCache.get(className);
			ICompilerResult res = sendExpression("isConnector(" + className + ")", false);
			String retval = res.getFirstResult();
			boolean result = retval.contains("true");
			connectorCache.put(className, result);
			return result;
		}
		
		
		private HashMap<String, Collection<ElementInfo>> ElementsCache = new  HashMap<>();
		/**
		 * Uses the OMC API call getElementsInfo to fetch lots of information
		 * about a class definition. See interactive_api.txt in the OMC
		 * source tree.
		 * @param className the fully qualified name of a class
		 * @return a <code>Collection</code> (of <code>ElementsInfo</code>)
		 * containing the information about className
		 */
		@Override
		public Collection<ElementInfo> getElements(String className)
				throws ConnectException, InvocationError, UnexpectedReplyException {
			if (ElementsCache.containsKey(className))
				return ElementsCache.get(className);
			ICompilerResult res = sendExpression("getElementsInfo(" + className + ")", false);
			String retval = res.getFirstResult();
			/*
			 * we need a efficient way to check if the result is
			 * humongosly huge list or 'Error' or maybe 'error'
			 */
			for (int i = 0; i < retval.length(); i++) {
				if (retval.charAt(i) == '{') {
					/*
					 * we found the beginning of the list, send it to parser and
					 * hope for the best
					 */
					List parsedList = null;
					try {
						parsedList = ModelicaParser.parseList(retval);
					}
					catch (ModelicaParserException e) {
						throw new UnexpectedReplyException("Unable to parse list: " + e.getMessage());
					}

					/* convert the parsedList to a collection of ElementsInfo:s */
					LinkedList<ElementInfo> elementsInfo = new LinkedList<ElementInfo>();

					for (ListElement element : parsedList) {
						elementsInfo.add(new ElementInfo((List)element));
					}
					ElementsCache.put(className, elementsInfo);
					return elementsInfo;
				}
				else if (retval.charAt(i) == 'E' || retval.charAt(i) == 'e') {
					/*
					 * this is the unreadable way to check if the retval
					 * equals 'Error' or 'error'
					 */
					if (retval.substring(i + 1, i + 5).equals("rror")) {
						throw new InvocationError("fetching contents of " + className, "getElementsInfo(" + className +")");
					}
					else {
						/* OMC returned something weird, panic mode ! */
						break;
					}
				}
			}
			/* we have no idea what OMC returned */
			throw new UnexpectedReplyException("getElementsInfo(" + className + ")" + "replies:'" + retval + "'");
		}

		@Override
		public IClassInfo getClassInfo(String className)
				throws ConnectException, UnexpectedReplyException {
			ICompilerResult res = sendExpression("getClassInformation(" + className + ")", false);
			String retval = res.getFirstResult();

			ClassInfo ci = null;

			try {
				ci = new ClassInfo(retval);
			}
			catch (ModelicaParserException e) {
				throw new UnexpectedReplyException(retval);
			}
			catch (IllegalRestrictionException e) {
				throw new UnexpectedReplyException(retval);
			}

			return	ci;
		}


		public String getCompilerVersion() throws ConnectException {
			ICompilerResult res = sendExpression("getVersion()", false);
			String retval = res.getFirstResult();
			if (retval.length() == 0) {
				return null;
			}
			if (retval.charAt(0) == '"') {
				retval = retval.substring(1);
			}
			int lio = -1;
			if ((lio = retval.lastIndexOf('"')) > 0) {
				retval = retval.substring(0, lio);
			}
			return retval.trim();
		}

		/**
		 * @return the name of the compiler that this plugin tries to communicate
		 * with (at least it tries...)
		 */
		@Override
		public String getCompilerName() {
			String version = "";
			try {
				version = getCompilerVersion();
			}
			catch (ConnectException e) {
				//TODO: ErrorManager.logError(e);
			}
			return "OpenModelica Compiler " + version;
		}

		/**
		 * Loads in the Modelica System Library and returns names of the top-level
		 * packages.
		 *
		 * @throws ConnectException if we're unable to start communicating with
		 * the server
		 */
		@Override
		public String[] getStandardLibrary() throws ConnectException {
	
			for (int i = 0; i < omcLibraries.length; i++) {
				sendExpression("loadModel(" + omcLibraries[i] + ")", false);
			}
	
			return omcLibraries;
		}

		/**
		 * lists the name of a class
		 *
		 * @throws ConnectException if we're unable to start communicating with
		 * the server
		 */
		@Override
		public ICompilerResult getClassString(String className) throws ConnectException, UnexpectedReplyException {
			ICompilerResult res = sendExpression("list("+ className +")", false);

			res.trimFirstResult();

			return res;
		}

		/**
		 *
		 * @author Adrian Pop
		 *
		 */
		public class ConsoleWriter extends Thread {
			private PrintWriter consoleWriter = null;
			private String message = null;
			private String prefix = null;

			public ConsoleWriter(OutputStream output, String prefix) {
				if (output != null) {
					consoleWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(output)));
				}
				this.prefix = prefix;
			}

			public synchronized void setMessage(String what) {
				message = what;
			}

			@Override
			public synchronized void run() {
				if (message != null) {
					StringTokenizer tokenizer = new StringTokenizer(message, "\n");
					while (tokenizer.hasMoreTokens()) {
						consoleWriter.write("\nmdt> " + prefix + tokenizer.nextToken());
					}
					consoleWriter.write("\nomc> ");
					consoleWriter.flush();
				}
			}
		}

		/**
		 * Evaluate command locally, don't sent it to OpenModelica Compiler
		 * @author Adrian Pop
		 * @param String command
		 * @return String result
		 */
		String evaluateExpressionLocally(String command) {
			String retval = "Unknown command!";
			if (command.equals("help")) {
				retval =
						"All the MDT Console local commands start with '!'.\n" +
								"All commands not starting with '!' will be sent to the OpenModelica compiler.\n" +
								"For a list of OpenModelica commands available type help().\n" +
								"NOTE: The !trace* commands display traces of all OpenModelica<->MDT\n" +
								"      communication except the commands send from the console!\n" +
								"Available local commands are:\n" +
								"!help	       - toggle display help on local commands.\n" +
								"!traceStatus  - toggle display the status of the OpenModelica compiler.\n" +
								"!traceError   - toggle display errors talking with the OpenModelica compiler.\n" +
								"!traceReply   - toggle display the reply of the OpenModelica compiler.\n" +
								"!traceCommand - toggle display the commands send to the OpenModelica compiler.\n" +
								"!traceAll     - toggle on the display of all the traces available.\n" +
								"!traceNone    - toggle off the display of all the traces available.\n";
			}

			if (command.equals("traceStatus")) {
				traceStatusPreferences = traceStatusPreferences ? false : true;
				retval = "Tracing of OpenModelica status is set to: " + traceStatusPreferences;
			}

			if (command.equals("traceError")) {
				traceErrorPreferences = traceErrorPreferences ? false : true;
				retval =
						"Tracing of errors while talking with OpenModelica is set to: " + traceErrorPreferences;
			}

			if (command.equals("traceReply")) {
				traceReplyPrefereces = traceReplyPrefereces ? false : true;
				retval = "Tracing of OpenModelica reply to commands is set to: " + traceReplyPrefereces;
			}

			if (command.equals("traceCommand")) {
				traceCommandsPreferences = traceCommandsPreferences ? false : true;
				retval = "Tracing of commands sent to OpenModelica is set to: " + traceCommandsPreferences;
			}

			if (command.equals("traceAll")) {
				traceCommandsPreferences = true;
				traceReplyPrefereces	 = true;
				traceErrorPreferences	 = true;
				traceStatusPreferences	 = true;
				retval = "All tracing is now enabled!";
			}

			if (command.equals("traceNone")) {
				traceCommandsPreferences = false;
				traceReplyPrefereces	 = false;
				traceErrorPreferences	 = false;
				traceStatusPreferences	 = false;
				retval = "All tracing is now disabled!";
			}

			return "\n" + retval;
		}

		

		/*
		 * Extended by Magnus Sjöstrand
		 *
		 */

		/**
		 * gets the nth inheritance to the given class
		 *
		 * @throws ConnectException if we're unable to start communicating with
		 * the server
		 */

		@Override
		public ICompilerResult getNthInheritedClass(String className, int n) throws ConnectException, UnexpectedReplyException {
			ICompilerResult res = sendExpression("getNthInheritedClass("+ className + ", " + n + ")", false);
			res.trimFirstResult();

			return res;
		}

		/**
		 * gets the number of inheritances to the given class
		 *
		 * @throws ConnectException if we're unable to start communicating with
		 * the server
		 */

		@Override
		public int getInheritanceCount(String className) throws ConnectException, UnexpectedReplyException {
			int resNum = 0;
			ICompilerResult res = sendExpression("getInheritanceCount(" + className + ")", false);
			res.trimFirstResult();
			if (!res.getFirstResult().isEmpty()){
				resNum = Integer.parseInt(res.getFirstResult());
			}
			return resNum;
		}

		/**
		 * gets the nth algorithm in the given class
		 *
		 * @throws ConnectException if we're unable to start communicating with
		 * the server
		 */

		@Override
		public ICompilerResult getNthAlgorithmItem(String className, int n) throws ConnectException, UnexpectedReplyException {
			ICompilerResult res = sendExpression("getNthAlgorithmItem("+ className + ", " + n + ")", false);
			res.trimFirstResult();

			return res;
		}

		/**
		 * gets the number of algorithms in the given class
		 *
		 * @throws ConnectException if we're unable to start communicating with
		 * the server
		 */

		@Override
		public int getAlgorithmItemsCount(String className) throws ConnectException, UnexpectedReplyException {
			int resNum = 0;
			ICompilerResult res = sendExpression("getAlgorithmItemsCount(" + className + ")", false);
			res.trimFirstResult();
			if (!res.getFirstResult().isEmpty()){
				resNum = Integer.parseInt(res.getFirstResult());
			}
			return resNum;
		}

		/**
		 * gets the nth equation in the given class
		 *
		 * @throws ConnectException if we're unable to start communicating with
		 * the server
		 */

		@Override
		public ICompilerResult getNthEquationItem(String className, int n) throws ConnectException, UnexpectedReplyException {
			ICompilerResult res = sendExpression("getNthEquationItem("+ className + ", " + n + ")", false);
			res.trimFirstResult();

			return res;
		}

		/**
		 * gets the number of equations to the given class
		 *
		 * @throws ConnectException if we're unable to start communicating with
		 * the server
		 */

		@Override
		public int getEquationItemsCount(String className) throws ConnectException, UnexpectedReplyException {
			int resNum = 0;
			ICompilerResult res = sendExpression("getEquationItemsCount(" + className + ")", false);
			res.trimFirstResult();
			if (!res.getFirstResult().isEmpty()){
				resNum = Integer.parseInt(res.getFirstResult());
			}
			return resNum;
		}

		/**
		 * lists all of the components in class
		 *
		 * @throws ConnectException if we're unable to start communicating with
		 * the server
		 */

		@Override
		public List getComponents(String className) throws ConnectException, UnexpectedReplyException {
			ICompilerResult res = sendExpression("getComponents("+ className +")", false);
			//System.out.println(res.getFirstResult());
			List list = null;
			try {
				list = ComponentParser.parseList(res.getFirstResult());
			}
			catch (ModelicaParserException e) {
				throw new UnexpectedReplyException("Unable to parse list: "
						+ e.getMessage());
			}
			return list;
		}

		@Override
		public boolean existClass(String className) throws ConnectException, UnexpectedReplyException {
			ICompilerResult res = sendExpression("existClass("+ className +")", false);
			if (res.getFirstResult().trim().toString().equals("true")) {
				return true;
			}
			return false;
		}

		@Override
		public ICompilerResult getErrorString() throws ConnectException, UnexpectedReplyException {
			ICompilerResult res = sendExpression("getErrorString()", false);
			return res;
		}

		@Override
		public ICompilerResult loadFile(String classPath) throws ConnectException, UnexpectedReplyException {
			classPath = classPath.replace("\\", "/");
			ICompilerResult res = sendExpression("loadFile(\""+ classPath +"\")", false);
			return res;
		}

		@Override
		public ICompilerResult getSourceFile(String className) throws ConnectException, UnexpectedReplyException {
			ICompilerResult res = sendExpression("getSourceFile(" + className +")", false);
			return res;
		}

		@Override
		public List parseFile(String fileName) throws ConnectException, UnexpectedReplyException {
			ICompilerResult retval = sendExpression("parseFile(\""+ fileName + "\")", false);

			List list = null;
			try {
				list = ModelicaParser.parseList(retval.getFirstResult());
			}
			catch (ModelicaParserException e) {
				throw new UnexpectedReplyException("Unable to parse list: "
						+ e.getMessage());
			}

			return list;
		}

		@Override
		public ICompilerResult getClassRestriction(String className) throws ConnectException, UnexpectedReplyException {
			ICompilerResult res = sendExpression("getClassRestriction(" + className + ")", false);
			return res;
		}

		@Override
		public ICompilerResult getClassComment(String className) throws ConnectException, UnexpectedReplyException {
			ICompilerResult res = sendExpression("getClassComment(" + className + ")", false);
			return res;
		}

		public ICompilerResult buildModel(String className) throws ConnectException, UnexpectedReplyException {
			ICompilerResult res = sendExpression("buildModel(" + className + ")", false);
			return res;
		}

		@Override
		public ICompilerResult getNthImport(String className, int n)
				throws ConnectException, UnexpectedReplyException
		{
			ICompilerResult res = sendExpression("getNthImport("+ className + ", " + n + ")", false);
			res.trimFirstResult();

			return res;
		}

		@Override
		public int getImportCount(String className) throws ConnectException,
				UnexpectedReplyException
		{
			int resNum = 0;
			ICompilerResult res = sendExpression("getImportCount(" + className + ")", false);
			res.trimFirstResult();
			if (!res.getFirstResult().isEmpty()){
				resNum = Integer.parseInt(res.getFirstResult());
			}
			return resNum;
		}

		@Override
		public String getModelicaPath() throws ConnectException,
				UnexpectedReplyException
		{
			ICompilerResult res = sendExpression("getModelicaPath()", false);
			String retval = res.getFirstResult();
			return retval;
		}

		/**
		 * Instantiates a model/class and returns a string containing the flat class definition.
		 * Ex: instantiateModel(dcmotor)
		 *
		 * @param modelname the modelname
		 * @return Reply from OMC
		 * @throws ConnectException 
		 */
		
		@Override
		public String instantiateModel(String modelname) throws ConnectException{
			ICompilerResult res = sendExpression("instantiateModel("+ modelname + ")", false);
			String retval = res.getFirstResult();
			return retval;
		}

		@Override
		public ICompilerResult searchClassNames(String className) throws ConnectException {
			ICompilerResult res = sendExpression("searchClassNames("+ className + ")", false);
			return res;
		}

		private HashMap<String, Boolean> primitiveCache = new  HashMap<>();
		
		@Override
		public boolean isPrimitive(String className) throws ConnectException {
				if (primitiveCache.containsKey(className))
					return primitiveCache.get(className);
				ICompilerResult res = sendExpression("isPrimitive(" + className + ")", false);
				String retval = res.getFirstResult();
				boolean result = retval.contains("true");
				primitiveCache.put(className, result);
				return result;
			}
	}
