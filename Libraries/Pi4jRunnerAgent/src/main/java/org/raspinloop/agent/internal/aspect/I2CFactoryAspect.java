package org.raspinloop.agent.internal.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.raspinloop.hwemulation.I2CBus;
import org.raspinloop.hwemulation.I2CEmulation;


@Aspect
public class I2CFactoryAspect {

	@Around("call(com.pi4j.io.i2c.I2CBus com.pi4j.io.i2c.I2CFactory.getInstance(..)) ")
	public com.pi4j.io.i2c.I2CBus i2CGetInstance(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();		
		return Adapters.forPi4j(I2CEmulation.getInstance(args));
	}	
}
