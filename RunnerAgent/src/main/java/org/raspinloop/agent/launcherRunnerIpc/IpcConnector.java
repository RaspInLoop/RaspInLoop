package org.raspinloop.agent.launcherRunnerIpc;

import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.raspinloop.agent.launcherRunnerIpc.LauncherService.Client;
import org.raspinloop.agent.launcherRunnerIpc.RunnerService.Iface;
import org.raspinloop.agent.launcherRunnerIpc.RunnerService.Processor;
import org.raspinloop.agent.CSHandler;
import org.raspinloop.agent.Handler;
import org.raspinloop.agent.HandlerRunner;

/**
 * Two way communication between launcher and runner. This is the runner part.
 * 
 * Runner must first connect its client socket to the launcher server. Then it
 * receives the port to listen for its server part.
 * 
 * @author Motte
 *
 */
public class IpcConnector implements HandlerRunner {

	private static final int RUNNER_SERVER_START_PORT = 9092;

	private static final int LAUNCHER_SERVER_PORT = 9091;

	final static Logger logger = Logger.getLogger(IpcConnector.class);

	public RunnerService.Processor<RunnerService.Iface> processor;

	private Client client;

	public IpcConnector(CSHandler handler) {
		processor = new Processor<Iface>(handler);
	}

	public IpcConnector() {
	}

	@Override
	public void run() {
		try {
			TSocket transport = new TSocket("localhost", LAUNCHER_SERVER_PORT, 1000, 500);
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			client = new Client(protocol);
		} catch (Exception e) {
			System.err.println("runner error:" + e.getMessage());
			System.err.println("Is there any launcher listening on port "+LAUNCHER_SERVER_PORT);
			System.exit(1);
		}

		TServerTransport serverTransport = null;
		int tryNb = 0;
		while (serverTransport == null) {
			try {
				serverTransport = new TServerSocket(RUNNER_SERVER_START_PORT + tryNb);
			} catch (Exception e) {
				tryNb++;
				if (tryNb > 20) {
					logger.fatal("runner error:" + e.getMessage());
					return;
				}
			}
		}
		TServer server = new TSimpleServer(new TSimpleServer.Args(serverTransport).processor(processor));

		server.setServerEventHandler(new TServerEventHandler() {

			@Override
			public void processContext(ServerContext serverContext, TTransport inputTransport, TTransport outputTransport) {
			}

			@Override
			public void preServe() {
				try {
					client.ReadyToStart(9092);
				} catch (TException e) {
					logger.fatal("runner cannot be launched:" + e.getMessage());
				}
			}

			@Override
			public void deleteContext(ServerContext serverContext, TProtocol input, TProtocol output) {
			}

			@Override
			public ServerContext createContext(TProtocol input, TProtocol output) {
				return null;
			}
		});

		server.serve();

	}

	@Override
	public void setHandle(Handler handler) {
		processor = new Processor<Iface>(handler.getCsHandler());
	}

}
