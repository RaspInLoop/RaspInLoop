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
package org.raspinloop.fmi.launcher.fmi;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import org.raspinloop.fmi.CoSimulation;
import org.raspinloop.fmi.CoSimulation.Iface;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.Runner;
import org.raspinloop.fmi.launcher.SimulationToolStatus;

public class FmiProxyServer {

	
	
	private Runner vmRunner;
	private IProxyMonitor proxyMonitor;
	
	
	public FmiProxyServer(Runner vmRunner, IProxyMonitor proxyMonitor) {
		super();
		this.vmRunner = vmRunner;
		this.proxyMonitor = proxyMonitor;
	}

	public void start() throws FmiProxyServerException{
		FmiProxy fmiProxy = new FmiProxy(proxyMonitor);
		CoSimulation.Processor<Iface> processor = new CoSimulation.Processor<Iface>(fmiProxy);

		
		TServerTransport serverTransport;
		try {
			serverTransport = new TServerSocket(9090);
			TServer server = new TSimpleServer(new TSimpleServer.Args(serverTransport).processor(processor));

			
			proxyMonitor.simulationToolsStatusChanged(SimulationToolStatus.WAITING);
			server.setServerEventHandler(new FMIListenServerHandler(server, fmiProxy,vmRunner , proxyMonitor));			
			server.serve();
		} catch (TTransportException e) {
			throw new FmiProxyServerException(e.getMessage());
		}
	}
}
