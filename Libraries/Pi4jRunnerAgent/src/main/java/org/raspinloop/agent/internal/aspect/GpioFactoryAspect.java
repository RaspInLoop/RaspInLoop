package org.raspinloop.agent.internal.aspect;
import java.lang.reflect.Field;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.raspinloop.agent.CSHandler;
import org.raspinloop.agent.internal.timeemulation.SimulatedTimeExecutorServiceFactory;

import com.pi4j.concurrent.ExecutorServiceFactory;
import com.pi4j.io.gpio.GpioProvider;

@Aspect
public class GpioFactoryAspect {

	private static GpioProvider provider;
	private static ExecutorServiceFactory executorServiceFactory;

//	@Before("within(com.pi4j.io.gpio.impl.GpioScheduledExecutorImpl) && execution( void com.pi4j.io.gpio.impl.GpioScheduledExecutorImpl.init(..))")
//	public void getInitMethod(ProceedingJoinPoint joinPoint) {
//		// clear cached GpioScheduledExecutorImpl.scheduledExecutorService
//		Object instance = joinPoint.getThis();
//
//		Field privateUriField;
//		try {
//			privateUriField = instance.getClass().getDeclaredField("scheduledExecutorService");
//
//			privateUriField.setAccessible(true);
//			privateUriField.set(instance, null);
//		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {			
//			e.printStackTrace();
//		}
//	}

	@Around("within(com.pi4j.io.gpio.GpioFactory) && execution(com.pi4j.io.gpio.GpioProvider com.pi4j.io.gpio.GpioFactory.getDefaultProvider())")
	public GpioProvider getDefaultProvider() {
		 // if a provider has not been created, then create a new instance
        if (provider == null) {
            // create the provider based on the json configuration
            provider = Adapters.forPi4j( CSHandler.getInstance().getHwEmulationFactory().get());
        }
        // return the provider instance
        return provider;
	}
	
	@Around("within(com.pi4j.io.gpio.GpioFactory) && execution(com.pi4j.concurrent.ExecutorServiceFactory com.pi4j.io.gpio.GpioFactory.getExecutorServiceFactory(..))")
	 public ExecutorServiceFactory getExecutorServiceFactory() {
	        // if an executor service provider factory has not been created, then create a new default instance
	        if (executorServiceFactory == null) {
	            executorServiceFactory = new SimulatedTimeExecutorServiceFactory();
	        }
	        // return the provider instance
	        return executorServiceFactory;
	    }
	
}