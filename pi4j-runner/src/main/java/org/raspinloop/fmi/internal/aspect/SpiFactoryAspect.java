package org.raspinloop.fmi.internal.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.raspinloop.fmi.internal.hwemulation.SpiEmulation;

import com.pi4j.io.spi.SpiDevice;

@Aspect
public class SpiFactoryAspect {

	@Around("call(SpiDevice com.pi4j.io.spi.SpiFactory.getInstance(..)) ")
	public SpiDevice spiGetInstance(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();		
		return SpiEmulation.getInstance(args);
	}	
}
