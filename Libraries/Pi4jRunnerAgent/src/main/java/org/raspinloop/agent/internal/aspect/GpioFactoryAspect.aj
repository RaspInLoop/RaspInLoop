package org.raspinloop.fmi.internal.aspect;

import org.apache.log4j.Logger;
import org.raspinloop.fmi.Handler;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.impl.GpioScheduledExecutorImpl;

@SuppressWarnings("unused")
public privileged aspect GpioFactoryAspect {
	
	final static Logger logger = Logger.getLogger(GpioFactoryAspect.class);
	
	protected pointcut inFactory(): within(com.pi4j.io.gpio.GpioFactory);
	private pointcut getInstanceMethod() : inFactory() && execution(GpioController GpioFactory.getInstance());	

	before(): getInstanceMethod() {		
		if (Handler.mustBeReinit())
			GpioFactory.controller = null;					
	}
	
	protected pointcut inGpioScheduledExecutor(): within(com.pi4j.io.gpio.impl.GpioScheduledExecutorImpl);
	private pointcut getInitMethod() : inGpioScheduledExecutor() && execution( void GpioScheduledExecutorImpl.init(GpioPinDigitalOutput));	
	before(): getInitMethod(){
		//clear cached GpioScheduledExecutorImpl.scheduledExecutorService
		GpioScheduledExecutorImpl.scheduledExecutorService = null;
	}
}
