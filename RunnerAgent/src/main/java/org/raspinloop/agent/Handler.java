package org.raspinloop.agent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.raspinloop.hwemulation.GpioProvider;
import org.raspinloop.hwemulation.HwEmulationFactory;
import org.raspinloop.hwemulation.HwEmulationFactoryFromJson;
import org.raspinloop.timeemulation.SimulatedTime;


public class Handler {

	public static Handler build(String jsonConfig) throws Exception {
		return new Handler(jsonConfig);
	}

	final static Logger logger = Logger.getLogger(Handler.class);

	final Executor ex = Executors.newCachedThreadPool();

	private CSHandler fmiHandler;
	
	private boolean readyForMain = false;
	
	public boolean isReadyForMain() {
		return readyForMain;
	}

	private void setReadyForMain() {
		this.readyForMain = true;
	}

	private Handler(String jsonConfig) throws Exception {

		fmiHandler = CSHandler.getInstance();
		

		HwEmulationFactory factory = new HwEmulationFactoryFromJson();

		if (factory.create(jsonConfig) == null){
			logger.fatal("Cannot create instance for json["+jsonConfig+"]");
			throw new Exception("Cannot create hardware for json");
		}
		
		fmiHandler.registerHardware(factory);

//		final SimulatedTimeExecutorServiceFactory simulatedTimeExecutorFactory = new SimulatedTimeExecutorServiceFactory();
		fmiHandler.addExperimentListener(new ExperimentListener() {

			@Override
			public void notifyStart(final SimulatedTime st, final GpioProvider provider) {
				ex.execute(new Runnable() {

					

					@Override
					public void run() {
						try {
						
							setReadyForMain();

						} catch (IllegalArgumentException |  SecurityException e) {
							logger.info("main is unable to start");
							if (logger.isTraceEnabled())
								logger.trace("main is unable to start", e);							
							SimulatedTime.INST.stop();
							System.exit(0);
						} 
					}
				});
			}

			@Override
			public void notifyStop(GpioProvider gpioProvider) {
				logger.info("Stop Called");
//				GpioFactory.getExecutorServiceFactory().shutdown();
				SimulatedTime.INST.stop(); // this will cause next sleep to
											// be interrupted!
			}
		});
	}

	public void start(HandlerRunner runnable) {
		runnable.setHandle(this);
		ex.execute(runnable);		
	}

	public CSHandler getCsHandler() {		
		return fmiHandler;
	}

}
