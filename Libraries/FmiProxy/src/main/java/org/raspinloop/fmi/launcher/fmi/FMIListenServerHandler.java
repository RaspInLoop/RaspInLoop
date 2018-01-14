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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TTransport;
import org.raspinloop.fmi.launcher.IProxyMonitor;
import org.raspinloop.fmi.launcher.Proxy;
import org.raspinloop.fmi.launcher.ProxyRunnerJob;
import org.raspinloop.fmi.launcher.Runner;


public class FMIListenServerHandler implements TServerEventHandler {


	private TServer server;
	private IProxyMonitor monitor;
	private Proxy proxy;
	private Runner runner;
	ExecutorService executor = Executors.newScheduledThreadPool(3);
	

	public FMIListenServerHandler(TServer server,Proxy proxy,  Runner runner,IProxyMonitor monitor) {
		this.server = server;
		this.proxy = proxy;
		this.runner = runner;
		this.monitor = monitor;
		
	}

	@Override
	public ServerContext createContext(TProtocol arg0, TProtocol arg1) {		
		return new FmiProxyRunnerJob(server, proxy, runner, monitor);
	}

	@Override
	public void deleteContext(ServerContext arg0, TProtocol arg1, TProtocol arg2) {
		if (arg0 instanceof ProxyRunnerJob) {
			ProxyRunnerJob fmiProxyRunnerFMIJob = (ProxyRunnerJob)arg0;
				fmiProxyRunnerFMIJob.cancel(); // ensure no more runner for this FMI-Proxy context
		}
		//simple server, so stop the server
		server.stop();
	}

	@Override
	public void preServe() {
	}

	@Override
	public void processContext(ServerContext arg0, TTransport arg1, TTransport arg2) {
	}

}
