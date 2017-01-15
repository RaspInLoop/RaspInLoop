package org.raspinloop.fmi;

import org.apache.log4j.Logger;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.raspinloop.fmi.CoSimulation.Iface;
import org.raspinloop.fmi.CoSimulation.Processor;

public class Server implements HandlerRunner {

	final static Logger logger = Logger.getLogger(Server.class);
	
	public CoSimulation.Processor<Iface> processor;

	public Server(CSHandler handler) {
		processor = new Processor<CoSimulation.Iface>(handler);
	}
	
	public Server() {
	}

	@Override
	public void run() {
		try {
			TServerTransport serverTransport = new TServerSocket(9090);
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(
					serverTransport).processor(processor));

			logger.info("Starting the simple server...");
			server.serve();
		} catch (Exception e) {
			//TODO
			e.printStackTrace();
		}
	}

	@Override
	public void setHandle(Handler handler) {
		processor = new Processor<CoSimulation.Iface>(handler.getCsHandler());		
	}

}
