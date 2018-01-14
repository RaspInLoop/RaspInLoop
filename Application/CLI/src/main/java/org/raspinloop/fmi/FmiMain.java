/*******************************************************************************
 * Copyright 2018 RaspInLoop
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.raspinloop.fmi;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.raspinloop.fmi.launcher.fmi.FmiProxyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FmiMain {

	private static final String ERR_INTERNAL_ERROR = "internal error";
	
	private static Logger logger = LoggerFactory.getLogger(FmiMain.class);

	public static void main(String[] args) {

		Options options = new Options();
		Option jSonConfigNameOption = Option.builder("j").hasArg().argName("jsonconfig").desc("json config file").required().build();

		Option vMArgumentsOption = Option.builder("v").hasArg().argName("vmargs").desc("VM Arguments").build();

		Option classPathsOption = Option.builder("c").hasArg().argName("classPath").desc("classPath for the application to run").build();

		Option classNameOption = Option.builder("m").hasArg().argName("mainclass").desc("classname of for the application to run").required().build();

		Option programArgumentOptions = Option.builder("p").hasArg().argName("pargs").desc("arguments for the application to run (quoted)").build();

		options.addOption(jSonConfigNameOption);
		options.addOption(classPathsOption);
		options.addOption(classNameOption);
		options.addOption(programArgumentOptions);
		options.addOption(vMArgumentsOption);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("FmiProxyMain", options);

			System.exit(1);
			return;
		}

		String jSonConfigName = cmd.getOptionValue("j");
		String vMArguments = cmd.getOptionValue("v");
		String classPath = cmd.getOptionValue("c");
		String[] classpaths = classPath.split(";");
		String className = cmd.getOptionValue("m");
		String programArguments = cmd.getOptionValue("p");

		FmiProxyMonitor monitor = new FmiProxyMonitor();
		VMRunner processunderDebug = new VMRunner(jSonConfigName, vMArguments, classpaths, className, programArguments);
		FmiProxyServer server = new FmiProxyServer(processunderDebug, monitor);
		try {
			server.start();
		} catch (Exception e) {
			logger.error("Communication with the simulation tool interrupted.", e, ERR_INTERNAL_ERROR);
		} finally {
			logger.info("FMI-Proxy server stopped");
		}
		}

}
