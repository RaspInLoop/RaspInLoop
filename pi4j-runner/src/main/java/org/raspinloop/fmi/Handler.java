package org.raspinloop.fmi;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.raspinloop.fmi.internal.hwemulation.HwEmulationFactory;
import org.raspinloop.fmi.internal.hwemulation.HwEmulationFactoryFromJson;
import org.raspinloop.fmi.internal.timeemulation.SimulatedTime;
import org.raspinloop.fmi.internal.timeemulation.SimulatedTimeExecutorServiceFactory;
import org.raspinloop.fmi.launcherRunnerIpc.Status;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioProvider;

public class Handler {

	public static Handler build(String jsonConfig, String mainclassName, String[] otherArgs) throws Exception {
		return new Handler(jsonConfig, mainclassName, otherArgs);
	}

	final static Logger logger = Logger.getLogger(Handler.class);

	final Executor ex = Executors.newCachedThreadPool();

	private CSHandler fmiHandler;
	
	private Handler(String jsonConfig, final String mainclassName, final String[] otherArgs) throws Exception {

		fmiHandler = new CSHandler();

		ClassLoader classLoader = Boot.class.getClassLoader();

		final Class<?> mainclass = classLoader.loadClass(mainclassName);		

		HwEmulationFactory factory = new HwEmulationFactoryFromJson();

		if (factory.create(jsonConfig) == null){
			logger.fatal("Cannot create instance for json["+jsonConfig+"]");
			throw new Exception("Cannot create hardware for json");
		}
		
		fmiHandler.registerHardware(factory);

		final SimulatedTimeExecutorServiceFactory simulatedTimeExecutorFactory = new SimulatedTimeExecutorServiceFactory();
		fmiHandler.addExperimentListener(new ExperimentListener() {

			@Override
			public void notifyStart(final SimulatedTime st, final GpioProvider provider) {
				ex.execute(new Runnable() {

					@Override
					public void run() {
						try {

							firstCall = true;
							// we have to set simulated raspberry provider
							GpioFactory.setDefaultProvider(provider);
							// executors need to be cleared before each run
							simulatedTimeExecutorFactory.reinit();
							GpioFactory.setExecutorServiceFactory(simulatedTimeExecutorFactory);
							// calling the main class
							Object[] param = { (Object[]) otherArgs };
							mainclass.getMethod("main", String[].class).invoke(null, param);							
						} catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e) {
							logger.info("main is unable to start");
							SimulatedTime.INST.stop();
						} catch (InvocationTargetException e) {
							logger.debug("main was stopped: " + e.getTargetException().getLocalizedMessage());
							SimulatedTime.INST.stop();
						}
						finally{
							System.exit(0);
						}
					}
				});
			}

			@Override
			public void notifyStop(GpioProvider gpioProvider) {
				logger.info("Stop Called");
				GpioFactory.getExecutorServiceFactory().shutdown();
				SimulatedTime.INST.stop(); // this will cause next sleep to
											// be interrupted!
			}
		});
	}

	private static boolean firstCall = true;
	public static GpioProvider currentProvider;

	public static boolean mustBeReinit() {
		if (firstCall) {
			firstCall  = false;
			return true;
		} else
			return false;
	}
	
	public void start(HandlerRunner runnable) {
		runnable.setHandle(this);
		ex.execute(runnable);		
	}

	public CSHandler getCsHandler() {		
		return fmiHandler;
	}

}
