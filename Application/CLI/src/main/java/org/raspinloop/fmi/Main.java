package org.raspinloop.fmi;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.raspinloop.fmi.CoSimulation.Iface;
import org.raspinloop.fmi.launcher.FmiProxy;
import org.raspinloop.fmi.launcher.fmi.FMIListenServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final String RR_INTERNAL_ERROR = "internal error";
	
	private static Logger logger = LoggerFactory.getLogger(FmiProxyMain.class);

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
		org.raspinloop.fmi.launcher.FmiProxy fmiProxy = new FmiProxy(monitor);
		CoSimulation.Processor<Iface> processor = new CoSimulation.Processor<Iface>(fmiProxy);

		TServerTransport serverTransport;
		try {
			serverTransport = new TServerSocket(9090);
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

			VMRunner processunderDebug = new VMRunner(jSonConfigName, vMArguments, classpaths, className, programArguments);
			server.setServerEventHandler(new FMIListenServerHandler(server, fmiProxy, processunderDebug, monitor));

			monitor.subTask("Starting the FMI-Proxy server...");
			server.serve();
			logger.info("FMI-Proxy server stopped");
		} catch (TTransportException e) {
			logger.error("Communication with the simulation tool interrupted.", e, RR_INTERNAL_ERROR);
		}
	}

}
